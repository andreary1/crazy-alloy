package net.mcreator.countries.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modeleyespider<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cl", "modeleyespider"), "main");
	public final ModelPart head;
	public final ModelPart leg0;
	public final ModelPart leg1;
	public final ModelPart leg2;
	public final ModelPart leg3;
	public final ModelPart leg4;
	public final ModelPart leg5;
	public final ModelPart leg6;
	public final ModelPart leg7;

	public Modeleyespider(ModelPart root) {
		this.head = root.getChild("head");
		this.leg0 = root.getChild("leg0");
		this.leg1 = root.getChild("leg1");
		this.leg2 = root.getChild("leg2");
		this.leg3 = root.getChild("leg3");
		this.leg4 = root.getChild("leg4");
		this.leg5 = root.getChild("leg5");
		this.leg6 = root.getChild("leg6");
		this.leg7 = root.getChild("leg7");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(32, 4).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(46, 23).addBox(-3.0F, -5.0F, -4.0F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 15.0F, 0.0F));
		PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0F, 15.0F, 2.0F, 0.0F, 0.7854F, -0.7854F));
		PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0F, 15.0F, 2.0F, 0.0F, -0.7854F, 0.7854F));
		PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0F, 15.0F, 1.0F, 0.0F, 0.2618F, -0.6109F));
		PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0F, 15.0F, 1.0F, 0.0F, -0.2618F, 0.6109F));
		PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0F, 15.0F, 0.0F, 0.0F, -0.2618F, -0.6109F));
		PartDefinition leg5 = partdefinition.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0F, 15.0F, 0.0F, 0.0F, 0.2618F, 0.6109F));
		PartDefinition leg6 = partdefinition.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F, 0.0F, -0.7854F, -0.7854F));
		PartDefinition leg7 = partdefinition.addOrReplaceChild("leg7", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0F, 15.0F, -1.0F, 0.0F, 0.7854F, 0.7854F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg0.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
