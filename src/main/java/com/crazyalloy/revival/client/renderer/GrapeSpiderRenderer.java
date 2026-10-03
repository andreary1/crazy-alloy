package com.crazyalloy.revival.client.renderer;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.client.ModModelLayers;
import com.crazyalloy.revival.client.model.GrapeSpiderModel;
import com.crazyalloy.revival.entity.GrapeSpider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class GrapeSpiderRenderer extends MobRenderer<GrapeSpider, LivingEntityRenderState, GrapeSpiderModel> {
    private static final Identifier TEXTURE = CrazyAlloyRevival.id("textures/entity/grape_spider.png");

    public GrapeSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new GrapeSpiderModel(context.bakeLayer(ModModelLayers.GRAPE_SPIDER)), 0.6F);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    protected float getFlipDegrees() {
        return 180.0F;
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }
}
