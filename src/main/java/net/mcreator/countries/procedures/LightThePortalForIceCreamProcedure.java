package net.mcreator.countries.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.mcreator.countries.init.ClModItems;
import net.mcreator.countries.init.ClModBlocks;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class LightThePortalForIceCreamProcedure {
	@SubscribeEvent
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ClModItems.ICE_CREAM_AMULET.get()) {
			world.setBlock(BlockPos.containing(x, y, z - 1), ClModBlocks.ICE_CREAM_PORTAL_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x, y, z - 2), ClModBlocks.ICE_CREAM_PORTAL_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + 1, y, z - 1), ClModBlocks.ICE_CREAM_PORTAL_BLOCK.get().defaultBlockState(), 3);
			world.setBlock(BlockPos.containing(x + 1, y, z - 2), ClModBlocks.ICE_CREAM_PORTAL_BLOCK.get().defaultBlockState(), 3);
		}
	}
}
