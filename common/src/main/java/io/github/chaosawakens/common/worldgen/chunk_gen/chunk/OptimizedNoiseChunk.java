package io.github.chaosawakens.common.worldgen.chunk_gen.chunk;

import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;

public class OptimizedNoiseChunk extends NoiseChunk {

    public OptimizedNoiseChunk(int horizontalCellCount, RandomState rand, int firstXNoise, int firstZNoise, NoiseSettings settings, DensityFunctions.BeardifierOrMarker terrainBeardifier, NoiseGeneratorSettings noiseGenSettings, Aquifer.FluidPicker fluidPicker, Blender chunkBlender) {
        super(horizontalCellCount, rand, firstXNoise, firstZNoise, settings, terrainBeardifier, noiseGenSettings, fluidPicker, chunkBlender);
    }
}
