package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class IrukandjiArrowEntity extends AbstractArrowEntity {
    private int duration = 200;

    public IrukandjiArrowEntity(EntityType<? extends IrukandjiArrowEntity> type, World worldIn) {
        super(type, worldIn);
        this.setBaseDamage(100D);
    }

    public IrukandjiArrowEntity(World worldIn, double x, double y, double z) {
        super(CAEntityTypes.IRUKANDJI_ARROW.get(), x, y, z, worldIn);
    }

    public IrukandjiArrowEntity(World worldIn, LivingEntity shooter) {
        super(CAEntityTypes.IRUKANDJI_ARROW.get(), shooter, worldIn);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(CAItems.IRUKANDJI_ARROW.get());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Duration")) {
            this.duration = compound.getInt("Duration");
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.level.broadcastEntityEvent(this, (byte) 0);
        }

    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Duration", this.duration);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}