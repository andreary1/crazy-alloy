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
 * Living Ice Cream: a walking ice cream cone. A cube of ice cream (with a dripping lip) sits on a yellow checked wafer
 * body that narrows towards the bottom, carried by two thin pale legs with little feet. The four flavours share this
 * model and differ by texture. Toddles with quick steps; the scoop wobbles on top.
 * Texture 64x32; cubes are tagged for tools/gen_textures_v6.py.
 */
public class LivingIceCreamModel extends EntityModel<RevivalRenderState> {
    private final ModelPart cone, scoop, rightLeg, leftLeg;

    public LivingIceCreamModel(ModelPart root) {
        super(root);
        this.cone = root.getChild("cone");
        this.scoop = cone.getChild("scoop");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(48, 11).addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1) // tex:leg
                .texOffs(52, 11).addBox(-1.0F, 4.0F, -2.0F, 2, 1, 3), // tex:foot
                PartPose.offset(-1.3F, 19.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(48, 11).mirror().addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1) // tex:leg
                .texOffs(52, 11).mirror().addBox(-1.0F, 4.0F, -2.0F, 2, 1, 3), // tex:foot
                PartPose.offset(1.3F, 19.0F, 0.0F));
        // The cone is built from its tip up: narrow at the legs, wide under the scoop.
        PartDefinition cone = root.addOrReplaceChild("cone", CubeListBuilder.create()
                .texOffs(40, 11).addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2) // tex:cone_tip
                .texOffs(24, 11).addBox(-2.0F, -5.0F, -2.0F, 4, 3, 4) // tex:cone_mid
                .texOffs(0, 11).addBox(-3.0F, -8.0F, -3.0F, 6, 3, 6), // tex:cone_top
                PartPose.offset(0.0F, 19.5F, 0.0F));
        cone.addOrReplaceChild("scoop", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -5.0F, -3.0F, 6, 5, 6) // tex:scoop
                .texOffs(24, 0).addBox(-3.5F, -1.5F, -3.5F, 7, 2, 7), // tex:drip
                PartPose.offset(0.0F, -8.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(RevivalRenderState state) {
        super.setupAnim(state);
        float t = state.ageInTicks;
        float pos = state.walkAnimationPos * 1.3F;
        float spd = Math.min(1.0F, state.walkAnimationSpeed * 1.5F);
        rightLeg.xRot = Mth.cos(pos) * 1.0F * spd;
        leftLeg.xRot = Mth.cos(pos + Mth.PI) * 1.0F * spd;
        cone.y -= Math.abs(Mth.sin(pos)) * 1.0F * spd;
        cone.zRot = Mth.sin(pos) * 0.08F * spd;
        cone.yRot = state.yRot * Mth.DEG_TO_RAD * 0.3F;
        scoop.zRot = Mth.sin(pos - 0.8F) * 0.1F * spd + Mth.sin(t * 0.1F) * 0.03F;
        scoop.xRot = Mth.sin(t * 0.07F) * 0.03F;
        scoop.yScale = 1.0F + Mth.sin(t * 0.15F) * 0.03F; // soft, a little squishy
    }
}
