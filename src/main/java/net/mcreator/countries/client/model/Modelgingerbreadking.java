package net.mcreator.countries.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
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
public class Modelgingerbreadking<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("cl", "modelgingerbreadking"), "main");
	public final ModelPart rightleg;
	public final ModelPart leftleg;
	public final ModelPart head;
	public final ModelPart crown;
	public final ModelPart rightarm;
	public final ModelPart leftarm;
	public final ModelPart bb_main;

	public Modelgingerbreadking(ModelPart root) {
		this.rightleg = root.getChild("rightleg");
		this.leftleg = root.getChild("leftleg");
		this.head = root.getChild("head");
		this.crown = this.head.getChild("crown");
		this.rightarm = root.getChild("rightarm");
		this.leftarm = root.getChild("leftarm");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition rightleg = partdefinition.addOrReplaceChild("rightleg", CubeListBuilder.create(), PartPose.offset(-6.0F, 18.0F, -1.0F));
		PartDefinition cube_r1 = rightleg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(44, 82).addBox(-3.0F, 4.0F, -15.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, 0.3927F, 0.0F));
		PartDefinition cube_r2 = rightleg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 59).addBox(-3.0F, 0.0F, -15.0F, 8.0F, 6.0F, 17.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3927F, 0.0F));
		PartDefinition leftleg = partdefinition.addOrReplaceChild("leftleg", CubeListBuilder.create(), PartPose.offset(6.0F, 18.0F, -1.0F));
		PartDefinition cube_r3 = leftleg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(72, 82).addBox(-5.0F, 4.0F, -15.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.3927F, 0.0F));
		PartDefinition cube_r4 = leftleg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 36).addBox(-5.0F, 0.0F, -15.0F, 8.0F, 6.0F, 17.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));
		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 72).addBox(-6.0F, -8.0F, -5.0F, 12.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 90).addBox(5.5F, -7.5F, -4.5F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(68, 90)
						.addBox(-6.5F, -7.5F, -4.5F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(72, 0).addBox(-5.5F, 0.0F, -4.5F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(72, 10)
						.addBox(-5.5F, -9.0F, -4.5F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(72, 10).mirror().addBox(-5.5F, -9.0F, -4.5F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 72).mirror()
						.addBox(-6.0F, -8.0F, -5.0F, 12.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(72, 0).mirror().addBox(-5.5F, 0.0F, -4.5F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition crown = head.addOrReplaceChild("crown",
				CubeListBuilder.create().texOffs(88, 90).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(44, 75).addBox(-2.5F, -1.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(56, 74)
						.addBox(0.5F, -1.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(50, 72).addBox(-3.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(56, 72)
						.addBox(-2.5F, -1.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(44, 72).addBox(2.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(50, 75)
						.addBox(0.5F, -1.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(88, 90).mirror().addBox(-3.0F, 0.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(0.0F, -10.0F, 0.0F));
		PartDefinition rightarm = partdefinition.addOrReplaceChild("rightarm", CubeListBuilder.create(), PartPose.offset(-9.0F, 0.0F, 0.0F));
		PartDefinition cube_r5 = rightarm.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 90).addBox(-5.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.0F, 10.0F, 0.0F, 0.0F, 0.0F, -0.829F));
		PartDefinition cube_r6 = rightarm.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(72, 28).addBox(-12.0F, -2.0F, -2.0F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, 2.0F, 0.0F, 0.0F, 0.0F, -0.829F));
		PartDefinition leftarm = partdefinition.addOrReplaceChild("leftarm", CubeListBuilder.create(), PartPose.offset(9.0F, 0.0F, 0.0F));
		PartDefinition cube_r7 = leftarm.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(24, 90).addBox(-1.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0F, 10.0F, 0.0F, 0.0F, 0.0F, 0.829F));
		PartDefinition cube_r8 = leftarm.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(72, 20).addBox(-1.0F, -2.0F, -2.0F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.829F));
		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -25.0F, -9.0F, 18.0F, 18.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(0, 36)
				.addBox(-8.0F, -26.5F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 54).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rightleg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftleg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.rightleg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.rightarm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.leftleg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.leftarm.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
	}
}
