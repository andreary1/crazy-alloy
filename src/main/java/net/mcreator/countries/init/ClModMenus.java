
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;

import net.mcreator.countries.world.inventory.WizardGUIMenu;
import net.mcreator.countries.world.inventory.SpellsGUIMenu;
import net.mcreator.countries.world.inventory.MineralConverterGUIMenu;
import net.mcreator.countries.world.inventory.IceCreamMachineGUIMenu;
import net.mcreator.countries.world.inventory.ChocolateFactoryGUIMenu;
import net.mcreator.countries.ClMod;

public class ClModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ClMod.MODID);
	public static final RegistryObject<MenuType<ChocolateFactoryGUIMenu>> CHOCOLATE_FACTORY_GUI = REGISTRY.register("chocolate_factory_gui", () -> IForgeMenuType.create(ChocolateFactoryGUIMenu::new));
	public static final RegistryObject<MenuType<IceCreamMachineGUIMenu>> ICE_CREAM_MACHINE_GUI = REGISTRY.register("ice_cream_machine_gui", () -> IForgeMenuType.create(IceCreamMachineGUIMenu::new));
	public static final RegistryObject<MenuType<MineralConverterGUIMenu>> MINERAL_CONVERTER_GUI = REGISTRY.register("mineral_converter_gui", () -> IForgeMenuType.create(MineralConverterGUIMenu::new));
	public static final RegistryObject<MenuType<SpellsGUIMenu>> SPELLS_GUI = REGISTRY.register("spells_gui", () -> IForgeMenuType.create(SpellsGUIMenu::new));
	public static final RegistryObject<MenuType<WizardGUIMenu>> WIZARD_GUI = REGISTRY.register("wizard_gui", () -> IForgeMenuType.create(WizardGUIMenu::new));
}
