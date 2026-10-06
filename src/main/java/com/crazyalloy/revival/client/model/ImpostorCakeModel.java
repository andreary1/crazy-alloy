package com.crazyalloy.revival.client.model;

import com.crazyalloy.revival.entity.ImpostorCake;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Impostor Cake: a three-tier party cake, square layers getting smaller towards the top, each with thick white icing
 * dripping down its sides, and a small white candle with a yellow flame. The bottom tier is split by a wide mouth:
 * its lower part is the jaw resting on the ground and everything above it is the upper jaw, hinged at the back.
 * Inside are small pale teeth and a long flat pink tongue that folds down over the front.
 * <p>
 * Closed (disguised) there is no visible seam: it is just a cake. The reveal gapes the jaw, unrolls the tongue and
 * wobbles the tiers; while revealed the tongue sways, it hops when it moves and the jaw snaps shut on a bite.
 * Texture 64x64; each cube is tagged with the texture region tools/gen_textures_v5.py paints for it.
 */
public class ImpostorCakeModel extends EntityModel<RevivalRenderState> {
    private static final float REVEAL_TICKS = ImpostorCake.REVEAL_TICKS;
    private final ModelPart cake, lowerJaw, upperJaw, middleTier, topTier, flame, tongue, tongueTip;

    public ImpostorCakeModel(ModelPart root) {
        super(root);
        this.cake = root.getChild("cake");
        this.lowerJaw = cake.getChild("lower_jaw");
        this.upperJaw = cake.getChild("upper_jaw");
        this.middleTier = upperJaw.getChild("middle_tier");
        this.topTier = middleTier.getChild("top_tier");
        this.flame = topTier.getChild("candle").getChild("flame");
        this.tongue = lowerJaw.getChild("tongue");
        this.tongueTip = tongue.getChild("tongue_tip");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition cake = root.addOrReplaceChild("cake", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        // The jaw: the bottom 3 pixels of the first tier, with a row of teeth along its front edge.
        PartDefinition lowerJaw = cake.addOrReplaceChild("lower_jaw", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-7.0F, -3.0F, -7.0F, 14, 3, 14) // tex:lower_jaw
                .texOffs(56, 4).addBox(-5.5F, -4.0F, -6.5F, 1, 1, 1) // tex:tooth
                .texOffs(56, 4).addBox(-3.5F, -4.0F, -6.5F, 1, 1, 1) // tex:tooth
                .texOffs(56, 4).addBox(2.5F, -4.0F, -6.5F, 1, 1, 1) // tex:tooth
                .texOffs(56, 4).addBox(4.5F, -4.0F, -6.5F, 1, 1, 1), // tex:tooth
                PartPose.ZERO);
        // The tongue lies flat on the jaw, rooted at the back; its tip folds down over the front when it is out.
        PartDefinition tongue = lowerJaw.addOrReplaceChild("tongue", CubeListBuilder.create()
                .texOffs(0, 50).addBox(-2.0F, -1.0F, -9.0F, 4, 1, 9), // tex:tongue
                PartPose.offset(0.0F, -3.0F, 2.0F));
        tongue.addOrReplaceChild("tongue_tip", CubeListBuilder.create()
                .texOffs(26, 50).addBox(-2.0F, -1.0F, -6.0F, 4, 1, 6), // tex:tongue_tip
                PartPose.offset(0.0F, 0.0F, -9.0F));

        // Upper jaw: the rest of the first tier, hinged on its back edge, carrying the other two tiers and the candle.
        PartDefinition upperJaw = cake.addOrReplaceChild("upper_jaw", CubeListBuilder.create()
                .texOffs(0, 17).addBox(-7.0F, -4.0F, -14.0F, 14, 4, 14) // tex:first_tier
                .texOffs(56, 4).addBox(-4.0F, 0.0F, -13.5F, 1, 1, 1) // tex:tooth
                .texOffs(56, 4).addBox(-1.0F, 0.0F, -13.5F, 1, 1, 1) // tex:tooth
                .texOffs(56, 4).addBox(3.0F, 0.0F, -13.5F, 1, 1, 1), // tex:tooth
                PartPose.offset(0.0F, -3.0F, 7.0F));
        PartDefinition middle = upperJaw.addOrReplaceChild("middle_tier", CubeListBuilder.create()
                .texOffs(0, 35).addBox(-5.0F, -5.0F, -5.0F, 10, 5, 10), // tex:middle_tier
                PartPose.offset(0.0F, -4.0F, -7.0F));
        PartDefinition top = middle.addOrReplaceChild("top_tier", CubeListBuilder.create()
                .texOffs(40, 35).addBox(-3.0F, -4.0F, -3.0F, 6, 4, 6), // tex:top_tier
                PartPose.offset(0.0F, -5.0F, 0.0F));
        PartDefinition candle = top.addOrReplaceChild("candle", CubeListBuilder.create()
                .texOffs(56, 0).addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1), // tex:candle
                PartPose.offset(0.0F, -4.0F, 0.0F));
        candle.addOrReplaceChild("flame", CubeListBuilder.create()
                .texOffs(60, 0).addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1), // tex:flame
                PartPose.offset(0.0F, -3.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float open = state.stance;
        float spd = Math.min(1.0F, state.walkAnimationSpeed * 2.0F) * open;

        // The candle flame flickers even on a "harmless" cake.
        float flicker = 0.85F + 0.15F * Mth.sin(t * 0.9F) * Mth.sin(t * 0.37F + 1.0F);
        flame.xScale = flicker;
        flame.zScale = flicker;
        flame.yScale = 0.8F + 0.35F * Math.abs(Mth.sin(t * 0.55F));

        // Revealed: the jaw hangs open and breathes, the tongue sways from side to side.
        float jaw = open * (0.42F + 0.04F * Mth.sin(t * 0.12F));
        float tongueOut = open;
        float tipFold = 1.25F + 0.15F * Mth.sin(t * 0.2F);
        tongue.yRot = open * Mth.sin(t * 0.13F) * 0.22F;

        // Reveal: a huge gape, the tongue unrolls from a curl, the tiers wobble and settle.
        float r = state.ticksSince(state.actionA);
        if (r >= 0.0F && r < REVEAL_TICKS + 10) {
            float k = r / REVEAL_TICKS;
            float gape = k < 0.35F ? k / 0.35F : Math.max(0.0F, 1.0F - (k - 0.35F) / 0.9F);
            jaw += 0.4F * gape;
            float unroll = Mth.clamp((r - 3.0F) / 10.0F, 0.0F, 1.0F);
            tongueOut = Math.min(tongueOut, unroll);
            tipFold = Mth.lerp(unroll, -2.6F, tipFold);
            float decay = Math.max(0.0F, 1.0F - k * 0.8F);
            middleTier.zRot = Mth.sin(r * 1.3F) * 0.12F * decay;
            topTier.zRot = Mth.sin(r * 1.3F + 1.1F) * 0.2F * decay;
            middleTier.xRot = Mth.sin(r * 0.9F) * 0.05F * decay;
        }

        // Moving: little hops, the tiers swaying behind each one.
        float hop = Math.abs(Mth.sin(state.walkAnimationPos * 0.7F)) * spd;
        cake.y -= hop * 2.5F;
        cake.xRot = Mth.sin(state.walkAnimationPos * 0.7F) * 0.06F * spd;
        middleTier.zRot += Mth.sin(state.walkAnimationPos * 0.7F - 0.6F) * 0.06F * spd;
        topTier.zRot += Mth.sin(state.walkAnimationPos * 0.7F - 1.2F) * 0.09F * spd;

        // Bite: the jaw opens wider and snaps shut halfway through the swing.
        float a = state.attackAnim;
        if (a > 0.0F) {
            jaw = a < 0.4F ? jaw + 0.35F * (a / 0.4F) : Mth.lerp(Mth.clamp((a - 0.4F) / 0.2F, 0.0F, 1.0F), jaw + 0.35F, 0.0F);
            if (a > 0.7F) {
                jaw = Mth.lerp((a - 0.7F) / 0.3F, 0.0F, open * 0.42F);
            }
        }

        upperJaw.xRot = -jaw;
        tongue.visible = tongueOut > 0.05F;
        tongue.z += (1.0F - tongueOut) * 8.0F;
        tongueTip.xRot = tipFold * tongueOut;
    }
}
