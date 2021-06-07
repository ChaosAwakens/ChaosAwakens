package io.github.chaosawakens.common.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

public class CrystalClusterBlock extends DirectionalBlock {
	protected static final Map<String, VoxelShape> SHAPES = Util.make( new HashMap<>(), (map) -> {
		map.put("[age=0,facing=up]", Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 10.0D, 6.0D, 10.0D));
		map.put("[age=1,facing=up]", Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D));
		map.put("[age=2,facing=up]", Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D));
		map.put("[age=3,facing=up]", Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D));
		map.put("[age=0,facing=down]", Block.makeCuboidShape(5.0D, 10.0D, 5.0D, 11.0D, 16.0D, 11.0D));
		map.put("[age=1,facing=down]", Block.makeCuboidShape(5.0D, 9.0D, 5.0D, 11.0D, 16.0D, 11.0D));
		map.put("[age=2,facing=down]", Block.makeCuboidShape(4.0D, 6.0D, 4.0D, 12.0D, 16.0D, 12.0D));
		map.put("[age=3,facing=down]", Block.makeCuboidShape(3.0D, 4.0D, 3.0D, 13.0D, 16.0D, 13.0D));
		map.put("[age=0,facing=north]", Block.makeCuboidShape(5.0D, 10.0D, 5.0D, 10.0D, 16.0D, 10.0D));
		map.put("[age=1,facing=north]", Block.makeCuboidShape(5.0D, 5.0D, 9.0D, 11.0D, 11.0D, 16.0D));
		map.put("[age=2,facing=north]", Block.makeCuboidShape(4.0D, 4.0D, 6.0D, 12.0D, 12.0D, 16.0D));
		map.put("[age=3,facing=north]", Block.makeCuboidShape(3.0D, 3.0D, 4.0D, 13.0D, 13.0D, 16.0D));
		map.put("[age=0,facing=south]", Block.makeCuboidShape(5.0D, 5.0D, 0.0D, 10.0D, 10.0D, 6.0D));
		map.put("[age=1,facing=south]", Block.makeCuboidShape(5.0D, 5.0D, 0.0D, 11.0D, 7.0D, 11.0D));
		map.put("[age=2,facing=south]", Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 10.0D));
		map.put("[age=3,facing=south]", Block.makeCuboidShape(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 12.0D));
		map.put("[age=0,facing=east]", Block.makeCuboidShape(0.0D, 5.0D, 5.0D, 6.0D, 10.0D, 10.0D));
		map.put("[age=1,facing=east]", Block.makeCuboidShape(0.0D, 5.0D, 5.0D, 7.0D, 11.0D, 11.0D));
		map.put("[age=2,facing=east]", Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 10.0D, 12.0D, 12.0D));
		map.put("[age=3,facing=east]", Block.makeCuboidShape(0.0D, 3.0D, 3.0D, 12.0D, 13.0D, 13.0D));
		map.put("[age=0,facing=west]", Block.makeCuboidShape(10.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D));
		map.put("[age=1,facing=west]", Block.makeCuboidShape(9.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D));
		map.put("[age=2,facing=west]", Block.makeCuboidShape(6.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D));
		map.put("[age=3,facing=west]", Block.makeCuboidShape(4.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D));
	});
	
	public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
	
	public CrystalClusterBlock(Properties builder) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP).with(AGE, new Random().nextInt(4) ));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape = SHAPES.get(state.toString().substring(state.toString().indexOf("}")+1));
		return shape != null ? shape: VoxelShapes.fullCube();
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.with(FACING, mirrorIn.mirror(state.get(FACING)));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getFace();
		BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(direction.getOpposite()));
		return blockstate.matchesBlock(this) && blockstate.get(FACING) == direction ? this.getDefaultState().with(FACING, direction.getOpposite()).with(AGE, 3) : this.getDefaultState().with(FACING, direction).with(AGE, 3);
	}
	
	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}
	
	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING).add(AGE);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction direction = state.get(FACING);
		BlockPos blockpos = pos.offset(direction.getOpposite());
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return blockstate.isSolidSide(worldIn, blockpos, direction);
	}
}