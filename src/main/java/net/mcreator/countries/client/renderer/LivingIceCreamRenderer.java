
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.LivingIceCreamEntity;
import net.mcreator.countries.client.model.Modellivingicecream;

public class LivingIceCreamRenderer extends MobRenderer<LivingIceCreamEntity, Modellivingicecream<LivingIceCreamEntity>> {
	public LivingIceCreamRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellivingicecream<LivingIceCreamEntity>(context.bakeLayer(Modellivingicecream.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(LivingIceCreamEntity entity) {
		return new ResourceLocation("cl:textures/entities/icecreamguy.png");
	}
}
