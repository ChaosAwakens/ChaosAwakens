package io.github.chaosawakens.common.worldgen.base;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

/**
 * Remapped version of {@link CaveWorldCarver}, with extra functionality added to it as well.
 * <br> </br>
 * 
 */
public class ExtendedCaveWorldCarver extends CaveWorldCarver {

	public ExtendedCaveWorldCarver(Codec<ProbabilityConfig> probabilityConfig, int height) {
		super(probabilityConfig, height);
	}
	
	@Override
	public boolean carve(IChunk targetChunk, Function<BlockPos, Biome> blockPosToBiomeFunc, Random random, int seed, int chunkX, int chunkZ, int height, int range, BitSet carvedPositions, ProbabilityConfig probabilityConfig) {
		return super.carve(targetChunk, blockPosToBiomeFunc, random, seed, chunkX, chunkZ, height, range, carvedPositions, probabilityConfig);
	}

}
