package com.crazyalloy.revival.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/** A walking lollipop: a big candy disc wearing a top hat, on a stick body with thin arms and legs. Texture 64x32. */
public class LollipopGuyModel extends EntityModel<LollipopGuyRenderState> {
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public LollipopGuyModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -1.0F, 10, 10, 2), PartPose.offset(0.0F, 8.0F, 0.0F));
        // Black top hat, as on the original Lollipop Guy.
        head.addOrReplaceChild("hat", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-4.0F, -11.0F, -2.0F, 8, 1, 4)
                .texOffs(32, 5).addBox(-3.0F, -16.0F, -1.5F, 6, 5, 3), PartPose.rotation(0.0F, 0.0F, -0.12F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-2.0F, 0.0F, -1.5F, 4, 10, 3), PartPose.offset(0.0F, 8.0F, 0.0F));
        CubeListBuilder arm = CubeListBuilder.create().texOffs(16, 12).addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
        root.addOrReplaceChild("right_arm", arm, PartPose.offset(-3.0F, 9.0F, 0.0F));
        root.addOrReplaceChild("left_arm", arm, PartPose.offset(3.0F, 9.0F, 0.0F));
        CubeListBuilder leg = CubeListBuilder.create().texOffs(24, 12).addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
        root.addOrReplaceChild("right_leg", leg, PartPose.offset(-1.2F, 18.0F, 0.0F));
        root.addOrReplaceChild("left_leg", leg, PartPose.offset(1.2F, 18.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(LollipopGuyRenderState state) {
        super.setupAnim(state);
        float pos = state.walkAnimationPos;
        float speed = state.walkAnimationSpeed;

        // The candy head turns to look around and wobbles gently.
        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.zRot = Mth.sin(state.ageInTicks * 0.08F) * 0.08F;

        rightLeg.xRot = Mth.cos(pos * 0.6662F) * 1.4F * speed;
        leftLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.4F * speed;
        rightArm.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * speed;
        leftArm.xRot = Mth.cos(pos * 0.6662F) * speed;
        rightArm.zRot = 0.1F;
        leftArm.zRot = -0.1F;

        // Swing both arms down when attacking.
        if (state.attackAnim > 0.0F) {
            float swing = Mth.sin(state.attackAnim * Mth.PI);
            rightArm.xRot = -2.0F + swing * 1.5F;
            leftArm.xRot = -2.0F + swing * 1.5F;
        }
    }
}
