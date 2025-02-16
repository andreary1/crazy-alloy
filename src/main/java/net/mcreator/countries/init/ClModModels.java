
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.countries.client.model.Modellollipopguy;
import net.mcreator.countries.client.model.Modelgingerbreadsoldier;
import net.mcreator.countries.client.model.Modelbrownsugarhino;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ClModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modellollipopguy.LAYER_LOCATION, Modellollipopguy::createBodyLayer);
		event.registerLayerDefinition(Modelgingerbreadsoldier.LAYER_LOCATION, Modelgingerbreadsoldier::createBodyLayer);
		event.registerLayerDefinition(Modelbrownsugarhino.LAYER_LOCATION, Modelbrownsugarhino::createBodyLayer);
	}
}
