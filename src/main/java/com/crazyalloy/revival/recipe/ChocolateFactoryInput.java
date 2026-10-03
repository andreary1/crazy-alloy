package com.crazyalloy.revival.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

/** The two ingredient slots of a Chocolate Factory. */
public record ChocolateFactoryInput(ItemStack first, ItemStack second) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> first;
            case 1 -> second;
            default -> throw new IllegalArgumentException("No slot " + index);
        };
    }

    @Override
    public int size() {
        return 2;
    }
}
