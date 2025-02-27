
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.VanillaIceCreamGuyEntity;
import net.mcreator.countries.client.model.Modellivingicecream;

public class VanillaIceCreamGuyRenderer extends MobRenderer<VanillaIceCreamGuyEntity, Modellivingicecream<VanillaIceCreamGuyEntity>> {
	public VanillaIceCreamGuyRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellivingicecream<VanillaIceCreamGuyEntity>(context.bakeLayer(Modellivingicecream.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(VanillaIceCreamGuyEntity entity) {
		return new ResourceLocation("cl:textures/entities/vanillaicecreamguy.png");
	}
}
