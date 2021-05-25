package io.github.chaosawakens.common.entity.projectile;

import javax.annotation.Nonnull;

import io.github.chaosawakens.common.entity.RoboEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class RoboLaserEntity extends DamagingProjectileEntity {
	private float damage;
	
	public RoboLaserEntity(EntityType<RoboLaserEntity> entityType, World worldIn) {
		super(entityType, worldIn);
	}
	
	public RoboLaserEntity(World worldIn, RoboEntity p_i47273_2_) {
		this(CAEntityTypes.ROBO_LASER.get(), worldIn);
		super.setShooter(p_i47273_2_);
		this.setPosition(p_i47273_2_.getPosX() - (double) (p_i47273_2_.getWidth() + 1.0F) * 0.5D * (double) MathHelper.sin(p_i47273_2_.renderYawOffset * ((float) Math.PI / 180F)), p_i47273_2_.getPosYEye() - (double) 0.1F, p_i47273_2_.getPosZ() + (double) (p_i47273_2_.getWidth() + 1.0F) * 0.5D * (double) MathHelper.cos(p_i47273_2_.renderYawOffset * ((float) Math.PI / 180F)));
	}
	
	@OnlyIn(Dist.CLIENT)
	public RoboLaserEntity(World worldIn, double x, double y, double z, double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
		this(CAEntityTypes.ROBO_LASER.get(), worldIn);
		this.setPosition(x, y, z);
		
		for (int i = 0; i < 7; ++i) {
			double d0 = 0.4D + 0.1D * (double) i;
			worldIn.addParticle(ParticleTypes.CRIT, x, y, z, p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0);
		}
		this.setMotion(p_i47274_8_, p_i47274_10_, p_i47274_12_);
	}
	
	public RoboLaserEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(CAEntityTypes.ROBO_LASER.get(), shooter, accelX, accelY, accelZ, worldIn);
	}
	
	@Override
	public void tick() {
		super.tick();
		Vector3d vector3d = this.getMotion();
		RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
		if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
			this.onImpact(raytraceresult);
		}
		this.setFire(0);
		double d0 = this.getPosX() + vector3d.x;
		double d1 = this.getPosY() + vector3d.y;
		double d2 = this.getPosZ() + vector3d.z;
		this.updatePitchAndYaw();
		if (this.world.func_234853_a_(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
			this.remove();
		} else {
			this.setMotion(vector3d.scale(0.99F));
			this.setPosition(d0, d1, d2);
		}
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		super.onEntityHit(result);
		Entity entity = this.getShooter();
		if (entity instanceof RoboEntity)
			result.getEntity().attackEntityFrom(DamageSource.causeIndirectDamage(this, (RoboEntity) entity).setProjectile(), damage);
		
	}
	
	@Override
	protected boolean isFireballFiery() {
		return true; //TODO Make it not fiery
	}
	
	@Nonnull
	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void registerData() {}
	
	public float getDamage() { return damage; }
	public void setDamage(float damage) { this.damage = damage; }
}
