package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.JellyBunnyEntity;

public class JellyBunnyModel extends GeoModel<JellyBunnyEntity> {
	@Override
	public ResourceLocation getAnimationResource(JellyBunnyEntity entity) {
		return new ResourceLocation("cl", "animations/jellybunny.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(JellyBunnyEntity entity) {
		return new ResourceLocation("cl", "geo/jellybunny.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(JellyBunnyEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(JellyBunnyEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
