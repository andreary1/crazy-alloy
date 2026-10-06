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
 * Jelly Bean Fields (each can be switched off in the config), and the ground under both becomes a Candy Cave.
 * Only chunks generated after installation are affected; existing terrain is never rewritten.
 */
public class CandyRegion extends Region {
    /** Vanilla places surface biomes at depth 0 (and 1); underground biomes such as Lush Caves use this depth span. */
    private static final Climate.Parameter SURFACE_DEPTH = Climate.Parameter.point(0.0F);
    private static final Climate.Parameter CAVE_DEPTH = Climate.Parameter.span(0.2F, 1.0F);
    /**
     * Small fitness penalty on the underground twins, so vanilla cave biomes (offset 0) still win where their climate
     * matches, and the surface point still wins in the first blocks under the ground.
     */
    private static final float UNDERGROUND_OFFSET = 0.1F;

    private final boolean sweetForest;
    private final boolean jellyBeanFields;
    private final boolean candyCave;

    public CandyRegion(Identifier name, int weight, boolean sweetForest, boolean jellyBeanFields, boolean candyCave) {
        super(name, RegionType.OVERWORLD, weight);
        this.sweetForest = sweetForest;
        this.jellyBeanFields = jellyBeanFields;
        this.candyCave = candyCave;
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(pair -> {
            mapper.accept(pair);
            if (!candyCave) {
                return;
            }
            // Every surface point gets an underground twin with the same climate at cave depth: the Candy Cave under
            // Sweet Forests and Jelly Bean Fields, the same biome under everything else. With the twins, the biome
            // underground is decided by climate alone (as vanilla does it), so the cave stays below the candy
            // biomes instead of winning wherever the surface points are a depth mismatch away.
            ResourceKey<Biome> biome = pair.getSecond();
            Climate.ParameterPoint p = pair.getFirst();
            if (p.depth().equals(SURFACE_DEPTH)) {
                boolean candy = biome == ModWorldgen.SWEET_FOREST || biome == ModWorldgen.JELLY_BEAN_FIELDS;
                mapper.accept(Pair.of(Climate.parameters(p.temperature(), p.humidity(), p.continentalness(), p.erosion(),
                        CAVE_DEPTH, p.weirdness(), UNDERGROUND_OFFSET), candy ? ModWorldgen.CANDY_CAVE : biome));
            }
        }, builder -> {
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
