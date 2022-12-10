package io.github.chaosawakens.common.blocks.trees;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CATree extends Tree {

	private Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> tree;

	public CATree(Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> tree) {
		this.tree = tree;
	}

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean grow) {
		return tree.get();
	}
}
