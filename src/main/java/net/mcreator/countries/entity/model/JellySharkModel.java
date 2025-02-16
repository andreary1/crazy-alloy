package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.JellySharkEntity;

public class JellySharkModel extends GeoModel<JellySharkEntity> {
	@Override
	public ResourceLocation getAnimationResource(JellySharkEntity entity) {
		return new ResourceLocation("cl", "animations/jellyshark.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(JellySharkEntity entity) {
		return new ResourceLocation("cl", "geo/jellyshark.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(JellySharkEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
