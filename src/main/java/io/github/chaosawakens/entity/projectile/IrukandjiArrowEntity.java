package io.github.chaosawakens.entity.projectile;

import io.github.chaosawakens.registry.ModEntityTypes;
import io.github.chaosawakens.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class IrukandjiArrowEntity extends AbstractArrowEntity {

	private int duration = 200;

	public IrukandjiArrowEntity(EntityType<? extends IrukandjiArrowEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public IrukandjiArrowEntity(World worldIn, double x, double y, double z) {
		super(ModEntityTypes.IRUKANDJI_ARROW.get(), x, y, z, worldIn);
	}

	public IrukandjiArrowEntity(World worldIn, LivingEntity shooter) {
		super(ModEntityTypes.IRUKANDJI_ARROW.get(), shooter, worldIn);
	}
	
	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(ModItems.IRUKANDJI_ARROW.get());
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
		if (!this.world.isRemote && this.inGround && this.timeInGround != 0 && this.timeInGround >= 600) {
			this.world.setEntityState(this, (byte) 0);
		}
		
	}
	
	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("Duration", this.duration);
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	public double getDamage() {
		return 100.0D;
	}
}