
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.StrawberryIceCreamGuyEntity;
import net.mcreator.countries.client.model.Modellivingicecream;

public class StrawberryIceCreamGuyRenderer extends MobRenderer<StrawberryIceCreamGuyEntity, Modellivingicecream<StrawberryIceCreamGuyEntity>> {
	public StrawberryIceCreamGuyRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellivingicecream<StrawberryIceCreamGuyEntity>(context.bakeLayer(Modellivingicecream.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(StrawberryIceCreamGuyEntity entity) {
		return new ResourceLocation("cl:textures/entities/strawicecreamguy.png");
	}
}
