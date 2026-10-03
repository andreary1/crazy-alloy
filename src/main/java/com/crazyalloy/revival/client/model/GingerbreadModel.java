package com.crazyalloy.revival.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * The small gingerbread men: a rectangular head, a narrow torso and simple limbs, about 1.4 blocks tall.
 * The Warrior adds a red headband with a tail that flutters to one side and fights with raised fists
 * (quick bouncing steps, short alternating jabs). The Soldier adds a tall dark green hat with a brim and a long
 * rifle held at the ready, which he raises to aim, fires with a recoil and reloads.
 * Texture 64x64 (both textures share one layout); cubes are tagged for tools/gen_mobs_v3.py.
 */
public class GingerbreadModel extends EntityModel<RevivalRenderState> {
    public enum Kind { WARRIOR, SOLDIER }

    private final Kind kind;
    private final ModelPart body, head, rightArm, leftArm, rightLeg, leftLeg;
    private final ModelPart bandTailUpper, bandTailLower, rifle;

    public GingerbreadModel(ModelPart root, Kind kind) {
        super(root);
        this.kind = kind;
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.rightArm = body.getChild("right_arm");
        this.leftArm = body.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        if (kind == Kind.WARRIOR) {
            ModelPart knot = head.getChild("band_knot");
            this.bandTailUpper = knot.getChild("band_tail_upper");
            this.bandTailLower = knot.getChild("band_tail_lower");
            this.rifle = null;
        } else {
            this.bandTailUpper = null;
            this.bandTailLower = null;
            this.rifle = body.getChild("rifle");
        }
    }

    private static PartDefinition base(MeshDefinition mesh) {
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(48, 11).addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3), // tex:leg
                PartPose.offset(-2.0F, 17.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(48, 11).mirror().addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3), // tex:leg
                PartPose.offset(2.0F, 17.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(26, 0).addBox(-3.5F, -8.0F, -2.0F, 7, 8, 4), // tex:torso
                PartPose.offset(0.0F, 17.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -7.0F, -2.5F, 8, 7, 5), // tex:head
                PartPose.offset(0.0F, -8.0F, 0.0F));
        body.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(48, 0).addBox(-1.5F, -1.0F, -1.5F, 3, 8, 3), // tex:arm
                PartPose.offset(-5.0F, -7.0F, 0.0F));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(48, 0).mirror().addBox(-1.5F, -1.0F, -1.5F, 3, 8, 3), // tex:arm
                PartPose.offset(5.0F, -7.0F, 0.0F));
        return body;
    }

    public static LayerDefinition createWarriorLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition head = base(mesh).getChild("head");
        head.addOrReplaceChild("band", CubeListBuilder.create()
                .texOffs(30, 30).addBox(-4.0F, -7.0F, -2.5F, 8, 2, 5, new CubeDeformation(0.3F)), // tex:w_band
                PartPose.ZERO);
        PartDefinition knot = head.addOrReplaceChild("band_knot", CubeListBuilder.create()
                .texOffs(58, 21).addBox(0.0F, -1.0F, -1.0F, 1, 2, 2), // tex:w_knot
                PartPose.offset(4.2F, -6.0F, 1.0F));
        knot.addOrReplaceChild("band_tail_upper", CubeListBuilder.create()
                .texOffs(14, 26).addBox(0.0F, -0.5F, -0.5F, 4, 1, 1), // tex:w_tail
                PartPose.offsetAndRotation(1.0F, -0.5F, 0.0F, 0.0F, 0.5F, -0.3F));
        knot.addOrReplaceChild("band_tail_lower", CubeListBuilder.create()
                .texOffs(14, 26).addBox(0.0F, -0.5F, -0.5F, 4, 1, 1), // tex:w_tail
                PartPose.offsetAndRotation(1.0F, 0.5F, 0.0F, 0.0F, 0.3F, 0.3F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    public static LayerDefinition createSoldierLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition body = base(mesh);
        body.getChild("head").addOrReplaceChild("hat", CubeListBuilder.create()
                .texOffs(24, 22).addBox(-5.0F, -7.5F, -3.5F, 10, 1, 7) // tex:s_brim
                .texOffs(0, 12).addBox(-3.5F, -13.5F, -2.5F, 7, 6, 5) // tex:s_crown
                .texOffs(14, 23).addBox(-1.0F, -12.5F, -3.0F, 2, 2, 1), // tex:s_badge
                PartPose.ZERO);
        // The rifle points along -z from its grip; setupAnim places it between the ready and aiming poses.
        body.addOrReplaceChild("rifle", CubeListBuilder.create()
                .texOffs(0, 23).addBox(-1.0F, -1.0F, 2.0F, 2, 3, 5) // tex:s_stock
                .texOffs(24, 12).addBox(-1.0F, -1.5F, -6.0F, 2, 2, 8) // tex:s_body
                .texOffs(14, 30).addBox(-0.5F, -1.5F, -13.0F, 1, 1, 7), // tex:s_barrel
                PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float spd = Math.min(1.0F, state.walkAnimationSpeed);
        boolean warriorFighting = kind == Kind.WARRIOR && state.aggressive;
        float pos = state.walkAnimationPos * 0.6662F * (warriorFighting ? 1.4F : 1.0F);
        rightLeg.xRot = Mth.cos(pos) * 1.1F * spd;
        leftLeg.xRot = Mth.cos(pos + Mth.PI) * 1.1F * spd;
        head.yRot = state.yRot * Mth.DEG_TO_RAD;
        head.xRot = state.xRot * Mth.DEG_TO_RAD;
        body.zRot = Mth.cos(pos) * 0.05F * spd; // stiff cookie waddle
        if (kind == Kind.WARRIOR) {
            animateWarrior(state, t, pos, spd);
        } else {
            animateSoldier(state, t, pos, spd);
        }
    }

    private void animateWarrior(RevivalRenderState state, float t, float pos, float spd) {
        float flutter = Mth.sin(t * 0.55F) * 0.25F + spd * 0.5F;
        bandTailUpper.yRot = 0.5F + flutter;
        bandTailLower.yRot = 0.3F + flutter * 0.8F;
        bandTailLower.zRot = 0.3F + Mth.sin(t * 0.55F + 1.0F) * 0.15F;
        if (!state.aggressive) {
            rightArm.xRot = Mth.cos(pos + Mth.PI) * 0.9F * spd;
            leftArm.xRot = Mth.cos(pos) * 0.9F * spd;
            rightArm.zRot = 0.05F + Mth.sin(t * 0.09F) * 0.03F;
            leftArm.zRot = -0.05F - Mth.sin(t * 0.09F) * 0.03F;
            return;
        }
        // Guard: fists up in front of the face, leaning in, bouncing on the toes.
        float bob = Mth.sin(t * 0.35F) * 0.08F;
        body.xRot = 0.15F;
        head.xRot -= 0.15F;
        body.y -= Math.abs(Mth.sin(t * 0.5F)) * 0.6F;
        rightArm.xRot = -1.35F + bob;
        rightArm.yRot = -0.35F;
        leftArm.xRot = -1.35F - bob;
        leftArm.yRot = 0.35F;
        float a = state.attackAnim;
        if (a > 0.0F) {
            // Short jab with the arm the server swung; the shoulders twist into it.
            float p = Mth.sin(a * Mth.PI);
            ModelPart arm = state.offHandSwing ? leftArm : rightArm;
            arm.xRot = Mth.lerp(p, arm.xRot, -1.65F);
            arm.yRot = Mth.lerp(p, arm.yRot, 0.0F);
            arm.z -= 2.5F * p;
            body.yRot += (state.offHandSwing ? -0.3F : 0.3F) * p;
        }
    }

    private void animateSoldier(RevivalRenderState state, float t, float pos, float spd) {
        float aim = state.stance;
        float pitch = state.xRot * Mth.DEG_TO_RAD;
        float yaw = state.yRot * Mth.DEG_TO_RAD;
        // Ready: rifle diagonally across the chest, barrel up to the left. Aim: stock at the right shoulder, barrel forward.
        float swing = (1.0F - aim) * 0.15F * spd;
        rightArm.xRot = Mth.lerp(aim, -0.75F, -Mth.HALF_PI + pitch) + Mth.cos(pos) * swing;
        rightArm.yRot = Mth.lerp(aim, -0.25F, -0.15F + yaw);
        leftArm.xRot = Mth.lerp(aim, -1.2F, -Mth.HALF_PI + pitch) + Mth.cos(pos + Mth.PI) * swing;
        leftArm.yRot = Mth.lerp(aim, 0.65F, 0.5F + yaw);
        rifle.x = Mth.lerp(aim, 0.0F, -3.0F);
        rifle.y = Mth.lerp(aim, -4.0F, -7.0F);
        rifle.z = Mth.lerp(aim, -4.0F, -6.0F);
        rifle.xRot = Mth.lerp(aim, -1.15F, pitch);
        rifle.yRot = Mth.lerp(aim, 0.0F, yaw);
        rifle.zRot = Mth.lerp(aim, 0.35F, 0.0F);
        float e = state.ticksSince(state.actionA);
        if (e >= 0.0F && e < 6.0F) {
            // Recoil: the rifle kicks back and the muzzle climbs.
            float k = (1.0F - e / 6.0F) * (1.0F - e / 6.0F);
            rifle.z += 2.0F * k;
            rifle.xRot -= 0.35F * k;
            rightArm.xRot -= 0.25F * k;
            leftArm.xRot -= 0.25F * k;
            body.xRot -= 0.1F * k;
            head.xRot -= 0.1F * k;
        } else if (e >= 7.0F && e < 25.0F) {
            // Reload: rifle tipped down, the left hand works the action twice, eyes on the rifle.
            float r = Mth.sin((e - 7.0F) / 18.0F * Mth.PI);
            rifle.xRot += 0.6F * r;
            rifle.zRot += 0.4F * r;
            rifle.y += 1.5F * r;
            leftArm.xRot += 0.4F * r + Mth.sin((e - 7.0F) * 0.7F) * 0.3F * r;
            leftArm.z += Mth.sin((e - 7.0F) * 0.7F) * 1.2F * r;
            head.xRot += 0.35F * r;
        }
    }
}
