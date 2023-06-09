package io.github.chaosawakens.api.animation;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;

/**
 * //TODO Name this something reasonable
 */
public class AnimationControllerWrapper<T extends IAnimatableEntity> {
	
	protected T animatable;
	protected String name;
	protected ExpandedAnimationState animationState = ExpandedAnimationState.Finished;
	
	protected Animation currentAnimation = none();
	protected double transitionLength;
	protected double transitionProgress = 0;
	protected double animationLength;
	protected double animationProgress;
	
	protected final AnimationController<T> controller;
	
	protected MinecraftServer server;
	
	public AnimationControllerWrapper(T animatable, AnimationController<T> controller) {
		this.animatable = animatable;
		this.transitionLength = animatable.animationInterval();
		this.controller = controller;
		this.name = controller.getName();
		this.server = ((Entity) animatable).getServer();
		this.animatable.getControllerWrappers().add(this);
	}
	
	void tick() {
		double delta = server == null ? controller.tickOffset / 50.0 : Math.abs(server.getNextTickTime() - Util.getMillis()) / 50.0;
		switch (animationState) {
		case Transitioning:
			if(this.transitionProgress >= this.transitionLength) {
				this.transitionProgress = 0;
				this.animationState = ExpandedAnimationState.Running;
			} else {
				this.transitionProgress += delta;
			}
			break;
		case Running:
			if(this.animationProgress >= this.animationLength) {
				this.animationProgress = 0;
				if(this.currentAnimation.loop == EDefaultLoopTypes.LOOP) {
					this.animationProgress = 0;
					this.animationState = ExpandedAnimationState.Transitioning;
				} else {
					this.animationState = ExpandedAnimationState.Finished;
				}
			} else {
				this.animationProgress += delta;
			}
			break;
		case Stopped:
			break;
		case Finished:
			break;
		}
	}
	
	public void playAnimation(IAnimationBuilder builder, boolean clearCache) {
		if(!builder.getAnimation().animationName.equals(getCurrentAnimation().animationName)
				|| animationState.equals(ExpandedAnimationState.Finished)) {
			if(clearCache)
				builder.playAnimation(true);
			else
				builder.playAnimation(false);
			this.animationProgress = 0;
			this.animationLength = builder.getAnimation().animationLength;
			this.transitionProgress = 0;
			this.animationState = ExpandedAnimationState.Transitioning;
		}
		this.currentAnimation = builder.getAnimation();
		this.controller.setAnimation(builder.getBuilder());
	}
	
	public String getName() {
		return name;
	}
	
	public ExpandedAnimationState getAnimationState() {
		return animationState;
	}

	public double getAnimationProgressTicks() {
		return animationProgress;
	}
	
	public AnimationController<T> getController() {
		return controller;
	}
	
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public static Animation none() {
		Animation noneAnimation = new Animation();
		noneAnimation.animationName = "None";
		noneAnimation.boneAnimations = new ArrayList<>();
		noneAnimation.animationLength = 0.0;
		return noneAnimation;
	}
}
