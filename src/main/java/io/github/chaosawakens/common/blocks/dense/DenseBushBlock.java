package io.github.chaosawakens.common.blocks.dense;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.IPlantable;

public class DenseBushBlock extends Block implements IPlantable {
	
	public DenseBushBlock(Properties properties) {
		super(properties);
	}

	protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
		return state.is(CABlocks.DENSE_GRASS_BLOCK.get()) || state.is(CABlocks.DENSE_DIRT.get());
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState curState, IWorld world, BlockPos curPos, BlockPos facingPos) {
		return !state.canSurvive(world, curPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, curState, world, curPos, facingPos);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldReader, BlockPos pos) {
		BlockPos belowPos = pos.below();
		if (state.getBlock() == this) return worldReader.getBlockState(belowPos).canSustainPlant(worldReader, belowPos, Direction.UP, this);
		return mayPlaceOn(worldReader.getBlockState(belowPos), worldReader, belowPos);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos targetPos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType pathType) {
		return pathType == PathType.AIR && !this.hasCollision || super.isPathfindable(state, world, pos, pathType);
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		BlockState targetState = world.getBlockState(pos);
		if (targetState.getBlock() != this) return defaultBlockState();
		return targetState;
	}
}
