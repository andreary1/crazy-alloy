package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModEntities;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModSounds;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Gingerbread King (revival proposal, stage 3): the boss of the gingerbread men. Since stage 4 he waits on the throne
 * of the Gingerbread Fortress ({@link RevivalConfig#GINGERBREAD_KING_IN_FORTRESSES}); towers can still have one on
 * the roof ({@link RevivalConfig#GINGERBREAD_KING_IN_TOWERS}, off by default). He can also be summoned with his spawn egg.
 * <ul>
 * <li>Wide hooks with alternating fists.</li>
 * <li>Ground slam: a 1 second wind-up with both fists raised (the warning), then a shockwave that hurts and throws
 * everything within 4.5 blocks except his own gingerbread men.</li>
 * <li>Calls the guard: once he is below 80 % health he raises a fist and two Gingerbread Soldiers or Warriors pop up
 * next to him, up to a configurable number of guards nearby.</li>
 * <li>Below half health he gets angry: faster, and slams twice as often.</li>
 * </ul>
 * Shows a boss bar to every player tracking him, so it works the same in multiplayer.
 */
public class GingerbreadKing extends Monster implements AnimatedMob {
    public static final int SLAM_WINDUP = 20, SLAM_STRIKE = 4, SLAM_RECOVER = 12;
    public static final int SUMMON_LENGTH = 30, SUMMON_AT = 15;
    public static final double SLAM_RADIUS = 4.5;
    private static final Identifier RAGE_SPEED = Identifier.fromNamespaceAndPath("crazyalloy_revival", "king_rage_speed");

    private final ServerBossEvent bossEvent = new ServerBossEvent(Mth.createInsecureUUID(this.random), this.getDisplayName(),
            BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_10);
    private final AnimationState slamAnimation = new AnimationState();
    private final AnimationState summonAnimation = new AnimationState();
    private int slamTicks = -1;
    private int summonTicks = -1;
    private int slamCooldown = 60;
    private int summonCooldown = 100;
    private boolean enraged;
    private boolean leftHook;
    /** Placed by a Gingerbread Tower (removed on its first tick if towers should have no king). */
    private boolean fromTower;
    /** Placed on the throne of a Gingerbread Fortress (the structure sets FromFortress in its entity data). */
    private boolean fromFortress;

    public GingerbreadKing(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 80;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 250.0)
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.ATTACK_DAMAGE, 9.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.ARMOR, 6.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.9)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BusyGoal());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 0.8));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6, 0.0F));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, GingerbreadWarrior.class, GingerbreadSoldier.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(RevivalConfig.GINGERBREAD_KING_HEALTH.get());
        }
        groupData = super.finalizeSpawn(level, difficulty, reason, groupData);
        this.setHealth(this.getMaxHealth());
        this.setPersistenceRequired();
        if (reason == EntitySpawnReason.STRUCTURE) {
            if (this.fromFortress) {
                this.setHomeTo(this.blockPosition(), 8); // stays near his throne until someone comes for him
            } else {
                this.fromTower = true;
                this.setHomeTo(this.blockPosition(), 12);
            }
        }
        return groupData;
    }

    // --- boss bar --------------------------------------------------------------------------

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    // --- saving -----------------------------------------------------------------------------

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("Enraged", this.enraged);
        output.putBoolean("FromTower", this.fromTower);
        output.putBoolean("FromFortress", this.fromFortress);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.enraged = input.getBooleanOr("Enraged", false);
        this.fromTower = input.getBooleanOr("FromTower", false);
        this.fromFortress = input.getBooleanOr("FromFortress", false);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    // --- combat -----------------------------------------------------------------------------

    @Override
    public void swing(InteractionHand hand, boolean sendToSwingingEntity) {
        super.swing(this.leftHook ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND, sendToSwingingEntity);
        this.leftHook = !this.leftHook;
    }

    private boolean busy() {
        return this.slamTicks >= 0 || this.summonTicks >= 0;
    }

    private static boolean isGingerbread(@Nullable Entity entity) {
        return entity instanceof GingerbreadKing || entity instanceof GingerbreadWarrior || entity instanceof GingerbreadSoldier;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        // His own guards' stray gumdrops and fists do not hurt him.
        if (isGingerbread(source.getEntity())) {
            return false;
        }
        return super.hurtServer(level, source, damage);
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageModifier, DamageSource damageSource) {
        return false; // jumping off his tower is part of the fight
    }

    @Override
    public boolean removeWhenFarAway(double distSqr) {
        return false;
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        if (this.tickCount < 5 && (this.fromTower && !RevivalConfig.GINGERBREAD_KING_IN_TOWERS.get()
                || this.fromFortress && !RevivalConfig.GINGERBREAD_KING_IN_FORTRESSES.get())) {
            this.discard();
            return;
        }
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        if (!this.enraged && this.getHealth() < this.getMaxHealth() * 0.5F) {
            this.enrage();
        }
        if (this.slamCooldown > 0) {
            this.slamCooldown--;
        }
        if (this.summonCooldown > 0) {
            this.summonCooldown--;
        }

        LivingEntity target = this.getTarget();
        if (this.slamTicks >= 0) {
            this.slamTicks++;
            if (this.slamTicks == SLAM_WINDUP) {
                this.slam(level);
            }
            if (this.slamTicks >= SLAM_WINDUP + SLAM_STRIKE + SLAM_RECOVER) {
                this.slamTicks = -1;
            }
        } else if (this.summonTicks >= 0) {
            this.summonTicks++;
            if (this.summonTicks == SUMMON_AT) {
                this.summonGuards(level, target);
            }
            if (this.summonTicks >= SUMMON_LENGTH) {
                this.summonTicks = -1;
            }
        } else if (target != null && target.isAlive()) {
            double dist = this.distanceToSqr(target);
            if (this.slamCooldown <= 0 && this.onGround() && dist < 5.0 * 5.0) {
                this.startSlam();
            } else if (this.summonCooldown <= 0 && this.getHealth() < this.getMaxHealth() * 0.8F
                    && this.countGuards(level) < RevivalConfig.GINGERBREAD_KING_MAX_GUARDS.get()) {
                this.startSummon();
            }
        }
    }

    private void enrage() {
        this.enraged = true;
        AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.addOrReplacePermanentModifier(new AttributeModifier(RAGE_SPEED, 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        this.playSound(ModSounds.GINGERBREAD_KING_ROAR.get(), 2.0F, 0.9F);
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY(0.9), this.getZ(), 8, 0.8, 0.4, 0.8, 0.0);
        }
    }

    private void startSlam() {
        this.slamTicks = 0;
        this.slamCooldown = this.enraged ? 80 : 160;
        this.getNavigation().stop();
        this.level().broadcastEntityEvent(this, EVENT_ACTION_A);
        this.playSound(ModSounds.GINGERBREAD_KING_ROAR.get(), 1.5F, 1.1F);
    }

    private void slam(ServerLevel level) {
        Vec3 c = this.position();
        double damage = this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.6;
        List<LivingEntity> hit = level.getEntitiesOfClass(LivingEntity.class, new AABB(c, c).inflate(SLAM_RADIUS, 2.0, SLAM_RADIUS),
                e -> e != this && e.isAlive() && !isGingerbread(e) && !(e instanceof Player p && (p.isCreative() || p.isSpectator())));
        for (LivingEntity e : hit) {
            double dx = e.getX() - c.x;
            double dz = e.getZ() - c.z;
            double d = Math.sqrt(dx * dx + dz * dz);
            if (d > SLAM_RADIUS) {
                continue;
            }
            float falloff = (float) (1.0 - 0.5 * d / SLAM_RADIUS);
            if (e.hurtServer(level, this.damageSources().mobAttack(this), (float) damage * falloff)) {
                double push = 1.1 * falloff * (1.0 - e.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                Vec3 dir = d > 0.01 ? new Vec3(dx / d, 0.0, dz / d) : Vec3.ZERO;
                e.push(dir.x * push, 0.5 * push + 0.2, dir.z * push);
                e.hurtMarked = true;
            }
        }
        BlockState ground = level.getBlockState(BlockPos.containing(c).below());
        if (!ground.isAir()) {
            for (int i = 0; i < 48; i++) {
                double a = i / 48.0 * Math.PI * 2.0;
                double r = 1.0 + this.random.nextDouble() * 3.0;
                level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ground), c.x + Math.cos(a) * r, c.y + 0.1, c.z + Math.sin(a) * r,
                        2, 0.1, 0.1, 0.1, 0.15);
            }
        }
        level.sendParticles(ParticleTypes.EXPLOSION, c.x, c.y + 0.3, c.z, 2, 0.6, 0.1, 0.6, 0.0);
        this.playSound(ModSounds.GINGERBREAD_KING_SLAM.get(), 2.0F, 0.8F + this.random.nextFloat() * 0.2F);
    }

    private int countGuards(ServerLevel level) {
        return level.getEntitiesOfClass(Monster.class, this.getBoundingBox().inflate(16.0),
                e -> e instanceof GingerbreadWarrior || e instanceof GingerbreadSoldier).size();
    }

    private void startSummon() {
        this.summonTicks = 0;
        this.summonCooldown = 400;
        this.getNavigation().stop();
        this.level().broadcastEntityEvent(this, EVENT_ACTION_B);
        this.playSound(ModSounds.GINGERBREAD_KING_SUMMON.get(), 2.0F, 1.0F);
    }

    private void summonGuards(ServerLevel level, @Nullable LivingEntity target) {
        int room = RevivalConfig.GINGERBREAD_KING_MAX_GUARDS.get() - this.countGuards(level);
        int count = Math.min(2, room);
        for (int i = 0; i < count; i++) {
            EntityType<? extends Mob> type = this.random.nextBoolean() ? ModEntities.GINGERBREAD_SOLDIER.get() : ModEntities.GINGERBREAD_WARRIOR.get();
            BlockPos pos = this.findGuardSpot(level, type);
            if (pos == null) {
                continue;
            }
            Mob guard = type.create(level, EntitySpawnReason.MOB_SUMMONED);
            if (guard == null) {
                continue;
            }
            guard.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, this.getYRot(), 0.0F);
            guard.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), EntitySpawnReason.MOB_SUMMONED, null);
            if (target != null) {
                guard.setTarget(target);
            }
            level.addFreshEntity(guard);
            level.sendParticles(ParticleTypes.POOF, guard.getX(), guard.getY(0.5), guard.getZ(), 12, 0.3, 0.5, 0.3, 0.02);
            level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, ModItems.GINGERBREAD.get()),
                    guard.getX(), guard.getY(0.5), guard.getZ(), 10, 0.3, 0.4, 0.3, 0.08);
        }
    }

    private @Nullable BlockPos findGuardSpot(ServerLevel level, EntityType<?> type) {
        BlockPos home = this.blockPosition();
        for (int tries = 0; tries < 16; tries++) {
            double a = this.random.nextDouble() * Math.PI * 2.0;
            double r = 2.0 + this.random.nextDouble() * 2.5;
            int x = Mth.floor(this.getX() + Math.cos(a) * r);
            int z = Mth.floor(this.getZ() + Math.sin(a) * r);
            for (int dy : new int[] {0, 1, -1, 2, -2}) {
                BlockPos p = new BlockPos(x, home.getY() + dy, z);
                if (level.getBlockState(p.below()).isFaceSturdy(level, p.below(), net.minecraft.core.Direction.UP)
                        && level.noCollision(type.getSpawnAABB(p.getX() + 0.5, p.getY(), p.getZ() + 0.5))) {
                    return p;
                }
            }
        }
        return null;
    }

    /** Keeps him in place (no walking, no regular punches) while he winds up a slam or calls his guard. */
    private class BusyGoal extends Goal {
        BusyGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return GingerbreadKing.this.busy();
        }

        @Override
        public void tick() {
            GingerbreadKing.this.getNavigation().stop();
            LivingEntity target = GingerbreadKing.this.getTarget();
            if (target != null) {
                GingerbreadKing.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
        }
    }

    // --- client -----------------------------------------------------------------------------

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EVENT_ACTION_A) {
            this.slamAnimation.start(this.tickCount);
        } else if (id == EVENT_ACTION_B) {
            this.summonAnimation.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public AnimationState actionA() {
        return this.slamAnimation;
    }

    @Override
    public AnimationState actionB() {
        return this.summonAnimation;
    }

    // --- sounds -----------------------------------------------------------------------------

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GINGERBREAD_KING.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.GINGERBREAD_KING.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GINGERBREAD_KING.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PACKED_MUD_STEP, 1.0F, 0.6F);
    }

    @Override
    protected float getSoundVolume() {
        return 1.6F;
    }
}
