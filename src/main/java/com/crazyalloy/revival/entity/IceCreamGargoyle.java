package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModSounds;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Ice Cream Gargoyle (stage 6): a small horned flyer with caramel wafer wings. It drifts around in the air above the
 * plains and, once it has a target, dives straight at it and hurts it on contact, then pulls away and comes again.
 * 30 health. Flies freely (no gravity, no fall damage) but, unlike a Vex, cannot pass through blocks.
 */
public class IceCreamGargoyle extends Monster {
    public IceCreamGargoyle(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.moveControl = new GargoyleMoveControl();
        this.setNoGravity(true);
        this.xpReward = 8;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FLYING_SPEED, 0.6)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public static boolean checkSpawnRules(EntityType<? extends Monster> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random)
                && SpawnRules.chance(reason, random, RevivalConfig.ICE_CREAM_GARGOYLE_SPAWN_CHANCE::get);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new DiveGoal());
        this.goalSelector.addGoal(8, new WanderGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double ya, boolean onGround, BlockState onState, BlockPos pos) {
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ICE_CREAM_GARGOYLE.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ICE_CREAM_GARGOYLE.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ICE_CREAM_GARGOYLE.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    /** Pushes the gargoyle straight towards the wanted point; collisions still apply. */
    private class GargoyleMoveControl extends MoveControl {
        GargoyleMoveControl() {
            super(IceCreamGargoyle.this);
        }

        @Override
        public void tick() {
            IceCreamGargoyle g = IceCreamGargoyle.this;
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                g.setDeltaMovement(g.getDeltaMovement().scale(0.9));
                return;
            }
            Vec3 delta = new Vec3(this.wantedX - g.getX(), this.wantedY - g.getY(), this.wantedZ - g.getZ());
            double length = delta.length();
            if (length < g.getBoundingBox().getSize()) {
                this.operation = MoveControl.Operation.WAIT;
                g.setDeltaMovement(g.getDeltaMovement().scale(0.5));
                return;
            }
            g.setDeltaMovement(g.getDeltaMovement().scale(0.92).add(delta.scale(this.speedModifier * 0.06 / length)));
            if (g.horizontalCollision) {
                g.setDeltaMovement(g.getDeltaMovement().add(0.0, 0.08, 0.0));
            }
            LivingEntity target = g.getTarget();
            double dx = target != null ? target.getX() - g.getX() : g.getDeltaMovement().x;
            double dz = target != null ? target.getZ() - g.getZ() : g.getDeltaMovement().z;
            g.setYRot(-((float) Mth.atan2(dx, dz)) * Mth.RAD_TO_DEG);
            g.yBodyRot = g.getYRot();
        }
    }

    /** Dive at the target and hit it on contact, then climb away a little before the next dive. */
    private class DiveGoal extends Goal {
        private int retreat;

        DiveGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = IceCreamGargoyle.this.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void stop() {
            this.retreat = 0;
        }

        @Override
        public void tick() {
            IceCreamGargoyle g = IceCreamGargoyle.this;
            LivingEntity target = g.getTarget();
            if (target == null) {
                return;
            }
            g.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.retreat > 0) {
                this.retreat--;
                Vec3 away = g.position().subtract(target.position()).multiply(1.0, 0.0, 1.0);
                away = away.lengthSqr() < 0.01 ? new Vec3(1.0, 0.0, 0.0) : away.normalize();
                g.getMoveControl().setWantedPosition(target.getX() + away.x * 5.0, target.getY() + 4.0, target.getZ() + away.z * 5.0, 1.0);
                return;
            }
            Vec3 eye = target.getEyePosition();
            g.getMoveControl().setWantedPosition(eye.x, eye.y - 0.3, eye.z, 1.2);
            if (g.getBoundingBox().inflate(0.2).intersects(target.getBoundingBox()) && g.level() instanceof ServerLevel level) {
                g.doHurtTarget(level, target);
                g.playSound(SoundEvents.PHANTOM_BITE, 0.8F, 1.3F);
                this.retreat = 25;
            }
        }
    }

    /** Drift to a random open spot nearby, staying a few blocks above the ground. */
    private class WanderGoal extends Goal {
        WanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            IceCreamGargoyle g = IceCreamGargoyle.this;
            return g.getTarget() == null && !g.getMoveControl().hasWanted() && g.getRandom().nextInt(reducedTickDelay(10)) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            IceCreamGargoyle g = IceCreamGargoyle.this;
            RandomSource r = g.getRandom();
            for (int i = 0; i < 4; i++) {
                BlockPos p = g.blockPosition().offset(r.nextInt(17) - 8, r.nextInt(7) - 3, r.nextInt(17) - 8);
                int ground = g.level().getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING, p.getX(), p.getZ());
                if (p.getY() < ground + 3) {
                    p = new BlockPos(p.getX(), ground + 3 + r.nextInt(5), p.getZ());
                }
                if (g.level().isEmptyBlock(p)) {
                    g.getMoveControl().setWantedPosition(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5, 0.5);
                    return;
                }
            }
        }
    }
}
