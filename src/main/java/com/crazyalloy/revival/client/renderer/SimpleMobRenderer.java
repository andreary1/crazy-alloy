package com.crazyalloy.revival.client.renderer;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;

/** Renderer for stage 2 creatures that need nothing beyond a model, one texture and half size for babies. */
public class SimpleMobRenderer<T extends Mob, M extends EntityModel<LivingEntityRenderState>> extends MobRenderer<T, LivingEntityRenderState, M> {
    private final Identifier texture;
    private final float scale;

    public SimpleMobRenderer(EntityRendererProvider.Context context, M model, float shadow, String name) {
        this(context, model, shadow, name, 1.0F);
    }

    public SimpleMobRenderer(EntityRendererProvider.Context context, M model, float shadow, String name, float scale) {
        super(context, model, shadow);
        this.texture = CrazyAlloyRevival.id("textures/entity/" + name + ".png");
        this.scale = scale;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    protected void scale(LivingEntityRenderState state, PoseStack poseStack) {
        float s = state.isBaby ? this.scale * 0.5F : this.scale;
        if (s != 1.0F) {
            poseStack.scale(s, s, s);
        }
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return texture;
    }
}
