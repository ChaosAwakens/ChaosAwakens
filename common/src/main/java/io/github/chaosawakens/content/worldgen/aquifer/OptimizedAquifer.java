package io.github.chaosawakens.content.worldgen.aquifer;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.*;
import org.jetbrains.annotations.Nullable;

/**
 * An optimized version of the Aquifer interface with improved performance characteristics.
 * This implementation focuses on reducing object allocations and improving cache efficiency.
 */
public interface OptimizedAquifer extends Aquifer {

    /**
     * Creates a new instance of OptimizedAquifer.
     *
     * @param chunk                   The noise chunk being generated
     * @param chunkPos                The position of the chunk being generated
     * @param noiseRouter             The noise router providing various noise values
     * @param positionalRandomFactory Factory for creating position-dependent random number generators
     * @param minY                    Minimum Y level for aquifer generation
     * @param height                  Height range for aquifer generation
     * @param globalFluidPicker       Determines fluid types at given positions
     * @return A new OptimizedAquifer instance
     */
    static OptimizedAquifer create(NoiseChunk chunk, ChunkPos chunkPos, NoiseRouter noiseRouter,
                                   PositionalRandomFactory positionalRandomFactory, int minY, int height,
                                   OptimizedAquifer.FluidPicker globalFluidPicker) {
        return new OptimizedNoiseBasedAquifer(chunk, chunkPos, noiseRouter, positionalRandomFactory,
                minY, height, globalFluidPicker);
    }

    /**
     * Creates a disabled, or no-op aquifer. This will fill any open areas below sea level with the default fluid.
     *
     * @param defaultFluid The fluid picker that determines what fluid to use below sea level
     * @return An aquifer that only handles basic fluid placement
     */
    static OptimizedAquifer createDisabled(final OptimizedAquifer.FluidPicker defaultFluid) {
        return new OptimizedAquifer() {
            // Cache the fluid state to avoid repeated lookups
            private BlockState cachedFluidState;
            private int lastY = Integer.MIN_VALUE;

            @Nullable
            public BlockState computeSubstance(DensityFunction.FunctionContext context, double density) {
                if (density > 0.0) return null;

                int y = context.blockY();
                // Only compute fluid state if Y changes
                if (y != lastY) {
                    cachedFluidState = defaultFluid.computeFluid(
                            context.blockX(),
                            y,
                            context.blockZ()
                    ).at(y);
                    lastY = y;
                }
                return cachedFluidState;
            }


            @Override
            public @Nullable BlockState computeSubstance(DensityFunction.SinglePointContext context, double density) {
                return null;
            }

            public boolean shouldScheduleFluidUpdate() {
                return false;
            }
        };
    }

    /**
     * Computes the block state for a given position and density value.
     * This is the main method called during world generation.
     *
     * @param context The density function context
     * @param density The density value at the current position
     * @return The block state to place, or null for no change
     */
    @Nullable
    BlockState computeSubstance(DensityFunction.SinglePointContext context, double density);

    /**
     * Determines if a fluid update should be scheduled for this aquifer.
     * This is typically used for dynamic fluid behavior.
     *
     * @return true if a fluid update should be scheduled
     */
    boolean shouldScheduleFluidUpdate();

    /**
     * Gets the fluid type at the specified position.
     * This is used to determine what fluid should be placed in open areas.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return The fluid state at the given position
     */
    default BlockState getFluidType(int x, int y, int z) {
        return null;
    }

    /**
     * Updates the aquifer state for a chunk after generation.
     * This can be used for post-processing or cleanup.
     *
     * @param chunk    The chunk being generated
     * @param chunkPos The position of the chunk
     */
    default void updateChunk(ChunkAccess chunk, ChunkPos chunkPos) {
        // Default implementation does nothing
    }

    /**
     * Optimized version of the noise-based aquifer implementation.
     */
    class OptimizedNoiseBasedAquifer implements OptimizedAquifer {
        private final DensityFunction barrierNoise;
        private final DensityFunction fluidLevelFloodednessNoise;
        private final DensityFunction fluidLevelSpreadNoise;
        private final double[] noiseData;
        private final int minY;
        private final int height;
        private final Aquifer.FluidPicker globalFluidPicker;
        private final PositionalRandomFactory randomFactory;

        public OptimizedNoiseBasedAquifer(NoiseChunk chunk, ChunkPos chunkPos, NoiseRouter noiseRouter,
                                          PositionalRandomFactory randomFactory, int minY, int height,
                                          Aquifer.FluidPicker globalFluidPicker) {
            this.barrierNoise = noiseRouter.barrierNoise();
            this.fluidLevelFloodednessNoise = noiseRouter.fluidLevelFloodednessNoise();
            this.fluidLevelSpreadNoise = noiseRouter.fluidLevelSpreadNoise();
            this.noiseData = new double[16 * 16 * 16]; // Local cache for noise values
            this.minY = minY;
            this.height = height;
            this.globalFluidPicker = globalFluidPicker;
            this.randomFactory = randomFactory;
        }

        @Override
        @Nullable
        public BlockState computeSubstance(DensityFunction.FunctionContext context, double density) {
            if (context instanceof DensityFunction.SinglePointContext singlePointContext) {
                return computeSubstance(singlePointContext, density);
            }
            // Fallback behavior if the context is not a SinglePointContext
            return null;
        }

        @Override
        @Nullable
        public BlockState computeSubstance(DensityFunction.SinglePointContext context, double density) {
            int x = context.blockX();
            int y = context.blockY();
            int z = context.blockZ();

            // Skip if outside vertical range
            if (y < this.minY || y >= this.minY + this.height) {
                return null;
            }

            // Calculate local coordinates within chunk (0-15)
            int localX = x & 15;
            int localZ = z & 15;
            int index = (y - this.minY) * 256 + localZ * 16 + localX;

            // Use cached noise value if available
            double noiseValue;
            if (index >= 0 && index < this.noiseData.length) {
                noiseValue = this.noiseData[index];
            } else {
                // Fallback to direct noise calculation if cache miss
                noiseValue = this.barrierNoise.compute(new DensityFunction.SinglePointContext(x, y, z));
            }

            // Check if we should place fluid
            if (noiseValue > 0.0D) {
                return null; // Solid block, no fluid
            }

            // Get fluid type from the global fluid picker
            return this.globalFluidPicker.computeFluid(x, y, z).at(y);
        }

        @Override
        public boolean shouldScheduleFluidUpdate() {
            return true; // Default to true for dynamic fluid behavior
        }
    }
}