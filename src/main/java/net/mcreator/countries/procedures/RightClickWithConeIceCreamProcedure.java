package net.mcreator.countries.procedures;

import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.mcreator.countries.init.ClModItems;
import net.mcreator.countries.init.ClModEntities;
import net.mcreator.countries.entity.VanillaIceCreamGuyEntity;
import net.mcreator.countries.entity.StrawberryIceCreamGuyEntity;
import net.mcreator.countries.entity.MintIceCreamGuyEntity;
import net.mcreator.countries.entity.ChocolateIceCreamGuyEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class RightClickWithConeIceCreamProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getTarget(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("minecraft:icecreamguys")))
				&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ClModItems.EMPTY_ICE_CREAM_CONE.get() && entity instanceof StrawberryIceCreamGuyEntity) {
			if (sourceentity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(ClModItems.STRAWBERRY_ICE_CREAM.get()).copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			if (sourceentity instanceof Player _player) {
				ItemStack _stktoremove = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ANGRY_VILLAGER, x, y, z, 5, 3, 3, 3, 1);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = ClModEntities.ANGRY_ICE_CREAM_CONE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setDeltaMovement(0, 0, 0);
				}
			}
			if (!entity.level().isClientSide())
				entity.discard();
		}
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("minecraft:icecreamguys")))
				&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ClModItems.EMPTY_ICE_CREAM_CONE.get() && entity instanceof VanillaIceCreamGuyEntity) {
			if (sourceentity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(ClModItems.VANILLA_ICE_CREAM.get()).copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			if (sourceentity instanceof Player _player) {
				ItemStack _stktoremove = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ANGRY_VILLAGER, x, y, z, 5, 3, 3, 3, 1);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = ClModEntities.ANGRY_ICE_CREAM_CONE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setDeltaMovement(0, 0, 0);
				}
			}
			if (!entity.level().isClientSide())
				entity.discard();
		}
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("minecraft:icecreamguys")))
				&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ClModItems.EMPTY_ICE_CREAM_CONE.get() && entity instanceof ChocolateIceCreamGuyEntity) {
			if (sourceentity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(ClModItems.CHOCOLATE_ICE_CREAM.get()).copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			if (sourceentity instanceof Player _player) {
				ItemStack _stktoremove = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ANGRY_VILLAGER, x, y, z, 5, 3, 3, 3, 1);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = ClModEntities.ANGRY_ICE_CREAM_CONE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setDeltaMovement(0, 0, 0);
				}
			}
			if (!entity.level().isClientSide())
				entity.discard();
		}
		if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("minecraft:icecreamguys")))
				&& (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ClModItems.EMPTY_ICE_CREAM_CONE.get() && entity instanceof MintIceCreamGuyEntity) {
			if (sourceentity instanceof Player _player) {
				ItemStack _setstack = new ItemStack(ClModItems.MINT_ICE_CREAM.get()).copy();
				_setstack.setCount(1);
				ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
			}
			if (sourceentity instanceof Player _player) {
				ItemStack _stktoremove = (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
				_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ANGRY_VILLAGER, x, y, z, 5, 3, 3, 3, 1);
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = ClModEntities.ANGRY_ICE_CREAM_CONE.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
				if (entityToSpawn != null) {
					entityToSpawn.setDeltaMovement(0, 0, 0);
				}
			}
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
