package io.github.chaosawakens.common.entity.projectile.arrow;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class UltimateCrossbowArrowEntity extends AbstractArrowEntity {
	private int duration = 200;

	public UltimateCrossbowArrowEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
		super(type, worldIn);
		this.setBaseDamage(getBaseDamage() * 12);
	}

	public UltimateCrossbowArrowEntity(World worldIn, double x, double y, double z) {
		super(CAEntityTypes.ULTIMATE_CROSSBOW_ARROW.get(), x, y, z, worldIn);
		this.setBaseDamage(getBaseDamage() * 12);
	}

	public UltimateCrossbowArrowEntity(World worldIn, LivingEntity shooter) {
		super(CAEntityTypes.ULTIMATE_CROSSBOW_ARROW.get(), shooter, worldIn);
		this.setBaseDamage(getBaseDamage() * 12);
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult result) {
		Entity targetEntity = result.getEntity();
		
		if (targetEntity instanceof PlayerEntity || (targetEntity instanceof TameableEntity && ((TameableEntity) targetEntity).isTame() && ((TameableEntity) targetEntity).getOwner() == this.getOwner())) {
			((LivingEntity) targetEntity).heal(5.0F);
			remove();
			return;
		}
		super.onHitEntity(result);
	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(Items.ARROW);
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
		if (!this.level.isClientSide && this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) this.level.broadcastEntityEvent(this, (byte) 0);
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
