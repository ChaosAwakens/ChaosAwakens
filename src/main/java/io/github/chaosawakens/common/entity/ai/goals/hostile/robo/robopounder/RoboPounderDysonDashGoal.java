package io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robopounder;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableMeleeGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.registry.CATags;
import io.github.chaosawakens.common.util.BlockPosUtil;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;

public class RoboPounderDysonDashGoal extends AnimatableMeleeGoal {
	private final double overshoot;
	private final ObjectArrayList<LivingEntity> affectedEntities = new ObjectArrayList<>();

	public RoboPounderDysonDashGoal(RoboPounderEntity owner, Supplier<? extends IAnimationBuilder> dashAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, int probability, int presetCooldown, double overshoot) {
		super(owner, dashAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, probability, presetCooldown);
		this.overshoot = overshoot;
	}
	
	public RoboPounderDysonDashGoal(RoboPounderEntity owner, Supplier<? extends IAnimationBuilder> dashAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double angleRange, int probability, int presetCooldown, double overshoot, Predicate<AnimatableMonsterEntity> activationConditions) {
		super(owner, dashAnim, attackId, actionPointTickStart, actionPointTickEnd, angleRange, presetCooldown, presetCooldown);
		this.overshoot = overshoot;
		this.extraActivationConditions = activationConditions;
	}
	
	@Override
	public boolean canUse() {
		if (curCooldown > 0) curCooldown--;
		
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), attackId) && !owner.isOnAttackCooldown() && curCooldown <= 0 && !owner.getTarget().isInvulnerable() && owner.isAlive() && !owner.isAttacking() && owner.getTarget().isAlive()
				&& ((owner.distanceTo(owner.getTarget()) > owner.getMeleeAttackReach(owner.getTarget()) && owner.distanceTo(owner.getTarget()) <= 16.0D))
				&& (extraActivationConditions != null ? extraActivationConditions.test(owner) && owner.getRandom().nextInt(probability) == 0 : owner.getRandom().nextInt(probability) == 0);
	}
	
	@Override
	public void start() {
		super.start();
		affectedEntities.clear();
	}
	
	@Override
	public void stop() {
		owner.stopAnimation(curAnim.get());
		super.stop();
		affectedEntities.clear();
	}
	
	@Override
	public void tick() {
		owner.getNavigation().stop();
		LivingEntity target = owner.getTarget();
		
		if (!ObjectUtil.performNullityChecks(false, target)) return;

		if (curAnim.get().getWrappedAnimProgress() < actionPointTickStart) owner.getLookControl().setLookAt(target, 30.0F, 30.0F);
		else EntityUtil.freezeEntityRotation(owner);
		
		if (MathUtil.isBetween(curAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickStart + 1)) EntityUtil.chargeTowards(owner, target, overshoot, overshoot, 0.19);
		BlockPosUtil.destroyCollidingBlocks(owner, owner.getRandom().nextBoolean(), (targetBlock) -> !targetBlock.is(CATags.Blocks.POUNDER_IMMUNE));
		
		double reach = owner.getMeleeAttackReach(target);
		List<LivingEntity> potentialAffectedTargets = EntityUtil.getAllEntitiesAround(owner, reach, reach, reach, reach);
		
		for (LivingEntity potentialAffectedTarget : potentialAffectedTargets) {			
			double targetAngle = MathUtil.getRelativeAngleBetweenEntities(owner, potentialAffectedTarget);
			double attackAngle = owner.yBodyRot % 360;
			
			if (targetAngle < 0) targetAngle += 360;
			if (attackAngle < 0) attackAngle += 360;
			
			double relativeHitAngle = targetAngle - attackAngle;
			float hitDistanceSqr = (float) (Math.sqrt((target.getZ() - owner.getZ()) * (target.getZ() - owner.getZ()) + (target.getX() - owner.getX()) * (target.getX() - owner.getX())) - owner.getBbWidth() / 2F);
			
			if (MathUtil.isBetween(curAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickEnd)) {
				if (hitDistanceSqr <= reach && MathUtil.isWithinAngleRestriction(relativeHitAngle, angleRange) && !affectedEntities.contains(potentialAffectedTarget)) {
					owner.doHurtTarget(potentialAffectedTarget);
					affectedEntities.add(potentialAffectedTarget);
				}
			}
		}
	}
}
