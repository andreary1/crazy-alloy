package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.entity.BrownSugarRhino;
import com.crazyalloy.revival.entity.Bubblegum;
import com.crazyalloy.revival.entity.BubbalooCreeper;
import com.crazyalloy.revival.entity.CandyTubeDog;
import com.crazyalloy.revival.entity.CottonCandyTornado;
import com.crazyalloy.revival.entity.GingerbreadKing;
import com.crazyalloy.revival.entity.GingerbreadSoldier;
import com.crazyalloy.revival.entity.GingerbreadWarrior;
import com.crazyalloy.revival.entity.GrapeSpider;
import com.crazyalloy.revival.entity.JellyBunny;
import com.crazyalloy.revival.entity.JellyShark;
import com.crazyalloy.revival.entity.JellySnake;
import com.crazyalloy.revival.entity.LollipopGuy;
import com.crazyalloy.revival.entity.RollCakeMonster;
import com.crazyalloy.revival.entity.projectile.BrownSugarBrickEntity;
import com.crazyalloy.revival.entity.projectile.GumdropShot;
import com.crazyalloy.revival.entity.projectile.JellySnakeShot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModEntities {
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(CrazyAlloyRevival.MOD_ID);

    // --- Sweet Forest -----------------------------------------------------------------------
    public static final DeferredHolder<EntityType<?>, EntityType<CandyTubeDog>> CANDY_TUBE_DOG = ENTITIES.registerEntityType(
            "candy_tube_dog", CandyTubeDog::new, MobCategory.CREATURE, b -> b.sized(0.6F, 0.8F).eyeHeight(0.6F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<LollipopGuy>> LOLLIPOP_GUY = ENTITIES.registerEntityType(
            "lollipop_guy", LollipopGuy::new, MobCategory.CREATURE, b -> b.sized(0.6F, 1.9F).eyeHeight(1.2F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<BrownSugarRhino>> BROWN_SUGAR_RHINO = ENTITIES.registerEntityType(
            "brown_sugar_rhino", BrownSugarRhino::new, MobCategory.CREATURE, b -> b.sized(1.4F, 1.4F).eyeHeight(1.0F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<CottonCandyTornado>> COTTON_CANDY_TORNADO = ENTITIES.registerEntityType(
            "cotton_candy_tornado", CottonCandyTornado::new, MobCategory.MONSTER, b -> b.sized(0.9F, 1.8F).eyeHeight(1.5F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<Bubblegum>> BUBBLEGUM = ENTITIES.registerEntityType(
            "bubblegum", Bubblegum::new, MobCategory.MONSTER, b -> b.sized(0.7F, 0.7F).eyeHeight(0.4F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<GingerbreadWarrior>> GINGERBREAD_WARRIOR = ENTITIES.registerEntityType(
            "gingerbread_warrior", GingerbreadWarrior::new, MobCategory.MONSTER, b -> b.sized(0.6F, 1.45F).eyeHeight(1.2F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<GingerbreadSoldier>> GINGERBREAD_SOLDIER = ENTITIES.registerEntityType(
            "gingerbread_soldier", GingerbreadSoldier::new, MobCategory.MONSTER, b -> b.sized(0.6F, 1.45F).eyeHeight(1.2F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<GingerbreadKing>> GINGERBREAD_KING = ENTITIES.registerEntityType(
            "gingerbread_king", GingerbreadKing::new, MobCategory.MONSTER, b -> b.sized(1.8F, 3.4F).eyeHeight(2.6F).clientTrackingRange(10).fireImmune());

    // --- Jelly Bean Fields ------------------------------------------------------------------
    public static final DeferredHolder<EntityType<?>, EntityType<GrapeSpider>> GRAPE_SPIDER = ENTITIES.registerEntityType(
            "grape_spider", GrapeSpider::new, MobCategory.MONSTER, b -> b.sized(1.0F, 0.65F).eyeHeight(0.45F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<JellyBunny>> JELLY_BUNNY = ENTITIES.registerEntityType(
            "jelly_bunny", JellyBunny::new, MobCategory.CREATURE, b -> b.sized(0.45F, 0.6F).eyeHeight(0.45F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<JellySnake>> JELLY_SNAKE = ENTITIES.registerEntityType(
            "jelly_snake", JellySnake::new, MobCategory.MONSTER, b -> b.sized(0.6F, 0.35F).eyeHeight(0.2F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<JellyShark>> JELLY_SHARK = ENTITIES.registerEntityType(
            "jelly_shark", JellyShark::new, MobCategory.MONSTER, b -> b.sized(1.5F, 0.95F).eyeHeight(0.5F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<RollCakeMonster>> ROLL_CAKE_MONSTER = ENTITIES.registerEntityType(
            "roll_cake_monster", RollCakeMonster::new, MobCategory.MONSTER, b -> b.sized(0.8F, 2.3F).eyeHeight(2.0F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<BubbalooCreeper>> BUBBALOO_CREEPER = ENTITIES.registerEntityType(
            "bubbaloo_creeper", BubbalooCreeper::new, MobCategory.MONSTER, b -> b.sized(0.6F, 1.7F).clientTrackingRange(8));

    // --- Projectiles ------------------------------------------------------------------------
    public static final DeferredHolder<EntityType<?>, EntityType<BrownSugarBrickEntity>> BROWN_SUGAR_BRICK = ENTITIES.registerEntityType(
            "brown_sugar_brick", BrownSugarBrickEntity::new, MobCategory.MISC, b -> b.noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
    public static final DeferredHolder<EntityType<?>, EntityType<GumdropShot>> GUMDROP_SHOT = ENTITIES.registerEntityType(
            "gumdrop_shot", GumdropShot::new, MobCategory.MISC, b -> b.noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
    public static final DeferredHolder<EntityType<?>, EntityType<JellySnakeShot>> JELLY_SNAKE_SHOT = ENTITIES.registerEntityType(
            "jelly_snake_shot", JellySnakeShot::new, MobCategory.MISC, b -> b.noLootTable().sized(0.35F, 0.35F).clientTrackingRange(4).updateInterval(10));

    private ModEntities() {}
}
