package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.menu.ChocolateFactoryMenu;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, CrazyAlloyRevival.MOD_ID);

    public static final Supplier<MenuType<ChocolateFactoryMenu>> CHOCOLATE_FACTORY = MENUS.register("chocolate_factory",
            () -> IMenuTypeExtension.create(ChocolateFactoryMenu::clientSide));

    private ModMenus() {}
}
