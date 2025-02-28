package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.ImpostorCakeEntity;

public class ImpostorCakeModel extends GeoModel<ImpostorCakeEntity> {
	@Override
	public ResourceLocation getAnimationResource(ImpostorCakeEntity entity) {
		return new ResourceLocation("cl", "animations/impostorcake.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ImpostorCakeEntity entity) {
		return new ResourceLocation("cl", "geo/impostorcake.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ImpostorCakeEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
