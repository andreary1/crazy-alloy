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
 * Brown Sugar Rhino (stage 4 remodel): a heavy dark-brown quadruped built of stacked volumes, a wide torso with a
 * high, stepped back, a bulky shoulder that runs into the head with almost no neck, and short, thick legs. The big
 * head ends in a long, angular muzzle with small eyes at the sides, two short ears and a dark mouth line. On the
 * muzzle sit two thick, roughly prismatic horns of packed brown sugar, the front one bigger. Walks with heavy,
 * rolling steps and slow head movements. Charge (revival proposal): it lowers its head and scrapes the ground with
 * a front foot (actionA), then charges with the head down (stance).
 * Texture 128x64; each cube is tagged with the texture region tools/gen_mobs_v4.py paints for it.
 */
public class BrownSugarRhinoModel extends EntityModel<RevivalRenderState> {
    /** Length of the scrape in ticks, shared with the entity's charge. */
    public static final float SCRAPE_TICKS = 30.0F;

    private final ModelPart body, head, tail;
    private final ModelPart frontRight, frontLeft, hindRight, hindLeft;

    public BrownSugarRhinoModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.tail = body.getChild("tail");
        this.frontRight = root.getChild("front_right_leg");
        this.frontLeft = root.getChild("front_left_leg");
        this.hindRight = root.getChild("hind_right_leg");
        this.hindLeft = root.getChild("hind_left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-7.0F, -5.5F, -11.0F, 14, 11, 22) // tex:torso
                .texOffs(0, 33).addBox(-6.0F, -8.5F, -8.0F, 12, 3, 16) // tex:back
                .texOffs(56, 33).addBox(-4.5F, -10.5F, -7.0F, 9, 2, 9) // tex:hump
                .texOffs(72, 0).addBox(-6.0F, -4.5F, -14.0F, 12, 9, 5), // tex:shoulder
                PartPose.offset(0.0F, 13.0F, 2.0F));
        body.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(54, 52).addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1), // tex:tail
                PartPose.offsetAndRotation(0.0F, -3.5F, 11.0F, 0.3F, 0.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(72, 14).addBox(-4.5F, -4.0F, -9.0F, 9, 8, 9) // tex:head
                .texOffs(92, 33).addBox(-3.5F, -2.0F, -15.0F, 7, 6, 6), // tex:muzzle
                PartPose.offset(0.0F, 12.0F, -10.0F));
        PartDefinition bigHorn = head.addOrReplaceChild("big_horn", CubeListBuilder.create()
                .texOffs(20, 52).addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3) // tex:horn_big
                .texOffs(32, 52).addBox(-1.0F, -9.0F, -0.5F, 2, 3, 2), // tex:horn_tip
                PartPose.offsetAndRotation(0.0F, -1.5F, -12.5F, -0.3F, 0.0F, 0.0F));
        head.addOrReplaceChild("small_horn", CubeListBuilder.create()
                .texOffs(40, 52).addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2), // tex:horn_small
                PartPose.offsetAndRotation(0.0F, -1.5F, -9.5F, -0.25F, 0.0F, 0.0F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create()
                .texOffs(48, 52).addBox(-1.0F, -3.0F, -0.5F, 2, 3, 1), // tex:ear
                PartPose.offsetAndRotation(-3.5F, -4.0F, -1.5F, 0.0F, 0.0F, -0.35F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create()
                .texOffs(48, 52).mirror().addBox(-1.0F, -3.0F, -0.5F, 2, 3, 1), // tex:ear
                PartPose.offsetAndRotation(3.5F, -4.0F, -1.5F, 0.0F, 0.0F, 0.35F));

        CubeListBuilder leg = CubeListBuilder.create().texOffs(0, 52).addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5); // tex:leg
        root.addOrReplaceChild("front_right_leg", leg, PartPose.offset(-4.5F, 18.0F, -6.0F));
        root.addOrReplaceChild("front_left_leg", leg, PartPose.offset(4.5F, 18.0F, -6.0F));
        root.addOrReplaceChild("hind_right_leg", leg, PartPose.offset(-4.5F, 18.0F, 10.0F));
        root.addOrReplaceChild("hind_left_leg", leg, PartPose.offset(4.5F, 18.0F, 10.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 0.6662F;
        float speed = Math.min(1.0F, state.walkAnimationSpeed);
        float charge = state.stance;

        // Slow, heavy head: it follows the look direction only part of the way and sways a little.
        head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.5F * (1.0F - charge);
        head.xRot = state.xRot * Mth.DEG_TO_RAD * 0.4F + Mth.sin(t * 0.045F) * 0.05F;

        // Heavy steps: short leg swings, the body rolls from side to side and sinks on every footfall.
        float amp = Mth.lerp(charge, 0.8F, 1.25F);
        float stride = charge > 0.0F ? pos * Mth.lerp(charge, 1.0F, 1.3F) : pos;
        frontRight.xRot = Mth.cos(stride) * amp * speed;
        hindLeft.xRot = Mth.cos(stride) * amp * speed;
        frontLeft.xRot = Mth.cos(stride + Mth.PI) * amp * speed;
        hindRight.xRot = Mth.cos(stride + Mth.PI) * amp * speed;
        body.zRot = Mth.sin(stride) * 0.05F * speed;
        float sink = Mth.abs(Mth.cos(stride)) * 0.8F * speed;
        body.y += sink;
        head.y += sink * 0.8F;
        head.zRot = body.zRot * 0.6F;
        tail.zRot = Mth.sin(t * 0.12F) * 0.25F;

        // Scrape: the head goes down and a front foot drags back along the ground three times, then the charge.
        float lower = charge;
        float s = state.ticksSince(state.actionA);
        if (s >= 0.0F && s < SCRAPE_TICKS + 4.0F) {
            lower = Math.max(lower, Math.min(1.0F, s / 8.0F));
            if (s < SCRAPE_TICKS) {
                float paw = s / SCRAPE_TICKS * 3.0F * Mth.TWO_PI;
                frontRight.xRot = -0.55F + Mth.cos(paw) * 0.55F;
                frontRight.y -= Math.max(0.0F, Mth.sin(paw)) * 1.5F;
                body.xRot = 0.04F;
                body.y += 0.5F;
                head.zRot = Mth.sin(paw) * 0.04F;
            }
        }
        head.xRot += lower * 0.55F;
        head.y += lower * 1.5F;
        if (charge > 0.0F) {
            body.xRot = 0.06F * charge;
        }
    }
}
