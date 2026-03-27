package io.github.chaosawakens.api.animation_resolver_system.faal.animation;

import com.mememan.nexus.loader.ModSide;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation.AnimationData;

public interface Animation {

    String getAnimationName();

    AnimationData getBackingData();

    double getTickProgress();
    double getTickDuration(); // Properties here are usually taken from #getBackingData(), but they take precedence over ones from backing data

    boolean justStarted();
    boolean isPlaying();
    boolean isFinished();

    <A extends Animatable> void tickAnimation(ModSide currentSide, A animatable);
}
