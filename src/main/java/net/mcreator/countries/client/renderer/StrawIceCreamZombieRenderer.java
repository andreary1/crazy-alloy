
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.StrawIceCreamZombieEntity;
import net.mcreator.countries.client.model.Modelicecreamzombie;

public class StrawIceCreamZombieRenderer extends MobRenderer<StrawIceCreamZombieEntity, Modelicecreamzombie<StrawIceCreamZombieEntity>> {
	public StrawIceCreamZombieRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelicecreamzombie<StrawIceCreamZombieEntity>(context.bakeLayer(Modelicecreamzombie.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(StrawIceCreamZombieEntity entity) {
		return new ResourceLocation("cl:textures/entities/strawicecreamzombie.png");
	}
}
