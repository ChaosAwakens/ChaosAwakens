package io.github.chaosawakens.util;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;

public final class RenderUtil {

    private RenderUtil() {
        throw new IllegalAccessError("Attempted to construct Mixin class! (RenderUtil)");
    }

    public static void renderSkeletonHitbox(Skeleton skeleton, boolean renderGeneralShape) {

    }

    public static void renderSkeletonHitbox(Skeleton skeleton) {
        renderSkeletonHitbox(skeleton, true);
    }

    public static void renderBoneHitbox(Bone bone, boolean renderGeneralShape) {

    }

    public static void renderBoneHitbox(Bone bone) {
        renderBoneHitbox(bone, true);
    }

    public static void renderCubeHitbox(Cube cube) {

    }
}
