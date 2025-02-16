
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
		if (tabData.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			tabData.accept(ClModBlocks.GUMMY_GRASS.get().asItem());
			tabData.accept(ClModBlocks.GUMMY_DIRT.get().asItem());
			tabData.accept(ClModBlocks.ROCK_CANDY.get().asItem());
			tabData.accept(ClModBlocks.CANDY_LEAVES.get().asItem());
		} else if (tabData.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			tabData.accept(ClModItems.MELTED_CHOCOLATE_BUCKET.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			tabData.accept(ClModItems.CANDY_TUBE_DOG_SPAWN_EGG.get());
			tabData.accept(ClModItems.JELLY_SHARK_SPAWN_EGG.get());
			tabData.accept(ClModItems.LOLLIPOP_GUY_SPAWN_EGG.get());
			tabData.accept(ClModItems.BROWN_SUGAR_RHINO_SPAWN_EGG.get());
			tabData.accept(ClModItems.GINGERBREAD_SOLDIER_SPAWN_EGG.get());
			tabData.accept(ClModItems.COTTON_CANDY_TORNADO_SPAWN_EGG.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
			tabData.accept(ClModItems.LOLLIPOP.get());
		} else if (tabData.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			tabData.accept(ClModBlocks.CANDYLOG.get().asItem());
			tabData.accept(ClModBlocks.GINGERB.get().asItem());
			tabData.accept(ClModBlocks.GINGERBREAD_BLOCK.get().asItem());
			tabData.accept(ClModBlocks.CANDY_PLANKS.get().asItem());
		}
	}
}
