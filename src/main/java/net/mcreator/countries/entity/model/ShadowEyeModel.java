package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.ShadowEyeEntity;

public class ShadowEyeModel extends GeoModel<ShadowEyeEntity> {
	@Override
	public ResourceLocation getAnimationResource(ShadowEyeEntity entity) {
		return new ResourceLocation("cl", "animations/shadoweye.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ShadowEyeEntity entity) {
		return new ResourceLocation("cl", "geo/shadoweye.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShadowEyeEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
