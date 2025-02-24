
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.mcreator.countries.client.renderer.SegwayRenderer;
import net.mcreator.countries.client.renderer.RouladeMonsterRenderer;
import net.mcreator.countries.client.renderer.LollipopGuyRenderer;
import net.mcreator.countries.client.renderer.JellySnakeRenderer;
import net.mcreator.countries.client.renderer.JellySharkRenderer;
import net.mcreator.countries.client.renderer.GuardDinosaurRenderer;
import net.mcreator.countries.client.renderer.GingerbreadWarriorRenderer;
import net.mcreator.countries.client.renderer.GingerbreadSoldierRenderer;
import net.mcreator.countries.client.renderer.DeadSnakeProjRenderer;
import net.mcreator.countries.client.renderer.CottonCandyTornadoRenderer;
import net.mcreator.countries.client.renderer.CandyTubeDogRenderer;
import net.mcreator.countries.client.renderer.BubbalooCreeperRenderer;
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
		event.registerEntityRenderer(ClModEntities.COTTON_CANDY_TORNADO.get(), CottonCandyTornadoRenderer::new);
		event.registerEntityRenderer(ClModEntities.GINGERBREAD_WARRIOR.get(), GingerbreadWarriorRenderer::new);
		event.registerEntityRenderer(ClModEntities.COTTON_CANDY_PROJ.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ClModEntities.JELLY_SNAKE.get(), JellySnakeRenderer::new);
		event.registerEntityRenderer(ClModEntities.DEAD_SNAKE_PROJ.get(), DeadSnakeProjRenderer::new);
		event.registerEntityRenderer(ClModEntities.ROULADE_MONSTER.get(), RouladeMonsterRenderer::new);
		event.registerEntityRenderer(ClModEntities.BUBBALOO_CREEPER.get(), BubbalooCreeperRenderer::new);
		event.registerEntityRenderer(ClModEntities.SEGWAY.get(), SegwayRenderer::new);
		event.registerEntityRenderer(ClModEntities.BULLET_PROJ.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ClModEntities.GUARD_DINOSAUR.get(), GuardDinosaurRenderer::new);
	}
}
