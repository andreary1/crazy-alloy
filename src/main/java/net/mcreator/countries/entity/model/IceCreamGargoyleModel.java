package net.mcreator.countries.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.entity.IceCreamGargoyleEntity;

public class IceCreamGargoyleModel extends GeoModel<IceCreamGargoyleEntity> {
	@Override
	public ResourceLocation getAnimationResource(IceCreamGargoyleEntity entity) {
		return new ResourceLocation("cl", "animations/icecreamgargoyle.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(IceCreamGargoyleEntity entity) {
		return new ResourceLocation("cl", "geo/icecreamgargoyle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IceCreamGargoyleEntity entity) {
		return new ResourceLocation("cl", "textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(IceCreamGargoyleEntity animatable, long instanceId, AnimationState animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("head");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
