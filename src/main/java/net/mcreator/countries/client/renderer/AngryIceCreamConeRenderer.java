
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.AngryIceCreamConeEntity;
import net.mcreator.countries.client.model.Modelangryicecream;

public class AngryIceCreamConeRenderer extends MobRenderer<AngryIceCreamConeEntity, Modelangryicecream<AngryIceCreamConeEntity>> {
	public AngryIceCreamConeRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelangryicecream<AngryIceCreamConeEntity>(context.bakeLayer(Modelangryicecream.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AngryIceCreamConeEntity entity) {
		return new ResourceLocation("cl:textures/entities/minticecreamguy.png");
	}
}
