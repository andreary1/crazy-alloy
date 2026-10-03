package com.crazyalloy.revival.item;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Brown Sugar Sword: every hit adds knockback roughly equal to Knockback II, like the original. */
public class BrownSugarSwordItem extends Item {
    public BrownSugarSwordItem(Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        float yaw = attacker.getYRot() * Mth.DEG_TO_RAD;
        target.knockback(1.0, Mth.sin(yaw), -Mth.cos(yaw));
    }
}
