package io.github.chaosawakens.common.entity.ai.goals.hostile;

import java.util.EnumSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.misc.AOEHitboxEntity;
import io.github.chaosawakens.common.util.BlockPosUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class AnimatableLeapGoal extends Goal {
	private final AnimatableMonsterEntity owner;
	private final Supplier<SingletonAnimationBuilder> leapAnim;
	private final Supplier<SingletonAnimationBuilder> midairAnim;
	private final Supplier<SingletonAnimationBuilder> landAnim;
	private final byte attackId;
	private final int probability;
	private final double leapPower;
	private final double minDistanceBlocks;
	@Nullable
	private Predicate<AnimatableMonsterEntity> extraActivationConditions;
	@Nullable
	private Predicate<Block> blockBreakPredicate;
	@Nullable
	private Consumer<LivingEntity> actionOnLand;
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double leapPower, double minDistance, int probability, @Nullable Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this.owner = owner;
		this.leapAnim = leapAnim;
		this.midairAnim = midairAnim;
		this.landAnim = landAnim;
		this.attackId = attackId;
		this.leapPower = leapPower;
		this.minDistanceBlocks = minDistance;
		this.probability = probability;
		this.extraActivationConditions = extraActivationConditions;
		setFlags(EnumSet.of(Flag.TARGET, Flag.JUMP, Flag.LOOK));
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double leapPower, double minDistance) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, leapPower, minDistance, 1, null);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double leapPower, double minDistance, int probability) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, leapPower, minDistance, probability, null);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double leapPower, double minDistance, @Nullable Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, leapPower, minDistance, 1, extraActivationConditions);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, int probability, double minDistance, @Nullable Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, 30.0D, minDistance, probability, extraActivationConditions);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double minDistance, @Nullable Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, 30.0D, minDistance, 1, extraActivationConditions);
	}
	
	public AnimatableLeapGoal(AnimatableMonsterEntity owner, Supplier<SingletonAnimationBuilder> leapAnim, Supplier<SingletonAnimationBuilder> midairAnim, Supplier<SingletonAnimationBuilder> landAnim, byte attackId, double minDistance) {
		this(owner, leapAnim, midairAnim, landAnim, attackId, 30.0D, minDistance, null);
	}
	
	public AnimatableLeapGoal setBlockBreakPredicate(Predicate<Block> blockBreakPredicate) {
		this.blockBreakPredicate = blockBreakPredicate;
		
		return this;
	}
	
	public AnimatableLeapGoal setLandAction(Consumer<LivingEntity> actionOnLand) {
		this.actionOnLand = actionOnLand;
		
		return this;
	}

	@Override
	public boolean canUse() {
		return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), leapAnim.get(), midairAnim.get(), landAnim.get(), attackId) && owner.isAlive() && !owner.isAttacking() && owner.getTarget().isAlive() && owner.distanceTo(owner.getTarget()) >= minDistanceBlocks && (extraActivationConditions != null ? extraActivationConditions.test(owner) : owner.getRandom().nextInt(probability) == 0);
	}
	
	@Override
	public boolean canContinueToUse() {
		if (owner.isPlayingAnimation(leapAnim.get())) return ObjectUtil.performNullityChecks(false, owner, owner.getTarget(), leapAnim.get(), midairAnim.get(), landAnim.get(), attackId) && owner.getTarget().isAlive();
		return ObjectUtil.performNullityChecks(false, owner, leapAnim.get(), midairAnim.get(), landAnim.get(), attackId) && (owner.isOnGround() ? !owner.isDeadOrDying() : true) && !landAnim.get().hasAnimationFinished();
	}
	
	@Override
	public boolean isInterruptable() {
		return owner.isDeadOrDying() && owner.isOnGround();
	}
	
	@Override
	public void start() {
		owner.setAttackID(attackId);
		owner.playAnimation(leapAnim.get(), true);
		owner.getNavigation().stop();
	}
	
	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
	}
	
	@Override
	public void tick() {
		owner.getNavigation().stop();
		LivingEntity target = owner.getTarget();
		
		if (target != null && target.isAlive()) {
			if (owner.isPlayingAnimation(leapAnim.get())) {
				owner.setDeltaMovement(0, owner.getDeltaMovement().y, 0);
				owner.lookAt(target, 30F, 30F);
			}
			
			BlockPos targetPos = target.blockPosition();
			
			if (leapAnim.get().hasAnimationFinished()) {
				if (target == null || !target.isAlive() || owner.isDeadOrDying()) return;
				
				targetPos = target.blockPosition().immutable(); // Update the target's cached position if not null
				
				Vector3d curOwnerMovementVec = owner.getDeltaMovement();
				Vector3d launchVec = new Vector3d(targetPos.getX() - owner.getX(), 0, targetPos.getZ() - owner.getZ());
								
				if (launchVec.lengthSqr() > 1.0E-7D) launchVec = launchVec.normalize().scale(leapPower).add(curOwnerMovementVec.scale(leapPower / 1.36D));
				
				owner.setDeltaMovement(launchVec.x, leapPower, launchVec.z);
				owner.playAnimation(midairAnim.get(), true);
			}
			
			if (!owner.isOnGround() && owner.isPlayingAnimation(midairAnim.get())) {
				final Vector3d cachedLookAtPos = new Vector3d(targetPos.getX(), targetPos.getY(), targetPos.getZ());
				
				owner.lookAt(Type.EYES, cachedLookAtPos);
				BlockPosUtil.destroyCollidingBlocks(owner, owner.getRandom().nextBoolean(), blockBreakPredicate);
			}
			
			if (owner.isOnGround() || (MathUtil.getVerticalDistanceBetween(owner.blockPosition(), targetPos) <= 1.0D)) {
				owner.playAnimation(landAnim.get(), true);
				
		//		ObjectArrayList<BlockPos> affectedPositions = BlockPatternShape.CIRCLE.applyShape(owner.level, targetPos, 10, 1, true, false, null);
				
				AOEHitboxEntity aoeDamageEffect = new AOEHitboxEntity(owner.level, owner.blockPosition(), (float) (Math.ceil(leapPower) * 10), (float) (leapPower * 3), 20, 1, actionOnLand);
				
				owner.level.addFreshEntity(aoeDamageEffect);
			}
		}
	}
}