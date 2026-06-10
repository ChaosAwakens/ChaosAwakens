package io.github.chaosawakens.content.entity.projectiles;

import io.github.chaosawakens.content.registry.CAEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;

import net.minecraft.world.phys.HitResult;


public class ThunderStaffProjectileEntity extends AbstractHurtingProjectile {
    public ThunderStaffProjectileEntity(EntityType<? extends ThunderStaffProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }
    public ThunderStaffProjectileEntity(Level level, LivingEntity shooter, double xPower, double yPower, double zPower) {
        super(CAEntityTypes.THUNDER_BALL.get(), shooter, xPower, yPower, zPower, level);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!level().isClientSide()) {
            strikeLightning();
            level().explode(this, getX(), getY(), getZ(), 1.5F, false, Level.ExplosionInteraction.MOB);

            discard();
        }
    }

    private void strikeLightning() {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level());

        if (lightning != null) {
            lightning.moveTo(getX(), getY(), getZ());

            if (getOwner() instanceof ServerPlayer serverPlayer) {
                lightning.setCause(serverPlayer);
            }

            level().addFreshEntity(lightning);
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

