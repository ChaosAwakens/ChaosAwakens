package io.github.chaosawakens.common.worldgen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class GeodeFeature extends Feature<GeodeFeatureConfig> {

	public GeodeFeature(Codec<GeodeFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, GeodeFeatureConfig config) {
		int radius = config.diameter.sample(rand)/2;
		int radiusSqrd = radius*radius;
		BlockPos.Mutable mutable = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());
		for(int i = -radius; i < radius; i++) {
			for(int j = -radius; j < radius; j++) {
				for(int k = -radius; k < radius; k++) {
					int distance = Math.abs(i*i) + Math.abs(j*j) + Math.abs(k*k);
					if(distance < radiusSqrd)
						reader.setBlock(mutable.offset(i, j, k), Blocks.CAVE_AIR.defaultBlockState(), distance);
					else if(distance <= radiusSqrd + 1)
						if(rand.nextInt(4) == 0)reader.setBlock(mutable.offset(i, j, k), config.state, 3);
				}
			}
		}
		return true;
	}
}
