package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CornTopBlock extends CropTopPlantBlock {
	public CornTopBlock(Properties properties, Direction direction, VoxelShape shape, double growPerTickProbability) {
		super(properties, direction, shape, growPerTickProbability);
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(CAItems.CORN_SEEDS.get());
	}

	@Override
	protected Block getBodyBlock() {
		return CABlocks.CORN_BODY_BLOCK.get();
	}

	@Override
	protected int getMaxHeight() {
		return 18;
	}
}
