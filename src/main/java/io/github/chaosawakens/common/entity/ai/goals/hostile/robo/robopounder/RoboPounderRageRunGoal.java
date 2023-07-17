package io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robopounder;

import java.util.function.Supplier;

import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.hostile.robo.RoboPounderEntity;
import io.github.chaosawakens.common.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class RoboPounderRageRunGoal extends Goal {
	private final RoboPounderEntity owner;
	private final Supplier<SingletonAnimationBuilder> rageBeginAnim;
	private final Supplier<SingletonAnimationBuilder> rageRunAnim;
	private final Supplier<SingletonAnimationBuilder> rageCooldownAnim;
	private final Supplier<SingletonAnimationBuilder> rageRestartAnim;
	private final byte rageRunAttackId;
	private final int presetMaxCooldown;
	private int curCooldown;
	private final int probability;
	
	public RoboPounderRageRunGoal(RoboPounderEntity owner, Supplier<SingletonAnimationBuilder> rageBeginAnim, Supplier<SingletonAnimationBuilder> rageRunAnim, Supplier<SingletonAnimationBuilder> rageCooldownAnim, Supplier<SingletonAnimationBuilder> rageRestartAnim, byte rageRunAttackId, int presetMaxCooldown, int probability) {
		this.owner = owner;
		this.rageBeginAnim = rageBeginAnim;
		this.rageRunAnim = rageRunAnim;
		this.rageCooldownAnim = rageCooldownAnim;
		this.rageRestartAnim = rageRestartAnim;
		this.rageRunAttackId = rageRunAttackId;
		this.presetMaxCooldown = presetMaxCooldown;
		this.probability = probability;
	}
	
	public RoboPounderRageRunGoal(RoboPounderEntity owner, Supplier<SingletonAnimationBuilder> rageBeginAnim, Supplier<SingletonAnimationBuilder> rageRunAnim, Supplier<SingletonAnimationBuilder> rageCooldownAnim, Supplier<SingletonAnimationBuilder> rageRestartAnim, byte rageRunAttackId, int presetMaxCooldown) {
		this(owner, rageBeginAnim, rageRunAnim, rageCooldownAnim, rageRestartAnim, rageRunAttackId, presetMaxCooldown, 1);
	}
	
	public RoboPounderRageRunGoal(RoboPounderEntity owner, Supplier<SingletonAnimationBuilder> rageBeginAnim, Supplier<SingletonAnimationBuilder> rageRunAnim, Supplier<SingletonAnimationBuilder> rageCooldownAnim, Supplier<SingletonAnimationBuilder> rageRestartAnim, byte rageRunAttackId, Integer probability) {
		this(owner, rageBeginAnim, rageRunAnim, rageCooldownAnim, rageRestartAnim, rageRunAttackId, 400, probability);
	}
	
	public RoboPounderRageRunGoal(RoboPounderEntity owner, Supplier<SingletonAnimationBuilder> rageBeginAnim, Supplier<SingletonAnimationBuilder> rageRunAnim, Supplier<SingletonAnimationBuilder> rageCooldownAnim, Supplier<SingletonAnimationBuilder> rageRestartAnim, byte rageRunAttackId) {
		this(owner, rageBeginAnim, rageRunAnim, rageCooldownAnim, rageRestartAnim, rageRunAttackId, 400, 1);
	}

	@Override
	public boolean canUse() {
		return false;
	}
	
	@Override
	public boolean canContinueToUse() {
		return super.canContinueToUse();
	}
	
	@Override
	public boolean isInterruptable() {
		return super.isInterruptable();
	}
	
	@Override
	public void start() {
		owner.setAttackID(rageRunAttackId);
		owner.setRageRunning(true);
		owner.getNavigation().stop();
		owner.playAnimation(rageBeginAnim.get(), true);
	}
	
	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
		owner.setRageRunning(false);
	}
	
	private void createRageRunPath() {
		
	}
	
	@Override
	public void tick() {
		LivingEntity target = owner.getTarget();
		
		if (rageBeginAnim.get().isPlaying() || rageCooldownAnim.get().isPlaying() || rageRestartAnim.get().isPlaying()) {
			owner.getNavigation().stop();
			owner.setDeltaMovement(owner.getDeltaMovement().multiply(0, 1, 0));
			EntityUtil.freezeEntityRotation(owner);
		} else {
			if (rageBeginAnim.get().hasAnimationFinished()) {
				if (target != null) {
					
				} else owner.playAnimation(rageCooldownAnim.get(), true);
			}
			
			if (rageCooldownAnim.get().hasAnimationFinished()) owner.playAnimation(rageRestartAnim.get(), true);
		}
	}
}
