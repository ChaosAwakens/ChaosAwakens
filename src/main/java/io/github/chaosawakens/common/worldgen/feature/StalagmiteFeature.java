package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class StalagmiteFeature extends Feature<StalagmiteFeatureConfig> {

	public StalagmiteFeature(Codec<StalagmiteFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator gen, Random rand, BlockPos pos, StalagmiteFeatureConfig cfg) {
		//Micro-optimization : Instead of checking the Optional every loop, check it once and cache the result
		boolean oreFlag = cfg.oresTag.isPresent();
		float steepness = cfg.baseSteepness + ((rand.nextFloat() - 0.5F) * cfg.variation) * cfg.baseSteepness;
		int variationFloor = (int) Math.floor(steepness);
		int radius = cfg.baseRadius + (rand.nextInt(variationFloor * 2 + 1) - variationFloor);
		
		if( radius == 0) radius++;
		// Don't generate ores when ore generation is deactivated
		if(!CAConfigManager.MAIN_COMMON.enableStalagmiteOreGen.get())oreFlag = false;

		Mutable targetMutable = new Mutable(pos.getX(), getLowestCorner(gen, pos, radius), pos.getZ());

		//Micro-optimization: Cache this math operation since the result is constant
		float rrs = radius * radius * steepness;

		//Break from both for-s when Y bigger than height limit
		worldHeightLoop:
			for (int j = 0; j < rrs; j++) {
				for (int i = -radius; i <= radius; i++) {
					for (int k = -radius; k <= radius; k++) {
						float pillarCeiling = (-i * i * steepness -k * k * steepness + rrs);
						int smallerThanZeroFlag = i < 0 || k < 0 ? -1 : 0;

						if (pillarCeiling >= -1 * smallerThanZeroFlag && pillarCeiling >= j) {
							if (targetMutable.getY() + j > 255) break worldHeightLoop;
							if (oreFlag) {
								if (cfg.oreChance >= rand.nextFloat()) reader.setBlock(targetMutable.offset(i, j, k), cfg.oresTag.get().getRandomElement(rand).defaultBlockState(), 2);
								else reader.setBlock(targetMutable.offset(i, j, k), cfg.state, 2);
							} else reader.setBlock(targetMutable.offset(i, j, k), cfg.state, 2);
						}
					}
				}
			}
		return true;
	}

	private int getLowestCorner(ChunkGenerator gen, BlockPos pos, int radius) {
		int pXpY = gen.getBaseHeight(pos.getX() + radius, pos.getZ() + radius, Heightmap.Type.OCEAN_FLOOR);
		int mXpY = gen.getBaseHeight(pos.getX() - radius, pos.getZ() + radius, Heightmap.Type.OCEAN_FLOOR);
		int pXmY = gen.getBaseHeight(pos.getX() + radius, pos.getZ() - radius, Heightmap.Type.OCEAN_FLOOR);
		int mXmY = gen.getBaseHeight(pos.getX() - radius, pos.getZ() - radius, Heightmap.Type.OCEAN_FLOOR);
		return Math.min(Math.min(Math.min(pXpY, mXpY), pXmY), mXmY);
	}
}