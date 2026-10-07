package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModEntities;
import com.crazyalloy.revival.registry.ModSounds;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Ice Cream Dragon (stage 6): the boss of the Ice Cream Dimension, hatched from the egg on top of the Ice Cream Nest.
 * A heavy, wingless dragon on thick legs, with a long curved neck, a vanilla head and neck, a chocolate torso, pink
 * legs and mint crests. 300 health ({@link RevivalConfig#ICE_CREAM_DRAGON_HEALTH}).
 * <ul>
 * <li>Chases and bites in melee.</li>
 * <li>Fireball volley: rears its head and spits three large fireballs, one after another.</li>
 * <li>Sprint: when its target gets away it roars and gains Speed for a few seconds.</li>
 * <li>Regeneration: the first time it drops below two thirds and below one third of its health.</li>
 * <li>Calls Angry Ice Cream Cones once it is below 80 %, up to a configurable number nearby.</li>
 * </ul>
 * Shows a boss bar to every player tracking it. Drops 10 to 15 ice creams of each flavour and 5 to 10 Ultimate Ice
 * Creams (loot table), and the player who kills it gets the Dragon Killer advancement.
 */
public class IceCreamDragon extends Monster implements AnimatedMob {
    public static final int VOLLEY_LENGTH = 40;
    private static final int[] VOLLEY_SHOTS = {12, 22, 32};
    public static final int ROAR_LENGTH = 30, ROAR_AT = 14;

    private final ServerBossEvent bossEvent = new ServerBossEvent(Mth.createInsecureUUID(this.random), this.getDisplayName(),
            BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.NOTCHED_10);
    private final AnimationState volleyAnimation = new AnimationState();
    private final AnimationState roarAnimation = new AnimationState();
    private int volleyTicks = -1;
    private int roarTicks = -1;
    private int volleyCooldown = 60;
    private int sprintCooldown = 100;
    private int summonCooldown = 200;
    /** Health thresholds already used for a regeneration burst (bit 0: two thirds, bit 1: one third). */
    private int regenUsed;
    private boolean summonOnRoar;

    public IceCreamDragon(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 200;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 12.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5)
                .add(Attributes.ARMOR, 8.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.STEP_HEIGHT, 1.5);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BusyGoal());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6, 0.0F));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AngryIceCreamCone.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            health.setBaseValue(RevivalConfig.ICE_CREAM_DRAGON_HEALTH.get());
        }
        groupData = super.finalizeSpawn(level, difficulty, reason, groupData);
        this.setHealth(this.getMaxHealth());
        this.setPersistenceRequired();
        return groupData;
    }

    // --- boss bar ---------------------------------------------------------------------------

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
        output.putInt("RegenUsed", this.regenUsed);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.regenUsed = input.getIntOr("RegenUsed", 0);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    // --- combat -----------------------------------------------------------------------------

    public boolean isBusy() {
        return this.volleyTicks >= 0 || this.roarTicks >= 0;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        Entity attacker = source.getEntity();
        if (attacker == this || attacker instanceof AngryIceCreamCone) {
            return false; // its own fireballs' blasts and its little helpers never hurt it
        }
        return super.hurtServer(level, source, damage);
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distSqr) {
        return false;
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        if (this.volleyCooldown > 0) this.volleyCooldown--;
        if (this.sprintCooldown > 0) this.sprintCooldown--;
        if (this.summonCooldown > 0) this.summonCooldown--;

        float fraction = this.getHealth() / this.getMaxHealth();
        if ((this.regenUsed & 1) == 0 && fraction < 2.0F / 3.0F) {
            this.regenUsed |= 1;
            this.regenerate(level);
        } else if ((this.regenUsed & 2) == 0 && fraction < 1.0F / 3.0F) {
            this.regenUsed |= 2;
            this.regenerate(level);
        }

        LivingEntity target = this.getTarget();
        if (this.volleyTicks >= 0) {
            this.volleyTicks++;
            for (int shot : VOLLEY_SHOTS) {
                if (this.volleyTicks == shot && target != null && target.isAlive()) {
                    this.shootFireball(level, target);
                }
            }
            if (this.volleyTicks >= VOLLEY_LENGTH) {
                this.volleyTicks = -1;
            }
        } else if (this.roarTicks >= 0) {
            this.roarTicks++;
            if (this.roarTicks == ROAR_AT && this.summonOnRoar) {
                this.summonCones(level, target);
            }
            if (this.roarTicks >= ROAR_LENGTH) {
                this.roarTicks = -1;
                this.summonOnRoar = false;
            }
        } else if (target != null && target.isAlive()) {
            double dist = this.distanceToSqr(target);
            if (this.summonCooldown <= 0 && fraction < 0.8F && this.countCones(level) < RevivalConfig.ICE_CREAM_DRAGON_MAX_CONES.get()) {
                this.summonCooldown = 400;
                this.summonOnRoar = true;
                this.startRoar();
            } else if (this.volleyCooldown <= 0 && dist < 32.0 * 32.0 && this.hasLineOfSight(target)) {
                // At range it always spits fire; in a close fight only one time in three, otherwise it keeps biting.
                if (dist > 5.0 * 5.0 || this.random.nextInt(3) == 0) {
                    this.startVolley();
                } else {
                    this.volleyCooldown = 40;
                }
            } else if (this.sprintCooldown <= 0 && dist > 10.0 * 10.0) {
                this.sprintCooldown = 300;
                this.addEffect(new MobEffectInstance(MobEffects.SPEED, 100, 1));
                this.playSound(ModSounds.ICE_CREAM_DRAGON_ROAR.get(), 2.0F, 1.3F);
                level.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY() + 0.3, this.getZ(), 16, 1.0, 0.2, 1.0, 0.05);
            }
        }
    }

    private void startVolley() {
        this.volleyTicks = 0;
        this.volleyCooldown = this.getHealth() < this.getMaxHealth() * 0.5F ? 80 : 120;
        this.getNavigation().stop();
        this.level().broadcastEntityEvent(this, EVENT_ACTION_A);
    }

    private void startRoar() {
        this.roarTicks = 0;
        this.getNavigation().stop();
        this.level().broadcastEntityEvent(this, EVENT_ACTION_B);
        this.playSound(ModSounds.ICE_CREAM_DRAGON_ROAR.get(), 3.0F, 0.9F);
    }

    /** Where the fireballs come out: in front of the head, at the end of the neck. */
    public Vec3 mouthPosition() {
        Vec3 look = Vec3.directionFromRotation(0.0F, this.yBodyRot);
        return this.position().add(look.x * 2.6, 2.7, look.z * 2.6);
    }

    private void shootFireball(ServerLevel level, LivingEntity target) {
        Vec3 mouth = this.mouthPosition();
        Vec3 dir = new Vec3(target.getX() - mouth.x, target.getY(0.5) - mouth.y, target.getZ() - mouth.z).normalize()
                .add(this.random.triangle(0.0, 0.06), this.random.triangle(0.0, 0.03), this.random.triangle(0.0, 0.06));
        LargeFireball fireball = new LargeFireball(level, this, dir.normalize(), RevivalConfig.ICE_CREAM_DRAGON_FIREBALL_POWER.get());
        fireball.setPos(mouth.x, mouth.y, mouth.z);
        level.addFreshEntity(fireball);
        if (!this.isSilent()) {
            level.levelEvent(null, 1016, this.blockPosition(), 0); // the ghast "shoot" sound
        }
    }

    private void regenerate(ServerLevel level) {
        this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 2));
        this.startRoar();
        level.sendParticles(ParticleTypes.HEART, this.getX(), this.getY(0.8), this.getZ(), 10, 1.2, 0.6, 1.2, 0.0);
    }

    private int countCones(ServerLevel level) {
        return level.getEntitiesOfClass(AngryIceCreamCone.class, this.getBoundingBox().inflate(16.0)).size();
    }

    private void summonCones(ServerLevel level, @Nullable LivingEntity target) {
        int room = RevivalConfig.ICE_CREAM_DRAGON_MAX_CONES.get() - this.countCones(level);
        for (int i = 0; i < Math.min(2, room); i++) {
            AngryIceCreamCone cone = ModEntities.ANGRY_ICE_CREAM_CONE.get().create(level, EntitySpawnReason.MOB_SUMMONED);
            BlockPos pos = this.findSpot(level);
            if (cone == null || pos == null) {
                continue;
            }
            cone.snapTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, this.getYRot(), 0.0F);
            cone.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), EntitySpawnReason.MOB_SUMMONED, null);
            if (target != null) {
                cone.setTarget(target);
            }
            level.addFreshEntity(cone);
            level.sendParticles(new DustParticleOptions(0xE9B872, 1.2F), cone.getX(), cone.getY(0.5), cone.getZ(), 16, 0.3, 0.5, 0.3, 0.0);
            level.sendParticles(ParticleTypes.POOF, cone.getX(), cone.getY(0.5), cone.getZ(), 8, 0.3, 0.4, 0.3, 0.02);
        }
    }

    private @Nullable BlockPos findSpot(ServerLevel level) {
        EntityType<?> type = ModEntities.ANGRY_ICE_CREAM_CONE.get();
        for (int tries = 0; tries < 16; tries++) {
            double a = this.random.nextDouble() * Math.PI * 2.0;
            double r = 2.5 + this.random.nextDouble() * 2.5;
            int x = Mth.floor(this.getX() + Math.cos(a) * r);
            int z = Mth.floor(this.getZ() + Math.sin(a) * r);
            for (int dy : new int[] {0, 1, -1, 2, -2}) {
                BlockPos p = new BlockPos(x, this.getBlockY() + dy, z);
                if (level.getBlockState(p.below()).isFaceSturdy(level, p.below(), Direction.UP)
                        && level.noCollision(type.getSpawnAABB(p.getX() + 0.5, p.getY(), p.getZ() + 0.5))) {
                    return p;
                }
            }
        }
        return null;
    }

    /** Holds still (no walking, no bites) while it spits fire or roars. */
    private class BusyGoal extends Goal {
        BusyGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return IceCreamDragon.this.isBusy();
        }

        @Override
        public void tick() {
            IceCreamDragon.this.getNavigation().stop();
            LivingEntity target = IceCreamDragon.this.getTarget();
            if (target != null) {
                IceCreamDragon.this.getLookControl().setLookAt(target, 20.0F, 30.0F);
                // Turn the whole body towards the target so the fireballs leave the mouth in its direction.
                double dx = target.getX() - IceCreamDragon.this.getX();
                double dz = target.getZ() - IceCreamDragon.this.getZ();
                float yaw = (float) (Mth.atan2(dz, dx) * Mth.RAD_TO_DEG) - 90.0F;
                IceCreamDragon.this.setYRot(Mth.approachDegrees(IceCreamDragon.this.getYRot(), yaw, 10.0F));
                IceCreamDragon.this.yBodyRot = IceCreamDragon.this.getYRot();
            }
        }
    }

    // --- client -----------------------------------------------------------------------------

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EVENT_ACTION_A) {
            this.volleyAnimation.start(this.tickCount);
        } else if (id == EVENT_ACTION_B) {
            this.roarAnimation.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    /** Fireball volley. */
    @Override
    public AnimationState actionA() {
        return this.volleyAnimation;
    }

    /** Roar (regeneration, calling the cones). */
    @Override
    public AnimationState actionB() {
        return this.roarAnimation;
    }

    // --- sounds -----------------------------------------------------------------------------

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ICE_CREAM_DRAGON.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ICE_CREAM_DRAGON.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ICE_CREAM_DRAGON.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.RAVAGER_STEP, 0.8F, 0.9F);
    }

    @Override
    protected float getSoundVolume() {
        return 2.0F;
    }
}
