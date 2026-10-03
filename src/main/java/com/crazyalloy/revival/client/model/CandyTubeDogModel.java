package com.crazyalloy.revival.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/** A long, low "tube" dog. Texture 64x32 (see textures/entity/candy_tube_dog.png). */
public class CandyTubeDogModel extends EntityModel<CandyTubeDogRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart frontRightLeg;
    private final ModelPart frontLeftLeg;
    private final ModelPart hindRightLeg;
    private final ModelPart hindLeftLeg;

    public CandyTubeDogModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.tail = root.getChild("tail");
        this.frontRightLeg = root.getChild("front_right_leg");
        this.frontLeftLeg = root.getChild("front_left_leg");
        this.hindRightLeg = root.getChild("hind_right_leg");
        this.hindLeftLeg = root.getChild("hind_left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -6.0F, 6, 6, 12), PartPose.offset(0.0F, 15.0F, 0.0F));
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 18).addBox(-3.0F, -4.0F, -4.0F, 6, 6, 4)
                .texOffs(20, 18).addBox(-1.5F, -1.0F, -7.0F, 3, 3, 3), PartPose.offset(0.0F, 13.0F, -6.0F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -2.0F, -0.5F, 2, 2, 1), PartPose.offset(-2.0F, -4.0F, -1.5F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(36, 0).mirror().addBox(-1.0F, -2.0F, -0.5F, 2, 2, 1), PartPose.offset(2.0F, -4.0F, -1.5F));
        CubeListBuilder leg = CubeListBuilder.create().texOffs(36, 18).addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2);
        root.addOrReplaceChild("front_right_leg", leg, PartPose.offset(-2.0F, 18.0F, -4.0F));
        root.addOrReplaceChild("front_left_leg", leg, PartPose.offset(2.0F, 18.0F, -4.0F));
        root.addOrReplaceChild("hind_right_leg", leg, PartPose.offset(-2.0F, 18.0F, 4.0F));
        root.addOrReplaceChild("hind_left_leg", leg, PartPose.offset(2.0F, 18.0F, 4.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(44, 18).addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6),
                PartPose.offsetAndRotation(0.0F, 13.0F, 5.5F, 0.6F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(CandyTubeDogRenderState state) {
        super.setupAnim(state);
        float pos = state.walkAnimationPos;
        float speed = state.walkAnimationSpeed;

        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot = state.xRot * Mth.DEG_TO_RAD;

        // Tail wags faster when tame, droops when wild and idle.
        float wag = state.tame ? 0.6F : 0.2F;
        tail.yRot = Mth.cos(state.ageInTicks * (state.tame ? 0.6F : 0.2F)) * wag;

        if (state.sitting) {
            body.y = 17.0F;
            head.y = 15.0F;
            tail.y = 16.0F;
            tail.xRot = 1.2F;
            hindRightLeg.y = 22.0F;
            hindLeftLeg.y = 22.0F;
            hindRightLeg.xRot = -Mth.HALF_PI;
            hindLeftLeg.xRot = -Mth.HALF_PI;
            frontRightLeg.y = 20.0F;
            frontLeftLeg.y = 20.0F;
            frontRightLeg.xRot = -0.3F;
            frontLeftLeg.xRot = -0.3F;
        } else {
            frontRightLeg.xRot = Mth.cos(pos * 0.6662F) * 1.4F * speed;
            frontLeftLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.4F * speed;
            hindRightLeg.xRot = Mth.cos(pos * 0.6662F + Mth.PI) * 1.4F * speed;
            hindLeftLeg.xRot = Mth.cos(pos * 0.6662F) * 1.4F * speed;
            // The long body bobs a little while running.
            body.y = 15.0F + Mth.abs(Mth.sin(pos * 0.6662F)) * speed;
        }
    }
}
