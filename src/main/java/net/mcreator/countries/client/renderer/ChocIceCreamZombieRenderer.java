
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.ChocIceCreamZombieEntity;
import net.mcreator.countries.client.model.Modelicecreamzombie;

public class ChocIceCreamZombieRenderer extends MobRenderer<ChocIceCreamZombieEntity, Modelicecreamzombie<ChocIceCreamZombieEntity>> {
	public ChocIceCreamZombieRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelicecreamzombie<ChocIceCreamZombieEntity>(context.bakeLayer(Modelicecreamzombie.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(ChocIceCreamZombieEntity entity) {
		return new ResourceLocation("cl:textures/entities/chocicecreamzombie.png");
	}
}
