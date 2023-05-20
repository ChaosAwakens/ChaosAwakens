package io.github.chaosawakens.client.models.entity.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import io.github.chaosawakens.api.animation.ICAGeoModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public abstract class ExtendedAnimatedTickingGeoModel<E extends IAnimatableEntity> extends AnimatedTickingGeoModel<E> implements ICAGeoModel {
	protected abstract boolean shouldApplyHeadRot();
	
	protected abstract boolean shouldApplyChildScaling();
	
	@SuppressWarnings("rawtypes")
	@Override
	public void setLivingAnimations(E entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		
		if (shouldApplyHeadRot()) applyHeadRotations(getAnimationProcessor(), customPredicate);
		if (shouldApplyChildScaling()) setBabyScaling(getAnimationProcessor(), customPredicate);
	}
}
