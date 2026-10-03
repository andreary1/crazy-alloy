package com.crazyalloy.revival.client.renderer;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.client.ModModelLayers;
import com.crazyalloy.revival.client.model.LollipopGuyModel;
import com.crazyalloy.revival.entity.LollipopGuy;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import com.crazyalloy.revival.client.model.LollipopGuyRenderState;
import net.minecraft.resources.Identifier;

public class LollipopGuyRenderer extends MobRenderer<LollipopGuy, LollipopGuyRenderState, LollipopGuyModel> {
    private static final Identifier TEXTURE = CrazyAlloyRevival.id("textures/entity/lollipop_guy.png");

    public LollipopGuyRenderer(EntityRendererProvider.Context context) {
        super(context, new LollipopGuyModel(context.bakeLayer(ModModelLayers.LOLLIPOP_GUY)), 0.35F);
    }

    @Override
    public LollipopGuyRenderState createRenderState() {
        return new LollipopGuyRenderState();
    }

    @Override
    public void extractRenderState(LollipopGuy entity, LollipopGuyRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.attackAnim = entity.getAttackAnim(partialTick);
    }

    @Override
    public Identifier getTextureLocation(LollipopGuyRenderState state) {
        return TEXTURE;
    }
}
