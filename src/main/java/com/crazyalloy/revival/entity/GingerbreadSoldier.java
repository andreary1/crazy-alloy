package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.entity.projectile.GumdropShot;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Gingerbread Soldier: the ranged guard of Gingerbread Towers. 10 health; keeps its distance and fires
 * Gumdrops (3 damage each) from a long rifle. Revival choice: the original's "bullet" is a gumdrop here. Drops
 * Gingerbread and sometimes Gumdrops.
 * Stage 3: the rifle is held at the ready and raised to aim while the soldier has a target; each shot plays a
 * recoil and reload animation (sent to every client watching it) with smoke at the muzzle.
 */
public class GingerbreadSoldier extends Monster implements RangedAttackMob, AnimatedMob {
    private final AnimationState shootAnimation = new AnimationState();
    private float aim, aimO;

    public GingerbreadSoldier(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.0, 25, 35, 14.0F));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, GingerbreadWarrior.class, GingerbreadSoldier.class, GingerbreadKing.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        // The aggressive flag is synced to clients and drives the aiming pose.
        LivingEntity target = this.getTarget();
        this.setAggressive(target != null && target.isAlive() && this.distanceToSqr(target) < 20.0 * 20.0);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.aimO = this.aim;
            this.aim = Mth.approach(this.aim, this.isAggressive() ? 1.0F : 0.0F, 0.15F);
        }
    }

    @Override
    public AnimationState actionA() {
        return this.shootAnimation;
    }

    @Override
    public float stance(float partialTick) {
        return Mth.lerp(partialTick, this.aimO, this.aim);
    }

    /** Where the rifle's muzzle is when aiming: in front of the right shoulder, at eye height. */
    private Vec3 muzzle() {
        float yaw = this.yBodyRot * Mth.DEG_TO_RAD;
        Vec3 forward = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
        Vec3 right = new Vec3(-Mth.cos(yaw), 0.0, -Mth.sin(yaw));
        return this.position().add(0.0, this.getEyeHeight() - 0.1, 0.0).add(forward.scale(1.0)).add(right.scale(0.2));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        GumdropShot shot = new GumdropShot(this.level(), this, new ItemStack(ModItems.GUMDROP.get()));
        Vec3 from = this.muzzle();
        shot.setPos(from.x, from.y, from.z);
        double dx = target.getX() - from.x;
        double dy = target.getY(0.33) - from.y;
        double dz = target.getZ() - from.z;
        double dist = Math.sqrt(dx * dx + dz * dz);
        shot.shoot(dx, dy + dist * 0.18, dz, 1.4F, 6.0F);
        this.playSound(ModSounds.GINGERBREAD_SOLDIER_SHOOT.get(), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.3F);
        this.level().addFreshEntity(shot);
        this.level().broadcastEntityEvent(this, EVENT_ACTION_A);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EVENT_ACTION_A) {
            this.shootAnimation.start(this.tickCount);
            Vec3 m = this.muzzle();
            for (int i = 0; i < 4; i++) {
                this.level().addParticle(ParticleTypes.SMOKE, m.x, m.y, m.z, this.random.nextGaussian() * 0.02, 0.03, this.random.nextGaussian() * 0.02);
            }
            this.level().addParticle(ParticleTypes.SMALL_FLAME, m.x, m.y, m.z, 0.0, 0.0, 0.0);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GINGERBREAD.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.GINGERBREAD.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GINGERBREAD.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PACKED_MUD_STEP, 0.3F, 1.3F);
    }
}
