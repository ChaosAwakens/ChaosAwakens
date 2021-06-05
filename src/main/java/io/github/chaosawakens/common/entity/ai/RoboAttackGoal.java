package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.RoboEntity;
import io.github.chaosawakens.common.entity.projectile.RoboLaserEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class RoboAttackGoal extends Goal {
	private final RoboEntity projectileOwner;
	private int attackTimer;
	private int fireRateBase;
	
	private float damage;
	private float ownerHeightYScale;
	
	public RoboAttackGoal(RoboEntity robo, int fireRateBase, float damage, float ownerHeightYScale) {
		this.projectileOwner = robo;
		this.fireRateBase = fireRateBase;
		this.damage = damage;
		this.ownerHeightYScale = ownerHeightYScale;
	}
	
	@Override
	public boolean shouldExecute() {
		return this.projectileOwner.getAttackTarget() != null;
	}
	
	@Override
	public void startExecuting() {
		this.attackTimer = 0;
	}
	
	@Override
	public void resetTask() {
		this.attackTimer = 0;
		this.projectileOwner.setAttacking(false);
	}
	
	@Override
	public void tick() {
		LivingEntity targetEntity = this.projectileOwner.getAttackTarget();
		if (targetEntity.getDistanceSq(this.projectileOwner) < 4096.0D && this.projectileOwner.canEntityBeSeen(targetEntity)) {
			World world = this.projectileOwner.world;
			
			this.projectileOwner.getLookController().setLookPositionWithEntity(projectileOwner.getAttackTarget(), 30.0F, 30.0F);
			
			this.attackTimer++;
			if (this.attackTimer == fireRateBase*2) {
				Vector3d lookVector = this.projectileOwner.getLook(1.0F);
				Vector3d directionNormal = new Vector3d(targetEntity.getPosX() - (this.projectileOwner.getPosX() - lookVector.getX()), targetEntity.getPosYHeight(0.5) - (0.5 + this.projectileOwner.getPosYHeight(0.5)), targetEntity.getPosZ() - (this.projectileOwner.getPosZ() - lookVector.getZ())).normalize();
				
				if (!this.projectileOwner.isSilent()) {
					world.playEvent(null, 1016, this.projectileOwner.getPosition(), 0);
				}
				
				RoboLaserEntity roboLaserEntity = new RoboLaserEntity(world, this.projectileOwner, directionNormal.getX()/5, directionNormal.getY()/5, directionNormal.getZ()/5);
				roboLaserEntity.setPosition(this.projectileOwner.getPosX(), this.projectileOwner.getPosYHeight(ownerHeightYScale), this.projectileOwner.getPosZ());
				roboLaserEntity.setDamage(damage);
				
				this.projectileOwner.world.playSound(null, this.projectileOwner.getPosX(), this.projectileOwner.getPosY(), this.projectileOwner.getPosZ(), CASoundEvents.ROBO_SHOOT.get(), this.projectileOwner.getSoundCategory(), 1.0F, 1.0F + 1 * 0.2F);
				
				world.addEntity(roboLaserEntity);
				
				this.attackTimer = -fireRateBase*4;
			}
		} else if (this.attackTimer > 0) {
			this.attackTimer--;
		}
		this.projectileOwner.setAttacking(this.attackTimer > 10);
	}
}