package net.mcreator.countries.procedures;

import net.minecraft.world.entity.Entity;

public class InsertSitProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return !entity.getPersistentData().getBoolean("Sitting");
	}
}
