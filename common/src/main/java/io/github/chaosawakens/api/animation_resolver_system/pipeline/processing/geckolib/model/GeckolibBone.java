package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class GeckolibBone implements Bone {

    public GeckolibBone(@NotNull String name, @Nullable Bone parentBone, List<Cube> cubes) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Optional<Bone> getParentBone() {
        return Optional.empty();
    }

    @Override
    public List<Cube> getCubes() {
        return List.of();
    }
}
