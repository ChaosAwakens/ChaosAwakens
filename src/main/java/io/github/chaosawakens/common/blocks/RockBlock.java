package io.github.chaosawakens.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;

//Replace the model, it's broken --Meme Man
public class RockBlock extends HorizontalBlock{
	
	public RockBlock(Properties prop) {
		super(prop);
	}
	
	@SuppressWarnings("unused")
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
		VoxelShape shape_w = VoxelShapes.empty();
		
		VoxelShape shape_s = VoxelShapes.empty();
		
		VoxelShape shape_e = VoxelShapes.empty();
		
		VoxelShape shape_n = VoxelShapes.empty();
		
		switch (state.getValue(FACING)) {
		    default: return shape_w;
		    case NORTH: return shape_w;
		    case SOUTH: return shape_s;
		    case EAST: return shape_e;
		    case WEST: return shape_w;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public boolean canDropFromExplosion(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion) {
		return true;
	}
	
	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, Direction side) {
		return false;
	}

}
