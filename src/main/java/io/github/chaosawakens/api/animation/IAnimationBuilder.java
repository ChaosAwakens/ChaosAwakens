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
	
	/**
	 * Gets the owner {@link WrappedAnimationController} of this {@code IAnimationBuilder} instance.
	 * @return The owner {@link WrappedAnimationController}.
	 */
	WrappedAnimationController<? extends IAnimatableEntity> getWrappedController();
	
	/**
	 * Sets the controller this {@code IAnimationBuilder} instance should use. Should not be null.
	 * @param targetWrappedController The controller to use for playing this animation.
	 * @return {@code this} (builder method).
	 */
	IAnimationBuilder setWrappedController(WrappedAnimationController<? extends IAnimatableEntity> targetWrappedController);
	/**
	 * Sets the animation speed multiplier for this {@code IAnimationBuilder} instance.
	 * @param animSpeedMultiplier The animation speed multiplier.
	 * @return {@code this} (builder method).
	 */
	IAnimationBuilder setAnimSpeed(double animSpeedMultiplier);
	
	/**
	 * Gets the owner {@link IAnimatableEntity} of this {@code IAnimationBuilder} instance.
	 * @return The owner {@link IAnimatableEntity}.
	 */
	IAnimatableEntity getOwner();
	
	/**
	 * Gets the {@linkplain EDefaultLoopTypes loop type} of this {@code IAnimationBuilder} instance.
	 * @return The {@linkplain EDefaultLoopTypes loop type}.
	 */
	EDefaultLoopTypes getLoopType();
	
	boolean isPlaying();
	boolean hasAnimationFinished();
	
	@Nullable
	Animation getAnimation();
	
	String getAnimationName();
	
	/**
	 * Gets the owner {@link WrappedAnimationController}'s {@link ExpandedAnimationState} while this {@code IAnimationBuilder} instance is running.
	 * @return The {@link ExpandedAnimationState} of the owner {@link WrappedAnimationController}.
	 */
	ExpandedAnimationState getAnimationState();
	
	AnimationBuilder getBuilder();
	
	double getWrappedAnimProgress();
	double getWrappedAnimLength();
	/**
	 * Gets the animation's speed multiplier.
	 * @return The animation's speed multiplier.
	 */
	double getWrappedAnimSpeed();
	
	/**
	 * The main method which handles starting and playing an animation (client-side).
	 * @param forceAnim Whether or not the animation cache of {@link #getWrappedController()} should be cleared before this {@code IAnimationBuilder} 
	 * instance is played.
	 */
	void playAnimation(boolean forceAnim);
	void stopAnimation();
}