package com.crazyalloy.revival.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Common config (shared by client and dedicated server, loaded before world data packs so that
 * world generation conditions can read it).
 */
public final class RevivalConfig {
    public static final ModConfigSpec SPEC;

    // World generation
    public static final ModConfigSpec.IntValue CANDY_REGION_WEIGHT;
    public static final ModConfigSpec.BooleanValue SWEET_FOREST_ENABLED;
    public static final ModConfigSpec.BooleanValue JELLY_BEAN_FIELDS_ENABLED;
    public static final ModConfigSpec.EnumValue<StructureFrequency> GINGERBREAD_TOWER_FREQUENCY;
    public static final ModConfigSpec.IntValue TOURMALINE_VEINS_PER_CHUNK;
    public static final ModConfigSpec.IntValue DEEP_TOURMALINE_VEINS_PER_CHUNK;
    public static final ModConfigSpec.EnumValue<StructureFrequency> COOKIE_HUT_FREQUENCY;
    public static final ModConfigSpec.BooleanValue GINGERBREAD_KING_IN_TOWERS;

    // Creature spawning
    public static final ModConfigSpec.DoubleValue CANDY_TUBE_DOG_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue LOLLIPOP_GUY_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue BROWN_SUGAR_RHINO_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue COTTON_CANDY_TORNADO_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue BUBBLEGUM_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue JELLY_BUNNY_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue JELLY_SNAKE_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue JELLY_SHARK_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue ROLL_CAKE_MONSTER_SPAWN_CHANCE;
    public static final ModConfigSpec.DoubleValue BUBBALOO_CREEPER_SPAWN_CHANCE;

    // Difficulty
    public static final ModConfigSpec.DoubleValue MOB_HEALTH_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MOB_DAMAGE_MULTIPLIER;
    public static final ModConfigSpec.IntValue GRAPE_SPIDER_POISON_SECONDS;
    public static final ModConfigSpec.IntValue JELLY_SNAKE_POISON_SECONDS;
    public static final ModConfigSpec.DoubleValue BUBBLEGUM_EXPLOSION_POWER;
    public static final ModConfigSpec.DoubleValue ORANGE_JELLY_EXPLOSION_POWER;
    public static final ModConfigSpec.IntValue GINGERBREAD_KING_HEALTH;
    public static final ModConfigSpec.IntValue GINGERBREAD_KING_MAX_GUARDS;

    // Machines
    public static final ModConfigSpec.DoubleValue CHOCOLATE_FACTORY_SPEED;

    static {
        ModConfigSpec.Builder b = new ModConfigSpec.Builder();

        b.comment("World generation. Changes only affect chunks generated after the change.").translation("crazyalloy_revival.configuration.worldgen").push("worldgen");
        CANDY_REGION_WEIGHT = b
                .comment("Weight of the Crazy Alloy TerraBlender region. Inside it, forests become Sweet Forests and plains become Jelly Bean Fields. 0 disables both biomes in new worlds. Vanilla regions use 10.")
                .translation("crazyalloy_revival.configuration.candyRegionWeight")
                .worldRestart()
                .defineInRange("candyRegionWeight", 3, 0, 20);
        SWEET_FOREST_ENABLED = b
                .comment("Generate Sweet Forests in the candy region.")
                .translation("crazyalloy_revival.configuration.sweetForestEnabled")
                .worldRestart()
                .define("sweetForestEnabled", true);
        JELLY_BEAN_FIELDS_ENABLED = b
                .comment("Generate Jelly Bean Fields in the candy region.")
                .translation("crazyalloy_revival.configuration.jellyBeanFieldsEnabled")
                .worldRestart()
                .define("jellyBeanFieldsEnabled", true);
        TOURMALINE_VEINS_PER_CHUNK = b
                .comment("Tourmaline ore veins per chunk between Y=-16 and Y=48 in the Overworld.")
                .translation("crazyalloy_revival.configuration.tourmalineVeinsPerChunk")
                .defineInRange("tourmalineVeinsPerChunk", 4, 0, 32);
        DEEP_TOURMALINE_VEINS_PER_CHUNK = b
                .comment("Extra, larger tourmaline veins per chunk below Y=0.")
                .translation("crazyalloy_revival.configuration.deepTourmalineVeinsPerChunk")
                .defineInRange("deepTourmalineVeinsPerChunk", 2, 0, 32);
        COOKIE_HUT_FREQUENCY = b
                .comment("How often Cookie Huts appear in Sweet Forests. DISABLED removes them from new chunks.")
                .translation("crazyalloy_revival.configuration.cookieHutFrequency")
                .worldRestart()
                .defineEnum("cookieHutFrequency", StructureFrequency.NORMAL);
        GINGERBREAD_TOWER_FREQUENCY = b
                .comment("How often Gingerbread Towers appear in Sweet Forests. DISABLED removes them from new chunks.")
                .translation("crazyalloy_revival.configuration.gingerbreadTowerFrequency")
                .worldRestart()
                .defineEnum("gingerbreadTowerFrequency", StructureFrequency.NORMAL);
        GINGERBREAD_KING_IN_TOWERS = b
                .comment("Each newly generated Gingerbread Tower has a Gingerbread King on its roof. If false, kings placed by towers vanish when first loaded; the spawn egg and /summon still work.")
                .translation("crazyalloy_revival.configuration.gingerbreadKingInTowers")
                .define("gingerbreadKingInTowers", true);
        b.pop();

        b.comment("Creature spawning. Each value is the chance (0 to 1) that a natural spawn attempt is allowed.").translation("crazyalloy_revival.configuration.spawns").push("spawns");
        CANDY_TUBE_DOG_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.candyTubeDogSpawnChance")
                .defineInRange("candyTubeDogSpawnChance", 1.0, 0.0, 1.0);
        LOLLIPOP_GUY_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.lollipopGuySpawnChance")
                .defineInRange("lollipopGuySpawnChance", 1.0, 0.0, 1.0);
        BROWN_SUGAR_RHINO_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.brownSugarRhinoSpawnChance")
                .defineInRange("brownSugarRhinoSpawnChance", 1.0, 0.0, 1.0);
        COTTON_CANDY_TORNADO_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.cottonCandyTornadoSpawnChance")
                .defineInRange("cottonCandyTornadoSpawnChance", 1.0, 0.0, 1.0);
        BUBBLEGUM_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.bubblegumSpawnChance")
                .defineInRange("bubblegumSpawnChance", 1.0, 0.0, 1.0);
        JELLY_BUNNY_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.jellyBunnySpawnChance")
                .defineInRange("jellyBunnySpawnChance", 1.0, 0.0, 1.0);
        JELLY_SNAKE_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.jellySnakeSpawnChance")
                .defineInRange("jellySnakeSpawnChance", 1.0, 0.0, 1.0);
        JELLY_SHARK_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.jellySharkSpawnChance")
                .defineInRange("jellySharkSpawnChance", 1.0, 0.0, 1.0);
        ROLL_CAKE_MONSTER_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.rollCakeMonsterSpawnChance")
                .defineInRange("rollCakeMonsterSpawnChance", 1.0, 0.0, 1.0);
        BUBBALOO_CREEPER_SPAWN_CHANCE = b
                .translation("crazyalloy_revival.configuration.bubbalooCreeperSpawnChance")
                .defineInRange("bubbalooCreeperSpawnChance", 1.0, 0.0, 1.0);
        b.pop();

        b.comment("Difficulty of Crazy Alloy creatures, applied when they spawn.").translation("crazyalloy_revival.configuration.difficulty").push("difficulty");
        MOB_HEALTH_MULTIPLIER = b
                .translation("crazyalloy_revival.configuration.mobHealthMultiplier")
                .defineInRange("mobHealthMultiplier", 1.0, 0.25, 4.0);
        MOB_DAMAGE_MULTIPLIER = b
                .translation("crazyalloy_revival.configuration.mobDamageMultiplier")
                .defineInRange("mobDamageMultiplier", 1.0, 0.25, 4.0);
        GRAPE_SPIDER_POISON_SECONDS = b
                .comment("Seconds of Poison applied by Grape Spider bites on Normal difficulty (doubled on Hard, none on Easy).")
                .translation("crazyalloy_revival.configuration.grapeSpiderPoisonSeconds")
                .defineInRange("grapeSpiderPoisonSeconds", 4, 0, 30);
        JELLY_SNAKE_POISON_SECONDS = b
                .comment("Seconds of Poison II applied by Jelly Snake bites (the original used Poison III for 6 seconds).")
                .translation("crazyalloy_revival.configuration.jellySnakePoisonSeconds")
                .defineInRange("jellySnakePoisonSeconds", 5, 0, 30);
        BUBBLEGUM_EXPLOSION_POWER = b
                .comment("Power of the pop when a Bubblegum dies (creeper = 3, TNT = 4, original mod = 4). 0 disables it. Follows the mobGriefing game rule.")
                .translation("crazyalloy_revival.configuration.bubblegumExplosionPower")
                .defineInRange("bubblegumExplosionPower", 2.0, 0.0, 4.0);
        ORANGE_JELLY_EXPLOSION_POWER = b
                .comment("Power of the explosion when a player walks on an Orange Jelly Bean Block (original mod = 4). 0 disables it.")
                .translation("crazyalloy_revival.configuration.orangeJellyExplosionPower")
                .defineInRange("orangeJellyExplosionPower", 2.0, 0.0, 4.0);
        GINGERBREAD_KING_HEALTH = b
                .comment("Base health of the Gingerbread King (before mobHealthMultiplier). Applies to kings spawned after the change.")
                .translation("crazyalloy_revival.configuration.gingerbreadKingHealth")
                .defineInRange("gingerbreadKingHealth", 250, 20, 2000);
        GINGERBREAD_KING_MAX_GUARDS = b
                .comment("The Gingerbread King stops calling soldiers while this many Gingerbread Warriors and Soldiers are within 16 blocks. 0 disables the summon.")
                .translation("crazyalloy_revival.configuration.gingerbreadKingMaxGuards")
                .defineInRange("gingerbreadKingMaxGuards", 4, 0, 12);
        b.pop();

        b.translation("crazyalloy_revival.configuration.machines").push("machines");
        CHOCOLATE_FACTORY_SPEED = b
                .comment("Processing speed multiplier for the Chocolate Factory.")
                .translation("crazyalloy_revival.configuration.chocolateFactorySpeed")
                .defineInRange("chocolateFactorySpeed", 1.0, 0.25, 4.0);
        b.pop();

        SPEC = b.build();
    }

    private RevivalConfig() {}

    public enum StructureFrequency {
        DISABLED,
        RARE,
        NORMAL,
        COMMON
    }
}
