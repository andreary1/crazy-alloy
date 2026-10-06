package com.crazyalloy.revival.menu;

import com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModMenus;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

public class IceCreamMachineMenu extends AbstractContainerMenu {
    public static final int CONE_X = 26, CONE_Y = 20;
    public static final int FLAVOR_X = 26, FLAVOR_Y = 46;
    public static final int MILK_X = 61, MILK_Y = 54;
    public static final int OUTPUT_X = 124, OUTPUT_Y = 33;

    private static final int PLAYER_INV_START = IceCreamMachineBlockEntity.SLOT_COUNT;
    private static final int PLAYER_INV_END = PLAYER_INV_START + 36;

    private final Container container;
    private final ContainerData data;

    public static IceCreamMachineMenu clientSide(int id, Inventory inventory, @Nullable RegistryFriendlyByteBuf extra) {
        return new IceCreamMachineMenu(id, inventory, new SimpleContainer(IceCreamMachineBlockEntity.SLOT_COUNT),
                new SimpleContainerData(IceCreamMachineBlockEntity.DATA_COUNT));
    }

    public IceCreamMachineMenu(int id, Inventory inventory, Container container, ContainerData data) {
        super(ModMenus.ICE_CREAM_MACHINE.get(), id);
        checkContainerSize(container, IceCreamMachineBlockEntity.SLOT_COUNT);
        checkContainerDataCount(data, IceCreamMachineBlockEntity.DATA_COUNT);
        this.container = container;
        this.data = data;
        container.startOpen(inventory.player);

        this.addSlot(new Slot(container, IceCreamMachineBlockEntity.SLOT_CONE, CONE_X, CONE_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ModItems.WAFER_CONE.get());
            }
        });
        this.addSlot(new Slot(container, IceCreamMachineBlockEntity.SLOT_FLAVOR, FLAVOR_X, FLAVOR_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return !stack.is(ModItems.WAFER_CONE.get()) && !stack.is(Items.MILK_BUCKET);
            }
        });
        this.addSlot(new Slot(container, IceCreamMachineBlockEntity.SLOT_MILK, MILK_X, MILK_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.MILK_BUCKET);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(container, IceCreamMachineBlockEntity.SLOT_OUTPUT, OUTPUT_X, OUTPUT_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player taker, ItemStack stack) {
                if (container instanceof IceCreamMachineBlockEntity machine) {
                    machine.awardExperience(taker);
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

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (index < PLAYER_INV_START) {
            if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, index == IceCreamMachineBlockEntity.SLOT_OUTPUT)) {
                return ItemStack.EMPTY;
            }
            if (index == IceCreamMachineBlockEntity.SLOT_OUTPUT) {
                slot.onQuickCraft(stack, copy);
            }
        } else {
            int target = stack.is(ModItems.WAFER_CONE.get()) ? IceCreamMachineBlockEntity.SLOT_CONE
                    : stack.is(Items.MILK_BUCKET) ? IceCreamMachineBlockEntity.SLOT_MILK : IceCreamMachineBlockEntity.SLOT_FLAVOR;
            if (!this.moveItemStackTo(stack, target, target + 1, false)) {
                return ItemStack.EMPTY;
            }
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

    public int milk() {
        return data.get(IceCreamMachineBlockEntity.DATA_MILK);
    }

    public int milkMax() {
        return Math.max(1, data.get(IceCreamMachineBlockEntity.DATA_MILK_MAX));
    }

    /** Progress of the current serving as a value from 0 to {@code width}. */
    public int progressScaled(int width) {
        int total = data.get(IceCreamMachineBlockEntity.DATA_PROGRESS_TOTAL);
        int progress = data.get(IceCreamMachineBlockEntity.DATA_PROGRESS);
        return total > 0 && progress > 0 ? Math.min(width, progress * width / total) : 0;
    }

    /** The ice cream the current flavour makes, or empty when the flavour slot holds nothing usable. */
    public ItemStack flavorResult() {
        int id = data.get(IceCreamMachineBlockEntity.DATA_RESULT);
        return id > 0 ? new ItemStack(BuiltInRegistries.ITEM.byId(id - 1)) : ItemStack.EMPTY;
    }

    public ItemStack slotItem(int slot) {
        return container.getItem(slot);
    }
}
