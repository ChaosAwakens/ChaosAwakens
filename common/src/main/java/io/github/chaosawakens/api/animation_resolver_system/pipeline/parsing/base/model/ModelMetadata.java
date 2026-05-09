package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model;

public interface ModelMetadata {

    String getGeometryId();

    double getTextureWidth();

    double getTextureHeight();

    double getVisibleBoundsWidth();

    double getVisibleBoundsHeight();

    double getVisibleBoundsOffsetX();

    double getVisibleBoundsOffsetY();

    double getVisibleBoundsOffsetZ();
}
