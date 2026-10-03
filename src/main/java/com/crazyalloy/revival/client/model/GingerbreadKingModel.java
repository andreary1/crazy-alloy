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
 * Gingerbread King: a broad gingerbread body with thick arms ending in block fists, short legs with wide feet, a
 * near-cubic head under a golden crown, angry icing brows, a big curled icing moustache and a layered purple
 * collar over the shoulders. Heavy steps with a torso sway, wide hooks, a visible wind-up before the ground slam
 * (both fists raised, leaning back) and a raised-fist gesture when he calls his soldiers.
 * Texture 128x128; the renderer scales the model by 1.25. Cubes are tagged for tools/gen_mobs_v3.py.
 */
public class GingerbreadKingModel extends EntityModel<RevivalRenderState> {
    /** Slam timeline in ticks: wind-up, strike, recovery. Shared with the entity. */
    public static final float SLAM_WINDUP = 20.0F, SLAM_STRIKE = 4.0F, SLAM_RECOVER = 12.0F;
    public static final float SUMMON_LENGTH = 30.0F;

    private final ModelPart body, head, mustache, rightArm, leftArm, rightLeg, leftLeg;

    public GingerbreadKingModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.mustache = head.getChild("mustache");
        this.rightArm = body.getChild("right_arm");
        this.leftArm = body.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        for (int side = -1; side <= 1; side += 2) {
            String name = side < 0 ? "right" : "left";
            root.addOrReplaceChild(name + "_leg", CubeListBuilder.create()
                    .texOffs(100, 19).addBox(-3.0F, 0.0F, -3.0F, 6, 8, 6) // tex:leg
                    .texOffs(78, 47).addBox(-3.5F, 8.0F, -4.5F, 7, 3, 8), // tex:foot
                    PartPose.offset(side * 4.5F, 13.0F, 0.0F));
        }
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8.0F, -14.0F, -5.0F, 16, 14, 10) // tex:torso
                .texOffs(64, 24).addBox(-1.0F, -12.5F, -6.0F, 2, 2, 1) // tex:button
                .texOffs(64, 24).addBox(-1.0F, -9.5F, -6.0F, 2, 2, 1) // tex:button
                .texOffs(64, 24).addBox(-1.0F, -6.5F, -6.0F, 2, 2, 1) // tex:button
                .texOffs(64, 24).addBox(-1.0F, -3.5F, -6.0F, 2, 2, 1), // tex:button
                PartPose.offset(0.0F, 13.0F, 0.0F));
        body.addOrReplaceChild("collar", CubeListBuilder.create()
                .texOffs(52, 33).addBox(-11.0F, -1.0F, -6.0F, 22, 2, 12) // tex:collar1
                .texOffs(28, 47).addBox(-7.5F, -3.0F, -5.0F, 15, 2, 10), // tex:collar2
                PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(52, 0).addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12), // tex:head
                PartPose.offset(0.0F, -17.0F, 0.0F));
        head.addOrReplaceChild("crown", CubeListBuilder.create()
                .texOffs(0, 24).addBox(-6.5F, -13.0F, -6.5F, 13, 2, 13) // tex:crown_band
                .texOffs(58, 24).addBox(-5.5F, -16.0F, -6.5F, 2, 3, 1) // tex:crown_pt_fb
                .texOffs(58, 24).addBox(-1.0F, -16.0F, -6.5F, 2, 3, 1) // tex:crown_pt_fb
                .texOffs(58, 24).addBox(3.5F, -16.0F, -6.5F, 2, 3, 1) // tex:crown_pt_fb
                .texOffs(58, 24).addBox(-5.5F, -16.0F, 5.5F, 2, 3, 1) // tex:crown_pt_fb
                .texOffs(58, 24).addBox(-1.0F, -16.0F, 5.5F, 2, 3, 1) // tex:crown_pt_fb
                .texOffs(58, 24).addBox(3.5F, -16.0F, 5.5F, 2, 3, 1) // tex:crown_pt_fb
                .texOffs(52, 24).addBox(-6.5F, -16.0F, -1.0F, 1, 3, 2) // tex:crown_pt_side
                .texOffs(52, 24).addBox(5.5F, -16.0F, -1.0F, 1, 3, 2), // tex:crown_pt_side
                PartPose.ZERO);
        head.addOrReplaceChild("right_brow", CubeListBuilder.create()
                .texOffs(64, 27).addBox(-2.0F, -0.5F, -0.5F, 4, 1, 1), // tex:brow
                PartPose.offsetAndRotation(-2.8F, -8.0F, -6.3F, 0.0F, 0.0F, 0.3F));
        head.addOrReplaceChild("left_brow", CubeListBuilder.create()
                .texOffs(64, 27).addBox(-2.0F, -0.5F, -0.5F, 4, 1, 1), // tex:brow
                PartPose.offsetAndRotation(2.8F, -8.0F, -6.3F, 0.0F, 0.0F, -0.3F));
        head.addOrReplaceChild("mustache", CubeListBuilder.create()
                .texOffs(70, 24).addBox(-4.0F, -3.5F, -7.0F, 8, 2, 1) // tex:must_center
                .texOffs(88, 24).addBox(-6.0F, -4.5F, -7.0F, 2, 2, 1) // tex:must_tip
                .texOffs(88, 24).addBox(4.0F, -4.5F, -7.0F, 2, 2, 1) // tex:must_tip
                .texOffs(124, 0).addBox(-7.0F, -6.5F, -7.0F, 1, 2, 1) // tex:must_hook
                .texOffs(124, 0).addBox(6.0F, -6.5F, -7.0F, 1, 2, 1), // tex:must_hook
                PartPose.ZERO);

        for (int side = -1; side <= 1; side += 2) {
            String name = side < 0 ? "right" : "left";
            PartDefinition arm = body.addOrReplaceChild(name + "_arm", CubeListBuilder.create()
                    .texOffs(100, 0).addBox(-3.0F, -2.0F, -3.0F, 6, 13, 6), // tex:arm
                    PartPose.offsetAndRotation(side * 11.0F, -12.0F, 0.0F, 0.0F, 0.0F, -side * 0.12F));
            arm.addOrReplaceChild(name + "_hand", CubeListBuilder.create()
                    .texOffs(0, 39).addBox(-3.5F, 0.0F, -3.5F, 7, 6, 7), // tex:hand
                    PartPose.offset(0.0F, 11.0F, 0.0F));
        }
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static float smooth(float x) {
        x = Mth.clamp(x, 0.0F, 1.0F);
        return x * x * (3.0F - 2.0F * x);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float spd = Math.min(1.0F, state.walkAnimationSpeed);
        float pos = state.walkAnimationPos * 0.45F;

        // Heavy steps: short leg swing, the body drops as each foot lands and rolls from side to side.
        rightLeg.xRot = Mth.cos(pos) * 0.7F * spd;
        leftLeg.xRot = Mth.cos(pos + Mth.PI) * 0.7F * spd;
        body.y += (1.0F - Math.abs(Mth.cos(pos))) * 1.2F * spd + Mth.sin(t * 0.08F) * 0.25F;
        body.zRot = Mth.sin(pos) * 0.07F * spd;
        body.yRot = Mth.cos(pos) * 0.08F * spd;
        rightArm.xRot = Mth.cos(pos + Mth.PI) * 0.45F * spd;
        leftArm.xRot = Mth.cos(pos) * 0.45F * spd;
        rightArm.zRot += Mth.sin(t * 0.08F) * 0.03F;
        leftArm.zRot -= Mth.sin(t * 0.08F) * 0.03F;
        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot = state.xRot * Mth.DEG_TO_RAD * 0.6F;
        mustache.yScale = 1.0F + Mth.sin(t * 0.3F) * 0.04F;

        float a = state.attackAnim;
        if (a > 0.0F) {
            // Wide hook: the fist sweeps in from the side while the torso twists into it.
            float p = Mth.sin(a * Mth.PI);
            boolean left = state.offHandSwing;
            ModelPart arm = left ? leftArm : rightArm;
            float dir = left ? -1.0F : 1.0F;
            arm.xRot = -1.45F * p;
            arm.yRot = dir * (0.9F - 1.6F * a) * p;
            body.yRot += dir * (0.35F - 0.8F * a) * p;
        }

        float e = state.ticksSince(state.actionA);
        if (e >= 0.0F) {
            animateSlam(state, e, t);
        }
        float s = state.ticksSince(state.actionB);
        if (s >= 0.0F && s < SUMMON_LENGTH) {
            // Calling the guard: right fist raised high and pumped, left fist on the hip, chin up.
            float u = s < 6.0F ? s / 6.0F : s > SUMMON_LENGTH - 6.0F ? (SUMMON_LENGTH - s) / 6.0F : 1.0F;
            u = smooth(u);
            float pump = s > 8.0F && s < 22.0F ? Mth.sin((s - 8.0F) * 0.9F) * 0.2F : 0.0F;
            rightArm.xRot = Mth.lerp(u, rightArm.xRot, -2.95F + pump);
            rightArm.zRot = Mth.lerp(u, rightArm.zRot, -0.15F);
            leftArm.xRot = Mth.lerp(u, leftArm.xRot, 0.35F);
            leftArm.zRot = Mth.lerp(u, leftArm.zRot, -0.5F);
            body.xRot -= 0.1F * u;
            head.xRot -= 0.35F * u;
        }
    }

    private void animateSlam(RevivalRenderState state, float e, float t) {
        float raise, lean, crouch;
        if (e < SLAM_WINDUP) {
            // Wind-up: both fists climb over the head and he leans back, trembling just before the strike.
            float w = smooth(e / SLAM_WINDUP);
            raise = -2.9F * w + Mth.sin(t * 3.0F) * 0.04F * w;
            lean = -0.2F * w;
            crouch = 0.0F;
        } else if (e < SLAM_WINDUP + SLAM_STRIKE) {
            float s = (e - SLAM_WINDUP) / SLAM_STRIKE;
            raise = Mth.lerp(s, -2.9F, -0.7F);
            lean = Mth.lerp(s, -0.2F, 0.45F);
            crouch = 2.5F * s;
        } else if (e < SLAM_WINDUP + SLAM_STRIKE + SLAM_RECOVER) {
            float r = 1.0F - smooth((e - SLAM_WINDUP - SLAM_STRIKE) / SLAM_RECOVER);
            raise = -0.7F * r;
            lean = 0.45F * r;
            crouch = 2.5F * r;
        } else {
            return;
        }
        rightArm.xRot = raise;
        leftArm.xRot = raise;
        rightArm.yRot = 0.0F;
        leftArm.yRot = 0.0F;
        rightArm.zRot = 0.25F * (raise / -2.9F) + 0.05F;
        leftArm.zRot = -rightArm.zRot;
        body.xRot += lean;
        body.y += crouch;
        head.xRot -= lean * 0.6F;
    }
}
