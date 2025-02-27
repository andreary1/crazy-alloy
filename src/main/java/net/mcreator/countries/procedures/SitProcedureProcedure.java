package net.mcreator.countries.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.mcreator.countries.init.ClModAttributes;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class SitProcedureProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getTarget());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(ClModAttributes.SIT.get()) && (entity instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false)) {
			if (entity.getPersistentData().getBoolean("Sitting") == false) {
				entity.getPersistentData().putBoolean("Sitting", true);
			} else {
				entity.getPersistentData().putBoolean("Sitting", false);
			}
		}
	}
}
