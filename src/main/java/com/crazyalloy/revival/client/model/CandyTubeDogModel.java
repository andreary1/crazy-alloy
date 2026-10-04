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
 * Candy Tube Dog (stage 4 remodel): a long, narrow wine-red candy tube on four thin, straight legs, with a small tail
 * sticking out behind. The head is big for the body, with a long rectangular muzzle, light eyes with dark pupils,
 * floppy ears hanging at the sides and a black smiling line around the mouth; a light patch covers the chest.
 * Wags its tail (faster when tame), tilts its head when someone nearby holds a sweet, and runs in little bounds.
 * Texture 64x32; each cube is tagged with the texture region tools/gen_mobs_v4.py paints for it.
 */
public class CandyTubeDogModel extends EntityModel<CandyTubeDogRenderState> {
    private final ModelPart body, head, tail, rightEar, leftEar;
    private final ModelPart frontRightLeg, frontLeftLeg, hindRightLeg, hindLeftLeg;

    public CandyTubeDogModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.rightEar = head.getChild("right_ear");
        this.leftEar = head.getChild("left_ear");
        this.tail = body.getChild("tail");
        this.frontRightLeg = root.getChild("front_right_leg");
        this.frontLeftLeg = root.getChild("front_left_leg");
        this.hindRightLeg = root.getChild("hind_right_leg");
        this.hindLeftLeg = root.getChild("hind_left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-2.5F, -2.5F, -7.0F, 5, 5, 14) // tex:tube
                .texOffs(38, 0).addBox(-2.0F, -1.5F, -8.0F, 4, 4, 1), // tex:chest
                PartPose.offset(0.0F, 15.0F, 0.0F));
        body.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(38, 6).addBox(-0.5F, -0.5F, 0.0F, 1, 1, 5), // tex:tail
                PartPose.offsetAndRotation(0.0F, -1.5F, 7.0F, 0.5F, 0.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 19).addBox(-3.5F, -5.0F, -5.0F, 7, 6, 6) // tex:head
                .texOffs(26, 19).addBox(-2.0F, -2.0F, -9.0F, 4, 3, 4), // tex:muzzle
                PartPose.offset(0.0F, 13.5F, -7.5F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create()
                .texOffs(42, 19).addBox(-1.0F, 0.0F, -1.5F, 1, 5, 3), // tex:ear
                PartPose.offsetAndRotation(-3.5F, -4.5F, -2.0F, 0.0F, 0.0F, 0.15F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create()
                .texOffs(42, 19).mirror().addBox(0.0F, 0.0F, -1.5F, 1, 5, 3), // tex:ear
                PartPose.offsetAndRotation(3.5F, -4.5F, -2.0F, 0.0F, 0.0F, -0.15F));

        CubeListBuilder leg = CubeListBuilder.create().texOffs(52, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2); // tex:leg
        root.addOrReplaceChild("front_right_leg", leg, PartPose.offset(-1.5F, 17.0F, -5.0F));
        root.addOrReplaceChild("front_left_leg", leg, PartPose.offset(1.5F, 17.0F, -5.0F));
        root.addOrReplaceChild("hind_right_leg", leg, PartPose.offset(-1.5F, 17.0F, 5.0F));
        root.addOrReplaceChild("hind_left_leg", leg, PartPose.offset(1.5F, 17.0F, 5.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(CandyTubeDogRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 0.6662F;
        float speed = Math.min(1.0F, state.walkAnimationSpeed);

        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot = state.xRot * Mth.DEG_TO_RAD;
        head.zRot = state.headTilt * 0.45F;
        // Floppy ears swing a little behind every movement of the head.
        float flop = Mth.sin(t * 0.25F) * 0.05F + speed * 0.25F * Mth.abs(Mth.sin(pos));
        rightEar.zRot = 0.15F + flop - state.headTilt * 0.2F;
        leftEar.zRot = -0.15F - flop - state.headTilt * 0.2F;

        // Tail: a happy fast wag when tame, a slow sway when wild; it also lifts while running.
        tail.yRot = Mth.sin(t * (state.tame ? 0.9F : 0.25F)) * (state.tame ? 0.7F : 0.25F);
        tail.xRot = 0.5F + speed * 0.4F;

        if (state.sitting) {
            body.y = 18.0F;
            body.xRot = -0.35F;
            head.y = 13.0F;
            head.z = -6.0F;
            frontRightLeg.y = frontLeftLeg.y = 17.5F;
            frontRightLeg.xRot = frontLeftLeg.xRot = -0.15F;
            hindRightLeg.y = hindLeftLeg.y = 22.5F;
            hindRightLeg.z = hindLeftLeg.z = 3.5F;
            hindRightLeg.xRot = hindLeftLeg.xRot = -Mth.HALF_PI;
            tail.xRot = 1.3F;
            tail.y = 1.0F;
            return;
        }
        if (speed > 0.55F) {
            // Running: little bounds, front legs together then hind legs together, the long body rocking.
            float k = (speed - 0.55F) / 0.45F;
            float front = Mth.cos(pos) * 1.1F * speed;
            float hind = Mth.cos(pos + Mth.PI) * 1.1F * speed;
            frontRightLeg.xRot = Mth.lerp(k, Mth.cos(pos) * 1.4F * speed, front);
            frontLeftLeg.xRot = Mth.lerp(k, Mth.cos(pos + Mth.PI) * 1.4F * speed, front * 0.9F);
            hindRightLeg.xRot = Mth.lerp(k, Mth.cos(pos + Mth.PI) * 1.4F * speed, hind);
            hindLeftLeg.xRot = Mth.lerp(k, Mth.cos(pos) * 1.4F * speed, hind * 0.9F);
            float bound = Mth.abs(Mth.sin(pos)) * 1.6F * k;
            body.y -= bound;
            head.y -= bound;
            frontRightLeg.y -= bound;
            frontLeftLeg.y -= bound;
            hindRightLeg.y -= bound;
            hindLeftLeg.y -= bound;
            body.xRot = Mth.sin(pos) * 0.12F * k;
        } else {
            frontRightLeg.xRot = Mth.cos(pos) * 1.4F * speed;
            frontLeftLeg.xRot = Mth.cos(pos + Mth.PI) * 1.4F * speed;
            hindRightLeg.xRot = Mth.cos(pos + Mth.PI) * 1.4F * speed;
            hindLeftLeg.xRot = Mth.cos(pos) * 1.4F * speed;
            body.y += Mth.abs(Mth.sin(pos)) * 0.6F * speed;
        }
    }
}
