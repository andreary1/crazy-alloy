package com.crazyalloy.revival.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Jelly Bean Fields ground. Every jelly bean block slows walking (speed factor 0.8) and makes jumps bouncy.
 * <p>Revival choice: the original multiplies jumps by 3, which launches players about 8 blocks up; here the
 * jump factor is 1.8 (about 3 blocks) and landing on jelly takes half the usual fall damage. Heavy Boots
 * cancel the bounce.
 */
public class JellyBeanBlock extends Block {
    public static final float SPEED_FACTOR = 0.8F;
    public static final float JUMP_FACTOR = 1.8F;

    public JellyBeanBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        entity.causeFallDamage(fallDistance, 0.5F, level.damageSources().fall());
    }
}
