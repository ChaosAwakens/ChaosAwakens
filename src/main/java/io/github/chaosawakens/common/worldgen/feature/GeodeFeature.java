package io.github.chaosawakens.common.worldgen.feature;

import com.mojang.serialization.Codec;
import io.github.chaosawakens.common.blocks.CrystalClusterBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
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
		int yPos = rand.nextInt(config.upperBound - config.lowerBound) + config.lowerBound;

		if (rand.nextInt(config.frequency) < rand.nextInt(4)) return true;

		int numClusters = rand.nextInt(3) + rand.nextInt(3);

		BlockPos targetBudPos = pos.offset(rand.nextInt(8), yPos, rand.nextInt(8));
		reader.setBlock(targetBudPos, config.budState.setValue(BlockStateProperties.AGE_25, rand.nextInt(8) + 17), 2);

		for (int i = 0; i < numClusters; i++) {
			BlockPos targetClusterPos = targetBudPos;
			Direction direction = Direction.values()[rand.nextInt(Direction.values().length - 1)];
			targetClusterPos = targetClusterPos.offset(direction.getNormal());

			reader.setBlock(targetClusterPos, config.clusterState.setValue(CrystalClusterBlock.FACING, direction).setValue(CrystalClusterBlock.AGE, rand.nextInt(2)), 2);
		}
		return true;
	}
}
