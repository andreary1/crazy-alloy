
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.TrollEntity;
import net.mcreator.countries.client.model.Modeltroll;

public class TrollRenderer extends MobRenderer<TrollEntity, Modeltroll<TrollEntity>> {
	public TrollRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeltroll<TrollEntity>(context.bakeLayer(Modeltroll.LAYER_LOCATION)), 0.8f);
	}

	@Override
	public ResourceLocation getTextureLocation(TrollEntity entity) {
		return new ResourceLocation("cl:textures/entities/troll.png");
	}
}
