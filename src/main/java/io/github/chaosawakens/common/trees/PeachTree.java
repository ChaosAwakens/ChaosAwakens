package io.github.chaosawakens.common.trees;

import java.util.Random;

import io.github.chaosawakens.common.registry.CAConfiguredFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class PeachTree extends Tree{

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean grow) {
		if(rand.nextInt(10) == 0) {
			return grow ? CAConfiguredFeatures.FANCY_PEACH_TREE : CAConfiguredFeatures.FANCY_PEACH_TREE_BEES_005;
		}
		else {
			return grow ? CAConfiguredFeatures.PEACH_TREE : CAConfiguredFeatures.PEACH_TREE_BEES_005;
		}
	}

}
