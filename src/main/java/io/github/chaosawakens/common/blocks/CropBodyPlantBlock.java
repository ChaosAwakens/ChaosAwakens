package io.github.chaosawakens.common.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public abstract class CropBodyPlantBlock extends AbstractBodyPlantBlock {
	public CropBodyPlantBlock(Properties properties, Direction direction, VoxelShape shape, boolean p_i241179_4_) {
		super(properties, direction, shape, p_i241179_4_);
	}

	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, IWorld worldIn, BlockPos pos, BlockPos pos2) {
		if (direction == this.growthDirection.getOpposite() && !state.canSurvive(worldIn, pos)) {
			worldIn.getBlockTicks().scheduleTick(pos, this, 1);
		}

		CropTopPlantBlock topBlock = (CropTopPlantBlock) this.getHeadBlock();
		if (direction == this.growthDirection) {
			Block block = state2.getBlock();
			if (block != this && block != topBlock) return topBlock.getUpdateShapeState(worldIn);
		}

		if (this.scheduleFluidTicks) worldIn.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));

		return super.updateShape(state, direction, state2, worldIn, pos, pos2);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) {
		BlockPos downPos = pos.relative(this.growthDirection.getOpposite());
		BlockState downState = reader.getBlockState(downPos);
		Block block = downState.getBlock();
		return block == this.getHeadBlock() || block == this.getBodyBlock()
				|| downState.is(Blocks.GRASS_BLOCK) || downState.is(Blocks.DIRT)
				|| downState.is(Blocks.COARSE_DIRT) || downState.is(Blocks.PODZOL)
				|| downState.is(Blocks.FARMLAND);
	}

	@Override
	protected abstract AbstractTopPlantBlock getHeadBlock();
}
