
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.countries.client.model.Modellollipopguy;
import net.mcreator.countries.client.model.Modeljellysnakedead;
import net.mcreator.countries.client.model.Modelicecreamman;
import net.mcreator.countries.client.model.Modelgingerbreadwarrior;
import net.mcreator.countries.client.model.Modelgingerbreadsoldier;
import net.mcreator.countries.client.model.Modelbrownsugarhino;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ClModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelicecreamman.LAYER_LOCATION, Modelicecreamman::createBodyLayer);
		event.registerLayerDefinition(Modellollipopguy.LAYER_LOCATION, Modellollipopguy::createBodyLayer);
		event.registerLayerDefinition(Modelgingerbreadsoldier.LAYER_LOCATION, Modelgingerbreadsoldier::createBodyLayer);
		event.registerLayerDefinition(Modelgingerbreadwarrior.LAYER_LOCATION, Modelgingerbreadwarrior::createBodyLayer);
		event.registerLayerDefinition(Modeljellysnakedead.LAYER_LOCATION, Modeljellysnakedead::createBodyLayer);
		event.registerLayerDefinition(Modelbrownsugarhino.LAYER_LOCATION, Modelbrownsugarhino::createBodyLayer);
	}
}
