package com.crazyalloy.revival.item;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.worldgen.IceCreamPortals;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * Ice Cream Amulet (stage 6): used on the inside face of a frame of Chocolate Ice Cream Blocks, it lights the portal to
 * the Ice Cream Dimension. It is not used up. The Ice Cream Vendor sells it at level 5 for five Ultimate Ice Creams.
 */
public class IceCreamAmuletItem extends Item {
    public IceCreamAmuletItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos inside = context.getClickedPos().relative(context.getClickedFace());
        if (!RevivalConfig.ICE_CREAM_PORTAL_ENABLED.get() || !IceCreamPortals.isFrame(level.getBlockState(context.getClickedPos()))) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (IceCreamPortals.tryLight(level, inside)) {
            level.playSound(null, inside, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.3F);
            level.playSound(null, inside, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 0.4F, 1.6F);
            if (level instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.HAPPY_VILLAGER, inside.getX() + 0.5, inside.getY() + 0.5, inside.getZ() + 0.5, 12, 0.6, 0.8, 0.6, 0.0);
            }
            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.FAIL;
    }
}
