package com.crazyalloy.revival.entity.projectile;

import com.crazyalloy.revival.registry.ModEntities;
import com.crazyalloy.revival.registry.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Brown Sugar Brick, thrown by hand. Revival choice: it deals 2 damage (the original only granted an advancement). */
public class BrownSugarBrickEntity extends CandyProjectile {
    public BrownSugarBrickEntity(EntityType<? extends BrownSugarBrickEntity> type, Level level) {
        super(type, level);
    }

    public BrownSugarBrickEntity(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.BROWN_SUGAR_BRICK.get(), owner, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BROWN_SUGAR_BRICK.get();
    }

    @Override
    protected float damage() {
        return 2.0F;
    }

    @Override
    protected float knockback() {
        return 0.5F;
    }

    @Override
    protected SoundEvent impactSound() {
        return SoundEvents.MUD_BRICKS_BREAK;
    }
}
