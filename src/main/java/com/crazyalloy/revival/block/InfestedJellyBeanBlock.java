package com.crazyalloy.revival.block;

import com.crazyalloy.revival.entity.GrapeSpider;
import com.crazyalloy.revival.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;

/**
 * Looks exactly like Purple Jelly Bean Block, but two Grape Spiders burst out when it is broken.
 * Drops the normal purple block. Silk Touch (like vanilla infested blocks) prevents the ambush.
 */
public class InfestedJellyBeanBlock extends JellyBeanBlock {
    public InfestedJellyBeanBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, tool, dropExperience);
        if (level.getGameRules().get(GameRules.BLOCK_DROPS) && !EnchantmentHelper.hasTag(tool, EnchantmentTags.PREVENTS_INFESTED_SPAWNS)) {
            for (int i = 0; i < 2; i++) {
                GrapeSpider spider = ModEntities.GRAPE_SPIDER.get().create(level, EntitySpawnReason.TRIGGERED);
                if (spider != null) {
                    spider.snapTo(pos.getX() + 0.3 + i * 0.4, pos.getY(), pos.getZ() + 0.5, level.getRandom().nextFloat() * 360.0F, 0.0F);
                    level.addFreshEntity(spider);
                    spider.spawnAnim();
                }
            }
        }
    }
}
