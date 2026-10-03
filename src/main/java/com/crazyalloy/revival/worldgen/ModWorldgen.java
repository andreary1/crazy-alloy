package com.crazyalloy.revival.worldgen;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public final class ModWorldgen {
    public static final ResourceKey<Biome> SWEET_FOREST = ResourceKey.create(Registries.BIOME, CrazyAlloyRevival.id("sweet_forest"));
    public static final ResourceKey<Biome> JELLY_BEAN_FIELDS = ResourceKey.create(Registries.BIOME, CrazyAlloyRevival.id("jelly_bean_fields"));

    private ModWorldgen() {}

    /** Called on the main thread during common setup. Adds the candy region and the surface of its biomes. */
    public static void registerTerraBlender() {
        int weight = RevivalConfig.CANDY_REGION_WEIGHT.get();
        boolean sweet = RevivalConfig.SWEET_FOREST_ENABLED.get();
        boolean jelly = RevivalConfig.JELLY_BEAN_FIELDS_ENABLED.get();
        if (weight > 0 && (sweet || jelly)) {
            Regions.register(new CandyRegion(CrazyAlloyRevival.id("overworld_candy"), weight, sweet, jelly));
        } else {
            CrazyAlloyRevival.LOGGER.info("Crazy Alloy candy region disabled by config");
        }
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, CrazyAlloyRevival.MOD_ID,
                SurfaceRules.sequence(sweetForestSurface(), jellyBeanFieldsSurface()));
    }

    /** Gummy Grass on top of Chocolate Dirt. */
    private static SurfaceRules.RuleSource sweetForestSurface() {
        SurfaceRules.RuleSource grass = SurfaceRules.state(ModBlocks.CHOCOLATE_GRASS_BLOCK.get().defaultBlockState());
        SurfaceRules.RuleSource soil = SurfaceRules.state(ModBlocks.CHOCOLATE_SOIL.get().defaultBlockState());
        SurfaceRules.ConditionSource aboveWater = SurfaceRules.waterBlockCheck(-1, 0);

        return SurfaceRules.ifTrue(SurfaceRules.isBiome(SWEET_FOREST), SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(SurfaceRules.ifTrue(aboveWater, grass), soil)),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, soil)));
    }

    /** Green jelly on top, a yellow layer below it, red jelly at the bottom of lakes and rivers. */
    private static SurfaceRules.RuleSource jellyBeanFieldsSurface() {
        SurfaceRules.RuleSource green = SurfaceRules.state(ModBlocks.GREEN_JELLY_BEAN_BLOCK.get().defaultBlockState());
        SurfaceRules.RuleSource yellow = SurfaceRules.state(ModBlocks.YELLOW_JELLY_BEAN_BLOCK.get().defaultBlockState());
        SurfaceRules.RuleSource red = SurfaceRules.state(ModBlocks.RED_JELLY_BEAN_BLOCK.get().defaultBlockState());
        SurfaceRules.ConditionSource aboveWater = SurfaceRules.waterBlockCheck(-1, 0);

        return SurfaceRules.ifTrue(SurfaceRules.isBiome(JELLY_BEAN_FIELDS), SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.sequence(SurfaceRules.ifTrue(aboveWater, green), red)),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.sequence(SurfaceRules.ifTrue(aboveWater, yellow), red))));
    }
}
