package io.github.chaosawakens.common.worldgen.chunk_gen;

import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class OptimizedRandomState extends RandomState {

    public OptimizedRandomState(NoiseGeneratorSettings noiseGeneratorSettings, HolderGetter<NormalNoise.NoiseParameters> noiseParameterRegAccess, long randSrcSeed) {
        super(noiseGeneratorSettings, noiseParameterRegAccess, randSrcSeed);
    }


}
