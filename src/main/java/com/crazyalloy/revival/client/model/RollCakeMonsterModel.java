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
 * Roll Cake Monster: a thin, hunched, dark brown humanoid whose head is a slice of roll cake, the pink filling
 * spiral standing in for a face. Long jointed arms end in pale claws; thin legs stay bent. It walks with an
 * uneven limp, its arms swing loosely behind the body and its head jerks to new angles.
 * Texture 64x64; each cube is tagged with the texture region tools/gen_mobs_v3.py paints for it.
 */
public class RollCakeMonsterModel extends EntityModel<RevivalRenderState> {
    private static final float HUNCH = 0.45F;
    private final ModelPart torso, head, rightArm, leftArm, rightForearm, leftForearm, rightClaws, leftClaws;
    private final ModelPart rightThigh, leftThigh, rightShin, leftShin;

    public RollCakeMonsterModel(ModelPart root) {
        super(root);
        this.torso = root.getChild("torso");
        this.head = torso.getChild("neck").getChild("head");
        this.rightArm = torso.getChild("right_arm");
        this.leftArm = torso.getChild("left_arm");
        this.rightForearm = rightArm.getChild("right_forearm");
        this.leftForearm = leftArm.getChild("left_forearm");
        this.rightClaws = rightForearm.getChild("right_claws");
        this.leftClaws = leftForearm.getChild("left_claws");
        this.rightThigh = root.getChild("right_thigh");
        this.leftThigh = root.getChild("left_thigh");
        this.rightShin = rightThigh.getChild("right_shin");
        this.leftShin = leftThigh.getChild("left_shin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition torso = root.addOrReplaceChild("torso", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.5F, -14.0F, -2.0F, 7, 14, 4), // tex:torso
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, HUNCH, 0.0F, 0.0F));
        PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create()
                .texOffs(42, 13).addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2), // tex:neck
                PartPose.offset(0.0F, -14.0F, -0.5F));
        // The slice: a 10x8 block with 8x1 caps above and below, so the spiral face reads as rounded.
        neck.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(22, 0).addBox(-5.0F, -8.5F, -2.5F, 10, 8, 5) // tex:head_main
                .texOffs(0, 24).addBox(-4.0F, -9.5F, -2.5F, 8, 1, 5) // tex:head_cap_top
                .texOffs(26, 24).addBox(-4.0F, -0.5F, -2.5F, 8, 1, 5), // tex:head_cap_bottom
                PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, -HUNCH, 0.0F, 0.0F));

        for (int side = -1; side <= 1; side += 2) {
            String name = side < 0 ? "right" : "left";
            PartDefinition arm = torso.addOrReplaceChild(name + "_arm", CubeListBuilder.create()
                    .texOffs(52, 0).addBox(-1.0F, -1.0F, -1.0F, 2, 10, 2), // tex:upper_arm
                    PartPose.offsetAndRotation(side * 4.5F, -12.5F, 0.0F, -HUNCH, 0.0F, -side * 0.12F));
            PartDefinition forearm = arm.addOrReplaceChild(name + "_forearm", CubeListBuilder.create()
                    .texOffs(52, 12).addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2), // tex:forearm
                    PartPose.offsetAndRotation(0.0F, 9.0F, 0.0F, -0.35F, 0.0F, 0.0F));
            forearm.addOrReplaceChild(name + "_claws", CubeListBuilder.create()
                    .texOffs(60, 0).addBox(-1.5F, 0.0F, -1.5F, 1, 3, 1) // tex:claw
                    .texOffs(60, 0).addBox(0.5F, 0.0F, -1.5F, 1, 3, 1) // tex:claw
                    .texOffs(60, 0).addBox(-0.5F, 0.0F, 0.5F, 1, 3, 1), // tex:claw
                    PartPose.offsetAndRotation(0.0F, 10.0F, 0.0F, -0.3F, 0.0F, 0.0F));

            PartDefinition thigh = root.addOrReplaceChild(name + "_thigh", CubeListBuilder.create()
                    .texOffs(22, 13).addBox(-1.5F, 0.0F, -1.5F, 3, 8, 3), // tex:thigh
                    PartPose.offsetAndRotation(side * 2.2F, 8.0F, 0.0F, -0.4F, 0.0F, -side * 0.12F));
            thigh.addOrReplaceChild(name + "_shin", CubeListBuilder.create()
                    .texOffs(34, 13).addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2) // tex:shin
                    .texOffs(0, 18).addBox(-1.5F, 8.0F, -2.5F, 3, 1, 3), // tex:foot
                    PartPose.offsetAndRotation(0.0F, 7.5F, 0.0F, 0.75F, 0.0F, 0.0F));
        }
        return LayerDefinition.create(mesh, 64, 64);
    }

    /** Deterministic noise in [-1, 1] for the head's twitches. */
    private static float noise(int k) {
        float s = Mth.sin(k * 12.9898F) * 43758.5453F;
        return (s - Mth.floor(s)) * 2.0F - 1.0F;
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float spd = Math.min(1.0F, state.walkAnimationSpeed);
        float pos = state.walkAnimationPos * 0.55F;
        // Uneven gait: the phase is warped so one step is quick and the next one drags, and the left stride is longer.
        float warped = pos + 0.45F * Mth.sin(pos);
        float r = Mth.cos(warped);
        float l = Mth.cos(warped + Mth.PI);
        rightThigh.xRot += r * 0.65F * spd;
        leftThigh.xRot += l * 0.9F * spd;
        rightShin.xRot += Math.max(0.0F, r) * 0.5F * spd;
        leftShin.xRot += Math.max(0.0F, l) * 0.7F * spd;
        torso.zRot = Mth.sin(warped) * 0.12F * spd;
        torso.xRot += Math.abs(Mth.sin(warped)) * 0.1F * spd + Mth.sin(t * 0.06F) * 0.025F;
        torso.y += Math.abs(r) * 0.6F * spd;

        // Arms dangle and swing late, so they look loose.
        rightArm.xRot += Mth.cos(warped + Mth.PI + 0.6F) * 0.55F * spd + Mth.sin(t * 0.07F) * 0.04F;
        leftArm.xRot += Mth.cos(warped + 0.6F) * 0.55F * spd + Mth.sin(t * 0.07F + 1.0F) * 0.04F;
        rightForearm.xRot += Mth.cos(warped + Mth.PI) * 0.3F * spd;
        leftForearm.xRot += Mth.cos(warped) * 0.3F * spd;
        rightArm.zRot += Mth.sin(t * 0.05F) * 0.05F;
        leftArm.zRot -= Mth.sin(t * 0.05F + 0.7F) * 0.05F;

        // Head: follows the look direction, with a tilt that snaps to a new angle every second and a half.
        float seg = t / 30.0F;
        int k = Mth.floor(seg);
        float f = Math.min(1.0F, (seg - k) / 0.12F);
        head.zRot = Mth.lerp(f, noise(k - 1), noise(k)) * 0.35F;
        head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.8F + Mth.lerp(f, noise(k + 50), noise(k + 51)) * 0.15F;
        head.xRot += state.xRot * Mth.DEG_TO_RAD * 0.5F;

        if (state.aggressive) {
            // Ready to pounce: claws raised in front and spread.
            rightArm.xRot -= 0.5F;
            leftArm.xRot -= 0.5F;
            rightClaws.xRot -= 0.3F;
            leftClaws.xRot -= 0.3F;
        }
        float a = state.attackAnim;
        if (a > 0.0F) {
            // Both arms rise and rake down.
            float lift = Mth.sin(a * Mth.PI);
            float rake = a < 0.5F ? -1.4F * (a / 0.5F) : -1.4F + 1.9F * ((a - 0.5F) / 0.5F);
            rightArm.xRot += rake * lift;
            leftArm.xRot += rake * lift;
            rightForearm.xRot -= 0.5F * lift;
            leftForearm.xRot -= 0.5F * lift;
            torso.xRot += 0.15F * lift;
        }
    }
}
