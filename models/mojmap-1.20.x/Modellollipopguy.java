// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modellollipopguy<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "lollipopguy"), "main");
	private final ModelPart rightarm;
	private final ModelPart head;
	private final ModelPart leftleg;
	private final ModelPart leftarm;
	private final ModelPart rightleg;
	private final ModelPart bb_main;

	public Modellollipopguy(ModelPart root) {
		this.rightarm = root.getChild("rightarm");
		this.head = root.getChild("head");
		this.leftleg = root.getChild("leftleg");
		this.leftarm = root.getChild("leftarm");
		this.rightleg = root.getChild("rightleg");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rightarm = partdefinition.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(20, 19)
				.addBox(-5.0F, 4.0F, -1.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-0.5F, 6.0F, 0.0F));

		PartDefinition cube_r1 = rightarm.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(8, 23).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-4.5F, 5.0F, -1.0F, 0.9599F, 0.0F, 0.0F));

		PartDefinition cube_r2 = rightarm
				.addOrReplaceChild("cube_r2",
						CubeListBuilder.create().texOffs(4, 23).addBox(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(-4.5F, 4.0F, 0.0F, 0.9599F, 0.0F, 0.0F));

		PartDefinition cube_r3 = rightarm
				.addOrReplaceChild("cube_r3",
						CubeListBuilder.create().texOffs(20, 7).addBox(-6.5711F, -1.0F, -0.5F, 7.0F, 1.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.5F, 1.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(20, 15)
						.addBox(-3.0F, -1.0F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 15)
						.addBox(-2.0F, -13.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(16, 22)
						.addBox(3.0F, -7.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 17)
						.addBox(-3.0F, -8.0F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 7)
						.addBox(-3.5F, -7.5F, -0.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-3.0F, -9.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 23)
						.addBox(-4.0F, -7.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition leftleg = partdefinition.addOrReplaceChild("leftleg", CubeListBuilder.create(),
				PartPose.offset(0.0F, 19.0F, 0.0F));

		PartDefinition cube_r4 = leftleg
				.addOrReplaceChild("cube_r4",
						CubeListBuilder.create().texOffs(20, 9).addBox(-0.2218F, -0.7929F, -0.5F, 7.0F, 1.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition leftarm = partdefinition.addOrReplaceChild("leftarm", CubeListBuilder.create(),
				PartPose.offset(0.5F, 6.0F, 0.0F));

		PartDefinition cube_r5 = leftarm
				.addOrReplaceChild("cube_r5",
						CubeListBuilder.create().texOffs(20, 11).addBox(-0.4289F, -1.0F, -0.5F, 7.0F, 1.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(-0.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition rightleg = partdefinition.addOrReplaceChild("rightleg", CubeListBuilder.create(),
				PartPose.offset(0.0F, 19.0F, 0.0F));

		PartDefinition cube_r6 = rightleg
				.addOrReplaceChild("cube_r6",
						CubeListBuilder.create().texOffs(20, 13).addBox(-6.7782F, -0.7929F, -0.5F, 7.0F, 1.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(16, 7)
				.addBox(-0.5F, -19.0F, -0.5F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		rightarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftleg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftarm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightleg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.rightleg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.rightarm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.leftleg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.leftarm.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
	}
}