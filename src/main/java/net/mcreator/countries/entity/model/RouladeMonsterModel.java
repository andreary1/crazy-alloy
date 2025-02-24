package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.RouladeMonsterEntity;

public class RouladeMonsterModel extends GeoModel<RouladeMonsterEntity> {
	@Override
	public ResourceLocation getAnimationResource(RouladeMonsterEntity entity) {
		return new ResourceLocation("cl", "animations/orangemonster.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RouladeMonsterEntity entity) {
		return new ResourceLocation("cl", "geo/orangemonster.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RouladeMonsterEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
