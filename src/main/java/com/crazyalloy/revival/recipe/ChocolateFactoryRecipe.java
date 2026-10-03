package com.crazyalloy.revival.recipe;

import com.crazyalloy.revival.registry.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;

/**
 * Chocolate Factory recipe: one or two ingredients (in any slot order, one item of each is consumed),
 * a result, a processing time in ticks and experience granted when the output is taken.
 *
 * <pre>
 * { "type": "crazyalloy_revival:chocolate_factory",
 *   "ingredients": ["crazyalloy_revival:cocoa_powder", "minecraft:sugar"],
 *   "result": { "id": "crazyalloy_revival:chocolate_bar", "count": 1 },
 *   "processing_time": 200, "experience": 0.2 }
 * </pre>
 */
public class ChocolateFactoryRecipe implements Recipe<ChocolateFactoryInput> {
    public static final MapCodec<ChocolateFactoryRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf(1, 2).fieldOf("ingredients").forGetter(r -> r.ingredients),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("processing_time", 200).forGetter(r -> r.processingTime),
            Codec.floatRange(0.0F, 10.0F).optionalFieldOf("experience", 0.0F).forGetter(r -> r.experience)
    ).apply(inst, ChocolateFactoryRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ChocolateFactoryRecipe> STREAM_CODEC =
            ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    private final List<Ingredient> ingredients;
    private final ItemStackTemplate result;
    private final int processingTime;
    private final float experience;

    public ChocolateFactoryRecipe(List<Ingredient> ingredients, ItemStackTemplate result, int processingTime, float experience) {
        this.ingredients = List.copyOf(ingredients);
        this.result = result;
        this.processingTime = processingTime;
        this.experience = experience;
    }

    @Override
    public boolean matches(ChocolateFactoryInput input, Level level) {
        ItemStack a = input.first();
        ItemStack b = input.second();
        if (ingredients.size() == 1) {
            Ingredient only = ingredients.get(0);
            return (only.test(a) && b.isEmpty()) || (only.test(b) && a.isEmpty());
        }
        Ingredient x = ingredients.get(0);
        Ingredient y = ingredients.get(1);
        return (x.test(a) && y.test(b)) || (x.test(b) && y.test(a));
    }

    @Override
    public ItemStack assemble(ChocolateFactoryInput input) {
        return result.create();
    }

    public ItemStack resultPreview() {
        return result.create();
    }

    public int processingTime() {
        return processingTime;
    }

    public float experience() {
        return experience;
    }

    public List<Ingredient> ingredients() {
        return ingredients;
    }

    @Override
    public RecipeSerializer<ChocolateFactoryRecipe> getSerializer() {
        return ModRecipes.CHOCOLATE_FACTORY_SERIALIZER.get();
    }

    @Override
    public RecipeType<ChocolateFactoryRecipe> getType() {
        return ModRecipes.CHOCOLATE_FACTORY_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        // No recipe book for the factory; marking it special keeps vanilla from warning about placement info.
        return true;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of();
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return ModRecipes.CHOCOLATE_FACTORY_CATEGORY.get();
    }
}
