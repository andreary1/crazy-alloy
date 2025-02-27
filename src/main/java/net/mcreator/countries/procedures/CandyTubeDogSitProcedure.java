package net.mcreator.countries.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.entity.Entity;

import net.mcreator.countries.entity.CandyTubeDogEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CandyTubeDogSitProcedure {
	@SubscribeEvent
	public static void onEntityTick(LivingEvent.LivingTickEvent event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof CandyTubeDogEntity) {
			if (entity.getPersistentData().getBoolean("Sitting") == true) {
				if (entity instanceof CandyTubeDogEntity) {
					((CandyTubeDogEntity) entity).setAnimation("animation.sugartubedog.sit");
				}
			} else {
				if (entity instanceof CandyTubeDogEntity) {
					((CandyTubeDogEntity) entity).setAnimation("empty");
				}
			}
		}
	}
}
