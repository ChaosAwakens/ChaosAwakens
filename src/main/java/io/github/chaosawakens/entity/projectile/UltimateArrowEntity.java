package io.github.chaosawakens.entity.projectile;

import io.github.chaosawakens.registry.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class UltimateArrowEntity extends AbstractArrowEntity {

    private int duration = 200;

    public UltimateArrowEntity(EntityType<? extends UltimateArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public UltimateArrowEntity(World worldIn, double x, double y, double z) {
        super(ModEntityTypes.ULTIMATE_ARROW.get(), x, y, z, worldIn);
    }

    public UltimateArrowEntity(World worldIn, LivingEntity shooter) {
        super(ModEntityTypes.ULTIMATE_ARROW.get(), shooter, worldIn);
    }

    protected void arrowHit(LivingEntity living) {
        if (living instanceof PlayerEntity || living instanceof TameableEntity) {
            living.heal(1.0F);
            setDead();
            return;
        }
        super.arrowHit(living);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.AIR);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Duration")) {
            this.duration = compound.getInt("Duration");
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Duration", this.duration);
    }

    public double getDamage() { return 10.0D; }
}
