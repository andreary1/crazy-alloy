
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.MintIceCreamGuyEntity;
import net.mcreator.countries.client.model.Modellivingicecream;

public class MintIceCreamGuyRenderer extends MobRenderer<MintIceCreamGuyEntity, Modellivingicecream<MintIceCreamGuyEntity>> {
	public MintIceCreamGuyRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellivingicecream<MintIceCreamGuyEntity>(context.bakeLayer(Modellivingicecream.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(MintIceCreamGuyEntity entity) {
		return new ResourceLocation("cl:textures/entities/minticecreamguy.png");
	}
}
