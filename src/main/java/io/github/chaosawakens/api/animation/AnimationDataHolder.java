package io.github.chaosawakens.api.animation;

import software.bernie.geckolib3.core.builder.ILoopType;

public class AnimationDataHolder {
    private final String animationName;
    private final double animationLength;
    private final ILoopType loopType;
    public static final AnimationDataHolder EMPTY = new AnimationDataHolder("empty", 0, ILoopType.EDefaultLoopTypes.PLAY_ONCE);

    public AnimationDataHolder(String animationName, double animationLength, ILoopType loopType) {
        this.animationName = animationName;
        this.animationLength = animationLength;
        this.loopType = loopType;
    }

    public String getAnimationName() {
        return animationName;
    }

    public double getAnimationLength() {
        return animationLength;
    }

    public ILoopType getLoopType() {
        return loopType;
    }
}
