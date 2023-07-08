package io.github.chaosawakens.api.animation;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.keyframe.BoneAnimation;

public class WrappedAnimationController<E extends IAnimatableEntity> {
	protected E animatable;
	protected String name;
	protected ExpandedAnimationState animationState = ExpandedAnimationState.FINISHED;	
	protected IAnimationBuilder curAnim;
	protected double transitionLength;
	protected double transitionProgress = 0;
	protected double animationLength;
	protected double animationProgress = 0;	
	protected final AnimationController<E> controller;
	protected MinecraftServer server;
	
	public WrappedAnimationController(E animatable, int transitionTicks, AnimationController<E> controller) {
		this.animatable = animatable;
		this.transitionLength = transitionTicks;
		this.controller = controller;
		this.name = controller.getName();
		this.server = ((Entity) animatable).getServer();
	}
	
	public WrappedAnimationController(E animatable, AnimationController<E> controller) {
		this.animatable = animatable;
		this.transitionLength = animatable.animationInterval();
		this.controller = controller;
		this.name = controller.getName();
		this.server = ((Entity) animatable).getServer();
	}
	
	public void tick() { //TODO Sync client somewhat
		double tickProgressDelta = server == null ? Math.max(Minecraft.getInstance().getFrameTime() - Util.getMillis(), 0.0) / 50.0 : Math.max(server.getNextTickTime() - Util.getMillis(), 0.0) / 50.0;
		
		switch (animationState) {
		case TRANSITIONING:
			if (this.transitionProgress >= this.transitionLength) {
				this.transitionProgress = 0;
				this.animationState = ExpandedAnimationState.RUNNING;
			} else {
				this.transitionProgress += tickProgressDelta;
			}
			break;
		case RUNNING:
			if (this.animationProgress >= this.animationLength) {
				this.animationProgress = 0;
				if (this.curAnim.getLoopType() == EDefaultLoopTypes.LOOP) {
					this.animationProgress = 0;
					this.animationState = ExpandedAnimationState.TRANSITIONING;
				} else {
					this.animationState = ExpandedAnimationState.FINISHED;
				}
			} else {
				this.animationProgress += tickProgressDelta;
			}
			break;
		case STOPPED:
			if (this.curAnim != null && !this.curAnim.getAnimationName().equalsIgnoreCase("none")) this.animationState = ExpandedAnimationState.TRANSITIONING;
			break;
		case FINISHED:
			break;
		}
	}
	
	public void playAnimation(IAnimationBuilder targetAnim, boolean clearCache) {
		if (targetAnim == null) {
			this.animationProgress = 0;
			this.animationLength = 0;
			this.transitionProgress = 0;
			this.animationState = ExpandedAnimationState.FINISHED;
		}
		
		if (!curAnim.getAnimationName().equalsIgnoreCase(targetAnim.getAnimationName()) || clearCache) {
			if (clearCache) targetAnim.playAnimation(true);
			else targetAnim.playAnimation(false);
			
			this.animationProgress = 0;
			this.animationLength = 70;
			this.transitionProgress = 0;
			this.animationState = ExpandedAnimationState.TRANSITIONING;
		}
		this.controller.setAnimation(targetAnim.getBuilder());
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isCurrentAnimationFinished() {
		return animationState.equals(ExpandedAnimationState.FINISHED);
	}
	
	public boolean isAnimationFinished(String targetAnimName) {
		return curAnim.getAnimationName().equals(targetAnimName) && animationState.equals(ExpandedAnimationState.FINISHED);
	}
	
	public boolean isAnimationFinished(IAnimationBuilder targetAnim) {
		return isAnimationFinished(targetAnim.getAnimationName());
	}
	
	public boolean isPlayingAnimation(String targetAnimName) {
		return curAnim.getAnimationName().equals(targetAnimName) && (animationState.equals(ExpandedAnimationState.RUNNING) || animationState.equals(ExpandedAnimationState.TRANSITIONING));
	}
	
	public boolean isPlayingAnimation(IAnimationBuilder targetAnim) {
		return isPlayingAnimation(targetAnim.getAnimationName());
	}
	
	public ExpandedAnimationState getAnimationState() {
		return animationState;
	}

	public double getAnimationProgressTicks() {
		return Math.ceil(animationProgress) + 3;
	}
	
	public double getAnimationLength() {
		return Math.floor(animationLength) - 4;
	}
	
	public AnimationController<E> getWrappedController() {
		return controller;
	}
	
	public IAnimationBuilder getCurAnim() {
		return curAnim;
	}
	
	public static Animation none() {
		Animation noneAnimation = new Animation();
		noneAnimation.animationName = "None";
		noneAnimation.boneAnimations = new ArrayList<BoneAnimation>();
		noneAnimation.animationLength = 0.0;
		return noneAnimation;
	}
}
