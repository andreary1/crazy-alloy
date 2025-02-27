
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.IceCreamZombieEntity;
import net.mcreator.countries.client.model.Modelicecreamzombie;

public class IceCreamZombieRenderer extends MobRenderer<IceCreamZombieEntity, Modelicecreamzombie<IceCreamZombieEntity>> {
	public IceCreamZombieRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelicecreamzombie<IceCreamZombieEntity>(context.bakeLayer(Modelicecreamzombie.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(IceCreamZombieEntity entity) {
		return new ResourceLocation("cl:textures/entities/mintzombie.png");
	}
}
