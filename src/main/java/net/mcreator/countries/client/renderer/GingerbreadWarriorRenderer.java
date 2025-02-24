
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.GingerbreadWarriorEntity;
import net.mcreator.countries.client.model.Modelgingerbreadwarrior;

public class GingerbreadWarriorRenderer extends MobRenderer<GingerbreadWarriorEntity, Modelgingerbreadwarrior<GingerbreadWarriorEntity>> {
	public GingerbreadWarriorRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgingerbreadwarrior<GingerbreadWarriorEntity>(context.bakeLayer(Modelgingerbreadwarrior.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(GingerbreadWarriorEntity entity) {
		return new ResourceLocation("cl:textures/entities/gingerbreadwarrior.png");
	}
}
