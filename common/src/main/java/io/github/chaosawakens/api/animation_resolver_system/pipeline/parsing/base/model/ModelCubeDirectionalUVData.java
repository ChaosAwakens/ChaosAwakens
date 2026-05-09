package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model;

import org.joml.Vector2d;

public interface ModelCubeDirectionalUVData {

    Vector2d getUVCoords();

    Vector2d getUVSize();

    default boolean isBox() {
        return getUVSize().x() == 0.0D && getUVSize().y() == 0.0D;
    }
}
