
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.WizardEntity;
import net.mcreator.countries.client.model.Modelwizard;

public class WizardRenderer extends MobRenderer<WizardEntity, Modelwizard<WizardEntity>> {
	public WizardRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwizard<WizardEntity>(context.bakeLayer(Modelwizard.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(WizardEntity entity) {
		return new ResourceLocation("cl:textures/entities/wizard.png");
	}
}
