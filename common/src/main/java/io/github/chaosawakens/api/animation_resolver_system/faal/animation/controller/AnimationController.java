package io.github.chaosawakens.api.animation_resolver_system.faal.animation.controller;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animation;
import io.github.chaosawakens.api.animation_resolver_system.faal.animation.StandardAnimation;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;

public class AnimationController<A extends Animatable> { // Basic FSM, blending is handled by AnimationManager
    protected final ResourceLocation controllerId;
    protected final A animatable;
    protected final ObjectArrayFIFOQueue<Animation> processingQueue = new ObjectArrayFIFOQueue<>();
    protected final ObjectArrayList<Animation> animationCache = new ObjectArrayList<>();
    protected double sequentialTransitionProgress = 0.0D; // Clamped (0.0 - 1.0)
    protected ControllerState controllerState = ControllerState.IDLE;
    protected Animation curSequentialAnim;

    public AnimationController(ResourceLocation controllerId, A animatable) {
        this.controllerId = controllerId;
        this.animatable = animatable;

        animatable.getAnimationInfo().getMappedAnimations().forEach((animationName, animationData) -> animationCache.add(new StandardAnimation(animationData)));
    }

    public void validateAndTickControllerState() { // Default assumption: we're on the server thread
        if (processingQueue.isEmpty()) this.controllerState = ControllerState.IDLE;

        controllerState.tick(this);
    }

    public ResourceLocation getControllerId() {
        return controllerId;
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
