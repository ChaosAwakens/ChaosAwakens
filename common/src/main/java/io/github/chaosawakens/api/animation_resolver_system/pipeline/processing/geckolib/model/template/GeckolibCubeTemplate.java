package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.template.CubeTemplate;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

public final class GeckolibCubeTemplate implements CubeTemplate {
    private final int index;
    private final int parentBoneIndex;
    private final ModelCubeData backingData;
    private final ModelCoordinateData baseCoordinateData;
    private final boolean allowsLocalClipping;
    private final boolean hasCollisions;
    private final boolean shouldSync;

    public GeckolibCubeTemplate(int index, int parentBoneIndex, ModelCubeData backingData, boolean shouldSync) {
        this.index = index;
        this.parentBoneIndex = parentBoneIndex;
        this.backingData = backingData;
        this.baseCoordinateData = createBaseCoordinateData(backingData);
        this.allowsLocalClipping = true;
        this.hasCollisions = true;
        this.shouldSync = shouldSync;
    }

    @Override
    public @NotNull ModelCubeData getBackingData() {
        return backingData;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getParentBoneIndex() {
        return parentBoneIndex;
    }

    @Override
    public @NotNull ModelCoordinateData createCoordinateData() {
        return new ModelCoordinateData(baseCoordinateData);
    }

    @Override
    public boolean allowsLocalClipping() {
        return allowsLocalClipping;
    }

    @Override
    public boolean hasCollision() {
        return hasCollisions;
    }

    @Override
    public boolean shouldSync() {
        return shouldSync;
    }

    private static ModelCoordinateData createBaseCoordinateData(ModelCubeData cubeData) {
        Vector3d origin = cubeData.getOrigin();
        Vector3d size = cubeData.getSize();
        ModelCoordinateData coordinateData = new ModelCoordinateData();

        double minX = -origin.x - size.x; // Geckolib shenanigans
        double maxX = -origin.x;
        double minY = origin.y;
        double maxY = origin.y + size.y;
        double minZ = origin.z;
        double maxZ = origin.z + size.z;

        coordinateData.setBounds(minX, minY, minZ, maxX, maxY, maxZ);

        return coordinateData; // Rotations and other transforms are handled in the "runtime" counterparts (e.g. GeckolibCube)
    }

    @Override
    public String toString() {
        return String.format(
                "GeckolibCubeTemplate{index=%d, parentBoneIndex=%d, allowsLocalClipping=%s, hasCollisions=%s, shouldSync=%s, backingData=%s}",
                index,
                parentBoneIndex,
                allowsLocalClipping,
                hasCollisions,
                shouldSync,
                backingData
        );
    }

}
