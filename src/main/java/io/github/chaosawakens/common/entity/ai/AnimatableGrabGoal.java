package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;

import io.github.chaosawakens.api.IGrabber;
import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

/**
 * @author invalid2
 *
 */
public class AnimatableGrabGoal<G extends AnimatableMonsterEntity & IGrabber> extends AnimatableGoal {
	
	private boolean isGrabbing;
	private boolean hasGrabbed;
	private final int avrgNumHits;
	private int numOfHits;
	
	/**
	 * Grabs the target entity and deals some damage
	 */
	public AnimatableGrabGoal(G entity, int avrgNumHits) {
		this.entity = entity;
		this.avrgNumHits = avrgNumHits;
		this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
	}
	
	@Override
	public boolean shouldExecute() {
		if(RANDOM.nextInt(10) == 0)return false;
		
		return this.checkIfValid(this, this.entity, this.entity.getAttackTarget());
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(RANDOM.nextInt(10) == 0)return true;
		
		return this.checkIfValid(this, this.entity, this.entity.getAttackTarget());
	}
	
	@Override
	public void resetTask() {
		this.isGrabbing = false;
		((IGrabber) this.entity).setGrabbing(this.entity, false);
		this.hasGrabbed = false;
	}
	
	@Override
	public void tick() {
		this.baseTick();
		LivingEntity target = this.entity.getAttackTarget();
		if(target == null || this.hasGrabbed)return;
		
		this.grabTarget(target);
		
		target.updateRidden();
	}
	
	protected void grabTarget(LivingEntity target) {
		if(this.isGrabbing) {
			this.attackWhileGrab(target);
			return;
		}
		target.startRiding(entity);
		this.entity.setAttacking(true);
		this.isGrabbing = true;
		((IGrabber) this.entity).setGrabbing(this.entity, true);
	}
	
	protected void attackWhileGrab(LivingEntity target) {
		if(RANDOM.nextInt(this.avrgNumHits) < this.avrgNumHits - this.numOfHits) {
			this.entity.attackEntityAsMob(target);
			this.numOfHits++;
			return;
		}
		this.releaseTarget(target);
	}
	
	protected void releaseTarget(LivingEntity target) {
		this.hasGrabbed = true;
		this.isGrabbing = false;
		this.entity.setAttacking(false);
		((IGrabber) this.entity).setGrabbing(this.entity, false);
		
	}
	
	private boolean checkIfValid(AnimatableGrabGoal<?> goal, AnimatableMonsterEntity attacker, LivingEntity target) {
		if(target == null)return false;
		if(target.isAlive() && !target.isSpectator()) {
			if(target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
				attacker.setAttacking(false);
				return false;
			}
			double distance = goal.entity.getDistanceSq(target.getPosX(), target.getPosY(), target.getPosZ());
			if(distance <= AnimatableGoal.getAttackReachSq(attacker, target))return true;
		}
		attacker.setAttacking(false);
		return false;
	}
}
