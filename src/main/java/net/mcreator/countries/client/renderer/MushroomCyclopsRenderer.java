
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

import net.mcreator.countries.entity.MushroomCyclopsEntity;
import net.mcreator.countries.client.model.Modelmushroomcyclops;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class MushroomCyclopsRenderer extends MobRenderer<MushroomCyclopsEntity, Modelmushroomcyclops<MushroomCyclopsEntity>> {
	public MushroomCyclopsRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelmushroomcyclops<MushroomCyclopsEntity>(context.bakeLayer(Modelmushroomcyclops.LAYER_LOCATION)), 0.5f);
		this.addLayer(new RenderLayer<MushroomCyclopsEntity, Modelmushroomcyclops<MushroomCyclopsEntity>>(this) {
			final ResourceLocation LAYER_TEXTURE = new ResourceLocation("cl:textures/entities/mushroomcyclopsglow.png");

			@Override
			public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, MushroomCyclopsEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
				VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(LAYER_TEXTURE));
				EntityModel model = new Modelmushroomcyclops(Minecraft.getInstance().getEntityModels().bakeLayer(Modelmushroomcyclops.LAYER_LOCATION));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0), 1, 1, 1, 1);
			}
		});
	}

	@Override
	public ResourceLocation getTextureLocation(MushroomCyclopsEntity entity) {
		return new ResourceLocation("cl:textures/entities/mushroomcyclops.png");
	}
}
