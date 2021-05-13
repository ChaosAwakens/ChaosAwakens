package io.github.chaosawakens.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class BuddingBlock extends Block {
	
	
	public BuddingBlock(Properties builder) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState());
	}
	
	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}
	
	//TODO Maximum number off clusters generated ever
	/*@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}*/
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		ChaosAwakens.LOGGER.debug("Tick!");
		
		//See if we should grow
		if(random.nextInt(1) != 0)return;
		
		//Check which spots are available/valid
		List<BlockStatePos> valids = this.checkValidPositions(state, worldIn, pos);
		
		//if there are none available, return
		if(valids.size() == 0)return;
		
		//Loop 1 to 3 times between them and pick random ones
		int numLoops = random.nextInt(3)+1;
		numLoops = numLoops > valids.size() ? valids.size() : numLoops;
		
		for(int i = 0; i < numLoops; i++) {
			BlockStatePos stateAndPos = valids.get(numLoops); 
			worldIn.setBlockState(stateAndPos.getPos(), stateAndPos.getState());
		}
		
		//Update the maximum created property
		//TODO
	}
	
	private List<BlockStatePos> checkValidPositions(BlockState state, ServerWorld worldIn, BlockPos pos) {
		List<BlockStatePos> valid = new ArrayList<>();
		BlockState budState = ModBlocks.PINK_TOURMALINE_CLUSTER.get().getDefaultState();
		for(Direction direction : Direction.values()) {
			BlockPos budTargetPos = pos;
			budTargetPos = budTargetPos.add(direction.getDirectionVec());
			ChaosAwakens.LOGGER.debug(worldIn.getBlockState(budTargetPos).isSolidSide(worldIn, budTargetPos, direction));
			
			if(!worldIn.getBlockState(budTargetPos).isSolidSide(worldIn, budTargetPos, direction))
				valid.add(
						new BlockStatePos(budTargetPos, budState.with(BlockStateProperties.FACING, direction)));
		}
		
		return valid;
	}
	
	class BlockStatePos {
		private BlockPos pos;
		private BlockState state;
		
		public BlockStatePos(BlockPos pos, BlockState state) {
			this.pos = pos;
			this.state = state;
		}

		public BlockPos getPos() { return pos; }

		public BlockState getState() { return state; }
	}
}