package com.crazyalloy.revival.item;

import com.crazyalloy.revival.entity.projectile.JellySnakeShot;
import com.crazyalloy.revival.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Jelly Bazooka: fires a Dead Jelly Snake from your inventory (5 damage, strong knockback).
 * Revival choice: one-second cooldown and 250 uses, so it does not replace a bow.
 */
public class JellyBazookaItem extends Item {
    public static final int COOLDOWN_TICKS = 20;

    public JellyBazookaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack bazooka = player.getItemInHand(hand);
        ItemStack ammo = findAmmo(player);
        if (ammo.isEmpty() && !player.hasInfiniteMaterials()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.DISPENSER_FAIL, SoundSource.PLAYERS, 0.6F, 1.4F);
            return InteractionResult.FAIL;
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SLIME_JUMP, SoundSource.PLAYERS, 1.0F, 0.6F);
        if (level instanceof ServerLevel serverLevel) {
            ItemStack shot = new ItemStack(ModItems.DEAD_JELLY_SNAKE.get());
            Projectile.spawnProjectileFromRotation(JellySnakeShot::new, serverLevel, shot, player, 0.0F, 2.0F, 0.5F);
            bazooka.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }
        if (!player.hasInfiniteMaterials()) {
            ammo.shrink(1);
        }
        player.getCooldowns().addCooldown(bazooka, COOLDOWN_TICKS);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

    private static ItemStack findAmmo(Player player) {
        if (player.getOffhandItem().is(ModItems.DEAD_JELLY_SNAKE.get())) {
            return player.getOffhandItem();
        }
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(ModItems.DEAD_JELLY_SNAKE.get())) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
