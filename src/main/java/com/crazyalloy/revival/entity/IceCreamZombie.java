package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

/**
 * Ice Cream Zombie (stage 6): the simplest hostile of the Ice Cream Dimension, a narrow humanoid moulded from one
 * flavour (chocolate, vanilla, strawberry or mint: a variant of one entity type, with its own texture, name and
 * ice cream drop). It hunts players and hits in melee. 20 health. Not burned by daylight.
 */
public class IceCreamZombie extends Monster implements FlavoredMob {
    private static final EntityDataAccessor<Integer> DATA_FLAVOR =
            SynchedEntityData.defineId(IceCreamZombie.class, EntityDataSerializers.INT);
    public IceCreamZombie(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0)
                .add(Attributes.FOLLOW_RANGE, 35.0);
    }

    public static boolean checkSpawnRules(EntityType<? extends Monster> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random)
                && SpawnRules.chance(reason, random, RevivalConfig.ICE_CREAM_ZOMBIE_SPAWN_CHANCE::get);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAVOR, 0);
    }

    @Override
    public IceCreamFlavor flavor() {
        return IceCreamFlavor.byIndex(this.entityData.get(DATA_FLAVOR));
    }

    public void setFlavor(IceCreamFlavor flavor) {
        this.entityData.set(DATA_FLAVOR, flavor.ordinal());
    }

    /** Natural spawns, spawn eggs and /summon without a "Flavor" get a random flavour. */
    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
            DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData groupData) {
        this.setFlavor(IceCreamFlavor.random(level.getRandom()));
        return super.finalizeSpawn(level, difficulty, reason, groupData);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putString("Flavor", this.flavor().id());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.getString("Flavor").ifPresent(id -> this.setFlavor(IceCreamFlavor.byId(id)));
    }

    /** "Chocolate Ice Cream Zombie" and so on: the flavour is part of the name. */
    @Override
    protected Component getTypeName() {
        return Component.translatable(this.getType().getDescriptionId() + "." + this.flavor().id());
    }

    /** The flavour's own ice cream (the shared loot table has the rest). */
    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean killedByPlayer) {
        super.dropCustomDeathLoot(level, source, killedByPlayer);
        int count = this.random.nextInt(2) + (killedByPlayer ? this.random.nextInt(2) : 0);
        if (count > 0) {
            this.spawnAtLocation(level, new ItemStack(this.flavor().iceCream(), count));
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, IceCreamZombie.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ICE_CREAM_ZOMBIE.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ICE_CREAM_ZOMBIE.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ICE_CREAM_ZOMBIE.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SNOW_STEP, 0.4F, 0.9F);
    }
}
