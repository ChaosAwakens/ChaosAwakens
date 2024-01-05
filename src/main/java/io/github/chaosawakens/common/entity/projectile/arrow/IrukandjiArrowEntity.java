package io.github.chaosawakens.common.entity.projectile.arrow;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class IrukandjiArrowEntity extends AbstractArrowEntity {
	private int duration = 200;

	public IrukandjiArrowEntity(EntityType<? extends IrukandjiArrowEntity> type, World worldIn) {
		super(type, worldIn);
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
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Duration", this.duration);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Duration")) this.duration = compound.getInt("Duration");
	}

	@Override
	public void tick() {
		super.tick();

		if (!level.isClientSide && inGround && inGroundTime != 0 && inGroundTime >= 600) level.broadcastEntityEvent(this, (byte) 0);
		if (level instanceof ServerWorld && !inGround && isAlive() && getDeltaMovement().length() > 0.01D) ((ServerWorld) level).sendParticles(ParticleTypes.CRIT, getX(), getY(), getZ(), MathHelper.nextInt(random, 1, 3), 0.0D, 0.0D, 0.0D, 0.1D);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
