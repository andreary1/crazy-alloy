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
 * Ice Cream Gargoyle: a compact chocolate body, a pale vanilla head with a square snout, two upright horns and dark
 * marks around the eyes, magenta-pink limbs dangling below, and angular caramel wafer wings spread to the sides.
 * The wings beat all the time, faster while it dives; the limbs trail behind. Texture 64x32; cubes are tagged for
 * tools/gen_textures_v6.py.
 */
public class IceCreamGargoyleModel extends EntityModel<RevivalRenderState> {
    private final ModelPart body, head, rightWing, leftWing, rightArm, leftArm, rightLeg, leftLeg;

    public IceCreamGargoyleModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightWing = body.getChild("right_wing");
        this.leftWing = body.getChild("left_wing");
        this.rightArm = body.getChild("right_arm");
        this.leftArm = body.getChild("left_arm");
        this.rightLeg = body.getChild("right_leg");
        this.leftLeg = body.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -7.0F, -2.5F, 6, 7, 5), // tex:body
                PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.25F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(22, 0).addBox(-3.0F, -5.0F, -3.0F, 6, 5, 5) // tex:head
                .texOffs(44, 0).addBox(-2.0F, -3.0F, -5.5F, 4, 3, 3), // tex:snout
                PartPose.offsetAndRotation(0.0F, -7.0F, -0.5F, -0.25F, 0.0F, 0.0F));
        head.addOrReplaceChild("horns", CubeListBuilder.create()
                .texOffs(58, 0).addBox(-2.5F, -8.0F, -1.0F, 1, 3, 1) // tex:horn
                .texOffs(58, 0).addBox(1.5F, -8.0F, -1.0F, 1, 3, 1), // tex:horn
                PartPose.ZERO);
        body.addOrReplaceChild("right_wing", CubeListBuilder.create()
                .texOffs(0, 16).addBox(-10.0F, -0.5F, -1.0F, 10, 1, 7), // tex:wing
                PartPose.offsetAndRotation(-2.5F, -6.0F, 1.5F, 0.0F, 0.0F, 0.2F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create()
                .texOffs(0, 16).mirror().addBox(0.0F, -0.5F, -1.0F, 10, 1, 7), // tex:wing
                PartPose.offsetAndRotation(2.5F, -6.0F, 1.5F, 0.0F, 0.0F, -0.2F));
        body.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(44, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2), // tex:limb
                PartPose.offset(-3.5F, -6.0F, -1.0F));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(44, 6).mirror().addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2), // tex:limb
                PartPose.offset(3.5F, -6.0F, -1.0F));
        body.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(44, 6).addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2), // tex:limb
                PartPose.offset(-1.8F, 0.0F, 0.5F));
        body.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(44, 6).mirror().addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2), // tex:limb
                PartPose.offset(1.8F, 0.0F, 0.5F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float rate = state.aggressive ? 0.9F : 0.6F;
        float flap = Mth.sin(t * rate);
        rightWing.zRot = 0.2F + flap * 0.75F;
        leftWing.zRot = -0.2F - flap * 0.75F;
        rightWing.yRot = -0.15F + Mth.cos(t * rate) * 0.12F;
        leftWing.yRot = 0.15F - Mth.cos(t * rate) * 0.12F;
        body.y -= flap * 1.0F; // rises on each down-stroke
        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot += state.xRot * Mth.DEG_TO_RAD;
        float dangle = Mth.sin(t * rate - 1.0F) * 0.15F;
        rightArm.xRot = -0.2F + dangle;
        leftArm.xRot = -0.2F - dangle;
        rightLeg.xRot = 0.35F + dangle;
        leftLeg.xRot = 0.35F - dangle;
        if (state.aggressive) {
            body.xRot = 0.55F; // leaning into the dive
            rightArm.xRot = -1.1F + dangle;
            leftArm.xRot = -1.1F - dangle;
        }
        float a = state.attackAnim;
        if (a > 0.0F) {
            float p = Mth.sin(a * Mth.PI);
            rightArm.xRot -= 0.8F * p;
            leftArm.xRot -= 0.8F * p;
            head.xRot += 0.4F * p;
        }
    }
}
