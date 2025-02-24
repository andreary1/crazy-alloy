// Made with Blockbench 4.12.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modeljellysnakedead<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "jellysnakedead"), "main");
	private final ModelPart seg6;
	private final ModelPart seg5;
	private final ModelPart seg4;
	private final ModelPart seg2;
	private final ModelPart seg1;
	private final ModelPart head;
	private final ModelPart seg3;
	private final ModelPart seg7;

	public Modeljellysnakedead(ModelPart root) {
		this.seg6 = root.getChild("seg6");
		this.seg5 = root.getChild("seg5");
		this.seg4 = root.getChild("seg4");
		this.seg2 = root.getChild("seg2");
		this.seg1 = root.getChild("seg1");
		this.head = root.getChild("head");
		this.seg3 = root.getChild("seg3");
		this.seg7 = root.getChild("seg7");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition seg6 = partdefinition.addOrReplaceChild("seg6", CubeListBuilder.create().texOffs(0, 8).addBox(
				-5.0F, -2.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 24.0F, 5.0F));

		PartDefinition seg5 = partdefinition.addOrReplaceChild("seg5", CubeListBuilder.create().texOffs(0, 0).addBox(
				-4.0F, -2.0F, -1.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 24.0F, 0.0F));

		PartDefinition seg4 = partdefinition.addOrReplaceChild("seg4", CubeListBuilder.create().texOffs(14, 8)
				.addBox(-1.0F, -2.0F, -0.0503F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, -4.0F));

		PartDefinition seg2 = partdefinition.addOrReplaceChild("seg2", CubeListBuilder.create().texOffs(16, 0).addBox(
				-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -5.0F));

		PartDefinition seg1 = partdefinition.addOrReplaceChild("seg1", CubeListBuilder.create().texOffs(14, 15).addBox(
				-1.0F, 1.0F, -6.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, -7.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offset(0.0F, 21.0F, -7.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 3.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition seg3 = partdefinition.addOrReplaceChild("seg3", CubeListBuilder.create().texOffs(8, 22).addBox(
				-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -3.0F));

		PartDefinition seg7 = partdefinition.addOrReplaceChild("seg7", CubeListBuilder.create().texOffs(0, 15).addBox(
				-1.0F, -2.0F, 1.75F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 8.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		seg6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		seg5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		seg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		seg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		seg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		seg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		seg7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}