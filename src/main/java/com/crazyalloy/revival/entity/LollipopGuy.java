package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Lollipop Guy: a neutral walking lollipop.
 * <ul>
 *   <li>Ignores players until hurt; then it and nearby Lollipop Guys fight back.</li>
 *   <li>Revival proposal (simple trade): hand it Sugar and, after a moment, it tosses you a Lollipop.
 *       Each Lollipop Guy then needs about a minute before the next trade.</li>
 * </ul>
 */
public class LollipopGuy extends PathfinderMob {
    private static final int GIFT_DELAY = 30;
    private static final int TRADE_COOLDOWN = 1200;

    private int giftTimer;
    private int tradeCooldown;
    private @Nullable Player giftTarget;

    public LollipopGuy(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0) // original: 20
                .add(Attributes.MOVEMENT_SPEED, 0.27)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.TEMPT_RANGE, 10.0);
    }

    public static boolean checkSpawnRules(EntityType<LollipopGuy> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return level.getRawBrightness(pos, 0) > 8
                && level.getBlockState(pos.below()).isSolid()
                && (EntitySpawnReason.isSpawner(reason) || random.nextDouble() < RevivalConfig.LOLLIPOP_GUY_SPAWN_CHANCE.get());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15, false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 0.9, stack -> stack.is(Items.SUGAR), false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(Items.SUGAR) || this.getTarget() != null) {
            return super.mobInteract(player, hand);
        }
        if (this.tradeCooldown > 0 || this.giftTimer > 0) {
            if (!this.level().isClientSide()) {
                this.playSound(ModSounds.LOLLIPOP_GUY_AMBIENT.get(), 0.8F, 0.6F);
            }
            return InteractionResult.CONSUME;
        }
        if (!this.level().isClientSide()) {
            stack.consume(1, player);
            this.giftTimer = GIFT_DELAY;
            this.giftTarget = player;
            this.playSound(SoundEvents.GENERIC_EAT.value(), 0.8F, 1.4F);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            return;
        }
        if (this.tradeCooldown > 0) {
            this.tradeCooldown--;
        }
        if (this.giftTimer > 0 && --this.giftTimer == 0) {
            giveLollipop();
        }
    }

    private void giveLollipop() {
        ServerLevel level = (ServerLevel) this.level();
        ItemEntity item = new ItemEntity(level, this.getX(), this.getEyeY() - 0.3, this.getZ(), new ItemStack(ModItems.LOLLIPOP.get()));
        if (this.giftTarget != null && this.giftTarget.isAlive()) {
            Vec3 toward = this.giftTarget.position().subtract(this.position()).normalize().scale(0.25);
            item.setDeltaMovement(toward.x, 0.25, toward.z);
            this.getLookControl().setLookAt(this.giftTarget);
        }
        item.setDefaultPickUpDelay();
        level.addFreshEntity(item);
        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1.6, this.getZ(), 5, 0.3, 0.3, 0.3, 0.0);
        this.playSound(ModSounds.LOLLIPOP_GUY_GIFT.get(), 1.0F, 1.0F);
        this.tradeCooldown = TRADE_COOLDOWN;
        this.giftTarget = null;
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("trade_cooldown", tradeCooldown);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.tradeCooldown = input.getIntOr("trade_cooldown", 0);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.LOLLIPOP_GUY_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.LOLLIPOP_GUY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.LOLLIPOP_GUY_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.AMETHYST_BLOCK_STEP, 0.3F, 1.6F);
    }
}
