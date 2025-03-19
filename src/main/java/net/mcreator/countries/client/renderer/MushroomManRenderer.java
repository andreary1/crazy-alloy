
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.Minecraft;

import net.mcreator.countries.entity.MushroomManEntity;
import net.mcreator.countries.client.model.Modelmushroomman;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class MushroomManRenderer extends MobRenderer<MushroomManEntity, Modelmushroomman<MushroomManEntity>> {
	public MushroomManRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelmushroomman<MushroomManEntity>(context.bakeLayer(Modelmushroomman.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<MushroomManEntity, Modelmushroomman<MushroomManEntity>>(this) {
			final ResourceLocation LAYER_TEXTURE = new ResourceLocation("cl:textures/entities/mushroommanglow.png");

			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, MushroomManEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(LAYER_TEXTURE));
				EntityModel model = new Modelmushroomman(Minecraft.getInstance().getEntityModels().bakeLayer(Modelmushroomman.LAYER_LOCATION));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0), 1, 1, 1, 1);
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(MushroomManEntity entity) {
		return new ResourceLocation("cl:textures/entities/mushroomman.png");
	}
}
