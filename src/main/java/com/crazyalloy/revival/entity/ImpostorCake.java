package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModSounds;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
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
import org.jspecify.annotations.Nullable;

/**
 * Impostor Cake: a three-tier party cake whose bottom tier is really a mouth.
 * <ul>
 *   <li>Revival proposal (config {@code impostorCakeDisguise}): it sits perfectly still with its mouth shut, like an
 *       ordinary cake, until a player comes within {@code impostorCakeRevealDistance} blocks or hits it.</li>
 *   <li>Then it reveals itself: for one second the jaw gapes, the tongue unrolls and the tiers wobble (the warning),
 *       and only after that does it hop after its target and bite.</li>
 *   <li>With nobody to chase for ten seconds it settles down, closes its mouth and waits again.</li>
 * </ul>
 */
public class ImpostorCake extends Monster implements AnimatedMob {
    /** Ticks of the reveal (matches ImpostorCakeModel.REVEAL_TICKS). */
    public static final int REVEAL_TICKS = 20;
    private static final int CALM_DOWN_TICKS = 200;
    private static final EntityDataAccessor<Boolean> DATA_DISGUISED = SynchedEntityData.defineId(ImpostorCake.class, EntityDataSerializers.BOOLEAN);

    private final AnimationState revealAnimation = new AnimationState();
    /** Server: ticks left in the reveal, during which the cake does not move. */
    private int revealTicks;
    /** Server: ticks spent revealed without a target. */
    private int calmTicks;
    /** Client: how far the mouth is open, 0 to 1. */
    private float open, openO;

    public ImpostorCake(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 6;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 22.0)
                .add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
                .add(Attributes.FOLLOW_RANGE, 20.0);
    }

    public static boolean checkSpawnRules(EntityType<ImpostorCake> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random) && SpawnRules.chance(reason, random, RevivalConfig.IMPOSTOR_CAKE_SPAWN_CHANCE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_DISGUISED, true);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData data) {
        // Cakes sit square to the world, like a placed block.
        float yaw = this.random.nextInt(4) * 90.0F;
        this.setYRot(yaw);
        this.setYHeadRot(yaw);
        this.setYBodyRot(yaw);
        this.setDisguised(RevivalConfig.IMPOSTOR_CAKE_DISGUISE.get());
        return super.finalizeSpawn(level, difficulty, reason, data);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new HoldStillGoal());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.15, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return !ImpostorCake.this.isDisguised() && super.canUse();
            }
        });
    }

    public boolean isDisguised() {
        return this.entityData.get(DATA_DISGUISED);
    }

    public void setDisguised(boolean disguised) {
        this.entityData.set(DATA_DISGUISED, disguised);
    }

    /** Server: opens the mouth and turns on {@code target} (may be null). */
    public void reveal(@Nullable LivingEntity target) {
        if (!this.isDisguised()) {
            return;
        }
        this.setDisguised(false);
        this.revealTicks = REVEAL_TICKS;
        this.calmTicks = 0;
        if (target != null && !(target instanceof Player p && (p.isCreative() || p.isSpectator()))) {
            this.setTarget(target);
        }
        this.level().broadcastEntityEvent(this, EVENT_ACTION_A);
        this.playSound(ModSounds.IMPOSTOR_CAKE_REVEAL.get(), 1.0F, 0.9F + this.random.nextFloat() * 0.2F);
    }

    public boolean isRevealing() {
        return this.revealTicks > 0;
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        if (this.isDisguised()) {
            double range = RevivalConfig.IMPOSTOR_CAKE_REVEAL_DISTANCE.get();
            Player player = level.getNearestPlayer(this.getX(), this.getY(), this.getZ(), range,
                    p -> p instanceof Player pl && !pl.isCreative() && !pl.isSpectator() && pl.isAlive());
            if (player != null && this.getSensing().hasLineOfSight(player)) {
                this.reveal(player);
            }
            return;
        }
        if (this.revealTicks > 0) {
            this.revealTicks--;
        }
        if (this.getTarget() == null && RevivalConfig.IMPOSTOR_CAKE_DISGUISE.get()) {
            if (++this.calmTicks >= CALM_DOWN_TICKS && this.onGround()) {
                // Settle down square to the world and close the mouth.
                float yaw = Math.round(this.getYRot() / 90.0F) * 90.0F;
                this.setYRot(yaw);
                this.setYBodyRot(yaw);
                this.setYHeadRot(yaw);
                this.getNavigation().stop();
                this.setDisguised(true);
                this.calmTicks = 0;
            }
        } else {
            this.calmTicks = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.openO = this.open;
            this.open = Mth.approach(this.open, this.isDisguised() ? 0.0F : 1.0F, 0.12F);
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        boolean hurt = super.hurtServer(level, source, damage);
        if (hurt && this.isAlive() && this.isDisguised()) {
            this.reveal(source.getEntity() instanceof LivingEntity attacker ? attacker : null);
        }
        return hurt;
    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity target) {
        boolean hit = super.doHurtTarget(level, target);
        if (hit) {
            this.playSound(ModSounds.IMPOSTOR_CAKE_CHOMP.get(), 1.0F, 0.9F + this.random.nextFloat() * 0.2F);
        }
        return hit;
    }

    @Override
    public boolean isPushable() {
        return !this.isDisguised() && super.isPushable();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isDisguised() ? null : ModSounds.IMPOSTOR_CAKE.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.IMPOSTOR_CAKE.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.IMPOSTOR_CAKE.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOOL_STEP, 0.3F, 0.7F);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EVENT_ACTION_A) {
            this.revealAnimation.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public AnimationState actionA() {
        return this.revealAnimation;
    }

    /** How far the mouth is open, 0 (a harmless-looking cake) to 1. */
    @Override
    public float stance(float partialTick) {
        return Mth.lerp(partialTick, this.openO, this.open);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("disguised", this.isDisguised());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setDisguised(input.getBooleanOr("disguised", true));
        if (this.isDisguised()) {
            this.open = this.openO = 0.0F;
        }
    }

    /** While disguised or revealing, the cake does not move or turn: it holds every other goal back. */
    private class HoldStillGoal extends Goal {
        HoldStillGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return ImpostorCake.this.isDisguised() || ImpostorCake.this.isRevealing();
        }

        @Override
        public void start() {
            ImpostorCake.this.getNavigation().stop();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            ImpostorCake cake = ImpostorCake.this;
            cake.setDeltaMovement(cake.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            LivingEntity target = cake.getTarget();
            if (!cake.isDisguised() && target != null) {
                cake.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
        }
    }
}
