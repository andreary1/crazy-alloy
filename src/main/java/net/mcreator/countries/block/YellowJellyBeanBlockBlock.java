
package net.mcreator.countries.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.mcreator.countries.procedures.YellowJellyBeanBlockEntityWalksOnTheBlockProcedure;

public class YellowJellyBeanBlockBlock extends Block {
	public YellowJellyBeanBlockBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.SLIME_BLOCK).strength(1f, 10f).speedFactor(0.8f).jumpFactor(3f));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState blockstate, Entity entity) {
		super.stepOn(world, pos, blockstate, entity);
		YellowJellyBeanBlockEntityWalksOnTheBlockProcedure.execute(entity);
	}
}
