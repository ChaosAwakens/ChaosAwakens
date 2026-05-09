package io.github.chaosawakens.api.animation_resolver_system.faal.animation;

import com.mememan.nexus.loader.ModSide;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationData;
import org.jetbrains.annotations.NotNull;

public interface Animation {

    String getAnimationName();

    @NotNull
    AnimationData getBackingData();

    LoopType getLoopType();

    double getTickProgress();

    double getTickDuration(); // Properties here are usually taken from #getBackingData(), but they take precedence over ones from backing data

    boolean justStarted();

    boolean isPlaying();

    boolean isFinished();

    boolean isPaused();

    void playAnimation();

    void pauseAnimation();

    void resetAnimation();

    void stopAnimation();

    <A extends Animatable> void tickAnimation(ModSide currentSide, A animatable);
}
