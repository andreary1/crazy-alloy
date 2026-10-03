package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CrazyAlloyRevival.MOD_ID);

    public static final Supplier<CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.crazyalloy_revival"))
            .icon(() -> new ItemStack(ModItems.LOLLIPOP.get()))
            .displayItems((params, output) -> ModItems.TAB_ORDER.forEach(item -> output.accept(item.get())))
            .build());

    private ModCreativeTabs() {}
}
