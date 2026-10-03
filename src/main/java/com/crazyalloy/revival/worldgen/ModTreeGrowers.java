package com.crazyalloy.revival.worldgen;

import com.crazyalloy.revival.CrazyAlloyRevival;
import java.util.Optional;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class ModTreeGrowers {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SWEETWOOD_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, CrazyAlloyRevival.id("sweetwood_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_SWEETWOOD_TREE =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, CrazyAlloyRevival.id("tall_sweetwood_tree"));

    public static final TreeGrower SWEETWOOD = new TreeGrower(
            CrazyAlloyRevival.MOD_ID + ":sweetwood",
            0.15F,
            Optional.empty(), Optional.empty(),
            Optional.of(SWEETWOOD_TREE), Optional.of(TALL_SWEETWOOD_TREE),
            Optional.empty(), Optional.empty());

    private ModTreeGrowers() {}
}
