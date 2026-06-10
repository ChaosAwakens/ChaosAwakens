package io.github.chaosawakens.content.entity.projectiles;

import io.github.chaosawakens.content.registry.CAEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.HitResult;


public class RayGunProjectileEntity extends AbstractHurtingProjectile {
    public RayGunProjectileEntity(EntityType<? extends RayGunProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }
    public RayGunProjectileEntity(Level level, LivingEntity shooter, double xPower, double yPower, double zPower) {
        super(CAEntityTypes.RAY_GUN_LASER.get(), shooter, xPower, yPower, zPower, level);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!level().isClientSide()) {
            level().explode(this, getX(), getY(), getZ(), 4F, true, Level.ExplosionInteraction.MOB);
            discard();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }


    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {super.readAdditionalSaveData(compoundTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {super.addAdditionalSaveData(compoundTag);
    }
}

