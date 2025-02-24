
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import net.mcreator.countries.fluid.MeltedChocolateFluid;
import net.mcreator.countries.fluid.BubbalooFluid;
import net.mcreator.countries.ClMod;

public class ClModFluids {
	public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, ClMod.MODID);
	public static final RegistryObject<FlowingFluid> MELTED_CHOCOLATE = REGISTRY.register("melted_chocolate", () -> new MeltedChocolateFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_MELTED_CHOCOLATE = REGISTRY.register("flowing_melted_chocolate", () -> new MeltedChocolateFluid.Flowing());
	public static final RegistryObject<FlowingFluid> BUBBALOO = REGISTRY.register("bubbaloo", () -> new BubbalooFluid.Source());
	public static final RegistryObject<FlowingFluid> FLOWING_BUBBALOO = REGISTRY.register("flowing_bubbaloo", () -> new BubbalooFluid.Flowing());

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class FluidsClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(MELTED_CHOCOLATE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_MELTED_CHOCOLATE.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(BUBBALOO.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FLOWING_BUBBALOO.get(), RenderType.translucent());
		}
	}
}
