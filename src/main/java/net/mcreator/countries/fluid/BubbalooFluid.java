
package net.mcreator.countries.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import net.mcreator.countries.init.ClModItems;
import net.mcreator.countries.init.ClModFluids;
import net.mcreator.countries.init.ClModFluidTypes;
import net.mcreator.countries.init.ClModBlocks;

public abstract class BubbalooFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> ClModFluidTypes.BUBBALOO_TYPE.get(), () -> ClModFluids.BUBBALOO.get(), () -> ClModFluids.FLOWING_BUBBALOO.get()).explosionResistance(100f)
			.tickRate(20).bucket(() -> ClModItems.BUBBALOO_BUCKET.get()).block(() -> (LiquidBlock) ClModBlocks.BUBBALOO.get());

	private BubbalooFluid() {
		super(PROPERTIES);
	}

	public static class Source extends BubbalooFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends BubbalooFluid {
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}
}
