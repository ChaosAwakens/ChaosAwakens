package io.github.chaosawakens.common.entity.projectile;

import javax.annotation.Nonnull;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class RayGunProjectileEntity extends AbstractFireballEntity {
	private static final float EXPLOSION_POWER = CAConfigManager.MAIN_COMMON.rayGunExplosionSize.get();

	public RayGunProjectileEntity(EntityType<? extends AbstractFireballEntity> type, World world) {
		super(type, world);
	}

	public RayGunProjectileEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(CAEntityTypes.EXPLOSIVE_BALL.get(), x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public RayGunProjectileEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(CAEntityTypes.EXPLOSIVE_BALL.get(), shooter, accelX, accelY, accelZ, worldIn);
	}

	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		if (!this.level.isClientSide) {
			boolean hasFire = CAConfigManager.MAIN_COMMON.rayGunExplosionFire.get();
			switch (CAConfigManager.MAIN_COMMON.rayGunExplosionType.get()) {
				case NONE: this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.NONE);
				case BREAK: this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.BREAK);
				case DESTROY: this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.DESTROY);
			}
			this.remove();
		}
	}

	@Override
	public ItemStack getItem() {
		ItemStack itemstack = this.getItemRaw();
		return itemstack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemstack;
	}

	@Nonnull
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
