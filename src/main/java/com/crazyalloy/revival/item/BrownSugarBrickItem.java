package com.crazyalloy.revival.item;

import com.crazyalloy.revival.entity.projectile.BrownSugarBrickEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Brown Sugar Brick: dropped by Brown Sugar Rhinos; crafts the Brown Sugar Sword and can be thrown. */
public class BrownSugarBrickItem extends Item {
    public BrownSugarBrickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.6F, 0.5F);
        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(BrownSugarBrickEntity::new, serverLevel, stack, player, 0.0F, 1.2F, 1.5F);
        }
        player.getCooldowns().addCooldown(stack, 10);
        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);
        return InteractionResult.SUCCESS;
    }
}
