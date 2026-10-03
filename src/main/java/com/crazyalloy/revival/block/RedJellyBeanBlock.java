package com.crazyalloy.revival.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Red jelly lines the bottom of Jelly Bean Fields lakes. Starting to break it gives Hunger III for 15 seconds. */
public class RedJellyBeanBlock extends JellyBeanBlock {
    public RedJellyBeanBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (!level.isClientSide() && !player.isCreative()) {
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, 2));
        }
        super.attack(state, level, pos, player);
    }
}
