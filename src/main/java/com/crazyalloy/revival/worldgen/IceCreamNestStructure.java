package com.crazyalloy.revival.worldgen;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.registry.ModFeatures;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

/**
 * The Ice Cream Nest: one template (data/crazyalloy_revival/structure/ice_cream_nest.nbt, 37 x 14 x 37) centred on the
 * chunk. A jigsaw structure samples the ground height at one corner only, which on the Ice Cream Plains' hills left the
 * nest floating on a shelf; this one samples the centre and four points around the footprint, skips sites that are
 * too rugged or under melted chocolate and stands the mound on the lowest of them (the beard_box adaptation clears the higher ground inside).
 */
public class IceCreamNestStructure extends Structure {
    public static final MapCodec<IceCreamNestStructure> CODEC = simpleCodec(IceCreamNestStructure::new);
    private static final int HALF = 18;
    /** Largest height difference across the footprint that still gets a nest. */
    private static final int MAX_SLOPE = 14;

    public IceCreamNestStructure(Structure.StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        int cx = context.chunkPos().getMiddleBlockX(), cz = context.chunkPos().getMiddleBlockZ();
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int[] o : new int[][] {{0, 0}, {-14, -14}, {14, -14}, {-14, 14}, {14, 14}}) {
            int h = context.chunkGenerator().getFirstOccupiedHeight(cx + o[0], cz + o[1], Heightmap.Types.WORLD_SURFACE_WG,
                    context.heightAccessor(), context.randomState());
            int floor = context.chunkGenerator().getFirstOccupiedHeight(cx + o[0], cz + o[1], Heightmap.Types.OCEAN_FLOOR_WG,
                    context.heightAccessor(), context.randomState());
            if (h - floor > 1) {
                return Optional.empty(); // under melted chocolate: no nests in the lakes and seas
            }
            min = Math.min(min, h);
            max = Math.max(max, h);
        }
        if (max - min > MAX_SLOPE || min <= context.heightAccessor().getMinY() + 8) {
            return Optional.empty();
        }
        Rotation rotation = Rotation.getRandom(context.random());
        BlockPos centreOffset = new BlockPos(HALF, 0, HALF).rotate(rotation);
        BlockPos origin = new BlockPos(cx - centreOffset.getX(), min - 1, cz - centreOffset.getZ());
        StructurePoolElement element = StructurePoolElement.single(CrazyAlloyRevival.id("ice_cream_nest").toString())
                .apply(StructureTemplatePool.Projection.RIGID);
        return Optional.of(new Structure.GenerationStub(origin, builder -> {
            BoundingBox box = element.getBoundingBox(context.structureTemplateManager(), origin, rotation);
            builder.addPiece(new PoolElementStructurePiece(context.structureTemplateManager(), element, origin, 1, rotation, box,
                    LiquidSettings.IGNORE_WATERLOGGING));
        }));
    }

    @Override
    public StructureType<?> type() {
        return ModFeatures.ICE_CREAM_NEST.get();
    }
}
