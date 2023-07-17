package io.github.chaosawakens.common.blocks.trees;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CABigTree extends BigTree {
	private Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> tree;
	private Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> bigTree;
	
	public CABigTree(Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> tree, Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> bigTree) {
		this.bigTree = bigTree;
		this.tree = tree;
	}

	@Nullable
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredMegaFeature(Random pRand) {
		return bigTree.get();
	}
	
	@Nullable
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
		return tree.get();
	}
}
