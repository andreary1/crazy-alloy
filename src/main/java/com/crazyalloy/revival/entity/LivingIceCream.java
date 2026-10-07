package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModEntities;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Living Ice Cream (stage 6): a wafer cone on two thin legs with a cube of ice cream on top, in chocolate, vanilla,
 * strawberry or mint (one entity type per flavour). Harmless; it wanders the Ice Cream Plains.
 * <p>
 * Using an empty Wafer Cone on it scoops its ice cream: the player gets an ice cream of that flavour, the cone in hand
 * is used up, and the creature is replaced by an Angry Ice Cream Cone that turns on the player.
 */
public class LivingIceCream extends PathfinderMob {
    public LivingIceCream(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.22);
    }

    public static boolean checkSpawnRules(EntityType<? extends Mob> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return SpawnRules.brightGround(level, pos) && SpawnRules.chance(reason, random, RevivalConfig.LIVING_ICE_CREAM_SPAWN_CHANCE::get);
    }

    public IceCreamFlavor flavor() {
        return IceCreamFlavor.of(this.getType());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(ModItems.WAFER_CONE.get())) {
            return super.mobInteract(player, hand);
        }
        if (this.level() instanceof ServerLevel level) {
            scoop(level, player, stack);
        }
        return InteractionResult.SUCCESS;
    }

    /** Gives the player this creature's ice cream for one cone and leaves an Angry Ice Cream Cone in its place. */
    public void scoop(ServerLevel level, Player player, ItemStack cone) {
        var iceCream = this.flavor().iceCream();
        ItemStack scoop = new ItemStack(iceCream);
        cone.consume(1, player);
        if (!player.getInventory().add(scoop)) { // add() empties the stack it takes
            player.drop(scoop, false);
        }
        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, iceCream), this.getX(), this.getY(0.9), this.getZ(), 12, 0.2, 0.15, 0.2, 0.05);
        this.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.3F);
        AngryIceCreamCone angry = ModEntities.ANGRY_ICE_CREAM_CONE.get().create(level, EntitySpawnReason.CONVERSION);
        if (angry != null) {
            angry.snapTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            angry.yBodyRot = this.yBodyRot;
            angry.yHeadRot = this.yHeadRot;
            angry.finalizeSpawn(level, level.getCurrentDifficultyAt(this.blockPosition()), EntitySpawnReason.CONVERSION, null);
            if (this.hasCustomName()) {
                angry.setCustomName(this.getCustomName());
            }
            if (!player.getAbilities().instabuild) {
                angry.setTarget(player);
            }
            level.addFreshEntity(angry);
            angry.playSound(ModSounds.ANGRY_ICE_CREAM_CONE.ambient().get(), 1.0F, 1.0F);
        }
        this.discard();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.LIVING_ICE_CREAM.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.LIVING_ICE_CREAM.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.LIVING_ICE_CREAM.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SNOW_STEP, 0.25F, 1.5F);
    }
}
