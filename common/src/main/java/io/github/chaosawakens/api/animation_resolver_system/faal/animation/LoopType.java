package io.github.chaosawakens.api.animation_resolver_system.faal.animation;

@FunctionalInterface
public interface LoopType {

    <A extends Animatable> boolean getLoopBehaviour(A animatableOwner, double tickProgress, double animationLength, double transitionProgress);
}
