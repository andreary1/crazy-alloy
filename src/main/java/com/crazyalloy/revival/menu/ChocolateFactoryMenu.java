package com.crazyalloy.revival.menu;

import com.crazyalloy.revival.block.entity.ChocolateFactoryBlockEntity;
import com.crazyalloy.revival.registry.ModMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jspecify.annotations.Nullable;

public class ChocolateFactoryMenu extends AbstractContainerMenu {
    public static final int INPUT_A_X = 38, INPUT_B_X = 60, INPUT_Y = 17;
    public static final int FUEL_X = 49, FUEL_Y = 53;
    public static final int OUTPUT_X = 120, OUTPUT_Y = 35;

    private static final int PLAYER_INV_START = ChocolateFactoryBlockEntity.SLOT_COUNT;
    private static final int PLAYER_INV_END = PLAYER_INV_START + 36;

    private final Container container;
    private final ContainerData data;
    private final Player player;

    /** Client-side constructor used by the menu type factory. */
    public static ChocolateFactoryMenu clientSide(int id, Inventory inventory, @Nullable RegistryFriendlyByteBuf extra) {
        return new ChocolateFactoryMenu(id, inventory, new SimpleContainer(ChocolateFactoryBlockEntity.SLOT_COUNT),
                new SimpleContainerData(ChocolateFactoryBlockEntity.DATA_COUNT));
    }

    public ChocolateFactoryMenu(int id, Inventory inventory, Container container, ContainerData data) {
        super(ModMenus.CHOCOLATE_FACTORY.get(), id);
        checkContainerSize(container, ChocolateFactoryBlockEntity.SLOT_COUNT);
        checkContainerDataCount(data, ChocolateFactoryBlockEntity.DATA_COUNT);
        this.container = container;
        this.data = data;
        this.player = inventory.player;
        container.startOpen(player);

        this.addSlot(new Slot(container, ChocolateFactoryBlockEntity.SLOT_INPUT_A, INPUT_A_X, INPUT_Y));
        this.addSlot(new Slot(container, ChocolateFactoryBlockEntity.SLOT_INPUT_B, INPUT_B_X, INPUT_Y));
        this.addSlot(new Slot(container, ChocolateFactoryBlockEntity.SLOT_FUEL, FUEL_X, FUEL_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isFuel(stack);
            }
        });
        this.addSlot(new Slot(container, ChocolateFactoryBlockEntity.SLOT_OUTPUT, OUTPUT_X, OUTPUT_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player taker, ItemStack stack) {
                if (container instanceof ChocolateFactoryBlockEntity factory) {
                    factory.awardExperience(taker);
                }
                super.onTake(taker, stack);
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }
        this.addDataSlots(data);
    }

    private boolean isFuel(ItemStack stack) {
        return stack.getBurnTime(RecipeType.SMELTING, player.level().fuelValues()) > 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index == ChocolateFactoryBlockEntity.SLOT_OUTPUT) {
            if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickCraft(stack, copy);
        } else if (index >= PLAYER_INV_START) {
            boolean moved = isFuel(stack)
                    && this.moveItemStackTo(stack, ChocolateFactoryBlockEntity.SLOT_FUEL, ChocolateFactoryBlockEntity.SLOT_FUEL + 1, false);
            if (!moved && !this.moveItemStackTo(stack, ChocolateFactoryBlockEntity.SLOT_INPUT_A, ChocolateFactoryBlockEntity.SLOT_INPUT_B + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        if (stack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }
        slot.onTake(player, stack);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }

    public boolean isBurning() {
        return data.get(ChocolateFactoryBlockEntity.DATA_BURN_LEFT) > 0;
    }

    /** Remaining fuel as a value from 0 to {@code height}. */
    public int burnScaled(int height) {
        int total = data.get(ChocolateFactoryBlockEntity.DATA_BURN_TOTAL);
        if (total <= 0) {
            total = 200;
        }
        return Math.min(height, data.get(ChocolateFactoryBlockEntity.DATA_BURN_LEFT) * height / total);
    }

    /** Recipe progress as a value from 0 to {@code width}. */
    public int progressScaled(int width) {
        int total = data.get(ChocolateFactoryBlockEntity.DATA_PROGRESS_TOTAL);
        int progress = data.get(ChocolateFactoryBlockEntity.DATA_PROGRESS);
        return total > 0 && progress > 0 ? Math.min(width, progress * width / total) : 0;
    }
}
