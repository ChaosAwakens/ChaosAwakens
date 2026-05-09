package io.github.chaosawakens.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

public final class MathUtil {
    public static final double PENNER_CONSTANT = 1.70158D;

    private MathUtil() {
        throw new UnsupportedOperationException("Attempted to construct instance of utility class! (MathUtil)");
    }

    public static double sineEasing(double timeDelta) {
        return 1 - Math.cos(timeDelta * Math.PI / 2.0D);
    }

    public static double quadraticEasing(double timeDelta) {
        return timeDelta * timeDelta;
    }

    public static double cubicEasing(double timeDelta) {
        return timeDelta * timeDelta * timeDelta;
    }

    public static double quarticEasing(double timeDelta) {
        return timeDelta * timeDelta * timeDelta * timeDelta;
    }

    public static double quinticEasing(double timeDelta) {
        return timeDelta * timeDelta * timeDelta * timeDelta * timeDelta;
    }
    public static double exponentialEasing(double timeDelta) {
        return Math.pow(2, 10 * (timeDelta - 1));
    }

    public static double circularEasing(double timeDelta) {
        return 1 - Math.sqrt(1 - timeDelta * timeDelta);
    }

    public static double backEasing(double timeDelta, Double overshootFactor) {
        double chosenOvershootFactor = overshootFactor == null ? PENNER_CONSTANT : overshootFactor * PENNER_CONSTANT;

        return timeDelta * timeDelta * ((chosenOvershootFactor + 1) * timeDelta - chosenOvershootFactor);
    }

    public static double elasticEasing(double timeDelta, Double freqAmp) {
        double chosenFrequencyFactor = freqAmp == null ? 1.0D : freqAmp;

        return 1 - Math.pow(Math.cos(timeDelta * (Math.PI / 2.0F)), 3) * Math.cos(timeDelta * chosenFrequencyFactor * Math.PI); // Have it behave deterministically as in Geckolib (different function of t by default, oscillations die out as t -> 1)
    }

    public static double normalize(double value, double min, double max, double targetMin, double targetMax) {
        if (min == max) throw new IllegalArgumentException("Input range min cannot equal max");
        if (targetMin == targetMax) throw new IllegalArgumentException("Target range min cannot equal max");

        double normalizedValue = (value - min) / (max - min); // Normalize between 0 - 1 first

        return normalizedValue * (targetMax - targetMin) + targetMin;
    }

    public static double normalize(double value, double targetMin, double targetMax) {
        return normalize(value, Double.MIN_VALUE, Double.MAX_VALUE, targetMin, targetMax);
    }

    public static double getRelativeAngleBetween(double x1, double z1, double x2, double z2) {
        return (Math.atan2(z2 - z1, x2 - x1) * (180 / Math.PI) - 90) % 360;
    }

    public static double getRelativeAngleBetweenBlockPositions(BlockPos posA, BlockPos posB) {
        return getRelativeAngleBetween(posA.getX(), posA.getZ(), posB.getX(), posB.getZ());
    }

    public static double getRelativeAngleBetweenEntities(Entity first, Entity second) {
        return getRelativeAngleBetween(first.getX(), first.getZ(), second.getX(), second.getZ());
    }

    public static boolean isWithinAngleRestriction(double relAngle, double angleRestriction) {
        return (relAngle <= angleRestriction / 2 && relAngle >= -angleRestriction / 2) || (relAngle >= 360 - angleRestriction / 2 || relAngle <= -360 + angleRestriction / 2);
    }

    public static boolean isBetween(int num, int min, int max) {
        return num >= min && num <= max;
    }

    public static boolean isBetween(double num, double min, double max) {
        return num >= min && num <= max;
    }

    public static boolean isBetween(float num, float min, float max) {
        return num >= min && num <= max;
    }

    public static boolean isBetween(long num, long min, long max) {
        return num >= min && num <= max;
    }

    public static ObjectArrayList<BlockPos> getDirectLineBetween(int startX, int startY, int startZ, int endX, int endY, int endZ) {
        ObjectArrayList<BlockPos> bresenhamLine = new ObjectArrayList<>();
        int dx = endX - startX;
        int dy = endY - startY;
        int dz = endZ - startZ;
        int steps = Math.max(Math.max(Math.abs(dx), Math.abs(dy)), Math.abs(dz));

        double xIncrement = (double) dx / steps;
        double yIncrement = (double) dy / steps;
        double zIncrement = (double) dz / steps;

        for (int i = 0; i <= steps; i++) {
            int x = startX + (int) Math.round(xIncrement * i);
            int y = startY + (int) Math.round(yIncrement * i);
            int z = startZ + (int) Math.round(zIncrement * i);

            bresenhamLine.add(BlockPos.containing(x, y, z));
        }

        return bresenhamLine;
    }

    public static ObjectArrayList<BlockPos> getDirectLineBetween(Vec3 start, Vec3 end) {
        return getDirectLineBetween((int) start.x, (int) start.y, (int) start.z, (int) end.x, (int) end.y, (int) end.z);
    }

    public static ObjectArrayList<BlockPos> getDirectLineBetween(BlockPos start, BlockPos end) {
        return getDirectLineBetween(start.getX(), start.getY(), start.getZ(), end.getX(), end.getY(), end.getZ());
    }

    public static Vec3 ground(Vec3 basePos, Level curLevel, boolean fullColumnCheck) {
        if (!curLevel.getBlockState(BlockPos.containing(basePos)).getCollisionShape(curLevel, BlockPos.containing(basePos)).isEmpty() || !curLevel.getBlockState(BlockPos.containing(basePos).below()).getCollisionShape(curLevel, BlockPos.containing(basePos)).isEmpty()) return basePos; // Avoid unnecessary computation

        ClipContext colliderCtx = fullColumnCheck
                ? new ClipContext(Vec3.atCenterOf(BlockPos.containing(basePos.x(), curLevel.getMinBuildHeight(), basePos.z())), Vec3.atCenterOf(BlockPos.containing(basePos.x(), curLevel.getMaxBuildHeight(), basePos.z())), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, null)
                : new ClipContext(Vec3.atCenterOf(BlockPos.containing(basePos)), Vec3.atCenterOf(BlockPos.containing(basePos.x(), curLevel.getMinBuildHeight(), basePos.z())), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, null);
        BlockHitResult potentialHitResult = curLevel.clip(colliderCtx);

        return BlockHitResult.Type.MISS.equals(potentialHitResult.getType()) ? basePos : potentialHitResult.getLocation();
    }

    public static Vector3d closestPointOnTriangle(Vector3d p, Vector3d a, Vector3d b, Vector3d c) { // Shoutout https://math.stackexchange.com/questions/1092912/find-closest-point-in-triangle-given-barycentric-coordinates-outside
        Vector3d ab = new Vector3d(b).sub(a);
        Vector3d ac = new Vector3d(c).sub(a);
        Vector3d ap = new Vector3d(p).sub(a);

        double d1 = ab.dot(ap);
        double d2 = ac.dot(ap);

        if (d1 <= 0 && d2 <= 0) return new Vector3d(a);

        Vector3d bp = new Vector3d(p).sub(b);
        double d3 = ab.dot(bp);
        double d4 = ac.dot(bp);

        if (d3 >= 0 && d4 <= d3) return new Vector3d(b);

        double vc = d1 * d4 - d3 * d2;

        if (vc <= 0 && d1 >= 0 && d3 <= 0) {
            double v = d1 / (d1 - d3);

            return new Vector3d(a).add(new Vector3d(ab).mul(v));
        }

        Vector3d cp = new Vector3d(p).sub(c);
        double d5 = ab.dot(cp);
        double d6 = ac.dot(cp);

        if (d6 >= 0 && d5 <= d6) return new Vector3d(c);

        double vb = d5 * d2 - d1 * d6;

        if (vb <= 0 && d2 >= 0 && d6 <= 0) {
            double w = d2 / (d2 - d6);

            return new Vector3d(a).add(new Vector3d(ac).mul(w));
        }

        double va = d3 * d6 - d5 * d4;

        if (va <= 0 && (d4 - d3) >= 0 && (d5 - d6) >= 0) {
            double w = (d4 - d3) / ((d4 - d3) + (d5 - d6));

            return new Vector3d(b).add(new Vector3d(c).sub(b).mul(w));
        }

        double denom = 1.0D / (va + vb + vc);
        double sv = vb * denom;
        double sw = vc * denom;

        return new Vector3d(a).add(new Vector3d(ab).mul(sv)).add(new Vector3d(ac).mul(sw));
    }
    
    public static OscillationResult oscillate(OscillationResult result, float startValue, float endValue, float delta) {
        if (result.increasing) {
            result.value += delta;
            if (result.value >= endValue) {
                result.value = endValue;
                result.increasing = false;
            }
        } else {
            result.value -= delta;
            if (result.value <= startValue) {
                result.value = startValue;
                result.increasing = true;
            }
        }

        return result;
    }

    public static class OscillationResult {
        private float value;
        private boolean increasing;

        public OscillationResult(float value, boolean increasing) {
            this.value = value;
            this.increasing = increasing;
        }

        public float getValue() {
            return value;
        }

        public boolean isIncreasing() {
            return increasing;
        }

        public boolean isDecreasing() {
            return !increasing;
        }
    }
}