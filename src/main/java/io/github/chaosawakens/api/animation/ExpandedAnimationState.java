package io.github.chaosawakens.api.animation;

import software.bernie.geckolib3.core.AnimationState;

public enum ExpandedAnimationState {
	RUNNING(AnimationState.Running),
	TRANSITIONING(AnimationState.Transitioning),
	STOPPED(AnimationState.Stopped),
	FINISHED(AnimationState.Stopped);
	
	private final AnimationState translatedState;
	
	ExpandedAnimationState(AnimationState translatedState) {
		this.translatedState = translatedState;
	}

	public AnimationState translate() {
		return translatedState;
	}
}