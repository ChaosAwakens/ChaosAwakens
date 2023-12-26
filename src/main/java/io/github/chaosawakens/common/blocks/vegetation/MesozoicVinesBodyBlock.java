package io.github.chaosawakens.common.blocks.vegetation;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.AbstractBodyPlantBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;

public class MesozoicVinesBodyBlock extends AbstractBodyPlantBlock {
	public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	
	public MesozoicVinesBodyBlock(Properties prop) {
		super(prop, Direction.DOWN, SHAPE, false);
	}

	@Override
	protected AbstractTopPlantBlock getHeadBlock() {
		return CABlocks.MESOZOIC_VINES.get();
	}
	
	@Override
	public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
		BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
		BlockState blockstate = pLevel.getBlockState(blockpos);
		Block block = blockstate.getBlock();
		if (!this.canAttachToBlock(block)) {
			return false;
		} else {
			return block == this.getHeadBlock() || block == this.getBodyBlock() || block.is(CABlocks.MESOZOIC_LEAVES.get()) || blockstate.isFaceSturdy(pLevel, blockpos, this.growthDirection);
		}
	}
}