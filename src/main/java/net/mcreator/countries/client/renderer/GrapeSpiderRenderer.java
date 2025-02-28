
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.GrapeSpiderEntity;
import net.mcreator.countries.client.model.Modeleyespider;

public class GrapeSpiderRenderer extends MobRenderer<GrapeSpiderEntity, Modeleyespider<GrapeSpiderEntity>> {
	public GrapeSpiderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeleyespider<GrapeSpiderEntity>(context.bakeLayer(Modeleyespider.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(GrapeSpiderEntity entity) {
		return new ResourceLocation("cl:textures/entities/eyespider.png");
	}
}
