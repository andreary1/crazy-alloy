
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
import net.mcreator.countries.block.StemBlockBlock;
import net.mcreator.countries.block.RockCandyBlock;
import net.mcreator.countries.block.RedLicoricePlantBlock;
import net.mcreator.countries.block.RedJellyBeanBlockBlock;
import net.mcreator.countries.block.PurpleMushroomBlock;
import net.mcreator.countries.block.PurpleJellyBeanBlockBlock;
import net.mcreator.countries.block.OrangeJellyBeanBlockBlock;
import net.mcreator.countries.block.OnionStage4Block;
import net.mcreator.countries.block.OnionStage3Block;
import net.mcreator.countries.block.OnionStage2Block;
import net.mcreator.countries.block.OnionStage1Block;
import net.mcreator.countries.block.MysticStairsBlock;
import net.mcreator.countries.block.MysticSlabBlock;
import net.mcreator.countries.block.MysticPressurePlateBlock;
import net.mcreator.countries.block.MysticPlanksBlock;
import net.mcreator.countries.block.MysticLogBlock;
import net.mcreator.countries.block.MysticLeavesBlock;
import net.mcreator.countries.block.MysticGrassBlock;
import net.mcreator.countries.block.MysticFenceGateBlock;
import net.mcreator.countries.block.MysticFenceBlock;
import net.mcreator.countries.block.MysticDoorBlock;
import net.mcreator.countries.block.MysticDirtBlock;
import net.mcreator.countries.block.MysticButtonBlock;
import net.mcreator.countries.block.MintIceCreamBlockBlock;
import net.mcreator.countries.block.MineralConverterBlock;
import net.mcreator.countries.block.MeltedChocolateBlock;
import net.mcreator.countries.block.JarBlock;
import net.mcreator.countries.block.JadeOreBlock;
import net.mcreator.countries.block.InfestedPurpleJellyBeanBlockBlock;
import net.mcreator.countries.block.IceCreamMachineBlock;
import net.mcreator.countries.block.IceCreamDragonEggBlock;
import net.mcreator.countries.block.IceCreamDimensionPortalBlock;
import net.mcreator.countries.block.GummyGrassBlock;
import net.mcreator.countries.block.GummyDirtBlock;
import net.mcreator.countries.block.GreenJellyBeanBlockBlock;
import net.mcreator.countries.block.GingerbreadBlockBlock;
import net.mcreator.countries.block.GingerbBlock;
import net.mcreator.countries.block.GingerBreadStairsBlock;
import net.mcreator.countries.block.GingerBreadSlabBlock;
import net.mcreator.countries.block.GardenGnomeBlock;
import net.mcreator.countries.block.FairyJarBlock;
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
import net.mcreator.countries.block.CandyDoorBlock;
import net.mcreator.countries.block.CandyButtonBlock;
import net.mcreator.countries.block.BubbalooBlock;
import net.mcreator.countries.block.BlueMushroomBlock;
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
	public static final RegistryObject<Block> RED_LICORICE_PLANT = REGISTRY.register("red_licorice_plant", () -> new RedLicoricePlantBlock());
	public static final RegistryObject<Block> MINERAL_CONVERTER = REGISTRY.register("mineral_converter", () -> new MineralConverterBlock());
	public static final RegistryObject<Block> INFESTED_PURPLE_JELLY_BEAN_BLOCK = REGISTRY.register("infested_purple_jelly_bean_block", () -> new InfestedPurpleJellyBeanBlockBlock());
	public static final RegistryObject<Block> ICE_CREAM_DRAGON_EGG = REGISTRY.register("ice_cream_dragon_egg", () -> new IceCreamDragonEggBlock());
	public static final RegistryObject<Block> MYSTIC_GRASS = REGISTRY.register("mystic_grass", () -> new MysticGrassBlock());
	public static final RegistryObject<Block> MYSTIC_DIRT = REGISTRY.register("mystic_dirt", () -> new MysticDirtBlock());
	public static final RegistryObject<Block> MYSTIC_LEAVES = REGISTRY.register("mystic_leaves", () -> new MysticLeavesBlock());
	public static final RegistryObject<Block> MYSTIC_LOG = REGISTRY.register("mystic_log", () -> new MysticLogBlock());
	public static final RegistryObject<Block> MYSTIC_PLANKS = REGISTRY.register("mystic_planks", () -> new MysticPlanksBlock());
	public static final RegistryObject<Block> MYSTIC_STAIRS = REGISTRY.register("mystic_stairs", () -> new MysticStairsBlock());
	public static final RegistryObject<Block> MYSTIC_SLAB = REGISTRY.register("mystic_slab", () -> new MysticSlabBlock());
	public static final RegistryObject<Block> MYSTIC_FENCE = REGISTRY.register("mystic_fence", () -> new MysticFenceBlock());
	public static final RegistryObject<Block> MYSTIC_FENCE_GATE = REGISTRY.register("mystic_fence_gate", () -> new MysticFenceGateBlock());
	public static final RegistryObject<Block> MYSTIC_PRESSURE_PLATE = REGISTRY.register("mystic_pressure_plate", () -> new MysticPressurePlateBlock());
	public static final RegistryObject<Block> MYSTIC_BUTTON = REGISTRY.register("mystic_button", () -> new MysticButtonBlock());
	public static final RegistryObject<Block> STEM_BLOCK = REGISTRY.register("stem_block", () -> new StemBlockBlock());
	public static final RegistryObject<Block> ONION_STAGE_1 = REGISTRY.register("onion_stage_1", () -> new OnionStage1Block());
	public static final RegistryObject<Block> ONION_STAGE_2 = REGISTRY.register("onion_stage_2", () -> new OnionStage2Block());
	public static final RegistryObject<Block> ONION_STAGE_3 = REGISTRY.register("onion_stage_3", () -> new OnionStage3Block());
	public static final RegistryObject<Block> ONION_STAGE_4 = REGISTRY.register("onion_stage_4", () -> new OnionStage4Block());
	public static final RegistryObject<Block> GARDEN_GNOME = REGISTRY.register("garden_gnome", () -> new GardenGnomeBlock());
	public static final RegistryObject<Block> BLUE_MUSHROOM = REGISTRY.register("blue_mushroom", () -> new BlueMushroomBlock());
	public static final RegistryObject<Block> JAR = REGISTRY.register("jar", () -> new JarBlock());
	public static final RegistryObject<Block> FAIRY_JAR = REGISTRY.register("fairy_jar", () -> new FairyJarBlock());
	public static final RegistryObject<Block> JADE_ORE = REGISTRY.register("jade_ore", () -> new JadeOreBlock());
	public static final RegistryObject<Block> CANDY_DOOR = REGISTRY.register("candy_door", () -> new CandyDoorBlock());
	public static final RegistryObject<Block> MYSTIC_DOOR = REGISTRY.register("mystic_door", () -> new MysticDoorBlock());
	public static final RegistryObject<Block> PURPLE_MUSHROOM = REGISTRY.register("purple_mushroom", () -> new PurpleMushroomBlock());

	// Start of user code block custom blocks
	// End of user code block custom blocks
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class BlocksClientSideHandler {
		@SubscribeEvent
		public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
			GummyGrassBlock.blockColorLoad(event);
			CandyLeavesBlock.blockColorLoad(event);
			MysticGrassBlock.blockColorLoad(event);
			MysticLeavesBlock.blockColorLoad(event);
		}
	}
}
