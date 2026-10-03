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

/** A slithering snake of jelly segments (green head, red, yellow, orange). Texture 64x32. */
public class JellySnakeModel extends EntityModel<LivingEntityRenderState> {
    private static final int[][] SEGMENT_UV = {{0, 8}, {14, 8}, {28, 8}, {0, 8}};
    private final ModelPart head;
    private final ModelPart[] segments = new ModelPart[5];

    public JellySnakeModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        for (int i = 0; i < 4; i++) {
            segments[i] = root.getChild("segment_" + i);
        }
        segments[4] = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -5.0F, 4, 3, 5), PartPose.offset(0.0F, 24.0F, -6.0F));
        for (int i = 0; i < 4; i++) {
            root.addOrReplaceChild("segment_" + i, CubeListBuilder.create().texOffs(SEGMENT_UV[i][0], SEGMENT_UV[i][1]).addBox(-1.5F, -3.0F, 0.0F, 3, 3, 4),
                    PartPose.offset(0.0F, 24.0F, -6.0F + i * 4.0F));
        }
        root.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, -2.0F, 0.0F, 2, 2, 4), PartPose.offset(0.0F, 24.0F, 10.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks * 0.35F + state.walkAnimationPos * 0.8F;
        float amp = 0.6F + Math.min(1.0F, state.walkAnimationSpeed) * 1.2F;
        head.x = Mth.sin(t) * amp * 0.5F;
        head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.5F + Mth.cos(t) * 0.2F;
        for (int i = 0; i < segments.length; i++) {
            segments[i].x = Mth.sin(t - (i + 1) * 0.9F) * amp;
            segments[i].yRot = Mth.cos(t - (i + 1) * 0.9F) * 0.3F;
        }
        head.xRot = -0.15F;
    }
}
