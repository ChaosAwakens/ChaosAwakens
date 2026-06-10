package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template.GeckolibBoneTemplate;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.geckolib.model.template.GeckolibSkeletonTemplate;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeckolibBone implements Bone {
    protected final GeckolibSkeleton owner;
    protected final int index;
    protected boolean enabled = true; // Weakly-linked(?) general flag to skip performance-intensive iterations of every single cube and whatnot
    @NotNull
    protected Vector3d curPivot;
    @Nullable
    protected Vector3d curRotation;
    @Nullable
    protected Vector3d curScale;

    public GeckolibBone(GeckolibSkeleton owner, int index) {
        this.owner = owner;
        this.index = index;
        this.curPivot = new Vector3d(getBackingData().getPivot());
        this.curRotation = getBackingData().getRotation()
                .map(Vector3d::new)
                .orElse(null);
    }

    @Override
    public @NotNull GeckolibBoneTemplate getTemplate() {
        return owner.getTemplate().boneTemplateAt(index);
    }

    @Override
    public Optional<GeckolibBone> getParentBone() {
        int parentIndex = getTemplate().getParentIndex();

        return parentIndex == GeckolibSkeletonTemplate.ROOT_INDEX
                ? Optional.empty()
                : Optional.of(owner.getBone(parentIndex));
    }

    @Override
    public List<GeckolibCube> getCubes() {
        return Arrays.stream(getTemplate().getCubeIndices())
                .mapToObj(owner::getCube)
                .collect(Collectors.toCollection(ObjectArrayList::new));
    }

    @Override
    public @NotNull Vector3d getPivot() {
        return curPivot;
    }

    @Override
    public Optional<Vector3d> getRotation() {
        return Optional.ofNullable(curRotation);
    }

    @Override
    public Optional<Vector3d> getScale() {
        return Optional.ofNullable(curScale);
    }

    @Override
    public void setScale(Vector3dc scale) {
        this.curScale = scale == null ? null : new Vector3d(scale);
    }

    @Override
    public void setScale(double scaleX, double scaleY, double scaleZ) {
        if (curScale == null) setScale(new Vector3d(scaleX, scaleY, scaleZ));
        else curScale.set(scaleX, scaleY, scaleZ);
    }

    @Override
    public AABB getGeneralBounds() {
        return null;
    }

    @Override
    public void enable() {
        Bone.super.enable();

        this.enabled = true;
    }

    @Override
    public void disable() {
        Bone.super.disable();

        this.enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled || Bone.super.isEnabled();
    }
}
