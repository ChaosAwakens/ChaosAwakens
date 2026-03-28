package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface Skeleton { // Topological + Upward Traversal

    Set<Bone> getAllBones();
    Set<Bone> getRootBones();

    Map<String, Bone> getBonesByName();
    Map<Bone, Set<Bone>> getBoneBranches();

    Optional<Bone> getBoneByName(String boneName);

    void initializeBoneTree();
    void updateBranch(Bone bone);
    void refresh(ModelInfo updatedModelInfo);

    boolean isColliding(Skeleton other);

    default ModelCoordinateData getCoordinateData() {
        return null; // TODO Impl
    }
}
