package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModTags;
import net.minecraft.core.BlockPos;
import java.util.Optional;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.InteractGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

/**
 * Ice Cream Vendor (revival proposal, stage 4): the person behind the counter of an Ice Cream Truck. Uses the vanilla
 * trading screen with a fixed menu: ice creams and wafer cones for emeralds, and it buys sugar, sweet berries, milk
 * and jelly beans. Every trade restocks once per in-game day. It stays in its truck (home radius 2), never despawns
 * and does not breed. Holds up a cone while someone is trading (synced, so every player sees it).
 */
public class IceCreamVendor extends AbstractVillager implements AnimatedMob {
    private static final EntityDataAccessor<Boolean> DATA_SERVING = SynchedEntityData.defineId(IceCreamVendor.class, EntityDataSerializers.BOOLEAN);
    private static final int RESTOCK_INTERVAL = 24000;

    private final AnimationState unused = new AnimationState();
    private float serve, serveO;
    private long lastRestock;

    public IceCreamVendor(EntityType<? extends AbstractVillager> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.5);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SERVING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.5));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.4));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        this.setPersistenceRequired();
        this.setHomeTo(this.blockPosition(), 2);
        return super.finalizeSpawn(level, difficulty, reason, groupData);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(ModItems.ICE_CREAM_VENDOR_SPAWN_EGG.get()) && this.isAlive() && !this.isTrading()) {
            if (hand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }
            if (!this.level().isClientSide() && !this.getOffers().isEmpty()) {
                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void updateTrades(ServerLevel level) {
        MerchantOffers offers = this.getOffers();
        offers.add(sell(ModItems.VANILLA_ICE_CREAM.get(), 2, 1));
        offers.add(sell(ModItems.STRAWBERRY_ICE_CREAM.get(), 2, 1));
        offers.add(sell(ModItems.CHOCOLATE_ICE_CREAM.get(), 2, 1));
        offers.add(sell(ModItems.WAFER_CONE.get(), 4, 1));
        offers.add(buy(Items.SUGAR, 12));
        offers.add(buy(Items.SWEET_BERRIES, 10));
        offers.add(buy(ModItems.JELLY_BEANS.get(), 6));
        offers.add(new MerchantOffer(new ItemCost(Items.MILK_BUCKET), Optional.empty(), new ItemStack(Items.EMERALD, 2), 6, 2, 0.05F));
    }

    private static MerchantOffer sell(ItemLike item, int count, int emeralds) {
        return new MerchantOffer(new ItemCost(Items.EMERALD, emeralds), new ItemStack(item, count), 12, 1, 0.05F);
    }

    private static MerchantOffer buy(ItemLike item, int count) {
        return new MerchantOffer(new ItemCost(item, count), new ItemStack(Items.EMERALD), 16, 2, 0.05F);
    }

    @Override
    protected void rewardTradeXp(MerchantOffer offer) {
        if (offer.shouldRewardExp()) {
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), 2 + this.random.nextInt(3)));
        }
        // Stage 5 (revival proposal): selling an ice cream pulls the lever of the Ice Cream Machine behind the counter.
        ItemStack sold = offer.getResult();
        if (sold.is(ModTags.Items.ICE_CREAMS)) {
            BlockPos.betweenClosedStream(this.blockPosition().offset(-3, -1, -3), this.blockPosition().offset(3, 2, 3))
                    .map(pos -> this.level().getBlockEntity(pos))
                    .filter(be -> be instanceof IceCreamMachineBlockEntity)
                    .findFirst()
                    .ifPresent(be -> ((IceCreamMachineBlockEntity) be).playServeAnimation(sold.getItem(), 30));
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            this.entityData.set(DATA_SERVING, this.isTrading());
            long time = this.level().getGameTime();
            if (time - this.lastRestock >= RESTOCK_INTERVAL && !this.isTrading()) {
                this.lastRestock = time;
                this.getOffers().forEach(MerchantOffer::resetUses);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.serveO = this.serve;
            this.serve = Mth.approach(this.serve, this.entityData.get(DATA_SERVING) ? 1.0F : 0.0F, 0.15F);
        }
    }

    @Override
    public AnimationState actionA() {
        return this.unused;
    }

    /** 0 to 1: how far the cone is held up for the customer. */
    @Override
    public float stance(float partialTick) {
        return Mth.lerp(partialTick, this.serveO, this.serve);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distSqr) {
        return false;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putLong("LastRestock", this.lastRestock);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.lastRestock = input.getLongOr("LastRestock", 0L);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isTrading() ? SoundEvents.WANDERING_TRADER_TRADE : SoundEvents.WANDERING_TRADER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WANDERING_TRADER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WANDERING_TRADER_DEATH;
    }

    @Override
    protected SoundEvent getTradeUpdatedSound(boolean validTrade) {
        return validTrade ? SoundEvents.WANDERING_TRADER_YES : SoundEvents.WANDERING_TRADER_NO;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.WANDERING_TRADER_YES;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 1.25F;
    }
}
