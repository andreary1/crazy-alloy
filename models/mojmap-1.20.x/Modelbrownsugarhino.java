// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelbrownsugarhino<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "brownsugarhino"), "main");
	private final ModelPart rhino;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart leftleg2;
	private final ModelPart rightleg1;
	private final ModelPart rightleg2;
	private final ModelPart leftleg1;

	public Modelbrownsugarhino(ModelPart root) {
		this.rhino = root.getChild("rhino");
		this.head = this.rhino.getChild("head");
		this.tail = this.rhino.getChild("tail");
		this.leftleg2 = this.rhino.getChild("leftleg2");
		this.rightleg1 = this.rhino.getChild("rightleg1");
		this.rightleg2 = this.rhino.getChild("rightleg2");
		this.leftleg1 = this.rhino.getChild("leftleg1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rhino = partdefinition.addOrReplaceChild("rhino",
				CubeListBuilder.create().texOffs(60, 57)
						.addBox(-3.5F, -22.0F, -18.0F, 7.0F, 5.0F, 21.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-8.0F, -17.0F, -21.0F, 16.0F, 16.0F, 41.0F, new CubeDeformation(0.0F)).texOffs(60, 83)
						.addBox(-5.5F, -24.0F, 3.0F, 11.0F, 7.0F, 17.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 13.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition head = rhino.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offset(0.0F, -15.0F, 20.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(114, 17).addBox(-2.0F, -10.0F, 15.0F, 4.0F, 8.0F, 3.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -8.0F, -7.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(52, 93)
						.addBox(3.0F, -2.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(52, 87)
						.addBox(-5.0F, -2.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -8.0F, 3.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(52, 107).addBox(-3.0F, -10.0F, 13.0F, 6.0F, 12.0F, 5.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(0, 57).addBox(-5.0F, -8.0F, 0.0F, 10.0F, 10.0F, 20.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition tail = rhino.addOrReplaceChild("tail", CubeListBuilder.create(),
				PartPose.offset(0.0F, -13.0F, -21.0F));

		PartDefinition cube_r5 = tail
				.addOrReplaceChild("cube_r5",
						CubeListBuilder.create().texOffs(114, 28).addBox(-0.5F, 0.0F, -8.0F, 1.0F, 1.0F, 7.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition cube_r6 = tail.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(52, 99).addBox(0.0F, 0.0F, -3.0F, 2.0F, 2.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.0F, 4.0F, -4.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition leftleg2 = rhino.addOrReplaceChild("leftleg2",
				CubeListBuilder.create().texOffs(90, 107)
						.addBox(-2.0F, 6.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 106)
						.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, -10.0F, -17.0F));

		PartDefinition rightleg1 = rhino.addOrReplaceChild("rightleg1",
				CubeListBuilder.create().texOffs(114, 0)
						.addBox(-2.0F, 8.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(26, 106)
						.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, -10.0F, 15.0F));

		PartDefinition rightleg2 = rhino.addOrReplaceChild("rightleg2",
				CubeListBuilder.create().texOffs(26, 87)
						.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(90, 107)
						.addBox(-2.0F, 6.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, -10.0F, -17.0F));

		PartDefinition leftleg1 = rhino.addOrReplaceChild("leftleg1",
				CubeListBuilder.create().texOffs(114, 0)
						.addBox(-2.0F, 8.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 87)
						.addBox(-3.0F, 0.0F, -3.0F, 6.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, -10.0F, 15.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		rhino.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
		this.head.xRot = headPitch / (180F / (float) Math.PI);
		this.rightleg2.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.tail.zRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leftleg2.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.rightleg1.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
		this.leftleg1.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
	}
}