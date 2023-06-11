package io.github.chaosawakens.api.animation;

import javax.annotation.Nullable;

import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;

/**
 * A shared interface across all {@code AnimationBuilder} types/classes. Provides an implementation for all common helper
 * methods, as well as the base methods which allow for server-sync execution.
 */
public interface IAnimationBuilder {
	WrappedAnimationController<? extends IAnimatableEntity> getWrappedController();
	
	IAnimatableEntity getOwner();
	
	EDefaultLoopTypes getLoopType();
	
	boolean isPlaying();
	boolean hasAnimationFinished();
	
	@Nullable
	Animation getAnimation();
	
	AnimationBuilder getBuilder();
	
	double getWrappedAnimProgress();
	double getWrappedAnimLength();
	
	void playAnimation(boolean forceAnim);
	void stopAnimation();
}