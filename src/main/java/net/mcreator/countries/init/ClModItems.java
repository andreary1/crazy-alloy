
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.ForgeSpawnEggItem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.countries.item.VanillaIceCreamItem;
import net.mcreator.countries.item.UltimateIceCreamItem;
import net.mcreator.countries.item.TourmalineSwordItem;
import net.mcreator.countries.item.TourmalineShovelItem;
import net.mcreator.countries.item.TourmalinePickaxeItem;
import net.mcreator.countries.item.TourmalineItem;
import net.mcreator.countries.item.TourmalineHoeItem;
import net.mcreator.countries.item.TourmalineGemItem;
import net.mcreator.countries.item.TourmalineAxeItem;
import net.mcreator.countries.item.StrawberryIceCreamItem;
import net.mcreator.countries.item.RollCakeItem;
import net.mcreator.countries.item.RedLicoriceCandyItem;
import net.mcreator.countries.item.MoneyItem;
import net.mcreator.countries.item.MintIceCreamItem;
import net.mcreator.countries.item.MeltedChocolateItem;
import net.mcreator.countries.item.LollipopItem;
import net.mcreator.countries.item.JellyBazookaItem;
import net.mcreator.countries.item.IceCreamDimensionItem;
import net.mcreator.countries.item.HeavyBootsItem;
import net.mcreator.countries.item.GingerbreadLongswordItem;
import net.mcreator.countries.item.GingerbreadItem;
import net.mcreator.countries.item.GingerbreadCannonItem;
import net.mcreator.countries.item.EmptyIceCreamConeItem;
import net.mcreator.countries.item.DeadJellySnakeItem;
import net.mcreator.countries.item.CottonCandyItem;
import net.mcreator.countries.item.CookedLicoriceCandyItem;
import net.mcreator.countries.item.CoinItem;
import net.mcreator.countries.item.ChocolateIceCreamItem;
import net.mcreator.countries.item.ChocolateBarItem;
import net.mcreator.countries.item.BulletItem;
import net.mcreator.countries.item.BubbalooItem;
import net.mcreator.countries.item.BrownSugarSwordItem;
import net.mcreator.countries.item.BrownSugarBrickItem;
import net.mcreator.countries.item.AK47Item;
import net.mcreator.countries.ClMod;

public class ClModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ClMod.MODID);
	public static final RegistryObject<Item> GUMMY_GRASS = block(ClModBlocks.GUMMY_GRASS);
	public static final RegistryObject<Item> GUMMY_DIRT = block(ClModBlocks.GUMMY_DIRT);
	public static final RegistryObject<Item> ROCK_CANDY = block(ClModBlocks.ROCK_CANDY);
	public static final RegistryObject<Item> CANDYLOG = block(ClModBlocks.CANDYLOG);
	public static final RegistryObject<Item> MELTED_CHOCOLATE_BUCKET = REGISTRY.register("melted_chocolate_bucket", () -> new MeltedChocolateItem());
	public static final RegistryObject<Item> CANDY_LEAVES = block(ClModBlocks.CANDY_LEAVES);
	public static final RegistryObject<Item> CANDY_TUBE_DOG_SPAWN_EGG = REGISTRY.register("candy_tube_dog_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.CANDY_TUBE_DOG, -5034893, -6351009, new Item.Properties()));
	public static final RegistryObject<Item> JELLY_SHARK_SPAWN_EGG = REGISTRY.register("jelly_shark_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.JELLY_SHARK, -3407770, -13369600, new Item.Properties()));
	public static final RegistryObject<Item> LOLLIPOP_GUY_SPAWN_EGG = REGISTRY.register("lollipop_guy_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.LOLLIPOP_GUY, -1, -3407872, new Item.Properties()));
	public static final RegistryObject<Item> LOLLIPOP = REGISTRY.register("lollipop", () -> new LollipopItem());
	public static final RegistryObject<Item> GINGERBREAD_BLOCK = block(ClModBlocks.GINGERBREAD_BLOCK);
	public static final RegistryObject<Item> GINGERB = block(ClModBlocks.GINGERB);
	public static final RegistryObject<Item> BROWN_SUGAR_RHINO_SPAWN_EGG = REGISTRY.register("brown_sugar_rhino_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.BROWN_SUGAR_RHINO, -6737152, -4359366, new Item.Properties()));
	public static final RegistryObject<Item> CANDY_PLANKS = block(ClModBlocks.CANDY_PLANKS);
	public static final RegistryObject<Item> GINGERBREAD_SOLDIER_SPAWN_EGG = REGISTRY.register("gingerbread_soldier_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.GINGERBREAD_SOLDIER, -6198989, -15438810, new Item.Properties()));
	public static final RegistryObject<Item> COTTON_CANDY_TORNADO_SPAWN_EGG = REGISTRY.register("cotton_candy_tornado_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.COTTON_CANDY_TORNADO, -284435, -1602934, new Item.Properties()));
	public static final RegistryObject<Item> GINGERBREAD_WARRIOR_SPAWN_EGG = REGISTRY.register("gingerbread_warrior_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.GINGERBREAD_WARRIOR, -6198989, -3202267, new Item.Properties()));
	public static final RegistryObject<Item> CHOCOLATE_BLOCK = block(ClModBlocks.CHOCOLATE_BLOCK);
	public static final RegistryObject<Item> CHOCOLATE_FACTORY = block(ClModBlocks.CHOCOLATE_FACTORY);
	public static final RegistryObject<Item> CANDY_BUTTON = block(ClModBlocks.CANDY_BUTTON);
	public static final RegistryObject<Item> CANDY_STAIRS = block(ClModBlocks.CANDY_STAIRS);
	public static final RegistryObject<Item> CANDY_PRESSURE_PLATE = block(ClModBlocks.CANDY_PRESSURE_PLATE);
	public static final RegistryObject<Item> CANDY_FENCE = block(ClModBlocks.CANDY_FENCE);
	public static final RegistryObject<Item> CANDY_FENCE_GATE = block(ClModBlocks.CANDY_FENCE_GATE);
	public static final RegistryObject<Item> CANDY_SLAB = block(ClModBlocks.CANDY_SLAB);
	public static final RegistryObject<Item> CHOCOLATE_BAR = REGISTRY.register("chocolate_bar", () -> new ChocolateBarItem());
	public static final RegistryObject<Item> GINGER_BREAD_STAIRS = block(ClModBlocks.GINGER_BREAD_STAIRS);
	public static final RegistryObject<Item> GINGER_BREAD_SLAB = block(ClModBlocks.GINGER_BREAD_SLAB);
	public static final RegistryObject<Item> GINGERBREAD = REGISTRY.register("gingerbread", () -> new GingerbreadItem());
	public static final RegistryObject<Item> RED_JELLY_BEAN_BLOCK = block(ClModBlocks.RED_JELLY_BEAN_BLOCK);
	public static final RegistryObject<Item> GREEN_JELLY_BEAN_BLOCK = block(ClModBlocks.GREEN_JELLY_BEAN_BLOCK);
	public static final RegistryObject<Item> YELLOW_JELLY_BEAN_BLOCK = block(ClModBlocks.YELLOW_JELLY_BEAN_BLOCK);
	public static final RegistryObject<Item> PURPLE_JELLY_BEAN_BLOCK = block(ClModBlocks.PURPLE_JELLY_BEAN_BLOCK);
	public static final RegistryObject<Item> ORANGE_JELLY_BEAN_BLOCK = block(ClModBlocks.ORANGE_JELLY_BEAN_BLOCK);
	public static final RegistryObject<Item> COTTON_CANDY = REGISTRY.register("cotton_candy", () -> new CottonCandyItem());
	public static final RegistryObject<Item> JELLY_SNAKE_SPAWN_EGG = REGISTRY.register("jelly_snake_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.JELLY_SNAKE, -65536, -10027213, new Item.Properties()));
	public static final RegistryObject<Item> HEAVY_BOOTS_BOOTS = REGISTRY.register("heavy_boots_boots", () -> new HeavyBootsItem.Boots());
	public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM = REGISTRY.register("chocolate_ice_cream", () -> new ChocolateIceCreamItem());
	public static final RegistryObject<Item> VANILLA_ICE_CREAM = REGISTRY.register("vanilla_ice_cream", () -> new VanillaIceCreamItem());
	public static final RegistryObject<Item> STRAWBERRY_ICE_CREAM = REGISTRY.register("strawberry_ice_cream", () -> new StrawberryIceCreamItem());
	public static final RegistryObject<Item> DEAD_JELLY_SNAKE = REGISTRY.register("dead_jelly_snake", () -> new DeadJellySnakeItem());
	public static final RegistryObject<Item> JELLY_BAZOOKA = REGISTRY.register("jelly_bazooka", () -> new JellyBazookaItem());
	public static final RegistryObject<Item> TOURMALINE_ORE_BLOCK = block(ClModBlocks.TOURMALINE_ORE_BLOCK);
	public static final RegistryObject<Item> TOURMALINE_GEM = REGISTRY.register("tourmaline_gem", () -> new TourmalineGemItem());
	public static final RegistryObject<Item> BLOCK_OF_TOURMALINE = block(ClModBlocks.BLOCK_OF_TOURMALINE);
	public static final RegistryObject<Item> TOURMALINE_SWORD = REGISTRY.register("tourmaline_sword", () -> new TourmalineSwordItem());
	public static final RegistryObject<Item> TOURMALINE_PICKAXE = REGISTRY.register("tourmaline_pickaxe", () -> new TourmalinePickaxeItem());
	public static final RegistryObject<Item> ROULADE_MONSTER_SPAWN_EGG = REGISTRY.register("roulade_monster_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.ROULADE_MONSTER, -6724096, -103, new Item.Properties()));
	public static final RegistryObject<Item> BUBBALOO_CREEPER_SPAWN_EGG = REGISTRY.register("bubbaloo_creeper_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.BUBBALOO_CREEPER, -1609510, -2460242, new Item.Properties()));
	public static final RegistryObject<Item> BUBBALOO_BUCKET = REGISTRY.register("bubbaloo_bucket", () -> new BubbalooItem());
	public static final RegistryObject<Item> MINT_ICE_CREAM = REGISTRY.register("mint_ice_cream", () -> new MintIceCreamItem());
	public static final RegistryObject<Item> TOURMALINE_AXE = REGISTRY.register("tourmaline_axe", () -> new TourmalineAxeItem());
	public static final RegistryObject<Item> MONEY = REGISTRY.register("money", () -> new MoneyItem());
	public static final RegistryObject<Item> COIN = REGISTRY.register("coin", () -> new CoinItem());
	public static final RegistryObject<Item> SEGWAY_SPAWN_EGG = REGISTRY.register("segway_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.SEGWAY, -13421773, -1, new Item.Properties()));
	public static final RegistryObject<Item> AK_47 = REGISTRY.register("ak_47", () -> new AK47Item());
	public static final RegistryObject<Item> BULLET = REGISTRY.register("bullet", () -> new BulletItem());
	public static final RegistryObject<Item> TOURMALINE_HELMET = REGISTRY.register("tourmaline_helmet", () -> new TourmalineItem.Helmet());
	public static final RegistryObject<Item> TOURMALINE_CHESTPLATE = REGISTRY.register("tourmaline_chestplate", () -> new TourmalineItem.Chestplate());
	public static final RegistryObject<Item> TOURMALINE_LEGGINGS = REGISTRY.register("tourmaline_leggings", () -> new TourmalineItem.Leggings());
	public static final RegistryObject<Item> TOURMALINE_BOOTS = REGISTRY.register("tourmaline_boots", () -> new TourmalineItem.Boots());
	public static final RegistryObject<Item> ULTIMATE_ICE_CREAM = REGISTRY.register("ultimate_ice_cream", () -> new UltimateIceCreamItem());
	public static final RegistryObject<Item> GUARD_DINOSAUR_SPAWN_EGG = REGISTRY.register("guard_dinosaur_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.GUARD_DINOSAUR, -15526637, -13864397, new Item.Properties()));
	public static final RegistryObject<Item> TOURMALINE_HOE = REGISTRY.register("tourmaline_hoe", () -> new TourmalineHoeItem());
	public static final RegistryObject<Item> TOURMALINE_SHOVEL = REGISTRY.register("tourmaline_shovel", () -> new TourmalineShovelItem());
	public static final RegistryObject<Item> VANILLA_ICE_CREAM_BLOCK = block(ClModBlocks.VANILLA_ICE_CREAM_BLOCK);
	public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM_BLOCK = block(ClModBlocks.CHOCOLATE_ICE_CREAM_BLOCK);
	public static final RegistryObject<Item> STRAWBERRY_ICE_CREAM_BLOCK = block(ClModBlocks.STRAWBERRY_ICE_CREAM_BLOCK);
	public static final RegistryObject<Item> ICE_CREAM_DIMENSION = REGISTRY.register("ice_cream_dimension", () -> new IceCreamDimensionItem());
	public static final RegistryObject<Item> GINGERBREADKING_SPAWN_EGG = REGISTRY.register("gingerbreadking_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.GINGERBREADKING, -6724096, -256, new Item.Properties()));
	public static final RegistryObject<Item> ICE_CREAM_GARGOYLE_SPAWN_EGG = REGISTRY.register("ice_cream_gargoyle_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.ICE_CREAM_GARGOYLE, -10079488, -65332, new Item.Properties()));
	public static final RegistryObject<Item> LIVING_ICE_CREAM_SPAWN_EGG = REGISTRY.register("living_ice_cream_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.LIVING_ICE_CREAM, -668329, -406945, new Item.Properties()));
	public static final RegistryObject<Item> EMPTY_ICE_CREAM_CONE = REGISTRY.register("empty_ice_cream_cone", () -> new EmptyIceCreamConeItem());
	public static final RegistryObject<Item> ANGRY_ICE_CREAM_CONE_SPAWN_EGG = REGISTRY.register("angry_ice_cream_cone_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.ANGRY_ICE_CREAM_CONE, -13261, -52429, new Item.Properties()));
	public static final RegistryObject<Item> MINT_ICE_CREAM_BLOCK = block(ClModBlocks.MINT_ICE_CREAM_BLOCK);
	public static final RegistryObject<Item> BUBBLEGUM_SPAWN_EGG = REGISTRY.register("bubblegum_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.BUBBLEGUM, -65332, -7060844, new Item.Properties()));
	public static final RegistryObject<Item> ICE_CREAM_ZOMBIE_SPAWN_EGG = REGISTRY.register("ice_cream_zombie_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.ICE_CREAM_ZOMBIE, -14982089, -2706587, new Item.Properties()));
	public static final RegistryObject<Item> ICE_CREAM_BEAST_SPAWN_EGG = REGISTRY.register("ice_cream_beast_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.ICE_CREAM_BEAST, -15511785, -10407, new Item.Properties()));
	public static final RegistryObject<Item> ICE_CREAM_MACHINE = block(ClModBlocks.ICE_CREAM_MACHINE);
	public static final RegistryObject<Item> GINGERBREAD_CANNON = REGISTRY.register("gingerbread_cannon", () -> new GingerbreadCannonItem());
	public static final RegistryObject<Item> RED_LICORICE_PLANT = block(ClModBlocks.RED_LICORICE_PLANT);
	public static final RegistryObject<Item> BROWN_SUGAR_BRICK = REGISTRY.register("brown_sugar_brick", () -> new BrownSugarBrickItem());
	public static final RegistryObject<Item> GINGERBREAD_LONGSWORD = REGISTRY.register("gingerbread_longsword", () -> new GingerbreadLongswordItem());
	public static final RegistryObject<Item> MINERAL_CONVERTER = block(ClModBlocks.MINERAL_CONVERTER);
	public static final RegistryObject<Item> GRAPE_SPIDER_SPAWN_EGG = REGISTRY.register("grape_spider_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.GRAPE_SPIDER, -8446821, -4456250, new Item.Properties()));
	public static final RegistryObject<Item> INFESTED_PURPLE_JELLY_BEAN_BLOCK = block(ClModBlocks.INFESTED_PURPLE_JELLY_BEAN_BLOCK);
	public static final RegistryObject<Item> JELLY_BUNNY_SPAWN_EGG = REGISTRY.register("jelly_bunny_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.JELLY_BUNNY, -25270, -12583110, new Item.Properties()));
	public static final RegistryObject<Item> RED_LICORICE_CANDY = REGISTRY.register("red_licorice_candy", () -> new RedLicoriceCandyItem());
	public static final RegistryObject<Item> COOKED_LICORICE_CANDY = REGISTRY.register("cooked_licorice_candy", () -> new CookedLicoriceCandyItem());
	public static final RegistryObject<Item> BROWN_SUGAR_SWORD = REGISTRY.register("brown_sugar_sword", () -> new BrownSugarSwordItem());
	public static final RegistryObject<Item> IMPOSTOR_CAKE_SPAWN_EGG = REGISTRY.register("impostor_cake_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.IMPOSTOR_CAKE, -9554156, -1, new Item.Properties()));
	public static final RegistryObject<Item> ICE_CREAM_DRAGON_SPAWN_EGG = REGISTRY.register("ice_cream_dragon_spawn_egg", () -> new ForgeSpawnEggItem(ClModEntities.ICE_CREAM_DRAGON, -16186359, -1371971, new Item.Properties()));
	public static final RegistryObject<Item> ICE_CREAM_DRAGON_EGG = block(ClModBlocks.ICE_CREAM_DRAGON_EGG);
	public static final RegistryObject<Item> ROLL_CAKE = REGISTRY.register("roll_cake", () -> new RollCakeItem());

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
