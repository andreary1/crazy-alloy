package com.crazyalloy.revival.entity;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.ServerLevelAccessor;

/** Shared spawn checks for Crazy Alloy creatures. */
public final class SpawnRules {
    private SpawnRules() {}

    /** Spawners and eggs always pass; natural spawns pass with the configured chance. */
    public static boolean chance(EntitySpawnReason reason, RandomSource random, Supplier<Double> configChance) {
        return EntitySpawnReason.isSpawner(reason) || reason == EntitySpawnReason.SPAWN_ITEM_USE || reason == EntitySpawnReason.COMMAND
                || random.nextDouble() < configChance.get();
    }

    /** Daytime creatures: bright, solid ground. */
    public static boolean brightGround(ServerLevelAccessor level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) > 8 && level.getBlockState(pos.below()).isSolid();
    }
}
