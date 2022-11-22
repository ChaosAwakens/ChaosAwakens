package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CrystalClusterBlock extends DirectionalBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	protected static final Map<String, VoxelShape> SHAPES = Util.make(new HashMap<>(), (map) -> {
		map.put("[age=0,facing=up]", Block.box(5.0D, 0.0D, 5.0D, 10.0D, 6.0D, 10.0D));
		map.put("[age=1,facing=up]", Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D));
		map.put("[age=2,facing=up]", Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D));
		map.put("[age=3,facing=up]", Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D));
		map.put("[age=0,facing=down]", Block.box(5.0D, 10.0D, 5.0D, 11.0D, 16.0D, 11.0D));
		map.put("[age=1,facing=down]", Block.box(5.0D, 9.0D, 5.0D, 11.0D, 16.0D, 11.0D));
		map.put("[age=2,facing=down]", Block.box(4.0D, 6.0D, 4.0D, 12.0D, 16.0D, 12.0D));
		map.put("[age=3,facing=down]", Block.box(3.0D, 4.0D, 3.0D, 13.0D, 16.0D, 13.0D));
		map.put("[age=0,facing=north]", Block.box(5.0D, 10.0D, 5.0D, 10.0D, 16.0D, 10.0D));
		map.put("[age=1,facing=north]", Block.box(5.0D, 5.0D, 9.0D, 11.0D, 11.0D, 16.0D));
		map.put("[age=2,facing=north]", Block.box(4.0D, 4.0D, 6.0D, 12.0D, 12.0D, 16.0D));
		map.put("[age=3,facing=north]", Block.box(3.0D, 3.0D, 4.0D, 13.0D, 13.0D, 16.0D));
		map.put("[age=0,facing=south]", Block.box(5.0D, 5.0D, 0.0D, 10.0D, 10.0D, 6.0D));
		map.put("[age=1,facing=south]", Block.box(5.0D, 5.0D, 0.0D, 11.0D, 7.0D, 11.0D));
		map.put("[age=2,facing=south]", Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 10.0D));
		map.put("[age=3,facing=south]", Block.box(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 12.0D));
		map.put("[age=0,facing=east]", Block.box(0.0D, 5.0D, 5.0D, 6.0D, 10.0D, 10.0D));
		map.put("[age=1,facing=east]", Block.box(0.0D, 5.0D, 5.0D, 7.0D, 11.0D, 11.0D));
		map.put("[age=2,facing=east]", Block.box(0.0D, 4.0D, 4.0D, 10.0D, 12.0D, 12.0D));
		map.put("[age=3,facing=east]", Block.box(0.0D, 3.0D, 3.0D, 12.0D, 13.0D, 13.0D));
		map.put("[age=0,facing=west]", Block.box(10.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D));
		map.put("[age=1,facing=west]", Block.box(9.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D));
		map.put("[age=2,facing=west]", Block.box(6.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
		map.put("[age=3,facing=west]", Block.box(4.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D));
	});

	public CrystalClusterBlock(Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(AGE, new Random().nextInt(4)));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape = SHAPES.get(state.toString().substring(state.toString().indexOf("}") + 1));
		return shape != null ? shape : VoxelShapes.block();
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getClickedFace();
		BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().relative(direction.getOpposite()));
		return blockstate.is(this) && blockstate.getValue(FACING) == direction
				? this.defaultBlockState().setValue(FACING, direction.getOpposite()).setValue(AGE, 3)
				: this.defaultBlockState().setValue(FACING, direction).setValue(AGE, 3);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING).add(AGE);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction direction = state.getValue(FACING);
		BlockPos blockpos = pos.relative(direction.getOpposite());
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return blockstate.isFaceSturdy(worldIn, blockpos, direction);
	}
}
