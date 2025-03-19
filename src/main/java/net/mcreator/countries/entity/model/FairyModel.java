package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.FairyEntity;

public class FairyModel extends GeoModel<FairyEntity> {
	@Override
	public ResourceLocation getAnimationResource(FairyEntity entity) {
		return new ResourceLocation("cl", "animations/fairy.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FairyEntity entity) {
		return new ResourceLocation("cl", "geo/fairy.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FairyEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
