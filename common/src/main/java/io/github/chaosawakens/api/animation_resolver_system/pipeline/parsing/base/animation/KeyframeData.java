package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.animation;

import java.util.function.Supplier;

public interface KeyframeData {

    KeyframeTarget getPosition();
    KeyframeTarget getRotation();
    KeyframeTarget getScale();

    <E extends Easing> Supplier<E> getEasing();
}
