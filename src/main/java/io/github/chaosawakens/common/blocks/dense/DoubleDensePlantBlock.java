package io.github.chaosawakens.common.blocks.dense;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class DoubleDensePlantBlock extends DenseBushBlock {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	public DoubleDensePlantBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState targetState, IWorld world, BlockPos curPos, BlockPos facingPos) {
		DoubleBlockHalf blockHalf = state.getValue(HALF);
		
		if (direction.getAxis() != Direction.Axis.Y || blockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || targetState.is(this) && targetState.getValue(HALF) != blockHalf) {
			return blockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(world, curPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, targetState, world, curPos, facingPos);
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getClickedPos();
		return blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		world.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 3);
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldReader, BlockPos pos) {
		if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
			return super.canSurvive(state, worldReader, pos);
		} else {
			BlockState belowState = worldReader.getBlockState(pos.below());
			if (state.getBlock() != this) return super.canSurvive(state, worldReader, pos);
			return belowState.is(this) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER;
		}
	}

	public void placeAt(IWorld world, BlockPos pos, int updateFlag) {
		world.setBlock(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), updateFlag);
		world.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), updateFlag);
	}

	@Override
	public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClientSide) {
			if (player.isCreative()) {
				preventCreativeDropFromBottomPart(world, pos, state, player);
			} else {
				dropResources(state, world, pos, null, player, player.getMainHandItem());
			}
		}
		super.playerWillDestroy(world, pos, state, player);
	}

	@Override
	public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
		super.playerDestroy(world, player, pos, Blocks.AIR.defaultBlockState(), tileEntity, stack);
	}

	protected static void preventCreativeDropFromBottomPart(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		DoubleBlockHalf blockHalf = state.getValue(HALF);
		
		if (blockHalf == DoubleBlockHalf.UPPER) {
			BlockPos belowPos = pos.below();
			BlockState belowState = world.getBlockState(belowPos);
			if (belowState.getBlock() == state.getBlock() && belowState.getValue(HALF) == DoubleBlockHalf.LOWER) {
				world.setBlock(belowPos, Blocks.AIR.defaultBlockState(), 35);
				world.levelEvent(player, 2001, belowPos, Block.getId(belowState));
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
		state.add(HALF);
	}
	
	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XZ;
	}

	@Override
	public long getSeed(BlockState state, BlockPos pos) {
		return MathHelper.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
	}
}
