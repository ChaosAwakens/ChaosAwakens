package io.github.chaosawakens.api.animation_resolver_system.faal.animation;

import com.mememan.nexus.loader.ModSide;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationData;

public class StandardAnimation implements Animation {

    public StandardAnimation() {

    }

    @Override
    public String getAnimationName() {
        return "";
    }

    @Override
    public AnimationData getBackingData() {
        return null;
    }

    @Override
    public double getTickProgress() {
        return 0;
    }

    @Override
    public double getTickDuration() {
        return 0;
    }

    @Override
    public boolean justStarted() {
        return false;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public <A extends Animatable> void tickAnimation(ModSide currentSide, A animatable) {

    }
}
