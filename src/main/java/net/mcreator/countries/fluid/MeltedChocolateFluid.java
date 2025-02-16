
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

public abstract class MeltedChocolateFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> ClModFluidTypes.MELTED_CHOCOLATE_TYPE.get(), () -> ClModFluids.MELTED_CHOCOLATE.get(), () -> ClModFluids.FLOWING_MELTED_CHOCOLATE.get())
			.explosionResistance(100f).tickRate(30).bucket(() -> ClModItems.MELTED_CHOCOLATE_BUCKET.get()).block(() -> (LiquidBlock) ClModBlocks.MELTED_CHOCOLATE.get());

	private MeltedChocolateFluid() {
		super(PROPERTIES);
	}

	public static class Source extends MeltedChocolateFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends MeltedChocolateFluid {
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
