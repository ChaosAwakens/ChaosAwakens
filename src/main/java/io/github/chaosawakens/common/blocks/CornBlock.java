package io.github.chaosawakens.common.blocks;

import java.util.Random;

import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

public class CornBlock extends BushBlock implements IGrowable {
	private static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
	private static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	private boolean isAboveAir = true;
	private int maxAge = 3;
	
	public CornBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
		return state.get(AGE) < this.getMaxAge() || this.isAboveAir;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (!worldIn.isAreaLoaded(pos, 1))return;
		if (worldIn.getLightSubtracted(pos, 0) >= 9) {
			int i = state.get(AGE);
			if (i < this.getMaxAge()) {
				if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(4) == 0)) {
					worldIn.setBlockState(pos, state.with(AGE, i + 1), 2);
					ForgeHooks.onCropsGrowPost(worldIn, pos, state);
				}
			} else if(worldIn.getBlockState(pos.up()).isAir(worldIn, pos.up())) {
				worldIn.setBlockState(pos.up(), state.with(AGE, 0));
			}	
		}
		
		this.isAboveAir = worldIn.getBlockState(pos.up()).isAir(worldIn, pos.up());
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn;
	}
	
	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return state.matchesBlock(Blocks.GRASS_BLOCK) || state.matchesBlock(Blocks.FARMLAND) || (state.matchesBlock(this) && state.get(AGE) == 3);
	}
	
	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return (worldIn.getLightSubtracted(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && super.isValidPosition(state, worldIn, pos);
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
	
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(CAItems.CORN_SEEDS.get());
	}
	
	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		if(state.get(AGE) < this.getMaxAge())
			worldIn.setBlockState(pos, state.with(AGE, state.get(AGE)+1), 2);
		return;
	}
	
	public CornBlock withMaxAge(int maxAge) {
		this.maxAge = maxAge;
		return this;
	}
	
	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) { return PlantType.PLAINS; }
	
	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) { return getDefaultState(); }
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) { return true; }
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) { return true; }
	
	public int getMaxAge() { return this.maxAge; }
}
