package io.github.chaosawakens.common.blocks.dense;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DenseFlowerBlock extends FlowerBlock {
	
	public DenseFlowerBlock(Effect effect, int effectDuration, Properties properties) {
		super(effect, effectDuration, properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
		return state.is(CABlocks.DENSE_GRASS_BLOCK.get()) || state.is(CABlocks.DENSE_DIRT.get());
	}
}
