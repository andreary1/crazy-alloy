package com.crazyalloy.revival.client.renderer;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.client.model.RevivalRenderState;
import com.crazyalloy.revival.entity.AnimatedMob;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;

/** Renderer for the animated creatures: one texture, an optional scale, and the shared animation state. */
public class RevivalMobRenderer<T extends Mob, M extends EntityModel<RevivalRenderState>> extends MobRenderer<T, RevivalRenderState, M> {
    private final Identifier texture;
    private final float scale;

    public RevivalMobRenderer(EntityRendererProvider.Context context, M model, float shadow, String name, float scale) {
        super(context, model, shadow * scale);
        this.texture = CrazyAlloyRevival.id("textures/entity/" + name + ".png");
        this.scale = scale;
    }

    @Override
    public RevivalRenderState createRenderState() {
        return new RevivalRenderState();
    }

    @Override
    public void extractRenderState(T entity, RevivalRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.attackAnim = entity.getAttackAnim(partialTick);
        state.offHandSwing = entity.swingingArm == InteractionHand.OFF_HAND;
        state.aggressive = entity.isAggressive();
        state.onGround = entity.onGround();
        if (entity instanceof AnimatedMob animated) {
            state.actionA.copyFrom(animated.actionA());
            state.actionB.copyFrom(animated.actionB());
            state.stance = animated.stance(partialTick);
        }
    }

    @Override
    protected void scale(RevivalRenderState state, PoseStack poseStack) {
        float s = state.isBaby ? this.scale * 0.5F : this.scale;
        if (s != 1.0F) {
            poseStack.scale(s, s, s);
        }
    }

    @Override
    public Identifier getTextureLocation(RevivalRenderState state) {
        return texture;
    }
}
