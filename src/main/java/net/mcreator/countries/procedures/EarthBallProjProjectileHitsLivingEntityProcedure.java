package net.mcreator.countries.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;

public class EarthBallProjProjectileHitsLivingEntityProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		{
			Entity _ent = entity;
			_ent.teleportTo((entity.getX()), (entity.getY()), (entity.getZ()));
			if (_ent instanceof ServerPlayer _serverPlayer)
				_serverPlayer.connection.teleport((entity.getX()), (entity.getY()), (entity.getZ()), _ent.getYRot(), _ent.getXRot());
		}
		world.setBlock(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ()), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ() + 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX(), entity.getY() - 1, entity.getZ() - 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() + 1, entity.getY() - 1, entity.getZ()), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() + 1, entity.getY() - 1, entity.getZ() + 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() + 1, entity.getY() - 1, entity.getZ() - 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() - 1, entity.getY() - 1, entity.getZ()), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() - 1, entity.getY() - 1, entity.getZ() - 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() - 1, entity.getY() - 1, entity.getZ() + 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX(), entity.getY() - 2, entity.getZ()), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX(), entity.getY() - 2, entity.getZ() + 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX(), entity.getY() - 2, entity.getZ() - 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() + 1, entity.getY() - 2, entity.getZ()), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() + 1, entity.getY() - 2, entity.getZ() + 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() + 1, entity.getY() - 2, entity.getZ() - 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() - 1, entity.getY() - 2, entity.getZ()), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() - 1, entity.getY() - 2, entity.getZ() - 1), Blocks.AIR.defaultBlockState(), 3);
		world.setBlock(BlockPos.containing(entity.getX() - 1, entity.getY() - 2, entity.getZ() + 1), Blocks.AIR.defaultBlockState(), 3);
	}
}
