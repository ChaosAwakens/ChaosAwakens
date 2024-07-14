package io.github.chaosawakens.common.entity.ai.goals.hostile;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class AnimatableShootGoal extends Goal {
	private final AnimatableMonsterEntity owner;
	protected final byte attackId;
	Supplier<SingletonAnimationBuilder> shootAnim;
	protected final BiFunction<AnimatableMonsterEntity, Vector3d, Entity> projectileFactory;
	protected final Vector3d projectileOffset;
	protected final double actionPointTickStart;
	protected final double actionPointTickEnd;
	protected final int fireRate;
	protected final int probability;
	protected final double minimumDistance;
	protected final int rotationDelay;
	protected boolean hasShotProjectile, hasStartedAnimations;
	protected float targetAngle;
	protected int delayCount;

	public AnimatableShootGoal(AnimatableMonsterEntity owner, byte attackId, Supplier<SingletonAnimationBuilder> shootAnim,
			BiFunction<AnimatableMonsterEntity, Vector3d, Entity> projectileFactory, Vector3d projectileOffset,
			double actionPointTickStart, double actionPointTickEnd, int fireRate, int probability, double minimumDistance, int rotationDelay) {
		this.owner = owner;
		this.shootAnim = shootAnim;
		this.fireRate = fireRate;
		this.attackId = attackId;
		this.actionPointTickStart = actionPointTickStart;
		this.actionPointTickEnd = actionPointTickEnd;
		this.projectileOffset = projectileOffset;
		this.probability = probability;
		this.projectileFactory = projectileFactory;
		this.minimumDistance = minimumDistance;
		this.rotationDelay = rotationDelay;
	}

	@Override
	public boolean canUse() {
		return ObjectUtil.performNullityChecks(false, owner.getTarget())
				&& owner.distanceTo(owner.getTarget()) >= minimumDistance && owner.canSee(owner.getTarget()) && !owner.getTarget().isInvulnerable()
				&& owner.isAlive() && !owner.isAttacking() && owner.getTarget().isAlive() && !owner.getTarget().isDeadOrDying()
				&& !owner.isOnAttackCooldown() && owner.getRandom().nextInt(probability) == 0;
	}
	
	@Override
	public boolean canContinueToUse() {
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget()) && !owner.isDeadOrDying() && !this.shootAnim.get().hasAnimationFinished();
	}

	@Override
	public void start() {
//		owner.setAttackID(attackId);
//		owner.getNavigation().stop();
//		owner.playAnimation(shootAnim.get(), true);
		Vector3d ownerPos = owner.position(), targetPos = owner.getTarget().position();
		this.targetAngle = (float) (Math.atan2(targetPos.z() - ownerPos.z(), targetPos.x() - ownerPos.x()) * 180 / Math.PI) - 90;
		
		this.delayCount = 0;
		this.hasShotProjectile = false;
		this.hasStartedAnimations = false;
	}

	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
		owner.stopAnimation(shootAnim.get());
		owner.setAttackCooldown(fireRate);

		this.delayCount = 0;
		this.hasShotProjectile = false;
		this.hasStartedAnimations = false;
	}

	@Override
	public void tick() {
		owner.stopAnimation(owner.getIdleAnim());
		owner.stopAnimation(owner.getWalkAnim());

		owner.getNavigation().stop();
		LivingEntity target = this.owner.getTarget();
		
		ChaosAwakens.debug("DIFF", MathHelper.degreesDifferenceAbs(targetAngle, owner.yHeadRot));
		ChaosAwakens.debug("AMBOS", targetAngle + " " + owner.yHeadRot);
		if (MathHelper.degreesDifferenceAbs(targetAngle, owner.yHeadRot) < 1.0f) {
			if (!this.hasStartedAnimations && delayCount >= rotationDelay) {
				owner.setAttackID(attackId);
				owner.playAnimation(shootAnim.get(), true);
				this.hasStartedAnimations = true;
			} else {
				if (MathUtil.isBetween(shootAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickEnd)) {
					if (!this.hasShotProjectile) {
						World world = this.owner.level;
						world.addFreshEntity(this.projectileFactory.apply(this.owner, this.projectileOffset));
						this.hasShotProjectile = true;
					}
				}
				delayCount++;
			}
		} else {
			if (target != null) owner.getLookControl().setLookAt(target, 30.0F, 30.0F);
		}
	}
}