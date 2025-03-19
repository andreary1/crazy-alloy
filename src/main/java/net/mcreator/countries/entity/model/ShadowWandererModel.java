package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.ShadowWandererEntity;

public class ShadowWandererModel extends GeoModel<ShadowWandererEntity> {
	@Override
	public ResourceLocation getAnimationResource(ShadowWandererEntity entity) {
		return new ResourceLocation("cl", "animations/shadowwanderer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ShadowWandererEntity entity) {
		return new ResourceLocation("cl", "geo/shadowwanderer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShadowWandererEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
