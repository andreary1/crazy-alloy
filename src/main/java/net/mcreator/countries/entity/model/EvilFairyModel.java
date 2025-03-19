package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.EvilFairyEntity;

public class EvilFairyModel extends GeoModel<EvilFairyEntity> {
	@Override
	public ResourceLocation getAnimationResource(EvilFairyEntity entity) {
		return new ResourceLocation("cl", "animations/fairy.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(EvilFairyEntity entity) {
		return new ResourceLocation("cl", "geo/fairy.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EvilFairyEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
