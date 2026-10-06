package com.crazyalloy.revival.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

/** The flavour slot of an Ice Cream Machine (the cone and the milk are the same for every recipe). */
public record IceCreamMachineInput(ItemStack flavor) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No slot " + index);
        }
        return flavor;
    }

    @Override
    public int size() {
        return 1;
    }
}
