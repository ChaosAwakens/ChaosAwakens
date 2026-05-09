package io.github.chaosawakens.content.worldgen.chunk_gen.chunk;

import io.github.chaosawakens.content.worldgen.aquifer.OptimizedAquifer;
import net.minecraft.world.level.levelgen.DensityFunction;

public class OptimizedNoiseChunk implements DensityFunction.ContextProvider {
    // Chunk dimensions in blocks
    public static final int WIDTH = 16;
    public static final int HEIGHT = 384;
    public static final int AREA = WIDTH * WIDTH;
    public static final int VOLUME = AREA * HEIGHT;

    private final OptimizedAquifer aquifer;
    private final int chunkX;
    private final int chunkZ;

    /**
     * Creates a new OptimizedNoiseChunk with the specified aquifer and chunk coordinates.
     *
     * @param aquifer The optimized aquifer to use for fluid level calculations
     * @param chunkX  The chunk's X coordinate
     * @param chunkZ  The chunk's Z coordinate
     */
    public OptimizedNoiseChunk(OptimizedAquifer aquifer, int chunkX, int chunkZ) {
        this.aquifer = aquifer;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public DensityFunction.FunctionContext forIndex(int chunkRelativeIndex) {
        // Convert 1D index to 3D coordinates within the chunk
        // The order is XZY (x changes fastest, then z, then y)
        int x = chunkRelativeIndex & 0xF;          // Lower 4 bits for X (0-15)
        int z = (chunkRelativeIndex >> 4) & 0xF;    // Next 4 bits for Z (0-15)
        int y = chunkRelativeIndex >> 8;            // Remaining bits for Y (0-383)

        // Calculate world coordinates by adding chunk offset
        int worldX = (chunkX << 4) + x;
        int worldZ = (chunkZ << 4) + z;

        // Return a new context with just the coordinates
        // Block state sampling needs to be handled separately in the density function
        return new DensityFunction.SinglePointContext(worldX, y, worldZ);
    }

    @Override
    public void fillAllDirectly(double[] densityValues, DensityFunction densityFunction) {
        // Fill the densityValues array by applying the densityFunction to each position
        for (int i = 0; i < densityValues.length; i++) {
            DensityFunction.FunctionContext context = forIndex(i);
            densityValues[i] = densityFunction.compute(context);
        }
    }

    /**
     * Gets the optimized aquifer used by this noise chunk.
     *
     * @return The OptimizedAquifer instance
     */
    public OptimizedAquifer getAquifer() {
        return this.aquifer;
    }
}