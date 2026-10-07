package com.crazyalloy.revival.worldgen;

import com.crazyalloy.revival.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * Three-flavour ice cream pinnacle (stage 6): a narrow natural tower on the Ice Cream Plains with a white vanilla base,
 * a pink strawberry band and a brown chocolate top, tapering in steps like a giant three-scoop ice cream.
 * Heights vary from about 12 to 34 blocks. The base is sunk a few blocks into the ground so it never floats.
 */
public class IceCreamPinnacleFeature extends Feature<NoneFeatureConfiguration> {
    public IceCreamPinnacleFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        BlockState ground = level.getBlockState(origin.below());
        if (!isIceCream(ground)) {
            return false;
        }
        int height = 12 + random.nextInt(23);
        float baseRadius = 2.2F + random.nextFloat() * 1.8F + height / 20.0F;
        int steps = 3 + random.nextInt(3);
        BlockState vanilla = ModBlocks.VANILLA_ICE_CREAM_BLOCK.get().defaultBlockState();
        BlockState strawberry = ModBlocks.STRAWBERRY_ICE_CREAM_BLOCK.get().defaultBlockState();
        BlockState chocolate = ModBlocks.CHOCOLATE_ICE_CREAM_BLOCK.get().defaultBlockState();
        int vanillaTop = Math.round(height * (0.42F + random.nextFloat() * 0.1F));
        int strawberryTop = Math.round(height * (0.72F + random.nextFloat() * 0.08F));
        // Slight lean: the centre of each step drifts a little to one side.
        double leanX = (random.nextDouble() - 0.5) * 0.08, leanZ = (random.nextDouble() - 0.5) * 0.08;
        for (int y = -4; y < height; y++) {
            float t = Math.max(0, y) / (float) height;
            int step = Math.min(steps - 1, (int) (t * steps));
            float radius = baseRadius * (1.0F - step / (float) steps * 0.75F);
            if (y >= height - 2) {
                radius = Math.max(0.6F, radius * 0.6F);
            }
            BlockState state = y < vanillaTop ? vanilla : y < strawberryTop ? strawberry : chocolate;
            double cx = origin.getX() + 0.5 + leanX * Math.max(0, y), cz = origin.getZ() + 0.5 + leanZ * Math.max(0, y);
            int r = (int) Math.ceil(radius);
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    BlockPos p = new BlockPos((int) Math.floor(cx) + dx, origin.getY() + y, (int) Math.floor(cz) + dz);
                    double ddx = p.getX() + 0.5 - cx, ddz = p.getZ() + 0.5 - cz;
                    if (ddx * ddx + ddz * ddz <= radius * radius + 0.3) {
                        if (y < 0 && !level.getBlockState(p).isAir() && !level.getBlockState(p).liquid()) {
                            continue; // keep the ground the pinnacle stands in
                        }
                        level.setBlock(p, state, 2);
                    }
                }
            }
        }
        return true;
    }

    private static boolean isIceCream(BlockState state) {
        return state.is(ModBlocks.VANILLA_ICE_CREAM_BLOCK.get()) || state.is(ModBlocks.CHOCOLATE_ICE_CREAM_BLOCK.get())
                || state.is(ModBlocks.STRAWBERRY_ICE_CREAM_BLOCK.get()) || state.is(ModBlocks.MINT_ICE_CREAM_BLOCK.get());
    }
}
