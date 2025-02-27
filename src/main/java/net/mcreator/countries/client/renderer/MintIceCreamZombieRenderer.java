
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.MintIceCreamZombieEntity;
import net.mcreator.countries.client.model.Modelicecreamzombie;

public class MintIceCreamZombieRenderer extends MobRenderer<MintIceCreamZombieEntity, Modelicecreamzombie<MintIceCreamZombieEntity>> {
	public MintIceCreamZombieRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelicecreamzombie<MintIceCreamZombieEntity>(context.bakeLayer(Modelicecreamzombie.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(MintIceCreamZombieEntity entity) {
		return new ResourceLocation("cl:textures/entities/mintzombie.png");
	}
}
