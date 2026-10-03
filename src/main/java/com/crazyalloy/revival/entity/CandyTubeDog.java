package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.entity.ai.SeekSweetsGoal;
import com.crazyalloy.revival.registry.ModEntities;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModSounds;
import com.crazyalloy.revival.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.event.EventHooks;
import org.jspecify.annotations.Nullable;

/**
 * Candy Tube Dog: a passive, tameable sausage-shaped dog from the Sweet Forest.
 * <ul>
 *   <li>Tame with a Lollipop (1 in 3 chance). Tamed dogs follow, sit on command and defend their owner.</li>
 *   <li>Heal and breed with sweets (tag {@code candy_tube_dog_food}); also walks to sweets dropped on the ground.</li>
 *   <li>Revival proposal: tamed adults periodically shed a Candy Tube, a renewable sugar source.</li>
 * </ul>
 */
public class CandyTubeDog extends TamableAnimal {
    private static final double WILD_HEALTH = 20.0; // original: 20
    private static final double TAME_HEALTH = 30.0;
    private int shedTimer;

    public CandyTubeDog(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
        this.shedTimer = nextShedDelay(this.random);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, WILD_HEALTH)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 3.0);
    }

    public static boolean checkSpawnRules(EntityType<CandyTubeDog> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Animal.checkAnimalSpawnRules(type, level, reason, pos, random)
                && (EntitySpawnReason.isSpawner(reason) || random.nextDouble() < RevivalConfig.CANDY_TUBE_DOG_SPAWN_CHANCE.get());
    }

    private static int nextShedDelay(RandomSource random) {
        return 6000 + random.nextInt(6000);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.1, stack -> stack.is(ModItems.LOLLIPOP.get()) || this.isFood(stack), false));
        this.goalSelector.addGoal(7, new SeekSweetsGoal(this, 1.1, 8.0, this::eatFromGround));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
    }

    private void eatFromGround(ItemStack stack) {
        this.heal(4.0F);
        this.playSound(SoundEvents.GENERIC_EAT.value(), 0.8F, 1.3F);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.HEART, this.getX(), this.getY() + 0.9, this.getZ(), 1, 0.2, 0.1, 0.2, 0.0);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!this.isTame()) {
            if (stack.is(ModItems.LOLLIPOP.get())) {
                if (!this.level().isClientSide()) {
                    stack.consume(1, player);
                    if (this.random.nextInt(3) == 0 && !EventHooks.onAnimalTame(this, player)) {
                        this.tame(player);
                        this.navigation.stop();
                        this.setTarget(null);
                        this.setOrderedToSit(true);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    } else {
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                }
                return InteractionResult.SUCCESS;
            }
            return super.mobInteract(player, hand);
        }

        if (this.isFood(stack) && this.getHealth() < this.getMaxHealth()) {
            if (!this.level().isClientSide()) {
                stack.consume(1, player);
                this.heal(6.0F);
                this.playSound(SoundEvents.GENERIC_EAT.value(), 0.8F, 1.3F);
            }
            return InteractionResult.SUCCESS;
        }

        InteractionResult result = super.mobInteract(player, hand);
        if (!result.consumesAction() && this.isOwnedBy(player)) {
            // Empty hand (or any non-food item): toggle sitting.
            if (!this.level().isClientSide()) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
            }
            return InteractionResult.SUCCESS;
        }
        return result;
    }

    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(TAME_HEALTH);
            this.setHealth((float) TAME_HEALTH);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(WILD_HEALTH);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide() && this.isAlive() && this.isTame() && !this.isBaby() && --this.shedTimer <= 0) {
            this.spawnAtLocation((ServerLevel) this.level(), new ItemStack(ModItems.CANDY_TUBE.get()));
            this.playSound(ModSounds.CANDY_TUBE_DOG_SHED.get(), 1.0F, 1.0F);
            this.shedTimer = nextShedDelay(this.random);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.Items.CANDY_TUBE_DOG_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        CandyTubeDog puppy = ModEntities.CANDY_TUBE_DOG.get().create(level, EntitySpawnReason.BREEDING);
        if (puppy != null && this.isTame() && this.getOwner() instanceof Player owner) {
            puppy.tame(owner);
        }
        return puppy;
    }

    @Override
    public boolean canMate(Animal other) {
        return other != this && other instanceof CandyTubeDog dog && this.isTame() && dog.isTame()
                && !this.isInSittingPose() && !dog.isInSittingPose() && this.isInLove() && dog.isInLove();
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof TamableAnimal pet && pet.isTame() && pet.getOwner() == owner) {
            return false;
        }
        return !(target instanceof Player victim && owner instanceof Player ownerPlayer && !ownerPlayer.canHarmPlayer(victim));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("shed_timer", shedTimer);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.shedTimer = input.getIntOr("shed_timer", nextShedDelay(this.random));
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.CANDY_TUBE_DOG_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.CANDY_TUBE_DOG_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CANDY_TUBE_DOG_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP.value(), 0.12F, 1.4F);
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 1.1F;
    }
}
