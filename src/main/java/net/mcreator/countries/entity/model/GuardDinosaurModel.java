package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.GuardDinosaurEntity;

public class GuardDinosaurModel extends GeoModel<GuardDinosaurEntity> {
	@Override
	public ResourceLocation getAnimationResource(GuardDinosaurEntity entity) {
		return new ResourceLocation("cl", "animations/dinosaur_guard.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GuardDinosaurEntity entity) {
		return new ResourceLocation("cl", "geo/dinosaur_guard.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GuardDinosaurEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
