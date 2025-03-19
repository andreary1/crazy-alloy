package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.EntEntity;

public class EntModel extends GeoModel<EntEntity> {
	@Override
	public ResourceLocation getAnimationResource(EntEntity entity) {
		return new ResourceLocation("cl", "animations/ent.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(EntEntity entity) {
		return new ResourceLocation("cl", "geo/ent.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EntEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
