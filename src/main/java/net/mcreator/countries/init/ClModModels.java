
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.countries.client.model.Modelwizard;
import net.mcreator.countries.client.model.Modeltroll;
import net.mcreator.countries.client.model.Modelogre;
import net.mcreator.countries.client.model.Modelmushroomman;
import net.mcreator.countries.client.model.Modelmushroomcyclops;
import net.mcreator.countries.client.model.Modelminerhelmet;
import net.mcreator.countries.client.model.Modellollipopguy;
import net.mcreator.countries.client.model.Modellivingicecream;
import net.mcreator.countries.client.model.Modeljellysnakedead;
import net.mcreator.countries.client.model.Modelicecreamzombie;
import net.mcreator.countries.client.model.Modelicecreamman;
import net.mcreator.countries.client.model.Modelgingerbreadwarrior;
import net.mcreator.countries.client.model.Modelgingerbreadsoldier;
import net.mcreator.countries.client.model.Modelgingerbreadking;
import net.mcreator.countries.client.model.Modelgingerbreadhelper;
import net.mcreator.countries.client.model.Modeleyespider;
import net.mcreator.countries.client.model.Modeldwarf;
import net.mcreator.countries.client.model.Modelbubblegum;
import net.mcreator.countries.client.model.Modelbrownsugarhino;
import net.mcreator.countries.client.model.Modelangryicecream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ClModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelwizard.LAYER_LOCATION, Modelwizard::createBodyLayer);
		event.registerLayerDefinition(Modelicecreamman.LAYER_LOCATION, Modelicecreamman::createBodyLayer);
		event.registerLayerDefinition(Modelgingerbreadhelper.LAYER_LOCATION, Modelgingerbreadhelper::createBodyLayer);
		event.registerLayerDefinition(Modeleyespider.LAYER_LOCATION, Modeleyespider::createBodyLayer);
		event.registerLayerDefinition(Modeljellysnakedead.LAYER_LOCATION, Modeljellysnakedead::createBodyLayer);
		event.registerLayerDefinition(Modelbrownsugarhino.LAYER_LOCATION, Modelbrownsugarhino::createBodyLayer);
		event.registerLayerDefinition(Modelminerhelmet.LAYER_LOCATION, Modelminerhelmet::createBodyLayer);
		event.registerLayerDefinition(Modeldwarf.LAYER_LOCATION, Modeldwarf::createBodyLayer);
		event.registerLayerDefinition(Modelgingerbreadking.LAYER_LOCATION, Modelgingerbreadking::createBodyLayer);
		event.registerLayerDefinition(Modelmushroomman.LAYER_LOCATION, Modelmushroomman::createBodyLayer);
		event.registerLayerDefinition(Modelbubblegum.LAYER_LOCATION, Modelbubblegum::createBodyLayer);
		event.registerLayerDefinition(Modellollipopguy.LAYER_LOCATION, Modellollipopguy::createBodyLayer);
		event.registerLayerDefinition(Modelgingerbreadwarrior.LAYER_LOCATION, Modelgingerbreadwarrior::createBodyLayer);
		event.registerLayerDefinition(Modelicecreamzombie.LAYER_LOCATION, Modelicecreamzombie::createBodyLayer);
		event.registerLayerDefinition(Modelogre.LAYER_LOCATION, Modelogre::createBodyLayer);
		event.registerLayerDefinition(Modelmushroomcyclops.LAYER_LOCATION, Modelmushroomcyclops::createBodyLayer);
		event.registerLayerDefinition(Modellivingicecream.LAYER_LOCATION, Modellivingicecream::createBodyLayer);
		event.registerLayerDefinition(Modeltroll.LAYER_LOCATION, Modeltroll::createBodyLayer);
		event.registerLayerDefinition(Modelgingerbreadsoldier.LAYER_LOCATION, Modelgingerbreadsoldier::createBodyLayer);
		event.registerLayerDefinition(Modelangryicecream.LAYER_LOCATION, Modelangryicecream::createBodyLayer);
	}
}
