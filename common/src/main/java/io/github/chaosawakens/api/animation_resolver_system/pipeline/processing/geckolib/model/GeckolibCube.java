package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GeckolibCube implements Cube {

    public GeckolibCube(@Nullable Bone parentBone, ModelCoordinateData coordinateData, boolean shouldSync) {

    }

    @Override
    public Optional<Bone> getParentBone() {
        return Optional.empty();
    }

    @Override
    public ModelCoordinateData getCoordinateData() {
        return null;
    }

    @Override
    public boolean allowsLocalClipping() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public boolean hasCollision() {
        return false;
    }

    @Override
    public boolean shouldSync() {
        return false;
    }

    @Override
    public void setCollision(boolean collision) {

    }

    @Override
    public void setAllowsLocalClipping(boolean allowsLocalClipping) {

    }
}
