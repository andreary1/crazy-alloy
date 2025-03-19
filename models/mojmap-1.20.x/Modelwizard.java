// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelwizard<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "wizard"), "main");
	private final ModelPart head;
	private final ModelPart leftarm;
	private final ModelPart rightarm;
	private final ModelPart bb_main;

	public Modelwizard(ModelPart root) {
		this.head = root.getChild("head");
		this.leftarm = root.getChild("leftarm");
		this.rightarm = root.getChild("rightarm");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 27)
						.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(60, 39)
						.addBox(-1.0F, 6.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 15)
						.addBox(-4.5F, -10.0F, -4.5F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(20, 63)
						.addBox(-3.0F, -3.0F, -5.0F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 60)
						.addBox(-2.0F, 5.0F, -5.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(0, 43).addBox(-4.0F, -7.0F, -2.0F, 8.0F, 3.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -5.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(20, 54).addBox(-2.0F, -7.0F, 2.5F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.0F, -14.0F, -3.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(60, 32).addBox(-1.0F, -7.0F, 0.5F, 4.0F, 3.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, -10.0F, -3.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(44, 0).addBox(-3.0F, -7.0F, -1.5F, 6.0F, 3.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -7.0F, -3.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition leftarm = partdefinition.addOrReplaceChild("leftarm", CubeListBuilder.create(),
				PartPose.offset(4.0F, 4.0F, 0.0F));

		PartDefinition cube_r5 = leftarm
				.addOrReplaceChild("cube_r5",
						CubeListBuilder.create().texOffs(0, 60).addBox(-5.165F, -0.0585F, -3.5F, 5.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(3.0F, 8.0F, 1.0F, 0.0F, 0.0F, -1.8326F));

		PartDefinition cube_r6 = leftarm
				.addOrReplaceChild("cube_r6",
						CubeListBuilder.create().texOffs(44, 9).addBox(-5.445F, -0.0862F, -3.5F, 5.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(5.0F, 8.0F, 1.0F, 0.0F, 0.0F, -1.4399F));

		PartDefinition cube_r7 = leftarm.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(32, 46).addBox(0.0F, 0.0F, -3.5F, 5.0F, 12.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.0F, 3.0F, 1.0F, 0.0F, 0.0F, -0.8639F));

		PartDefinition rightarm = partdefinition.addOrReplaceChild("rightarm", CubeListBuilder.create(),
				PartPose.offset(-4.0F, 4.0F, 0.0F));

		PartDefinition cube_r8 = rightarm
				.addOrReplaceChild("cube_r8",
						CubeListBuilder.create().texOffs(60, 26).addBox(0.445F, -0.0862F, -3.5F, 5.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(-5.0F, 8.0F, 1.0F, 0.0F, 0.0F, 1.4399F));

		PartDefinition cube_r9 = rightarm
				.addOrReplaceChild("cube_r9",
						CubeListBuilder.create().texOffs(52, 46).addBox(-5.0F, 0.0F, -3.5F, 5.0F, 12.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(4.0F, 3.0F, 1.0F, 0.0F, 0.0F, 0.8639F));

		PartDefinition cube_r10 = rightarm
				.addOrReplaceChild("cube_r10",
						CubeListBuilder.create().texOffs(0, 54).addBox(0.165F, -0.0585F, -3.5F, 5.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(-3.0F, 8.0F, 1.0F, 0.0F, 0.0F, 1.8326F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-6.0F, -5.0F, -5.0F, 12.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(0, 15)
						.addBox(-5.0F, -9.0F, -4.0F, 10.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(32, 27)
						.addBox(-4.0F, -22.0F, -3.0F, 8.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.rightarm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.leftarm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
	}
}