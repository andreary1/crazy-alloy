
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.CreeperModel;

import net.mcreator.countries.entity.BubbalooCreeperEntity;

public class BubbalooCreeperRenderer extends MobRenderer<BubbalooCreeperEntity, CreeperModel<BubbalooCreeperEntity>> {
	public BubbalooCreeperRenderer(EntityRendererProvider.Context context) {
		super(context, new CreeperModel<BubbalooCreeperEntity>(context.bakeLayer(ModelLayers.CREEPER)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(BubbalooCreeperEntity entity) {
		return new ResourceLocation("cl:textures/entities/bubaloocreepr.png");
	}
}
