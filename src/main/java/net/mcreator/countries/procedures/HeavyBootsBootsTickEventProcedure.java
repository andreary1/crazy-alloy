package net.mcreator.countries.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;

public class HeavyBootsBootsTickEventProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getDeltaMovement().y() > 1) {
			entity.setDeltaMovement(new Vec3(0, (entity.getDeltaMovement().y() * 0.5), 0));
		}
	}
}
