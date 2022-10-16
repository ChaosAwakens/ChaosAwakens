package io.github.chaosawakens.common.worldgen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

/**
 * @author invalid2
 */
public class StalagmiteFeature extends Feature<StalagmiteFeatureConfig> {

	public StalagmiteFeature(Codec<StalagmiteFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator gen, Random rand, BlockPos pos, StalagmiteFeatureConfig cfg) {
		if (1.0f/cfg.chance < rand.nextFloat() || reader.isWaterAt(pos.below())) return false;
		
		float steepness = cfg.baseSteepness + ((rand.nextFloat()-0.5f)*cfg.variation)*cfg.baseSteepness;
		int variationFLoor = (int) Math.floor(steepness);
		int radius = cfg.baseRadius + (rand.nextInt(variationFLoor*2 + 1) - variationFLoor);
		if(radius == 0)radius++;
		
		pos = pos.below( pos.getY() - getLowestCorner(gen, pos, radius) + 1);
		for(int j = 0; j < radius*radius*steepness; j++) {
			for(int i = -radius; i <= radius; i++) {
				for(int k = -radius; k <= radius; k++) {
					float pillarCeiling = (-i*i*steepness -k*k*steepness + radius*radius*steepness);
					int smallerThanZeroFlag = i < 0 || k < 0 ? -1 : 0; 
					if(pillarCeiling >= -1*smallerThanZeroFlag && pillarCeiling >= j)
						reader.setBlock(pos.offset(i, j, k), cfg.block, 2);
				}
			}
		}
		return true;
	}
	
	int getLowestCorner(ChunkGenerator gen, BlockPos pos, int radius) {
		int pXpY = gen.getBaseHeight(pos.getX()+radius, pos.getZ()+radius, Heightmap.Type.WORLD_SURFACE);
		int mXpY = gen.getBaseHeight(pos.getX()-radius, pos.getZ()+radius, Heightmap.Type.WORLD_SURFACE);
		int pXmY = gen.getBaseHeight(pos.getX()+radius, pos.getZ()-radius, Heightmap.Type.WORLD_SURFACE);
		int mXmY = gen.getBaseHeight(pos.getX()-radius, pos.getZ()-radius, Heightmap.Type.WORLD_SURFACE);
		return Math.min( Math.min( Math.min(pXpY, mXpY), pXmY), mXmY);
	}
}
