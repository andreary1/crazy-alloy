package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModSounds;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Cotton Candy Tornado: a hostile whirl of spun sugar (30 health, 3 damage) that roams the Sweet Forest at night.
 * Once it is worn down to 5 hearts or less, right-click it with a Stick to wind it up: the stick is used, the
 * tornado is gone and you get Cotton Candy (and the "COTTON CANDYYYYY!!!" advancement).
 */
public class CottonCandyTornado extends Monster {
    public static final float CATCHABLE_HEALTH = 10.0F;
    private static final DustParticleOptions PINK_DUST = new DustParticleOptions(0xFFB8E6, 1.2F);
    private static final DustParticleOptions THREAD = new DustParticleOptions(0xFF9FDC, 0.6F);
    private static final DustParticleOptions SUGAR = new DustParticleOptions(0xFFFFFF, 0.5F);

    public CottonCandyTornado(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    public static boolean checkSpawnRules(EntityType<CottonCandyTornado> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random) && SpawnRules.chance(reason, random, RevivalConfig.COTTON_CANDY_TORNADO_SPAWN_CHANCE);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.1, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(Items.STICK) || this.getHealth() > CATCHABLE_HEALTH) {
            return super.mobInteract(player, hand);
        }
        if (this.level() instanceof ServerLevel level) {
            stack.consume(1, player);
            ItemStack candy = new ItemStack(ModItems.COTTON_CANDY.get(), 3);
            if (!player.getInventory().add(candy)) {
                level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), candy));
            }
            level.sendParticles(PINK_DUST, this.getX(), this.getY(0.5), this.getZ(), 30, 0.4, 0.6, 0.4, 0.0);
            level.sendParticles(ParticleTypes.POOF, this.getX(), this.getY(0.5), this.getZ(), 8, 0.3, 0.4, 0.3, 0.02);
            this.playSound(SoundEvents.WOOL_BREAK, 1.0F, 1.4F);
            if (player instanceof ServerPlayer serverPlayer) {
                AdvancementHolder adv = level.getServer().getAdvancements().get(CrazyAlloyRevival.id("cotton_candy_catch"));
                if (adv != null) {
                    serverPlayer.getAdvancements().award(adv, "catch");
                }
            }
            this.discard();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide()) {
            // Threads of cotton candy and grains of sugar whirl around the lower layers, below the eyes.
            float angle = this.tickCount * 0.45F + this.random.nextFloat() * 0.6F;
            double h = this.getBbHeight() * (0.3 + this.random.nextFloat() * 0.35);
            double r = 0.25 + h * 0.3;
            double x = this.getX() + Math.cos(angle) * r;
            double z = this.getZ() + Math.sin(angle) * r;
            double tx = -Math.sin(angle) * 0.08;
            double tz = Math.cos(angle) * 0.08;
            if (this.random.nextInt(2) == 0) {
                this.level().addParticle(THREAD, x, this.getY() + h, z, tx, 0.01, tz);
            }
            if (this.random.nextInt(4) == 0) {
                this.level().addParticle(SUGAR, x, this.getY() + h, z, tx * 1.5, 0.03, tz * 1.5);
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.COTTON_CANDY_TORNADO.ambient().get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.COTTON_CANDY_TORNADO.hurt().get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.COTTON_CANDY_TORNADO.death().get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }
}
