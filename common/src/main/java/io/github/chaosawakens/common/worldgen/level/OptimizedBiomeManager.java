package io.github.chaosawakens.common.worldgen.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import org.jetbrains.annotations.NotNull;

/**
 * Optimized {@link BiomeManager} implementation that employs basic optimization principles to improve performance,
 * particularly since the methods in {@link BiomeManager} are called upwards of millions of times in a single session,
 * thus making them performance hotspots viable for optimization.
 * <br></br>
 * While Vanilla's {@link BiomeManager} is fairly optimized for the base game, attempting to generate chunks containing
 * more than ~150,000 blocks results in worker threads not being able to compute chunks fast enough for the main thread
 * to actually generate them. This results in over 90% of a tick on the main thread being spent idling when it comes to
 * worldgen.
 * <br></br>
 * This {@code class} functionally produces the same outputs Vanilla would, but with a number of micro-optimizations
 * that make an otherwise notable impact on chunk generation performance. The majority of said impact can be attributed
 * to {@link #next(long, long)} (see {@link LinearCongruentialGenerator#next(long, long)}) and
 * {@link #getFiddledDistance(long, int, int, int, double, double, double)}
 * (see {@link BiomeManager#getFiddledDistance(long, int, int, int, double, double, double)}).
 * <br></br>
 * The main optimization techniques used here include loop-unrolling, inlining, constant propagation, localized evaluation,
 * bitwise operations, and pre-computation. They're all pretty basic, but there honestly isn't a need for anything too
 * crazy since these alone produce a neat 30% - 50% performance increase based on profiled results (Addendum: The total
 * time spent in this method sees these improvements, and the time spent within the method itself varies but is largely
 * negligible).
 * <br></br>
 * <b>NOTE: </b> This is only used for CA's dimensions atm since we want to try and keep things the least obtrusive we
 * can. We could probably add a config option later on that just globalizes this implementation by always returning it
 * in whatever mixins use it, idk.
 */
public class OptimizedBiomeManager extends BiomeManager {
    public static final double FIDDLE_MULTIPLIER = 0.9 / 1024.0; // 0.00087890625
    public static final double FIDDLE_OFFSET = -0.45; // -0.5 * 0.9
    public static final long LCG_MULTIPLIER = 6364136223846793005L;
    public static final long LCG_INCREMENT = 1442695040888963407L;
    public static final int ZOOM_BITS = 2; // Biome grid bits
    public static final int ZOOM_MASK = 0b11; // 3
    public static final int X_BIT_MASK = 0b100;  // 4
    public static final int Y_BIT_MASK = 0b010;  // 2
    public static final int Z_BIT_MASK = 0b001;  // 1
    public static final int X_BIT_SHIFT = 2;
    public static final int Y_BIT_SHIFT = 1;

    public OptimizedBiomeManager(NoiseBiomeSource noiseBiomeSource, long biomeZoomSeed) {
        super(noiseBiomeSource, biomeZoomSeed);
    }

    @Override
    public @NotNull OptimizedBiomeManager withDifferentSource(@NotNull NoiseBiomeSource noiseBiomeSource) {
        return new OptimizedBiomeManager(noiseBiomeSource, biomeZoomSeed);
    }

    @Override
    public @NotNull Holder<Biome> getBiome(BlockPos targetPos) { // ZOOM isn't used since we unrolled the loop and took care of each grid corner ourselves anyway
        // Unpack coordinates and precompute chunk-aligned positions
        final int x = targetPos.getX() - 2;
        final int y = targetPos.getY() - 2;
        final int z = targetPos.getZ() - 2;

        // Calculate chunk coordinates using bitwise shift (faster than division)
        final int chunkX = x >> ZOOM_BITS;
        final int chunkY = y >> ZOOM_BITS;
        final int chunkZ = z >> ZOOM_BITS;

        // Calculate local positions within chunk (faster than modulo)
        final double localX = (x & ZOOM_MASK) * 0.25;  // 1.0/4.0 = 0.25
        final double localY = (y & ZOOM_MASK) * 0.25;
        final double localZ = (z & ZOOM_MASK) * 0.25;

        // Precompute chunk offsets and local position adjustments
        int bestIndex = 0;
        double minDistance = Double.POSITIVE_INFINITY;
        final long seed = this.biomeZoomSeed;

        // Unroll the loop for better performance
        // Each iteration represents one of the 8 corners of the 2x2x2 cube
        // Bit 2: X offset, Bit 1: Y offset, Bit 0: Z offset

        // Corner 0: (0,0,0)
        double d0 = getFiddledDistance(seed, chunkX, chunkY, chunkZ, localX, localY, localZ);

        if (d0 < minDistance) {
            minDistance = d0;
            bestIndex = 0;
        }

        // Corner 1: (0,0,1)
        double d1 = getFiddledDistance(seed, chunkX, chunkY, chunkZ + 1, localX, localY, localZ - 1.0);

        if (d1 < minDistance) {
            minDistance = d1;
            bestIndex = 1;
        }

        // Corner 2: (0,1,0)
        double d2 = getFiddledDistance(seed, chunkX, chunkY + 1, chunkZ, localX, localY - 1.0, localZ);

        if (d2 < minDistance) {
            minDistance = d2;
            bestIndex = 2;
        }

        // Corner 3: (0,1,1)
        double d3 = getFiddledDistance(seed, chunkX, chunkY + 1, chunkZ + 1, localX, localY - 1.0, localZ - 1.0);

        if (d3 < minDistance) {
            minDistance = d3;
            bestIndex = 3;
        }

        // Corner 4: (1,0,0)
        double d4 = getFiddledDistance(seed, chunkX + 1, chunkY, chunkZ, localX - 1.0, localY, localZ);

        if (d4 < minDistance) {
            minDistance = d4;
            bestIndex = 4;
        }

        // Corner 5: (1,0,1)
        double d5 = getFiddledDistance(seed, chunkX + 1, chunkY, chunkZ + 1, localX - 1.0, localY, localZ - 1.0);

        if (d5 < minDistance) {
            minDistance = d5;
            bestIndex = 5;
        }

        // Corner 6: (1,1,0)
        double d6 = getFiddledDistance(seed, chunkX + 1, chunkY + 1, chunkZ, localX - 1.0, localY - 1.0, localZ);

        if (d6 < minDistance) {
            minDistance = d6;
            bestIndex = 6;
        }

        // Corner 7: (1,1,1)
        double d7 = getFiddledDistance(seed, chunkX + 1, chunkY + 1, chunkZ + 1, localX - 1.0, localY - 1.0, localZ - 1.0);

        if (d7 < minDistance) {
            minDistance = d7;
            bestIndex = 7;
        }

        // Calculate final biome coordinates using bitwise operations
        final int finalX = chunkX + ((bestIndex & X_BIT_MASK) >>> X_BIT_SHIFT);  // (bestIndex >> 2) & 1
        final int finalY = chunkY + ((bestIndex & Y_BIT_MASK) >>> Y_BIT_SHIFT);  // (bestIndex >> 1) & 1
        final int finalZ = chunkZ + (bestIndex & Z_BIT_MASK);

        return this.noiseBiomeSource.getNoiseBiome(finalX, finalY, finalZ);
    }

    private static double getFiddledDistance(long seed, int x, int y, int z, double dx, double dy, double dz) {
        long state = seed; // Manually unrolled LCG sequence

        // First 6 steps (original sequence)
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + x;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + y;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + z;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + x;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + y;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + z;

        double fiddle1 = getFiddle(state); // Get first fiddle value

        // Next state and fiddle
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + seed;
        double fiddle2 = getFiddle(state);

        // Final state and fiddle
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + seed;
        double fiddle3 = getFiddle(state);

        // Optimized distance calculation using basic euclidean distance calculation and fma (see #getFiddle(long))
        double xComp = dx + fiddle1;
        double yComp = dy + fiddle2;
        double zComp = dz + fiddle3;

        return xComp * xComp + yComp * yComp + zComp * zComp;
    }

    private static double getFiddle(long value) { // Extract bits 24-33 (10 bits) and multiply by precomputed constant, then apply offset (fma)
        return Math.fma((value >> 24) & 0x3FF, FIDDLE_MULTIPLIER, FIDDLE_OFFSET);
    }

    private static long next(long state, long right) { // We could've gone with a little more extreme and just converted these to ints, but that would sacrifice more precision than the improvement made by such a change would be worth tbh
        // Horner's method my goat
        // 6364136223846793005 = 0x5851F42D4C957F2D
        // 1442695040888963407 = 0x14057B7EF767814F
        long newState = state * LCG_MULTIPLIER + LCG_INCREMENT; // Calculate new state using distributive property to reduce operations

        return newState + right; // Return the sum with right (JVM should optimize this to a single instruction)
    }
}
