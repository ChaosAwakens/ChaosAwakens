package io.github.chaosawakens.common.blocks.trees;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CAMultiBigTree extends BigTree {
	
	List<Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>>> bigTrees;
	
	@SafeVarargs
	public CAMultiBigTree(Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>>... bigTree) {
		this.bigTrees = Lists.newArrayList(bigTree);
	}

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredMegaFeature(Random pRand) {
		return this.bigTrees.get(pRand.nextInt(this.bigTrees.size())).get();
	}

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
		return null;
	}

}
