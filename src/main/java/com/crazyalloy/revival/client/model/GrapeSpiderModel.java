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

/** A bunch of grapes with a stem-like head and eight thin legs. Texture 64x32. */
public class GrapeSpiderModel extends EntityModel<LivingEntityRenderState> {
    private static final float[] LEG_Z = {-2.0F, -0.5F, 1.0F, 2.5F};
    private static final float[] LEG_FAN = {0.6F, 0.2F, -0.2F, -0.6F};

    private final ModelPart head;
    private final ModelPart[] rightLegs = new ModelPart[4];
    private final ModelPart[] leftLegs = new ModelPart[4];

    public GrapeSpiderModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        for (int i = 0; i < 4; i++) {
            rightLegs[i] = root.getChild("right_leg_" + i);
            leftLegs[i] = root.getChild("left_leg_" + i);
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -5.0F, 6, 6, 5), PartPose.offset(0.0F, 18.0F, -2.0F));
        root.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -4.0F, 0.0F, 8, 7, 9), PartPose.offset(0.0F, 18.0F, -1.0F));
        CubeListBuilder right = CubeListBuilder.create().texOffs(36, 0).addBox(-12.0F, -1.0F, -1.0F, 12, 2, 2);
        CubeListBuilder left = CubeListBuilder.create().texOffs(36, 0).mirror().addBox(0.0F, -1.0F, -1.0F, 12, 2, 2);
        for (int i = 0; i < 4; i++) {
            root.addOrReplaceChild("right_leg_" + i, right, PartPose.offset(-3.0F, 19.0F, LEG_Z[i]));
            root.addOrReplaceChild("left_leg_" + i, left, PartPose.offset(3.0F, 19.0F, LEG_Z[i]));
        }
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);
        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot = state.xRot * Mth.DEG_TO_RAD;

        float pos = state.walkAnimationPos * 0.6662F;
        float speed = state.walkAnimationSpeed;
        for (int i = 0; i < 4; i++) {
            // Legs alternate in pairs like a real spider: 0/2 move together, 1/3 opposite.
            float phase = (i % 2 == 0) ? 0.0F : Mth.PI;
            float swingY = -(Mth.cos(pos * 2.0F + phase) * 0.4F) * speed;
            float lift = Mth.abs(Mth.sin(pos + phase) * 0.4F) * speed;
            rightLegs[i].yRot = LEG_FAN[i] + swingY;
            leftLegs[i].yRot = -LEG_FAN[i] - swingY;
            rightLegs[i].zRot = -0.7F + lift;
            leftLegs[i].zRot = 0.7F - lift;
        }
    }
}
