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
 * Grape Spider (stage 4 remodel): a compact, almost cubic body of very dark purple held a little above the ground by
 * eight long, thin, angular legs that open out to the sides. Each leg is a straight thigh rising to a sharp knee and
 * a straight shin dropping to the ground. The front is one big square black "eye" with a light rim; there is no
 * separate abdomen. Walks with alternating leg groups and small, jittery tilts of the body; before a pounce it
 * crouches (knees up, body down) and springs (actionA).
 * Texture 64x32; each cube is tagged with the texture region tools/gen_mobs_v4.py paints for it.
 */
public class GrapeSpiderModel extends EntityModel<RevivalRenderState> {
    /** Ticks of the crouch before the spring, shared with the entity's pounce. */
    public static final float CROUCH_TICKS = 10.0F;
    private static final float[] LEG_Z = {-2.5F, -0.8F, 0.8F, 2.5F};
    private static final float[] LEG_FAN = {0.75F, 0.25F, -0.25F, -0.75F};
    private static final float THIGH_LIFT = 0.5F, SHIN_OUT = 0.25F;

    private final ModelPart body;
    private final ModelPart[] rightThighs = new ModelPart[4], leftThighs = new ModelPart[4];
    private final ModelPart[] rightShins = new ModelPart[4], leftShins = new ModelPart[4];

    public GrapeSpiderModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        for (int i = 0; i < 4; i++) {
            rightThighs[i] = root.getChild("right_leg_" + i);
            leftThighs[i] = root.getChild("left_leg_" + i);
            rightShins[i] = rightThighs[i].getChild("shin");
            leftShins[i] = leftThighs[i].getChild("shin");
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -3.5F, -4.0F, 8, 7, 8), // tex:body
                PartPose.offset(0.0F, 17.0F, 0.0F));
        body.addOrReplaceChild("face", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-3.5F, -3.0F, -0.5F, 7, 6, 1), // tex:face
                PartPose.offset(0.0F, -0.2F, -4.0F));
        body.addOrReplaceChild("right_fang", CubeListBuilder.create()
                .texOffs(18, 16).addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1), // tex:fang
                PartPose.offsetAndRotation(-1.5F, 3.0F, -4.2F, -0.3F, 0.0F, 0.0F));
        body.addOrReplaceChild("left_fang", CubeListBuilder.create()
                .texOffs(18, 16).mirror().addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1), // tex:fang
                PartPose.offsetAndRotation(1.5F, 3.0F, -4.2F, -0.3F, 0.0F, 0.0F));
        body.addOrReplaceChild("bump", CubeListBuilder.create()
                .texOffs(24, 16).addBox(-2.5F, -1.0F, -2.5F, 5, 1, 5), // tex:bump
                PartPose.offset(0.0F, -3.5F, 0.5F));

        CubeListBuilder rightThigh = CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, -0.5F, -0.5F, 6, 1, 1); // tex:thigh
        CubeListBuilder leftThigh = CubeListBuilder.create().texOffs(32, 0).mirror().addBox(0.0F, -0.5F, -0.5F, 6, 1, 1); // tex:thigh
        CubeListBuilder shin = CubeListBuilder.create().texOffs(48, 0).addBox(-0.5F, 0.0F, -0.5F, 1, 9, 1); // tex:shin
        for (int i = 0; i < 4; i++) {
            PartDefinition r = root.addOrReplaceChild("right_leg_" + i, rightThigh, PartPose.offset(-3.5F, 18.0F, LEG_Z[i]));
            r.addOrReplaceChild("shin", shin, PartPose.offset(-6.0F, 0.0F, 0.0F));
            PartDefinition l = root.addOrReplaceChild("left_leg_" + i, leftThigh, PartPose.offset(3.5F, 18.0F, LEG_Z[i]));
            l.addOrReplaceChild("shin", shin, PartPose.offset(6.0F, 0.0F, 0.0F));
        }
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 0.9F;
        float speed = Math.min(1.0F, state.walkAnimationSpeed * 1.4F);

        // Unsettling idle: the body twitches and leans a little, never quite still.
        float twitch = Mth.sin(t * 0.9F) * Mth.sin(t * 0.13F) > 0.85F ? 0.08F : 0.0F;
        body.zRot = Mth.sin(t * 0.17F) * 0.05F + twitch + Mth.sin(pos) * 0.08F * speed;
        body.xRot = state.xRot * Mth.DEG_TO_RAD * 0.3F + Mth.sin(t * 0.11F) * 0.03F;
        body.yRot = state.yRot * Mth.DEG_TO_RAD * 0.3F;
        body.y += Mth.abs(Mth.sin(pos)) * -0.6F * speed;

        // Pounce: crouch for CROUCH_TICKS (body sinks, knees rise), then spring with the legs thrown out.
        float crouch = 0.0F, spring = 0.0F;
        float p = state.ticksSince(state.actionA);
        if (p >= 0.0F) {
            if (p < CROUCH_TICKS) {
                crouch = Mth.sin(Math.min(1.0F, p / (CROUCH_TICKS * 0.6F)) * Mth.HALF_PI);
                body.zRot += Mth.sin(p * 2.6F) * 0.04F; // trembling with tension
            } else if (p < CROUCH_TICKS + 14.0F) {
                spring = 1.0F - (p - CROUCH_TICKS) / 14.0F;
            }
        }
        if (!state.onGround && p < 0.0F) {
            spring = 0.6F; // any jump or fall: legs spread
        }
        body.y += crouch * 2.5F - spring * 0.5F;
        body.xRot += crouch * 0.18F - spring * 0.25F;

        for (int i = 0; i < 4; i++) {
            // Two groups of four alternate: right 0 and 2 with left 1 and 3, then the other four.
            float phase = (i % 2 == 0) ? 0.0F : Mth.PI;
            float sweepR = Mth.cos(pos + phase) * 0.35F * speed;
            float liftR = Math.max(0.0F, Mth.sin(pos + phase)) * 0.35F * speed;
            float sweepL = Mth.cos(pos + phase + Mth.PI) * 0.35F * speed;
            float liftL = Math.max(0.0F, Mth.sin(pos + phase + Mth.PI)) * 0.35F * speed;
            float knee = THIGH_LIFT + crouch * 0.45F - spring * 0.55F;
            float shin = SHIN_OUT + crouch * 0.2F + spring * 0.9F;
            rightThighs[i].yRot = LEG_FAN[i] + sweepR;
            leftThighs[i].yRot = -LEG_FAN[i] - sweepL;
            rightThighs[i].zRot = knee + liftR;
            leftThighs[i].zRot = -knee - liftL;
            rightShins[i].zRot = -(knee + liftR) + shin - liftR * 0.5F;
            leftShins[i].zRot = (knee + liftL) - shin + liftL * 0.5F;
            rightThighs[i].y += crouch * 1.5F;
            leftThighs[i].y += crouch * 1.5F;
        }
    }
}
