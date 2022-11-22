package io.github.chaosawakens.common.entity.projectile;

import javax.annotation.Nonnull;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.AttackSquidEntity;
import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AttackSquidProjectile extends DamagingProjectileEntity {

	public AttackSquidProjectile(EntityType<? extends DamagingProjectileEntity> type, World world) {
		super(type, world);
	}
	
	public AttackSquidProjectile(AttackSquidEntity entity, World world) {
		this(CAEntityTypes.ATTACK_SQUID_PROJECTILE.get(), world);
		super.setOwner(entity);
		this.setPos(
				entity.getX() - (double) (entity.getBbWidth() + 1.0F) * 0.5D * (double) MathHelper.sin(entity.yBodyRot * ((float) Math.PI / 180F)),
				entity.getEyeY() - (double) 0.1F,
				entity.getZ() + (double) (entity.getBbWidth() + 1.0F) * 0.5D * (double) MathHelper.cos(entity.yBodyRot * ((float) Math.PI / 180F)));
	}
	
	public AttackSquidProjectile(World worldIn, double x, double y, double z, double deltaX, double deltaY, double deltaZ) {
		this(CAEntityTypes.ATTACK_SQUID_PROJECTILE.get(), worldIn);
		this.setPos(x, y, z);
		for (int i = 0; i < 8; ++i) {
			double d0 = 0.4D + 0.1D * (double) i;
			worldIn.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, deltaX * d0, deltaY, deltaZ * d0);
		}
		this.setDeltaMovement(deltaX, deltaY, deltaZ);
	}
	
	public AttackSquidProjectile(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, AttackSquidProjectileType type) {
		super(CAEntityTypes.ATTACK_SQUID_PROJECTILE.get(), shooter, accelX, accelY, accelZ, worldIn);
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
		AttackSquidProjectileType type = AttackSquidProjectileType.WATER;
		if (!this.level.isClientSide) {
			if (this.getEntity() instanceof AttackSquidEntity) {
				AttackSquidEntity squid = (AttackSquidEntity) this.getEntity();
				LivingEntity target = squid.getTarget();
				switch (type) {
				default:
					super.onHit(result);
					return;
					
				case WATER:
					for (int time = 0; time < IUtilityHelper.randomBetween(200, 400); time++) {
						target.hurt(DamageSource.DROWN, 1.0F);
					}
					super.onHit(result);
					break;
				
				case INK:
					target.hurt(DamageSource.mobAttack(squid), 8.0F);
					squid.spawnInk();
					for (int time = 0; time < IUtilityHelper.randomBetween(100, 200); time++) {
						target.addEffect(new EffectInstance(Effects.BLINDNESS, IUtilityHelper.randomBetween(100, 200)));
					}
					super.onHit(result);
					break;
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		super.tick();
		Vector3d vector3d = this.getDeltaMovement();
		RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
		if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) this.onHit(raytraceresult);
		this.setSecondsOnFire(0);
		double d0 = this.getX() + vector3d.x;
		double d1 = this.getY() + vector3d.y;
		double d2 = this.getZ() + vector3d.z;
		this.updateRotation();
		if (this.level.getBlockStates(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
			this.remove();
		} else {
			this.setDeltaMovement(vector3d.scale(0.99F));
			this.setPos(d0, d1, d2);
		}
	}
	
	@Override
	protected void updateRotation() {
		super.updateRotation();
	}
	
	@Override
	protected boolean updateInWaterStateAndDoFluidPushing() {
		return false;
	}
	
	@Nonnull
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	static enum AttackSquidProjectileType {
		WATER,
		INK
	}
	
}
