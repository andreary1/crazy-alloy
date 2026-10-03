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
 * Jelly Shark: four flavour segments (green head, magenta trunk, orange rear, purple tail), a big head with a
 * hinged jaw and two rows of square teeth, a tall angular dorsal fin and a forked tail. The body ripples as a
 * travelling wave with a slight jelly squash and stretch; out of water it lies on its belly and flops.
 * Texture 128x64; each cube is tagged with the texture region tools/gen_mobs_v3.py paints for it.
 */
public class JellySharkModel extends EntityModel<RevivalRenderState> {
    private final ModelPart body, head, jaw, rear, tail, caudal, dorsal, rightFin, leftFin;

    public JellySharkModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.jaw = head.getChild("jaw");
        this.rear = body.getChild("rear");
        this.tail = rear.getChild("tail");
        this.caudal = tail.getChild("caudal");
        this.dorsal = body.getChild("dorsal_fin");
        this.rightFin = body.getChild("right_fin");
        this.leftFin = body.getChild("left_fin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-5.0F, -5.0F, -7.0F, 10, 10, 14), // tex:trunk
                PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(82, 0).addBox(-4.5F, -4.0F, -10.0F, 9, 5, 10) // tex:skull
                .texOffs(0, 37).addBox(-3.5F, -3.0F, -14.0F, 7, 4, 4) // tex:snout
                .texOffs(0, 24).addBox(-3.5F, 1.0F, -13.5F, 7, 1, 12), // tex:teeth_up
                PartPose.offset(0.0F, -1.0F, -7.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create()
                .texOffs(82, 15).addBox(-4.0F, 0.0F, -12.0F, 8, 3, 12) // tex:jaw
                .texOffs(38, 32).addBox(-3.5F, -1.0F, -11.5F, 7, 1, 10), // tex:teeth_low
                PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, 0.35F, 0.0F, 0.0F));

        body.addOrReplaceChild("dorsal_fin", CubeListBuilder.create()
                .texOffs(60, 17).addBox(-1.0F, -7.0F, -3.0F, 2, 7, 7) // tex:dorsal
                .texOffs(38, 24).addBox(-1.0F, -11.0F, 0.0F, 2, 4, 3), // tex:dorsal_tip
                PartPose.offsetAndRotation(0.0F, -4.5F, -1.0F, -0.45F, 0.0F, 0.0F));
        body.addOrReplaceChild("right_fin", CubeListBuilder.create()
                .texOffs(102, 40).addBox(-8.0F, -0.5F, -2.5F, 8, 1, 5), // tex:pec_fin
                PartPose.offsetAndRotation(-5.0F, 3.0F, -3.0F, 0.0F, 0.0F, -0.55F));
        body.addOrReplaceChild("left_fin", CubeListBuilder.create()
                .texOffs(102, 40).mirror().addBox(0.0F, -0.5F, -2.5F, 8, 1, 5), // tex:pec_fin
                PartPose.offsetAndRotation(5.0F, 3.0F, -3.0F, 0.0F, 0.0F, 0.55F));

        PartDefinition rear = body.addOrReplaceChild("rear", CubeListBuilder.create()
                .texOffs(48, 0).addBox(-4.0F, -4.0F, 0.0F, 8, 8, 9), // tex:rear
                PartPose.offset(0.0F, 0.5F, 7.0F));
        rear.addOrReplaceChild("rear_dorsal", CubeListBuilder.create()
                .texOffs(112, 30).addBox(-0.5F, -3.0F, 0.0F, 1, 3, 4), // tex:rear_dorsal
                PartPose.offsetAndRotation(0.0F, -4.0F, 3.0F, -0.3F, 0.0F, 0.0F));
        rear.addOrReplaceChild("right_pelvic_fin", CubeListBuilder.create()
                .texOffs(22, 37).addBox(-4.0F, -0.5F, -1.5F, 4, 1, 3), // tex:pelvic_fin
                PartPose.offsetAndRotation(-4.0F, 3.0F, 4.0F, 0.0F, 0.0F, -0.5F));
        rear.addOrReplaceChild("left_pelvic_fin", CubeListBuilder.create()
                .texOffs(22, 37).mirror().addBox(0.0F, -0.5F, -1.5F, 4, 1, 3), // tex:pelvic_fin
                PartPose.offsetAndRotation(4.0F, 3.0F, 4.0F, 0.0F, 0.0F, 0.5F));
        rear.addOrReplaceChild("anal_fin", CubeListBuilder.create()
                .texOffs(120, 0).addBox(-0.5F, 0.0F, 0.0F, 1, 3, 3), // tex:anal_fin
                PartPose.offsetAndRotation(0.0F, 4.0F, 5.0F, 0.4F, 0.0F, 0.0F));

        PartDefinition tail = rear.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(78, 30).addBox(-2.5F, -3.0F, 0.0F, 5, 6, 7), // tex:tail
                PartPose.offset(0.0F, 0.0F, 9.0F));
        PartDefinition caudal = tail.addOrReplaceChild("caudal", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 7.0F));
        caudal.addOrReplaceChild("upper_lobe", CubeListBuilder.create()
                .texOffs(48, 17).addBox(-1.0F, -11.0F, 0.0F, 2, 11, 4), // tex:fin_upper
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        caudal.addOrReplaceChild("lower_lobe", CubeListBuilder.create()
                .texOffs(102, 30).addBox(-1.0F, 0.0F, 0.0F, 2, 7, 3), // tex:fin_lower
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.7F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        boolean water = state.isInWater;
        float speed = Math.min(1.0F, state.walkAnimationSpeed);
        // A wave travels from the head to the tail; faster and wider while swimming hard or flopping on land.
        float freq = water ? 0.22F + speed * 0.25F : 0.8F;
        float amp = water ? 0.12F + speed * 0.2F : 0.3F;
        body.yRot = Mth.sin(t * freq) * amp * 0.3F;
        head.yRot = -Mth.sin(t * freq + 0.6F) * amp * 0.4F;
        rear.yRot = Mth.sin(t * freq - 0.9F) * amp;
        tail.yRot = Mth.sin(t * freq - 1.8F) * amp * 1.3F;
        caudal.yRot = Mth.sin(t * freq - 2.7F) * amp * 1.6F;

        // Jelly: each segment squashes and stretches a little, out of step with the others.
        float w1 = Mth.sin(t * 0.33F) * 0.035F;
        float w2 = Mth.sin(t * 0.33F - 1.3F) * 0.045F;
        float w3 = Mth.sin(t * 0.33F - 2.6F) * 0.05F;
        body.xScale = 1.0F + w1;
        body.yScale = 1.0F - w1;
        rear.xScale = 1.0F + w2;
        rear.yScale = 1.0F - w2;
        tail.xScale = 1.0F + w3;
        tail.yScale = 1.0F - w3;
        dorsal.zRot = Mth.sin(t * 0.33F - 0.5F) * 0.06F;

        if (water) {
            body.xRot = state.xRot * Mth.DEG_TO_RAD;
            rightFin.zRot += Mth.sin(t * 0.2F) * 0.12F;
            leftFin.zRot -= Mth.sin(t * 0.2F) * 0.12F;
        } else {
            // Beached: rests on its belly with the side fins spread, and rolls from side to side as it flops.
            body.y += 4.0F;
            body.zRot = Mth.sin(t * 0.8F) * (state.onGround ? 0.22F : 0.08F);
            rightFin.zRot = -0.12F;
            leftFin.zRot = 0.12F;
        }

        // The mouth hangs open; a bite opens it wide and snaps it shut. Hunting sharks gape wider.
        float base = state.aggressive ? 0.55F : 0.32F + Mth.sin(t * 0.1F) * 0.06F;
        float jawRot = base;
        float a = state.attackAnim;
        if (a > 0.0F) {
            if (a < 0.4F) {
                jawRot = Mth.lerp(a / 0.4F, base, 1.0F);
            } else if (a < 0.55F) {
                jawRot = Mth.lerp((a - 0.4F) / 0.15F, 1.0F, 0.0F);
            } else {
                jawRot = Mth.lerp((a - 0.55F) / 0.45F, 0.0F, base);
            }
            head.xRot = -Mth.sin(a * Mth.PI) * 0.2F;
        }
        jaw.xRot = jawRot;
    }
}
