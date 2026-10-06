package com.crazyalloy.revival.block.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.menu.IceCreamMachineMenu;
import com.crazyalloy.revival.recipe.IceCreamMachineInput;
import com.crazyalloy.revival.recipe.IceCreamMachineRecipe;
import com.crazyalloy.revival.registry.ModBlockEntities;
import com.crazyalloy.revival.registry.ModItems;
import com.crazyalloy.revival.registry.ModRecipes;
import com.crazyalloy.revival.registry.ModSounds;
import com.mojang.logging.LogUtils;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;

/**
 * Ice Cream Machine (revival proposal, stage 5): a Wafer Cone slot, a flavour slot, a milk slot and an output slot.
 * A Milk Bucket fills the internal tank with {@code iceCreamMachineServingsPerBucket} servings (the empty bucket stays
 * in the milk slot). Each serving uses one cone, one flavour item and one serving of milk; the flavour picks the
 * recipe. No fuel: it runs on its own.
 * <p>
 * The client gets the slot contents (to draw the ice cream on the tray) and two block events: {@link #EVENT_SERVE}
 * starts the lever-and-swirl animation, carrying the result item and the duration.
 */
public class IceCreamMachineBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final int SLOT_CONE = 0;
    public static final int SLOT_FLAVOR = 1;
    public static final int SLOT_MILK = 2;
    public static final int SLOT_OUTPUT = 3;
    public static final int SLOT_COUNT = 4;

    public static final int DATA_MILK = 0;
    public static final int DATA_MILK_MAX = 1;
    public static final int DATA_PROGRESS = 2;
    public static final int DATA_PROGRESS_TOTAL = 3;
    /** Raw id + 1 of the ice cream the current flavour makes (0 = none), so the screen can name the flavour. */
    public static final int DATA_RESULT = 4;
    public static final int DATA_COUNT = 5;

    /** Block event: a serving started. Parameter = result item raw id << 12 | duration in ticks. */
    public static final int EVENT_SERVE = 1;

    private static final int[] SLOTS_TOP = {SLOT_CONE, SLOT_FLAVOR};
    private static final int[] SLOTS_SIDE = {SLOT_MILK};
    private static final int[] SLOTS_BOTTOM = {SLOT_OUTPUT};

    private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private int milk;
    /** Progress in hundredths of a tick so that the speed multiplier can be fractional. */
    private int progress;
    private int progressTotal;
    private int resultId;
    private float storedExperience;
    private boolean displayChanged;
    private @Nullable ResourceKey<Recipe<?>> currentRecipe;
    private final RecipeManager.CachedCheck<IceCreamMachineInput, IceCreamMachineRecipe> quickCheck;

    /** Client: when the current serve animation started (game time) and how long it lasts. */
    private long animStart = Long.MIN_VALUE;
    private int animLength;
    private ItemStack animItem = ItemStack.EMPTY;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_MILK -> milk;
                case DATA_MILK_MAX -> milkCapacity();
                case DATA_PROGRESS -> Math.min(progress / 100, Short.MAX_VALUE);
                case DATA_PROGRESS_TOTAL -> Math.min(progressTotal, Short.MAX_VALUE);
                case DATA_RESULT -> resultId;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            // Values are server-authoritative.
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public IceCreamMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ICE_CREAM_MACHINE.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(ModRecipes.ICE_CREAM_MACHINE_TYPE.get());
    }

    public static int milkCapacity() {
        return RevivalConfig.ICE_CREAM_MACHINE_SERVINGS_PER_BUCKET.get() * 2;
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, IceCreamMachineBlockEntity machine) {
        boolean changed = false;
        // Pour a milk bucket into the tank when there is room for all of it.
        ItemStack milkStack = machine.items.get(SLOT_MILK);
        int perBucket = RevivalConfig.ICE_CREAM_MACHINE_SERVINGS_PER_BUCKET.get();
        if (milkStack.is(Items.MILK_BUCKET) && machine.milk + perBucket <= milkCapacity()) {
            machine.milk += perBucket;
            machine.items.set(SLOT_MILK, new ItemStack(Items.BUCKET));
            changed = true;
        }

        ItemStack flavor = machine.items.get(SLOT_FLAVOR);
        Optional<RecipeHolder<IceCreamMachineRecipe>> recipe = flavor.isEmpty()
                ? Optional.empty() : machine.quickCheck.getRecipeFor(new IceCreamMachineInput(flavor), level);
        int newResult = recipe.map(r -> BuiltInRegistries.ITEM.getId(r.value().resultPreview().getItem()) + 1).orElse(0);
        if (newResult != machine.resultId) {
            machine.resultId = newResult;
            changed = true;
        }
        boolean canWork = recipe.isPresent() && machine.milk > 0 && machine.items.get(SLOT_CONE).is(ModItems.WAFER_CONE.get())
                && machine.canOutput(recipe.get().value().resultPreview());

        if (canWork) {
            ResourceKey<Recipe<?>> id = recipe.get().id();
            if (machine.currentRecipe != null && !machine.currentRecipe.equals(id)) {
                machine.progress = 0;
            }
            machine.currentRecipe = id;
            IceCreamMachineRecipe r = recipe.get().value();
            machine.progressTotal = r.processingTime();
            int step = Math.max(1, Mth.floor(100 * RevivalConfig.ICE_CREAM_MACHINE_SPEED.get()));
            if (machine.progress == 0) {
                int ticks = Mth.clamp(Mth.ceil(machine.progressTotal * 100.0F / step), 1, 4095);
                int item = BuiltInRegistries.ITEM.getId(r.resultPreview().getItem());
                level.blockEvent(pos, state.getBlock(), EVENT_SERVE, (item << 12) | ticks);
            }
            machine.progress += step;
            if (machine.progress >= machine.progressTotal * 100) {
                machine.serve(r, flavor);
                machine.progress = 0;
                level.playSound(null, pos, ModSounds.ICE_CREAM_MACHINE_SERVE.get(), SoundSource.BLOCKS, 0.7F, 0.9F + level.getRandom().nextFloat() * 0.2F);
            }
            changed = true;
        } else if (machine.progress > 0) {
            machine.progress = 0;
            machine.currentRecipe = null;
            changed = true;
        }

        if (machine.displayChanged) {
            machine.displayChanged = false;
            level.sendBlockUpdated(pos, state, state, 3);
        }
        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private boolean canOutput(ItemStack result) {
        if (result.isEmpty()) {
            return false;
        }
        ItemStack out = items.get(SLOT_OUTPUT);
        return out.isEmpty() || (ItemStack.isSameItemSameComponents(out, result) && out.getCount() + result.getCount() <= out.getMaxStackSize());
    }

    private void serve(IceCreamMachineRecipe recipe, ItemStack flavor) {
        ItemStack result = recipe.assemble(new IceCreamMachineInput(flavor));
        ItemStack out = items.get(SLOT_OUTPUT);
        if (out.isEmpty()) {
            items.set(SLOT_OUTPUT, result);
        } else {
            out.grow(result.getCount());
        }
        items.get(SLOT_CONE).shrink(1);
        ItemStackTemplate remainder = flavor.getCraftingRemainder();
        flavor.shrink(1);
        if (flavor.isEmpty() && remainder != null) {
            items.set(SLOT_FLAVOR, remainder.create());
        }
        milk--;
        storedExperience += recipe.experience();
        displayChanged = true;
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

    public int milk() {
        return milk;
    }

    // ------------------------------------------------------------------ client animation
    @Override
    public boolean triggerEvent(int id, int param) {
        if (id == EVENT_SERVE) {
            if (level != null && level.isClientSide()) {
                this.animStart = level.getGameTime();
                this.animLength = Math.max(1, param & 0xFFF);
                Item item = BuiltInRegistries.ITEM.byId(param >>> 12);
                this.animItem = new ItemStack(item);
            }
            return true;
        }
        return super.triggerEvent(id, param);
    }

    /** Starts the serve animation on every client nearby, e.g. when the Ice Cream Vendor hands out a cone. */
    public void playServeAnimation(Item result, int ticks) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.blockEvent(worldPosition, getBlockState().getBlock(), EVENT_SERVE,
                    (BuiltInRegistries.ITEM.getId(result) << 12) | Mth.clamp(ticks, 1, 4095));
        }
    }

    /** Client: 0 to 1 through the current serve animation, or -1 when none is running. */
    public float serveProgress(float partialTick) {
        if (level == null || animStart == Long.MIN_VALUE) {
            return -1.0F;
        }
        float t = (level.getGameTime() - animStart + partialTick) / animLength;
        return t >= 0.0F && t <= 1.0F ? t : -1.0F;
    }

    public ItemStack animItem() {
        return animItem;
    }

    public ItemStack trayItem() {
        return items.get(SLOT_OUTPUT);
    }

    // ------------------------------------------------------------------ sync and save
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            ContainerHelper.saveAllItems(output, this.items, true);
            return output.buildResult();
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.milk = input.getIntOr("milk", 0);
        this.progress = input.getIntOr("progress", 0);
        this.progressTotal = input.getIntOr("progress_total", 0);
        this.storedExperience = input.getFloatOr("stored_experience", 0.0F);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, this.items);
        output.putInt("milk", milk);
        output.putInt("progress", progress);
        output.putInt("progress_total", progressTotal);
        output.putFloat("stored_experience", storedExperience);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        super.setItem(slot, stack);
        this.displayChanged = true;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        this.displayChanged = true;
        return super.removeItem(slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        this.displayChanged = true;
        return super.removeItemNoUpdate(slot);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.crazyalloy_revival.ice_cream_machine");
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
        return new IceCreamMachineMenu(containerId, inventory, this, data);
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return switch (slot) {
            case SLOT_CONE -> stack.is(ModItems.WAFER_CONE.get());
            case SLOT_MILK -> stack.is(Items.MILK_BUCKET);
            case SLOT_FLAVOR -> !stack.is(ModItems.WAFER_CONE.get()) && !stack.is(Items.MILK_BUCKET);
            default -> false;
        };
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
        // The output, and empty buckets left in the milk slot.
        return slot == SLOT_OUTPUT || (slot == SLOT_MILK && stack.is(Items.BUCKET));
    }
}
