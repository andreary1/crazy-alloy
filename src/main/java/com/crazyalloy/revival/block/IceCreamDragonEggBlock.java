package com.crazyalloy.revival.block;

import com.crazyalloy.revival.entity.IceCreamDragon;
import com.crazyalloy.revival.registry.ModEntities;
import com.crazyalloy.revival.registry.ModSounds;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * The egg on top of the Ice Cream Nest (stage 6). Right-click it to wake the Ice Cream Dragon: the egg trembles for
 * {@link #SUMMON_TICKS} ticks (about three seconds), then bursts with particles and a roar, the dragon appears in its
 * place and the egg is gone. The summon runs on the server's scheduled ticks, so it works the same in multiplayer.
 */
public class IceCreamDragonEggBlock extends Block {
    public static final MapCodec<IceCreamDragonEggBlock> CODEC = simpleCodec(IceCreamDragonEggBlock::new);
    public static final BooleanProperty SUMMONING = BooleanProperty.create("summoning");
    public static final int SUMMON_TICKS = 60;
    private static final VoxelShape SHAPE = Block.column(14.0, 0.0, 16.0);
    private static final DustParticleOptions[] FLAVOURS = {new DustParticleOptions(0xFFF4DE, 1.2F), new DustParticleOptions(0x7B4A2E, 1.2F),
            new DustParticleOptions(0xF4A6C4, 1.2F), new DustParticleOptions(0x9FE7C8, 1.2F)};

    public IceCreamDragonEggBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SUMMONING, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SUMMONING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (state.getValue(SUMMONING)) {
            return InteractionResult.CONSUME;
        }
        if (!level.isClientSide()) {
            startSummon(level, pos, state);
        }
        return InteractionResult.SUCCESS;
    }

    /** Starts the countdown; the dragon appears {@link #SUMMON_TICKS} ticks later. */
    public static void startSummon(Level level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state.setValue(SUMMONING, true), 3);
        level.scheduleTick(pos, state.getBlock(), SUMMON_TICKS);
        level.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 1.0F, 0.7F);
        level.playSound(null, pos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.HOSTILE, 1.0F, 0.6F);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.getValue(SUMMONING)) {
            return;
        }
        level.removeBlock(pos, false);
        double x = pos.getX() + 0.5, y = pos.getY(), z = pos.getZ() + 0.5;
        for (DustParticleOptions dust : FLAVOURS) {
            level.sendParticles(dust, x, y + 1.0, z, 40, 1.5, 1.5, 1.5, 0.0);
        }
        level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, x, y + 0.5, z, 1, 0.0, 0.0, 0.0, 0.0);
        level.sendParticles(ParticleTypes.END_ROD, x, y + 1.0, z, 30, 0.8, 1.2, 0.8, 0.08);
        level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 1.0F, 1.2F);
        IceCreamDragon dragon = ModEntities.ICE_CREAM_DRAGON.get().create(level, EntitySpawnReason.TRIGGERED);
        if (dragon != null) {
            dragon.snapTo(x, y, z, random.nextFloat() * 360.0F, 0.0F);
            dragon.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), EntitySpawnReason.TRIGGERED, null);
            level.addFreshEntity(dragon);
            dragon.playSound(ModSounds.ICE_CREAM_DRAGON_ROAR.get(), 3.0F, 1.0F);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(SUMMONING)) {
            if (random.nextInt(4) == 0) {
                level.addParticle(FLAVOURS[random.nextInt(4)], pos.getX() + random.nextDouble(), pos.getY() + 1.05, pos.getZ() + random.nextDouble(), 0, 0, 0);
            }
            return;
        }
        for (int i = 0; i < 6; i++) {
            double a = random.nextDouble() * Math.PI * 2.0;
            double r = 0.6 + random.nextDouble() * 1.2;
            level.addParticle(FLAVOURS[random.nextInt(4)], pos.getX() + 0.5 + Math.cos(a) * r, pos.getY() + random.nextDouble() * 1.6,
                    pos.getZ() + 0.5 + Math.sin(a) * r, 0.0, 0.0, 0.0);
        }
        level.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5,
                (random.nextDouble() - 0.5) * 1.5, random.nextDouble(), (random.nextDouble() - 0.5) * 1.5);
    }
}
