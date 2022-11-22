package io.github.chaosawakens.common.blocks;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class DoubleDensePlantBlock extends DenseBushBlock {
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	public DoubleDensePlantBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
	}

	public BlockState updateShape(BlockState state, Direction direction, BlockState state1, IWorld world, BlockPos pos, BlockPos pos1) {
		DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
		if (direction.getAxis() != Direction.Axis.Y
				|| doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP)
				|| state1.is(this)
				&& state1.getValue(HALF) != doubleblockhalf) {
			return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(world, pos)
					? Blocks.AIR.defaultBlockState()
					: super.updateShape(state, direction, state1, world, pos, pos1);
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos blockpos = context.getClickedPos();
		return blockpos.getY() < 255 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
	}

	public void setPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		world.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 3);
	}

	public boolean canSurvive(BlockState state, IWorldReader worldReader, BlockPos pos) {
		if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
			return super.canSurvive(state, worldReader, pos);
		} else {
			BlockState blockstate = worldReader.getBlockState(pos.below());
			if (state.getBlock() != this) return super.canSurvive(state, worldReader, pos);
			return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
		}
	}

	public void placeAt(IWorld world, BlockPos pos, int p_196390_3_) {
		world.setBlock(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), p_196390_3_);
		world.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), p_196390_3_);
	}

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

	public void playerDestroy(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tileEntity, ItemStack stack) {
		super.playerDestroy(world, player, pos, Blocks.AIR.defaultBlockState(), tileEntity, stack);
	}

	protected static void preventCreativeDropFromBottomPart(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
		if (doubleblockhalf == DoubleBlockHalf.UPPER) {
			BlockPos blockpos = pos.below();
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
				world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
				world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
			}
		}
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
		state.add(HALF);
	}

	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.XZ;
	}

	@OnlyIn(Dist.CLIENT)
	public long getSeed(BlockState state, BlockPos pos) {
		return MathHelper.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
	}
}
