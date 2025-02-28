
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.mcreator.countries.client.renderer.VanillaIceCreamZombieRenderer;
import net.mcreator.countries.client.renderer.VanillaIceCreamGuyRenderer;
import net.mcreator.countries.client.renderer.StrawberryIceCreamGuyRenderer;
import net.mcreator.countries.client.renderer.StrawIceCreamZombieRenderer;
import net.mcreator.countries.client.renderer.SegwayRenderer;
import net.mcreator.countries.client.renderer.RouladeMonsterRenderer;
import net.mcreator.countries.client.renderer.MintIceCreamZombieRenderer;
import net.mcreator.countries.client.renderer.MintIceCreamGuyRenderer;
import net.mcreator.countries.client.renderer.LollipopGuyRenderer;
import net.mcreator.countries.client.renderer.LivingIceCreamRenderer;
import net.mcreator.countries.client.renderer.JellySnakeRenderer;
import net.mcreator.countries.client.renderer.JellySharkRenderer;
import net.mcreator.countries.client.renderer.JellyBunnyRenderer;
import net.mcreator.countries.client.renderer.ImpostorCakeRenderer;
import net.mcreator.countries.client.renderer.IceCreamZombieRenderer;
import net.mcreator.countries.client.renderer.IceCreamGargoyleRenderer;
import net.mcreator.countries.client.renderer.IceCreamDragonRenderer;
import net.mcreator.countries.client.renderer.IceCreamBeastRenderer;
import net.mcreator.countries.client.renderer.GuardDinosaurRenderer;
import net.mcreator.countries.client.renderer.GrapeSpiderRenderer;
import net.mcreator.countries.client.renderer.GingerbreadWarriorRenderer;
import net.mcreator.countries.client.renderer.GingerbreadSoldierRenderer;
import net.mcreator.countries.client.renderer.GingerbreadKingRenderer;
import net.mcreator.countries.client.renderer.GingerbreadHelperRenderer;
import net.mcreator.countries.client.renderer.DeadSnakeProjRenderer;
import net.mcreator.countries.client.renderer.CottonCandyTornadoRenderer;
import net.mcreator.countries.client.renderer.ChocolateIceCreamGuyRenderer;
import net.mcreator.countries.client.renderer.ChocIceCreamZombieRenderer;
import net.mcreator.countries.client.renderer.CandyTubeDogRenderer;
import net.mcreator.countries.client.renderer.BubblegumRenderer;
import net.mcreator.countries.client.renderer.BubbalooCreeperRenderer;
import net.mcreator.countries.client.renderer.BrownSugarRhinoRenderer;
import net.mcreator.countries.client.renderer.AngryIceCreamConeRenderer;

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
		event.registerEntityRenderer(ClModEntities.GINGERBREADKING.get(), GingerbreadKingRenderer::new);
		event.registerEntityRenderer(ClModEntities.ICE_CREAM_GARGOYLE.get(), IceCreamGargoyleRenderer::new);
		event.registerEntityRenderer(ClModEntities.LIVING_ICE_CREAM.get(), LivingIceCreamRenderer::new);
		event.registerEntityRenderer(ClModEntities.CHOCOLATE_ICE_CREAM_GUY.get(), ChocolateIceCreamGuyRenderer::new);
		event.registerEntityRenderer(ClModEntities.STRAWBERRY_ICE_CREAM_GUY.get(), StrawberryIceCreamGuyRenderer::new);
		event.registerEntityRenderer(ClModEntities.VANILLA_ICE_CREAM_GUY.get(), VanillaIceCreamGuyRenderer::new);
		event.registerEntityRenderer(ClModEntities.MINT_ICE_CREAM_GUY.get(), MintIceCreamGuyRenderer::new);
		event.registerEntityRenderer(ClModEntities.ANGRY_ICE_CREAM_CONE.get(), AngryIceCreamConeRenderer::new);
		event.registerEntityRenderer(ClModEntities.BUBBLEGUM.get(), BubblegumRenderer::new);
		event.registerEntityRenderer(ClModEntities.ICE_CREAM_ZOMBIE.get(), IceCreamZombieRenderer::new);
		event.registerEntityRenderer(ClModEntities.CHOC_ICE_CREAM_ZOMBIE.get(), ChocIceCreamZombieRenderer::new);
		event.registerEntityRenderer(ClModEntities.VANILLA_ICE_CREAM_ZOMBIE.get(), VanillaIceCreamZombieRenderer::new);
		event.registerEntityRenderer(ClModEntities.STRAW_ICE_CREAM_ZOMBIE.get(), StrawIceCreamZombieRenderer::new);
		event.registerEntityRenderer(ClModEntities.MINT_ICE_CREAM_ZOMBIE.get(), MintIceCreamZombieRenderer::new);
		event.registerEntityRenderer(ClModEntities.ICE_CREAM_BEAST.get(), IceCreamBeastRenderer::new);
		event.registerEntityRenderer(ClModEntities.GINGERBREAD_HELPER.get(), GingerbreadHelperRenderer::new);
		event.registerEntityRenderer(ClModEntities.GINGERBREAD_PROJ.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ClModEntities.BROWN_SUGAR_PROJ.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ClModEntities.GRAPE_SPIDER.get(), GrapeSpiderRenderer::new);
		event.registerEntityRenderer(ClModEntities.JELLY_BUNNY.get(), JellyBunnyRenderer::new);
		event.registerEntityRenderer(ClModEntities.IMPOSTOR_CAKE.get(), ImpostorCakeRenderer::new);
		event.registerEntityRenderer(ClModEntities.ICE_CREAM_DRAGON.get(), IceCreamDragonRenderer::new);
	}
}
