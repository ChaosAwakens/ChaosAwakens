/**
 * 
 */
package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.AnimatedMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.Hand;

/**
 * @author invalid2
 */
public class GeoMeleeAttackGoal extends MeleeAttackGoal {
	private AnimatedMonsterEntity attacker;
	private double hittingFrame;
	private long tickDelta;
	private double animationTick;
	
	public GeoMeleeAttackGoal(AnimatedMonsterEntity monster, double speedIn, double hittingFrame, boolean useLongMemory) {
		super(monster, speedIn, useLongMemory);
		this.attacker = monster;
		this.hittingFrame = hittingFrame;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		//this.tickDelta = System.currentTimeMillis();
		return super.shouldContinueExecuting();
	}
	@Override
	public void startExecuting() {
		this.tickDelta = System.currentTimeMillis();
		super.startExecuting();
	}
	
	@Override
	public void resetTask() {
		this.attacker.setAttacking(false);
		this.attacker.setHitting(false);
		this.animationTick = 0;
		super.resetTask();
	}
	
	@Override
	public void tick() {
		this.tickDelta = System.currentTimeMillis() - this.tickDelta;
		
		if (this.attacker.getAttacking())
			this.animationTick += this.tickDelta;
		ChaosAwakens.LOGGER.debug(this.hittingFrame);
		
		if(this.animationTick >= this.hittingFrame*100/3) {
			this.attacker.setHitting(true);
			this.animationTick = 0;
		}
		
		ChaosAwakens.LOGGER.debug("Goal: "+this.tickDelta+" "+this.animationTick+" ");
		
		super.tick();
		this.tickDelta = System.currentTimeMillis();
	}
	
	@Override
	protected void checkAndPerformAttack(LivingEntity target, double distToTargetSqr) {
		if (distToTargetSqr <= this.getAttackReachSqr(target) && this.getSwingCooldown() <= 0) {
			if (!this.attacker.getAttacking())
				this.attacker.setAttacking(true);
			
			if (this.attacker.getHitting()) {
				this.resetSwingCooldown();
				this.attacker.setAttacking(false);
				this.attacker.setHitting(false);
				this.attacker.swingArm(Hand.MAIN_HAND);
				this.attacker.attackEntityAsMob(target);
			}
		} else {
			this.attacker.setAttacking(false);
			this.animationTick = 0;
		}
	}
}
