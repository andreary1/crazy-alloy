package com.crazyalloy.revival.client.renderer;

import com.crazyalloy.revival.CrazyAlloyRevival;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.Identifier;

/** The vanilla creeper model with the Bubbaloo texture. */
public class BubbalooCreeperRenderer extends CreeperRenderer {
    private static final Identifier TEXTURE = CrazyAlloyRevival.id("textures/entity/bubbaloo_creeper.png");

    public BubbalooCreeperRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(CreeperRenderState state) {
        return TEXTURE;
    }
}
