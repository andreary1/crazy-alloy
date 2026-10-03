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

/** Gumdrop fired by Gingerbread Soldiers. */
public class GumdropShot extends CandyProjectile {
    public GumdropShot(EntityType<? extends GumdropShot> type, Level level) {
        super(type, level);
    }

    public GumdropShot(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.GUMDROP_SHOT.get(), owner, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GUMDROP.get();
    }

    @Override
    protected float damage() {
        return 3.0F;
    }

    @Override
    protected float knockback() {
        return 0.0F;
    }

    @Override
    protected SoundEvent impactSound() {
        return SoundEvents.SLIME_SQUISH_SMALL;
    }
}
