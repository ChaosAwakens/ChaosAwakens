package io.github.chaosawakens.api.animation_resolver_system.faal.controller;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animation;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;

public class AnimationController<A extends Animatable> {
    protected final A animatable;
    protected double sequentialTransitionProgress = 0.0D; // Clamped (0.0 - 1.0)
    protected ControllerState controllerState = ControllerState.IDLE;
    protected final ObjectArrayFIFOQueue<Animation> processingQueue = new ObjectArrayFIFOQueue<>();
    protected Animation curSequentialAnim;

    public AnimationController(A animatable) {
        this.animatable = animatable;
    }

    protected void validateAndTickControllerState() {
        controllerState.tick(this);
    }

    public A getOwner() {
        return animatable;
    }

    public ControllerState getControllerState() {
        return controllerState;
    }

    public Animation getCurrentAnimation() {
        return curSequentialAnim;
    }

    public double getTickProgress() {
        return curSequentialAnim != null
                ? curSequentialAnim.getTickProgress()
                : getControllerState().equals(ControllerState.TRANSITIONING)
                ? sequentialTransitionProgress
                : 0.0D;
    }
}
