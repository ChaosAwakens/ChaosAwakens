package io.github.chaosawakens.common.blocks.crystal;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class CrystalFlowerBlock extends FlowerBlock {
	
	public CrystalFlowerBlock(Effect effect, int effectDuration, AbstractBlock.Properties properties) {
		super(effect, effectDuration, properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
		return state.is(CABlocks.CRYSTAL_GRASS_BLOCK.get());
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldReader, BlockPos pos) {
		BlockPos blockpos = pos.below();
		if (state.getBlock() == this) return worldReader.getBlockState(blockpos).canSustainPlant(worldReader, blockpos, Direction.UP, this);
		return this.mayPlaceOn(worldReader.getBlockState(blockpos), worldReader, blockpos);
	}

	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XZ;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean skipRendering(BlockState state, BlockState state1, Direction direction) {
		return state1.getBlock() instanceof CrystalBlock || super.skipRendering(state, state1, direction);
	}

	@Override
	public float getShadeBrightness(BlockState state, IBlockReader reader, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}
}
