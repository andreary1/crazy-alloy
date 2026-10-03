package com.crazyalloy.revival.entity.ai;

import com.crazyalloy.revival.registry.ModTags;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * The mob notices sweets lying on the ground nearby, walks to them and eats one.
 * Used by the Candy Tube Dog: dropping a sweet is a way to lure or heal it without holding the item.
 */
public class SeekSweetsGoal extends Goal {
    private final PathfinderMob mob;
    private final double speed;
    private final double range;
    private final Consumer<ItemStack> onEat;
    private @Nullable ItemEntity target;
    private int cooldown;

    public SeekSweetsGoal(PathfinderMob mob, double speed, double range, Consumer<ItemStack> onEat) {
        this.mob = mob;
        this.speed = speed;
        this.range = range;
        this.onEat = onEat;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }
        cooldown = 20 + mob.getRandom().nextInt(20);
        List<ItemEntity> items = mob.level().getEntitiesOfClass(ItemEntity.class, mob.getBoundingBox().inflate(range, 3.0, range),
                item -> item.isAlive() && item.getItem().is(ModTags.Items.SWEETS));
        target = items.isEmpty() ? null : items.get(0);
        return target != null;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive() && mob.distanceToSqr(target) < range * range * 2;
    }

    @Override
    public void start() {
        if (target != null) {
            mob.getNavigation().moveTo(target, speed);
        }
    }

    @Override
    public void stop() {
        target = null;
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (target == null) {
            return;
        }
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        if (mob.distanceToSqr(target) < 2.0) {
            ItemStack stack = target.getItem();
            ItemStack eaten = stack.split(1);
            if (stack.isEmpty()) {
                target.discard();
            } else {
                target.setItem(stack);
            }
            onEat.accept(eaten);
            target = null;
        } else if (mob.getNavigation().isDone()) {
            mob.getNavigation().moveTo(target, speed);
        }
    }
}
