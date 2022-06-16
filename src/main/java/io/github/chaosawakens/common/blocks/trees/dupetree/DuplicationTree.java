package io.github.chaosawakens.common.blocks.trees.dupetree;

import io.github.chaosawakens.api.IUtilityHelper;

import io.github.chaosawakens.common.registry.CAConfiguredFeatures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class DuplicationTree extends Tree implements IUtilityHelper {
	public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
	public static final IntegerProperty IS_DEAD = BlockStateProperties.AGE_25;
//	public static final BooleanProperty DUPLICATED = BooleanProperty.create("duplicated");

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean grow) {
		if (rand.nextInt(10) == 0) {
			return grow ? CAConfiguredFeatures.FANCY_DUPLICATION_TREE : CAConfiguredFeatures.FANCY_DEAD_DUPLICATION_TREE;
		} else {
			return grow ? CAConfiguredFeatures.DUPLICATION_TREE : CAConfiguredFeatures.DEAD_DUPLICATION_TREE;
		}
	}

	public DuplicationTree() {

	}

	public void reset() {

	}

	public void Duplicate(World w, BlockState state, BlockPos pos, Block blockToDupe, double x, double y, double z) {
//		List<Block> blockListOfDupes = new ArrayList<>();

//		Queue.add(blockToDupe);

		if (!IUtilityHelper.isDuplicatable(blockToDupe)) return;

		if (w == null) return;

		Block BTD = w.getBlockState(pos).getBlock();
		blockToDupe = BTD;
	}

	public static IntegerProperty getDistance() {
		return DISTANCE;
	}
}
