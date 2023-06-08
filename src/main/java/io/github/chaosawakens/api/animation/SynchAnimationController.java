package io.github.chaosawakens.api.animation;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;

/**
 * //TODO Name this something reasonable
 */
public class SynchAnimationController<T extends IAnimatableEntity> {
	
	protected T animatable;
	protected String name;
	protected ExpandedAnimationState animationState = ExpandedAnimationState.Finished;
	
	protected Animation currentAnimation = none();
	protected double transitionLength;
	protected double transitionProgress = 0;
	protected double animationLength;
	protected double animationProgress;
	
	private AnimationBuilder builder;
	protected final AnimationController<T> controller;
	
	protected MinecraftServer server;
	
	public SynchAnimationController(T animatable, AnimationController<T> controller) {
		this.animatable = animatable;
		this.transitionLength = animatable.animationInterval();
		this.controller = controller;
		this.name = controller.getName();
		this.server = ((Entity) animatable).getServer();
	}
	
	void tick() {
		double delta = Math.abs(server.getNextTickTime() - Util.getMillis()) / 50.0;
		switch (animationState) {
		case Transitioning:
			if(this.transitionProgress >= this.transitionLength) {
				this.transitionProgress = 0;
				this.animationState = ExpandedAnimationState.Running;
			} else {
				this.transitionProgress += delta;
				this.controller.setAnimation(builder);
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
				this.controller.setAnimation(builder);
				this.animationProgress += delta;
			}
			break;
		case Stopped:
			break;
		case Finished:
			break;
		}
	}
	
	public void setAnimation(Animation animation, AnimationBuilder builder) {
		this.animationProgress = 0;
		this.animationLength = animation.animationLength;
		this.transitionProgress = 0;
		this.currentAnimation = animation;
		this.animationState = ExpandedAnimationState.Transitioning;
		this.controller.setAnimation(builder);
		this.builder = builder;
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
		return noneAnimation;
	}
}
