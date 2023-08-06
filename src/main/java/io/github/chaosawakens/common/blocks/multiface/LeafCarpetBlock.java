package io.github.chaosawakens.common.blocks.multiface;

import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

@SuppressWarnings("all")
public class LeafCarpetBlock extends MultifaceBlock implements IWaterLoggable {
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public LeafCarpetBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facingDir, BlockState facingState, IWorld world, BlockPos curPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) world.getLiquidTicks().scheduleTick(curPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));

		return super.updateShape(state, facingDir, facingState, world, curPos, facingPos);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> container) {
		super.createBlockStateDefinition(container);
		container.add(WATERLOGGED);
	}

	@Override
	public boolean canBeReplaced(BlockState blockState, BlockItemUseContext blockItemUseContext) {
		return !blockItemUseContext.getItemInHand().getItem().is(CATags.Items.LEAF_CARPETS) || super.canBeReplaced(blockState, blockItemUseContext);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}
}
