package com.crazyalloy.revival.gametest;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.block.entity.ChocolateFactoryBlockEntity;
import com.crazyalloy.revival.entity.CandyTubeDog;
import com.crazyalloy.revival.entity.GingerbreadKing;
import com.crazyalloy.revival.entity.GingerbreadSoldier;
import com.crazyalloy.revival.entity.GingerbreadWarrior;
import com.crazyalloy.revival.entity.GrapeSpider;
import com.crazyalloy.revival.entity.LollipopGuy;
import com.crazyalloy.revival.registry.ModBlocks;
import com.crazyalloy.revival.registry.ModEntities;
import com.crazyalloy.revival.registry.ModItems;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import com.crazyalloy.revival.block.OrangeJellyBeanBlock;
import com.crazyalloy.revival.entity.Bubblegum;
import com.crazyalloy.revival.entity.CottonCandyTornado;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

/**
 * Headless game tests for stage 1. They live in their own source set, so they are loaded by the dev
 * runs (runGameTestServer, runServer, runClient) but are not packaged into the release JAR.
 */
@EventBusSubscriber(modid = CrazyAlloyRevival.MOD_ID)
public final class RevivalGameTests {
    private static final Identifier PLATFORM = CrazyAlloyRevival.id("gametest/platform");
    private static final Map<String, Consumer<GameTestHelper>> TESTS = new LinkedHashMap<>();

    static {
        TESTS.put("mobs_tick_without_crashing", RevivalGameTests::mobsTickWithoutCrashing);
        TESTS.put("candy_tube_dog_tames_with_lollipops", RevivalGameTests::candyTubeDogTames);
        TESTS.put("lollipop_guy_trades_sugar_for_lollipop", RevivalGameTests::lollipopGuyTrades);
        TESTS.put("grape_spider_poisons_on_hit", RevivalGameTests::grapeSpiderPoisons);
        TESTS.put("chocolate_factory_makes_chocolate_bar", RevivalGameTests::chocolateFactoryWorks);
        TESTS.put("sweetwood_sapling_grows_on_chocolate_soil", RevivalGameTests::saplingGrows);
        TESTS.put("stage2_mobs_tick_without_crashing", RevivalGameTests::stage2MobsTick);
        TESTS.put("yellow_jelly_gives_mining_fatigue", RevivalGameTests::yellowJellyFatigue);
        TESTS.put("orange_jelly_explodes_after_fuse", RevivalGameTests::orangeJellyExplodes);
        TESTS.put("infested_jelly_releases_grape_spiders", RevivalGameTests::infestedJellySpawnsSpiders);
        TESTS.put("cotton_candy_tornado_caught_with_stick", RevivalGameTests::tornadoCatch);
        TESTS.put("bubblegum_pops_on_death", RevivalGameTests::bubblegumPops);
        TESTS.put("bubbaloo_creeper_leaves_bubbaloo", RevivalGameTests::bubbalooCreeperPuddle);
        TESTS.put("factory_turns_melted_chocolate_into_bars", RevivalGameTests::factoryMeltedChocolate);
        TESTS.put("licorice_grows_only_on_sweet_ground", RevivalGameTests::licoricePlacement);
        TESTS.put("jelly_bazooka_fires_dead_snakes", RevivalGameTests::bazookaFires);
        TESTS.put("gingerbread_warrior_fights_bare_handed", RevivalGameTests::warriorBareHanded);
        TESTS.put("stage3_mobs_tick_without_crashing", RevivalGameTests::stage3MobsTick);
        TESTS.put("gingerbread_soldier_fires_gumdrop", RevivalGameTests::soldierFires);
        TESTS.put("gingerbread_king_slam_hits_bystanders", RevivalGameTests::kingSlam);
        TESTS.put("gingerbread_king_calls_guards", RevivalGameTests::kingSummons);
        TESTS.put("stage4_mobs_tick_without_crashing", RevivalGameTests::stage4MobsTick);
        TESTS.put("grape_spider_crouches_and_pounces", RevivalGameTests::spiderPounces);
        TESTS.put("brown_sugar_rhino_scrapes_and_charges", RevivalGameTests::rhinoCharges);
        TESTS.put("ice_cream_vendor_sells_ice_cream", RevivalGameTests::vendorTrades);
        TESTS.put("fortress_template_has_king_on_throne", RevivalGameTests::fortressTemplate);
        TESTS.put("ice_cream_truck_template_has_vendor", RevivalGameTests::truckTemplate);
        TESTS.put("stage5_mobs_tick_without_crashing", RevivalGameTests::stage5MobsTick);
        TESTS.put("impostor_cake_waits_disguised_then_reveals", RevivalGameTests::impostorCakeReveals);
        TESTS.put("impostor_cake_reveals_when_hit", RevivalGameTests::impostorCakeRevealsWhenHit);
        TESTS.put("ice_cream_machine_serves_from_milk_tank", RevivalGameTests::iceCreamMachineServes);
        TESTS.put("ice_cream_truck_template_has_machine", RevivalGameTests::truckHasMachine);
        TESTS.put("stage5_registry_and_tags", RevivalGameTests::stage5Registry);
    }

    private RevivalGameTests() {}

    private static ResourceKey<Consumer<GameTestHelper>> functionKey(String name) {
        return ResourceKey.create(Registries.TEST_FUNCTION, CrazyAlloyRevival.id(name));
    }

    @SubscribeEvent
    public static void registerFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> TESTS.forEach((name, fn) -> helper.register(CrazyAlloyRevival.id(name), fn)));
    }

    // sky_access = true: without it the test area gets a barrier ceiling and the sapling has no room to grow.
    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> env = event.registerEnvironment(CrazyAlloyRevival.id("default"));
        TESTS.keySet().forEach(name -> event.registerTest(CrazyAlloyRevival.id(name),
                new FunctionGameTestInstance(functionKey(name), new TestData<>(env, PLATFORM, 400, 0, true, Rotation.NONE, false, 1, 1, true, 0))));
    }

    /** Regression for the missing tempt_range attribute: every mob must survive a few seconds of AI ticks. */
    private static void mobsTickWithoutCrashing(GameTestHelper helper) {
        CandyTubeDog dog = helper.spawn(ModEntities.CANDY_TUBE_DOG.get(), 2, 1, 2);
        LollipopGuy guy = helper.spawn(ModEntities.LOLLIPOP_GUY.get(), 6, 1, 2);
        GrapeSpider spider = helper.spawn(ModEntities.GRAPE_SPIDER.get(), 4, 1, 6);
        helper.runAfterDelay(100, () -> {
            helper.assertTrue(dog.isAlive() && guy.isAlive() && spider.isAlive(), "a stage 1 mob died or was removed while ticking");
            helper.succeed();
        });
    }

    private static void candyTubeDogTames(GameTestHelper helper) {
        CandyTubeDog dog = helper.spawn(ModEntities.CANDY_TUBE_DOG.get(), 4, 1, 4);
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.LOLLIPOP.get(), 64));
        // Each lollipop has a 1 in 3 chance; 60 tries fail with probability (2/3)^60, about 3e-11.
        for (int i = 0; i < 60 && !dog.isTame(); i++) {
            dog.mobInteract(player, InteractionHand.MAIN_HAND);
        }
        helper.assertTrue(dog.isTame(), "dog was not tamed after 60 lollipops");
        helper.assertTrue(dog.isOwnedBy(player), "dog is not owned by the player who tamed it");
        helper.assertTrue(dog.getMaxHealth() == 30.0F, "tamed dog max health should be 30, was " + dog.getMaxHealth());
        helper.succeed();
    }

    private static void lollipopGuyTrades(GameTestHelper helper) {
        LollipopGuy guy = helper.spawn(ModEntities.LOLLIPOP_GUY.get(), 4, 1, 4);
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.SUGAR, 4));
        guy.interact(player, InteractionHand.MAIN_HAND, Vec3.ZERO);
        helper.succeedWhen(() -> helper.assertItemEntityPresent(ModItems.LOLLIPOP.get()));
    }

    private static void grapeSpiderPoisons(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        GrapeSpider spider = helper.spawn(ModEntities.GRAPE_SPIDER.get(), 4, 1, 4);
        Pig pig = helper.spawn(EntityType.PIG, 5, 1, 4);
        helper.assertTrue(spider.doHurtTarget(helper.getLevel(), pig), "grape spider attack did not land");
        helper.assertTrue(pig.hasEffect(MobEffects.POISON), "grape spider hit did not apply poison on Normal");
        helper.succeed();
    }

    private static void chocolateFactoryWorks(GameTestHelper helper) {
        BlockPos pos = new BlockPos(4, 1, 4);
        helper.setBlock(pos, ModBlocks.CHOCOLATE_FACTORY.get());
        ChocolateFactoryBlockEntity factory = helper.getBlockEntity(pos, ChocolateFactoryBlockEntity.class);
        factory.setItem(ChocolateFactoryBlockEntity.SLOT_INPUT_A, new ItemStack(ModItems.COCOA_POWDER.get()));
        factory.setItem(ChocolateFactoryBlockEntity.SLOT_INPUT_B, new ItemStack(Items.SUGAR));
        factory.setItem(ChocolateFactoryBlockEntity.SLOT_FUEL, new ItemStack(Items.COAL));
        // Default recipe time is 200 ticks; the test allows 400.
        helper.succeedWhen(() -> {
            ItemStack out = factory.getItem(ChocolateFactoryBlockEntity.SLOT_OUTPUT);
            helper.assertTrue(out.is(ModItems.CHOCOLATE_BAR.get()), "no chocolate bar in the output slot yet");
            helper.assertTrue(factory.getItem(ChocolateFactoryBlockEntity.SLOT_INPUT_A).isEmpty(), "cocoa powder was not consumed");
            helper.assertTrue(factory.getItem(ChocolateFactoryBlockEntity.SLOT_INPUT_B).isEmpty(), "sugar was not consumed");
        });
    }

    private static void saplingGrows(GameTestHelper helper) {
        BlockPos soil = new BlockPos(4, 0, 4);
        BlockPos sapling = soil.above();
        helper.setBlock(soil, ModBlocks.CHOCOLATE_SOIL.get());
        helper.setBlock(sapling, ModBlocks.SWEETWOOD_SAPLING.get());
        BlockPos abs = helper.absolutePos(sapling);
        for (int i = 0; i < 20 && helper.getLevel().getBlockState(abs).getBlock() instanceof SaplingBlock; i++) {
            BlockState state = helper.getLevel().getBlockState(abs);
            ((SaplingBlock) state.getBlock()).advanceTree(helper.getLevel(), abs, state, helper.getLevel().getRandom());
        }
        helper.assertBlockPresent(ModBlocks.SWEETWOOD_LOG.get(), sapling);
        helper.succeed();
    }

    // ------------------------------------------------------------------ stage 2

    private static void stage2MobsTick(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        List<? extends Mob> mobs = List.of(
                helper.spawn(ModEntities.BROWN_SUGAR_RHINO.get(), 1, 1, 1), helper.spawn(ModEntities.COTTON_CANDY_TORNADO.get(), 4, 1, 1),
                helper.spawn(ModEntities.BUBBLEGUM.get(), 7, 2, 1), helper.spawn(ModEntities.GINGERBREAD_WARRIOR.get(), 1, 1, 4),
                helper.spawn(ModEntities.GINGERBREAD_SOLDIER.get(), 4, 1, 4), helper.spawn(ModEntities.JELLY_BUNNY.get(), 7, 1, 4),
                helper.spawn(ModEntities.JELLY_SNAKE.get(), 1, 1, 7), helper.spawn(ModEntities.JELLY_SHARK.get(), 4, 1, 7),
                helper.spawn(ModEntities.ROLL_CAKE_MONSTER.get(), 7, 1, 7), helper.spawn(ModEntities.BUBBALOO_CREEPER.get(), 2, 1, 2));
        helper.runAfterDelay(100, () -> {
            for (Mob mob : mobs) {
                helper.assertTrue(mob.isAlive(), mob.getType().toShortString() + " died or was removed while ticking");
            }
            helper.succeed();
        });
    }

    private static void yellowJellyFatigue(GameTestHelper helper) {
        BlockPos pos = new BlockPos(4, 0, 4);
        helper.setBlock(pos, ModBlocks.YELLOW_JELLY_BEAN_BLOCK.get());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        BlockPos abs = helper.absolutePos(pos);
        ModBlocks.YELLOW_JELLY_BEAN_BLOCK.get().stepOn(helper.getLevel(), abs, helper.getLevel().getBlockState(abs), player);
        helper.assertTrue(player.hasEffect(MobEffects.MINING_FATIGUE), "walking on yellow jelly did not give Mining Fatigue");
        helper.succeed();
    }

    private static void orangeJellyExplodes(GameTestHelper helper) {
        BlockPos pos = new BlockPos(4, 0, 4);
        helper.setBlock(pos, ModBlocks.ORANGE_JELLY_BEAN_BLOCK.get());
        BlockPos abs = helper.absolutePos(pos);
        Player sneaking = helper.makeMockPlayer(GameType.SURVIVAL);
        sneaking.setShiftKeyDown(true);
        ModBlocks.ORANGE_JELLY_BEAN_BLOCK.get().stepOn(helper.getLevel(), abs, helper.getLevel().getBlockState(abs), sneaking);
        helper.assertFalse(helper.getLevel().getBlockState(abs).getValue(OrangeJellyBeanBlock.PRIMED), "a sneaking player primed orange jelly");
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        ModBlocks.ORANGE_JELLY_BEAN_BLOCK.get().stepOn(helper.getLevel(), abs, helper.getLevel().getBlockState(abs), player);
        helper.assertTrue(helper.getLevel().getBlockState(abs).getValue(OrangeJellyBeanBlock.PRIMED), "stepping on orange jelly did not prime it");
        helper.succeedWhen(() -> helper.assertBlockNotPresent(ModBlocks.ORANGE_JELLY_BEAN_BLOCK.get(), pos));
    }

    private static void infestedJellySpawnsSpiders(GameTestHelper helper) {
        BlockPos pos = new BlockPos(4, 1, 4);
        helper.setBlock(pos, ModBlocks.INFESTED_PURPLE_JELLY_BEAN_BLOCK.get());
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        helper.getLevel().destroyBlock(helper.absolutePos(pos), true, null);
        helper.succeedWhen(() -> helper.assertEntityPresent(ModEntities.GRAPE_SPIDER.get()));
    }

    private static void tornadoCatch(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        CottonCandyTornado tornado = helper.spawn(ModEntities.COTTON_CANDY_TORNADO.get(), 4, 1, 4);
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STICK, 2));
        tornado.interact(player, InteractionHand.MAIN_HAND, Vec3.ZERO);
        helper.assertTrue(tornado.isAlive(), "a healthy tornado was caught");
        tornado.setHealth(CottonCandyTornado.CATCHABLE_HEALTH - 1);
        tornado.interact(player, InteractionHand.MAIN_HAND, Vec3.ZERO);
        helper.assertTrue(tornado.isRemoved(), "weak tornado was not caught");
        helper.assertTrue(player.getInventory().countItem(ModItems.COTTON_CANDY.get()) == 3, "catching did not give 3 cotton candy");
        helper.succeed();
    }

    private static void bubblegumPops(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        Bubblegum gum = helper.spawn(ModEntities.BUBBLEGUM.get(), 4, 1, 4);
        Pig pig = helper.spawn(EntityType.PIG, 5, 1, 4);
        gum.kill(helper.getLevel());
        helper.succeedWhen(() -> helper.assertTrue(pig.getHealth() < pig.getMaxHealth() || !pig.isAlive(), "the pop did not hurt the pig next to it"));
    }

    private static void bubbalooCreeperPuddle(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        Mob creeper = helper.spawn(ModEntities.BUBBALOO_CREEPER.get(), 4, 1, 4);
        helper.runAfterDelay(5, () -> {
            BlockPos at = creeper.blockPosition();
            creeper.kill(helper.getLevel());
            helper.succeedWhen(() -> helper.assertTrue(helper.getLevel().getBlockState(at).is(ModBlocks.BUBBALOO.get()),
                    "no Bubbaloo where the creeper died, found " + helper.getLevel().getBlockState(at)));
        });
    }

    private static void factoryMeltedChocolate(GameTestHelper helper) {
        BlockPos pos = new BlockPos(4, 1, 4);
        helper.setBlock(pos, ModBlocks.CHOCOLATE_FACTORY.get());
        ChocolateFactoryBlockEntity factory = helper.getBlockEntity(pos, ChocolateFactoryBlockEntity.class);
        factory.setItem(ChocolateFactoryBlockEntity.SLOT_INPUT_A, new ItemStack(ModItems.MELTED_CHOCOLATE_BUCKET.get()));
        factory.setItem(ChocolateFactoryBlockEntity.SLOT_FUEL, new ItemStack(Items.COAL));
        helper.succeedWhen(() -> {
            ItemStack out = factory.getItem(ChocolateFactoryBlockEntity.SLOT_OUTPUT);
            helper.assertTrue(out.is(ModItems.CHOCOLATE_BAR.get()) && out.getCount() == 10, "expected 10 chocolate bars, got " + out);
            helper.assertTrue(factory.getItem(ChocolateFactoryBlockEntity.SLOT_INPUT_A).is(Items.BUCKET), "the empty bucket was not left behind");
        });
    }

    private static void licoricePlacement(GameTestHelper helper) {
        BlockState licorice = ModBlocks.RED_LICORICE_PLANT.get().defaultBlockState();
        helper.setBlock(new BlockPos(2, 0, 2), ModBlocks.CHOCOLATE_GRASS_BLOCK.get());
        helper.assertTrue(licorice.canSurvive(helper.getLevel(), helper.absolutePos(new BlockPos(2, 1, 2))), "licorice cannot grow on Gummy Grass");
        helper.assertFalse(licorice.canSurvive(helper.getLevel(), helper.absolutePos(new BlockPos(6, 1, 6))), "licorice should not grow on stone");
        helper.succeed();
    }

    private static void bazookaFires(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setPos(helper.absoluteVec(new Vec3(4.5, 1, 4.5)));
        ItemStack bazooka = new ItemStack(ModItems.JELLY_BAZOOKA.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, bazooka);
        player.getInventory().add(new ItemStack(ModItems.DEAD_JELLY_SNAKE.get(), 2));
        bazooka.use(helper.getLevel(), player, InteractionHand.MAIN_HAND);
        helper.assertTrue(player.getInventory().countItem(ModItems.DEAD_JELLY_SNAKE.get()) == 1, "firing did not use one Dead Jelly Snake");
        helper.assertTrue(bazooka.getDamageValue() == 1, "firing did not cost durability");
        helper.assertEntityPresent(ModEntities.JELLY_SNAKE_SHOT.get());
        helper.succeed();
    }

    private static void warriorBareHanded(GameTestHelper helper) {
        Mob warrior = ModEntities.GINGERBREAD_WARRIOR.get().spawn(helper.getLevel(), helper.absolutePos(new BlockPos(4, 1, 4)), EntitySpawnReason.SPAWNER);
        helper.assertTrue(warrior != null && warrior.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty(),
                "gingerbread warrior should spawn without a weapon (stage 3: it fights with its fists)");
        helper.assertTrue(warrior.getAttributeValue(Attributes.ATTACK_DAMAGE) >= 6.0, "warrior fists should deal 6");
        helper.succeed();
    }

    private static void stage3MobsTick(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        List<? extends Mob> mobs = List.of(
                ModEntities.GINGERBREAD_KING.get().spawn(helper.getLevel(), helper.absolutePos(new BlockPos(4, 1, 4)), EntitySpawnReason.SPAWN_ITEM_USE),
                helper.spawn(ModEntities.GINGERBREAD_WARRIOR.get(), 1, 1, 1), helper.spawn(ModEntities.GINGERBREAD_SOLDIER.get(), 7, 1, 1),
                helper.spawn(ModEntities.COTTON_CANDY_TORNADO.get(), 1, 1, 7), helper.spawn(ModEntities.ROLL_CAKE_MONSTER.get(), 7, 1, 7),
                helper.spawn(ModEntities.JELLY_SHARK.get(), 4, 1, 1));
        helper.runAfterDelay(100, () -> {
            for (Mob mob : mobs) {
                helper.assertTrue(mob.isAlive(), mob.getType().toShortString() + " died or was removed while ticking");
            }
            helper.assertTrue(mobs.get(0).isPersistenceRequired(), "the king must not despawn");
            helper.succeed();
        });
    }

    private static void soldierFires(GameTestHelper helper) {
        GingerbreadSoldier soldier = helper.spawn(ModEntities.GINGERBREAD_SOLDIER.get(), 2, 1, 4);
        Pig pig = helper.spawn(EntityType.PIG, 7, 1, 4);
        soldier.performRangedAttack(pig, 1.0F);
        helper.assertEntityPresent(ModEntities.GUMDROP_SHOT.get());
        helper.succeed();
    }

    /** The slam hurts a pig standing behind the king, which his punches (aimed at his target) never reach. */
    private static void kingSlam(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        GingerbreadKing king = (GingerbreadKing) ModEntities.GINGERBREAD_KING.get().spawn(helper.getLevel(),
                helper.absolutePos(new BlockPos(4, 1, 4)), EntitySpawnReason.SPAWN_ITEM_USE);
        Mob target = helper.spawn(EntityType.IRON_GOLEM, 4, 1, 1); // sturdy, so the fight lasts
        Pig bystander = helper.spawn(EntityType.PIG, 4, 1, 6);
        target.setNoAi(true);
        bystander.setNoAi(true);
        king.setTarget(target);
        helper.succeedWhen(() -> helper.assertTrue(bystander.getHealth() < bystander.getMaxHealth() || !bystander.isAlive(),
                "the ground slam did not hurt the pig behind the king"));
    }

    private static void kingSummons(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        GingerbreadKing king = (GingerbreadKing) ModEntities.GINGERBREAD_KING.get().spawn(helper.getLevel(),
                helper.absolutePos(new BlockPos(4, 1, 4)), EntitySpawnReason.SPAWN_ITEM_USE);
        Mob target = helper.spawn(EntityType.IRON_GOLEM, 1, 1, 1);
        target.setNoAi(true);
        king.setHealth(king.getMaxHealth() * 0.6F);
        king.setTarget(target);
        helper.succeedWhen(() -> helper.assertTrue(
                !helper.getLevel().getEntitiesOfClass(Mob.class, king.getBoundingBox().inflate(16.0),
                        m -> m instanceof GingerbreadSoldier || m instanceof GingerbreadWarrior).isEmpty(),
                "the wounded king did not call any guards"));
    }

    // ------------------------------------------------------------------ stage 4

    private static void stage4MobsTick(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        List<? extends Mob> mobs = List.of(helper.spawn(ModEntities.BROWN_SUGAR_RHINO.get(), 2, 1, 2),
                helper.spawn(ModEntities.GRAPE_SPIDER.get(), 6, 1, 6), helper.spawn(ModEntities.CANDY_TUBE_DOG.get(), 2, 1, 6),
                helper.spawn(ModEntities.ICE_CREAM_VENDOR.get(), 6, 1, 2));
        helper.runAfterDelay(100, () -> {
            for (Mob mob : mobs) {
                helper.assertTrue(mob.isAlive(), mob.getType().toShortString() + " died or was removed while ticking");
            }
            helper.succeed();
        });
    }

    /** With the pounce on (default), the spider stops, then leaps through the air and bites the target. */
    private static void spiderPounces(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        GrapeSpider spider = helper.spawn(ModEntities.GRAPE_SPIDER.get(), 1, 1, 4);
        Mob target = helper.spawn(EntityType.IRON_GOLEM, 6, 1, 4);
        target.setNoAi(true);
        spider.setTarget(target);
        java.util.concurrent.atomic.AtomicBoolean leapt = new java.util.concurrent.atomic.AtomicBoolean();
        helper.onEachTick(() -> {
            if (!spider.onGround() && spider.getDeltaMovement().y > 0.25) {
                leapt.set(true);
            }
        });
        helper.succeedWhen(() -> {
            helper.assertTrue(leapt.get(), "the spider never leapt");
            helper.assertTrue(target.getHealth() < target.getMaxHealth(), "the pounce did not bite the target");
        });
    }

    /** The rhino scrapes, charges (synced charging flag) and hits a target 7 blocks away. */
    private static void rhinoCharges(GameTestHelper helper) {
        com.crazyalloy.revival.entity.BrownSugarRhino rhino = helper.spawn(ModEntities.BROWN_SUGAR_RHINO.get(), 1, 1, 4);
        Mob target = helper.spawn(EntityType.IRON_GOLEM, 8, 1, 4);
        target.setNoAi(true);
        rhino.setTarget(target);
        java.util.concurrent.atomic.AtomicBoolean charged = new java.util.concurrent.atomic.AtomicBoolean();
        helper.onEachTick(() -> {
            if (rhino.isCharging()) {
                charged.set(true);
            }
        });
        helper.succeedWhen(() -> {
            helper.assertTrue(charged.get(), "the rhino never charged");
            helper.assertTrue(target.getHealth() <= target.getMaxHealth() - 11.0F, "the charge did not hit hard, target health " + target.getHealth());
        });
    }

    private static void vendorTrades(GameTestHelper helper) {
        com.crazyalloy.revival.entity.IceCreamVendor vendor = helper.spawn(ModEntities.ICE_CREAM_VENDOR.get(), 4, 1, 4);
        var offers = vendor.getOffers();
        helper.assertTrue(offers.size() == 8, "expected 8 offers, got " + offers.size());
        helper.assertTrue(offers.stream().anyMatch(o -> o.getResult().is(ModItems.VANILLA_ICE_CREAM.get()) && o.getCostA().is(Items.EMERALD)),
                "no vanilla ice cream for emeralds");
        helper.assertTrue(offers.stream().anyMatch(o -> o.getCostA().is(Items.SUGAR) && o.getResult().is(Items.EMERALD)), "does not buy sugar");
        helper.succeed();
    }

    private static List<net.minecraft.world.entity.Entity> placeTemplate(GameTestHelper helper, String name, BlockPos where) {
        var level = helper.getLevel();
        var template = level.getStructureManager().get(CrazyAlloyRevival.id(name)).orElseThrow();
        var settings = new net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings().setFinalizeEntities(true);
        template.placeInWorld(level, where, where, settings, level.getRandom(), 2);
        var size = template.getSize();
        var box = new net.minecraft.world.phys.AABB(where.getX(), where.getY(), where.getZ(),
                where.getX() + size.getX(), where.getY() + size.getY(), where.getZ() + size.getZ());
        return level.getEntitiesOfClass(net.minecraft.world.entity.Entity.class, box, e -> !(e instanceof Player));
    }

    /** Places the fortress high above the test area: one King (from the fortress, persistent) and four guards. */
    private static void fortressTemplate(GameTestHelper helper) {
        BlockPos where = helper.absolutePos(new BlockPos(0, 60, 0));
        List<net.minecraft.world.entity.Entity> found = placeTemplate(helper, "gingerbread_fortress", where);
        long kings = found.stream().filter(e -> e instanceof GingerbreadKing).count();
        long guards = found.stream().filter(e -> e instanceof GingerbreadWarrior || e instanceof GingerbreadSoldier).count();
        helper.assertTrue(kings == 1, "expected 1 king in the fortress, found " + kings);
        helper.assertTrue(guards == 4, "expected 4 placed guards, found " + guards);
        GingerbreadKing king = (GingerbreadKing) found.stream().filter(e -> e instanceof GingerbreadKing).findFirst().orElseThrow();
        helper.assertTrue(king.isPersistenceRequired() && king.hasHome(), "the fortress king must be persistent and tied to his throne");
        helper.assertTrue(helper.getLevel().getBlockState(where.offset(16, 12, 27)).is(net.minecraft.world.level.block.Blocks.GOLD_BLOCK),
                "throne crown missing");
        helper.runAfterDelay(10, () -> {
            helper.assertTrue(king.isAlive(), "the fortress king was removed although gingerbreadKingInFortresses is on");
            helper.succeed();
        });
    }

    private static void truckTemplate(GameTestHelper helper) {
        BlockPos where = helper.absolutePos(new BlockPos(0, 40, 0));
        List<net.minecraft.world.entity.Entity> found = placeTemplate(helper, "ice_cream_truck", where);
        long vendors = found.stream().filter(e -> e instanceof com.crazyalloy.revival.entity.IceCreamVendor).count();
        helper.assertTrue(vendors == 1, "expected 1 vendor in the truck, found " + vendors);
        var banner = helper.getLevel().getBlockEntity(where.offset(1, 4, 0));
        helper.assertTrue(banner instanceof net.minecraft.world.level.block.entity.BannerBlockEntity b && b.getPatterns().layers().size() == 2,
                "truck banner should carry the two ice cream patterns");
        helper.succeed();
    }

    // ------------------------------------------------------------------ stage 5

    private static void stage5MobsTick(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        List<? extends Mob> mobs = List.of(helper.spawn(ModEntities.IMPOSTOR_CAKE.get(), 2, 1, 2),
                helper.spawn(ModEntities.JELLY_SHARK.get(), 6, 1, 6), helper.spawn(ModEntities.BROWN_SUGAR_RHINO.get(), 2, 1, 6));
        helper.runAfterDelay(100, () -> {
            for (Mob mob : mobs) {
                helper.assertTrue(mob.isAlive(), mob.getType().toShortString() + " died or was removed while ticking");
            }
            helper.succeed();
        });
    }

    /**
     * A disguised cake ignores a target it has not noticed yet and stays put; a survival player stepping within the
     * reveal distance makes it open up, hold still for the reveal and then bite.
     */
    private static void impostorCakeReveals(GameTestHelper helper) {
        helper.getLevel().getServer().setDifficulty(Difficulty.NORMAL, true);
        com.crazyalloy.revival.entity.ImpostorCake cake = helper.spawn(ModEntities.IMPOSTOR_CAKE.get(), 2, 1, 4);
        cake.setDisguised(true);
        Vec3 start = cake.position();
        ServerPlayer player = survivalMockPlayer(helper);
        player.snapTo(helper.absoluteVec(new Vec3(8.5, 1, 4.5)));
        helper.runAfterDelay(40, () -> {
            helper.assertTrue(cake.isDisguised(), "the cake revealed itself to a player 6 blocks away");
            helper.assertTrue(cake.position().distanceTo(start) < 0.05, "a disguised cake moved");
            player.snapTo(helper.absoluteVec(new Vec3(4.5, 1, 4.5)));
        });
        helper.runAfterDelay(45, () -> helper.assertFalse(cake.isDisguised(), "the cake did not reveal itself to a player 2 blocks away"));
        helper.succeedWhen(() -> helper.assertTrue(player.getHealth() < player.getMaxHealth(), "the revealed cake never bit the player"));
    }

    /** {@link GameTestHelper#makeMockServerPlayerInLevel()} always reports creative mode, which the cake ignores. */
    private static ServerPlayer survivalMockPlayer(GameTestHelper helper) {
        var cookie = net.minecraft.server.network.CommonListenerCookie.createInitial(
                new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), "test-survival-player"), false);
        ServerPlayer player = new ServerPlayer(helper.getLevel().getServer(), helper.getLevel(), cookie.gameProfile(), cookie.clientInformation()) {
            @Override
            public GameType gameMode() {
                return GameType.SURVIVAL;
            }
        };
        var connection = new net.minecraft.network.Connection(net.minecraft.network.protocol.PacketFlow.SERVERBOUND);
        new io.netty.channel.embedded.EmbeddedChannel(connection);
        helper.getLevel().getServer().getPlayerList().placeNewPlayer(connection, player, cookie);
        // Players are invulnerable until their client reports the world loaded.
        player.connection.markClientLoaded();
        // The game test server's default mode is creative, which also makes the abilities invulnerable.
        player.setGameMode(GameType.SURVIVAL);
        player.getAbilities().invulnerable = false;
        return player;
    }

    private static void impostorCakeRevealsWhenHit(GameTestHelper helper) {
        com.crazyalloy.revival.entity.ImpostorCake cake = helper.spawn(ModEntities.IMPOSTOR_CAKE.get(), 4, 1, 4);
        cake.setDisguised(true);
        Mob attacker = helper.spawn(EntityType.IRON_GOLEM, 7, 1, 4);
        attacker.setNoAi(true);
        cake.hurtServer(helper.getLevel(), helper.getLevel().damageSources().mobAttack(attacker), 1.0F);
        helper.assertFalse(cake.isDisguised(), "a hit cake stayed disguised");
        helper.assertTrue(cake.isRevealing(), "the reveal pause did not start");
        helper.assertTrue(cake.getTarget() == attacker, "the cake did not turn on whoever hit it");
        helper.succeed();
    }

    /** One milk bucket fills the tank; two servings use two cones and two flavour items and leave the empty bucket. */
    private static void iceCreamMachineServes(GameTestHelper helper) {
        BlockPos pos = new BlockPos(4, 1, 4);
        helper.setBlock(pos, ModBlocks.ICE_CREAM_MACHINE.get());
        var machine = helper.getBlockEntity(pos, com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity.class);
        machine.setItem(com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity.SLOT_CONE, new ItemStack(ModItems.WAFER_CONE.get(), 2));
        machine.setItem(com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity.SLOT_FLAVOR, new ItemStack(Items.SWEET_BERRIES, 2));
        machine.setItem(com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity.SLOT_MILK, new ItemStack(Items.MILK_BUCKET));
        // 80 ticks per serving; the test allows 400.
        helper.succeedWhen(() -> {
            ItemStack out = machine.getItem(com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity.SLOT_OUTPUT);
            helper.assertTrue(out.is(ModItems.STRAWBERRY_ICE_CREAM.get()) && out.getCount() == 2, "expected 2 strawberry ice creams, got " + out);
            helper.assertTrue(machine.getItem(com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity.SLOT_CONE).isEmpty(), "cones were not used");
            helper.assertTrue(machine.getItem(com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity.SLOT_MILK).is(Items.BUCKET), "the empty bucket should stay in the milk slot");
            helper.assertTrue(machine.milk() == 2, "4 servings per bucket minus 2 used should leave 2, was " + machine.milk());
        });
    }

    private static void truckHasMachine(GameTestHelper helper) {
        BlockPos where = helper.absolutePos(new BlockPos(0, 40, 0));
        placeTemplate(helper, "ice_cream_truck", where);
        BlockState machine = helper.getLevel().getBlockState(where.offset(2, 2, 4));
        helper.assertTrue(machine.is(ModBlocks.ICE_CREAM_MACHINE.get()), "no Ice Cream Machine inside the truck, found " + machine);
        helper.assertTrue(helper.getLevel().getBlockEntity(where.offset(2, 2, 4)) instanceof com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity,
                "the truck's machine has no block entity");
        helper.succeed();
    }

    /** Heavy Boots are gone, the cave biome exists, trucks only in Sweet Forests, bigger shark and rhino. */
    private static void stage5Registry(GameTestHelper helper) {
        var items = net.minecraft.core.registries.BuiltInRegistries.ITEM;
        helper.assertFalse(items.containsKey(CrazyAlloyRevival.id("heavy_boots")), "heavy_boots is still registered");
        var biomes = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME);
        var cave = biomes.getOrThrow(com.crazyalloy.revival.worldgen.ModWorldgen.CANDY_CAVE);
        var truckTag = net.minecraft.tags.TagKey.create(Registries.BIOME, CrazyAlloyRevival.id("has_structure/ice_cream_truck"));
        helper.assertTrue(biomes.getOrThrow(com.crazyalloy.revival.worldgen.ModWorldgen.SWEET_FOREST).is(truckTag), "trucks must spawn in Sweet Forests");
        helper.assertFalse(biomes.getOrThrow(com.crazyalloy.revival.worldgen.ModWorldgen.JELLY_BEAN_FIELDS).is(truckTag), "trucks must no longer spawn in Jelly Bean Fields");
        boolean creepers = cave.value().getMobSettings().getMobs(net.minecraft.world.entity.MobCategory.MONSTER).unwrap().stream()
                .anyMatch(w -> w.value().type() == ModEntities.BUBBALOO_CREEPER.get());
        helper.assertTrue(creepers, "Bubbaloo Creepers must spawn in Candy Caves");
        helper.assertTrue(ModEntities.JELLY_SHARK.get().getWidth() > 1.5F && ModEntities.BROWN_SUGAR_RHINO.get().getWidth() > 1.4F,
                "the shark and the rhino should be bigger than in 0.4.0");
        helper.succeed();
    }
}
