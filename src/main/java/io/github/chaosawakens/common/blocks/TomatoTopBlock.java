package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

/**
 * @author invalid2
 */
public class TomatoTopBlock extends CropTopPlantBlock {

	/**
	 * @param properties
	 * @param direction
	 * @param shape
	 * @param growPerTickProbability
	 */
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
