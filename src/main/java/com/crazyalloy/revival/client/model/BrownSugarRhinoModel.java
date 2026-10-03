package com.crazyalloy.revival.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

/** A bulky rhino of packed brown sugar: plated body, big and small horn, short legs and a tail. Texture 128x64. */
public class BrownSugarRhinoModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart frontRight, frontLeft, hindRight, hindLeft;

    public BrownSugarRhinoModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.tail = root.getChild("tail");
        this.frontRight = root.getChild("front_right_leg");
        this.frontLeft = root.getChild("front_left_leg");
        this.hindRight = root.getChild("hind_right_leg");
        this.hindLeft = root.getChild("hind_left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -12.0F, 14, 12, 24), PartPose.offset(0.0F, 10.0F, 2.0F));
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 36).addBox(-5.0F, -5.0F, -10.0F, 10, 9, 10),
                PartPose.offset(0.0F, 9.0F, -10.0F));
        head.addOrReplaceChild("big_horn", CubeListBuilder.create().texOffs(76, 14).addBox(-1.5F, -9.0F, -9.5F, 3, 4, 3), PartPose.ZERO);
        head.addOrReplaceChild("small_horn", CubeListBuilder.create().texOffs(76, 22).addBox(-1.0F, -7.0F, -5.0F, 2, 3, 2), PartPose.ZERO);
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(100, 0).addBox(-1.0F, -3.0F, -0.5F, 2, 3, 1), PartPose.offsetAndRotation(-4.0F, -5.0F, -1.0F, 0.0F, 0.0F, -0.4F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(100, 0).mirror().addBox(-1.0F, -3.0F, -0.5F, 2, 3, 1), PartPose.offsetAndRotation(4.0F, -5.0F, -1.0F, 0.0F, 0.0F, 0.4F));
        CubeListBuilder leg = CubeListBuilder.create().texOffs(76, 0).addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5);
        root.addOrReplaceChild("front_right_leg", leg, PartPose.offset(-4.5F, 16.0F, -6.0F));
        root.addOrReplaceChild("front_left_leg", leg, PartPose.offset(4.5F, 16.0F, -6.0F));
        root.addOrReplaceChild("hind_right_leg", leg, PartPose.offset(-4.5F, 16.0F, 10.0F));
        root.addOrReplaceChild("hind_left_leg", leg, PartPose.offset(4.5F, 16.0F, 10.0F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(90, 14).addBox(-1.0F, 0.0F, 0.0F, 2, 6, 1), PartPose.offsetAndRotation(0.0F, 6.0F, 14.0F, 0.35F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.6F;
        head.xRot = state.xRot * Mth.DEG_TO_RAD * 0.5F + Mth.sin(state.ageInTicks * 0.05F) * 0.04F;
        float pos = state.walkAnimationPos * 0.6662F;
        float speed = state.walkAnimationSpeed;
        frontRight.xRot = Mth.cos(pos) * 1.1F * speed;
        hindLeft.xRot = Mth.cos(pos) * 1.1F * speed;
        frontLeft.xRot = Mth.cos(pos + Mth.PI) * 1.1F * speed;
        hindRight.xRot = Mth.cos(pos + Mth.PI) * 1.1F * speed;
        tail.zRot = Mth.sin(state.ageInTicks * 0.15F) * 0.25F;
    }
}
