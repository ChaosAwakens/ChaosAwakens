package io.github.chaosawakens.common.entity.ai.goals.boss.robo.robojeffery;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.api.animation.SingletonAnimationBuilder;
import io.github.chaosawakens.common.entity.ai.goals.hostile.AnimatableAOEGoal;
import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.entity.boss.robo.RoboJefferyEntity;
import io.github.chaosawakens.common.entity.misc.CAScreenShakeEntity;
import io.github.chaosawakens.common.entity.misc.JefferyShockwaveEntity;
import io.github.chaosawakens.common.registry.CADamageSources;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class RoboJefferyShockwaveGoal extends AnimatableAOEGoal {
	private JefferyShockwaveEntity jefferyShockwave;
	private final ObjectArrayList<LivingEntity> affectedEntities = new ObjectArrayList<>();
	private boolean hasSpawnedShockwave = false;

	public RoboJefferyShockwaveGoal(RoboJefferyEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int amountThreshold, int probability, int presetCooldown) {
		super(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, amountThreshold, probability, presetCooldown);
	}
	
	public RoboJefferyShockwaveGoal(RoboJefferyEntity owner, Supplier<SingletonAnimationBuilder> aoeAnim, byte attackId, double actionPointTickStart, double actionPointTickEnd, double aoeRange, int presetCooldown, Predicate<AnimatableMonsterEntity> extraActivationConditions) {
		super(owner, aoeAnim, attackId, actionPointTickStart, actionPointTickEnd, aoeRange, presetCooldown, extraActivationConditions);
	}
	
	@Override
	public void start() {
		owner.setAttackID(attackId);
		owner.getNavigation().stop();

		Supplier<? extends IAnimationBuilder> targetAnim = animationsToPick != null && !animationsToPick.isEmpty() ? animationsToPick.get(owner.level.getRandom().nextInt(animationsToPick.size())) : aoeAnim;

		owner.playAnimation(targetAnim.get(), true);

		if (soundOnStart != null) owner.playSound(soundOnStart.get(), 1.0F, soundPitch);

		this.jefferyShockwave = new JefferyShockwaveEntity(owner.level, owner.blockPosition(), (float) aoeRange, 3.7F, 49, 2, null);
		
		jefferyShockwave.setActionOnIntersection((target) -> {
			if (jefferyShockwave != null && !affectedEntities.contains(target) && owner != target && !owner.isAlliedTo(target) && EntityPredicates.ATTACK_ALLOWED.test(target) && owner.getClass() != target.getClass()) {
				target.hurt(CADamageSources.SHOCKWAVE, 35.0F - owner.distanceTo(target));
				
				double targetAngle = (MathUtil.getAngleBetweenEntities(jefferyShockwave, target) + 90) * Math.PI / 180; //TODO Dist calc
				double kbMultiplier = target instanceof PlayerEntity ? -Math.min(owner.getAttackDamage() / 5, 100.0D) : -Math.min(owner.getAttackDamage() / 5, 100.0D) / 2.1D;
				
				target.setDeltaMovement(kbMultiplier * Math.cos(targetAngle), target.getDeltaMovement().normalize().y + Math.min(owner.getAttackDamage() / 10, 2.3D), kbMultiplier * Math.sin(targetAngle));
				target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 62, 4));
				
				affectedEntities.add(target);
			}
		});		
		this.hasSpawnedShockwave = false;
		this.curAnim = targetAnim;
	}
	
	@Override
	public void stop() {
		owner.setAttackID((byte) 0);
		owner.setAttackCooldown(10);
		owner.stopAnimation(curAnim.get());
		affectedEntities.clear();

		this.curAnim = null;
		this.curCooldown = presetCooldown;
		this.jefferyShockwave = null;
		
		this.hasSpawnedShockwave = false;
	}
	
	@Override
	public void tick() {
		owner.getNavigation().stop();
		owner.setDeltaMovement(0, owner.getDeltaMovement().y, 0);
		if (shouldFreezeRotation || curAnim.get().getWrappedAnimProgress() >= actionPointTickStart) EntityUtil.freezeEntityRotation(owner);
		else if (owner.getTarget() != null) owner.getLookControl().setLookAt(owner.getTarget(), 30.0F, 30.0F);

		if (MathUtil.isBetween(curAnim.get().getWrappedAnimProgress(), actionPointTickStart, actionPointTickStart + 1)) {
			if (!hasSpawnedShockwave) {
				owner.level.playSound(null, owner.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundCategory.HOSTILE, 1.0F, owner.getRandom().nextFloat());
				CAScreenShakeEntity.shakeScreen(owner.level, owner.position(), (float) aoeRange * 22, (float) Math.min(aoeRange / 10, 0.8D), 7, 60);
				owner.level.addFreshEntity(jefferyShockwave);
				this.hasSpawnedShockwave = true;
			}
		}
	}
}
