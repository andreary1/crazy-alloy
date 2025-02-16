
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.block.Block;

import net.mcreator.countries.block.RockCandyBlock;
import net.mcreator.countries.block.MeltedChocolateBlock;
import net.mcreator.countries.block.GummyGrassBlock;
import net.mcreator.countries.block.GummyDirtBlock;
import net.mcreator.countries.block.GingerbreadBlockBlock;
import net.mcreator.countries.block.GingerbBlock;
import net.mcreator.countries.block.CandylogBlock;
import net.mcreator.countries.block.CandyPlanksBlock;
import net.mcreator.countries.block.CandyLeavesBlock;
import net.mcreator.countries.ClMod;

public class ClModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ClMod.MODID);
	public static final RegistryObject<Block> GUMMY_GRASS = REGISTRY.register("gummy_grass", () -> new GummyGrassBlock());
	public static final RegistryObject<Block> GUMMY_DIRT = REGISTRY.register("gummy_dirt", () -> new GummyDirtBlock());
	public static final RegistryObject<Block> ROCK_CANDY = REGISTRY.register("rock_candy", () -> new RockCandyBlock());
	public static final RegistryObject<Block> CANDYLOG = REGISTRY.register("candylog", () -> new CandylogBlock());
	public static final RegistryObject<Block> MELTED_CHOCOLATE = REGISTRY.register("melted_chocolate", () -> new MeltedChocolateBlock());
	public static final RegistryObject<Block> CANDY_LEAVES = REGISTRY.register("candy_leaves", () -> new CandyLeavesBlock());
	public static final RegistryObject<Block> GINGERBREAD_BLOCK = REGISTRY.register("gingerbread_block", () -> new GingerbreadBlockBlock());
	public static final RegistryObject<Block> GINGERB = REGISTRY.register("gingerb", () -> new GingerbBlock());
	public static final RegistryObject<Block> CANDY_PLANKS = REGISTRY.register("candy_planks", () -> new CandyPlanksBlock());

	// Start of user code block custom blocks
	// End of user code block custom blocks
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class BlocksClientSideHandler {
		@SubscribeEvent
		public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
			GummyGrassBlock.blockColorLoad(event);
			GummyDirtBlock.blockColorLoad(event);
			CandyLeavesBlock.blockColorLoad(event);
		}
	}
}
