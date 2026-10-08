package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.registry.ModItems;
import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;

/**
 * The four ice cream flavours of the Ice Cream Dimension (stage 6). The Ice Cream Zombie and the Living Ice Cream are
 * one entity type each, and the flavour is a variant: synced to clients for the texture, saved as "Flavor".
 */
public enum IceCreamFlavor {
    CHOCOLATE(ModItems.CHOCOLATE_ICE_CREAM),
    VANILLA(ModItems.VANILLA_ICE_CREAM),
    STRAWBERRY(ModItems.STRAWBERRY_ICE_CREAM),
    MINT(ModItems.MINT_ICE_CREAM);

    private static final IceCreamFlavor[] VALUES = values();
    private final Supplier<? extends Item> iceCream;

    IceCreamFlavor(Supplier<? extends Item> iceCream) {
        this.iceCream = iceCream;
    }

    public Item iceCream() {
        return iceCream.get();
    }

    /** Lower-case name used in saves, translation keys and texture names. */
    public String id() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static IceCreamFlavor byIndex(int index) {
        return VALUES[Math.floorMod(index, VALUES.length)];
    }

    public static IceCreamFlavor byId(String id) {
        for (IceCreamFlavor f : VALUES) {
            if (f.id().equals(id)) {
                return f;
            }
        }
        return CHOCOLATE;
    }

    public static IceCreamFlavor random(RandomSource random) {
        return VALUES[random.nextInt(VALUES.length)];
    }
}
