package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template.GeckolibCubeTemplate;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template.GeckolibSkeletonTemplate;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;

import java.util.Optional;

public class GeckolibCube implements Cube {
    protected final GeckolibSkeleton owner;
    protected final int index;
    @NotNull
    protected final ModelCoordinateData coordinateData;
    protected boolean allowLocalClipping;
    protected boolean isEnabled = true;
    protected boolean hasCollision;
    protected boolean shouldSync;

    public GeckolibCube(GeckolibSkeleton owner, int index) {
        this.owner = owner;
        this.index = index;
        this.coordinateData = getTemplate().createCoordinateData();
        this.allowLocalClipping = getTemplate().allowsLocalClipping();
        this.hasCollision = getTemplate().hasCollision();
        this.shouldSync = getTemplate().shouldSync();

        refreshCoordinateData();
    }

    @Override
    public @NotNull GeckolibCubeTemplate getTemplate() {
        return owner.getTemplate().cubeTemplateAt(index);
    }

    @Override
    public Optional<GeckolibBone> getParentBone() {
        int parentBoneIndex = getTemplate().getParentBoneIndex();

        return parentBoneIndex == GeckolibSkeletonTemplate.ROOT_INDEX
                ? Optional.empty()
                : Optional.of(owner.getBone(parentBoneIndex));
    }

    @Override
    public @NotNull ModelCoordinateData getCoordinateData() {
        return coordinateData;
    }

    @Override
    public void refreshCoordinateData() {
        coordinateData.setBoneMatrix(owner.buildCubeMatrix(this));
    }

    @Override
    public Matrix4d buildCubeLocalMatrix() {
        Matrix4d cubeMatrix = new Matrix4d().identity();
        ModelCubeData backingData = getBackingData();

        backingData.getPivot().ifPresent(cubePivot -> backingData.getRotation().ifPresent(cubeRotation -> {
            double pivotX = -cubePivot.x;
            double pivotY = cubePivot.y;
            double pivotZ = cubePivot.z;

            cubeMatrix.translate(pivotX, pivotY, pivotZ);

            if (cubeRotation.z != 0.0D) cubeMatrix.rotateZ(Math.toRadians(cubeRotation.z)); // See: Geckolib4's RenderUtil
            if (cubeRotation.y != 0.0D) cubeMatrix.rotateY(Math.toRadians(-cubeRotation.y));
            if (cubeRotation.x != 0.0D) cubeMatrix.rotateX(Math.toRadians(-cubeRotation.x));

            cubeMatrix.translate(-pivotX, -pivotY, -pivotZ);
        }));

        return cubeMatrix;
    }

    @Override
    public boolean allowsLocalClipping() {
        return allowLocalClipping;
    }

    @Override
    public void setAllowsLocalClipping(boolean allowsLocalClipping) {
        this.allowLocalClipping = allowsLocalClipping;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        boolean toggle = isEnabled != enabled;

        this.isEnabled = enabled;

        if (toggle) owner.markDirty();
    }

    @Override
    public boolean hasCollision() {
        return hasCollision;
    }

    @Override
    public void setHasCollision(boolean hasCollision) {
        boolean toggle = this.hasCollision != hasCollision;

        this.hasCollision = hasCollision;

        if (toggle) owner.markDirty();
    }

    @Override
    public boolean shouldSync() {
        return shouldSync;
    }

    @Override
    public void setShouldSync(boolean shouldSync) {
        this.shouldSync = shouldSync;
    }
}
