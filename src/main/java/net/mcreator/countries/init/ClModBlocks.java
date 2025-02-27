
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

import net.mcreator.countries.block.YellowJellyBeanBlockBlock;
import net.mcreator.countries.block.VanillaIceCreamBlockBlock;
import net.mcreator.countries.block.TourmalineOreBlockBlock;
import net.mcreator.countries.block.StrawberryIceCreamBlockBlock;
import net.mcreator.countries.block.RockCandyBlock;
import net.mcreator.countries.block.RedJellyBeanBlockBlock;
import net.mcreator.countries.block.PurpleJellyBeanBlockBlock;
import net.mcreator.countries.block.OrangeJellyBeanBlockBlock;
import net.mcreator.countries.block.MintIceCreamBlockBlock;
import net.mcreator.countries.block.MeltedChocolateBlock;
import net.mcreator.countries.block.IceCreamMachineBlock;
import net.mcreator.countries.block.IceCreamDimensionPortalBlock;
import net.mcreator.countries.block.GummyGrassBlock;
import net.mcreator.countries.block.GummyDirtBlock;
import net.mcreator.countries.block.GreenJellyBeanBlockBlock;
import net.mcreator.countries.block.GingerbreadBlockBlock;
import net.mcreator.countries.block.GingerbBlock;
import net.mcreator.countries.block.GingerBreadStairsBlock;
import net.mcreator.countries.block.GingerBreadSlabBlock;
import net.mcreator.countries.block.ChocolateIceCreamBlockBlock;
import net.mcreator.countries.block.ChocolateFactoryBlock;
import net.mcreator.countries.block.ChocolateBlockBlock;
import net.mcreator.countries.block.CandylogBlock;
import net.mcreator.countries.block.CandyStairsBlock;
import net.mcreator.countries.block.CandySlabBlock;
import net.mcreator.countries.block.CandyPressurePlateBlock;
import net.mcreator.countries.block.CandyPlanksBlock;
import net.mcreator.countries.block.CandyLeavesBlock;
import net.mcreator.countries.block.CandyFenceGateBlock;
import net.mcreator.countries.block.CandyFenceBlock;
import net.mcreator.countries.block.CandyButtonBlock;
import net.mcreator.countries.block.BubbalooBlock;
import net.mcreator.countries.block.BlockOfTourmalineBlock;
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
	public static final RegistryObject<Block> CHOCOLATE_BLOCK = REGISTRY.register("chocolate_block", () -> new ChocolateBlockBlock());
	public static final RegistryObject<Block> CHOCOLATE_FACTORY = REGISTRY.register("chocolate_factory", () -> new ChocolateFactoryBlock());
	public static final RegistryObject<Block> CANDY_BUTTON = REGISTRY.register("candy_button", () -> new CandyButtonBlock());
	public static final RegistryObject<Block> CANDY_STAIRS = REGISTRY.register("candy_stairs", () -> new CandyStairsBlock());
	public static final RegistryObject<Block> CANDY_PRESSURE_PLATE = REGISTRY.register("candy_pressure_plate", () -> new CandyPressurePlateBlock());
	public static final RegistryObject<Block> CANDY_FENCE = REGISTRY.register("candy_fence", () -> new CandyFenceBlock());
	public static final RegistryObject<Block> CANDY_FENCE_GATE = REGISTRY.register("candy_fence_gate", () -> new CandyFenceGateBlock());
	public static final RegistryObject<Block> CANDY_SLAB = REGISTRY.register("candy_slab", () -> new CandySlabBlock());
	public static final RegistryObject<Block> GINGER_BREAD_STAIRS = REGISTRY.register("ginger_bread_stairs", () -> new GingerBreadStairsBlock());
	public static final RegistryObject<Block> GINGER_BREAD_SLAB = REGISTRY.register("ginger_bread_slab", () -> new GingerBreadSlabBlock());
	public static final RegistryObject<Block> RED_JELLY_BEAN_BLOCK = REGISTRY.register("red_jelly_bean_block", () -> new RedJellyBeanBlockBlock());
	public static final RegistryObject<Block> GREEN_JELLY_BEAN_BLOCK = REGISTRY.register("green_jelly_bean_block", () -> new GreenJellyBeanBlockBlock());
	public static final RegistryObject<Block> YELLOW_JELLY_BEAN_BLOCK = REGISTRY.register("yellow_jelly_bean_block", () -> new YellowJellyBeanBlockBlock());
	public static final RegistryObject<Block> PURPLE_JELLY_BEAN_BLOCK = REGISTRY.register("purple_jelly_bean_block", () -> new PurpleJellyBeanBlockBlock());
	public static final RegistryObject<Block> ORANGE_JELLY_BEAN_BLOCK = REGISTRY.register("orange_jelly_bean_block", () -> new OrangeJellyBeanBlockBlock());
	public static final RegistryObject<Block> TOURMALINE_ORE_BLOCK = REGISTRY.register("tourmaline_ore_block", () -> new TourmalineOreBlockBlock());
	public static final RegistryObject<Block> BLOCK_OF_TOURMALINE = REGISTRY.register("block_of_tourmaline", () -> new BlockOfTourmalineBlock());
	public static final RegistryObject<Block> BUBBALOO = REGISTRY.register("bubbaloo", () -> new BubbalooBlock());
	public static final RegistryObject<Block> VANILLA_ICE_CREAM_BLOCK = REGISTRY.register("vanilla_ice_cream_block", () -> new VanillaIceCreamBlockBlock());
	public static final RegistryObject<Block> CHOCOLATE_ICE_CREAM_BLOCK = REGISTRY.register("chocolate_ice_cream_block", () -> new ChocolateIceCreamBlockBlock());
	public static final RegistryObject<Block> STRAWBERRY_ICE_CREAM_BLOCK = REGISTRY.register("strawberry_ice_cream_block", () -> new StrawberryIceCreamBlockBlock());
	public static final RegistryObject<Block> ICE_CREAM_DIMENSION_PORTAL = REGISTRY.register("ice_cream_dimension_portal", () -> new IceCreamDimensionPortalBlock());
	public static final RegistryObject<Block> MINT_ICE_CREAM_BLOCK = REGISTRY.register("mint_ice_cream_block", () -> new MintIceCreamBlockBlock());
	public static final RegistryObject<Block> ICE_CREAM_MACHINE = REGISTRY.register("ice_cream_machine", () -> new IceCreamMachineBlock());

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
