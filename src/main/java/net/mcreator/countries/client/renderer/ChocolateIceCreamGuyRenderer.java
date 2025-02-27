
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.ChocolateIceCreamGuyEntity;
import net.mcreator.countries.client.model.Modellivingicecream;

public class ChocolateIceCreamGuyRenderer extends MobRenderer<ChocolateIceCreamGuyEntity, Modellivingicecream<ChocolateIceCreamGuyEntity>> {
	public ChocolateIceCreamGuyRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellivingicecream<ChocolateIceCreamGuyEntity>(context.bakeLayer(Modellivingicecream.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(ChocolateIceCreamGuyEntity entity) {
		return new ResourceLocation("cl:textures/entities/icecreamguy.png");
	}
}
