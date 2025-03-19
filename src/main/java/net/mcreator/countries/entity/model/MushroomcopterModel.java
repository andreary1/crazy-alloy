package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.MushroomcopterEntity;

public class MushroomcopterModel extends GeoModel<MushroomcopterEntity> {
	@Override
	public ResourceLocation getAnimationResource(MushroomcopterEntity entity) {
		return new ResourceLocation("cl", "animations/mushroomcopter.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MushroomcopterEntity entity) {
		return new ResourceLocation("cl", "geo/mushroomcopter.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MushroomcopterEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
