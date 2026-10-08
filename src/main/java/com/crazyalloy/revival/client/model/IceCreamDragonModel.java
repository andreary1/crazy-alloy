package com.crazyalloy.revival.client.model;

import com.crazyalloy.revival.entity.IceCreamDragon;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Ice Cream Dragon: a heavy, wingless dragon. A large chocolate-brown torso on four thick pink legs with wide feet;
 * a long vanilla neck in three segments curving up and forward to a big head with a rectangular snout and a deep lower
 * jaw (yellowish inside), yellow eyes, and mint-green crests along the head, neck, back and tail.
 * <ul>
 * <li>Walk: diagonal leg pairs, the neck and tail swaying against each other.</li>
 * <li>Fireball volley: the neck draws back before each of the three shots, then thrusts forward with the jaw open.</li>
 * <li>Roar: the head goes up, the jaw opens wide and the body shudders.</li>
 * <li>Bite: the neck lunges down and the jaw snaps shut.</li>
 * </ul>
 * Texture 128x128; cubes are tagged for tools/gen_textures_v6.py. Rendered at 1.6x.
 */
public class IceCreamDragonModel extends EntityModel<RevivalRenderState> {
    private final ModelPart body, neck1, neck2, neck3, head, jaw, tail1, tail2, tail3;
    private final ModelPart frontRight, frontLeft, backRight, backLeft;

    public IceCreamDragonModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.neck1 = body.getChild("neck1");
        this.neck2 = neck1.getChild("neck2");
        this.neck3 = neck2.getChild("neck3");
        this.head = neck3.getChild("head");
        this.jaw = head.getChild("jaw");
        this.tail1 = body.getChild("tail1");
        this.tail2 = tail1.getChild("tail2");
        this.tail3 = tail2.getChild("tail3");
        this.frontRight = root.getChild("front_right_leg");
        this.frontLeft = root.getChild("front_left_leg");
        this.backRight = root.getChild("back_right_leg");
        this.backLeft = root.getChild("back_left_leg");
    }

    private static CubeListBuilder leg(boolean mirror) {
        CubeListBuilder b = CubeListBuilder.create();
        if (mirror) {
            b.mirror();
        }
        return b.texOffs(80, 0).addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6) // tex:leg
                .texOffs(80, 18).addBox(-4.0F, 10.0F, -5.0F, 8, 2, 9); // tex:foot
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("front_right_leg", leg(false), PartPose.offset(-6.0F, 12.0F, -7.0F));
        root.addOrReplaceChild("front_left_leg", leg(true), PartPose.offset(6.0F, 12.0F, -7.0F));
        root.addOrReplaceChild("back_right_leg", leg(false), PartPose.offset(-6.0F, 12.0F, 8.0F));
        root.addOrReplaceChild("back_left_leg", leg(true), PartPose.offset(6.0F, 12.0F, 8.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8.0F, -14.0F, -12.0F, 16, 14, 24) // tex:body
                .texOffs(104, 0).addBox(-0.5F, -17.0F, -7.0F, 1, 3, 6) // tex:crest
                .texOffs(104, 0).addBox(-0.5F, -17.0F, 0.0F, 1, 3, 6) // tex:crest
                .texOffs(104, 0).addBox(-0.5F, -17.0F, 6.0F, 1, 3, 6), // tex:crest
                PartPose.offset(0.0F, 14.0F, 0.5F));

        // Neck: three segments, each angled further up; the head hangs forward from the last one.
        PartDefinition neck1 = body.addOrReplaceChild("neck1", CubeListBuilder.create()
                .texOffs(0, 38).addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8) // tex:neck
                .texOffs(104, 0).addBox(-0.5F, -6.0F, -7.0F, 1, 3, 6), // tex:crest
                PartPose.offsetAndRotation(0.0F, -10.0F, -11.0F, -0.55F, 0.0F, 0.0F));
        PartDefinition neck2 = neck1.addOrReplaceChild("neck2", CubeListBuilder.create()
                .texOffs(0, 38).addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8) // tex:neck
                .texOffs(104, 0).addBox(-0.5F, -6.0F, -7.0F, 1, 3, 6), // tex:crest
                PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, -0.25F, 0.0F, 0.0F));
        PartDefinition neck3 = neck2.addOrReplaceChild("neck3", CubeListBuilder.create()
                .texOffs(0, 38).addBox(-3.0F, -3.0F, -8.0F, 6, 6, 8) // tex:neck
                .texOffs(104, 0).addBox(-0.5F, -6.0F, -7.0F, 1, 3, 6), // tex:crest
                PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.35F, 0.0F, 0.0F));
        PartDefinition head = neck3.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(28, 38).addBox(-5.0F, -6.0F, -10.0F, 10, 8, 12) // tex:head
                .texOffs(72, 38).addBox(-4.0F, -3.0F, -18.0F, 8, 5, 8) // tex:snout
                .texOffs(104, 51).addBox(-1.0F, -10.0F, -6.0F, 2, 4, 6) // tex:head_crest
                .texOffs(120, 0).addBox(-3.5F, 2.0F, -17.5F, 1, 2, 1) // tex:tooth
                .texOffs(120, 0).addBox(2.5F, 2.0F, -17.5F, 1, 2, 1), // tex:tooth
                PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.45F, 0.0F, 0.0F));
        head.addOrReplaceChild("jaw", CubeListBuilder.create()
                .texOffs(0, 58).addBox(-4.0F, 0.0F, -10.0F, 8, 3, 10), // tex:jaw
                PartPose.offset(0.0F, 2.0F, -7.5F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create()
                .texOffs(36, 58).addBox(-4.0F, -3.5F, 0.0F, 8, 7, 10) // tex:tail1
                .texOffs(104, 0).addBox(-0.5F, -6.5F, 2.0F, 1, 3, 6), // tex:crest
                PartPose.offsetAndRotation(0.0F, -9.0F, 11.0F, -0.2F, 0.0F, 0.0F));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create()
                .texOffs(72, 58).addBox(-3.0F, -2.5F, 0.0F, 6, 5, 10) // tex:tail2
                .texOffs(104, 0).addBox(-0.5F, -5.5F, 2.0F, 1, 3, 6), // tex:crest
                PartPose.offsetAndRotation(0.0F, 0.5F, 9.5F, 0.2F, 0.0F, 0.0F));
        tail2.addOrReplaceChild("tail3", CubeListBuilder.create()
                .texOffs(0, 75).addBox(-2.0F, -2.0F, 0.0F, 4, 4, 10), // tex:tail3
                PartPose.offsetAndRotation(0.0F, 0.5F, 9.5F, 0.2F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 0.45F;
        float spd = Math.min(1.0F, state.walkAnimationSpeed);

        // Heavy walk: diagonal pairs.
        frontRight.xRot = Mth.cos(pos) * 0.7F * spd;
        backLeft.xRot = Mth.cos(pos) * 0.7F * spd;
        frontLeft.xRot = Mth.cos(pos + Mth.PI) * 0.7F * spd;
        backRight.xRot = Mth.cos(pos + Mth.PI) * 0.7F * spd;
        body.y += Math.abs(Mth.cos(pos)) * 0.8F * spd;
        body.zRot = Mth.cos(pos) * 0.03F * spd;

        // Breathing and the look direction, spread along the neck.
        float breathe = Mth.sin(t * 0.08F);
        float yaw = Mth.clamp(state.yRot, -60.0F, 60.0F) * Mth.DEG_TO_RAD;
        float pitch = Mth.clamp(state.xRot, -30.0F, 30.0F) * Mth.DEG_TO_RAD;
        neck1.yRot = yaw * 0.3F;
        neck2.yRot = yaw * 0.3F;
        neck3.yRot = yaw * 0.2F;
        head.yRot = yaw * 0.2F;
        neck1.xRot += breathe * 0.04F;
        head.xRot += pitch * 0.5F - breathe * 0.03F;
        neck2.zRot = Mth.sin(pos * 0.5F) * 0.06F * spd;
        jaw.xRot = 0.05F + Math.max(0.0F, breathe) * 0.05F;

        // Tail sways against the neck.
        tail1.yRot = Mth.sin(t * 0.06F) * 0.12F + Mth.sin(pos) * 0.15F * spd;
        tail2.yRot = Mth.sin(t * 0.06F - 0.8F) * 0.16F + Mth.sin(pos - 0.8F) * 0.18F * spd;
        tail3.yRot = Mth.sin(t * 0.06F - 1.6F) * 0.2F + Mth.sin(pos - 1.6F) * 0.2F * spd;

        // Fireball volley: draw back, thrust and open the jaw for each of the three shots.
        float v = state.ticksSince(state.actionA);
        if (v >= 0.0F && v < IceCreamDragon.VOLLEY_LENGTH + 4) {
            float shotPhase = 0.0F;
            for (int shot : new int[] {12, 22, 32}) {
                float d = v - shot;
                if (d > -8.0F && d < 6.0F) {
                    shotPhase = d < 0.0F ? -(d + 8.0F) / 8.0F : 1.0F - d / 6.0F; // -1 drawn back .. +1 thrust
                    shotPhase = d < 0.0F ? Math.max(-1.0F, shotPhase) : shotPhase;
                }
            }
            float back = Math.max(0.0F, -shotPhase);
            float thrust = Math.max(0.0F, shotPhase);
            neck1.xRot += -0.3F * back + 0.25F * thrust;
            neck2.xRot += -0.2F * back + 0.15F * thrust;
            head.xRot += 0.15F * back - 0.35F * thrust;
            jaw.xRot = Math.max(jaw.xRot, 0.9F * thrust);
        }

        // Roar: head raised high, jaw wide open, a shudder through the body.
        float r = state.ticksSince(state.actionB);
        if (r >= 0.0F && r < IceCreamDragon.ROAR_LENGTH + 6) {
            float k = r / IceCreamDragon.ROAR_LENGTH;
            float up = k < 0.3F ? k / 0.3F : Math.max(0.0F, 1.0F - (k - 0.75F) / 0.35F);
            up = Mth.clamp(up, 0.0F, 1.0F);
            neck1.xRot -= 0.45F * up;
            neck2.xRot -= 0.25F * up;
            head.xRot -= 0.35F * up;
            jaw.xRot = Math.max(jaw.xRot, 1.0F * up);
            body.zRot += Mth.sin(r * 2.1F) * 0.04F * up;
            body.xRot -= 0.08F * up;
        }

        // Bite: the neck lunges down, the jaw opens and snaps shut.
        float a = state.attackAnim;
        if (a > 0.0F) {
            float lunge = Mth.sin(a * Mth.PI);
            neck1.xRot += 0.45F * lunge;
            neck2.xRot += 0.3F * lunge;
            head.xRot += 0.2F * lunge;
            jaw.xRot = a < 0.5F ? Math.max(jaw.xRot, 1.0F * (a / 0.5F)) : jaw.xRot * (1.0F - (a - 0.5F) * 2.0F) + 0.05F;
        }
    }
}
