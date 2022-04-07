package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.RoboPounderEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;

//When calling this for goal, make sure to add the animation duration to the rage duration (for calculation purposes)
public class AnimatableRageRunGoal<LE extends LivingEntity> extends AnimatableGoal{
	public RoboPounderEntity roboPounder;
	public LivingEntity target;
	public Class<LE> targetType;
	public final BiFunction<Double, Double, Boolean> attackPredicate;
	public EntityPredicate targetConditions;
	public boolean hasHit;
	public float rageRunDuration;
	
	public AnimatableRageRunGoal(RoboPounderEntity roboPounder, Class<LE> targetType, float rageRunDuration, double animationLength, double attackBegin, double attackEnd) {
		this.roboPounder = roboPounder;
		this.targetType = targetType;
		this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
		this.rageRunDuration = rageRunDuration;
		this.setFlags(EnumSet.of(Flag.TARGET));
	}
	
	public AnimatableRageRunGoal(RoboPounderEntity roboPounder, Class<LE> targetType, float rageRunDuration, Predicate<LivingEntity> targetConditions, double animationLength, double attackBegin, double attackEnd) {
		this.roboPounder = roboPounder;
		this.targetType = targetType;
		this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
		this.targetConditions = (new EntityPredicate().range(getFollowRange()).allowUnseeable().selector(targetConditions));
		this.rageRunDuration = rageRunDuration;
		this.setFlags(EnumSet.of(Flag.TARGET));
	}
	
	public boolean canRageRun() {
		if (target == null) return false;
		if (roboPounder == null) return false;
		
		if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
			roboPounder.setAttacking(false);
			roboPounder.setRageMode(false);
			roboPounder.setRageRunning(false);
			return false;
		}
		
		if (isDoingAnythingThatIsNotRageRunning()) return false;
		
		target = target.level.getNearestLoadedEntity(targetType, targetConditions, roboPounder, roboPounder.getX(), roboPounder.getY(), roboPounder.getZ(), getTargetSearchArea(getFollowRange()));
		
		if (isGoalInProgress()) return false;
		if (target.isAlive() && target.isSpectator()) return false;
		
		double distance = roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());
		
        if (distance <= getFollowRange() && enableRageMode()) {
        	return true;
        }
        return false;
	}
	
    protected static double getRageAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth();
    }
    
    private boolean isDoingAnythingThatIsNotRageRunning() {
    	return roboPounder.getPunching() || roboPounder.getSideSweep();
    }
	
	public void rageRunTowardsTarget() {
    	roboPounder.setAggressive(true);
    	roboPounder.setAttacking(true);
    	roboPounder.setRageRunning(true);
    	roboPounder.setMoving(true);
    	roboPounder.setRageMode(false);
		roboPounder.setPunching(false);
		roboPounder.setSideSweeping(false);
    	roboPounder.lookAt(target, 30.0F, 30.0F);
    	roboPounder.moveTo(target.blockPosition(), 1.0F, 1.0F);
    	return;
	}
	
	@SuppressWarnings("unused")
	public void deactivateRage() {
    	roboPounder.setAggressive(false);
    	roboPounder.setAttacking(false);
    	roboPounder.setRageRunning(false);
    	roboPounder.setMoving(false);
    	roboPounder.setRageMode(false);
    	boolean deactivated = deactivated();
    	deactivated = true;
    	roboPounder.lookAt(target, 30.0F, 30.0F);
    	roboPounder.getNavigation().recomputePath();
    	return;
	}
	
	public static boolean deactivated() {
		return false;
	}
	
    public boolean enableRageMode() {
    	if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !hasHit && !isGoalInProgress() && !isDoingAnythingThatIsNotRageRunning()) {
    		do {
    			roboPounder.setSpeed(0);
    			roboPounder.setRageMode(true);
    		} while (this.animationProgress < this.animationLength);
    		
    		return true;
    	}
    	
    	if (isAnimationFinished() ) {
    		this.animationProgress = 0;
    		roboPounder.setRageMode(false);
    	}
    	return false;
    }
	
	protected double getFollowRange() {
		return this.roboPounder.getAttributeValue(Attributes.FOLLOW_RANGE);
	}
	
	 
	protected AxisAlignedBB getTargetSearchArea(double areaXAndZ) {	
		return roboPounder.getBoundingBox().inflate(areaXAndZ, 5.0D, areaXAndZ);
	}

	@Override
	public boolean canUse() {
		return canRageRun() && !isGoalInProgress();
	}

	@Override
	public boolean canContinueToUse() {
		if (Math.random() <= 0.1) return false;
		
		return canUse();
	}
	
	@SuppressWarnings("unused")
	@Override
	public void tick() {
		super.baseTick();
		if (isDoingAnythingThatIsNotRageRunning()) return;
		
		if (!isAnimationFinished() && roboPounder.getRageMode() || !isAnimationFinished() && deactivated()) {
			roboPounder.setSpeed(0);
		}
		
		if (canRageRun()) {
			float limit = 0;
			boolean hit = roboPounder.getTarget().getLastDamageSource() == DamageSource.mobAttack(roboPounder);
			if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !hasHit) {
				double distance = roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());
				target = target.level.getNearestLoadedEntity(targetType, targetConditions, roboPounder, roboPounder.getX(), roboPounder.getY(), roboPounder.getZ(), getTargetSearchArea(getFollowRange()));
				this.animationLength = this.animationLength + rageRunDuration;
				
	        	do {
	        		rageRunTowardsTarget();
	        		limit += 1;
	        	} while (this.animationProgress < this.animationLength && limit < rageRunDuration);
	        	
	        	if (this.animationProgress == this.animationLength) {
	        		this.animationProgress = 0;
	        	}
	        	
	        	if (distance <= getRageAttackReachSq(roboPounder, target)) {
	        		roboPounder.doHurtTarget(target);
	        	}
			}
			if (limit == rageRunDuration || hit) {
				deactivateRage();
				this.animationProgress = 0;
				for (int waitDur = 0; waitDur < 10; waitDur++) {
					boolean canRageRun = canRageRun();
					canRageRun = false;
				}
			}
		}
	}

}
