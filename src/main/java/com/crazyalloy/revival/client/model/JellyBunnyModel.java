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

/** A small hopping jelly bunny with long ears. Texture 64x32. */
public class JellyBunnyModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart body, head, tail, frontRight, frontLeft, hindRight, hindLeft;

    public JellyBunnyModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
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
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.5F, -4.0F, 6, 5, 8), PartPose.offset(0.0F, 18.5F, 0.0F));
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-2.5F, -4.0F, -4.0F, 5, 5, 5), PartPose.offset(0.0F, 17.5F, -3.0F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(20, 13).addBox(-1.0F, -6.0F, -0.5F, 2, 6, 1), PartPose.offsetAndRotation(-1.3F, -4.0F, -1.5F, -0.15F, 0.0F, -0.2F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(20, 13).mirror().addBox(-1.0F, -6.0F, -0.5F, 2, 6, 1), PartPose.offsetAndRotation(1.3F, -4.0F, -1.5F, -0.15F, 0.0F, 0.2F));
        CubeListBuilder leg = CubeListBuilder.create().texOffs(28, 0).addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2);
        root.addOrReplaceChild("front_right_leg", leg, PartPose.offset(-2.0F, 21.0F, -2.5F));
        root.addOrReplaceChild("front_left_leg", leg, PartPose.offset(2.0F, 21.0F, -2.5F));
        root.addOrReplaceChild("hind_right_leg", leg, PartPose.offset(-2.0F, 21.0F, 2.5F));
        root.addOrReplaceChild("hind_left_leg", leg, PartPose.offset(2.0F, 21.0F, 2.5F));
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(28, 5).addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2), PartPose.offset(0.0F, 17.5F, 4.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot = state.xRot * Mth.DEG_TO_RAD;
        // Hop: the whole bunny bounces while it moves, legs tuck in mid-air.
        float hop = Mth.abs(Mth.sin(state.walkAnimationPos * 0.6F)) * 3.0F * Math.min(1.0F, state.walkAnimationSpeed * 1.5F);
        for (ModelPart p : new ModelPart[] {body, head, tail, frontRight, frontLeft, hindRight, hindLeft}) {
            p.y -= hop;
        }
        float tuck = hop * 0.25F;
        frontRight.xRot = -tuck;
        frontLeft.xRot = -tuck;
        hindRight.xRot = tuck;
        hindLeft.xRot = tuck;
        tail.yRot = Mth.sin(state.ageInTicks * 0.3F) * 0.2F;
    }
}
