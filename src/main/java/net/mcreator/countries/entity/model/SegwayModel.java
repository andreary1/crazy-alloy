package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.SegwayEntity;

public class SegwayModel extends GeoModel<SegwayEntity> {
	@Override
	public ResourceLocation getAnimationResource(SegwayEntity entity) {
		return new ResourceLocation("cl", "animations/segway.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SegwayEntity entity) {
		return new ResourceLocation("cl", "geo/segway.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SegwayEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
