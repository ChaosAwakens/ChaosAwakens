package io.github.chaosawakens.common.blocks.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;
import java.util.function.Supplier;

public class FancyableTree extends Tree {
	private final Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> tree;
	private final Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> treeBee;
	private final Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> fancyTree;
	private final Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> fancyTreeBee;

	public FancyableTree(Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> tree, Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> treeBee, Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> fancyTree, Supplier<ConfiguredFeature<BaseTreeFeatureConfig, ?>> fancyTreeBee) {
		this.tree = tree;
		this.treeBee = treeBee;
		this.fancyTree = fancyTree;
		this.fancyTreeBee = fancyTreeBee;
	}

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean grow) {
		if (rand.nextInt(10) == 0) return grow ? fancyTree.get() : fancyTreeBee.get();
		else return grow ? tree.get() : treeBee.get();
	}
}
