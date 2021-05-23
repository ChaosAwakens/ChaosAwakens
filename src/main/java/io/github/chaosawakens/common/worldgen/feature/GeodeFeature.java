package io.github.chaosawakens.common.worldgen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import io.github.chaosawakens.common.blocks.CrystalClusterBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class GeodeFeature extends Feature<GeodeFeatureConfig> {
	
	public GeodeFeature(Codec<GeodeFeatureConfig> codec) {
		super(codec);
	}
	
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, GeodeFeatureConfig config) {
		int yPos = rand.nextInt(config.upperBound - config.lowerBound) + config.lowerBound;
		
		// ChaosAwakens.LOGGER.debug(reader.getBlockState(pos.add(0, yPos, 0)));
		if (rand.nextInt(config.frequency) < rand.nextInt(4))
			return true;
		
		int numClusters = rand.nextInt(3) + rand.nextInt(3);
		
		// ChaosAwakens.LOGGER.debug(pos);
		BlockPos targetBudPos = pos.add(rand.nextInt(8), yPos, rand.nextInt(8));
		reader.setBlockState(targetBudPos, config.budState, 2);
		for (int i = 0; i < numClusters; i++) {
			BlockPos targetClusterPos = targetBudPos;
			Direction direction = Direction.values()[rand.nextInt(Direction.values().length - 1)];
			targetClusterPos = targetClusterPos.add(direction.getDirectionVec());
			
			reader.setBlockState(targetClusterPos, config.clusterState.with(CrystalClusterBlock.FACING, direction).with(CrystalClusterBlock.AGE, rand.nextInt(2)), 2);
		}
		
		return true;
	}
	
}
