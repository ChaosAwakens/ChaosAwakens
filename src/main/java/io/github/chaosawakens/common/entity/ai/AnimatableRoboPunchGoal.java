package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;


import java.util.function.BiFunction;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.robo.RoboPounderEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;

public class AnimatableRoboPunchGoal extends AnimatableGoal {

    protected final double animationLength;
    protected final BiFunction<Double, Double, Boolean> attackPredicate;
    private boolean hasHit;
    public static float punchingTicks;

    /**
     * @param entity          Attacking entity
     * @param animationLength
     */
    public AnimatableRoboPunchGoal(AnimatableMonsterEntity entity, RoboPounderEntity robo, double animationLength, double attackBegin, double attackEnd) {
        this.entity = entity;
        this.roboPounder = robo;
        this.animationLength = animationLength;
        this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }
    
    public static double getAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
        return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth(); //return attacker.getBbWidth() * 2F * attacker.getBbWidth() * 2F + target.getBbWidth() / 2;
    }

    private static boolean checkIfValid(AnimatableRoboPunchGoal animatableRoboPunchGoal, RoboPounderEntity attacker, LivingEntity target) {
        if (target == null) return false;
        if (target.isAlive() && !target.isSpectator()) {
            if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
                attacker.setAttacking(false);
                return false;
            }
            double distance = animatableRoboPunchGoal.roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance <= getAttackReachSq(attacker, target)) return true;
        }
        attacker.setAttacking(false);
        return false;
    }

    @Override
    public boolean canUse() {
        if (Math.random() <= 0.1) return false;

        return AnimatableRoboPunchGoal.checkIfValid(this, roboPounder, this.roboPounder.getTarget()) && !this.roboPounder.getNavigation().isDone();
    }

    @Override
    public boolean canContinueToUse() {
        if (Math.random() <= 0.1) return true;

        return AnimatableRoboPunchGoal.checkIfValid(this, roboPounder, this.roboPounder.getTarget()) && !this.roboPounder.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.roboPounder.setAttacking(true);
        this.roboPounder.setAggressive(true);
        this.roboPounder.setPunching(true);
        this.animationProgress = 0;
        AnimatableRoboPunchGoal.punchingTicks = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.roboPounder.getTarget();
        if (!EntityPredicates.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.entity.setTarget(null);
        }
        this.roboPounder.setAttacking(false);
        this.roboPounder.setAggressive(false);
        this.roboPounder.setPunching(false);
        this.hasHit = false;
        this.animationProgress = 0;
        AnimatableRoboPunchGoal.punchingTicks = 0;
    }
    
    public void animateAttack(LivingEntity target) {
    	
    }

    @Override
    public void tick() {
        this.baseTick();
        LivingEntity target = this.roboPounder.getTarget();
        if (target != null) {
            //ChaosAwakens.debug("GOAL", this.animationProgress+" "+this.animationLength+" "+this.tickDelta+" "+this.animationProgress/this.animationLength);
            if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && !this.hasHit) {
                this.roboPounder.lookAt(target, 30.0F, 30.0F);
                if (this.roboPounder.distanceTo(target) >= 12.0F) {
                	this.roboPounder.getTarget().moveTo(target.blockPosition(), 3.0F, 10.0F);
                	this.roboPounder.getLookControl().setLookAt(target, 30.0F, 30.0F);
                }
          //      ChaosAwakens.LOGGER.debug(AnimatableRoboPunchGoal.punchingTicks);
                this.roboPounder.swing(Hand.MAIN_HAND);
                this.roboPounder.doHurtTarget(target);
                this.hasHit = true;
            }

            if (this.animationProgress > this.animationLength) {
                this.animationProgress = 0;
                this.hasHit = false;
            }
        }
    }
}
