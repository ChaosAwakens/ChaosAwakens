package io.github.chaosawakens.common.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public abstract class CropTopPlantBlock extends AbstractTopPlantBlock implements IGrowable {
	public static final IntegerProperty GROWTH = IntegerProperty.create("growth", 0, 3);
	private final double growPerTickProbability;

	public CropTopPlantBlock(Properties properties, Direction direction, VoxelShape shape, double growPerTickProbability) {
		super(properties, direction, shape, false, growPerTickProbability);
		this.growPerTickProbability = growPerTickProbability;
		this.registerDefaultState(this.stateDefinition.any().setValue(GROWTH, 0).setValue(AGE, 0));
	}

	@Override
	public BlockState getStateForPlacement(IWorld worldIn) {
		return this.defaultBlockState().setValue(AGE, 0).setValue(GROWTH, 0);
	}

	public BlockState getUpdateShapeState(IWorld worldIn) {
		return this.defaultBlockState().setValue(AGE, 0).setValue(GROWTH, 2);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(GROWTH) < 4;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState state2, IWorld worldIn, BlockPos pos, BlockPos pos2) {
		if (direction == this.growthDirection.getOpposite() && !state.canSurvive(worldIn, pos)) worldIn.getBlockTicks().scheduleTick(pos, this, 1);
		if (direction != this.growthDirection || !state2.is(this) && !state2.is(this.getBodyBlock())) {
			if (this.scheduleFluidTicks) worldIn.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
			return state;
		} else return this.getBodyBlock().defaultBlockState();
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		int age = state.getValue(GROWTH);
		int height = state.getValue(AGE);
		if (height < this.getMaxHeight() && ForgeHooks.onCropsGrowPre(worldIn, pos.relative(this.growthDirection), worldIn.getBlockState(pos.relative(this.growthDirection)), rand.nextDouble() < this.growPerTickProbability)) {
			BlockPos targetPos = pos.relative(this.growthDirection);
			if (age < 3) {
				worldIn.setBlockAndUpdate(pos, state.setValue(GROWTH, age + 1));
				ForgeHooks.onCropsGrowPost(worldIn, pos, worldIn.getBlockState(pos));
			} else {
				if (this.canGrowInto(worldIn.getBlockState(targetPos))) {
					worldIn.setBlockAndUpdate(pos, this.getBodyBlock().defaultBlockState());
					worldIn.setBlockAndUpdate(targetPos, state.setValue(AGE, state.getValue(AGE) + 1).setValue(GROWTH, 0));
					ForgeHooks.onCropsGrowPost(worldIn, targetPos, worldIn.getBlockState(targetPos));
				}
			}
		}
	}

	@Override
	public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
		BlockPos targetPos = pos;
		int j = rand.nextInt(8) + 1;

		worldIn.setBlockAndUpdate(pos, this.getBodyBlock().defaultBlockState());
		targetPos = targetPos.relative(this.growthDirection);
		worldIn.setBlockAndUpdate(targetPos, state.setValue(GROWTH, 0).setValue(AGE, state.getValue(AGE) + 1));
		if (j == this.getMaxHeight()) worldIn.setBlockAndUpdate(targetPos, state.setValue(GROWTH, 0).setValue(AGE, state.getValue(AGE)));
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockPos downPos = pos.relative(this.growthDirection.getOpposite());
		BlockState downState = worldIn.getBlockState(downPos);
		Block block = downState.getBlock();

		return block == this.getHeadBlock() || block == this.getBodyBlock() || downState.is(Blocks.FARMLAND);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> container) {
		container.add(AGE, GROWTH);
	}

	@Override
	public boolean isValidBonemealTarget(IBlockReader reader, BlockPos pos, BlockState state, boolean p_176473_4_) {
		return this.canGrowInto(reader.getBlockState(pos.relative(this.growthDirection)));
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	protected boolean canAttachToBlock(Block block) {
		return false;
	}

	@SuppressWarnings("deprecation")
	protected boolean canGrowInto(BlockState state) {
		return state.isAir();
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(Random p_230332_1_) {
		return 0;
	}

	abstract protected int getMaxHeight();

	@Override
	abstract protected Block getBodyBlock();
}
