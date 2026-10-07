package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Ice Cream Zombie (stage 6): the simplest hostile of the Ice Cream Dimension, a narrow humanoid moulded from one
 * flavour (chocolate, vanilla, strawberry or mint; each flavour is its own entity type with its own texture and
 * drops). It hunts players and hits in melee. 20 health. Not burned by daylight.
 */
public class IceCreamZombie extends Monster {
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

    public IceCreamFlavor flavor() {
        return IceCreamFlavor.of(this.getType());
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
