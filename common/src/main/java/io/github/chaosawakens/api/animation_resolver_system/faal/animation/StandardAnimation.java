package io.github.chaosawakens.api.animation_resolver_system.faal.animation;

import com.mememan.nexus.loader.ModSide;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationData;
import org.jetbrains.annotations.NotNull;

public class StandardAnimation implements Animation {
    protected final AnimationData backingData;
    protected double tickProgress = 0.0D;
    protected boolean isPlaying = false;
    protected boolean isFinished = false;
    protected boolean isPaused = false;

    public StandardAnimation(AnimationData backingData) {
        this.backingData = backingData;
    }

    @Override
    public String getAnimationName() {
        return getBackingData().getAnimationName();
    }

    @Override
    public @NotNull AnimationData getBackingData() {
        return backingData;
    }

    @Override
    public LoopType getLoopType() {
        return null;
    }

    @Override
    public double getTickProgress() {
        return tickProgress;
    }

    @Override
    public double getTickDuration() {
        return backingData.getAnimationLength();
    }

    @Override
    public boolean justStarted() {
        return getTickProgress() == 0.0D && isPlaying();
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public void playAnimation() {
        this.isPlaying = true;
        this.tickProgress = 0.0D;

        this.isFinished = false;
        this.isPaused = false;
    }

    @Override
    public void pauseAnimation() {
        this.isPlaying = false;
        this.isFinished = false;
        this.isPaused = true;
    }

    @Override
    public void resetAnimation() {
        this.tickProgress = 0.0D;
    }

    @Override
    public void stopAnimation() {

    }

    @Override
    public <A extends Animatable> void tickAnimation(ModSide currentSide, A animatable) {
        if (currentSide == ModSide.SERVER) {

        }
    }
}
