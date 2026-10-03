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

/** A wobbling cube of gum with dark eyes that blows a bubble. Texture 64x32. */
public class BubblegumModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart body;
    private final ModelPart bubble;

    public BubblegumModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.bubble = this.body.getChild("bubble");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10, 10, 10), PartPose.offset(0.0F, 19.0F, 0.0F));
        body.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(40, 0).addBox(-3.5F, -2.5F, -5.6F, 2, 2, 1), PartPose.ZERO);
        body.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(40, 0).addBox(1.5F, -2.5F, -5.6F, 2, 2, 1), PartPose.ZERO);
        body.addOrReplaceChild("bubble", CubeListBuilder.create().texOffs(40, 4).addBox(-2.0F, -2.0F, -3.0F, 4, 4, 3), PartPose.offset(0.0F, 2.5F, -5.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float squash = Mth.sin(t * 0.25F) * 0.07F;
        body.yScale = 1.0F + squash;
        body.xScale = 1.0F - squash * 0.5F;
        body.zScale = 1.0F - squash * 0.5F;
        body.y = 19.0F + Mth.sin(t * 0.12F) * 1.0F;
        body.yRot = state.yRot * Mth.DEG_TO_RAD;
        float blow = (t % 60.0F) / 60.0F; // a bubble grows for three seconds, then pops
        float s = 0.3F + blow * 1.1F;
        bubble.xScale = s;
        bubble.yScale = s;
        bubble.zScale = s;
    }
}
