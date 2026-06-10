package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelBoneData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.BoneTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class GeckolibBoneTemplate implements BoneTemplate {
    private final int index;
    private final String name;
    private final ModelBoneData backingData;
    private final int parentIndex;
    private final int depth;
    private final int[] childIndices;
    private final int[] cubeIndices;

    public GeckolibBoneTemplate(int index, ModelBoneData backingData, int parentIndex, int depth, int[] childIndices, int[] cubeIndices) {
        this.index = index;
        this.name = backingData.getBoneName();
        this.backingData = backingData;
        this.parentIndex = parentIndex;
        this.depth = depth;
        this.childIndices = childIndices.clone();
        this.cubeIndices = cubeIndices.clone();
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull ModelBoneData getBackingData() {
        return backingData;
    }

    @Override
    public int getParentIndex() {
        return parentIndex;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public int[] getChildIndices() {
        return childIndices.clone();
    }

    @Override
    public int[] getCubeIndices() {
        return cubeIndices.clone();
    }

    @Override
    public String toString() {
        return String.format(
                "GeckolibBoneTemplate{index=%d, name='%s', parentIndex=%d, depth=%d, childIndices=%s, cubeIndices=%s}",
                index,
                name,
                parentIndex,
                depth,
                Arrays.toString(childIndices),
                Arrays.toString(cubeIndices)
        );
    }

}
