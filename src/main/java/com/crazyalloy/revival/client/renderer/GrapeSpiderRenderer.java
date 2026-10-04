package com.crazyalloy.revival.client.renderer;

import com.crazyalloy.revival.client.ModModelLayers;
import com.crazyalloy.revival.client.model.GrapeSpiderModel;
import com.crazyalloy.revival.entity.GrapeSpider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class GrapeSpiderRenderer extends RevivalMobRenderer<GrapeSpider, GrapeSpiderModel> {
    public GrapeSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new GrapeSpiderModel(context.bakeLayer(ModModelLayers.GRAPE_SPIDER)), 0.6F, "grape_spider", 1.0F);
    }

    @Override
    protected float getFlipDegrees() {
        return 180.0F;
    }
}
