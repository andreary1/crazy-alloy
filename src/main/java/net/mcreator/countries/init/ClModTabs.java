
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.mcreator.countries.ClMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ClMod.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
			tabData.accept(ClModBlocks.CHOCOLATE_FACTORY.get().asItem());
			tabData.accept(ClModBlocks.ICE_CREAM_MACHINE.get().asItem());
			tabData.accept(ClModBlocks.MINERAL_CONVERTER.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
			tabData.accept(ClModBlocks.RED_JELLY_BEAN_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.GREEN_JELLY_BEAN_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.YELLOW_JELLY_BEAN_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.PURPLE_JELLY_BEAN_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.ORANGE_JELLY_BEAN_BLOCK.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			tabData.accept(ClModBlocks.BLOCK_OF_TOURMALINE.get().asItem());
			tabData.accept(ClModBlocks.CHOCOLATE_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.CANDYLOG.get().asItem());
			tabData.accept(ClModBlocks.CANDY_PLANKS.get().asItem());
			tabData.accept(ClModBlocks.CANDY_STAIRS.get().asItem());
			tabData.accept(ClModBlocks.CANDY_SLAB.get().asItem());
			tabData.accept(ClModBlocks.CANDY_FENCE.get().asItem());
			tabData.accept(ClModBlocks.CANDY_FENCE_GATE.get().asItem());
			tabData.accept(ClModBlocks.CANDY_PRESSURE_PLATE.get().asItem());
			tabData.accept(ClModBlocks.CANDY_BUTTON.get().asItem());
			tabData.accept(ClModBlocks.GINGERBREAD_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.GINGERB.get().asItem());
			tabData.accept(ClModBlocks.GINGER_BREAD_STAIRS.get().asItem());
			tabData.accept(ClModBlocks.GINGER_BREAD_SLAB.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_LOG.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_PLANKS.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_STAIRS.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_SLAB.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_FENCE.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_FENCE_GATE.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_PRESSURE_PLATE.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_BUTTON.get().asItem());
			tabData.accept(ClModBlocks.GARDEN_GNOME.get().asItem());
			tabData.accept(ClModBlocks.CANDY_DOOR.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_DOOR.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			tabData.accept(ClModBlocks.GUMMY_GRASS.get().asItem());
			tabData.accept(ClModBlocks.GUMMY_DIRT.get().asItem());
			tabData.accept(ClModBlocks.ROCK_CANDY.get().asItem());
			tabData.accept(ClModBlocks.CANDY_LEAVES.get().asItem());
			tabData.accept(ClModBlocks.RED_LICORICE_PLANT.get().asItem());
			tabData.accept(ClModBlocks.TOURMALINE_ORE_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.VANILLA_ICE_CREAM_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.CHOCOLATE_ICE_CREAM_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.STRAWBERRY_ICE_CREAM_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.MINT_ICE_CREAM_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_GRASS.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_DIRT.get().asItem());
			tabData.accept(ClModBlocks.MYSTIC_LEAVES.get().asItem());
			tabData.accept(ClModBlocks.STEM_BLOCK.get().asItem());
			tabData.accept(ClModItems.ONION_SEEDS.get());
			tabData.accept(ClModBlocks.BLUE_MUSHROOM.get().asItem());
			tabData.accept(ClModBlocks.PURPLE_MUSHROOM.get().asItem());
			tabData.accept(ClModBlocks.JADE_ORE.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(ClModItems.MELTED_CHOCOLATE_BUCKET.get());
			tabData.accept(ClModItems.BUBBALOO_BUCKET.get());
			tabData.accept(ClModItems.COTTON_CANDY.get());
			tabData.accept(ClModItems.TOURMALINE_PICKAXE.get());
			tabData.accept(ClModItems.TOURMALINE_AXE.get());
			tabData.accept(ClModItems.TOURMALINE_HOE.get());
			tabData.accept(ClModItems.TOURMALINE_SHOVEL.get());
			tabData.accept(ClModItems.ICE_CREAM_DIMENSION.get());
			tabData.accept(ClModItems.MINER_HELMET_HELMET.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(ClModItems.CANDY_TUBE_DOG_SPAWN_EGG.get());
			tabData.accept(ClModItems.BROWN_SUGAR_RHINO_SPAWN_EGG.get());
			tabData.accept(ClModItems.LOLLIPOP_GUY_SPAWN_EGG.get());
			tabData.accept(ClModItems.COTTON_CANDY_TORNADO_SPAWN_EGG.get());
			tabData.accept(ClModItems.BUBBLEGUM_SPAWN_EGG.get());
			tabData.accept(ClModItems.GINGERBREAD_SOLDIER_SPAWN_EGG.get());
			tabData.accept(ClModItems.GINGERBREAD_WARRIOR_SPAWN_EGG.get());
			tabData.accept(ClModItems.GINGERBREADKING_SPAWN_EGG.get());
			tabData.accept(ClModItems.JELLY_SHARK_SPAWN_EGG.get());
			tabData.accept(ClModItems.JELLY_SNAKE_SPAWN_EGG.get());
			tabData.accept(ClModItems.JELLY_BUNNY_SPAWN_EGG.get());
			tabData.accept(ClModItems.GRAPE_SPIDER_SPAWN_EGG.get());
			tabData.accept(ClModItems.ROULADE_MONSTER_SPAWN_EGG.get());
			tabData.accept(ClModItems.BUBBALOO_CREEPER_SPAWN_EGG.get());
			tabData.accept(ClModItems.IMPOSTOR_CAKE_SPAWN_EGG.get());
			tabData.accept(ClModItems.SEGWAY_SPAWN_EGG.get());
			tabData.accept(ClModItems.GUARD_DINOSAUR_SPAWN_EGG.get());
			tabData.accept(ClModItems.ICE_CREAM_GARGOYLE_SPAWN_EGG.get());
			tabData.accept(ClModItems.LIVING_ICE_CREAM_SPAWN_EGG.get());
			tabData.accept(ClModItems.ANGRY_ICE_CREAM_CONE_SPAWN_EGG.get());
			tabData.accept(ClModItems.ICE_CREAM_ZOMBIE_SPAWN_EGG.get());
			tabData.accept(ClModItems.ICE_CREAM_BEAST_SPAWN_EGG.get());
			tabData.accept(ClModItems.ICE_CREAM_DRAGON_SPAWN_EGG.get());
			tabData.accept(ClModItems.FAIRY_SPAWN_EGG.get());
			tabData.accept(ClModItems.OGRE_SPAWN_EGG.get());
			tabData.accept(ClModItems.TROLL_SPAWN_EGG.get());
			tabData.accept(ClModItems.SHADOW_EYE_SPAWN_EGG.get());
			tabData.accept(ClModItems.EVIL_FAIRY_SPAWN_EGG.get());
			tabData.accept(ClModItems.ENT_SPAWN_EGG.get());
			tabData.accept(ClModItems.DWARF_SPAWN_EGG.get());
			tabData.accept(ClModItems.SHADOW_WANDERER_SPAWN_EGG.get());
			tabData.accept(ClModItems.WIZARD_SPAWN_EGG.get());
			tabData.accept(ClModItems.MUSHROOM_MAN_SPAWN_EGG.get());
			tabData.accept(ClModItems.MUSHROOM_CYCLOPS_SPAWN_EGG.get());
			tabData.accept(ClModItems.MUSHROOMCOPTER_SPAWN_EGG.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			tabData.accept(ClModItems.LOLLIPOP.get());
			tabData.accept(ClModItems.CHOCOLATE_BAR.get());
			tabData.accept(ClModItems.RED_LICORICE_CANDY.get());
			tabData.accept(ClModItems.COOKED_LICORICE_CANDY.get());
			tabData.accept(ClModItems.ROLL_CAKE.get());
			tabData.accept(ClModItems.CHOCOLATE_ICE_CREAM.get());
			tabData.accept(ClModItems.VANILLA_ICE_CREAM.get());
			tabData.accept(ClModItems.STRAWBERRY_ICE_CREAM.get());
			tabData.accept(ClModItems.MINT_ICE_CREAM.get());
			tabData.accept(ClModItems.ULTIMATE_ICE_CREAM.get());
			tabData.accept(ClModItems.ELIXIR_OF_LIFE.get());
			tabData.accept(ClModItems.ONION.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(ClModItems.HEAVY_BOOTS_BOOTS.get());
			tabData.accept(ClModItems.GINGERBREAD.get());
			tabData.accept(ClModItems.GINGERBREAD_CANNON.get());
			tabData.accept(ClModItems.GINGERBREAD_LONGSWORD.get());
			tabData.accept(ClModItems.BROWN_SUGAR_SWORD.get());
			tabData.accept(ClModItems.DEAD_JELLY_SNAKE.get());
			tabData.accept(ClModItems.JELLY_BAZOOKA.get());
			tabData.accept(ClModItems.AK_47.get());
			tabData.accept(ClModItems.BULLET.get());
			tabData.accept(ClModItems.TOURMALINE_SWORD.get());
			tabData.accept(ClModItems.TOURMALINE_HELMET.get());
			tabData.accept(ClModItems.TOURMALINE_CHESTPLATE.get());
			tabData.accept(ClModItems.TOURMALINE_LEGGINGS.get());
			tabData.accept(ClModItems.TOURMALINE_BOOTS.get());
			tabData.accept(ClModItems.WIZARD_STAFF.get());
			tabData.accept(ClModItems.MUSHROOM_SWORD.get());
			tabData.accept(ClModItems.MUSHROOM_SHIELD.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			tabData.accept(ClModItems.BROWN_SUGAR_BRICK.get());
			tabData.accept(ClModItems.TOURMALINE_GEM.get());
			tabData.accept(ClModItems.JADE.get());
			tabData.accept(ClModItems.MONEY.get());
			tabData.accept(ClModItems.COIN.get());
			tabData.accept(ClModItems.EMPTY_ICE_CREAM_CONE.get());
			tabData.accept(ClModItems.SHADOW_SHARD.get());
			tabData.accept(ClModItems.ENT_HEART.get());
			tabData.accept(ClModBlocks.JAR.get().asItem());
			tabData.accept(ClModBlocks.FAIRY_JAR.get().asItem());
			tabData.accept(ClModItems.FIRE_BOOK.get());
			tabData.accept(ClModItems.WATER_BOOK.get());
			tabData.accept(ClModItems.AIR_BOOK.get());
			tabData.accept(ClModItems.EARTH_BOOK.get());
			tabData.accept(ClModItems.POISON_BOOK.get());
			tabData.accept(ClModItems.LIGHTNING_BOOK.get());
		}
	}
}
