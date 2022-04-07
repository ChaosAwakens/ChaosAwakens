package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;
import java.util.function.BiFunction;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.RoboPounderEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

public class AnimatableRoboSideSweepGoal extends AnimatableGoal {

    protected final BiFunction<Double, Double, Boolean> attackPredicate;
    protected boolean hasHit;
    protected RoboPounderEntity roboPounder;

    /**
     * @param entity          Attacking entity
     * @param animationLength
     */
    public AnimatableRoboSideSweepGoal(RoboPounderEntity roboPounder, double animationLength, double attackBegin, double attackEnd) {
        this.roboPounder = roboPounder;
 //       this.entity = this.roboPounder;
        this.animationLength = animationLength;
        this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }
    
    private boolean isDoingAnythingThatIsNotSideSweeping() {
    	return roboPounder.getRageMode() || roboPounder.getSideSweep() || roboPounder.getRageRunning() && this.animationProgress != 0;
    }
    
    protected static double getSideSweepAttackReachSq(RoboPounderEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 3F * attacker.getBbWidth() * 3F + target.getBbWidth();
    }
    
    protected static boolean canSideSweep() {
    	return RANDOM.nextDouble() <= 0.3;
    }

    protected static boolean checkIfValid(AnimatableRoboSideSweepGoal animatableRoboSideSweepGoal, RoboPounderEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (!canSideSweep()) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setAttacking(false);
                return false;
            }
            double distance = animatableRoboSideSweepGoal.roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= getSideSweepAttackReachSq(attacker, target)) return true;
        }
        attacker.setAttacking(false);
        attacker.setSideSweeping(false);
        return false;
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;
     //   if (isGoalInProgress()) return false;
        if (isDoingAnythingThatIsNotSideSweeping()) return false;

        return AnimatableRoboSideSweepGoal.checkIfValid(this, roboPounder, this.roboPounder.getTarget()) && canSideSweep() && roboPounder.getTarget().getHealth() <= 10.0F;
    }

    @Override
    public boolean canContinueToUse() {
        if (Math.random() <= 0.1) return true;
        if (isDoingAnythingThatIsNotSideSweeping()) return false;

        return AnimatableRoboSideSweepGoal.checkIfValid(this, roboPounder, this.roboPounder.getTarget()) && canSideSweep();
    }

    @Override
    public void start() {
        this.roboPounder.setAttacking(true);
        this.roboPounder.setAggressive(true);
        this.roboPounder.setSideSweeping(true);;
        this.animationProgress = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.roboPounder.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget(null);
        }
        this.roboPounder.setAttacking(false);
        this.roboPounder.setAggressive(false);
        this.roboPounder.setSideSweeping(false);
        this.hasHit = false;
        this.animationProgress = 0;
    }
    
    public void animateAttack(LivingEntity target) {
    }

    @Override
    public void tick() {
        this.baseTick();
        LivingEntity target = this.roboPounder.getTarget();
        if (this.entity == null) return;
    //    AnimatableMonsterEntity e = this.entity;
   //     e = roboPounder;
        if (target != null) {
            //ChaosAwakens.debug("GOAL", this.animationProgress+" "+this.animationLength+" "+this.tickDelta+" "+this.animationProgress/this.animationLength);
            if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
                this.roboPounder.lookAt(target, 30.0F, 30.0F);
                if (this.roboPounder.distanceTo(target) >= getSideSweepAttackReachSq(roboPounder, target)) {
                	this.roboPounder.getTarget().moveTo(target.blockPosition(), 3.0F, 10.0F);
                //	this.entity.swing(Hand.MAIN_HAND);
                //	this.entity.doHurtTarget(target);
                //	this.hasHit = true;
                }
                this.roboPounder.swing(Hand.MAIN_HAND);
                this.roboPounder.doHurtTarget(target);
                this.hasHit = true;
            }

            if (this.animationProgress > this.animationLength) {
                this.animationProgress = 0;
                this.hasHit = false;
            }
            
            if (!canSideSweep() || !canUse()) {
            	stop();
            }
        }
    }
}

