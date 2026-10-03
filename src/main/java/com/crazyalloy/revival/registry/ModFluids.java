package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Melted Chocolate (Sweet Forest lakes, bucketable, the Chocolate Factory turns a bucket into 10 bars) and
 * Bubbaloo (left by Bubbaloo Creepers, glows, stings). Both are thick and slow, neither makes infinite sources.
 */
public final class ModFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, CrazyAlloyRevival.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, CrazyAlloyRevival.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> MELTED_CHOCOLATE_TYPE = FLUID_TYPES.register("melted_chocolate", () -> new FluidType(
            FluidType.Properties.create().descriptionId("block.crazyalloy_revival.melted_chocolate")
                    .density(1800).viscosity(4000).temperature(310).motionScale(0.0045).canSwim(true).canDrown(true)
                    .pathType(PathType.WATER).adjacentPathType(null)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    public static final DeferredHolder<FluidType, FluidType> BUBBALOO_TYPE = FLUID_TYPES.register("bubbaloo", () -> new FluidType(
            FluidType.Properties.create().descriptionId("block.crazyalloy_revival.bubbaloo")
                    .density(2000).viscosity(5000).lightLevel(8).motionScale(0.003).canSwim(false).canDrown(true)
                    .pathType(PathType.DAMAGING).adjacentPathType(PathType.DAMAGING_IN_NEIGHBOR)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));

    public static final DeferredHolder<Fluid, FlowingFluid> MELTED_CHOCOLATE = FLUIDS.register("melted_chocolate", () -> new BaseFlowingFluid.Source(chocolate()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_MELTED_CHOCOLATE = FLUIDS.register("flowing_melted_chocolate", () -> new BaseFlowingFluid.Flowing(chocolate()));
    public static final DeferredHolder<Fluid, FlowingFluid> BUBBALOO = FLUIDS.register("bubbaloo", () -> new BaseFlowingFluid.Source(bubbaloo()));
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_BUBBALOO = FLUIDS.register("flowing_bubbaloo", () -> new BaseFlowingFluid.Flowing(bubbaloo()));

    private static BaseFlowingFluid.Properties chocolate() {
        return new BaseFlowingFluid.Properties(MELTED_CHOCOLATE_TYPE, MELTED_CHOCOLATE, FLOWING_MELTED_CHOCOLATE)
                .bucket(ModItems.MELTED_CHOCOLATE_BUCKET).block(ModBlocks.MELTED_CHOCOLATE).slopeFindDistance(2).levelDecreasePerBlock(2).tickRate(20);
    }

    private static BaseFlowingFluid.Properties bubbaloo() {
        return new BaseFlowingFluid.Properties(BUBBALOO_TYPE, BUBBALOO, FLOWING_BUBBALOO)
                .bucket(ModItems.BUBBALOO_BUCKET).block(ModBlocks.BUBBALOO).slopeFindDistance(2).levelDecreasePerBlock(2).tickRate(30);
    }

    private ModFluids() {}
}
