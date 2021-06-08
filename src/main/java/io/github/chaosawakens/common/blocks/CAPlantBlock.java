package io.github.chaosawakens.common.blocks;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CAPlantBlock extends Block implements net.minecraftforge.common.IPlantable, IGrowable {
	   public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
	   protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

	   public CAPlantBlock(AbstractBlock.Properties properties) {
	      super(properties);
	      this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }

	   public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
	      if (!state.isValidPosition(worldIn, pos)) {
	         worldIn.destroyBlock(pos, true);
	      }

	   }

	   public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
	      if (worldIn.isAirBlock(pos.up())) {
	         int i;
	         for(i = 1; worldIn.getBlockState(pos.down(i)).matchesBlock(this); ++i) {
	         }

	         if (i < 3) {
	            int j = state.get(AGE);
	            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
	            if (j == 15) {
	               worldIn.setBlockState(pos.up(), this.getDefaultState());
	               worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(0)), 4);
	            } else {
	               worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(j + 1)), 4);
	            }
	            }
	         }
	      }

	   }

	   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
	      if (!stateIn.isValidPosition(worldIn, currentPos)) {
	         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
	      }

	      return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	   }

	   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
	      BlockState soil = worldIn.getBlockState(pos.down());
	      if (soil.canSustainPlant(worldIn, pos.down(), Direction.UP, this)) return true;
	      BlockState blockstate = worldIn.getBlockState(pos.down());
	      if (blockstate.getBlock() == this) {
	         return true;
	      } else {
	         if (blockstate.matchesBlock(Blocks.FARMLAND) || blockstate.matchesBlock(Blocks.GRASS)) {
	            BlockPos blockpos = pos.down();

	            for(Direction direction : Direction.Plane.HORIZONTAL) {
	               BlockState blockstate1 = worldIn.getBlockState(blockpos.offset(direction));
	               FluidState fluidstate = worldIn.getFluidState(blockpos.offset(direction));
                      return true;
	               }
	            }
	         }
	         return false;
	      }
	   

	   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	      builder.add(AGE);
	   }

	   @Override
	   public net.minecraftforge.common.PlantType getPlantType(IBlockReader world, BlockPos pos) {
	       return net.minecraftforge.common.PlantType.PLAINS;
	   }

	   @Override
	   public BlockState getPlant(IBlockReader world, BlockPos pos) {
	      return getDefaultState();
	   }

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        return;
	}
	
	@Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}
	   
}