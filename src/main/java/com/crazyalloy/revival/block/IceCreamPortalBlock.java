package com.crazyalloy.revival.block;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.worldgen.IceCreamPortals;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

/**
 * The swirling chocolate surface inside a lit Ice Cream Dimension portal (stage 6). Works like a Nether portal: stand
 * in it to travel between the Overworld and the Ice Cream Dimension. It vanishes if its chocolate frame is broken.
 */
public class IceCreamPortalBlock extends Block implements Portal {
    public static final MapCodec<IceCreamPortalBlock> CODEC = simpleCodec(IceCreamPortalBlock::new);
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    private static final Map<Direction.Axis, VoxelShape> SHAPES = Shapes.rotateHorizontalAxis(Block.column(4.0, 16.0, 0.0, 16.0));
    private static final DustParticleOptions CHOCOLATE = new DustParticleOptions(0x7B4A2E, 1.0F);
    private static final DustParticleOptions STRAWBERRY = new DustParticleOptions(0xF4A6C4, 1.0F);
    private static final DustParticleOptions MINT = new DustParticleOptions(0x9FE7C8, 1.0F);

    public IceCreamPortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(AXIS));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction,
                                     BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        Direction.Axis updateAxis = direction.getAxis();
        Direction.Axis axis = state.getValue(AXIS);
        boolean wrongAxis = axis != updateAxis && updateAxis.isHorizontal();
        return !wrongAxis && !neighbourState.is(this) && !IceCreamPortals.stillFramed(level, pos, axis)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, level, ticks, pos, direction, neighbourPos, neighbourState, random);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effects, boolean precise) {
        if (RevivalConfig.ICE_CREAM_PORTAL_ENABLED.get() && entity.canUsePortal(false)) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    public int getPortalTransitionTime(ServerLevel level, Entity entity) {
        return entity instanceof Player player
                ? Math.max(0, level.getGameRules().get(player.getAbilities().invulnerable
                        ? GameRules.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY : GameRules.PLAYERS_NETHER_PORTAL_DEFAULT_DELAY))
                : 0;
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
        return RevivalConfig.ICE_CREAM_PORTAL_ENABLED.get() ? IceCreamPortals.destination(level, entity, pos) : null;
    }

    @Override
    public Portal.Transition getLocalTransition() {
        return Portal.Transition.CONFUSION;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,
                    0.4F, random.nextFloat() * 0.3F + 1.2F, false);
        }
        for (int i = 0; i < 3; i++) {
            DustParticleOptions dust = switch (random.nextInt(4)) {
                case 0 -> STRAWBERRY;
                case 1 -> MINT;
                default -> CHOCOLATE;
            };
            boolean xAxis = state.getValue(AXIS) == Direction.Axis.X;
            double x = pos.getX() + (xAxis ? random.nextDouble() : 0.5 + (random.nextDouble() - 0.5) * 0.6);
            double z = pos.getZ() + (xAxis ? 0.5 + (random.nextDouble() - 0.5) * 0.6 : random.nextDouble());
            level.addParticle(dust, x, pos.getY() + random.nextDouble(), z, 0.0, 0.03, 0.0);
        }
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return ItemStack.EMPTY;
    }
}
