package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
 * Ice Cream Beast (stage 6): a big, hunched brute made of all four flavours (strawberry head, vanilla torso, long
 * chocolate arms, mint legs) wearing a tilted wafer cone as a hat. 100 health. While it has a target it keeps renewing
 * Speed, Strength, Resistance and Regeneration on itself ({@link RevivalConfig#ICE_CREAM_BEAST_BUFFS}), so it is far
 * tougher than the zombies. Swings both heavy arms down together.
 */
public class IceCreamBeast extends Monster {
    private static final int BUFF_INTERVAL = 80, BUFF_LENGTH = 120;
    private int buffCooldown;

    public IceCreamBeast(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    public static boolean checkSpawnRules(EntityType<? extends Monster> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random)
                && SpawnRules.chance(reason, random, RevivalConfig.ICE_CREAM_BEAST_SPAWN_CHANCE::get);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        if (this.buffCooldown > 0) {
            this.buffCooldown--;
        }
        LivingEntity target = this.getTarget();
        if (this.buffCooldown <= 0 && target != null && target.isAlive() && RevivalConfig.ICE_CREAM_BEAST_BUFFS.get()) {
            this.buffCooldown = BUFF_INTERVAL;
            buff(MobEffects.SPEED, 0);
            buff(MobEffects.STRENGTH, 0);
            buff(MobEffects.RESISTANCE, 0);
            buff(MobEffects.REGENERATION, 0);
        }
    }

    private void buff(Holder<MobEffect> effect, int amplifier) {
        this.addEffect(new MobEffectInstance(effect, BUFF_LENGTH, amplifier, false, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ICE_CREAM_BEAST.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ICE_CREAM_BEAST.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ICE_CREAM_BEAST.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SNOW_STEP, 0.9F, 0.6F);
    }

    @Override
    protected float getSoundVolume() {
        return 1.3F;
    }
}
