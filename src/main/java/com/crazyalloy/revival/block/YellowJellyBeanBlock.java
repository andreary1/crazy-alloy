package com.crazyalloy.revival.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Sticky yellow jelly: walking on it gives Mining Fatigue III for 3 seconds (renewed while you stay on it). */
public class YellowJellyBeanBlock extends JellyBeanBlock {
    public YellowJellyBeanBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide() && entity instanceof Player player && !player.isSpectator()) {
            player.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 60, 2));
        }
        super.stepOn(level, pos, state, entity);
    }
}
