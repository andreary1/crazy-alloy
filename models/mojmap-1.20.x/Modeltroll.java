// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modeltroll<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "troll"), "main");
	private final ModelPart leftarm;
	private final ModelPart rightarm;
	private final ModelPart head;
	private final ModelPart leftleg;
	private final ModelPart rightleg;
	private final ModelPart bb_main;

	public Modeltroll(ModelPart root) {
		this.leftarm = root.getChild("leftarm");
		this.rightarm = root.getChild("rightarm");
		this.head = root.getChild("head");
		this.leftleg = root.getChild("leftleg");
		this.rightleg = root.getChild("rightleg");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition leftarm = partdefinition.addOrReplaceChild("leftarm", CubeListBuilder.create(),
				PartPose.offset(7.0F, -5.0F, 1.0F));

		PartDefinition cube_r1 = leftarm.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(50, 47).addBox(0.0F, -4.2904F, -0.196F, 6.0F, 6.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.0F, 0.0F, -1.0F, -1.9427F, -0.3775F, -0.7572F));

		PartDefinition cube_r2 = leftarm.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(0, 58).addBox(0.0F, -4.2904F, -0.196F, 6.0F, 6.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, 4.0F, -3.0F, -2.0878F, -0.0869F, -0.1515F));

		PartDefinition rightarm = partdefinition.addOrReplaceChild("rightarm", CubeListBuilder.create(),
				PartPose.offset(-7.0F, -5.0F, 1.0F));

		PartDefinition cube_r3 = rightarm.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(72, 65).addBox(-6.0F, -4.2904F, -0.196F, 6.0F, 6.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, 4.0F, -3.0F, -2.0878F, 0.0869F, 0.1515F));

		PartDefinition cube_r4 = rightarm.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(36, 65).addBox(-6.0F, -4.2904F, -0.196F, 6.0F, 6.0F, 12.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.0F, 0.0F, -1.0F, -1.9427F, 0.3775F, 0.7572F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offset(0.0F, -7.0F, -5.0F));

		PartDefinition cube_r5 = head.addOrReplaceChild(
				"cube_r5", CubeListBuilder.create().texOffs(74, 0).addBox(-5.0F, -7.4805F, -13.6191F, 10.0F, 4.0F,
						10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 3.0F, 5.0F, -1.0908F, 0.0F, 0.0F));

		PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(74, 28).addBox(1.0F, -2.2801F, -3.3045F, 3.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.0F, -1.0F, -2.0F, -1.0757F, -0.2204F, -0.9637F));

		PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(94, 22).addBox(-7.0F, -2.2801F, -3.3045F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-7.0F, -4.0F, -2.0F, -1.0757F, 0.2204F, 0.9637F));

		PartDefinition cube_r8 = head.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(36, 58).addBox(-4.0F, -2.2801F, -3.3045F, 3.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-6.0F, -1.0F, -2.0F, -1.0757F, 0.2204F, 0.9637F));

		PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9",
				CubeListBuilder.create().texOffs(74, 14).addBox(-6.0F, -7.4805F, -4.6191F, 12.0F, 3.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 2.0F, -12.0F, -1.4399F, 0.0F, 0.0F));

		PartDefinition cube_r10 = head.addOrReplaceChild("cube_r10",
				CubeListBuilder.create().texOffs(48, 95).addBox(-2.0F, -7.4805F, -5.6191F, 4.0F, 7.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, -13.0F, -1.4399F, 0.0F, 0.0F));

		PartDefinition cube_r11 = head.addOrReplaceChild("cube_r11",
				CubeListBuilder.create().texOffs(74, 22).addBox(1.0F, -2.2801F, -3.3045F, 6.0F, 2.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(7.0F, -4.0F, -2.0F, -1.0757F, -0.2204F, -0.9637F));

		PartDefinition cube_r12 = head.addOrReplaceChild("cube_r12",
				CubeListBuilder.create().texOffs(48, 83).addBox(-6.0F, -5.0F, -0.5F, 12.0F, 10.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -12.0F, -1.0F, -1.4399F, 0.0F, 0.0F));

		PartDefinition cube_r13 = head.addOrReplaceChild("cube_r13",
				CubeListBuilder.create().texOffs(0, 33).addBox(-6.0F, -7.4805F, -12.6191F, 12.0F, 12.0F, 13.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, -1.4399F, 0.0F, 0.0F));

		PartDefinition leftleg = partdefinition.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(0, 76)
				.addBox(-3.0F, 4.0F, -7.0F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, 6.0F, 5.0F));

		PartDefinition cube_r14 = leftleg.addOrReplaceChild("cube_r14",
				CubeListBuilder.create().texOffs(76, 83).addBox(-5.0F, -8.0F, 0.0F, 6.0F, 8.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, 4.0F, -7.0F, -1.0036F, 0.0F, 0.0F));

		PartDefinition rightleg = partdefinition.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(24, 83)
				.addBox(-3.0F, 4.0F, -7.0F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, 6.0F, 5.0F));

		PartDefinition cube_r15 = rightleg.addOrReplaceChild("cube_r15",
				CubeListBuilder.create().texOffs(86, 47).addBox(-1.0F, -8.0F, 0.0F, 6.0F, 8.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, 4.0F, -7.0F, -1.0036F, 0.0F, 0.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r16 = bb_main.addOrReplaceChild("cube_r16",
				CubeListBuilder.create().texOffs(15, 110).addBox(-7.0F, 1.5231F, -0.8044F, 16.0F, 0.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, -15.0F, 11.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r17 = bb_main.addOrReplaceChild("cube_r17",
				CubeListBuilder.create().texOffs(15, 110).addBox(-7.0F, 1.5231F, -0.8044F, 16.0F, 0.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, -14.0F, -1.0F, -1.789F, 0.0F, 0.0F));

		PartDefinition cube_r18 = bb_main.addOrReplaceChild("cube_r18",
				CubeListBuilder.create().texOffs(50, 33).addBox(-7.0F, -7.4805F, -10.6191F, 16.0F, 3.0F, 11.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, -13.0F, -9.0F, -1.309F, 0.0F, 0.0F));

		PartDefinition cube_r19 = bb_main.addOrReplaceChild("cube_r19",
				CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.4805F, -20.6191F, 16.0F, 12.0F, 21.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, -13.0F, 3.0F, -1.309F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		leftarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftleg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightleg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.rightleg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.rightarm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.leftleg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.leftarm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
	}
}