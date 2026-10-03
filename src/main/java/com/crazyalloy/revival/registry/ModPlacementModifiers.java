package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.worldgen.ConfigCountPlacement;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, CrazyAlloyRevival.MOD_ID);

    public static final Supplier<PlacementModifierType<ConfigCountPlacement>> CONFIG_COUNT =
            PLACEMENT_MODIFIERS.register("config_count", () -> () -> ConfigCountPlacement.CODEC);

    private ModPlacementModifiers() {}
}
