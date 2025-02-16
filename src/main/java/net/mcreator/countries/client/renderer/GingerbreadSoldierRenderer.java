
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.GingerbreadSoldierEntity;
import net.mcreator.countries.client.model.Modelgingerbreadsoldier;

public class GingerbreadSoldierRenderer extends MobRenderer<GingerbreadSoldierEntity, Modelgingerbreadsoldier<GingerbreadSoldierEntity>> {
	public GingerbreadSoldierRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgingerbreadsoldier<GingerbreadSoldierEntity>(context.bakeLayer(Modelgingerbreadsoldier.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(GingerbreadSoldierEntity entity) {
		return new ResourceLocation("cl:textures/entities/gingerbreadsoldier.png");
	}
}
