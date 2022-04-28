package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class TomatoTopBlock extends CropTopPlantBlock {
	public TomatoTopBlock(Properties properties, Direction direction, VoxelShape shape, double growPerTickProbability) {
		super(properties, direction, shape, growPerTickProbability);
	}

	@Override
	protected int getMaxHeight() {
		return 10;
	}

	@Override
	protected Block getBodyBlock() {
		return CABlocks.TOMATO_BODY_BLOCK.get();
	}
}
