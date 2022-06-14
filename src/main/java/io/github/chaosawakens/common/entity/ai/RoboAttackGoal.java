package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.projectile.RoboLaserEntity;
import io.github.chaosawakens.common.entity.robo.RoboEntity;
import io.github.chaosawakens.common.entity.robo.RoboRangedEntity;
import io.github.chaosawakens.common.entity.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class RoboAttackGoal extends Goal {
	private final RoboRangedEntity projectileOwner;
	private final int fireRateBase;
	private final float damage;
	private final float ownerHeightYScale;
	private int attackTimer;

	public RoboAttackGoal(RoboRangedEntity robo, int fireRateBase, float damage, float ownerHeightYScale) {
		this.projectileOwner = robo;
		this.fireRateBase = fireRateBase;
		this.damage = damage;
		this.ownerHeightYScale = ownerHeightYScale;
	}

	@Override
	public boolean canUse() {
		return this.projectileOwner.getTarget() != null;
	}

	@Override
	public void start() {
		this.attackTimer = 0;
	}

	@Override
	public void stop() {
		this.attackTimer = 0;
		this.projectileOwner.setAttacking(false);
	}

	@Override
	public void tick() {
		LivingEntity targetEntity = this.projectileOwner.getTarget();
		assert targetEntity != null;
		if (targetEntity.distanceToSqr(this.projectileOwner) < 4096.0D && this.projectileOwner.canSee(targetEntity)) {
			World world = this.projectileOwner.level;

			this.projectileOwner.getLookControl().setLookAt(projectileOwner.getTarget(), 30.0F, 30.0F);

			this.attackTimer++;
			if (this.attackTimer == fireRateBase * 2) {
				Vector3d lookVector = this.projectileOwner.getViewVector(1.0F);
				Vector3d directionNormal = new Vector3d(targetEntity.getX() - (this.projectileOwner.getX() - lookVector.x()), targetEntity.getY(0.5) - (0.5 + this.projectileOwner.getY(0.5)), targetEntity.getZ() - (this.projectileOwner.getZ() - lookVector.z())).normalize();

				if (!this.projectileOwner.isSilent()) world.levelEvent(null, 1016, this.projectileOwner.blockPosition(), 0);
				Entity entity = this.projectileOwner.getEntity();
				Entity entity1 = this.projectileOwner.getTarget();
				if (entity instanceof RoboWarriorEntity && !(entity1 instanceof RoboEntity)) {
					RoboLaserEntity roboExplosionLaserEntity = new RoboLaserEntity(world, this.projectileOwner, directionNormal.x() / 5, directionNormal.y() / 5, directionNormal.z() / 5, true);
					roboExplosionLaserEntity.setPos(this.projectileOwner.getX(), this.projectileOwner.getY(ownerHeightYScale), this.projectileOwner.getZ());
					roboExplosionLaserEntity.setDamage(damage);

					this.projectileOwner.level.playSound(null, this.projectileOwner.getX(), this.projectileOwner.getY(), this.projectileOwner.getZ(), CASoundEvents.ROBO_SHOOT.get(), this.projectileOwner.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);

					world.addFreshEntity(roboExplosionLaserEntity);
				} else {
					RoboLaserEntity roboLaserEntity = new RoboLaserEntity(world, this.projectileOwner, directionNormal.x() / 5, directionNormal.y() / 5, directionNormal.z() / 5, false);
					roboLaserEntity.setPos(this.projectileOwner.getX(), this.projectileOwner.getY(ownerHeightYScale), this.projectileOwner.getZ());

					roboLaserEntity.setDamage(damage);

					this.projectileOwner.level.playSound(null, this.projectileOwner.getX(), this.projectileOwner.getY(), this.projectileOwner.getZ(), CASoundEvents.ROBO_SHOOT.get(), this.projectileOwner.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);

					world.addFreshEntity(roboLaserEntity);
				}
				this.attackTimer = -fireRateBase * 4;
			}
		} else if (this.attackTimer > 0) this.attackTimer--;
		this.projectileOwner.setAttacking(this.attackTimer > 10);
	}
	
	public static class RoboSniperAttackkGoal extends Goal{
		private final RoboRangedEntity projectileOwner;
		private final int fireRateBase;
		private final float damage;
		private final float ownerHeightYScale;
		private int attackTimer;
		private double distance;

		public RoboSniperAttackkGoal(RoboRangedEntity robo, int fireRateBase, float damage, float ownerHeightYScale, double distance) {
			this.projectileOwner = robo;
			this.fireRateBase = fireRateBase;
			this.damage = damage;
			this.ownerHeightYScale = ownerHeightYScale;
			this.distance = distance;
		}

		@Override
		public boolean canUse() {
			return this.projectileOwner.getTarget() != null;
		}

		@Override
		public void start() {
			this.attackTimer = 0;
		}

		@Override
		public void stop() {
			this.attackTimer = 0;
			this.projectileOwner.setAttacking(false);
		}

		@Override
		public void tick() {
			LivingEntity targetEntity = this.projectileOwner.getTarget();
			assert targetEntity != null;
			if (targetEntity.distanceToSqr(this.projectileOwner) < distance && this.projectileOwner.canSee(targetEntity)) {
				World world = this.projectileOwner.level;

				this.projectileOwner.getLookControl().setLookAt(projectileOwner.getTarget(), 30.0F, 30.0F);

				this.attackTimer++;
				if (this.attackTimer == fireRateBase * 2) {
					Vector3d lookVector = this.projectileOwner.getViewVector(1.0F);
					Vector3d directionNormal = new Vector3d(targetEntity.getX() - (this.projectileOwner.getX() - lookVector.x()), targetEntity.getY(0.5) - (0.5 + this.projectileOwner.getY(0.5)), targetEntity.getZ() - (this.projectileOwner.getZ() - lookVector.z())).normalize();

					if (!this.projectileOwner.isSilent()) world.levelEvent(null, 1016, this.projectileOwner.blockPosition(), 0);
					Entity entity = this.projectileOwner.getEntity();
					Entity entity1 = this.projectileOwner.getTarget();
					if (entity instanceof RoboWarriorEntity && !(entity1 instanceof RoboEntity)) {
						RoboLaserEntity roboExplosionLaserEntity = new RoboLaserEntity(world, this.projectileOwner, directionNormal.x() / 5, directionNormal.y() / 5, directionNormal.z() / 5, true);
						roboExplosionLaserEntity.setPos(this.projectileOwner.getX(), this.projectileOwner.getY(ownerHeightYScale), this.projectileOwner.getZ());
						roboExplosionLaserEntity.setDamage(damage);

						this.projectileOwner.level.playSound(null, this.projectileOwner.getX(), this.projectileOwner.getY(), this.projectileOwner.getZ(), CASoundEvents.ROBO_SHOOT.get(), this.projectileOwner.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);

						world.addFreshEntity(roboExplosionLaserEntity);
					} else {
						RoboLaserEntity roboLaserEntity = new RoboLaserEntity(world, this.projectileOwner, directionNormal.x() / 5, directionNormal.y() / 5, directionNormal.z() / 5, false);
						roboLaserEntity.setPos(this.projectileOwner.getX(), this.projectileOwner.getY(ownerHeightYScale), this.projectileOwner.getZ());

						roboLaserEntity.setDamage(damage);

						this.projectileOwner.level.playSound(null, this.projectileOwner.getX(), this.projectileOwner.getY(), this.projectileOwner.getZ(), CASoundEvents.ROBO_SHOOT.get(), this.projectileOwner.getSoundSource(), 1.0F, 1.0F + 1 * 0.2F);

						world.addFreshEntity(roboLaserEntity);
					}
					this.attackTimer = -fireRateBase * 4;
				}
			} else if (this.attackTimer > 0) this.attackTimer--;
			this.projectileOwner.setAttacking(this.attackTimer > 10);
		}
	}
}