package com.crazyalloy.revival.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

/**
 * Sweets are quick to eat but not very filling; each has a small, distinct effect so they are
 * worth carrying alongside normal food.
 */
public final class ModFoods {
    public static final FoodProperties CHOCOLATE_BAR = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4F).build();
    public static final Consumable CHOCOLATE_BAR_CONSUMABLE = Consumables.defaultFood().consumeSeconds(1.2F).build();

    /** Better food from milk, with a few seconds of Regeneration. */
    public static final FoodProperties MILK_CHOCOLATE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();
    public static final Consumable MILK_CHOCOLATE_CONSUMABLE = Consumables.defaultFood().consumeSeconds(1.4F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0)))
            .build();

    /** Sugar rush: a short Speed boost. */
    public static final FoodProperties LOLLIPOP = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).alwaysEdible().build();
    public static final Consumable LOLLIPOP_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.8F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 160, 0)))
            .build();

    /** Light as air: a short Jump Boost. */
    public static final FoodProperties COTTON_CANDY = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).alwaysEdible().build();
    public static final Consumable COTTON_CANDY_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.6F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 200, 0)))
            .build();

    public static final FoodProperties GRAPE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build();
    public static final Consumable GRAPE_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.8F).build();

    /** Raw licorice is a light snack; smelting it makes a filling one. */
    public static final FoodProperties RED_LICORICE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).build();
    public static final Consumable RED_LICORICE_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.8F).build();
    public static final FoodProperties COOKED_LICORICE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();
    public static final Consumable COOKED_LICORICE_CONSUMABLE = Consumables.defaultFood().build();

    public static final FoodProperties GINGERBREAD = new FoodProperties.Builder().nutrition(4).saturationModifier(0.5F).build();
    public static final Consumable GINGERBREAD_CONSUMABLE = Consumables.defaultFood().consumeSeconds(1.2F).build();

    public static final FoodProperties GUMDROP = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).alwaysEdible().build();
    public static final Consumable GUMDROP_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.6F).build();

    public static final FoodProperties ROLL_CAKE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();
    public static final Consumable ROLL_CAKE_CONSUMABLE = Consumables.defaultFood().build();

    public static final FoodProperties JELLY_BEANS = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1F).alwaysEdible().build();
    public static final Consumable JELLY_BEANS_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.6F).build();

    // --- Stage 4: Ice Cream Truck. Cold, sweet and a little more filling than candy (revival proposal). ------------
    /** Vanilla: cools you down, a few seconds of Fire Resistance. */
    public static final FoodProperties VANILLA_ICE_CREAM = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5F).build();
    public static final Consumable VANILLA_ICE_CREAM_CONSUMABLE = Consumables.defaultFood().consumeSeconds(1.2F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0)))
            .build();
    /** Strawberry: a short Regeneration. */
    public static final FoodProperties STRAWBERRY_ICE_CREAM = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5F).build();
    public static final Consumable STRAWBERRY_ICE_CREAM_CONSUMABLE = Consumables.defaultFood().consumeSeconds(1.2F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 80, 0)))
            .build();
    /** Chocolate: Haste for a while. */
    public static final FoodProperties CHOCOLATE_ICE_CREAM = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5F).build();
    public static final Consumable CHOCOLATE_ICE_CREAM_CONSUMABLE = Consumables.defaultFood().consumeSeconds(1.2F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HASTE, 400, 0)))
            .build();
    /** The empty wafer cone is a crunchy snack on its own. */
    public static final FoodProperties WAFER_CONE = new FoodProperties.Builder().nutrition(1).saturationModifier(0.2F).build();
    public static final Consumable WAFER_CONE_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.8F).build();

    private ModFoods() {}
}
