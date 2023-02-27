package io.github.chaosawakens.common.worldgen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import io.github.chaosawakens.ChaosAwakens;
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
		int chance = config.buddingChance.sample(rand);
		float radius = config.diameter.sample(rand)/2,
				radiusSqrd = radius*radius,
				borderSqrd = (radius+1)*(radius+1);
		BlockPos.Mutable mutable = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());
		for(float i = -radius; i <= radius; i++) {
			for(float j = -radius; j <= radius; j++) {
				for(float k = -radius; k <= radius; k++) {
					float distance = Math.abs(i*i) + Math.abs(j*j) + Math.abs(k*k);
					ChaosAwakens.LOGGER.debug(mutable.offset(i, j, k)+" "+distance+" "+(distance < radiusSqrd)+" "+(distance <= radiusSqrd + 2));
					if(distance < radiusSqrd)
						reader.setBlock(mutable.offset(i, j, k), Blocks.CAVE_AIR.defaultBlockState(), 3);
					else if(distance <= borderSqrd)
						if(rand.nextInt(chance) == 0)
							reader.setBlock(mutable.offset(i, j, k), config.budding, 3);
						else
							if(!reader.getBlockState(mutable.offset(i, j, k)).is(config.base.getBlock()))
								reader.setBlock(mutable.offset(i, j, k), config.base, 3);
				}
			}
		}
		return true;
	}
}
