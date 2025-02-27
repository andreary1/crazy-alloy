
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.GingerbreadKingEntity;
import net.mcreator.countries.client.model.Modelgingerbreadking;

import com.mojang.blaze3d.vertex.PoseStack;

public class GingerbreadKingRenderer extends MobRenderer<GingerbreadKingEntity, Modelgingerbreadking<GingerbreadKingEntity>> {
	public GingerbreadKingRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgingerbreadking<GingerbreadKingEntity>(context.bakeLayer(Modelgingerbreadking.LAYER_LOCATION)), 0.8f);
	}

	@Override
	protected void scale(GingerbreadKingEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.3f, 1.3f, 1.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(GingerbreadKingEntity entity) {
		return new ResourceLocation("cl:textures/entities/gingerbreadking.png");
	}
}
