package io.github.chaosawakens.api.animation_resolver_system.faal.hitbox;

import io.github.chaosawakens.api.animation_resolver_system.faal.animation.Animatable;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import org.jetbrains.annotations.NotNull;

public interface MappableHitboxOwner extends Animatable {

    @NotNull
    Skeleton getSkeleton();

}
