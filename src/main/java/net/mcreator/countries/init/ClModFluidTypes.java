
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fluids.FluidType;

import net.mcreator.countries.fluid.types.MeltedChocolateFluidType;
import net.mcreator.countries.fluid.types.BubbalooFluidType;
import net.mcreator.countries.ClMod;

public class ClModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ClMod.MODID);
	public static final RegistryObject<FluidType> MELTED_CHOCOLATE_TYPE = REGISTRY.register("melted_chocolate", () -> new MeltedChocolateFluidType());
	public static final RegistryObject<FluidType> BUBBALOO_TYPE = REGISTRY.register("bubbaloo", () -> new BubbalooFluidType());
}
