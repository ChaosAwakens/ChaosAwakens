package io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model;

import java.util.List;
import java.util.Optional;

public interface ModelGeometryInfo {

    <MM extends ModelMetadata> Optional<MM> getMetadata();

    <MBI extends ModelBoneData> List<MBI> getBones();
}
