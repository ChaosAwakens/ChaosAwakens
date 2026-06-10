package io.github.chaosawakens.api.animation_resolver_system.faal.rendering.geckolib.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.chaosawakens.api.animation_resolver_system.faal.hitbox.MappableHitboxOwner;
import io.github.chaosawakens.api.animation_resolver_system.faal.rendering.base.SkeletonRenderer;
import io.github.chaosawakens.api.animation_resolver_system.faal.rendering.geckolib.model.GeckolibEntitySkeletonModel;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeDirectionalUVData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelCubeUVData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelMetadata;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Bone;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Cube;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.ModelCoordinateData;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.Skeleton;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape.CollisionShape;
import io.github.chaosawakens.util.RenderUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.*;

import java.lang.Math;
import java.util.List;

public class GeckolibEntitySkeletonRenderer<AE extends Entity & MappableHitboxOwner> extends EntityRenderer<AE> implements SkeletonRenderer<AE> {
    private static final double MODEL_TO_RENDER_SCALE = RenderUtil.MODEL_TO_RENDER_SCALE;
    private static final double DEFAULT_TEXTURE_SIZE = 64.0D;
    private static final int FACE_VERTEX_COUNT = 4;
    private static final int FACE_DOWN = 0;
    private static final int FACE_UP = 1;
    private static final int FACE_NORTH = 2;
    private static final int FACE_SOUTH = 3;
    private static final int FACE_WEST = 4;
    private static final int FACE_EAST = 5;
    private static final int[][] BOX_FACE_INDICES = {
            {0, 1, 5, 4}, // down (-Y)
            {6, 7, 3, 2}, // up (+Y)
            {2, 3, 1, 0}, // north (-Z)
            {7, 6, 4, 5}, // south (+Z)
            {6, 2, 0, 4}, // west (-X)
            {3, 7, 5, 1}  // east (+X)
    };
    protected GeckolibEntitySkeletonModel<AE> parentModel;
    protected AE ownerAnimatable;

    public GeckolibEntitySkeletonRenderer(EntityRendererProvider.Context context, GeckolibEntitySkeletonModel<AE> parentSkeletonModel) {
        super(context);

        this.parentModel = parentSkeletonModel == null ? new GeckolibEntitySkeletonModel<>() : parentSkeletonModel;
    }

    public GeckolibEntitySkeletonRenderer(EntityRendererProvider.Context context) {
        this(context, null);
    }

    @Override
    public void render(AE entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.ownerAnimatable = entity;

        Skeleton skeleton = entity.getSkeleton();
        parentModel.manageAnimationState(skeleton, entity, null, partialTick, entityYaw, entity.getXRot());
        skeleton.refreshStructure();

        renderSkeleton(poseStack, skeleton, buffer, partialTick, packedLight, LivingEntityRenderer.getOverlayCoords((LivingEntity) entity, 0), 1.0F, 1.0F, 1.0F, 1.0F);

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(AE ae) {
        return parentModel.getTextureLocation(ae);
    }

    @Override
    public @NotNull AE getMappableHitboxOwner() {
        if (ownerAnimatable == null) throw new IllegalStateException("Attempted to render skeleton geometry without an owner animatable!");

        return ownerAnimatable;
    }

    @Override
    public void renderCube(PoseStack targetStack, Cube targetCube, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (targetCube == null || !targetCube.isEnabled()) return;

        ModelCoordinateData coordinateData = targetCube.getCoordinateData();
        CollisionShape collisionShape = coordinateData.getCollisionShape();

        if (collisionShape.getShapeType() != CollisionShape.ShapeType.BOX) return; // TODO Extremely low-priority, but maybe later down the line, support other shape types as well(?)

        List<Vector3d> modelVertices = createBoxModelVertices(coordinateData);

        if (modelVertices.size() < 8) return;

        ModelMetadata metadata = getMappableHitboxOwner().getSkeleton().getTemplate().getBackingInfo().getGeometryInfo().<ModelMetadata>getMetadata()
                .orElse(null);

        double textureWidth = metadata == null ? DEFAULT_TEXTURE_SIZE : Math.max(1.0D, metadata.getTextureWidth());
        double textureHeight = metadata == null ? DEFAULT_TEXTURE_SIZE : Math.max(1.0D, metadata.getTextureHeight());

        VertexConsumer consumer = bufferSource.getBuffer(parentModel.getRenderType(getMappableHitboxOwner(), getTextureLocation(getMappableHitboxOwner())));
        ModelCubeUVData uvData = targetCube.getBackingData().getUVData();

        boolean boxUv = uvData.isHomogenous(); // TODO Fix
        boolean mirrored = targetCube.getBackingData().isMirrored();

        for (int face = 0; face < BOX_FACE_INDICES.length; face++) {
            renderFace(targetStack, consumer, modelVertices, resolveFaceIndices(face, mirrored, boxUv), resolveDirectionalUv(uvData, targetCube, face), mirrored, textureWidth, textureHeight, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

    @Override
    public void renderBone(PoseStack targetStack, Skeleton parentSkeleton, Bone targetBone, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (targetBone == null || !targetBone.isEnabled()) return;

        for (Cube cube : targetBone.getCubes()) {
            renderCube(targetStack, cube, bufferSource, partialTicks, packedLight, packedOverlay, red, green, blue, alpha);
        }

        renderChildBones(targetStack, parentSkeleton, targetBone, bufferSource, partialTicks, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void renderChildBones(PoseStack targetStack, Skeleton parentSkeleton, Bone targetSourceBone, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (targetSourceBone == null) return;

        for (Bone childBone : parentSkeleton.getChildren(targetSourceBone)) {
            renderBone(targetStack, parentSkeleton, childBone, bufferSource, partialTicks, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

    @Override
    public void renderSkeleton(PoseStack targetStack, Skeleton targetSkeleton, MultiBufferSource bufferSource, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (targetSkeleton == null || targetSkeleton.isEmpty()) return;

        targetStack.pushPose();

        for (Bone rootBone : targetSkeleton.getRootBones()) {
            renderBone(targetStack, targetSkeleton, rootBone, bufferSource, partialTicks, packedLight, packedOverlay, red, green, blue, alpha);
        }

        targetStack.popPose();
    }

    private static void renderFace(PoseStack targetStack, VertexConsumer consumer, List<Vector3d> vertices, int[] faceIndices, ModelCubeDirectionalUVData uvInfo, boolean mirrored, double textureWidth, double textureHeight, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (faceIndices.length != FACE_VERTEX_COUNT) return;

        PoseStack.Pose pose = targetStack.last();
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();
        Vector3d normal = computeFaceNormal(vertices, faceIndices);
        float[][] uvs = computeFaceUvs(uvInfo, textureWidth, textureHeight, mirrored);

        for (int i = 0; i < FACE_VERTEX_COUNT; i++) {
            Vector3d vertex = vertices.get(faceIndices[i]);
            float x = (float) (vertex.x * MODEL_TO_RENDER_SCALE);
            float y = (float) (vertex.y * MODEL_TO_RENDER_SCALE);
            float z = (float) (vertex.z * MODEL_TO_RENDER_SCALE);

            consumer.vertex(poseMatrix, x, y, z)
                    .color(red, green, blue, alpha)
                    .uv(uvs[i][0], uvs[i][1])
                    .overlayCoords(packedOverlay)
                    .uv2(packedLight)
                    .normal(normalMatrix, (float) normal.x, (float) normal.y, (float) normal.z)
                    .endVertex();
        }
    }

    private static List<Vector3d> createBoxModelVertices(ModelCoordinateData coordinateData) {
        Matrix4d modelTransform = coordinateData.buildModelSpaceMatrix();
        Vector3d min = coordinateData.getOriginalMin();
        Vector3d max = coordinateData.getOriginalMax();

        return ObjectArrayList.of(
                modelTransform.transformPosition(new Vector3d(min.x, min.y, min.z)),
                modelTransform.transformPosition(new Vector3d(max.x, min.y, min.z)),
                modelTransform.transformPosition(new Vector3d(min.x, max.y, min.z)),
                modelTransform.transformPosition(new Vector3d(max.x, max.y, min.z)),
                modelTransform.transformPosition(new Vector3d(min.x, min.y, max.z)),
                modelTransform.transformPosition(new Vector3d(max.x, min.y, max.z)),
                modelTransform.transformPosition(new Vector3d(min.x, max.y, max.z)),
                modelTransform.transformPosition(new Vector3d(max.x, max.y, max.z))
        );
    }

    private static Vector3d computeFaceNormal(List<Vector3d> vertices, int[] faceIndices) {
        Vector3d first = vertices.get(faceIndices[0]);
        Vector3d second = vertices.get(faceIndices[1]);
        Vector3d third = vertices.get(faceIndices[2]);
        Vector3d edgeA = new Vector3d(second).sub(first);
        Vector3d edgeB = new Vector3d(third).sub(first);
        Vector3d normal = edgeA.cross(edgeB);

        if (normal.lengthSquared() <= ModelCoordinateData.EPSILON) return new Vector3d(0.0D, 1.0D, 0.0D);

        return normal.normalize();
    }

    private static int[] resolveFaceIndices(int face, boolean mirrored, boolean boxUv) {
        if (!mirrored) return BOX_FACE_INDICES[face];

        return switch (face) {
            case FACE_DOWN -> BOX_FACE_INDICES[FACE_UP];
            case FACE_UP -> BOX_FACE_INDICES[FACE_DOWN];
            case FACE_WEST -> BOX_FACE_INDICES[FACE_EAST];
            case FACE_EAST -> BOX_FACE_INDICES[FACE_WEST];
            default -> BOX_FACE_INDICES[face];
        };
    }

    private static ModelCubeDirectionalUVData resolveDirectionalUv(ModelCubeUVData uvData, Cube cube, int face) {
        if (!uvData.isHomogenous()) {
            return switch (face) {
                case FACE_DOWN -> uvData.getDownUVInfo();
                case FACE_UP -> uvData.getUpUVInfo();
                case FACE_NORTH -> uvData.getNorthUVInfo();
                case FACE_SOUTH -> uvData.getSouthUVInfo();
                case FACE_WEST -> uvData.getWestUVInfo();
                case FACE_EAST -> uvData.getEastUVInfo();
                default -> uvData.getNorthUVInfo();
            };
        }

        Vector2d origin = uvData.getNorthUVInfo().getUVCoords();
        Vector3d cubeSize = cube.getBackingData().getSize();
        double width = Math.abs(cubeSize.x);
        double height = Math.abs(cubeSize.y);
        double depth = Math.abs(cubeSize.z);
        double u = origin.x;
        double v = origin.y;

        return switch (face) {
            case FACE_DOWN -> new ResolvedDirectionalUVData(new Vector2d(u + depth + width, v + depth), new Vector2d(width, -depth));
            case FACE_UP -> new ResolvedDirectionalUVData(new Vector2d(u + depth, v), new Vector2d(width, depth));
            case FACE_NORTH -> new ResolvedDirectionalUVData(new Vector2d(u + depth, v + depth), new Vector2d(width, height));
            case FACE_SOUTH -> new ResolvedDirectionalUVData(new Vector2d(u + depth + width + depth, v + depth), new Vector2d(width, height));
            case FACE_WEST -> new ResolvedDirectionalUVData(new Vector2d(u + depth + width, v + depth), new Vector2d(depth, height));
            case FACE_EAST -> new ResolvedDirectionalUVData(new Vector2d(u, v + depth), new Vector2d(depth, height));
            default -> new ResolvedDirectionalUVData(origin, new Vector2d(width, height));
        };
    }

    private static float[][] computeFaceUvs(ModelCubeDirectionalUVData uvInfo, double textureWidth, double textureHeight, boolean mirrored) {
        Vector2d uvCoords = uvInfo.getUVCoords();
        Vector2d uvSize = uvInfo.getUVSize();
        float u = (float) (uvCoords.x / textureWidth);
        float v = (float) (uvCoords.y / textureHeight);
        float uWidth = (float) ((uvCoords.x + uvSize.x) / textureWidth);
        float vHeight = (float) ((uvCoords.y + uvSize.y) / textureHeight);

        if (!mirrored) {
            float originalWidth = uWidth;
            uWidth = u;
            u = originalWidth;
        }

        return new float[][]{
                {u, v},
                {uWidth, v},
                {uWidth, vHeight},
                {u, vHeight}
        };
    }

    private record ResolvedDirectionalUVData(Vector2d uvCoords, Vector2d uvSize) implements ModelCubeDirectionalUVData {
        @Override
        public Vector2d getUVCoords() {
            return uvCoords;
        }

        @Override
        public Vector2d getUVSize() {
            return uvSize;
        }
    }
}
