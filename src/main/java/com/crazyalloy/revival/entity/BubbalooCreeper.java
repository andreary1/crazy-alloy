package com.crazyalloy.revival.entity;

import com.crazyalloy.revival.config.RevivalConfig;
import com.crazyalloy.revival.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gamerules.GameRules;

/**
 * Bubbaloo Creeper: a pink, gum-filled creeper. It behaves like a creeper, drops extra gunpowder and, when it is
 * killed (not when it explodes), leaves a puddle of Bubbaloo where it stood (only if mobGriefing is on).
 */
public class BubbalooCreeper extends Creeper {
    public BubbalooCreeper(EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Creeper.createAttributes();
    }

    public static boolean checkSpawnRules(EntityType<BubbalooCreeper> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(type, level, reason, pos, random) && SpawnRules.chance(reason, random, RevivalConfig.BUBBALOO_CREEPER_SPAWN_CHANCE);
    }

    @Override
    public void die(DamageSource source) {
        boolean wasAlive = !this.isRemoved() && !this.dead;
        super.die(source);
        if (wasAlive && this.level() instanceof ServerLevel level && level.getGameRules().get(GameRules.MOB_GRIEFING)) {
            BlockPos pos = this.blockPosition();
            if (level.getBlockState(pos).canBeReplaced()) {
                level.setBlockAndUpdate(pos, ModBlocks.BUBBALOO.get().defaultBlockState());
            }
        }
    }
}
