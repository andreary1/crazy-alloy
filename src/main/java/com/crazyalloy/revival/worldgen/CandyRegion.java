package com.crazyalloy.revival.worldgen;

import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

/**
 * A TerraBlender region identical to vanilla except that forests become Sweet Forests and plains become
 * Jelly Bean Fields (each can be switched off in the config). Only chunks generated after installation are
 * affected; existing terrain is never rewritten.
 */
public class CandyRegion extends Region {
    private final boolean sweetForest;
    private final boolean jellyBeanFields;

    public CandyRegion(Identifier name, int weight, boolean sweetForest, boolean jellyBeanFields) {
        super(name, RegionType.OVERWORLD, weight);
        this.sweetForest = sweetForest;
        this.jellyBeanFields = jellyBeanFields;
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            if (sweetForest) {
                builder.replaceBiome(Biomes.FOREST, ModWorldgen.SWEET_FOREST);
                builder.replaceBiome(Biomes.FLOWER_FOREST, ModWorldgen.SWEET_FOREST);
            }
            if (jellyBeanFields) {
                builder.replaceBiome(Biomes.PLAINS, ModWorldgen.JELLY_BEAN_FIELDS);
                builder.replaceBiome(Biomes.SUNFLOWER_PLAINS, ModWorldgen.JELLY_BEAN_FIELDS);
            }
        });
    }
}
