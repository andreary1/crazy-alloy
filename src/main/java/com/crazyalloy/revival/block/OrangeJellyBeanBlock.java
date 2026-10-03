package com.crazyalloy.revival.block;

import com.crazyalloy.revival.config.RevivalConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Orange jelly patches explode when a player walks on them.
 * <p>Revival choice: the original explodes instantly with power 4. Here a player that is not sneaking lights
 * a one-second fuse (hiss and smoke), so careful players can get away; the power is configurable
 * (default 2, 0 turns explosions off) and follows the usual block explosion rules.
 */
public class OrangeJellyBeanBlock extends JellyBeanBlock {
    public static final BooleanProperty PRIMED = BooleanProperty.create("primed");
    private static final int FUSE_TICKS = 20;

    public OrangeJellyBeanBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PRIMED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PRIMED);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide() && entity instanceof Player player && !player.isCreative() && !player.isSpectator()
                && !player.isSteppingCarefully() && !state.getValue(PRIMED) && RevivalConfig.ORANGE_JELLY_EXPLOSION_POWER.get() > 0) {
            level.setBlock(pos, state.setValue(PRIMED, true), Block.UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 0.8F, 1.6F);
            level.scheduleTick(pos, this, FUSE_TICKS);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(PRIMED)) {
            return;
        }
        float power = RevivalConfig.ORANGE_JELLY_EXPLOSION_POWER.get().floatValue();
        level.removeBlock(pos, false);
        if (power > 0) {
            level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, power, Level.ExplosionInteraction.BLOCK);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(PRIMED)) {
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + random.nextDouble(), pos.getY() + 1.05, pos.getZ() + random.nextDouble(), 0.0, 0.05, 0.0);
        }
    }
}
