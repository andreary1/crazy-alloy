package net.mcreator.countries.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.network.ClModVariables;

public class LightningButtonProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof ServerPlayer _plr0 && _plr0.level() instanceof ServerLevel && _plr0.getAdvancements().getOrStartProgress(_plr0.server.getAdvancements().getAdvancement(new ResourceLocation("cl:lightning_power"))).isDone()) {
			ClModVariables.MapVariables.get(world).SpellNumber = 6;
			ClModVariables.MapVariables.get(world).syncData(world);
			if (entity instanceof Player _player)
				_player.closeContainer();
		}
	}
}
