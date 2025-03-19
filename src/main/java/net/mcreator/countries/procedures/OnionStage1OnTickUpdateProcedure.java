package net.mcreator.countries.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.countries.init.ClModBlocks;

public class OnionStage1OnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		boolean hydrated = false;
		double rand = 0;
		double count = 0;
		double row = 0;
		double points = 0;
		if ((world.getMaxLocalRawBrightness(BlockPos.containing(x + 0.5, y + 1.5, z + 0.5)) > 7 || world.canSeeSkyFromBelowWater(BlockPos.containing(x, y, z)))
				&& (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.FARMLAND) {
			if (world.getMaxLocalRawBrightness(BlockPos.containing(x + 0.5, y + 1.5, z + 0.5)) >= 9) {
				rand = Math.random();
				points = 0.25;
				if (rand <= points) {
					if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ClModBlocks.ONION_STAGE_1.get()) {
						world.setBlock(BlockPos.containing(x, y, z), ClModBlocks.ONION_STAGE_2.get().defaultBlockState(), 3);
					} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ClModBlocks.ONION_STAGE_2.get()) {
						world.setBlock(BlockPos.containing(x, y, z), ClModBlocks.ONION_STAGE_3.get().defaultBlockState(), 3);
					} else if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ClModBlocks.ONION_STAGE_3.get()) {
						world.setBlock(BlockPos.containing(x, y, z), ClModBlocks.ONION_STAGE_4.get().defaultBlockState(), 3);
					}
				}
			}
		} else {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.crop.break")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.crop.break")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
		}
	}
}
