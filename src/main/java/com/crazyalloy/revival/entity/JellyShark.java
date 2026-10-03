package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import java.util.EnumSet;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

/**
 * Jelly Shark: a big (50 health) predator of Jelly Bean Fields water. It hunts players who are swimming
 * (3 damage per bite) and cannot leave the water for long. Spawns only where there is water at and above
 * the spawn point, like the original.
 * Revival addition, after the reference art that shows it above the ground: it leaps out of the water at players
 * standing near the shore, and when stranded it flops about (and may flop back into the water) while drying out.
 */
public class JellyShark extends Monster {
    private static final double LUNGE_RANGE = 7.0;

    public JellyShark(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 1.1)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 20.0);
    }

    public static boolean checkSpawnRules(EntityType<JellyShark> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return level.getFluidState(pos).is(FluidTags.WATER) && level.getFluidState(pos.above()).is(FluidTags.WATER)
                && level.getDifficulty() != Difficulty.PEACEFUL
                && SpawnRules.chance(reason, random, RevivalConfig.JELLY_SHARK_SPAWN_CHANCE);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3, true));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new LungeGoal());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
                (target, level) -> target.isInWater() || (this.isInWater() && this.distanceToSqr(target) < LUNGE_RANGE * LUNGE_RANGE)));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void baseTick() {
        int air = this.getAirSupply();
        super.baseTick();
        if (this.level() instanceof ServerLevel level && this.isAlive()) {
            if (this.isInWater()) {
                this.setAirSupply(300);
            } else {
                this.setAirSupply(air - 1);
                if (this.getAirSupply() <= -20) {
                    this.setAirSupply(0);
                    this.hurtServer(level, this.damageSources().dryOut(), 2.0F);
                }
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Stranded: flop around like a fish on land.
        if (!this.level().isClientSide() && !this.isInWater() && this.onGround() && this.random.nextInt(14) == 0) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.25F, 0.4, (this.random.nextFloat() * 2.0F - 1.0F) * 0.25F));
            this.setYRot(this.random.nextFloat() * 360.0F);
            this.playSound(SoundEvents.GUARDIAN_FLOP, 1.0F, 0.8F);
        }
    }

    @Override
    protected void travelInWater(Vec3 input, double baseGravity, boolean isFalling, double oldY) {
        this.moveRelative(this.getSpeed(), input);
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        if (this.getTarget() == null) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
        }
    }

    /** Leaps out of the water at a target on the shore and bites it in passing. */
    private class LungeGoal extends Goal {
        private int cooldown;
        private int ticks;
        private boolean bitten;

        LungeGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.cooldown > 0) {
                this.cooldown--;
                return false;
            }
            LivingEntity target = JellyShark.this.getTarget();
            return target != null && target.isAlive() && JellyShark.this.isInWater() && !target.isInWater()
                    && JellyShark.this.distanceToSqr(target) < LUNGE_RANGE * LUNGE_RANGE
                    && target.getY() < JellyShark.this.getY() + 3.0 && JellyShark.this.getSensing().hasLineOfSight(target);
        }

        @Override
        public boolean canContinueToUse() {
            return this.ticks < 4 || (this.ticks < 40 && !JellyShark.this.isInWater() && !JellyShark.this.onGround());
        }

        @Override
        public void start() {
            LivingEntity target = JellyShark.this.getTarget();
            Vec3 d = target.position().subtract(JellyShark.this.position());
            double h = Math.sqrt(d.x * d.x + d.z * d.z);
            double v = Math.min(1.1, h * 0.17);
            JellyShark.this.setDeltaMovement(d.x / h * v, 0.6 + Math.max(0.0, d.y) * 0.1, d.z / h * v);
            JellyShark.this.lookAt(target, 180.0F, 30.0F);
            JellyShark.this.playSound(SoundEvents.DOLPHIN_JUMP, 1.2F, 0.6F);
            this.ticks = 0;
            this.bitten = false;
            this.cooldown = 60;
        }

        @Override
        public void tick() {
            this.ticks++;
            LivingEntity target = JellyShark.this.getTarget();
            if (!this.bitten && target != null && JellyShark.this.getBoundingBox().inflate(0.4).intersects(target.getBoundingBox())
                    && JellyShark.this.level() instanceof ServerLevel level) {
                this.bitten = true;
                JellyShark.this.swing(InteractionHand.MAIN_HAND);
                JellyShark.this.doHurtTarget(level, target);
            }
        }
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.JELLY_SHARK.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.JELLY_SHARK.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.JELLY_SHARK.death().get();
    }
}
