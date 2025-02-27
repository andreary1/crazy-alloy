
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.GingerbreadHelperEntity;
import net.mcreator.countries.client.model.Modelgingerbreadhelper;

public class GingerbreadHelperRenderer extends MobRenderer<GingerbreadHelperEntity, Modelgingerbreadhelper<GingerbreadHelperEntity>> {
	public GingerbreadHelperRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgingerbreadhelper<GingerbreadHelperEntity>(context.bakeLayer(Modelgingerbreadhelper.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(GingerbreadHelperEntity entity) {
		return new ResourceLocation("cl:textures/entities/gingerbreadhelper.png");
	}
}
