package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class CornBodyBlock extends CropBodyPlantBlock {
	public CornBodyBlock(Properties properties, Direction direction, VoxelShape shape) {
		super(properties, direction, shape, false);
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(CAItems.CORN_SEEDS.get());
	}

	@Override
	protected AbstractTopPlantBlock getHeadBlock() {
		return CABlocks.CORN_TOP_BLOCK.get();
	}
}
