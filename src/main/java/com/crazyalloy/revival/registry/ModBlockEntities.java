package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.block.entity.ChocolateFactoryBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CrazyAlloyRevival.MOD_ID);

    public static final Supplier<BlockEntityType<ChocolateFactoryBlockEntity>> CHOCOLATE_FACTORY = BLOCK_ENTITIES.register("chocolate_factory",
            () -> new BlockEntityType<>(ChocolateFactoryBlockEntity::new, ModBlocks.CHOCOLATE_FACTORY.get()));

    private ModBlockEntities() {}
}
