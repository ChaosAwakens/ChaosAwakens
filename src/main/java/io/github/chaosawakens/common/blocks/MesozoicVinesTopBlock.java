package io.github.chaosawakens.common.blocks;

import java.util.Random;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlockHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;

public class MesozoicVinesTopBlock extends AbstractTopPlantBlock {
	public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	
	public MesozoicVinesTopBlock(Properties pProperties) {
		super(pProperties, Direction.DOWN, SHAPE, false, 0.1);
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(Random pRandom) {
		return PlantBlockHelper.getBlocksToGrowWhenBonemealed(pRandom);
	}

	@Override
	protected boolean canGrowInto(BlockState pState) {
		return PlantBlockHelper.isValidGrowthState(pState);
	}

	@Override
	protected Block getBodyBlock() {
		return CABlocks.MESOZOIC_VINES_PLANT.get();
	}
	
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
		BlockState blockstate = pLevel.getBlockState(blockpos);
		Block block = blockstate.getBlock();
		if (!this.canAttachToBlock(block)) {
			return false;
		} else {
			return block == this.getHeadBlock() || block == this.getBodyBlock()
					|| block.is(CABlocks.MESOZOIC_LEAVES.get())
					|| blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
		}
	}
}
