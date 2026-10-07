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
 * Angry Ice Cream Cone (stage 6): what is left of a Living Ice Cream after someone scoops its ice cream, and what the
 * Ice Cream Dragon calls to its side. An empty wafer cone on thin legs with an angry face, that charges and kicks.
 * The reference images do not show it in detail, so its look is a revival proposal. 12 health.
 */
public class AngryIceCreamCone extends Monster {
    public AngryIceCreamCone(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    public static boolean checkSpawnRules(EntityType<? extends Monster> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random)
                && SpawnRules.chance(reason, random, RevivalConfig.ANGRY_ICE_CREAM_CONE_SPAWN_CHANCE::get);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, AngryIceCreamCone.class, IceCreamDragon.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ANGRY_ICE_CREAM_CONE.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ANGRY_ICE_CREAM_CONE.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ANGRY_ICE_CREAM_CONE.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.BAMBOO_WOOD_STEP, 0.3F, 1.4F);
    }
}
