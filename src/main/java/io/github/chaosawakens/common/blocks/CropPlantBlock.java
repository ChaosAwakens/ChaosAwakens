package io.github.chaosawakens.common.blocks;

import net.minecraft.block.*;
import net.minecraft.item.Item;
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

import java.util.Random;
import java.util.function.Supplier;

public class CropPlantBlock extends BushBlock implements IGrowable {
	private static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	private final Supplier<? extends Item> seedItem;
	private boolean isAboveAir = true;
	private int maxAge = 3;

	public CropPlantBlock(Supplier<? extends Item> seedItem, Properties properties) {
		super(properties);
		this.seedItem = seedItem;
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) < this.getMaxAge() || this.isAboveAir;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (!worldIn.isAreaLoaded(pos, 1)) return;
		if (worldIn.getRawBrightness(pos, 0) >= 9) {
			int i = state.getValue(AGE);
			if (i < this.getMaxAge()) {
				if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(4) == 0)) {
					worldIn.setBlock(pos, state.setValue(AGE, i + 1), 2);
					ForgeHooks.onCropsGrowPost(worldIn, pos, state);
				}
			} else if (worldIn.getBlockState(pos.above()).isAir(worldIn, pos.above())) worldIn.setBlockAndUpdate(pos.above(), state.setValue(AGE, 0));
		}
		this.isAboveAir = worldIn.getBlockState(pos.above()).isAir(worldIn, pos.above());
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.FARMLAND) || (state.is(this) && state.getValue(AGE) == 3);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return (worldIn.getRawBrightness(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && super.canSurvive(state, worldIn, pos);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return new ItemStack(seedItem.get());
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		if (state.getValue(AGE) < this.getMaxAge()) worldIn.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), 2);
	}

	public CropPlantBlock withMaxAge(int maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	@Override
	public PlantType getPlantType(IBlockReader world, BlockPos pos) {
		return PlantType.PLAINS;
	}

	@Override
	public BlockState getPlant(IBlockReader world, BlockPos pos) {
		return defaultBlockState();
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	public int getMaxAge() {
		return this.maxAge;
	}
}
