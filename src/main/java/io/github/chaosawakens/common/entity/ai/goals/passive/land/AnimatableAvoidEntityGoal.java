package io.github.chaosawakens.common.entity.ai.goals.passive.land;

import java.util.EnumSet;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.base.AnimatableAnimalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class AnimatableAvoidEntityGoal<E extends LivingEntity> extends Goal {
	protected final AnimatableAnimalEntity owner;
	protected E entityToAvoid;
	protected final Class<E> entityToAvoidClass;
	private final double maxAvoidDist;
	@Nullable
	private Consumer<AnimatableAnimalEntity> actionOnActivation;
	@Nullable
	private Consumer<AnimatableAnimalEntity> actionOnDeactivation;
//	private final EntityPredicate entityToAvoidEvaluationPredicate;
	
	public AnimatableAvoidEntityGoal(AnimatableAnimalEntity owner, Class<E> entityToAvoidClass, double maxAvoidDist) {
		this.owner = owner;
		this.entityToAvoidClass = entityToAvoidClass;
		this.maxAvoidDist = maxAvoidDist;
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
