package io.github.chaosawakens.mixins.common.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import org.spongepowered.asm.mixin.*;

@Mixin(BiomeManager.class)
public abstract class BiomeManagerMixin { //TODO TEMPORARY - To be factored into our own chunk gen and world gen in general instead of this breaking ahh mixin
    @Unique
    private static final double FIDDLE_MULTIPLIER = 0.9 / 1024.0; // 0.00087890625
    @Unique
    private static final double FIDDLE_OFFSET = -0.45; // -0.5 * 0.9
    @Unique
    private static final long LCG_MULTIPLIER = 6364136223846793005L;
    @Unique
    private static final long LCG_INCREMENT = 1442695040888963407L;
    @Shadow
    @Final
    private BiomeManager.NoiseBiomeSource noiseBiomeSource;
    @Shadow
    @Final
    private long biomeZoomSeed;

    private BiomeManagerMixin() {
        throw new IllegalAccessError("Attempted to construct standalone Mixin Class!");
    }

    /**
     *
     * @author
     *
     * @reason
     */
    @Overwrite
    public Holder<Biome> getBiome(BlockPos targetPos) {
        // Unpack coordinates and precompute chunk-aligned positions
        final int x = targetPos.getX() - 2;
        final int y = targetPos.getY() - 2;
        final int z = targetPos.getZ() - 2;

        // Calculate chunk coordinates using bitwise shift (faster than division)
        final int chunkX = x >> 2;
        final int chunkY = y >> 2;
        final int chunkZ = z >> 2;

        // Calculate local positions within chunk (faster than modulo)
        final double localX = (x & 3) * 0.25;  // 1.0/4.0 = 0.25
        final double localY = (y & 3) * 0.25;
        final double localZ = (z & 3) * 0.25;

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
        final int finalX = chunkX + ((bestIndex & 4) >>> 2);  // (bestIndex >> 2) & 1
        final int finalY = chunkY + ((bestIndex & 2) >>> 1);  // (bestIndex >> 1) & 1
        final int finalZ = chunkZ + (bestIndex & 1);

        return this.noiseBiomeSource.getNoiseBiome(finalX, finalY, finalZ);
    }

    /**
     *
     * @author
     *
     * @reason
     */
    @Overwrite
    private static double getFiddledDistance(long seed, int x, int y, int z, double dx, double dy, double dz) {
        long state = seed; // Manually unrolled LCG sequence

        // First 6 steps (original sequence)
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + x;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + y;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + z;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + x;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + y;
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + z;

        double fiddle1 = chaosawakens$getFiddle(state); // Get first fiddle value

        // Next state and fiddle
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + seed;
        double fiddle2 = chaosawakens$getFiddle(state);

        // Final state and fiddle
        state = (state * LCG_MULTIPLIER + LCG_INCREMENT) * state + seed;
        double fiddle3 = chaosawakens$getFiddle(state);

        // Optimized distance calculation using fused multiply-add
        double xComp = dx + fiddle1;
        double yComp = dy + fiddle2;
        double zComp = dz + fiddle3;

        return xComp * xComp + yComp * yComp + zComp * zComp;
    }

    @Unique
    private static double chaosawakens$getFiddle(long value) { // Extract bits 24-33 (10 bits) and multiply by precomputed constant
        return Math.fma((value >> 24) & 0x3FF, FIDDLE_MULTIPLIER, FIDDLE_OFFSET);
    }

    @Unique
    private static long next(long state, long right) {
        // Horner's method my goat
        // 6364136223846793005 = 0x5851F42D4C957F2D
        // 1442695040888963407 = 0x14057B7EF767814F
        long newState = state * LCG_MULTIPLIER + LCG_INCREMENT; // Calculate new state using distributive property to reduce operations

        return newState + right; // Return the sum with right (JVM should optimize this to a single instruction)
    }
}
