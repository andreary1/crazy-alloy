
package net.mcreator.countries.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

import net.mcreator.countries.procedures.RedJellyBeanBlockPlayerStartsToDestroyProcedure;

public class RedJellyBeanBlockBlock extends Block {
	public RedJellyBeanBlockBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.SLIME_BLOCK).strength(1f, 10f).speedFactor(0.8f).jumpFactor(3f));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	public void attack(BlockState blockstate, Level world, BlockPos pos, Player entity) {
		super.attack(blockstate, world, pos, entity);
		RedJellyBeanBlockPlayerStartsToDestroyProcedure.execute(entity);
	}
}
