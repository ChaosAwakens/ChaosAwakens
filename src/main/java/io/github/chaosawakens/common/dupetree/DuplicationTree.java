package io.github.chaosawakens.common.dupetree;

import java.util.Random;

import io.github.chaosawakens.common.registry.CAConfiguredFeatures;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.server.ServerWorld;

public class DuplicationTree extends Tree{
	public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
	public static final BooleanProperty DUPLICATED = BooleanProperty.create("duplicated");

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean grow) {
		if(rand.nextInt(10) == 0) {
			return grow ? CAConfiguredFeatures.FANCY_DUPLICATION_TREE : CAConfiguredFeatures.FANCY_DEAD_DUPLICATION_TREE;
		}
		else {
			return grow ? CAConfiguredFeatures.DUPLICATION_TREE : CAConfiguredFeatures.DEAD_DUPLICATION_TREE;
		}
	}
	
	public void duplicationFunctions(BlockState state, Block blockToDupe, CATags tags, BlockTags vanillaTags) {
		//Working on the code here, so rest easy since you don't have to anything lol. Would be appreciated if you could but try focusing on other stuff as I focus on this. Thanks <3
	}
	
}
