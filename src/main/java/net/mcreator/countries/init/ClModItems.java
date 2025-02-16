
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

import net.mcreator.countries.item.MeltedChocolateItem;
import net.mcreator.countries.item.LollipopItem;
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

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
