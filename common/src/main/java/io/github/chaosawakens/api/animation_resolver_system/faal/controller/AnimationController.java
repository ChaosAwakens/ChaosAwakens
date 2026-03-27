package io.github.chaosawakens.api.animation_resolver_system.faal.controller;

import com.mojang.datafixers.util.Either;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animation;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;

import java.util.LinkedList;
import java.util.Objects;

public class AnimationController<A extends Animatable> {
    protected final A animatable;
    protected double sequentialTransitionProgress = 0.0D; // Clamped (0.0 - 1.0)
    protected ControllerState controllerState = ControllerState.IDLE;
    protected final ObjectArrayFIFOQueue<Animation> processingQueue = new ObjectArrayFIFOQueue<>();
    protected final ObjectArrayFIFOQueue<Animation> sequentialAnimQueue = new ObjectArrayFIFOQueue<>();
    protected Animation curSequentialAnim;
    protected LinkedList<Animation> asyncAnimations = new LinkedList<>();

    public AnimationController(A animatable, boolean asyncAnimations) {
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

    public Either<Animation, LinkedList<Animation>> getCurrentAnimation() {
        return isAsync() ? Either.right(asyncAnimations) : Either.left(curSequentialAnim);
    }

    public boolean isAsync() {
        return asyncAnimations != null && !asyncAnimations.isEmpty();
    }

    public double getTickProgress(Animation animation) {
        return isAsync() && getControllerState() == ControllerState.TICKING && asyncAnimations.contains(animation)
                ? asyncAnimations.get(asyncAnimations.indexOf(animation)).getTickProgress()
                : Objects.equals(curSequentialAnim, animation)
                ? curSequentialAnim.getTickProgress()
                : 0.0D;
    }
}
