package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;
import java.util.function.BiFunction;

import io.github.chaosawakens.api.IUtilityHelper;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class AnimatableLeapAtTargetGoal extends LeapAtTargetGoal implements IUtilityHelper {
	protected AnimatableAnimalEntity entity;
	private LivingEntity target;
	float yf;
	@SuppressWarnings("unused")
	private final double animationLength;
	@SuppressWarnings("unused")
	private final BiFunction<Double, Double, Boolean> attackPredicate;
	@SuppressWarnings("unused")
	private boolean hasHit;

	public AnimatableLeapAtTargetGoal(AnimatableAnimalEntity entity, float f, double animationLength, double attackBegin, double attackEnd) {
		super(entity, f);
		this.entity = entity;
		yf = f;
		this.animationLength = animationLength;
		this.attackPredicate = (progress, length) -> attackBegin < progress / (length) && progress / (length) < attackEnd;
		this.setFlags(EnumSet.of(Goal.Flag.LOOK));
	}

	@SuppressWarnings("unused")
	private static boolean checkIfValid(AnimatableMeleeGoal goal, AnimatableAnimalEntity attacker, LivingEntity target) {
		if (target == null) return false;
		if (target.isAlive() && !target.isSpectator()) {
			if (target instanceof PlayerEntity && ((PlayerEntity) target).isCreative()) {
				attacker.setAttacking(false);
				return false;
			}
			double distance = goal.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
			if (distance <= AnimatableGoal.getAttackReachSq(attacker, target)) return true;
		}
		attacker.setAttacking(false);
		return false;
	}

	@Override
	public boolean canUse() {
		if (this.entity.isVehicle()) {
			return false;
		} else {
			this.target = this.entity.getTarget();
			if (this.target == null) {
				return false;
			} else {
				double d0 = this.entity.distanceToSqr(this.target);
				if (!(d0 < 4.0D) && !(d0 > 16.0D)) {
					if (!this.entity.isOnGround()) {
						return false;
					} else {
						return this.entity.getRandom().nextInt(5) == 0;
					}
				} else {
					return false;
				}
			}
		}
	}

	@Override
	public boolean canContinueToUse() {
		return !this.entity.isOnGround();
	}

	@Override
	public void start() {
		Vector3d vector3d = this.entity.getDeltaMovement();
		Vector3d vector3d1 = new Vector3d(this.target.getX() - this.entity.getX(), 0.0D, this.target.getZ() - this.entity.getZ());
		if (vector3d1.lengthSqr() > 1.0E-7D) vector3d1 = vector3d1.normalize().scale(0.4D).add(vector3d.scale(0.2D));

		this.entity.setDeltaMovement(vector3d1.x, (double) this.yf, vector3d1.z);
	}

	public double getDistanceBetween(BlockPos a, BlockPos b) {
		return IUtilityHelper.getDistanceBetween(a, b);
	}
}
