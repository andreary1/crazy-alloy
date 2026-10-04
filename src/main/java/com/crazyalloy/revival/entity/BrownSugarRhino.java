package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModSounds;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Brown Sugar Rhino: a big, slow, neutral Sweet Forest grazer (50 health). It ignores players until hit,
 * then fights back with a heavy, knockback-heavy horn attack (8). Drops Brown Sugar Bricks.
 * <p>
 * Charge (revival proposal, stage 4, config {@code brownSugarRhinoCharge}): when its target is 4 to 16 blocks away
 * it stops, lowers its head and scrapes the ground for 1.5 seconds (the warning, with dust and snorts), then charges
 * in a straight line for up to 2 seconds. A charge that connects deals {@code brownSugarRhinoChargeDamage} and
 * throws the target; a charge into a wall leaves the rhino dazed for a second. Then it rests for 4 to 6 seconds.
 */
public class BrownSugarRhino extends PathfinderMob implements AnimatedMob {
    public static final int SCRAPE_TICKS = 30, CHARGE_TICKS = 40;
    private static final EntityDataAccessor<Boolean> DATA_CHARGING = SynchedEntityData.defineId(BrownSugarRhino.class, EntityDataSerializers.BOOLEAN);

    private final AnimationState scrapeAnimation = new AnimationState();
    private float stance, stanceO;
    private int dazedTicks;

    public BrownSugarRhino(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.22)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.2)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    public static boolean checkSpawnRules(EntityType<BrownSugarRhino> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return SpawnRules.brightGround(level, pos) && SpawnRules.chance(reason, random, RevivalConfig.BROWN_SUGAR_RHINO_SPAWN_CHANCE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CHARGING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ChargeGoal());
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.6, true));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_CHARGING);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.stanceO = this.stance;
            this.stance = Mth.approach(this.stance, this.isCharging() ? 1.0F : 0.0F, 0.2F);
        } else if (this.dazedTicks > 0) {
            this.dazedTicks--;
            this.getNavigation().stop();
            if (this.dazedTicks % 5 == 0 && this.level() instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.CRIT, this.getX(), this.getEyeY() + 0.5, this.getZ(), 3, 0.4, 0.1, 0.4, 0.0);
            }
        }
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() || this.dazedTicks > 0;
    }

    /** Lowers its head, scrapes the ground, then charges straight at where the target was. */
    private class ChargeGoal extends Goal {
        private int ticks;
        private int cooldown;
        private Vec3 direction = Vec3.ZERO;
        private boolean done;

        ChargeGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (!RevivalConfig.BROWN_SUGAR_RHINO_CHARGE.get() || BrownSugarRhino.this.dazedTicks > 0) {
                return false;
            }
            if (this.cooldown > 0) {
                this.cooldown--;
                return false;
            }
            LivingEntity target = BrownSugarRhino.this.getTarget();
            if (target == null || !target.isAlive() || !BrownSugarRhino.this.onGround() || BrownSugarRhino.this.isInWater()) {
                return false;
            }
            double d = BrownSugarRhino.this.distanceToSqr(target);
            return d > 16.0 && d < 256.0 && Math.abs(target.getY() - BrownSugarRhino.this.getY()) < 3.0
                    && BrownSugarRhino.this.getSensing().hasLineOfSight(target);
        }

        @Override
        public boolean canContinueToUse() {
            return !this.done && this.ticks < SCRAPE_TICKS + CHARGE_TICKS && BrownSugarRhino.this.isAlive();
        }

        @Override
        public void start() {
            this.ticks = 0;
            this.done = false;
            BrownSugarRhino.this.getNavigation().stop();
            BrownSugarRhino.this.level().broadcastEntityEvent(BrownSugarRhino.this, EVENT_ACTION_A);
        }

        @Override
        public void tick() {
            this.ticks++;
            BrownSugarRhino rhino = BrownSugarRhino.this;
            if (!(rhino.level() instanceof ServerLevel level)) {
                return;
            }
            LivingEntity target = rhino.getTarget();
            if (this.ticks <= SCRAPE_TICKS) {
                rhino.setDeltaMovement(rhino.getDeltaMovement().multiply(0.0, 1.0, 0.0));
                if (target != null) {
                    rhino.getLookControl().setLookAt(target, 10.0F, 30.0F);
                    rhino.lookAt(target, 10.0F, 30.0F);
                }
                if (this.ticks % 10 == 3) {
                    BlockState ground = level.getBlockState(rhino.blockPosition().below());
                    Vec3 foot = rhino.position().add(Vec3.directionFromRotation(0.0F, rhino.getYRot()).scale(1.2));
                    if (!ground.isAir()) {
                        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ground), foot.x, rhino.getY() + 0.1, foot.z, 12, 0.3, 0.05, 0.3, 0.1);
                    }
                    rhino.playSound(SoundEvents.HOGLIN_ANGRY, 0.7F, 0.6F);
                }
                if (this.ticks == SCRAPE_TICKS) {
                    Vec3 aim = target != null ? target.position().subtract(rhino.position()) : Vec3.directionFromRotation(0.0F, rhino.getYRot());
                    this.direction = new Vec3(aim.x, 0.0, aim.z).normalize();
                    rhino.entityData.set(DATA_CHARGING, true);
                    rhino.playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 1.4F);
                }
                return;
            }
            // Charging: straight ahead, no steering.
            rhino.setYRot((float) (Mth.atan2(this.direction.z, this.direction.x) * Mth.RAD_TO_DEG) - 90.0F);
            rhino.yBodyRot = rhino.getYRot();
            rhino.yHeadRot = rhino.getYRot();
            rhino.setDeltaMovement(this.direction.x * 0.6, rhino.getDeltaMovement().y, this.direction.z * 0.6);
            if (this.ticks % 4 == 0) {
                BlockState ground = level.getBlockState(rhino.blockPosition().below());
                if (!ground.isAir()) {
                    level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ground), rhino.getX(), rhino.getY() + 0.1, rhino.getZ(), 6, 0.5, 0.05, 0.5, 0.1);
                }
            }
            for (LivingEntity hit : level.getEntitiesOfClass(LivingEntity.class, rhino.getBoundingBox().inflate(0.4),
                    e -> e != rhino && e.isAlive() && !(e instanceof BrownSugarRhino) && !(e instanceof Player p && (p.isCreative() || p.isSpectator())))) {
                float damage = (float) (RevivalConfig.BROWN_SUGAR_RHINO_CHARGE_DAMAGE.get() * RevivalConfig.MOB_DAMAGE_MULTIPLIER.get());
                if (hit.hurtServer(level, rhino.damageSources().mobAttack(rhino), damage)) {
                    hit.knockback(2.2, -this.direction.x, -this.direction.z);
                    hit.setDeltaMovement(hit.getDeltaMovement().add(0.0, 0.35, 0.0));
                    hit.hurtMarked = true;
                }
                rhino.playSound(SoundEvents.RAVAGER_ATTACK, 1.0F, 0.8F);
                this.done = true;
            }
            if (!this.done && rhino.horizontalCollision && this.ticks > SCRAPE_TICKS + 2) {
                // Ran into a wall or a tree: dazed for a second.
                rhino.dazedTicks = 20;
                rhino.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 0.8F, 0.6F);
                this.done = true;
            }
        }

        @Override
        public void stop() {
            BrownSugarRhino.this.entityData.set(DATA_CHARGING, false);
            BrownSugarRhino.this.setDeltaMovement(BrownSugarRhino.this.getDeltaMovement().multiply(0.3, 1.0, 0.3));
            this.cooldown = 80 + BrownSugarRhino.this.random.nextInt(40);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EVENT_ACTION_A) {
            this.scrapeAnimation.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public AnimationState actionA() {
        return this.scrapeAnimation;
    }

    @Override
    public float stance(float partialTick) {
        return Mth.lerp(partialTick, this.stanceO, this.stance);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BROWN_SUGAR_RHINO.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.BROWN_SUGAR_RHINO.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BROWN_SUGAR_RHINO.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.RAVAGER_STEP, 0.5F, this.isCharging() ? 1.0F : 0.8F);
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }
}
