package net.mcreator.countries.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.countries.init.ClModEntities;

public class LivingIceCreamOnInitialEntitySpawnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double spawn = 0;
		spawn = Math.round(Math.random() * 3);
		if (spawn == 0) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = ClModEntities.VANILLA_ICE_CREAM_GUY.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
				}
			}
		}
		if (spawn == 1) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = ClModEntities.STRAWBERRY_ICE_CREAM_GUY.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
				}
			}
		}
		if (spawn == 2) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = ClModEntities.MINT_ICE_CREAM_GUY.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
				}
			}
		}
		if (spawn == 3) {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = ClModEntities.CHOCOLATE_ICE_CREAM_GUY.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
				}
			}
		}
		if (!entity.level().isClientSide())
			entity.discard();
	}
}
