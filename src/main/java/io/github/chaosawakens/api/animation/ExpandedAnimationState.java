package io.github.chaosawakens.api.animation;

import software.bernie.geckolib3.core.AnimationState;

public enum ExpandedAnimationState {
	Running(AnimationState.Running), Transitioning(AnimationState.Transitioning), Stopped(AnimationState.Stopped),
	Finished(AnimationState.Stopped);
	
	private final AnimationState translatedState;
	
	private ExpandedAnimationState(AnimationState translatedState) {
		this.translatedState = translatedState;
	}

	public AnimationState translate() {
		return translatedState;
	}
}
