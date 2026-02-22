package io.github.chaosawakens.api.animation_resolver_system.faal.controller;

import com.mojang.datafixers.util.Either;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;

import java.util.LinkedList;
import java.util.Objects;

public class AnimationController<A extends Animatable> {
    protected final A animatable;
    protected double sequentialTickProgress = 0.0D;
    protected double sequentialTransitionProgress = 0.0D; // Clamped (0.0 - 1.0)
    protected ControllerState controllerState = ControllerState.IDLE;
    protected final ObjectArrayFIFOQueue<?> processingQueue = new ObjectArrayFIFOQueue<>();
    protected final ObjectArrayFIFOQueue<?> sequentialAnimQueue = new ObjectArrayFIFOQueue<>();
    protected Object curSequentialAnim;
    protected LinkedList<Object> asyncAnimations = new LinkedList<>();

    public AnimationController(A animatable, boolean asyncAnimations) {
        this.animatable = animatable;
    }

    protected void validateAndTickControllerState() {
        controllerState.tick(this);
    }

    public A getAnimatable() {
        return animatable;
    }

    public ControllerState getControllerState() {
        return controllerState;
    }

    public Either<Object, LinkedList<Object>> getCurrentAnimation() {
        return isAsync() ? Either.right(asyncAnimations) : Either.left(curSequentialAnim);
    }

    public boolean isAsync() {
        return asyncAnimations != null && !asyncAnimations.isEmpty();
    }

    public double getTickProgress(Object animation) {
        return isAsync() && getControllerState() == ControllerState.TICKING
                ? 0//asyncAnimations.getOrDefault(animation, 0)
                : Objects.equals(curSequentialAnim, animation)
                ? sequentialTickProgress
                : 0.0D;
    }
}
