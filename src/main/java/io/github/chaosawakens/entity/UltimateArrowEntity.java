package io.github.chaosawakens.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public class UltimateArrowEntity extends ArrowEntity {

    public UltimateArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public UltimateArrowEntity(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public UltimateArrowEntity(World worldIn, LivingEntity shooter) {
        super(worldIn,shooter);
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        if (living instanceof PlayerEntity || living instanceof TameableEntity) {
            living.heal(1.0F);
            setDead();
            return;
        }
        super.arrowHit(living);
    }

    public double getDamage() { return 10.0D; }
}
