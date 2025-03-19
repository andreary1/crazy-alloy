package net.mcreator.countries.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

import net.mcreator.countries.init.ClModBlocks;

public class OnionStage3OnBoneMealSuccessProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.setBlock(BlockPos.containing(x, y, z), ClModBlocks.ONION_STAGE_4.get().defaultBlockState(), 3);
	}
}
