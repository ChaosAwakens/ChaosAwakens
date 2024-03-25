package io.github.chaosawakens.common.entity.ai.goals.hostile;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.misc.AOEHitboxEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.util.BlockPosUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AnimatableLeapGoal extends Goal {
	private final AnimatableMonsterEntity owner;
	private final Supplier<SingletonAnimationBuilder> leapAnim;
	private final Supplier<SingletonAnimationBuilder> midairAnim;
	private final Supplier<SingletonAnimationBuilder> landAnim;
	private final byte attackId;
	private final int probability;
	private final double leapPower;
	private final double minDistanceBlocks;
	private boolean hasDoneAOE = false;
	@Nullable
	private Predicate<AnimatableMonsterEntity> extraActivationConditions;
	@Nullable
	private Predicate<Block> blockBreakPredicate;
	@Nullable
	private Consumer<LivingEntity> actionOnLand;
	@Nullable
	protected Supplier<SoundEvent> leapSound;
	@Nullable
	protected Supplier<SoundEvent> landSound;
	protected float soundPitch = 1.0F;

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

	public AnimatableLeapGoal setSound(Supplier<SoundEvent> leapSound, Supplier<SoundEvent> landSound, float soundPitch) {
		this.leapSound = leapSound;
		this.landSound = landSound;
		this.soundPitch = soundPitch; //TODO Partition (newer versions probs, I do be lazy)

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

		if (leapSound != null) owner.playSound(leapSound.get(), 1.0F, soundPitch);
	}

	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
		this.hasDoneAOE = false;
	}

	@Override
	public void tick() {
		owner.getNavigation().stop();
		LivingEntity target = owner.getTarget();

		BlockPosUtil.destroyCollidingBlocksWithOffset(owner, owner.getRandom().nextBoolean(), 0.1F, 0F, 0.1F, blockBreakPredicate);

		if (owner.isPlayingAnimation(midairAnim.get()) && owner.isOnGround()) {
			owner.playAnimation(landAnim.get(), true);

			if (!hasDoneAOE) {
				AOEHitboxEntity aoeDamageEffect = new AOEHitboxEntity(owner.level, owner.blockPosition(), (float) (Math.ceil(leapPower) * 2), (float) (leapPower * 3), 10, 3, actionOnLand);

				owner.level.addFreshEntity(aoeDamageEffect);
				CAScreenShakeEntity.shakeScreen(owner.level, owner.position(), 80F, 0.235F, 10, 120);

				this.hasDoneAOE = true;

				if (landSound != null) owner.playSound(landSound.get(), 1.0F, soundPitch);
			}
		}

		if (target == null || !target.isAlive() || owner.isDeadOrDying()) return;

		if (owner.isPlayingAnimation(leapAnim.get())) {
			owner.setDeltaMovement(0, owner.getDeltaMovement().y, 0);
			owner.lookAt(Type.EYES, target.position());
		}

		BlockPos targetPos = target.blockPosition();

		if (owner.isOnGround()) {
			if (leapAnim.get().hasAnimationFinished()) {
				if (target != null) targetPos = BlockPosUtil.findHorizontalPositionBeyond(owner, targetPos, 6); // Update the target's cached position if not null

				Vector3d launchVec = new Vector3d((targetPos.getX() - owner.getX()), 0, (targetPos.getZ() - owner.getZ())).scale(0.146D);

				owner.setDeltaMovement(launchVec.x, leapPower, launchVec.z);
				owner.playAnimation(midairAnim.get(), true);
			}
		} else {
			if (owner.isPlayingAnimation(midairAnim.get())) {
				final Vector3d cachedLookAtPos = new Vector3d(targetPos.getX(), targetPos.getY(), targetPos.getZ());

				owner.lookAt(Type.EYES, cachedLookAtPos);
			}
		}
	}
}