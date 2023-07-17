package io.github.chaosawakens.common.blocks;

import io.github.chaosawakens.common.blocks.crystal.CrystalBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Rotation;

public class RotatedPillarCrystalBlock extends CrystalBlock {
	public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;

	public RotatedPillarCrystalBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(AXIS, Axis.Y));
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
		return adjacentState.getBlock() instanceof RotatedPillarCrystalBlock || super.skipRendering(state, adjacentState, direction);
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		switch (rotation) {
		default: return state;
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:
			switch (state.getValue(AXIS)) {
			default: return state;
			case X: return state.setValue(AXIS, Direction.Axis.Z);
			case Z: return state.setValue(AXIS, Direction.Axis.X);
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(AXIS);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		return defaultBlockState().setValue(AXIS, ctx.getClickedFace().getAxis());
	}
}
