package io.github.chaosawakens.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModBlocks;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class BuddingBlock extends Block {
	
	private static final IntegerProperty MAX_GENERATABLE = BlockStateProperties.AGE_0_25;
	private CrystalClusterBlock budBlock;
	
	public BuddingBlock(Properties builder, CrystalClusterBlock budBlock) {
		super(builder);
		this.setDefaultState(this.stateContainer.getBaseState().with(MAX_GENERATABLE, new Random().nextInt(8)+17));
		this.budBlock = budBlock;
	}
	
	//TODO Maximum number off clusters generated ever
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(MAX_GENERATABLE);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		ChaosAwakens.LOGGER.debug(state);
		//See if we should grow
		if(random.nextInt(1) != 0)return;
		if(state.get(MAX_GENERATABLE) == 0)return;
		
		//Check which spots are available/valid
		List<BlockStatePos> valids = this.checkValidPositions(state, worldIn, pos);
		
		//if there are none available, return
		if(valids.size() == 0)return;
		
		//Loop 1 to 3 times between them and pick random ones
		int numLoops = random.nextInt(3)+1;
		numLoops = numLoops > valids.size() ? valids.size() : numLoops;
		
		for(int i = 0; i < numLoops; i++) {
			BlockStatePos stateAndPos = valids.get(numLoops-1); 
			worldIn.setBlockState(stateAndPos.getPos(), stateAndPos.getState(), 2);
		}
		
		//Update the maximum creatable property
		if(random.nextInt(2) == 0) {
			worldIn.setBlockState(pos, state.with(MAX_GENERATABLE, state.get(MAX_GENERATABLE)-1));
		}
		
	}
	
	private List<BlockStatePos> checkValidPositions(BlockState state, ServerWorld worldIn, BlockPos pos) {
		List<BlockStatePos> validStatePos = new ArrayList<>();
		
		//Loop through all possible directions
		for(Direction direction : Direction.values()) {
			BlockPos budTargetPos = pos;
			budTargetPos = budTargetPos.add(direction.getDirectionVec());
			
			BlockState budState =  budBlock.getDefaultState();
			boolean newBudBlock = true;
			if(worldIn.getBlockState(budTargetPos).getBlock() instanceof CrystalClusterBlock) {
				budState =  worldIn.getBlockState(budTargetPos);
				newBudBlock = false;
			}
			
			//ChaosAwakens.LOGGER.debug(direction+": "+worldIn.getBlockState(budTargetPos)+" "+worldIn.getBlockState(budTargetPos).getBlock().equals(budBlock));
			
			if((worldIn.getBlockState(budTargetPos).getBlock().equals(budBlock) && worldIn.getBlockState(budTargetPos).get(BlockStateProperties.AGE_0_3) < 3) || !worldIn.getBlockState(budTargetPos).isSolidSide(worldIn, budTargetPos, direction))
				 validStatePos.add( new BlockStatePos(budTargetPos, budState.with(BlockStateProperties.FACING, direction)
								.with(BlockStateProperties.AGE_0_3, newBudBlock ? 0 : budState.get(BlockStateProperties.AGE_0_3)+1 )));
		}
		
		return validStatePos;
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