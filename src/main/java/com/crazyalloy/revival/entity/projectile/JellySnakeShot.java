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

/** Dead Jelly Snake fired from a Jelly Bazooka: 5 damage and a big bounce of knockback. */
public class JellySnakeShot extends CandyProjectile {
    public JellySnakeShot(EntityType<? extends JellySnakeShot> type, Level level) {
        super(type, level);
    }

    public JellySnakeShot(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.JELLY_SNAKE_SHOT.get(), owner, level, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DEAD_JELLY_SNAKE.get();
    }

    @Override
    protected float damage() {
        return 5.0F;
    }

    @Override
    protected float knockback() {
        return 2.0F;
    }

    @Override
    protected SoundEvent impactSound() {
        return SoundEvents.SLIME_JUMP;
    }
}
