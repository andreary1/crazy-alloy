package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.JellySnakeEntity;

public class JellySnakeModel extends GeoModel<JellySnakeEntity> {
	@Override
	public ResourceLocation getAnimationResource(JellySnakeEntity entity) {
		return new ResourceLocation("cl", "animations/jellysnake.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(JellySnakeEntity entity) {
		return new ResourceLocation("cl", "geo/jellysnake.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(JellySnakeEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
