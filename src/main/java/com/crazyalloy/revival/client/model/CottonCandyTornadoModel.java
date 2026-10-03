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
 * Cotton Candy Tornado: a funnel of spun sugar, wide at the top and narrow at the tip (pink, cream, light blue,
 * then a reddish tip), with an open rim on top and two bulging red eyes. The funnel stands on a thin stalk over a
 * grey base with a white collar and black feet. The layers spin at different speeds and the funnel sways; the
 * face layer stays turned toward where the tornado looks so the eyes are always readable.
 * Texture 64x64; each cube is tagged with the texture region tools/gen_mobs_v3.py paints for it.
 */
public class CottonCandyTornadoModel extends EntityModel<RevivalRenderState> {
    private final ModelPart funnel, tip, layer2, layer3, face, rim, rightEye, leftEye;

    public CottonCandyTornadoModel(ModelPart root) {
        super(root);
        this.funnel = root.getChild("funnel");
        this.tip = funnel.getChild("tip");
        this.layer2 = funnel.getChild("layer2");
        this.layer3 = funnel.getChild("layer3");
        this.face = funnel.getChild("face");
        this.rim = funnel.getChild("rim");
        this.rightEye = face.getChild("right_eye");
        this.leftEye = face.getChild("left_eye");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("base", CubeListBuilder.create()
                .texOffs(0, 17).addBox(-5.0F, -6.0F, -5.0F, 10, 4, 10) // tex:base
                .texOffs(40, 17).addBox(-3.0F, -8.0F, -3.0F, 6, 2, 6) // tex:white_piece
                .texOffs(52, 0).addBox(-1.0F, -12.0F, -1.0F, 2, 4, 2) // tex:stick
                .texOffs(52, 11).addBox(-5.0F, -2.0F, -5.0F, 2, 2, 2) // tex:foot
                .texOffs(52, 11).addBox(3.0F, -2.0F, -5.0F, 2, 2, 2) // tex:foot
                .texOffs(52, 11).addBox(-5.0F, -2.0F, 3.0F, 2, 2, 2) // tex:foot
                .texOffs(52, 11).addBox(3.0F, -2.0F, 3.0F, 2, 2, 2), // tex:foot
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition funnel = root.addOrReplaceChild("funnel", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
        funnel.addOrReplaceChild("tip", CubeListBuilder.create()
                .texOffs(40, 25).addBox(-2.0F, -3.0F, -2.0F, 4, 3, 4), // tex:tip
                PartPose.ZERO);
        funnel.addOrReplaceChild("layer2", CubeListBuilder.create()
                .texOffs(26, 45).addBox(-3.5F, -7.0F, -3.5F, 7, 4, 7), // tex:l2
                PartPose.ZERO);
        funnel.addOrReplaceChild("layer3", CubeListBuilder.create()
                .texOffs(0, 31).addBox(-5.0F, -11.0F, -5.0F, 10, 4, 10), // tex:l3
                PartPose.ZERO);
        PartDefinition face = funnel.addOrReplaceChild("face", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-6.5F, -15.0F, -6.5F, 13, 4, 13), // tex:l4
                PartPose.ZERO);
        face.addOrReplaceChild("right_eye", CubeListBuilder.create()
                .texOffs(52, 6).addBox(-1.5F, -1.5F, -2.0F, 3, 3, 2), // tex:eye
                PartPose.offset(-3.0F, -13.0F, -6.5F));
        face.addOrReplaceChild("left_eye", CubeListBuilder.create()
                .texOffs(52, 6).addBox(-1.5F, -1.5F, -2.0F, 3, 3, 2), // tex:eye
                PartPose.offset(3.0F, -13.0F, -6.5F));
        // Hollow rim around the top: the dark swirl on top of the face layer shows through as the whirl's mouth.
        funnel.addOrReplaceChild("rim", CubeListBuilder.create()
                .texOffs(26, 56).addBox(-7.5F, -17.0F, -7.5F, 15, 2, 2) // tex:rim_fb
                .texOffs(26, 56).addBox(-7.5F, -17.0F, 5.5F, 15, 2, 2) // tex:rim_fb
                .texOffs(0, 45).addBox(-7.5F, -17.0F, -5.5F, 2, 2, 11) // tex:rim_side
                .texOffs(0, 45).addBox(5.5F, -17.0F, -5.5F, 2, 2, 11), // tex:rim_side
                PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float speed = Math.min(1.0F, state.walkAnimationSpeed);
        // Each layer spins at its own speed: the narrow tip fastest, alternating directions.
        tip.yRot = -t * 0.9F;
        layer2.yRot = t * 0.55F;
        layer3.yRot = -t * 0.32F;
        rim.yRot = t * 0.18F;
        face.yRot = state.yRot * Mth.DEG_TO_RAD + Mth.sin(t * 0.15F) * 0.1F;
        // Lateral sway around the stalk, plus a lean into the direction of travel and into attacks.
        funnel.zRot = Mth.sin(t * 0.13F) * 0.09F;
        funnel.xRot = Mth.cos(t * 0.11F) * 0.05F + speed * 0.15F + Mth.sin(state.attackAnim * Mth.PI) * 0.35F;
        // The lower layers drift off-centre, so the column looks soft.
        tip.x = Mth.sin(t * 0.3F + 1.0F) * 0.8F;
        tip.z = Mth.cos(t * 0.3F + 1.0F) * 0.8F;
        layer2.x = Mth.sin(t * 0.3F) * 0.5F;
        layer2.z = Mth.cos(t * 0.3F) * 0.5F;
        // Eyes pulse, and bulge more when it is hunting.
        float bulge = (state.aggressive ? 1.2F : 1.0F) + Mth.sin(t * 0.25F) * 0.06F;
        rightEye.xScale = rightEye.yScale = bulge;
        leftEye.xScale = leftEye.yScale = bulge;
        rightEye.zScale = leftEye.zScale = bulge + 0.1F;
    }
}
