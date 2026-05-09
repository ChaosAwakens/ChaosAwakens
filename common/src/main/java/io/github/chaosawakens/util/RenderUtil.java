package io.github.chaosawakens.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.rendering.geckolib.model.GeckolibEntitySkeletonModel;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix4f;

public final class RenderUtil {

    private RenderUtil() {
        throw new IllegalAccessError("Attempted to construct Mixin class! (RenderUtil)");
    }

    public static void renderSkeletonHitbox(Skeleton skeleton, PoseStack poseStack, VertexConsumer buffer, boolean renderGeneralShape) {

    }

    public static void renderSkeletonHitbox(Skeleton skeleton, PoseStack poseStack, VertexConsumer buffer) {
        renderSkeletonHitbox(skeleton, poseStack, buffer, true);
    }

    public static void renderBoneHitbox(Bone bone, boolean renderGeneralShape) {

    }

    public static void renderBoneHitbox(Bone bone) {
        renderBoneHitbox(bone, true);
    }

    public static void renderCubeHitbox(Cube cube) {
        ModelCoordinateData coordinateData = cube.getCoordinateData();
    }

    public static void renderBox(VertexConsumer buffer, PoseStack poseStack, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float r, float g, float b, float a) {
        Matrix4f curPose = poseStack.last().pose();

        // Bottom face
        buffer.vertex(curPose, (float) minX, (float) minY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) minX, (float) minY, (float) maxZ).color(r, g, b, a).endVertex();

        // Top face
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) minZ).color(r, g, b, a).endVertex();

        // North face
        buffer.vertex(curPose, (float) minX, (float) minY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) minZ).color(r, g, b, a).endVertex();

        // South face
        buffer.vertex(curPose, (float) minX, (float) minY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) maxZ).color(r, g, b, a).endVertex();

        // West face
        buffer.vertex(curPose, (float) minX, (float) minY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) minX, (float) minY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) minZ).color(r, g, b, a).endVertex();

        // East face
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) minZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) maxZ).color(r, g, b, a).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) maxZ).color(r, g, b, a).endVertex();
    }

    public static <AE extends Entity & MappableHitboxOwner> GeckolibEntitySkeletonModel<AE> resolveModelFor(AE animatableOwner) {
        return null; // TODO
    }
}
