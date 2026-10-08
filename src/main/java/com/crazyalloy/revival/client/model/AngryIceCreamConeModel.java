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
 * Angry Ice Cream Cone (revival proposal: the reference images do not show it in detail): the empty wafer cone left
 * behind by a Living Ice Cream, its open top showing the hollow inside, with a scowling face on the wafer, stubby
 * arms that flail and the same thin legs. Leans forward and runs; kicks to attack.
 * Texture 64x32; cubes are tagged for tools/gen_textures_v6.py.
 */
public class AngryIceCreamConeModel extends EntityModel<RevivalRenderState> {
    private final ModelPart cone, rightArm, leftArm, rightLeg, leftLeg;

    public AngryIceCreamConeModel(ModelPart root) {
        super(root);
        this.cone = root.getChild("cone");
        this.rightArm = cone.getChild("right_arm");
        this.leftArm = cone.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(48, 0).addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1) // tex:leg
                .texOffs(52, 0).addBox(-1.0F, 4.0F, -2.0F, 2, 1, 3), // tex:foot
                PartPose.offset(-1.3F, 19.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(48, 0).mirror().addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1) // tex:leg
                .texOffs(52, 0).mirror().addBox(-1.0F, 4.0F, -2.0F, 2, 1, 3), // tex:foot
                PartPose.offset(1.3F, 19.0F, 0.0F));
        PartDefinition cone = root.addOrReplaceChild("cone", CubeListBuilder.create()
                .texOffs(40, 0).addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2) // tex:cone_tip
                .texOffs(24, 0).addBox(-2.0F, -6.0F, -2.0F, 4, 3, 4) // tex:cone_mid
                .texOffs(0, 0).addBox(-3.0F, -10.0F, -3.0F, 6, 4, 6), // tex:cone_top
                PartPose.offset(0.0F, 19.5F, 0.0F));
        cone.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(48, 6).addBox(-1.0F, 0.0F, -0.5F, 1, 4, 1), // tex:arm
                PartPose.offsetAndRotation(-3.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.5F));
        cone.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(48, 6).mirror().addBox(0.0F, 0.0F, -0.5F, 1, 4, 1), // tex:arm
                PartPose.offsetAndRotation(3.0F, -8.0F, 0.0F, 0.0F, 0.0F, -0.5F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 1.4F;
        float spd = Math.min(1.0F, state.walkAnimationSpeed * 1.5F);
        rightLeg.xRot = Mth.cos(pos) * 1.2F * spd;
        leftLeg.xRot = Mth.cos(pos + Mth.PI) * 1.2F * spd;
        cone.yRot = state.yRot * Mth.DEG_TO_RAD * 0.4F;
        cone.xRot = state.aggressive ? 0.25F : 0.05F;
        cone.y -= Math.abs(Mth.sin(pos)) * 1.2F * spd;
        // Furious little arms: they shake all the time and pump while it runs.
        float shake = Mth.sin(t * 1.3F) * 0.25F;
        rightArm.xRot = -0.4F + Mth.cos(pos + Mth.PI) * 1.0F * spd + shake;
        leftArm.xRot = -0.4F + Mth.cos(pos) * 1.0F * spd - shake;
        float a = state.attackAnim;
        if (a > 0.0F) {
            float p = Mth.sin(a * Mth.PI);
            rightLeg.xRot = -1.3F * p; // a kick
            cone.xRot -= 0.3F * p;
        }
    }
}
