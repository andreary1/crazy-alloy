
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.OgreEntity;
import net.mcreator.countries.client.model.Modelogre;

public class OgreRenderer extends MobRenderer<OgreEntity, Modelogre<OgreEntity>> {
	public OgreRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelogre<OgreEntity>(context.bakeLayer(Modelogre.LAYER_LOCATION)), 0.8f);
	}

	@Override
	public ResourceLocation getTextureLocation(OgreEntity entity) {
		return new ResourceLocation("cl:textures/entities/ogre.png");
	}
}
