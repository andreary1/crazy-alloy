package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.CandyTubeDogEntity;

public class CandyTubeDogModel extends GeoModel<CandyTubeDogEntity> {
	@Override
	public ResourceLocation getAnimationResource(CandyTubeDogEntity entity) {
		return new ResourceLocation("cl", "animations/sugartubedog.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CandyTubeDogEntity entity) {
		return new ResourceLocation("cl", "geo/sugartubedog.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CandyTubeDogEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
