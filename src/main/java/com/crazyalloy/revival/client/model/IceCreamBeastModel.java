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
 * Ice Cream Beast: a big hunched humanoid of four flavours: a strawberry head with a small dark-eyed face, a vanilla
 * torso leaning forward, long heavy chocolate arms hanging in front and mint legs. On its head sits a tall wafer cone
 * turned upside down and tilted, like a pointed hat. Lumbers with its arms swinging; hits by bringing both arms down.
 * Texture 128x64; cubes are tagged for tools/gen_textures_v6.py.
 */
public class IceCreamBeastModel extends EntityModel<RevivalRenderState> {
    private final ModelPart body, head, hat, rightArm, leftArm, rightLeg, leftLeg;

    public IceCreamBeastModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.hat = head.getChild("hat");
        this.rightArm = body.getChild("right_arm");
        this.leftArm = body.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.5F, 0.0F, -2.5F, 5, 14, 5), // tex:leg
                PartPose.offset(-3.6F, 10.0F, 1.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(0, 0).mirror().addBox(-2.5F, 0.0F, -2.5F, 5, 14, 5), // tex:leg
                PartPose.offset(3.6F, 10.0F, 1.0F));
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(20, 0).addBox(-6.0F, -14.0F, -3.5F, 12, 14, 7), // tex:torso
                PartPose.offsetAndRotation(0.0F, 10.0F, 1.0F, 0.38F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(58, 0).addBox(-4.0F, -8.0F, -4.5F, 8, 8, 8), // tex:head
                PartPose.offsetAndRotation(0.0F, -14.0F, -1.5F, -0.3F, 0.0F, 0.0F));
        // The upside-down cone: a wide rim on the head, narrowing to a point, tipped to one side.
        head.addOrReplaceChild("hat", CubeListBuilder.create()
                .texOffs(90, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 4, 8) // tex:hat_rim
                .texOffs(58, 16).addBox(-3.0F, -8.0F, -3.0F, 6, 4, 6) // tex:hat_mid
                .texOffs(82, 16).addBox(-2.0F, -12.0F, -2.0F, 4, 4, 4) // tex:hat_top
                .texOffs(98, 16).addBox(-1.0F, -15.0F, -1.0F, 2, 3, 2), // tex:hat_tip
                PartPose.offsetAndRotation(0.5F, -7.5F, -0.5F, -0.12F, 0.0F, 0.32F));
        body.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(0, 21).addBox(-2.5F, -2.0F, -2.5F, 5, 20, 5), // tex:arm
                PartPose.offset(-8.5F, -12.0F, 0.0F));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(0, 21).mirror().addBox(-2.5F, -2.0F, -2.5F, 5, 20, 5), // tex:arm
                PartPose.offset(8.5F, -12.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 0.5F;
        float spd = Math.min(1.0F, state.walkAnimationSpeed);
        head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.8F;
        head.xRot += state.xRot * Mth.DEG_TO_RAD * 0.6F;
        rightLeg.xRot = Mth.cos(pos) * 0.9F * spd;
        leftLeg.xRot = Mth.cos(pos + Mth.PI) * 0.9F * spd;
        // Heavy arms hang forward (the torso leans, so they need to swing back to hang straight down).
        float hang = -0.38F;
        rightArm.xRot = hang + Mth.cos(pos + Mth.PI) * 0.7F * spd + Mth.sin(t * 0.08F) * 0.04F;
        leftArm.xRot = hang + Mth.cos(pos) * 0.7F * spd - Mth.sin(t * 0.08F) * 0.04F;
        rightArm.zRot = 0.12F;
        leftArm.zRot = -0.12F;
        body.zRot = Mth.cos(pos) * 0.08F * spd; // side-to-side lumber
        body.y += Math.abs(Mth.sin(pos)) * 1.2F * spd;
        hat.zRot += Mth.sin(pos) * 0.06F * spd + Mth.sin(t * 0.05F) * 0.02F; // the cone wobbles on its head
        if (state.aggressive) {
            rightArm.xRot -= 0.35F;
            leftArm.xRot -= 0.35F;
        }
        float a = state.attackAnim;
        if (a > 0.0F) {
            // Double hammer blow: both arms go up behind the head, then slam down in front.
            float raise = a < 0.45F ? a / 0.45F : 1.0F - (a - 0.45F) / 0.55F;
            float slam = a < 0.45F ? 0.0F : Mth.sin((a - 0.45F) / 0.55F * Mth.PI);
            rightArm.xRot = Mth.lerp(raise, rightArm.xRot, -2.9F) + slam * 0.6F;
            leftArm.xRot = Mth.lerp(raise, leftArm.xRot, -2.9F) + slam * 0.6F;
            rightArm.zRot = 0.12F - 0.25F * raise;
            leftArm.zRot = -0.12F + 0.25F * raise;
            body.xRot += -0.25F * raise + 0.3F * slam;
        }
    }
}
