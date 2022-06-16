package io.github.chaosawakens.common.blocks;

import net.minecraft.block.*;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class LeafCarpetBlock extends HorizontalFaceBlock {
	protected static final VoxelShape CEILING_AABB = Block.box(0.0D, 16.0D, 0.0D, 16.0D, 15.0D, 16.0D);
	protected static final VoxelShape FLOOR_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
	protected static final VoxelShape WEST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);

	public LeafCarpetBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
	}

	public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos, ISelectionContext selectionContext) {
		Direction direction = blockState.getValue(FACING);
		switch (blockState.getValue(FACE)) {
		case FLOOR:
			return FLOOR_AABB;
		case WALL:
			switch (direction) {
			case EAST:
				return EAST_AABB;
			case WEST:
				return WEST_AABB;
			case SOUTH:
				return SOUTH_AABB;
			case NORTH:
			default:
				return NORTH_AABB;
			}
		case CEILING:
		default:
			return CEILING_AABB;
		}
	}

	public BlockState updateShape(BlockState blockState, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
		return !blockState.canSurvive(p_196271_4_, p_196271_5_) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
		p_206840_1_.add(FACING, FACE);
	}
}
