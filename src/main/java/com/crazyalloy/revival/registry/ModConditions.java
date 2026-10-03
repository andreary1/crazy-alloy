package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.worldgen.StructureFrequencyCondition;
import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
            DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, CrazyAlloyRevival.MOD_ID);

    public static final Supplier<MapCodec<StructureFrequencyCondition>> STRUCTURE_FREQUENCY =
            CONDITION_CODECS.register("structure_frequency", () -> StructureFrequencyCondition.CODEC);

    private ModConditions() {}
}
