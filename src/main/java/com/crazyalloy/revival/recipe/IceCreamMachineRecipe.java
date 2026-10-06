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
 * Ice Cream Machine recipe: the flavour ingredient decides which ice cream comes out. Every serving also uses one
 * Wafer Cone and one serving of milk from the machine's tank.
 *
 * <pre>
 * { "type": "crazyalloy_revival:ice_cream_machine",
 *   "flavor": "minecraft:sweet_berries",
 *   "result": { "id": "crazyalloy_revival:strawberry_ice_cream" },
 *   "processing_time": 80, "experience": 0.2 }
 * </pre>
 */
public class IceCreamMachineRecipe implements Recipe<IceCreamMachineInput> {
    public static final MapCodec<IceCreamMachineRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("flavor").forGetter(r -> r.flavor),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("processing_time", 80).forGetter(r -> r.processingTime),
            Codec.floatRange(0.0F, 10.0F).optionalFieldOf("experience", 0.0F).forGetter(r -> r.experience)
    ).apply(inst, IceCreamMachineRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, IceCreamMachineRecipe> STREAM_CODEC =
            ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    private final Ingredient flavor;
    private final ItemStackTemplate result;
    private final int processingTime;
    private final float experience;

    public IceCreamMachineRecipe(Ingredient flavor, ItemStackTemplate result, int processingTime, float experience) {
        this.flavor = flavor;
        this.result = result;
        this.processingTime = processingTime;
        this.experience = experience;
    }

    @Override
    public boolean matches(IceCreamMachineInput input, Level level) {
        return flavor.test(input.flavor());
    }

    @Override
    public ItemStack assemble(IceCreamMachineInput input) {
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

    @Override
    public RecipeSerializer<IceCreamMachineRecipe> getSerializer() {
        return ModRecipes.ICE_CREAM_MACHINE_SERIALIZER.get();
    }

    @Override
    public RecipeType<IceCreamMachineRecipe> getType() {
        return ModRecipes.ICE_CREAM_MACHINE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
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
        return ModRecipes.ICE_CREAM_MACHINE_CATEGORY.get();
    }
}
