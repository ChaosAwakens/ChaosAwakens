package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.block.*;
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

	public BlockState updateShape(BlockState p_222384_, Direction p_222385_, BlockState p_222386_, IWorld p_222387_, BlockPos p_222388_, BlockPos p_222389_) {
		if (p_222384_.getValue(WATERLOGGED)) {
			p_222387_.getLiquidTicks().scheduleTick(p_222388_, Fluids.WATER, Fluids.WATER.getTickDelay(p_222387_));
		}

		return super.updateShape(p_222384_, p_222385_, p_222386_, p_222387_, p_222388_, p_222389_);
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_222391_) {
		super.createBlockStateDefinition(p_222391_);
		p_222391_.add(WATERLOGGED);
	}

	public boolean canBeReplaced(BlockState blockState, BlockItemUseContext blockItemUseContext) {
		return !blockItemUseContext.getItemInHand().getItem().is(CATags.Items.LEAF_CARPETS) || super.canBeReplaced(blockState, blockItemUseContext);
	}

	public FluidState getFluidState(BlockState p_222394_) {
		return p_222394_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_222394_);
	}

	public PushReaction getPistonPushReaction(BlockState p_222397_) {
		return PushReaction.DESTROY;
	}
}
