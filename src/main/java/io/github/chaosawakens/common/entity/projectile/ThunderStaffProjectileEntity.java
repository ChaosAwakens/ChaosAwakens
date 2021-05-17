package io.github.chaosawakens.common.entity.projectile;

import javax.annotation.Nonnull;

import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.Entity;
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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ThunderStaffProjectileEntity extends AbstractFireballEntity {
	
	private static final float EXPLOSION_POWER = CAConfig.COMMON.explosionSize.get();
	
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
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!this.world.isRemote) {
			boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getShooter());
			LightningBoltEntity lightning = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
			lightning.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), 0, 0);
			this.world.addEntity(lightning);
			
			boolean hasFire = CAConfig.COMMON.explosionFire.get();
			switch (CAConfig.COMMON.explosionType.get()) {
				case 0:
					this.world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.NONE);
					break;
				case 1:
					this.world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.BREAK);
					break;
				case 2:
					this.world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), EXPLOSION_POWER, hasFire, Explosion.Mode.DESTROY);
					break;
			}
			this.remove();
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		ItemStack itemstack = this.getStack();
		return itemstack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : itemstack;
	}
	
	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
