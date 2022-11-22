package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class RayGunProjectileEntity extends AbstractFireballEntity {
	private static final float EXPLOSION_POWER = CACommonConfig.COMMON.rayGunExplosionSize.get();

	public RayGunProjectileEntity(EntityType<? extends AbstractFireballEntity> p_i50166_1_, World p_i50166_2_) {
		super(p_i50166_1_, p_i50166_2_);
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
			boolean hasFire = CACommonConfig.COMMON.rayGunExplosionFire.get();
			switch (CACommonConfig.COMMON.rayGunExplosionType.get()) {
				case NONE: this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.NONE);
				case BREAK: this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.BREAK);
				case DESTROY: this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.DESTROY);
			}
			this.remove();
		}
	}

	@OnlyIn(Dist.CLIENT)
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
