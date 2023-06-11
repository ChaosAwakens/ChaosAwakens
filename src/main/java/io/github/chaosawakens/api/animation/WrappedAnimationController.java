package io.github.chaosawakens.api.animation;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;

public class WrappedAnimationController<T extends IAnimatableEntity> {
	protected T animatable;
	protected String name;
	protected ExpandedAnimationState animationState = ExpandedAnimationState.FINISHED;	
	protected Animation currentAnimation = none();
	protected double transitionLength;
	protected double transitionProgress = 0;
	protected double animationLength;
	protected double animationProgress;	
	protected final AnimationController<T> controller;
	protected MinecraftServer server;
	
	public WrappedAnimationController(T animatable, AnimationController<T> controller) {
		this.animatable = animatable;
		this.transitionLength = animatable.animationInterval();
		this.controller = controller;
		this.name = controller.getName();
		this.server = ((Entity) animatable).getServer();
		this.animatable.getWrappedControllers().add(this);
	}
	
	public void tick() {
		double delta = server == null ? controller.tickOffset / 50.0 :
			Math.max(server.getNextTickTime() - Util.getMillis(), 0.0) / 50.0;
		
		switch (animationState) {
		case TRANSITIONING:
			if (this.transitionProgress >= this.transitionLength) {
				this.transitionProgress = 0;
				this.animationState = ExpandedAnimationState.RUNNING;
			} else {
				this.transitionProgress += delta;
			}
			break;
		case RUNNING:
			if (this.animationProgress >= this.animationLength) {
				this.animationProgress = 0;
				if (this.currentAnimation.loop == EDefaultLoopTypes.LOOP) {
					this.animationProgress = 0;
					this.animationState = ExpandedAnimationState.TRANSITIONING;
				} else {
					this.animationState = ExpandedAnimationState.FINISHED;
				}
			} else {
				this.animationProgress += delta;
			}
			break;
		case STOPPED:
			break;
		case FINISHED:
			break;
		}
	}
	
	public void playAnimation(IAnimationBuilder builder, boolean clearCache) {
		if (!builder.getAnimation().animationName.equals(getCurrentAnimation().animationName) || clearCache) {
			if (clearCache) builder.playAnimation(true);
			else builder.playAnimation(false);
			
			this.animationProgress = 0;
			this.animationLength = builder.getAnimation().animationLength;
			this.transitionProgress = 0;
			this.animationState = ExpandedAnimationState.TRANSITIONING;
		}
		this.currentAnimation = builder.getAnimation();
		this.controller.setAnimation(builder.getBuilder());
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isCurrentAnimationFinished() {
		return this.animationState.equals(ExpandedAnimationState.FINISHED);
	}
	
	public boolean isAnimationFinished(String targetAnimName) {
		return currentAnimation.animationName.equals(targetAnimName)
				&& animationState.equals(ExpandedAnimationState.FINISHED);
	}
	
	public boolean isAnimationFinished(IAnimationBuilder targetAnim) {
		return currentAnimation.animationName.equals(targetAnim.getAnimation().animationName)
				&& animationState.equals(ExpandedAnimationState.FINISHED);
	}
	
	public boolean isPlayingAnimation(String targetAnimName) {
		return currentAnimation.animationName.equals(targetAnimName)
				&& animationState.equals(ExpandedAnimationState.RUNNING);
	}
	
	public boolean isPlayingAnimation(IAnimationBuilder targetAnim) {
		return currentAnimation.animationName.equals(targetAnim.getAnimation().animationName)
				&& animationState.equals(ExpandedAnimationState.RUNNING);
	}
	
	public ExpandedAnimationState getAnimationState() {
		return animationState;
	}

	public double getAnimationProgress() {
		return animationProgress;
	}
	
	public double getAnimationLength() {
		return animationLength;
	}
	
	public AnimationController<T> getWrappedController() {
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
