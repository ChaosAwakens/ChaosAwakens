package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import io.github.chaosawakens.api.animation_resolver_system.pipeline.parsing.base.model.ModelInfo;
import io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model.shape.ConvexHullShape;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3d;

import java.util.*;

public interface Skeleton { // Topological + Upward Traversal

    private static boolean skeletonCubeFullyContained(List<Vector3d> corners,
                                                      ModelCoordinateData obb) {
        Vector3d center = obb.getModelObbCenter();
        Vector3d[] axes = obb.getModelObbAxes();
        Vector3d half = obb.getModelObbHalfExtents();

        for (Vector3d c : corners) {
            Vector3d d = new Vector3d(c).sub(center);
            if (Math.abs(d.dot(axes[0])) > half.x + ModelCoordinateData.EPSILON) return false;
            if (Math.abs(d.dot(axes[1])) > half.y + ModelCoordinateData.EPSILON) return false;
            if (Math.abs(d.dot(axes[2])) > half.z + ModelCoordinateData.EPSILON) return false;
        }
        return true;
    }

    Set<Bone> getAllBones();

    Set<Bone> getRootBones();

    Map<String, Bone> getBonesByName();

    Map<Bone, Set<Bone>> getBoneBranches();

    Optional<Bone> getBoneByName(String boneName);

    void initializeBoneTree(ModelInfo modelInfo);

    void updateBranch(Bone bone);

    void refresh(ModelInfo updatedModelInfo);

    default AABB getGeneralBounds() {
        return getCoordinateData(false).getWorldAABB();
    }

    default ModelCoordinateData getCoordinateData(boolean cleanupBoneCollisions) {
        // Collect all cube MCDs from every bone into a flat list (with per-bone model-space verts)
        List<ModelCoordinateData> allCubeMcds = new ArrayList<>();
        List<List<Vector3d>> allCubeVerts = new ArrayList<>();

        for (Bone bone : getAllBones()) {
            for (Cube cube : bone.getCubes()) {
                if (!cube.isEnabled() || !cube.hasCollision()) continue;
                ModelCoordinateData mcd = cube.getCoordinateData();
                if (mcd == null) continue;
                List<Vector3d> verts = mcd.getModelSpaceVertices();
                if (verts.isEmpty()) continue;
                allCubeMcds.add(mcd);
                allCubeVerts.add(new ArrayList<>(verts));
            }
        }

        if (allCubeVerts.isEmpty()) return null;

        // Skeleton-level cross-bone OBB containment pruning
        if (cleanupBoneCollisions && allCubeVerts.size() > 1) {
            int n = allCubeVerts.size();
            boolean[] pruned = new boolean[n];
            for (int a = 0; a < n; a++) {
                if (pruned[a]) continue;
                for (int b = 0; b < n; b++) {
                    if (a == b || pruned[b]) continue;
                    if (skeletonCubeFullyContained(allCubeVerts.get(a), allCubeMcds.get(b))) {
                        pruned[a] = true;
                        break;
                    }
                }
            }
            List<List<Vector3d>> surviving = new ArrayList<>();
            for (int i = 0; i < n; i++)
                if (!pruned[i]) surviving.add(allCubeVerts.get(i));
            allCubeVerts = surviving;
        }

        List<Vector3d> allPts = new ArrayList<>();
        for (List<Vector3d> verts : allCubeVerts) allPts.addAll(verts);

        if (allPts.size() < 4) return null;

        ConvexHullShape skeletonHull = ConvexHullShape.fromPointCloud(allPts);

        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;
        for (Vector3d v : allPts) {
            minX = Math.min(minX, v.x);
            minY = Math.min(minY, v.y);
            minZ = Math.min(minZ, v.z);
            maxX = Math.max(maxX, v.x);
            maxY = Math.max(maxY, v.y);
            maxZ = Math.max(maxZ, v.z);
        }

        ModelCoordinateData result = new ModelCoordinateData(skeletonHull);
        result.setBounds(minX, minY, minZ, maxX, maxY, maxZ);
        return result;
    }
}
