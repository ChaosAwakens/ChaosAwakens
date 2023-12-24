package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class GeodeFeature extends Feature<GeodeFeatureConfig> {

	public GeodeFeature(Codec<GeodeFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, GeodeFeatureConfig config) {
		int chance = config.buddingChance.sample(rand);
		float radius = config.diameter.sample(rand) / 2, radiusSqrd = radius * radius, borderSqrd = (radius + 1) * (radius + 1);
		BlockPos.Mutable targetMutable = new BlockPos.Mutable(pos.getX(), pos.getY(), pos.getZ());
		
		for (float xRad = -radius; xRad <= radius; xRad++) {
			for (float yRad = -radius; yRad <= radius; yRad++) {
				for (float zRad = -radius; zRad <= radius; zRad++) {
					float distance = Math.abs(xRad * xRad) + Math.abs(yRad * yRad) + Math.abs(zRad * zRad);
					
					if (distance < radiusSqrd) reader.setBlock(targetMutable.offset(xRad, yRad, zRad), Blocks.CAVE_AIR.defaultBlockState(), 3);
					else if (distance <= borderSqrd) {
						if (rand.nextInt(chance) == 0) reader.setBlock(targetMutable.offset(xRad, yRad, zRad), config.budding, 3);
					} else if (!reader.getBlockState(targetMutable.offset(xRad, yRad, zRad)).is(config.base.getBlock())) reader.setBlock(targetMutable.offset(xRad, yRad, zRad), config.base, 3);
				}
			}
		}
		return true;
	}
}
