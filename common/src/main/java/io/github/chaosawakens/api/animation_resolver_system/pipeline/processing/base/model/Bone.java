package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape.ConvexHullShape;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Bone {

    private static List<List<org.joml.Vector3d>> pruneContainedCubes(
            List<List<org.joml.Vector3d>> cubeVerts, List<Cube> cubes) {
        int n = cubeVerts.size();
        boolean[] pruned = new boolean[n];

        for (int a = 0; a < n; a++) {
            if (pruned[a]) continue;
            for (int b = 0; b < n; b++) {
                if (a == b || pruned[b]) continue;
                ModelCoordinateData mcdB = cubes.get(b).getCoordinateData();
                if (mcdB != null && isFullyContainedInOBB(cubeVerts.get(a), mcdB)) {
                    pruned[a] = true;
                    break;
                }
            }
        }

        List<List<org.joml.Vector3d>> result = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++)
            if (!pruned[i]) result.add(cubeVerts.get(i));
        return result;
    }

    private static boolean isFullyContainedInOBB(List<org.joml.Vector3d> corners,
                                                 ModelCoordinateData obb) {
        org.joml.Vector3d center = obb.getModelObbCenter();
        org.joml.Vector3d[] axes = obb.getModelObbAxes();
        org.joml.Vector3d half = obb.getModelObbHalfExtents();

        for (org.joml.Vector3d c : corners) {
            org.joml.Vector3d d = new org.joml.Vector3d(c).sub(center);
            if (Math.abs(d.dot(axes[0])) > half.x + ModelCoordinateData.EPSILON) return false;
            if (Math.abs(d.dot(axes[1])) > half.y + ModelCoordinateData.EPSILON) return false;
            if (Math.abs(d.dot(axes[2])) > half.z + ModelCoordinateData.EPSILON) return false;
        }
        return true;
    }

    String getName();

    Optional<Bone> getParentBone();

    List<Cube> getCubes();

    default void enable() {
        getCubes().forEach(Cube::enable);
    }

    default void disable() {
        getCubes().forEach(Cube::disable);
    }

    default AABB getGeneralBounds() {
        return getCoordinateData(false).getWorldAABB();
    }

    default ModelCoordinateData getCoordinateData(boolean cleanupCubeCollisions) {
        List<Cube> cubes = getCubes().stream()
                .filter(Cube::isEnabled)
                .filter(Cube::hasCollision)
                .toList();
        if (cubes.isEmpty()) return null;

        // Collect model-space vertices per cube (post localTransform*boneMatrix, pre entityTransform)
        List<List<org.joml.Vector3d>> cubeVerts = new java.util.ArrayList<>();
        for (Cube cube : cubes) {
            ModelCoordinateData mcd = cube.getCoordinateData();
            if (mcd == null) continue;
            List<org.joml.Vector3d> verts = mcd.getModelSpaceVertices();
            if (!verts.isEmpty()) cubeVerts.add(new java.util.ArrayList<>(verts));
        }
        if (cubeVerts.isEmpty()) return null;

        // Optional: prune cubes whose volume is fully contained inside another cube's OBB
        if (cleanupCubeCollisions && cubeVerts.size() > 1) {
            cubeVerts = pruneContainedCubes(cubeVerts, cubes);
        }

        // Flatten to a point cloud and build convex hull
        List<org.joml.Vector3d> allPts = new java.util.ArrayList<>();
        for (List<org.joml.Vector3d> verts : cubeVerts) allPts.addAll(verts);

        ConvexHullShape hull = allPts.size() >= 4
                ? ConvexHullShape.fromPointCloud(allPts)
                : new ConvexHullShape(allPts);

        // Compute tight bounds over all points (required to satisfy boundsInitialized)
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;
        for (org.joml.Vector3d v : allPts) {
            minX = Math.min(minX, v.x);
            minY = Math.min(minY, v.y);
            minZ = Math.min(minZ, v.z);
            maxX = Math.max(maxX, v.x);
            maxY = Math.max(maxY, v.y);
            maxZ = Math.max(maxZ, v.z);
        }

        // Hull vertices are already in model space — boneMatrix stays identity.
        // The caller applies the entity transform via computeWorldSpace(entityPos, yRot).
        ModelCoordinateData result = new ModelCoordinateData(hull);
        result.setBounds(minX, minY, minZ, maxX, maxY, maxZ);
        return result;
    }

    default boolean isLeaf() {
        return getCubes().isEmpty();
    }

    default boolean isHomogeneous() {
        return getCubes().isEmpty() || getCubes().stream().noneMatch(Cube::isEnabled);
    }

    default boolean isRoot() {
        return getParentBone().isEmpty();
    }

    default Set<Bone> getDirectParents() { // Sorted bottom -> top
        Set<Bone> directParents = new ObjectLinkedOpenHashSet<>();
        Optional<Bone> parentBone = getParentBone();

        while (parentBone.isPresent()) {
            directParents.add(parentBone.get());
            parentBone = parentBone.get().getParentBone();
        }

        return directParents;
    }
}
