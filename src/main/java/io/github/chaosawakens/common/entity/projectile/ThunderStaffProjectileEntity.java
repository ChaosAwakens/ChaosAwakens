package io.github.chaosawakens.common.entity.projectile;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
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

public class ThunderStaffProjectileEntity extends AbstractFireballEntity {

	private static final float EXPLOSION_POWER = CAConfig.COMMON.thunderStaffExplosionSize.get();

	public ThunderStaffProjectileEntity(EntityType<? extends AbstractFireballEntity> p_i50166_1_, World p_i50166_2_) {
		super(p_i50166_1_, p_i50166_2_);
	}

	public ThunderStaffProjectileEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(CAEntityTypes.THUNDER_BALL.get(), x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public ThunderStaffProjectileEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(CAEntityTypes.THUNDER_BALL.get(), shooter, accelX, accelY, accelZ, worldIn);
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		if (!this.level.isClientSide) {
			boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
			LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, level);
			lightning.moveTo(this.getX(), this.getY(), this.getZ(), 0, 0);
			this.level.addFreshEntity(lightning);

			boolean hasFire = CAConfig.COMMON.thunderStaffExplosionFire.get();
			switch (CAConfig.COMMON.thunderStaffExplosionType.get()) {
				case 0:
					this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.NONE);
					break;
				case 1:
					this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.BREAK);
					break;
				case 2:
					this.level.explode(null, this.getX(), this.getY(), this.getZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.DESTROY);
					break;
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
