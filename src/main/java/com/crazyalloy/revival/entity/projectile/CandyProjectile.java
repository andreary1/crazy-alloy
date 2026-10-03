package com.crazyalloy.revival.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/** A thrown or fired sweet that deals fixed damage, optionally knocks back, and breaks into crumbs on impact. */
public abstract class CandyProjectile extends ThrowableItemProjectile {
    protected CandyProjectile(EntityType<? extends CandyProjectile> type, Level level) {
        super(type, level);
    }

    protected CandyProjectile(EntityType<? extends CandyProjectile> type, LivingEntity owner, Level level, ItemStack stack) {
        super(type, owner, level, stack);
    }

    protected abstract float damage();

    /** Extra knockback strength applied in the direction of travel (0 for none). */
    protected float knockback() {
        return 0.0F;
    }

    protected @Nullable SoundEvent impactSound() {
        return null;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ItemStack item = this.getItem();
            if (!item.isEmpty()) {
                for (int i = 0; i < 8; i++) {
                    this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item)),
                            this.getX(), this.getY(), this.getZ(), (this.random.nextDouble() - 0.5) * 0.1, 0.1, (this.random.nextDouble() - 0.5) * 0.1);
                }
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity target = hitResult.getEntity();
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        boolean hurt = target.hurtServer(level, this.damageSources().thrown(this, this.getOwner()), this.damage());
        if (hurt && this.knockback() > 0.0F && target instanceof LivingEntity living) {
            Vec3 dir = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize();
            living.knockback(this.knockback(), -dir.x, -dir.z);
            living.push(0.0, 0.25, 0.0);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            SoundEvent sound = this.impactSound();
            if (sound != null) {
                this.playSound(sound, 1.0F, 1.0F + (this.random.nextFloat() - 0.5F) * 0.3F);
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}
