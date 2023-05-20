package io.github.chaosawakens.common.blocks.trees;

import java.util.Random;
import java.util.function.Supplier;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class CAMultiBigTree extends BigTree {
	private ObjectArrayList<Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>>> bigTrees;
	
	@SafeVarargs
	public CAMultiBigTree(Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>>... bigTree) {
		this.bigTrees = new ObjectArrayList<Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>>>(bigTree);
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
