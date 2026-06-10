package io.github.chaosawakens.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape.*;
import io.github.chaosawakens.api.animation_resolver_system.faal.rendering.geckolib.model.GeckolibEntitySkeletonModel;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3d;

import java.util.List;
import java.util.Set;

public final class RenderUtil {
    public static final float GENERAL_SHAPE_RED = 1.0F;
    public static final float GENERAL_SHAPE_GREEN = 1.0F;
    public static final float GENERAL_SHAPE_BLUE = 1.0F;
    public static final float GENERAL_SHAPE_ALPHA = 0.45F;
    public static final float DISABLED_RED = 0.35F;
    public static final float DISABLED_GREEN = 0.35F;
    public static final float DISABLED_BLUE = 0.35F;
    public static final float NO_COLLISION_RED = 0.55F;
    public static final float NO_COLLISION_GREEN = 0.55F;
    public static final float NO_COLLISION_BLUE = 1.0F;
    public static final float SYNC_RED = 1.0F;
    public static final float SYNC_GREEN = 0.35F;
    public static final float SYNC_BLUE = 0.15F;
    public static final float DEFAULT_ALPHA = 1.0F;
    public static final double MODEL_TO_RENDER_SCALE = ModelCoordinateData.MODEL_TO_WORLD_SCALE;
    public static final int[][] BOX_EDGE_INDICES = {
            {0, 1}, {0, 2}, {1, 3}, {2, 3},
            {4, 5}, {4, 6}, {5, 7}, {6, 7},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };
    public static final int[] HULL_FALLBACK_EDGE_LIMIT = {128}; // TODO Probably generalise this a bit more
    public static final int CSG_EDGE_DEDUP_GRID = 1_000_000; // TODO Revisit this and fine-tune, this is "good enough" but not adequate
    public static final float[][] BONE_COLORS = {
            {0.15F, 1.0F, 0.15F},
            {1.0F, 0.9F, 0.15F},
            {0.15F, 0.9F, 1.0F},
            {1.0F, 0.55F, 0.15F},
            {0.65F, 0.25F, 1.0F},
            {1.0F, 0.2F, 0.9F},
            {0.25F, 0.55F, 1.0F},
            {1.0F, 0.2F, 0.45F},
            {0.55F, 1.0F, 0.15F},
            {0.15F, 1.0F, 0.6F}
    };

    private RenderUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class! (RenderUtil)");
    }

    public static void renderSkeletonHitbox(Skeleton skeleton, PoseStack poseStack, VertexConsumer buffer) {
        renderSkeletonHitbox(skeleton, poseStack, buffer, true);
    }

    public static void renderSkeletonHitbox(Skeleton skeleton, PoseStack poseStack, VertexConsumer buffer, boolean renderGeneralShape) {
        renderSkeletonHitbox(skeleton, poseStack, buffer, null, renderGeneralShape);
    }

    public static void renderSkeletonHitbox(Skeleton skeleton, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, boolean renderGeneralShape) {
        if (skeleton == null || poseStack == null || buffer == null || skeleton.isEmpty()) return;

        for (Bone bone : skeleton.getAllBones()) {
            renderBoneHitbox(bone, poseStack, buffer, owner, false);
        }

        if (renderGeneralShape) renderGeneralSkeletonBounds(skeleton, poseStack, buffer, owner);
    }

    public static void renderSkeletonHitbox(Skeleton skeleton, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner) {
        renderSkeletonHitbox(skeleton, poseStack, buffer, owner, true);
    }

    public static void renderBoneHitbox(Bone bone, PoseStack poseStack, VertexConsumer buffer, boolean renderGeneralShape) {
        renderBoneHitbox(bone, poseStack, buffer, null, renderGeneralShape);
    }

    public static void renderBoneHitbox(Bone bone, PoseStack poseStack, VertexConsumer buffer) {
        renderBoneHitbox(bone, poseStack, buffer, true);
    }

    public static void renderBoneHitbox(Bone bone, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, boolean renderGeneralShape) {
        if (bone == null || poseStack == null || buffer == null) return;

        float[] color = colorForBone(bone);

        for (Cube cube : bone.getCubes()) {
            renderCubeHitbox(cube, poseStack, buffer, owner, color[0], color[1], color[2], DEFAULT_ALPHA);
        }

        if (renderGeneralShape) renderModelAabb(bone.getGeneralBounds(), poseStack, buffer, color[0], color[1], color[2], GENERAL_SHAPE_ALPHA);
    }

    public static void renderBoneHitbox(Bone bone, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner) {
        renderBoneHitbox(bone, poseStack, buffer, owner, true);
    }

    public static void renderCubeHitbox(Cube cube, PoseStack poseStack, VertexConsumer buffer) {
        renderCubeHitbox(cube, poseStack, buffer, null);
    }

    public static void renderCubeHitbox(Cube cube, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner) {
        float[] color = colorForCube(cube);
        
        renderCubeHitbox(cube, poseStack, buffer, owner, color[0], color[1], color[2], DEFAULT_ALPHA);
    }

    public static void renderCubeHitbox(Cube cube, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        if (cube == null || poseStack == null || buffer == null || !cube.isEnabled()) return;

        ModelCoordinateData coordinateData = cube.getCoordinateData();

        if (coordinateData == null) return;

        CollisionShape collisionShape = coordinateData.getCollisionShape();
        List<Vector3d> modelVertices = coordinateData.getModelSpaceVertices();

        if (modelVertices.isEmpty()) return;

        if (collisionShape instanceof CSGUnionShape csgUnionShape) renderCsgUnion(csgUnionShape, poseStack, buffer, owner, red, green, blue, alpha);
        else if (collisionShape instanceof ConvexHullShape convexHullShape) renderConvexHull(modelVertices, convexHullShape, poseStack, buffer, owner, red, green, blue, alpha);
        else renderGeneratedVertexWireframe(coordinateData, modelVertices, poseStack, buffer, owner, red, green, blue, alpha);
    }

    public static void renderGeneratedVertexWireframe(ModelCoordinateData coordinateData, List<Vector3d> vertices, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        CollisionShape collisionShape = coordinateData.getCollisionShape();

        switch (collisionShape.getShapeType()) {
            case BOX -> renderIndexedEdges(vertices, BOX_EDGE_INDICES, poseStack, buffer, owner, red, green, blue, alpha);
            case SPHERE -> renderSphere(vertices, poseStack, buffer, owner, red, green, blue, alpha);
            case CAPSULE -> renderCapsule(vertices, collisionShape, poseStack, buffer, owner, red, green, blue, alpha);
            case COMPOUND_UNION -> renderCompoundUnion(coordinateData, (CompoundUnionShape) collisionShape, poseStack, buffer, owner, red, green, blue, alpha);
            default -> renderAllPairs(vertices, HULL_FALLBACK_EDGE_LIMIT[0], poseStack, buffer, owner, red, green, blue, alpha);
        }
    }

    public static void renderSphere(List<Vector3d> vertices, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        if (vertices.size() < 4) {
            renderAllPairs(vertices, HULL_FALLBACK_EDGE_LIMIT[0], poseStack, buffer, owner, red, green, blue, alpha);
            return;
        }

        int bodyVertexCount = vertices.size() - 2;
        int segments = inferRingSegments(bodyVertexCount);

        if (segments <= 0) {
            renderAllPairs(vertices, HULL_FALLBACK_EDGE_LIMIT[0], poseStack, buffer, owner, red, green, blue, alpha);
            return;
        }

        int rings = bodyVertexCount / segments;
        int topPoleIndex = 0;
        int bottomPoleIndex = vertices.size() - 1;

        for (int ring = 0; ring < rings; ring++) {
            int ringStart = 1 + ring * segments;

            renderRing(vertices, ringStart, segments, poseStack, buffer, owner, red, green, blue, alpha);

            for (int segment = 0; segment < segments; segment++) {
                if (ring == 0) drawModelLine(vertices.get(topPoleIndex), vertices.get(ringStart + segment), poseStack, buffer, red, green, blue, alpha);

                if (ring < rings - 1) drawModelLine(vertices.get(ringStart + segment), vertices.get(ringStart + segments + segment), poseStack, buffer, red, green, blue, alpha);
                else drawModelLine(vertices.get(ringStart + segment), vertices.get(bottomPoleIndex), poseStack, buffer, red, green, blue, alpha);
            }
        }
    }

    public static void renderCapsule(List<Vector3d> vertices, CollisionShape collisionShape, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        int segments = collisionShape instanceof CapsuleShape capsuleShape
                ? capsuleShape.getRingSegments()
                : inferRingSegments(vertices.size());

        if (segments <= 0 || vertices.size() < segments * 2) {
            renderAllPairs(vertices, HULL_FALLBACK_EDGE_LIMIT[0], poseStack, buffer, owner, red, green, blue, alpha);
            return;
        }

        renderRing(vertices, 0, segments, poseStack, buffer, owner, red, green, blue, alpha);
        renderRing(vertices, segments, segments, poseStack, buffer, owner, red, green, blue, alpha);

        for (int segment = 0; segment < segments; segment++) {
            drawModelLine(vertices.get(segment), vertices.get(segments + segment), poseStack, buffer, red, green, blue, alpha);
        }

        int remaining = vertices.size() - segments * 2;
        int capStride = remaining / 2;

        for (int cap = 0; cap < 2; cap++) {
            int capStart = segments * 2 + cap * capStride;
            int capEnd = Math.min(vertices.size(), capStart + capStride);
            if (capStart >= capEnd) continue;

            int previousRingStart = cap == 0 ? 0 : segments;
            int ringStart = capStart;

            while (ringStart + segments <= capEnd - 1) {
                renderRing(vertices, ringStart, segments, poseStack, buffer, owner, red, green, blue, alpha);

                for (int segment = 0; segment < segments; segment++) {
                    drawModelLine(vertices.get(previousRingStart + segment), vertices.get(ringStart + segment), poseStack, buffer, red, green, blue, alpha);
                }

                previousRingStart = ringStart;
                ringStart += segments;
            }

            int poleIndex = capEnd - 1;

            if (poleIndex >= 0 && previousRingStart + segments <= vertices.size()) {
                for (int segment = 0; segment < segments; segment++) {
                    drawModelLine(vertices.get(previousRingStart + segment), vertices.get(poleIndex), poseStack, buffer, red, green, blue, alpha);
                }
            }
        }
    }

    public static void renderConvexHull(List<Vector3d> vertices, ConvexHullShape shape, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        IntList faceIndices = shape.getFaceIndices();

        if (faceIndices.isEmpty()) {
            renderAllPairs(vertices, HULL_FALLBACK_EDGE_LIMIT[0], poseStack, buffer, owner, red, green, blue, alpha);
            return;
        }

        Set<Long> drawnEdges = new ObjectOpenHashSet<>();

        for (int index = 0; index + 2 < faceIndices.size(); index += 3) {
            drawIndexedEdgeOnce(vertices, faceIndices.getInt(index), faceIndices.getInt(index + 1), drawnEdges, poseStack, buffer, owner, red, green, blue, alpha);
            drawIndexedEdgeOnce(vertices, faceIndices.getInt(index + 1), faceIndices.getInt(index + 2), drawnEdges, poseStack, buffer, owner, red, green, blue, alpha);
            drawIndexedEdgeOnce(vertices, faceIndices.getInt(index + 2), faceIndices.getInt(index), drawnEdges, poseStack, buffer, owner, red, green, blue, alpha);
        }
    }

    public static void renderCsgUnion(CSGUnionShape shape, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        Set<List<Long>> drawnEdges = new ObjectOpenHashSet<>();

        for (CSGUnionShape.Edge edge : shape.getEdges()) {
            drawModelLineDeduplicated(edge.a(), edge.b(), drawnEdges, poseStack, buffer, red, green, blue, alpha);
        }
    }

    public static void renderCompoundUnion(ModelCoordinateData parentCoordinateData, CompoundUnionShape shape, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        for (CompoundUnionShape.ChildShape child : shape.getChildren()) { // TODO This also needs to be redone whenever CUS supports non-box shapes
            ModelCoordinateData childData = new ModelCoordinateData(child.getShape());
            Vector3d min = child.getMinBounds();
            Vector3d max = child.getMaxBounds();

            childData.setBounds(min.x, min.y, min.z, max.x, max.y, max.z);
            childData.setBoneMatrix(parentCoordinateData.buildModelSpaceMatrix());

            renderGeneratedVertexWireframe(childData, childData.getModelSpaceVertices(), poseStack, buffer, owner, red, green, blue, alpha);
        }
    }

    public static void renderGeneralSkeletonBounds(Skeleton skeleton, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner) {
        if (owner != null) {
            AABB worldBounds = skeleton.getGeneralWorldBounds(owner);
            renderWorldAabb(worldBounds, poseStack, buffer, owner, GENERAL_SHAPE_RED, GENERAL_SHAPE_GREEN, GENERAL_SHAPE_BLUE, DEFAULT_ALPHA);
        }

        AABB modelBounds = skeleton.getGeneralModelBounds();
        renderModelAabb(modelBounds, poseStack, buffer, GENERAL_SHAPE_RED, GENERAL_SHAPE_GREEN, GENERAL_SHAPE_BLUE, GENERAL_SHAPE_ALPHA);
    }

    public static void renderBox(VertexConsumer buffer, PoseStack poseStack, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
        Matrix4f curPose = poseStack.last().pose();

        // Bottom face
        buffer.vertex(curPose, (float) minX, (float) minY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) minX, (float) minY, (float) maxZ).color(red, green, blue, alpha).endVertex();

        // Top face
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) minZ).color(red, green, blue, alpha).endVertex();

        // North face
        buffer.vertex(curPose, (float) minX, (float) minY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) minZ).color(red, green, blue, alpha).endVertex();

        // South face
        buffer.vertex(curPose, (float) minX, (float) minY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) maxZ).color(red, green, blue, alpha).endVertex();

        // West face
        buffer.vertex(curPose, (float) minX, (float) minY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) minX, (float) minY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) minX, (float) maxY, (float) minZ).color(red, green, blue, alpha).endVertex();

        // East face
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) minZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) maxY, (float) maxZ).color(red, green, blue, alpha).endVertex();
        buffer.vertex(curPose, (float) maxX, (float) minY, (float) maxZ).color(red, green, blue, alpha).endVertex();
    }

    public static void renderIndexedEdges(List<Vector3d> vertices, int[][] edgeIndices, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        for (int[] edge : edgeIndices) {
            if (edge[0] >= vertices.size() || edge[1] >= vertices.size()) continue;

            drawModelLine(vertices.get(edge[0]), vertices.get(edge[1]), poseStack, buffer, red, green, blue, alpha);
        }
    }

    public static void renderRing(List<Vector3d> vertices, int startIndex, int segmentCount, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        if (segmentCount <= 1 || startIndex < 0 || startIndex + segmentCount > vertices.size()) return;

        for (int segment = 0; segment < segmentCount; segment++) {
            drawModelLine(vertices.get(startIndex + segment), vertices.get(startIndex + (segment + 1) % segmentCount), poseStack, buffer, red, green, blue, alpha);
        }
    }

    public static void renderAllPairs(List<Vector3d> vertices, int maxEdges, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        int drawn = 0;

        for (int i = 0; i < vertices.size() && drawn < maxEdges; i++) {
            for (int j = i + 1; j < vertices.size() && drawn < maxEdges; j++) {
                drawModelLine(vertices.get(i), vertices.get(j), poseStack, buffer, red, green, blue, alpha);
                drawn++;
            }
        }
    }

    public static void drawIndexedEdgeOnce(List<Vector3d> vertices, int firstIndex, int secondIndex, Set<Long> drawnEdges, PoseStack poseStack, VertexConsumer buffer, @Nullable Entity owner, float red, float green, float blue, float alpha) {
        if (firstIndex < 0 || secondIndex < 0 || firstIndex >= vertices.size() || secondIndex >= vertices.size()) return;

        int min = Math.min(firstIndex, secondIndex);
        int max = Math.max(firstIndex, secondIndex);
        long key = (((long) min) << 32) | (max & 0xFFFFFFFFL);

        if (drawnEdges.add(key)) drawModelLine(vertices.get(firstIndex), vertices.get(secondIndex), poseStack, buffer, red, green, blue, alpha);
    }

    public static void drawModelLineDeduplicated(Vector3d first, Vector3d second, Set<List<Long>> drawnEdges, PoseStack poseStack, VertexConsumer buffer, float red, float green, float blue, float alpha) {
        Vector3d renderFirst = toRenderPosition(first);
        Vector3d renderSecond = toRenderPosition(second);
        List<Long> key = edgeKey(renderFirst, renderSecond);

        if (drawnEdges.add(key)) drawLine(renderFirst, renderSecond, poseStack, buffer, red, green, blue, alpha);
    }

    public static List<Long> edgeKey(Vector3d first, Vector3d second) {
        long ax = Math.round(first.x * CSG_EDGE_DEDUP_GRID);
        long ay = Math.round(first.y * CSG_EDGE_DEDUP_GRID);
        long az = Math.round(first.z * CSG_EDGE_DEDUP_GRID);
        long bx = Math.round(second.x * CSG_EDGE_DEDUP_GRID);
        long by = Math.round(second.y * CSG_EDGE_DEDUP_GRID);
        long bz = Math.round(second.z * CSG_EDGE_DEDUP_GRID);

        return ax < bx || (ax == bx && ay < by) || (ax == bx && ay == by && az <= bz)
                ? ObjectArrayList.of(ax, ay, az, bx, by, bz)
                : ObjectArrayList.of(bx, by, bz, ax, ay, az);
    }

    public static void drawModelLine(Vector3d first, Vector3d second, PoseStack poseStack, VertexConsumer buffer, float red, float green, float blue, float alpha) {
        drawLine(toRenderPosition(first), toRenderPosition(second), poseStack, buffer, red, green, blue, alpha);
    }

    public static void drawLine(Vector3d first, Vector3d second, PoseStack poseStack, VertexConsumer buffer, float red, float green, float blue, float alpha) {
        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        float x0 = (float) first.x;
        float y0 = (float) first.y;
        float z0 = (float) first.z;
        float x1 = (float) second.x;
        float y1 = (float) second.y;
        float z1 = (float) second.z;
        float nx = x1 - x0;
        float ny = y1 - y0;
        float nz = z1 - z0;
        float length = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);

        if (length > 0.0F) {
            nx /= length;
            ny /= length;
            nz /= length;
        }

        buffer.vertex(pose, x0, y0, z0).color(red, green, blue, alpha).normal(normal, nx, ny, nz).endVertex();
        buffer.vertex(pose, x1, y1, z1).color(red, green, blue, alpha).normal(normal, nx, ny, nz).endVertex();
    }

    public static Vector3d toRenderPosition(Vector3d modelPosition) {
        return new Vector3d(modelPosition).mul(MODEL_TO_RENDER_SCALE);
    }

    public static void renderModelAabb(AABB bounds, PoseStack poseStack, VertexConsumer buffer, float red, float green, float blue, float alpha) {
        if (bounds == null) return;

        LevelRenderer.renderLineBox(poseStack, buffer,
                bounds.minX * MODEL_TO_RENDER_SCALE,
                bounds.minY * MODEL_TO_RENDER_SCALE,
                bounds.minZ * MODEL_TO_RENDER_SCALE,
                bounds.maxX * MODEL_TO_RENDER_SCALE,
                bounds.maxY * MODEL_TO_RENDER_SCALE,
                bounds.maxZ * MODEL_TO_RENDER_SCALE,
                red, green, blue, alpha);
    }

    public static void renderWorldAabb(AABB bounds, PoseStack poseStack, VertexConsumer buffer, Entity owner, float red, float green, float blue, float alpha) {
        if (bounds == null) return;

        LevelRenderer.renderLineBox(poseStack, buffer, bounds.move(-owner.getX(), -owner.getY(), -owner.getZ()), red, green, blue, alpha);
    }

    public static int inferRingSegments(int vertexCount) {
        if (vertexCount <= 0) return -1;

        for (int candidate = 32; candidate >= 4; candidate--) {
            if (vertexCount % candidate == 0) return candidate;
        }

        return -1;
    }

    public static float[] colorForBone(Bone bone) {
        if (bone == null || !bone.isEnabled()) return new float[]{DISABLED_RED, DISABLED_GREEN, DISABLED_BLUE};

        return BONE_COLORS[Math.floorMod(bone.getIndex(), BONE_COLORS.length)];
    }

    public static float[] colorForCube(Cube cube) {
        if (cube == null || !cube.isEnabled()) return new float[]{DISABLED_RED, DISABLED_GREEN, DISABLED_BLUE};
        if (cube.shouldSync()) return new float[]{SYNC_RED, SYNC_GREEN, SYNC_BLUE};
        if (!cube.hasCollision()) return new float[]{NO_COLLISION_RED, NO_COLLISION_GREEN, NO_COLLISION_BLUE};

        return cube.getParentBone()
                .map(RenderUtil::colorForBone)
                .orElseGet(() -> BONE_COLORS[Math.floorMod(cube.getIndex(), BONE_COLORS.length)]);
    }

    public static <AE extends Entity & MappableHitboxOwner> GeckolibEntitySkeletonModel<AE> resolveModelFor(AE animatableOwner) {
        return null; // TODO
    }
}
