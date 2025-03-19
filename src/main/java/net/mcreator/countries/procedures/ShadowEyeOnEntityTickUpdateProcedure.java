package net.mcreator.countries.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.SimpleParticleType;

import net.mcreator.countries.init.ClModParticleTypes;
import net.mcreator.countries.ClMod;

public class ShadowEyeOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		ClMod.queueServerWork(5, () -> {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (ClModParticleTypes.SHADOW_PARTICLE.get()), x, y, z, 4, 0.6, 0.6, 0.6, 0.1);
		});
	}
}
