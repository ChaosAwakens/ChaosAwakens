package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;

public interface SkeletonTemplate {
    int ROOT_INDEX = -1;
    Comparator<BoneTemplate> DEFAULT_BONE_TEMPLATE_SORTER = Comparator.comparingInt(BoneTemplate::getDepth).thenComparing(BoneTemplate::getName);

    @NotNull
    ModelInfo getBackingInfo();

    int getTotalBoneCount();
    int getTotalCubeCount();

    @NotNull
    BoneTemplate getBone(int boneIndex);

    @NotNull
    CubeTemplate getCube(int cubeIndex);

    @NotNull
    BoneTemplate[] getAllBones();

    @NotNull
    BoneTemplate[] getRootBones();

    @NotNull
    CubeTemplate[] getAllCubes();

    int[] getRootBoneIndices();

    int getBoneIndex(String boneName);
    int getParentBoneIndex(int boneIndex);

    int[] getChildBoneIndices(int boneIndex);
    int[] getCubeIndices(int boneIndex);

    int getCubeParentBoneIndex(int cubeIndex);

    BoneTemplate boneTemplateAt(int boneIndex);

    CubeTemplate cubeTemplateAt(int cubeIndex);

    default Optional<? extends BoneTemplate> getBoneByName(String boneName) {
        int boneIndex = getBoneIndex(boneName);

        return boneIndex == ROOT_INDEX ? Optional.empty() : Optional.of(getBone(boneIndex));
    }

    static String normalizeBoneName(String boneName) {
        return boneName == null ? "" : boneName.toLowerCase(Locale.ROOT);
    }
}
