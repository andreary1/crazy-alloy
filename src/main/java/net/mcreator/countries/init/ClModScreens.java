
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.mcreator.countries.client.gui.WizardGUIScreen;
import net.mcreator.countries.client.gui.SpellsGUIScreen;
import net.mcreator.countries.client.gui.MineralConverterGUIScreen;
import net.mcreator.countries.client.gui.IceCreamMachineGUIScreen;
import net.mcreator.countries.client.gui.ChocolateFactoryGUIScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(ClModMenus.CHOCOLATE_FACTORY_GUI.get(), ChocolateFactoryGUIScreen::new);
			MenuScreens.register(ClModMenus.ICE_CREAM_MACHINE_GUI.get(), IceCreamMachineGUIScreen::new);
			MenuScreens.register(ClModMenus.MINERAL_CONVERTER_GUI.get(), MineralConverterGUIScreen::new);
			MenuScreens.register(ClModMenus.SPELLS_GUI.get(), SpellsGUIScreen::new);
			MenuScreens.register(ClModMenus.WIZARD_GUI.get(), WizardGUIScreen::new);
		});
	}
}
