
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.BrownSugarRhinoEntity;
import net.mcreator.countries.client.model.Modelbrownsugarhino;

public class BrownSugarRhinoRenderer extends MobRenderer<BrownSugarRhinoEntity, Modelbrownsugarhino<BrownSugarRhinoEntity>> {
	public BrownSugarRhinoRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbrownsugarhino<BrownSugarRhinoEntity>(context.bakeLayer(Modelbrownsugarhino.LAYER_LOCATION)), 1.2f);
	}

	@Override
	public ResourceLocation getTextureLocation(BrownSugarRhinoEntity entity) {
		return new ResourceLocation("cl:textures/entities/brownsugarrhino.png");
	}
}
