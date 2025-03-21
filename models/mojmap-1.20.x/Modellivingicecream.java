// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modellivingicecream<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "livingicecream"), "main");
	private final ModelPart head;
	private final ModelPart rightleg;
	private final ModelPart leftleg;
	private final ModelPart bb_main;

	public Modellivingicecream(ModelPart root) {
		this.head = root.getChild("head");
		this.rightleg = root.getChild("rightleg");
		this.leftleg = root.getChild("leftleg");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(26, 15)
						.addBox(-3.5F, -7.5F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition rightleg = partdefinition.addOrReplaceChild("rightleg",
				CubeListBuilder.create().texOffs(24, 32)
						.addBox(-2.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 27)
						.addBox(-2.0F, 5.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-1.0F, 17.0F, 0.0F));

		PartDefinition cube_r1 = rightleg
				.addOrReplaceChild("cube_r1",
						CubeListBuilder.create().texOffs(32, 32).addBox(0.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

		PartDefinition leftleg = partdefinition.addOrReplaceChild("leftleg",
				CubeListBuilder.create().texOffs(32, 6)
						.addBox(0.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(32, 0)
						.addBox(0.0F, 5.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(1.0F, 17.0F, 0.0F));

		PartDefinition cube_r2 = leftleg
				.addOrReplaceChild("cube_r2",
						CubeListBuilder.create().texOffs(0, 33).addBox(-2.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.6545F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
				CubeListBuilder.create().texOffs(12, 27)
						.addBox(-1.5F, -9.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(26, 23)
						.addBox(-2.5F, -14.0F, -2.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 15)
						.addBox(-3.5F, -19.0F, -3.0F, 7.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightleg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftleg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.rightleg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leftleg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
	}
}