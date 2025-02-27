
package net.mcreator.countries.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.countries.entity.VanillaIceCreamZombieEntity;
import net.mcreator.countries.client.model.Modelicecreamzombie;

public class VanillaIceCreamZombieRenderer extends MobRenderer<VanillaIceCreamZombieEntity, Modelicecreamzombie<VanillaIceCreamZombieEntity>> {
	public VanillaIceCreamZombieRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelicecreamzombie<VanillaIceCreamZombieEntity>(context.bakeLayer(Modelicecreamzombie.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(VanillaIceCreamZombieEntity entity) {
		return new ResourceLocation("cl:textures/entities/vanillaicecreamzombie.png");
	}
}
