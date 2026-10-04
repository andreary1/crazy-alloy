package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

/**
 * Grape Spider: a hostile cluster of grapes on spindly legs. Two burst out of every Infested Purple Jelly Bean Block.
 * <ul>
 *   <li>Climbs walls and leaps at its target. Revival proposal (stage 4, config {@code grapeSpiderPounce}): it first
 *       crouches for half a second, trembling, and then pounces; the crouch is the warning.</li>
 *   <li>Its bite is sour: Poison on Normal/Hard (duration configurable, doubled on Hard).</li>
 *   <li>Drops Grapes (food) and String.</li>
 * </ul>
 */
public class GrapeSpider extends Monster implements AnimatedMob {
    /** Ticks of the crouch before the pounce (matches GrapeSpiderModel.CROUCH_TICKS). */
    public static final int CROUCH_TICKS = 10;
    private static final EntityDataAccessor<Boolean> DATA_CLIMBING = SynchedEntityData.defineId(GrapeSpider.class, EntityDataSerializers.BOOLEAN);
    private final AnimationState pounceAnimation = new AnimationState();

    public GrapeSpider(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.MOVEMENT_SPEED, 0.32)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    public static boolean checkSpawnRules(EntityType<GrapeSpider> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        // Revival note: like the original, Grape Spiders no longer spawn naturally; they come out of
        // Infested Purple Jelly Bean Blocks. This rule only matters if a data pack adds them to a biome.
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CLIMBING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PounceGoal());
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F) {
            @Override
            public boolean canUse() {
                return !RevivalConfig.GRAPE_SPIDER_POUNCE.get() && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.entityData.set(DATA_CLIMBING, this.horizontalCollision);
        }
    }

    @Override
    public boolean onClimbable() {
        return this.entityData.get(DATA_CLIMBING);
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        if (!super.doHurtTarget(level, target)) {
            return false;
        }
        if (target instanceof LivingEntity living) {
            int seconds = RevivalConfig.GRAPE_SPIDER_POISON_SECONDS.get();
            Difficulty difficulty = level.getDifficulty();
            if (difficulty == Difficulty.HARD) {
                seconds *= 2;
            } else if (difficulty != Difficulty.NORMAL) {
                seconds = 0;
            }
            if (seconds > 0) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, seconds * 20, 0), this);
            }
        }
        return true;
    }

    /** Crouch, tremble, then spring at the target. Sends {@link #EVENT_ACTION_A} so every client plays the crouch. */
    private class PounceGoal extends Goal {
        private int ticks;
        private int cooldown;

        PounceGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (!RevivalConfig.GRAPE_SPIDER_POUNCE.get()) {
                return false;
            }
            if (this.cooldown > 0) {
                this.cooldown--;
                return false;
            }
            LivingEntity target = GrapeSpider.this.getTarget();
            if (target == null || !target.isAlive() || !GrapeSpider.this.onGround() || GrapeSpider.this.onClimbable()) {
                return false;
            }
            double d = GrapeSpider.this.distanceToSqr(target);
            return d > 4.0 && d < 36.0 && GrapeSpider.this.getSensing().hasLineOfSight(target);
        }

        @Override
        public boolean canContinueToUse() {
            return this.ticks < CROUCH_TICKS + 12;
        }

        @Override
        public void start() {
            this.ticks = 0;
            GrapeSpider.this.getNavigation().stop();
            GrapeSpider.this.level().broadcastEntityEvent(GrapeSpider.this, EVENT_ACTION_A);
        }

        @Override
        public void tick() {
            this.ticks++;
            LivingEntity target = GrapeSpider.this.getTarget();
            if (target != null) {
                GrapeSpider.this.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
            if (this.ticks < CROUCH_TICKS) {
                GrapeSpider.this.setDeltaMovement(GrapeSpider.this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            } else if (this.ticks == CROUCH_TICKS && target != null) {
                Vec3 d = target.position().subtract(GrapeSpider.this.position());
                double h = Math.max(0.01, Math.sqrt(d.x * d.x + d.z * d.z));
                double v = Math.min(0.9, 0.25 + h * 0.12);
                GrapeSpider.this.setDeltaMovement(d.x / h * v, 0.42 + Math.max(0.0, d.y) * 0.08, d.z / h * v);
                GrapeSpider.this.setYRot((float) (Math.atan2(d.z, d.x) * (180.0 / Math.PI)) - 90.0F);
                GrapeSpider.this.playSound(SoundEvents.SPIDER_AMBIENT, 0.6F, 1.8F);
            } else if (this.ticks > CROUCH_TICKS && target != null && GrapeSpider.this.level() instanceof ServerLevel level
                    && GrapeSpider.this.getBoundingBox().inflate(0.3).intersects(target.getBoundingBox())) {
                GrapeSpider.this.doHurtTarget(level, target);
                this.ticks = CROUCH_TICKS + 12;
            }
        }

        @Override
        public void stop() {
            this.cooldown = 40 + GrapeSpider.this.random.nextInt(30);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EVENT_ACTION_A) {
            this.pounceAnimation.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public AnimationState actionA() {
        return this.pounceAnimation;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GRAPE_SPIDER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.GRAPE_SPIDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GRAPE_SPIDER_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.3F);
    }
}
