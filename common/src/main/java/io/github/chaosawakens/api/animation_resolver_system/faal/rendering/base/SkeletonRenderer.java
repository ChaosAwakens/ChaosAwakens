package io.github.chaosawakens.api.animation_resolver_system.faal.rendering.base;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import net.minecraft.client.renderer.MultiBufferSource;
import org.jetbrains.annotations.NotNull;

public interface SkeletonRenderer<MHO extends MappableHitboxOwner> {

    @NotNull
    MHO getMappableHitboxOwner();

    void renderCube(PoseStack targetStack, Cube targetCube, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
    void renderBone(PoseStack targetStack, Skeleton parentSkeleton, Bone targetBone, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
    void renderChildBones(PoseStack targetStack, Skeleton parentSkeleton, Bone targetSourceBone, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
    void renderSkeleton(PoseStack targetStack, Skeleton targetSkeleton, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha);
}
