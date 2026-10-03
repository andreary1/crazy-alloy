package com.crazyalloy.revival.client.renderer;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.client.ModModelLayers;
import com.crazyalloy.revival.client.model.CandyTubeDogModel;
import com.crazyalloy.revival.client.model.CandyTubeDogRenderState;
import com.crazyalloy.revival.entity.CandyTubeDog;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class CandyTubeDogRenderer extends MobRenderer<CandyTubeDog, CandyTubeDogRenderState, CandyTubeDogModel> {
    private static final Identifier TEXTURE = CrazyAlloyRevival.id("textures/entity/candy_tube_dog.png");

    public CandyTubeDogRenderer(EntityRendererProvider.Context context) {
        super(context, new CandyTubeDogModel(context.bakeLayer(ModModelLayers.CANDY_TUBE_DOG)), 0.4F);
    }

    @Override
    public CandyTubeDogRenderState createRenderState() {
        return new CandyTubeDogRenderState();
    }

    @Override
    public void extractRenderState(CandyTubeDog entity, CandyTubeDogRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.sitting = entity.isInSittingPose();
        state.tame = entity.isTame();
    }

    @Override
    protected void scale(CandyTubeDogRenderState state, PoseStack poseStack) {
        if (state.isBaby) {
            poseStack.scale(0.6F, 0.6F, 0.6F);
        }
    }

    @Override
    public Identifier getTextureLocation(CandyTubeDogRenderState state) {
        return TEXTURE;
    }
}
