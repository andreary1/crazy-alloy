package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.registry.ModEntities;
import com.crazyalloy.revival.registry.ModItems;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

/** The four ice cream flavours of the Ice Cream Dimension (stage 6), shared by its zombies and living ice creams. */
public enum IceCreamFlavor {
    CHOCOLATE(ModItems.CHOCOLATE_ICE_CREAM, ModEntities.CHOCOLATE_ICE_CREAM_ZOMBIE, ModEntities.LIVING_CHOCOLATE_ICE_CREAM),
    VANILLA(ModItems.VANILLA_ICE_CREAM, ModEntities.VANILLA_ICE_CREAM_ZOMBIE, ModEntities.LIVING_VANILLA_ICE_CREAM),
    STRAWBERRY(ModItems.STRAWBERRY_ICE_CREAM, ModEntities.STRAWBERRY_ICE_CREAM_ZOMBIE, ModEntities.LIVING_STRAWBERRY_ICE_CREAM),
    MINT(ModItems.MINT_ICE_CREAM, ModEntities.MINT_ICE_CREAM_ZOMBIE, ModEntities.LIVING_MINT_ICE_CREAM);

    private final Supplier<? extends Item> iceCream;
    private final Supplier<? extends EntityType<?>> zombie;
    private final Supplier<? extends EntityType<?>> living;

    IceCreamFlavor(Supplier<? extends Item> iceCream, Supplier<? extends EntityType<?>> zombie, Supplier<? extends EntityType<?>> living) {
        this.iceCream = iceCream;
        this.zombie = zombie;
        this.living = living;
    }

    public Item iceCream() {
        return iceCream.get();
    }

    /** The flavour of a zombie or living ice cream type (chocolate if the type is not one of them). */
    public static IceCreamFlavor of(EntityType<?> type) {
        for (IceCreamFlavor f : values()) {
            if (f.zombie.get() == type || f.living.get() == type) {
                return f;
            }
        }
        return CHOCOLATE;
    }
}
