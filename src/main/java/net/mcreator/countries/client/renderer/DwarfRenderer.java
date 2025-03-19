
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.DwarfEntity;
import net.mcreator.countries.client.model.Modeldwarf;

import com.mojang.blaze3d.vertex.PoseStack;

public class DwarfRenderer extends MobRenderer<DwarfEntity, Modeldwarf<DwarfEntity>> {
	public DwarfRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeldwarf<DwarfEntity>(context.bakeLayer(Modeldwarf.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected void scale(DwarfEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.9f, 0.9f, 0.9f);
	}

	@Override
	public ResourceLocation getTextureLocation(DwarfEntity entity) {
		return new ResourceLocation("cl:textures/entities/dwarf.png");
	}
}
