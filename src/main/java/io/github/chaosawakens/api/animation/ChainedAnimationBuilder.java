package io.github.chaosawakens.api.animation;

import software.bernie.geckolib3.core.builder.AnimationBuilder;

//TODO
/**
 * Wrapper class for {@link AnimationBuilder} which allows for the instantiation of multiple chained animations. It also provides additional 
 * functionality, such as randomization of chained animation selection during a chaining sequence. It's important to note that this does not mean
 * that any rough transitions between chained animations will be handled by this builder. Animation transition behaviour will remain as-is, 
 * and Geckolib will still handle the animation transitioning itself.
 */
public abstract class ChainedAnimationBuilder implements IAnimationBuilder {
	
}
