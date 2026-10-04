package com.crazyalloy.revival.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Ice Cream Vendor (revival proposal, stage 4): a cheerful seller in a white uniform with pink trim, a pink bow tie
 * and a folded paper cap. While someone trades, it holds up an ice cream cone (stance).
 * Texture 64x64; each cube is tagged with the texture region tools/gen_mobs_v4.py paints for it.
 */
public class IceCreamVendorModel extends EntityModel<RevivalRenderState> {
    private final ModelPart head, rightArm, leftArm, rightLeg, leftLeg, cone;

    public IceCreamVendorModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.cone = rightArm.getChild("cone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8) // tex:head
                .texOffs(50, 8).addBox(-1.0F, -4.0F, -5.0F, 2, 2, 1), // tex:nose
                PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("hat", CubeListBuilder.create()
                .texOffs(32, 0).addBox(-4.5F, -3.0F, -2.5F, 9, 3, 5) // tex:hat
                .texOffs(32, 8).addBox(-3.5F, -4.0F, -1.0F, 7, 1, 2), // tex:hat_ridge
                PartPose.offset(0.0F, -7.5F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4), // tex:body
                PartPose.ZERO);
        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4), // tex:arm
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4), // tex:arm
                PartPose.offset(5.0F, 2.0F, 0.0F));
        PartDefinition cone = rightArm.addOrReplaceChild("cone", CubeListBuilder.create()
                .texOffs(0, 32).addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2) // tex:cone
                .texOffs(8, 32).addBox(-1.5F, -6.0F, -1.5F, 3, 3, 3), // tex:scoop
                PartPose.offset(-1.0F, 10.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4), // tex:leg
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4), // tex:leg
                PartPose.offset(2.0F, 12.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 0.6662F;
        float speed = Math.min(1.0F, state.walkAnimationSpeed);
        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot = state.xRot * Mth.DEG_TO_RAD;
        rightLeg.xRot = Mth.cos(pos) * 1.2F * speed;
        leftLeg.xRot = Mth.cos(pos + Mth.PI) * 1.2F * speed;
        rightArm.xRot = Mth.cos(pos + Mth.PI) * 0.8F * speed;
        leftArm.xRot = Mth.cos(pos) * 0.8F * speed;
        rightArm.zRot = Mth.cos(t * 0.09F) * 0.04F + 0.04F;
        leftArm.zRot = -Mth.cos(t * 0.09F) * 0.04F - 0.04F;

        // Serving: the right arm comes up holding a cone, which stays upright; a little bounce of the head.
        float serve = state.stance;
        cone.visible = serve > 0.05F;
        if (serve > 0.0F) {
            float raise = -1.25F * serve;
            rightArm.xRot = Mth.lerp(serve, rightArm.xRot, raise);
            rightArm.yRot = -0.15F * serve;
            cone.xRot = -rightArm.xRot;
            head.zRot = Mth.sin(t * 0.3F) * 0.05F * serve;
        }
    }
}
