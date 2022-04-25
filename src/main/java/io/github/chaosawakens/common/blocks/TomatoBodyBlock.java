package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

/**
 * @author invalid2
 */
public class TomatoBodyBlock extends CropBodyPlantBlock {

	/**
	 * @param properties
	 * @param direction
	 * @param shape
	 */
	public TomatoBodyBlock(Properties properties, Direction direction, VoxelShape shape) {
		super(properties, direction, shape, false);
	}

	@Override
	protected AbstractTopPlantBlock getHeadBlock() {
		return CABlocks.TOMATO_TOP_BLOCK.get();
	}

}
