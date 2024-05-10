package io.github.chaosawakens.api.animation;

import io.github.chaosawakens.common.codec.assets.AnimationDataCodec;
import io.github.chaosawakens.common.network.packets.s2c.AnimationFunctionalProgressPacket;
import io.github.chaosawakens.manager.CANetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.keyframe.BoneAnimation;
import software.bernie.geckolib3.util.AnimationUtils;

import java.util.ArrayList;
import java.util.Optional;

public class WrappedAnimationController<E extends IAnimatableEntity> {
	protected E animatable;
	protected String name;
	protected ExpandedAnimationState animationState = ExpandedAnimationState.FINISHED;
	protected ILoopType curAnimLoopType = EDefaultLoopTypes.PLAY_ONCE;
	protected Animation currentAnimation = none();
	protected IAnimationBuilder previousCachedAnimationBuilder;
	protected IAnimationBuilder currentAnimationBuilder;
	protected double transitionLength;
	protected double transitionProgress = 0;
	protected double animationLength;
	protected double animationProgress = 0;
	protected double animSpeedMultiplier = 1.0D;
	protected final AnimationController<E> controller;
	protected boolean enforcesProgress = false;
	protected MinecraftServer server;
	
	public WrappedAnimationController(E animatable, int transitionTicks, AnimationController<E> controller) {
		this.animatable = animatable;
		this.transitionLength = transitionTicks;
		this.controller = controller;
		this.name = controller.getName();
		this.server = ((Entity) animatable).getServer();
	}
	
	public WrappedAnimationController(E animatable, AnimationController<E> controller) {
		this(animatable, animatable.animationInterval(), controller);
		this.name = controller.getName();
		this.server = ((Entity) animatable).getServer();
	}
	
	public WrappedAnimationController<? extends E> setEnforcesAnimProgress() {
		this.enforcesProgress = true;
		return this;
	}
	
	public void tick() {
		double tickProgressDelta = getSyncedProgress();
		
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
				if (this.curAnimLoopType == EDefaultLoopTypes.HOLD_ON_LAST_FRAME) {
					this.animationState = ExpandedAnimationState.STOPPED;
				} else if (this.curAnimLoopType == EDefaultLoopTypes.LOOP) {
					this.animationProgress = 0;
					this.animationState = ExpandedAnimationState.TRANSITIONING;
				} else {
					this.animationProgress = 0;
					this.animationState = ExpandedAnimationState.FINISHED;
				}
			} else {
				this.animationProgress += tickProgressDelta;
			}
			break;
		case STOPPED:
			if (!currentAnimationBuilder.getAnimationName().equals(previousCachedAnimationBuilder.getAnimationName())) {
				if (currentAnimation != none() && currentAnimationBuilder != null) {
					this.animationProgress = 0;
					this.animationState = ExpandedAnimationState.TRANSITIONING;
				} else {
					this.animationState = ExpandedAnimationState.FINISHED;
				}
			}
			break;
		case FINISHED:
			break;
        }
	}
	
	public void playAnimation(IAnimationBuilder builder, boolean clearCache) {
		Optional<AnimationDataCodec.AnimationMetadataCodec> animatableMetadata = animatable.getSidedMetadataFor(builder.getAnimationName());

		if (builder != null && !getCurrentAnimation().animationName.equals(builder.getAnimationName()) || clearCache) {
			builder.playAnimation(clearCache);

			this.animationProgress = 0;
			this.animationLength = Optional.ofNullable(AnimationUtils.convertSecondsToTicks(animatableMetadata.get().getAnimationLength())).orElse(0.0D);
			this.curAnimLoopType = Optional.ofNullable(animatableMetadata.get().getLoopType()).orElse(EDefaultLoopTypes.PLAY_ONCE);
			this.transitionProgress = 0;
			this.animationState = ExpandedAnimationState.TRANSITIONING;
			this.animSpeedMultiplier = builder.getWrappedAnimSpeed();

			this.currentAnimationBuilder = builder;
			this.previousCachedAnimationBuilder = currentAnimationBuilder;
			this.currentAnimation = builder.getAnimation();
			this.controller.setAnimation(builder.getBuilder());
			this.controller.setAnimationSpeed(animSpeedMultiplier);
		}
	}
	
	public void stopAnimation(IAnimationBuilder targetAnim) {
		targetAnim.stopAnimation();
		
		this.animationProgress = 0;
		this.curAnimLoopType = EDefaultLoopTypes.PLAY_ONCE;
		this.animationLength = 0;
		this.transitionProgress = 0;
		this.animationState = ExpandedAnimationState.FINISHED;
		this.currentAnimation = none();
		this.currentAnimationBuilder = null;
		
		this.controller.setAnimation(null);
	}
	
	public double getSyncedProgress() {
		if (server != null) CANetworkManager.sendEntityTrackingPacket(new AnimationFunctionalProgressPacket(name, ((Entity) animatable).getId(), (Math.max(server.getNextTickTime() - Util.getMillis(), 0.0) / 50.0) * animSpeedMultiplier), (Entity) animatable);
		return server == null ? 0 : (Math.max(server.getNextTickTime() - Util.getMillis(), 0.0) / 50.0) * animSpeedMultiplier;
	}
	
	public void updateAnimProgress(double animationProgressDelta) {
		this.animationProgress += animationProgressDelta;
	}
	
	public double getAnimSpeed() {
		return animSpeedMultiplier;
	}
	
	public void setAnimSpeed(double animSpeedMultiplier) {
		this.animSpeedMultiplier = animSpeedMultiplier;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean enforcesProgress() {
		return enforcesProgress;
	}
	
	public boolean isCurrentAnimationFinished() {
		return animationState.equals(ExpandedAnimationState.FINISHED) || animationState.equals(ExpandedAnimationState.STOPPED);
	}
	
	public boolean isAnimationFinished(String targetAnimName) {
		if (server == null) {
			return this.getWrappedController().getAnimationState().equals(AnimationState.Stopped);
		} else {
			return currentAnimation != null && currentAnimation.animationName.equals(targetAnimName)
					&& (animationState.equals(ExpandedAnimationState.FINISHED) || animationState.equals(ExpandedAnimationState.STOPPED));
		}
	}
	
	public boolean isAnimationFinished(IAnimationBuilder targetAnim) {
		return isAnimationFinished(targetAnim.getAnimationName());
	}
	
	public boolean isPlayingAnimation(String targetAnimName) {
		return currentAnimation != null && currentAnimation.animationName.equals(targetAnimName)
				&& (animationState.equals(ExpandedAnimationState.RUNNING)
						|| animationState.equals(ExpandedAnimationState.TRANSITIONING));
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
	
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public static Animation none() {
		Animation noneAnimation = new Animation();
		noneAnimation.animationName = "None";
		noneAnimation.boneAnimations = new ArrayList<BoneAnimation>();
		noneAnimation.animationLength = 0.0;
		return noneAnimation;
	}
}
