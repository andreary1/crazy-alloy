package com.crazyalloy.revival.worldgen;

import com.crazyalloy.revival.block.IceCreamPortalBlock;
import com.crazyalloy.revival.registry.ModBlocks;
import com.crazyalloy.revival.registry.ModPoiTypes;
import java.util.Comparator;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.BlockUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Ice Cream Dimension portals (stage 6): a vertical frame of Chocolate Ice Cream Blocks, like a Nether portal frame,
 * lit with the Ice Cream Amulet. The opening is 2 to 21 blocks wide and 3 to 21 blocks tall (the smallest frame is
 * 4 x 5 with the corners optional). Travel is 1:1 between the Overworld and the Ice Cream Dimension; if no portal is
 * found within 64 blocks of the arrival point, a small one with a standing platform is built there.
 */
public final class IceCreamPortals {
    public static final int MIN_WIDTH = 2, MIN_HEIGHT = 3, MAX_SIZE = 21;
    private static final int SEARCH_RADIUS = 64;

    private IceCreamPortals() {}

    /** The opening of a frame: bottom-left inside corner, width along {@code axis} and height. */
    public record Shape(BlockPos bottomLeft, Direction.Axis axis, int width, int height) {}

    public static boolean isFrame(BlockState state) {
        return state.is(ModBlocks.CHOCOLATE_ICE_CREAM_BLOCK.get());
    }

    private static boolean isEmpty(BlockState state) {
        return state.isAir() || state.is(Blocks.FIRE) || state.is(ModBlocks.ICE_CREAM_PORTAL.get());
    }

    /** Lights the frame around {@code inside} (tries both orientations). True if a portal was made. */
    public static boolean tryLight(LevelAccessor level, BlockPos inside) {
        for (Direction.Axis axis : new Direction.Axis[] {Direction.Axis.X, Direction.Axis.Z}) {
            Optional<Shape> shape = find(level, inside, axis);
            if (shape.isPresent() && !hasPortal(level, shape.get())) {
                fill(level, shape.get());
                return true;
            }
        }
        return false;
    }

    private static boolean hasPortal(BlockGetter level, Shape shape) {
        return level.getBlockState(shape.bottomLeft()).is(ModBlocks.ICE_CREAM_PORTAL.get());
    }

    /** Finds a complete, empty (or already lit) frame around {@code inside} on the given axis. */
    public static Optional<Shape> find(BlockGetter level, BlockPos inside, Direction.Axis axis) {
        if (!isEmpty(level.getBlockState(inside))) {
            return Optional.empty();
        }
        Direction right = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        Direction left = right.getOpposite();
        // Down to the floor of the opening.
        BlockPos.MutableBlockPos pos = inside.mutable();
        int steps = 0;
        while (isEmpty(level.getBlockState(pos.below())) && steps++ < MAX_SIZE) {
            pos.move(Direction.DOWN);
        }
        if (!isFrame(level.getBlockState(pos.below()))) {
            return Optional.empty();
        }
        // Left to the side of the frame.
        steps = 0;
        while (isEmpty(level.getBlockState(pos.relative(left))) && isFrame(level.getBlockState(pos.relative(left).below())) && steps++ < MAX_SIZE) {
            pos.move(left);
        }
        if (!isFrame(level.getBlockState(pos.relative(left)))) {
            return Optional.empty();
        }
        BlockPos bottomLeft = pos.immutable();
        // Width: floor all the way, then a frame column.
        int width = 0;
        while (width <= MAX_SIZE && isEmpty(level.getBlockState(bottomLeft.relative(right, width)))
                && isFrame(level.getBlockState(bottomLeft.relative(right, width).below()))) {
            width++;
        }
        if (width < MIN_WIDTH || width > MAX_SIZE || !isFrame(level.getBlockState(bottomLeft.relative(right, width)))) {
            return Optional.empty();
        }
        // Height: rows of empty space with a frame block at both ends, closed by a full frame row.
        int height = 0;
        while (height <= MAX_SIZE) {
            BlockPos row = bottomLeft.above(height);
            boolean allEmpty = true, allFrame = true;
            for (int i = 0; i < width; i++) {
                BlockState s = level.getBlockState(row.relative(right, i));
                allEmpty &= isEmpty(s);
                allFrame &= isFrame(s);
            }
            if (allFrame && height > 0) {
                break; // the top of the frame; like the Nether portal, corners are optional
            }
            if (!isFrame(level.getBlockState(row.relative(left))) || !isFrame(level.getBlockState(row.relative(right, width)))) {
                return Optional.empty();
            }
            if (!allEmpty) {
                return Optional.empty();
            }
            height++;
        }
        if (height < MIN_HEIGHT || height > MAX_SIZE) {
            return Optional.empty();
        }
        return Optional.of(new Shape(bottomLeft, axis, width, height));
    }

    public static void fill(LevelAccessor level, Shape shape) {
        BlockState portal = ModBlocks.ICE_CREAM_PORTAL.get().defaultBlockState().setValue(IceCreamPortalBlock.AXIS, shape.axis());
        Direction right = Direction.get(Direction.AxisDirection.POSITIVE, shape.axis());
        for (int y = 0; y < shape.height(); y++) {
            for (int x = 0; x < shape.width(); x++) {
                level.setBlock(shape.bottomLeft().relative(right, x).above(y), portal, 18);
            }
        }
    }

    /** True while the portal block at {@code pos} still sits in a complete frame. */
    public static boolean stillFramed(BlockGetter level, BlockPos pos, Direction.Axis axis) {
        return find(level, pos, axis).isPresent();
    }

    // --- travel -------------------------------------------------------------------------------

    public static @Nullable TeleportTransition destination(ServerLevel current, Entity entity, BlockPos entryPos) {
        boolean leaving = current.dimension() == ModWorldgen.ICE_CREAM_DIMENSION;
        ServerLevel target = current.getServer().getLevel(leaving ? Level.OVERWORLD : ModWorldgen.ICE_CREAM_DIMENSION);
        if (target == null) {
            return null;
        }
        WorldBorder border = target.getWorldBorder();
        BlockPos approx = border.clampToBounds(entity.getX(), entity.getY(), entity.getZ());
        BlockUtil.FoundRectangle exit;
        TeleportTransition.PostTeleportTransition post;
        Optional<BlockPos> found = findPortal(target, approx);
        if (found.isPresent()) {
            BlockPos pos = found.get();
            BlockState state = target.getBlockState(pos);
            exit = BlockUtil.getLargestRectangleAround(pos, state.getValue(IceCreamPortalBlock.AXIS), MAX_SIZE, Direction.Axis.Y, MAX_SIZE,
                    p -> target.getBlockState(p) == state);
            post = TeleportTransition.PLAY_PORTAL_SOUND.then(e -> e.placePortalTicket(pos));
        } else {
            Direction.Axis axis = entity.level().getBlockState(entryPos).getOptionalValue(IceCreamPortalBlock.AXIS).orElse(Direction.Axis.X);
            exit = build(target, approx, axis);
            post = TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET);
        }
        return transition(entity, entryPos, target, exit, post);
    }

    private static Optional<BlockPos> findPortal(ServerLevel level, BlockPos around) {
        PoiManager poi = level.getPoiManager();
        poi.ensureLoadedAndValid(level, around, SEARCH_RADIUS);
        return poi.getInSquare(type -> type.is(ModPoiTypes.ICE_CREAM_PORTAL.getKey()), around, SEARCH_RADIUS, PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos)
                .filter(level.getWorldBorder()::isWithinBounds)
                .filter(p -> level.getBlockState(p).hasProperty(IceCreamPortalBlock.AXIS))
                .min(Comparator.<BlockPos>comparingDouble(p -> p.distSqr(around)).thenComparingInt(Vec3i::getY));
    }

    /**
     * Builds a 4 x 5 chocolate ice cream frame (2 x 3 opening) standing on a 4 x 3 platform at the surface of the
     * arrival column, clearing the space around it. Returns the lit opening.
     */
    public static BlockUtil.FoundRectangle build(ServerLevel level, BlockPos near, Direction.Axis axis) {
        int x = near.getX(), z = near.getZ();
        int top = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        int maxY = level.getMinY() + level.getLogicalHeight() - 7;
        int y = Math.max(level.getMinY() + 2, Math.min(top, maxY));
        if (top <= level.getMinY() + 1) {
            y = Math.max(level.getMinY() + 2, Math.min(70, maxY)); // void: float the platform at a sensible height
        }
        Direction right = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        Direction front = right.getClockWise();
        BlockPos bottomLeft = new BlockPos(x, y, z);
        BlockState frame = ModBlocks.CHOCOLATE_ICE_CREAM_BLOCK.get().defaultBlockState();
        for (int i = -1; i <= 2; i++) {
            for (int f = -1; f <= 1; f++) {
                BlockPos base = bottomLeft.relative(right, i).relative(front, f);
                level.setBlockAndUpdate(base.below(), frame);
                for (int h = 0; h <= 3; h++) {
                    if (f != 0) {
                        level.setBlockAndUpdate(base.above(h), Blocks.AIR.defaultBlockState());
                    }
                }
            }
            for (int h = -1; h <= 3; h++) {
                boolean edge = i == -1 || i == 2 || h == -1 || h == 3;
                level.setBlockAndUpdate(bottomLeft.relative(right, i).above(h), edge ? frame : Blocks.AIR.defaultBlockState());
            }
        }
        Shape shape = new Shape(bottomLeft, axis, 2, 3);
        fill(level, shape);
        return new BlockUtil.FoundRectangle(bottomLeft, 2, 3);
    }

    private static TeleportTransition transition(Entity entity, BlockPos entryPos, ServerLevel target, BlockUtil.FoundRectangle exit,
                                                 TeleportTransition.PostTeleportTransition post) {
        BlockState entry = entity.level().getBlockState(entryPos);
        Direction.Axis entryAxis;
        Vec3 offset;
        if (entry.hasProperty(IceCreamPortalBlock.AXIS)) {
            entryAxis = entry.getValue(IceCreamPortalBlock.AXIS);
            BlockUtil.FoundRectangle area = BlockUtil.getLargestRectangleAround(entryPos, entryAxis, MAX_SIZE, Direction.Axis.Y, MAX_SIZE,
                    p -> entity.level().getBlockState(p) == entry);
            offset = entity.getRelativePortalPosition(entryAxis, area);
        } else {
            entryAxis = Direction.Axis.X;
            offset = new Vec3(0.5, 0.0, 0.0);
        }
        BlockPos bottomLeft = exit.minCorner;
        Direction.Axis exitAxis = target.getBlockState(bottomLeft).getOptionalValue(IceCreamPortalBlock.AXIS).orElse(Direction.Axis.X);
        EntityDimensions dims = entity.getDimensions(entity.getPose());
        double right = dims.width() / 2.0 + (exit.axis1Size - dims.width()) * offset.x();
        double up = (exit.axis2Size - dims.height()) * offset.y();
        double forward = 0.5 + offset.z();
        boolean xAligned = exitAxis == Direction.Axis.X;
        Vec3 pos = new Vec3(bottomLeft.getX() + (xAligned ? right : forward), bottomLeft.getY() + up, bottomLeft.getZ() + (xAligned ? forward : right));
        Vec3 free = PortalShape.findCollisionFreePosition(pos, target, entity, dims);
        int rotation = entryAxis == exitAxis ? 0 : 90;
        return new TeleportTransition(target, free, Vec3.ZERO, rotation, 0.0F, Relative.union(Relative.DELTA, Relative.ROTATION), post);
    }
}
