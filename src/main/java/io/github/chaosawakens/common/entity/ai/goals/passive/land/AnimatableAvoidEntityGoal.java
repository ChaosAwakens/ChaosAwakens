package io.github.chaosawakens.common.entity.ai.goals.passive.land;

import java.util.EnumSet;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class AnimatableAvoidEntityGoal<E extends LivingEntity> extends Goal {
	protected final AnimatableAnimalEntity owner;
	protected E entityToAvoid;
	protected final IAnimationBuilder panicAnim;
	protected final Class<E> entityToAvoidClass;
	protected final double maxAvoidDist;
	protected final double panicDistThreshold;
	@Nullable
	protected Consumer<AnimatableAnimalEntity> actionOnActivation;
	@Nullable
	protected Consumer<AnimatableAnimalEntity> actionOnDeactivation;
//	private final EntityPredicate entityToAvoidEvaluationPredicate;
	
	public AnimatableAvoidEntityGoal(AnimatableAnimalEntity owner, IAnimationBuilder panicAnim, Class<E> entityToAvoidClass, double maxAvoidDist, double panicDistThreshold) {
		this.owner = owner;
		this.panicAnim = panicAnim;
		this.entityToAvoidClass = entityToAvoidClass;
		this.maxAvoidDist = maxAvoidDist;
		this.panicDistThreshold = panicDistThreshold;
	//	this.entityToAvoidEvaluationPredicate = (new EntityPredicate()).range(maxAvoidDist).selector(p_i48859_9_.and(p_i48859_3_));;
		
		setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		return false;
	}
	
	@Override
	public boolean canContinueToUse() {
		return false;
	}
	
	@Override
	public void start() {
		
	}
	
	@Override
	public void stop() {
		
	}
	
	@Override
	public void tick() {
		
	}
}
