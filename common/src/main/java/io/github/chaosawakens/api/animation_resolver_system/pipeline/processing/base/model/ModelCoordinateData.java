package io.github.chaosawakens.api.animation_resolver_system.pipeline.processing.base.model;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.joml.Matrix4d;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.util.List;

public class ModelCoordinateData {
    public static final float EPSILON = 1.0E-6F;
    public static final double TRANSFORMATION_SCALE = 1.0D / 16.0D;
    protected double minX, minY, minZ; // Local Bounds/Transformations
    protected double maxX, maxY, maxZ;
    protected double originX, originY, originZ;
    protected double pivotX, pivotY, pivotZ;
    protected double rotationX, rotationY, rotationZ;
    protected double scaleX, scaleY, scaleZ;
    protected Vector3d obbCenter; // OBB properties (still in local space btw)
    protected Vector3d obbHalfExtents;
    protected Quaterniond obbOrientation;
    protected Vector3d levelBasedMinBounds; // AABB-esque data for broad collision checks in the actual level
    protected Vector3d levelBasedMaxBounds;
    protected boolean needsUpdate = true; // Only used for level-bound updates (well, duh)

    public ModelCoordinateData() {
        this.minX = this.minY = this.minZ = Double.MAX_VALUE;
        this.maxX = this.maxY = this.maxZ = -Double.MAX_VALUE;
        this.originX = this.originY = this.originZ = 0.0D;
        this.pivotX = this.pivotY = this.pivotZ = 0.0D;
        this.rotationX = this.rotationY = this.rotationZ = 0.0D;
        this.scaleX = this.scaleY = this.scaleZ = 1.0D;
        this.obbCenter = new Vector3d();
        this.obbHalfExtents = new Vector3d();
        this.obbOrientation = new Quaterniond();
        this.levelBasedMinBounds = new Vector3d();
        this.levelBasedMaxBounds = new Vector3d();
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public Vector3d getMinBounds() {
        return new Vector3d(minX, minY, minZ);
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public Vector3d getMaxBounds() {
        return new Vector3d(maxX, maxY, maxZ);
    }

    public double getOriginX() {
        return originX;
    }

    public double getOriginY() {
        return originY;
    }

    public double getOriginZ() {
        return originZ;
    }

    public Vector3d getOrigin() {
        return new Vector3d(originX, originY, originZ);
    }

    public double getPivotX() {
        return pivotX;
    }

    public double getPivotY() {
        return pivotY;
    }

    public double getPivotZ() {
        return pivotZ;
    }

    public Vector3d getPivot() {
        return new Vector3d(pivotX, pivotY, pivotZ);
    }

    public double getRotationX() {
        return rotationX;
    }

    public double getRotationY() {
        return rotationY;
    }

    public double getRotationZ() {
        return rotationZ;
    }

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public double getScaleZ() {
        return scaleZ;
    }

    public void setMin(double minX, double minY, double minZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;

        markDirty();
    }

    public void setMax(double maxX, double maxY, double maxZ) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        markDirty();
    }

    public void setOrigin(double originX, double originY, double originZ) {
        this.originX = originX;
        this.originY = originY;
        this.originZ = originZ;

        markDirty();
    }

    public void setPivot(double pivotX, double pivotY, double pivotZ) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.pivotZ = pivotZ;

        markDirty();
    }

    public void setRotation(double rotationX, double rotationY, double rotationZ) {
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;

        markDirty();
    }

    public void setScale(double scaleX, double scaleY, double scaleZ) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;

        markDirty();
    }

    public Vector3d getObbCenter() {
        if (needsUpdate) {
            this.needsUpdate = false;

            updateOBBData();
        }
        return obbCenter;
    }

    public Vector3d getObbHalfExtents() {
        if (needsUpdate) {
            this.needsUpdate = false;

            updateOBBData();
        }
        return obbHalfExtents;
    }

    public Quaterniond getObbOrientation() {
        if (needsUpdate) {
            this.needsUpdate = false;

            updateOBBData();
        }
        return obbOrientation;
    }

    public List<Vector3d> getVertices() {
        return ObjectArrayList.of();
    }

    public List<Vector3d> getLevelBasedVertices() {
        return ObjectArrayList.of();
    }

    public Vector3d getLevelBasedOrigin() {
        return new Vector3d(); // TODO
    }

    public Vector3d getLevelBasedPivot() {
        return new Vector3d(); // TODO
    }

    public Vector3d getLevelBasedMinBounds() {
        if (needsUpdate) {
            this.needsUpdate = false;

            rebuildTransformationMatrix();
        }

        return levelBasedMinBounds;
    }

    public Vector3d getLevelBasedMaxBounds() {
        if (needsUpdate) {
            this.needsUpdate = false;

            rebuildTransformationMatrix();
        }

        return levelBasedMaxBounds;
    }

    protected void rebuildTransformationMatrix() {
        Matrix4d transformationMatrix = new Matrix4d().identity();

        double rootXTranslation = (getOriginX() - getPivotX()) * getScaleX();
        double rootYTranslation = (getOriginY() - getPivotY()) * getScaleY();
        double rootZTranslation = (getOriginZ() - getPivotZ()) * getScaleZ();

        transformationMatrix.translate(originX, originY, originZ);

        
    }

    protected void updateOBBData() {
        
    }

    protected void markDirty() {
        this.needsUpdate = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ModelCoordinateData other)) return false;

        return Double.compare(other.minX, minX) == 0
                && Double.compare(other.minY, minY) == 0
                && Double.compare(other.minZ, minZ) == 0
                && Double.compare(other.maxX, maxX) == 0
                && Double.compare(other.maxY, maxY) == 0
                && Double.compare(other.maxZ, maxZ) == 0
                && Double.compare(other.originX, originX) == 0
                && Double.compare(other.originY, originY) == 0
                && Double.compare(other.originZ, originZ) == 0
                && Double.compare(other.pivotX, pivotX) == 0
                && Double.compare(other.pivotY, pivotY) == 0
                && Double.compare(other.pivotZ, pivotZ) == 0
                && Double.compare(other.rotationX, rotationX) == 0
                && Double.compare(other.rotationY, rotationY) == 0
                && Double.compare(other.rotationZ, rotationZ) == 0
                && Double.compare(other.scaleX, scaleX) == 0
                && Double.compare(other.scaleY, scaleY) == 0
                && Double.compare(other.scaleZ, scaleZ) == 0;
    }
}
