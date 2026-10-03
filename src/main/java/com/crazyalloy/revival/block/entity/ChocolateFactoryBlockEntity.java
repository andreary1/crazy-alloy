package com.crazyalloy.revival.block.entity;

import com.crazyalloy.revival.block.ChocolateFactoryBlock;
import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.menu.ChocolateFactoryMenu;
import com.crazyalloy.revival.recipe.ChocolateFactoryInput;
import com.crazyalloy.revival.recipe.ChocolateFactoryRecipe;
import com.crazyalloy.revival.registry.ModBlockEntities;
import com.crazyalloy.revival.registry.ModRecipes;
import com.crazyalloy.revival.registry.ModSounds;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

/**
 * Chocolate Factory: two ingredient slots, a fuel slot (any furnace fuel) and an output slot.
 * All processing happens on the server; the client only receives slot contents and the four data values.
 */
public class ChocolateFactoryBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    public static final int SLOT_INPUT_A = 0;
    public static final int SLOT_INPUT_B = 1;
    public static final int SLOT_FUEL = 2;
    public static final int SLOT_OUTPUT = 3;
    public static final int SLOT_COUNT = 4;

    public static final int DATA_BURN_LEFT = 0;
    public static final int DATA_BURN_TOTAL = 1;
    public static final int DATA_PROGRESS = 2;
    public static final int DATA_PROGRESS_TOTAL = 3;
    public static final int DATA_COUNT = 4;

    private static final int[] SLOTS_TOP = {SLOT_INPUT_A, SLOT_INPUT_B};
    private static final int[] SLOTS_SIDE = {SLOT_FUEL};
    private static final int[] SLOTS_BOTTOM = {SLOT_OUTPUT};

    private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private int burnLeft;
    private int burnTotal;
    /** Progress in hundredths of a tick so that the speed multiplier can be fractional. */
    private int progress;
    private int progressTotal;
    private float storedExperience;
    /** Recipe being processed; progress resets when the ingredients change to another recipe. Not saved. */
    private @Nullable ResourceKey<Recipe<?>> currentRecipe;
    private final RecipeManager.CachedCheck<ChocolateFactoryInput, ChocolateFactoryRecipe> quickCheck;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_BURN_LEFT -> Math.min(burnLeft, Short.MAX_VALUE);
                case DATA_BURN_TOTAL -> Math.min(burnTotal, Short.MAX_VALUE);
                case DATA_PROGRESS -> Math.min(progress / 100, Short.MAX_VALUE);
                case DATA_PROGRESS_TOTAL -> Math.min(progressTotal, Short.MAX_VALUE);
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            // Values are server-authoritative; nothing to do on the server side.
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public ChocolateFactoryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHOCOLATE_FACTORY.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(ModRecipes.CHOCOLATE_FACTORY_TYPE.get());
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, ChocolateFactoryBlockEntity factory) {
        boolean wasBurning = factory.burnLeft > 0;
        boolean changed = false;
        if (factory.burnLeft > 0) {
            factory.burnLeft--;
        }

        ChocolateFactoryInput input = factory.input();
        Optional<RecipeHolder<ChocolateFactoryRecipe>> recipe = input.first().isEmpty() && input.second().isEmpty()
                ? Optional.empty()
                : factory.quickCheck.getRecipeFor(input, level);
        boolean canWork = recipe.isPresent() && factory.canOutput(recipe.get().value().resultPreview());

        if (canWork && factory.burnLeft <= 0) {
            ItemStack fuel = factory.items.get(SLOT_FUEL);
            int burn = fuel.isEmpty() ? 0 : fuel.getBurnTime(RecipeType.SMELTING, level.fuelValues());
            if (burn > 0) {
                factory.burnLeft = burn;
                factory.burnTotal = burn;
                ItemStackTemplate remainder = fuel.getCraftingRemainder();
                fuel.shrink(1);
                if (fuel.isEmpty()) {
                    factory.items.set(SLOT_FUEL, remainder != null ? remainder.create() : ItemStack.EMPTY);
                }
                changed = true;
            }
        }

        if (canWork && factory.burnLeft > 0) {
            ResourceKey<Recipe<?>> id = recipe.get().id();
            if (factory.currentRecipe != null && !factory.currentRecipe.equals(id)) {
                factory.progress = 0;
            }
            factory.currentRecipe = id;
            ChocolateFactoryRecipe r = recipe.get().value();
            factory.progressTotal = r.processingTime();
            factory.progress += Mth.floor(100 * RevivalConfig.CHOCOLATE_FACTORY_SPEED.get());
            if (factory.progress >= factory.progressTotal * 100) {
                factory.craft(r, input);
                factory.progress = 0;
                level.playSound(null, pos, ModSounds.CHOCOLATE_FACTORY_WORKING.get(), SoundSource.BLOCKS, 0.6F, 0.9F + level.getRandom().nextFloat() * 0.2F);
            }
            changed = true;
        } else if (factory.progress > 0) {
            // Lose progress slowly when the factory stops, like a furnace.
            factory.progress = Math.max(0, factory.progress - 200);
            changed = true;
        }

        boolean burning = factory.burnLeft > 0;
        if (wasBurning != burning) {
            level.setBlock(pos, state.setValue(ChocolateFactoryBlock.LIT, burning), 3);
            changed = true;
        }
        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private ChocolateFactoryInput input() {
        return new ChocolateFactoryInput(items.get(SLOT_INPUT_A), items.get(SLOT_INPUT_B));
    }

    private boolean canOutput(ItemStack result) {
        if (result.isEmpty()) {
            return false;
        }
        ItemStack out = items.get(SLOT_OUTPUT);
        if (out.isEmpty()) {
            return true;
        }
        return ItemStack.isSameItemSameComponents(out, result) && out.getCount() + result.getCount() <= out.getMaxStackSize();
    }

    private void craft(ChocolateFactoryRecipe recipe, ChocolateFactoryInput input) {
        ItemStack result = recipe.assemble(input);
        ItemStack out = items.get(SLOT_OUTPUT);
        if (out.isEmpty()) {
            items.set(SLOT_OUTPUT, result);
        } else {
            out.grow(result.getCount());
        }
        consumeInput(SLOT_INPUT_A);
        consumeInput(SLOT_INPUT_B);
        storedExperience += recipe.experience();
    }

    private void consumeInput(int slot) {
        ItemStack stack = items.get(slot);
        if (stack.isEmpty()) {
            return;
        }
        ItemStackTemplate remainder = stack.getCraftingRemainder();
        stack.shrink(1);
        if (stack.isEmpty() && remainder != null) {
            // e.g. a milk bucket leaves an empty bucket behind
            items.set(slot, remainder.create());
        }
    }

    /** Grants the experience collected since the output was last emptied. Called when a player takes the output. */
    public void awardExperience(Player player) {
        if (storedExperience <= 0 || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        int whole = Mth.floor(storedExperience);
        float fraction = storedExperience - whole;
        if (fraction > 0 && serverLevel.getRandom().nextFloat() < fraction) {
            whole++;
        }
        storedExperience = 0;
        if (whole > 0) {
            ExperienceOrb.award(serverLevel, player.position(), whole);
        }
        setChanged();
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.burnLeft = input.getIntOr("burn_left", 0);
        this.burnTotal = input.getIntOr("burn_total", 0);
        this.progress = input.getIntOr("progress", 0);
        this.progressTotal = input.getIntOr("progress_total", 0);
        this.storedExperience = input.getFloatOr("stored_experience", 0.0F);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        output.putInt("burn_left", burnLeft);
        output.putInt("burn_total", burnTotal);
        output.putInt("progress", progress);
        output.putInt("progress_total", progressTotal);
        output.putFloat("stored_experience", storedExperience);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.crazyalloy_revival.chocolate_factory");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ChocolateFactoryMenu(containerId, inventory, this, data);
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == SLOT_OUTPUT) {
            return false;
        }
        if (slot == SLOT_FUEL) {
            return level != null && stack.getBurnTime(RecipeType.SMELTING, level.fuelValues()) > 0;
        }
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return switch (side) {
            case UP -> SLOTS_TOP;
            case DOWN -> SLOTS_BOTTOM;
            default -> SLOTS_SIDE;
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction side) {
        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return slot == SLOT_OUTPUT;
    }
}
