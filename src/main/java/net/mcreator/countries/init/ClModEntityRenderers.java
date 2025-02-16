
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.mcreator.countries.client.renderer.LollipopGuyRenderer;
import net.mcreator.countries.client.renderer.JellySharkRenderer;
import net.mcreator.countries.client.renderer.GingerbreadSoldierRenderer;
import net.mcreator.countries.client.renderer.CottonCandyTornadoRenderer;
import net.mcreator.countries.client.renderer.CandyTubeDogRenderer;
import net.mcreator.countries.client.renderer.BrownSugarRhinoRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ClModEntities.CANDY_TUBE_DOG.get(), CandyTubeDogRenderer::new);
		event.registerEntityRenderer(ClModEntities.JELLY_SHARK.get(), JellySharkRenderer::new);
		event.registerEntityRenderer(ClModEntities.LOLLIPOP_GUY.get(), LollipopGuyRenderer::new);
		event.registerEntityRenderer(ClModEntities.BROWN_SUGAR_RHINO.get(), BrownSugarRhinoRenderer::new);
		event.registerEntityRenderer(ClModEntities.GINGERBREAD_SOLDIER.get(), GingerbreadSoldierRenderer::new);
		event.registerEntityRenderer(ClModEntities.GINGERBREAD_SOLDIER_PROJECTILE.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ClModEntities.COTTON_CANDY_TORNADO.get(), CottonCandyTornadoRenderer::new);
	}
}
