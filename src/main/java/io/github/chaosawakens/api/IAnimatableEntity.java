package io.github.chaosawakens.api;

import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public interface IAnimatableEntity extends IAnimatable {
	
	/**
	 * The animation controller attached to the entity. Can be used to set animations outside of the <code>predicate(AnimationEvent<E> event)</code>
	 * method.
	 * @return The animation controller attached to the entity, CANNOT BE NULL!
	 */
	AnimationController<?> getController();
	
	/**
	 * A forced interval between each animation.
	 * @return The delay between animations.
	 */
	int animationInterval();
	
	/**
	 * This is the main predicate, in which all the animations are handled conditionally.
	 * If you need to, you can make more predicates for your use case(s).
	 * @param <E> IAnimatable type parameter.
	 * @param Event the animation event.
	 * @return a PlayState for each set animation.
	 */
	<E extends IAnimatable> PlayState predicate(AnimationEvent<E> event);
	
	/**
	 * Stops all other animations the entity is playing and forces it to play the specified animation.
	 * @param animationName Name of the animation specified in the blockbench file. Must match.
	 * @param shouldLoop Whether or not the specified animation should loop.
	 */
	default void forceSetAnimation(String animationName, boolean shouldLoop) {
		getController().markNeedsReload();
		getController().setAnimation(new AnimationBuilder().addAnimation(animationName, shouldLoop));
	}
	
	/**
	 * Stops all other animations the entity is playing and forces it to play the specified animation (once).
	 * @param animationName Name of the animation specified in the blockbench file. Must match.
	 */
	default void forceSetAnimation(String animationName) {
		forceSetAnimation(animationName, false);
	}
	
	/**
	 * Stops all other animations the entity is playing and forces it to play the specified animation (looping).
	 * @param animationName Name of the animation specified in the blockbench file. Must match.
	 */
	default void forceSetLoopingAnimation(String animationName) {
		forceSetAnimation(animationName, true);
	}
	
	default AnimationBuilder forceSetRepeatAnimationWithDelay(String animationName, double animationLength, int delay, int timesToRepeat) {
		AnimationBuilder builder = new AnimationBuilder();
		
		for (int i = 0; i < timesToRepeat; i++) {
			int delayR = delay;
			double animationLengthR = animationLength;
			
			
			if (--delayR <= 0 && --animationLengthR <= 0) {
				delayR = delay;
				animationLengthR = animationLength;
				builder.addAnimation(animationName, false);
			}
		}
		
		return builder;
	}
	
	/**
	 * Gets the current animation that the entity is playing. Can be null.
	 */
	default Animation getCurrentAnimation() {
		return getController().getCurrentAnimation();
	}
	
	/**
	 * Consecutively play several animations.
	 * @param transitioningTicks Amount of ticks between eatch animation.
	 * @param animationNames The names of the animations that will play (in order).
	 */
	@SuppressWarnings("unused")
	default void consecutivelyPlayAnimations(int transitioningTicks, String... animationNames) {
		for (String animation : animationNames) {
			if (getCurrentAnimation() != null) {
				AnimationState animState = getController().getAnimationState();
				
				if (animState.equals(AnimationState.Running)) continue;
				else {
					double transitioningTicksLength = getController().transitionLengthTicks;
					transitioningTicksLength = transitioningTicks;
					forceSetAnimation(animation);
				}
			}
		}
	}
	
	/**
	 * Clears the animation cache, which also clears every stored animation within the entity.
	 */
	default void resetAnimationState() {
		getController().clearAnimationCache();
	}
	
	/**
	 * Forcefully prevents the current animation from continuing.
	 */
	default void forceStopAnimation() {
		if (getCurrentAnimation() != null) {
			getController().setAnimation(null);
		}
	}
	
	/**
	 * Forcefully transitions the currently playing animation to another specified one.
	 * @param newAnimation The new animation to transition to.
	 * @param transitioningTicks The amount of ticks in the transitioning process.
	 */
	@SuppressWarnings("unused")
	default void forceTransitionAnimation(String newAnimation, int transitioningTicks) {
		if (getCurrentAnimation() != null) {
			double transitioningTicksLength = getController().transitionLengthTicks;
			transitioningTicksLength = transitioningTicks;
			
			AnimationState animState = getController().getAnimationState();
			animState = AnimationState.Transitioning;
			
			if (transitioningTicks > 0) {
				for (int i = 0; i < transitioningTicks; i++) {
					if (i == transitioningTicks) {
						forceSetAnimation(newAnimation);
						animState = AnimationState.Running;
					}
				}
			}
		}
	}
	
	
}
