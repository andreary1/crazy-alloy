
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

import net.mcreator.countries.entity.BubblegumEntity;
import net.mcreator.countries.client.model.Modelbubblegum;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class BubblegumRenderer extends MobRenderer<BubblegumEntity, Modelbubblegum<BubblegumEntity>> {
	public BubblegumRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbubblegum<BubblegumEntity>(context.bakeLayer(Modelbubblegum.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<BubblegumEntity, Modelbubblegum<BubblegumEntity>>(this) {
			final ResourceLocation LAYER_TEXTURE = new ResourceLocation("cl:textures/entities/bubblegumeyes.png");

			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, BubblegumEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(LAYER_TEXTURE));
				EntityModel model = new Modelbubblegum(Minecraft.getInstance().getEntityModels().bakeLayer(Modelbubblegum.LAYER_LOCATION));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0), 1, 1, 1, 1);
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(BubblegumEntity entity) {
		return new ResourceLocation("cl:textures/entities/bubblegum.png");
	}

	@Override
	protected boolean isBodyVisible(BubblegumEntity entity) {
		return false;
	}
}
