
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import net.mcreator.countries.world.inventory.ChocolateFactoryGUIMenu;
import net.mcreator.countries.ClMod;

public class ClModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ClMod.MODID);
	public static final RegistryObject<MenuType<ChocolateFactoryGUIMenu>> CHOCOLATE_FACTORY_GUI = REGISTRY.register("chocolate_factory_gui", () -> IForgeMenuType.create(ChocolateFactoryGUIMenu::new));
}
