package com.crazyalloy.revival.block;

import com.crazyalloy.revival.CrazyAlloyRevival;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

/** Liquid bubblegum left behind by Bubbaloo Creepers: glows faintly and stings (1 damage per hit) anything inside it. */
public class BubbalooBlock extends LiquidBlock {
    public static final ResourceKey<DamageType> BUBBALOO_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, CrazyAlloyRevival.id("bubbaloo"));

    public BubbalooBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
        if (level instanceof ServerLevel serverLevel && entity instanceof LivingEntity living) {
            living.hurtServer(serverLevel, new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(BUBBALOO_DAMAGE)), 1.0F);
        }
    }
}
