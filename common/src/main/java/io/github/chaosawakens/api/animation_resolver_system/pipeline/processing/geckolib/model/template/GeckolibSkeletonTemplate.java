package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelBoneData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelMetadata;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.BoneTemplate;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.CubeTemplate;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.SkeletonTemplate;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class GeckolibSkeletonTemplate implements SkeletonTemplate {
    private final ModelInfo backingInfo;
    private final GeckolibBoneTemplate[] bones;
    private final GeckolibBoneTemplate[] rootBones;
    private final GeckolibCubeTemplate[] cubes;
    private final int[] rootBoneIndices;
    private final int[] parentBoneIndices;
    private final int[][] childBoneIndices;
    private final int[][] cubeIndicesByBone;
    private final int[] cubeParentBoneIndices;
    private final Object2IntOpenHashMap<String> boneIndexByName;

    public GeckolibSkeletonTemplate(ModelInfo modelInfo) {
        if (modelInfo == null) {
            throw new IllegalArgumentException("Attempted to build a GeckolibSkeletonTemplate with null model info!");
        }

        this.backingInfo = modelInfo;

        List<ModelBoneData> modelBones = modelInfo.getGeometryInfo().getBones();

        if (modelBones.isEmpty()) {
            CAConstants.LOGGER.warn("Attempted to construct empty GeckolibSkeletonTemplate for model {}, skipping...", modelInfo.getGeometryInfo().getMetadata().map(ModelMetadata::getGeometryId).orElse("[Unknown Model]"));
        }

        GeckolibTemplateBuildData buildData = buildTemplateData(modelBones);

        this.bones = buildData.bones;
        this.rootBones = buildData.rootBones;
        this.cubes = buildData.cubes;
        this.rootBoneIndices = buildData.rootBoneIndices;
        this.parentBoneIndices = buildData.parentBoneIndices;
        this.childBoneIndices = buildData.childBoneIndices;
        this.cubeIndicesByBone = buildData.cubeIndicesByBone;
        this.cubeParentBoneIndices = buildData.cubeParentBoneIndices;
        this.boneIndexByName = buildData.boneIndexByName;
    }

    @Override
    public @NotNull ModelInfo getBackingInfo() {
        return backingInfo;
    }

    @Override
    public int getTotalBoneCount() {
        return bones.length;
    }

    @Override
    public int getTotalCubeCount() {
        return cubes.length;
    }

    @Override
    public @NotNull BoneTemplate getBone(int boneIndex) {
        validateBoneIndex(boneIndex);
        return bones[boneIndex];
    }

    @Override
    public @NotNull CubeTemplate getCube(int cubeIndex) {
        validateCubeIndex(cubeIndex);
        return cubes[cubeIndex];
    }

    @Override
    public @NotNull BoneTemplate[] getAllBones() {
        return bones.clone();
    }

    @Override
    public @NotNull BoneTemplate[] getRootBones() {
        return rootBones.clone();
    }

    @Override
    public @NotNull CubeTemplate[] getAllCubes() {
        return cubes.clone();
    }

    @Override
    public int[] getRootBoneIndices() {
        return rootBoneIndices.clone();
    }

    @Override
    public int getBoneIndex(String boneName) {
        return boneIndexByName.getInt(SkeletonTemplate.normalizeBoneName(boneName));
    }

    @Override
    public int getParentBoneIndex(int boneIndex) {
        validateBoneIndex(boneIndex);
        return parentBoneIndices[boneIndex];
    }

    @Override
    public int[] getChildBoneIndices(int boneIndex) {
        validateBoneIndex(boneIndex);

        return childBoneIndices[boneIndex].clone();
    }

    @Override
    public int[] getCubeIndices(int boneIndex) {
        validateBoneIndex(boneIndex);

        return cubeIndicesByBone[boneIndex].clone();
    }

    @Override
    public int getCubeParentBoneIndex(int cubeIndex) {
        validateCubeIndex(cubeIndex);

        return cubeParentBoneIndices[cubeIndex];
    }

    @Override
    public GeckolibBoneTemplate boneTemplateAt(int boneIndex) {
        validateBoneIndex(boneIndex);

        return bones[boneIndex];
    }

    @Override
    public GeckolibCubeTemplate cubeTemplateAt(int cubeIndex) {
        validateCubeIndex(cubeIndex);

        return cubes[cubeIndex];
    }

    private void validateBoneIndex(int boneIndex) {
        if (boneIndex < 0 || boneIndex >= bones.length) {
            throw new IndexOutOfBoundsException(String.format("Bone index out of bounds: %d", boneIndex));
        }
    }

    private void validateCubeIndex(int cubeIndex) {
        if (cubeIndex < 0 || cubeIndex >= cubes.length) {
            throw new IndexOutOfBoundsException(String.format("Cube index out of bounds: %d", cubeIndex));
        }
    }

    private static GeckolibTemplateBuildData buildTemplateData(List<ModelBoneData> modelBones) {
        Object2IntOpenHashMap<String> indexByName = new Object2IntOpenHashMap<>();

        indexByName.defaultReturnValue(SkeletonTemplate.ROOT_INDEX);

        Map<String, ModelBoneData> dataByName = new Object2ObjectOpenHashMap<>();

        for (ModelBoneData boneData : modelBones) {
            String normalizedBoneName = SkeletonTemplate.normalizeBoneName(boneData.getBoneName());

            if (indexByName.put(normalizedBoneName, indexByName.size()) != SkeletonTemplate.ROOT_INDEX) {
                throw new IllegalStateException(String.format("Duplicate bone name: '%s'", normalizedBoneName));
            }

            dataByName.put(normalizedBoneName, boneData);
        }

        int boneCount = modelBones.size();
        int[] parentIndices = new int[boneCount];
        int[] depths = new int[boneCount];

        Arrays.fill(parentIndices, SkeletonTemplate.ROOT_INDEX);
        Arrays.fill(depths, SkeletonTemplate.ROOT_INDEX);

        Set<String> resolvingBones = new ObjectOpenHashSet<>();

        for (ModelBoneData boneData : modelBones) {
            resolveBoneDepthAndParent(SkeletonTemplate.normalizeBoneName(boneData.getBoneName()), dataByName, indexByName, parentIndices, depths, resolvingBones);
        }

        IntList[] childrenByBone = new IntList[boneCount];
        IntList[] cubesByBone = new IntList[boneCount];
        IntList rootIndices = new IntArrayList();
        List<GeckolibCubeTemplate> cubeTemplates = new ArrayList<>();

        for (int i = 0; i < boneCount; i++) {
            childrenByBone[i] = new IntArrayList();
            cubesByBone[i] = new IntArrayList();
        }

        for (int childIndex = 0; childIndex < boneCount; childIndex++) {
            int parentIndex = parentIndices[childIndex];

            if (parentIndex == SkeletonTemplate.ROOT_INDEX) rootIndices.add(childIndex);
            else childrenByBone[parentIndex].add(childIndex);
        }

        for (int boneIndex = 0; boneIndex < boneCount; boneIndex++) {
            ModelBoneData boneData = modelBones.get(boneIndex);

            for (ModelCubeData cubeData : boneData.getCubes()) {
                int cubeIndex = cubeTemplates.size();
                cubeTemplates.add(new GeckolibCubeTemplate(cubeIndex, boneIndex, cubeData, false));
                cubesByBone[boneIndex].add(cubeIndex);
            }
        }

        int[][] childIndices = toArrays(childrenByBone);
        int[][] cubeIndicesByBone = toArrays(cubesByBone);
        GeckolibCubeTemplate[] cubes = cubeTemplates.toArray(GeckolibCubeTemplate[]::new);
        int[] cubeParentIndices = new int[cubes.length];

        for (int i = 0; i < cubes.length; i++) {
            cubeParentIndices[i] = cubes[i].getParentBoneIndex();
        }

        GeckolibBoneTemplate[] bones = new GeckolibBoneTemplate[boneCount];

        for (int boneIndex = 0; boneIndex < boneCount; boneIndex++) {
            bones[boneIndex] = new GeckolibBoneTemplate(boneIndex, modelBones.get(boneIndex), parentIndices[boneIndex], depths[boneIndex], childIndices[boneIndex], cubeIndicesByBone[boneIndex]);
        }

        Arrays.sort(bones, DEFAULT_BONE_TEMPLATE_SORTER);
/*

        int[] remappedIndices = new int[boneCount];

        for (int sortedIndex = 0; sortedIndex < bones.length; sortedIndex++) {
            remappedIndices[bones[sortedIndex].getIndex()] = sortedIndex;
        }
*/

        /*
         * Keep parsed order indices stable for arrays.
         *
         * We're intentionally discarding the sorted copy for now, since index stability matters more than presentation
         * order for templates.
         *
         * TODO This'll probably need to be revisited at some point later down the line
         */
        bones = new GeckolibBoneTemplate[boneCount];

        for (int boneIndex = 0; boneIndex < boneCount; boneIndex++) {
            bones[boneIndex] = new GeckolibBoneTemplate(boneIndex, modelBones.get(boneIndex), parentIndices[boneIndex], depths[boneIndex], childIndices[boneIndex], cubeIndicesByBone[boneIndex]);
        }

        GeckolibBoneTemplate[] finalBones = bones;
        GeckolibBoneTemplate[] rootBones = rootIndices.intStream()
                .mapToObj(rootIndex -> finalBones[rootIndex])
                .toArray(GeckolibBoneTemplate[]::new);

        return new GeckolibTemplateBuildData(bones, rootBones, cubes, rootIndices.toIntArray(),
                parentIndices, childIndices, cubeIndicesByBone, cubeParentIndices, indexByName);
    }

    private static int resolveBoneDepthAndParent(String boneName, Map<String, ModelBoneData> dataByName, Object2IntOpenHashMap<String> indexByName, int[] parentIndices, int[] depths, Set<String> resolvingBones) {
        int boneIndex = indexByName.getInt(boneName);

        if (boneIndex == SkeletonTemplate.ROOT_INDEX) {
            throw new IllegalStateException(String.format("Missing bone data for bone: '%s'", boneName));
        }

        if (depths[boneIndex] != SkeletonTemplate.ROOT_INDEX) return depths[boneIndex];
        if (!resolvingBones.add(boneName)) throw new IllegalStateException(String.format("Cyclic bone parent chain @ bone: '%s'", boneName));

        ModelBoneData boneData = dataByName.get(boneName);
        int parentIndex = boneData.getParentBoneName()
                .map(SkeletonTemplate::normalizeBoneName)
                .map(parentName -> {
                    int resolvedParentIndex = indexByName.getInt(parentName);

                    if (resolvedParentIndex == SkeletonTemplate.ROOT_INDEX) {
                        throw new IllegalStateException(String.format("Missing parent bone '%s' for bone '%s'", parentName, boneData.getBoneName()));
                    }

                    resolveBoneDepthAndParent(parentName, dataByName, indexByName, parentIndices, depths, resolvingBones);

                    return resolvedParentIndex;
                }).orElse(SkeletonTemplate.ROOT_INDEX);

        parentIndices[boneIndex] = parentIndex;
        depths[boneIndex] = parentIndex == SkeletonTemplate.ROOT_INDEX ? 0 : depths[parentIndex] + 1;
        resolvingBones.remove(boneName);

        return depths[boneIndex];
    }

    private static int[][] toArrays(IntList[] values) {
        int[][] result = new int[values.length][];

        for (int i = 0; i < values.length; i++) {
            result[i] = values[i].toIntArray();
        }

        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "GeckolibSkeletonTemplate{totalBoneCount=%d, totalCubeCount=%d, rootBoneIndices=%s, parentBoneIndices=%s, cubeParentBoneIndices=%s, backingInfo=%s}",
                bones.length,
                cubes.length,
                Arrays.toString(rootBoneIndices),
                Arrays.toString(parentBoneIndices),
                Arrays.toString(cubeParentBoneIndices),
                backingInfo
        );
    }

    // TODO Maybe generalize all of ts
    private record GeckolibTemplateBuildData(GeckolibBoneTemplate[] bones, GeckolibBoneTemplate[] rootBones, GeckolibCubeTemplate[] cubes, int[] rootBoneIndices, int[] parentBoneIndices, int[][] childBoneIndices, int[][] cubeIndicesByBone, int[] cubeParentBoneIndices, Object2IntOpenHashMap<String> boneIndexByName) {}
}

