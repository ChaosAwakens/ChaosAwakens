package io.github.chaosawakens.api.animation;

import javax.annotation.Nullable;

import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;

/**
 * A shared interface across all {@code AnimationBuilder} types/classes. Provides an implementation for all common helper methods, as well as the base 
 * methods which allow for server-sync execution.
 */
public interface IAnimationBuilder {
	AnimationController<? extends IAnimatableEntity> getController();
	
	IAnimatableEntity getOwner();
	
	EDefaultLoopTypes getLoopType();
	
	boolean isPlaying();
	boolean hasAnimationFinished();
	
	@Nullable
	Animation getAnimation();
	
	AnimationBuilder getBuilder();
	
	double getProgressTicks();
	double getLengthTicks();
	
	default double getProgressSeconds() {
		return getProgressTicks() / 20;
	}
	
	default double getLengthSeconds() {
		return getLengthTicks() / 20;
	}
	
	void playAnimation(boolean forceAnim);
	void stopAnimation();
	void tickAnim();
}