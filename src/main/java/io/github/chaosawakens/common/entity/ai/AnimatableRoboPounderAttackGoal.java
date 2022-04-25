package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.robo.RoboPounderEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

//Tone
public class AnimatableRoboPounderAttackGoal<LE extends LivingEntity> extends AnimatableMeleeGoal
		implements IUtilityHelper {
	protected RoboPounderEntity roboPounder;
	protected Class<LE> targetType;
	protected LivingEntity target;
//	protected double distanceToTarget = getDistanceBetweenEntities(this.roboPounder, target);
	protected float checkInterval;
	protected EntityPredicate targetConditions;
	boolean isAlreadyAttacking;

	public AnimatableRoboPounderAttackGoal(AnimatableMonsterEntity entity, double animationLength, double attackBegin,
			double attackEnd) {
		super(entity, animationLength, attackBegin, attackEnd);
	}

	public AnimatableRoboPounderAttackGoal(RoboPounderEntity roboPounder, Class<LE> target,
			float checkForTargetInterval, double animationLength, double attackBegin, double attackEnd) {
		super(roboPounder, animationLength, attackBegin, attackEnd);
		this.roboPounder = roboPounder;
		this.targetType = target;
		this.checkInterval = checkForTargetInterval;
		this.setFlags(EnumSet.of(Flag.TARGET));
	}

	public AnimatableRoboPounderAttackGoal(RoboPounderEntity roboPounder, Class<LE> target,
			float checkForTargetInterval, @Nullable Predicate<LivingEntity> predicate, double animationLength,
			double attackBegin, double attackEnd) {
		super(roboPounder, animationLength, attackBegin, attackEnd);
		this.roboPounder = roboPounder;
		this.targetType = target;
		this.checkInterval = checkForTargetInterval;
		this.setFlags(EnumSet.of(Flag.TARGET));
		this.targetConditions = (new EntityPredicate()).range(this.getFollowRange()).selector(predicate);
	}

	protected static double getRageAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
		return attacker.getBbWidth() * 6F * attacker.getBbWidth() * 6F + target.getBbWidth();
	}

	protected static double getSideSweepAttackReachSq(AnimatableMonsterEntity attacker, LivingEntity target) {
		return attacker.getBbWidth() * 3F * attacker.getBbWidth() * 3F + target.getBbWidth();
	}

	public boolean canPunch() {
		return roboPounder.getSideSweep() == false && roboPounder.getRageRunning() == false
				&& roboPounder.getRageMode() == false && canHit();
	}

	public boolean canSideSweep() {
		return roboPounder.getPunching() == false && roboPounder.getRageRunning() == false
				&& roboPounder.getRageMode() == false && canHit();
	}

	public boolean canRageRun() {
		return roboPounder.getPunching() == false && roboPounder.getSideSweep() == false && enableRageMode()
				&& canHit();
	}

	public boolean isAnimationFinished() {
		return this.animationProgress > this.animationLength;
	}

	public boolean canHit() {
		return isAnimationFinished() == true;
	}

	public boolean enableRageMode() {
		boolean canHitt = canHit();
		if (this.attackPredicate.apply(this.animationProgress, this.animationLength) && canHitt) {
			roboPounder.setRageMode(true);
			for (int progress = 0; progress < this.animationLength; progress++) {
				roboPounder.setSpeed(0);
				canHitt = false;
			}
			canHitt = false;
			return true;
		}
		if (isAnimationFinished()) {
			this.animationProgress = 0;
			canHitt = true;
		}
		return false;
	}

	public boolean isValidForUse() {
		if (target == null)
			return false;
		if (roboPounder == null)
			return false;
		if (checkInterval <= 1)
			return false;

		if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
			roboPounder.setAttacking(false);
			roboPounder.setRageMode(false);
			roboPounder.setRageRunning(false);
			roboPounder.setPunching(false);
			roboPounder.setSideSweeping(false);
			return false;
		}

		if (target.isSpectator())
			return false;

		double distance = this.roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());
		if (distance <= AnimatableGoal.getAttackReachSq(roboPounder, target)) {
			// roboPounder.setAggressive(true);
			// roboPounder.setPunching(true);
			// roboPounder.setRageMode(false);
			// roboPounder.setRageRunning(false);
			// roboPounder.setSideSweeping(false);
			return true;
		}

		if (distance <= getRageAttackReachSq(roboPounder, target)) {
			// roboPounder.setAggressive(true);
			// roboPounder.setRageRunning(true);
			// roboPounder.setPunching(false);
			// roboPounder.setSideSweeping(false);
			// roboPounder.lookAt(target, 30.0F, 30.0F);
			// roboPounder.moveTo(target.blockPosition(), 10.0F, 10.0F);
			return true;
		}

		if (distance > this.getFollowRange()) {
			// roboPounder.setAttacking(false);
			// roboPounder.setRageMode(false);
			// roboPounder.setRageRunning(false);
			// roboPounder.setPunching(false);
			// roboPounder.setSideSweeping(false);
			return false;
		}

		return false;

	}

	public boolean isValidForRageRun() {
		double distance = this.roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());

		if (distance <= getRageAttackReachSq(roboPounder, target) && canRageRun() && !canSideSweep() && !canPunch()) {
			roboPounder.setAggressive(true);
			roboPounder.setRageRunning(true);
			roboPounder.setPunching(false);
			roboPounder.setSideSweeping(false);
			roboPounder.lookAt(target, 30.0F, 30.0F);
			roboPounder.moveTo(target.blockPosition(), 10.0F, 10.0F);
			return true;
		}

		return false;
	}

	public boolean isValidForPunch() {
		double distance = this.roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());

		if (distance <= AnimatableGoal.getAttackReachSq(roboPounder, target) && canPunch() && !canSideSweep()
				&& !canRageRun()) {
			roboPounder.setAggressive(true);
			roboPounder.setPunching(true);
			roboPounder.setRageMode(false);
			roboPounder.setRageRunning(false);
			roboPounder.setSideSweeping(false);
			return true;
		}

		return false;
	}

	public boolean isValidForSideSweep() {
		double distance = this.roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());

		if (distance <= getSideSweepAttackReachSq(roboPounder, target) && canSideSweep() && !canPunch()
				&& !canRageRun()) {
			roboPounder.setAggressive(true);
			roboPounder.setSideSweeping(true);
			roboPounder.setPunching(false);
			roboPounder.setRageMode(false);
			roboPounder.setRageRunning(false);
			return true;
		}

		return false;
	}

	@Override
	public boolean canUse() {
		return isValidForUse();
	}

	@Override
	public boolean canContinueToUse() {
		if (Math.random() <= 0.1)
			return false;

		return isValidForUse();
	}

	@Override
	public void start() {
		super.start();
		this.roboPounder.setTarget(roboPounder.getTarget());
		this.animationProgress = 0;
		this.roboPounder.setAggressive(true);
		this.roboPounder.setAttacking(true);
	}

	@Override
	public void stop() {
		super.stop();
		this.roboPounder.setRageMode(false);
		this.roboPounder.setRageRunning(false);
		this.roboPounder.setSideSweeping(false);
		this.roboPounder.setPunching(false);
		this.roboPounder.setTarget(null);
		this.roboPounder.setAggressive(false);
		this.animationProgress = 0;
	}

	@Override
	public void tick() {
		super.baseTick();
		if (roboPounder != null) {
			if (target != null) {
				if (Math.random() >= 10 && isValidForRageRun()) {
					roboPounder.setRageRunning(true);
					roboPounder.lookAt(target, 30.0F, 30.0F);
					roboPounder.moveTo(target.blockPosition(), 1.0F, 1.0F);
				}

				if (Math.random() >= 15 && isValidForPunch()) {
					roboPounder.setPunching(true);
					roboPounder.lookAt(target, 30.0F, 30.0F);
					double distance = this.roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());
					if (distance >= AnimatableGoal.getAttackReachSq(roboPounder, target)) {
						roboPounder.moveTo(target.blockPosition(), 1.0F, 1.0F);
					}
				}

				if (Math.random() >= 20 && isValidForSideSweep()) {
					roboPounder.setSideSweeping(true);
					roboPounder.lookAt(target, 30.0F, 30.0F);
					double distance = this.roboPounder.distanceToSqr(target.getX(), target.getY(), target.getZ());
					if (distance >= getSideSweepAttackReachSq(roboPounder, target)) {
						roboPounder.moveTo(target.blockPosition(), 1.0F, 1.0F);
					}
				}
			}
		}
	}

	/*
	 * @Override public void tick() { super.tick(); ++this.checkInterval; if
	 * (this.checkInterval >= 0 && target != null && this.roboPounder != null) {
	 * 
	 * @SuppressWarnings("unused") LivingEntity target2 =
	 * this.roboPounder.getTarget(); target2 = target;
	 * //this.roboPounder.setAggressive(true); target =
	 * this.roboPounder.getTarget(); boolean hasTarget = target != null; boolean
	 * isValidForRage = getDistanceBetweenEntities(roboPounder, target) >= 10.0D ||
	 * getDistanceBetweenEntities(this.roboPounder, target) <= 6.0D && target !=
	 * null; boolean isAggressive = this.roboPounder.isAggressive(); boolean
	 * isValidForFirstPunch = getDistanceBetweenEntities(roboPounder, target) <=
	 * 2.0D && target != null && target.getHealth() <= 5.0F; boolean
	 * isValidForSecondPunch = getDistanceBetweenEntities(roboPounder, target) <=
	 * 4.0D && this.target != null; double maxStopTime = 2.0D;
	 * this.roboPounder.setRageMode(false); this.roboPounder.setRageRunning(false);
	 * isAggressive = false; assert target != null;
	 * 
	 * 
	 * if (hasTarget) { //Rage attack, including start/stop animations if
	 * (isValidForRage) { this.roboPounder.setRageMode(true);
	 * 
	 * for (double stopTime = 0.0D; stopTime < maxStopTime; stopTime++) {
	 * this.roboPounder.setSpeed(0.0F); this.roboPounder.setMoving(false);
	 * this.roboPounder.setRageMode(true); }
	 * 
	 * if (this.roboPounder.getSpeed() == 0.0F) { this.roboPounder.setSpeed((float)
	 * ((float) this.roboPounder.getAttributeValue(Attributes.MOVEMENT_SPEED) *
	 * 2.0D)); }
	 * 
	 * assert this.roboPounder.getTarget() != null;
	 * this.roboPounder.lookAt(this.roboPounder.getTarget(), 1.0F, 1.0F);
	 * isAggressive = true; this.roboPounder.setRageRunning(true);
	 * this.roboPounder.moveTo(this.roboPounder.getTarget().getX(),
	 * this.roboPounder.getTarget().getY(), this.roboPounder.getTarget().getZ());
	 * this.roboPounder.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.
	 * roboPounder.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0D);
	 * this.roboPounder.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(this.
	 * roboPounder.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 2.0D);
	 * this.roboPounder.getAttribute(Attributes.ARMOR).setBaseValue(this.roboPounder
	 * .getAttributeValue(Attributes.ARMOR) * 2.0D);
	 * this.roboPounder.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(
	 * this.roboPounder.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * 2.0D);
	 * } else { assert target != null;
	 * this.roboPounder.lookAt(this.roboPounder.getTarget(), 1.0F, 1.0F);
	 * isAggressive = false;
	 * 
	 * if (isValidForFirstPunch) {
	 * this.roboPounder.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(this.
	 * roboPounder.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 2.0D);
	 * this.roboPounder.moveTo(this.roboPounder.getTarget().getX(),
	 * this.roboPounder.getTarget().getY(), this.roboPounder.getTarget().getZ());
	 * isAggressive = true; }
	 * 
	 * if (isValidForSecondPunch) {
	 * this.roboPounder.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.
	 * roboPounder.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0D);
	 * this.roboPounder.moveTo(this.roboPounder.getTarget().getX(),
	 * this.roboPounder.getTarget().getY(), this.roboPounder.getTarget().getZ());
	 * isAggressive = true; } } } } else { findTarget();
	 * this.roboPounder.setAggressive(false); } }
	 * 
	 * @SuppressWarnings("unused") public void attack() { target =
	 * this.roboPounder.getTarget(); boolean hasTarget = target != null; boolean
	 * isValidForRage = getDistanceBetweenEntities(roboPounder, target) >= 10.0D ||
	 * getDistanceBetweenEntities(this.roboPounder, target) <= 6.0D && target !=
	 * null; boolean isAggressive = this.roboPounder.isAggressive(); boolean
	 * isValidForFirstPunch = getDistanceBetweenEntities(roboPounder, target) <=
	 * 2.0D && target != null && target.getHealth() <= 5.0F; boolean
	 * isValidForSecondPunch = getDistanceBetweenEntities(roboPounder, target) <=
	 * 4.0D && this.target != null; double maxStopTime = 2.0D;
	 * this.roboPounder.setRageMode(false); this.roboPounder.setRageRunning(false);
	 * isAggressive = false; assert target != null;
	 * 
	 * 
	 * if (hasTarget) { //Rage attack, including start/stop animations if
	 * (isValidForRage) { this.roboPounder.setRageMode(true);
	 * 
	 * for (double stopTime = 0.0D; stopTime < maxStopTime; stopTime++) {
	 * this.roboPounder.setSpeed(0.0F); this.roboPounder.setMoving(false);
	 * this.roboPounder.setRageMode(true); }
	 * 
	 * if (this.roboPounder.getSpeed() == 0.0F) { this.roboPounder.setSpeed((float)
	 * ((float) this.roboPounder.getAttributeValue(Attributes.MOVEMENT_SPEED) *
	 * 2.0D)); }
	 * 
	 * assert this.roboPounder.getTarget() != null;
	 * this.roboPounder.lookAt(this.roboPounder.getTarget(), 1.0F, 1.0F);
	 * isAggressive = true; this.roboPounder.setRageRunning(true);
	 * this.roboPounder.moveTo(this.roboPounder.getTarget().getX(),
	 * this.roboPounder.getTarget().getY(), this.roboPounder.getTarget().getZ());
	 * this.roboPounder.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.
	 * roboPounder.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0D);
	 * this.roboPounder.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(this.
	 * roboPounder.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 2.0D);
	 * this.roboPounder.getAttribute(Attributes.ARMOR).setBaseValue(this.roboPounder
	 * .getAttributeValue(Attributes.ARMOR) * 2.0D);
	 * this.roboPounder.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(
	 * this.roboPounder.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * 2.0D);
	 * } else { assert target != null;
	 * this.roboPounder.lookAt(this.roboPounder.getTarget(), 1.0F, 1.0F);
	 * isAggressive = false;
	 * 
	 * if (isValidForFirstPunch) {
	 * this.roboPounder.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(this.
	 * roboPounder.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 2.0D);
	 * this.roboPounder.moveTo(this.roboPounder.getTarget().getX(),
	 * this.roboPounder.getTarget().getY(), this.roboPounder.getTarget().getZ());
	 * isAggressive = true; }
	 * 
	 * if (isValidForSecondPunch) {
	 * this.roboPounder.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.
	 * roboPounder.getAttributeValue(Attributes.ATTACK_DAMAGE) * 2.0D);
	 * this.roboPounder.moveTo(this.roboPounder.getTarget().getX(),
	 * this.roboPounder.getTarget().getY(), this.roboPounder.getTarget().getZ());
	 * isAggressive = true; } } } }
	 */

	protected void findTarget() {
		if (this.targetType != PlayerEntity.class && this.targetType != ServerPlayerEntity.class) {
			this.target = this.roboPounder.level.getNearestLoadedEntity(this.targetType, this.targetConditions,
					this.roboPounder, this.roboPounder.getX(), this.roboPounder.getEyeY(), this.roboPounder.getZ(),
					this.getTargetSearchArea(this.getFollowRange()));
		} else {
			this.target = this.roboPounder.level.getNearestPlayer(this.targetConditions, this.roboPounder,
					this.roboPounder.getX(), this.roboPounder.getEyeY(), this.roboPounder.getZ());
		}
	}

	protected AxisAlignedBB getTargetSearchArea(double area) {
		return this.roboPounder.getBoundingBox().inflate(area, 12.0D, area);
	}

	protected double getFollowRange() {
		return this.roboPounder.getAttributeValue(Attributes.FOLLOW_RANGE);
	}

}
