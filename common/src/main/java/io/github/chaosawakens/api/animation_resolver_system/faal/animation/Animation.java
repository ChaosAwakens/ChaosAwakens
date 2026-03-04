package io.github.chaosawakens.api.animation_resolver_system.faal.animation;

import com.mememan.nexus.loader.ModSide;

public interface Animation {

    String getAnimationName();

    double getTickProgress();
    double getTickDuration();

    boolean justStarted();
    boolean isPlaying();
    boolean isFinished();

    void tickAnimation(ModSide currentSide);
}
