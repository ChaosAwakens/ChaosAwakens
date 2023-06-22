package io.github.chaosawakens.common.blocks.crystal;

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

public class CrystalBushBlock extends Block implements IPlantable {
	
	public CrystalBushBlock(Properties properties) {
		super(properties);
	}

	protected boolean mayPlaceOn(BlockState state, IBlockReader world, BlockPos pos) {
		return state.is(CABlocks.CRYSTAL_GRASS_BLOCK.get());
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
		return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state1, world, pos, pos1);
	}

	@Override
	public boolean canSurvive(BlockState targetState, IWorldReader worldReader, BlockPos targetPos) {
		BlockPos belowPos = targetPos.below();
		if (targetState.getBlock() == this) return worldReader.getBlockState(belowPos).canSustainPlant(worldReader, belowPos, Direction.UP, this);
		return mayPlaceOn(worldReader.getBlockState(belowPos), worldReader, belowPos);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos targetPos) {
		return state.getFluidState().isEmpty();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType pathType) {
		return pathType == PathType.AIR && !this.hasCollision || super.isPathfindable(state, world, pos, pathType);
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos targetPos) {
		BlockState targetState = world.getBlockState(targetPos);
		if (targetState.getBlock() != this) return defaultBlockState();
		return targetState;
	}
}
