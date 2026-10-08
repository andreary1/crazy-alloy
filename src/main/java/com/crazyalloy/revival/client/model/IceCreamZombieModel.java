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
 * Ice Cream Zombie: a narrow humanoid with a cubic head, arms held out in front and long legs, all moulded from one
 * flavour (the four flavours share this model and differ only by texture). Shuffles with stiff arms that sway, and
 * lifts both arms higher to hit. Texture 64x32; cubes are tagged for tools/gen_textures_v6.py.
 */
public class IceCreamZombieModel extends EntityModel<RevivalRenderState> {
    private final ModelPart body, head, rightArm, leftArm, rightLeg, leftLeg;

    public IceCreamZombieModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightArm = body.getChild("right_arm");
        this.leftArm = body.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(12, 14).addBox(-1.5F, 0.0F, -1.5F, 3, 13, 3), // tex:leg
                PartPose.offset(-1.6F, 11.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(12, 14).mirror().addBox(-1.5F, 0.0F, -1.5F, 3, 13, 3), // tex:leg
                PartPose.offset(1.6F, 11.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(28, 0).addBox(-3.0F, -11.0F, -1.5F, 6, 11, 3), // tex:body
                PartPose.offset(0.0F, 11.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.5F, -7.0F, -3.5F, 7, 7, 7), // tex:head
                PartPose.offset(0.0F, -11.0F, 0.0F));
        body.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(0, 14).addBox(-1.5F, -1.0F, -1.5F, 3, 13, 3), // tex:arm
                PartPose.offset(-4.5F, -10.0F, 0.0F));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(0, 14).mirror().addBox(-1.5F, -1.0F, -1.5F, 3, 13, 3), // tex:arm
                PartPose.offset(4.5F, -10.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 0.6662F;
        float spd = Math.min(1.0F, state.walkAnimationSpeed);
        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot = state.xRot * Mth.DEG_TO_RAD;
        rightLeg.xRot = Mth.cos(pos) * 1.1F * spd;
        leftLeg.xRot = Mth.cos(pos + Mth.PI) * 1.1F * spd;
        // Arms straight out in front, swaying a little; slightly lower when idle.
        float reach = state.aggressive ? -1.55F : -1.35F;
        float sway = Mth.sin(t * 0.067F) * 0.05F;
        rightArm.xRot = reach + Mth.cos(t * 0.09F) * 0.05F;
        leftArm.xRot = reach - Mth.cos(t * 0.09F) * 0.05F;
        rightArm.zRot = 0.05F + sway;
        leftArm.zRot = -0.05F - sway;
        rightArm.yRot = -0.08F;
        leftArm.yRot = 0.08F;
        body.zRot = Mth.cos(pos) * 0.06F * spd; // heavy, soft-footed shuffle
        float a = state.attackAnim;
        if (a > 0.0F) {
            float p = Mth.sin(a * Mth.PI);
            rightArm.xRot -= 0.6F * p;
            leftArm.xRot -= 0.6F * p;
            body.xRot = 0.15F * p;
        }
    }
}
