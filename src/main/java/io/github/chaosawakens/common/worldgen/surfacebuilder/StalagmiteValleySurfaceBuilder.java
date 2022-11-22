package io.github.chaosawakens.common.worldgen.surfacebuilder;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.registry.CASurfaceBuilders;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class StalagmiteValleySurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
	public StalagmiteValleySurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
		super(codec);
	}

	public void apply(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
		if (noise < -3.0D) SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, CASurfaceBuilders.Configs.GRAVEL);
		if (noise > -3.0D && noise < 3.0D) SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, CASurfaceBuilders.Configs.STONE);
		if (noise > 3.0D) SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, CASurfaceBuilders.Configs.ANDESITE);
	}
	
	
}
