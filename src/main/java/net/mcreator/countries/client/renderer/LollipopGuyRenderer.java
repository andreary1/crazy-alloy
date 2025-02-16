
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.LollipopGuyEntity;
import net.mcreator.countries.client.model.Modellollipopguy;

public class LollipopGuyRenderer extends MobRenderer<LollipopGuyEntity, Modellollipopguy<LollipopGuyEntity>> {
	public LollipopGuyRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellollipopguy<LollipopGuyEntity>(context.bakeLayer(Modellollipopguy.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(LollipopGuyEntity entity) {
		return new ResourceLocation("cl:textures/entities/lollipopman.png");
	}
}
