package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GeckolibSkeleton implements Skeleton {
    @Override
    public Set<Bone> getAllBones() {
        return Set.of();
    }

    @Override
    public Set<Bone> getRootBones() {
        return Set.of();
    }

    @Override
    public Map<String, Bone> getBonesByName() {
        return Map.of();
    }

    @Override
    public Map<Bone, Set<Bone>> getBoneBranches() {
        return Map.of();
    }

    @Override
    public Optional<Bone> getBoneByName(String boneName) {
        return Optional.empty();
    }

    @Override
    public void initializeBoneTree() {

    }

    @Override
    public void updateBranch(Bone bone) {

    }

    @Override
    public void refresh(ModelInfo updatedModelInfo) {

    }

    @Override
    public boolean isColliding(Skeleton other) {
        return false;
    }
}
