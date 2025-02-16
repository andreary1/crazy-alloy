package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.CottonCandyTornadoEntity;

public class CottonCandyTornadoModel extends GeoModel<CottonCandyTornadoEntity> {
	@Override
	public ResourceLocation getAnimationResource(CottonCandyTornadoEntity entity) {
		return new ResourceLocation("cl", "animations/cottoncandytornado.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(CottonCandyTornadoEntity entity) {
		return new ResourceLocation("cl", "geo/cottoncandytornado.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CottonCandyTornadoEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

}
