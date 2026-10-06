package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.recipe.ChocolateFactoryRecipe;
import com.crazyalloy.revival.recipe.IceCreamMachineRecipe;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, CrazyAlloyRevival.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, CrazyAlloyRevival.MOD_ID);
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, CrazyAlloyRevival.MOD_ID);

    public static final Supplier<RecipeType<ChocolateFactoryRecipe>> CHOCOLATE_FACTORY_TYPE = RECIPE_TYPES.register("chocolate_factory",
            () -> RecipeType.simple(CrazyAlloyRevival.id("chocolate_factory")));
    public static final Supplier<RecipeSerializer<ChocolateFactoryRecipe>> CHOCOLATE_FACTORY_SERIALIZER = RECIPE_SERIALIZERS.register("chocolate_factory",
            () -> new RecipeSerializer<>(ChocolateFactoryRecipe.CODEC, ChocolateFactoryRecipe.STREAM_CODEC));
    public static final Supplier<RecipeBookCategory> CHOCOLATE_FACTORY_CATEGORY = RECIPE_BOOK_CATEGORIES.register("chocolate_factory",
            RecipeBookCategory::new);

    public static final Supplier<RecipeType<IceCreamMachineRecipe>> ICE_CREAM_MACHINE_TYPE = RECIPE_TYPES.register("ice_cream_machine",
            () -> RecipeType.simple(CrazyAlloyRevival.id("ice_cream_machine")));
    public static final Supplier<RecipeSerializer<IceCreamMachineRecipe>> ICE_CREAM_MACHINE_SERIALIZER = RECIPE_SERIALIZERS.register("ice_cream_machine",
            () -> new RecipeSerializer<>(IceCreamMachineRecipe.CODEC, IceCreamMachineRecipe.STREAM_CODEC));
    public static final Supplier<RecipeBookCategory> ICE_CREAM_MACHINE_CATEGORY = RECIPE_BOOK_CATEGORIES.register("ice_cream_machine",
            RecipeBookCategory::new);

    private ModRecipes() {}
}
