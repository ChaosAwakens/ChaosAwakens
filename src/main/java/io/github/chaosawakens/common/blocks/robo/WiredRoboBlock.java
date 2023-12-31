package io.github.chaosawakens.common.blocks.robo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;

public class WiredRoboBlock extends RotatedPillarBlock {
    public static final BooleanProperty FORCED_DEFAULT = BooleanProperty.create("forced_default");
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty ABOVE = BlockStateProperties.UP;
    public static final BooleanProperty BELOW = BlockStateProperties.DOWN;
    public static final BooleanProperty POSITIVE_AXIS = BooleanProperty.create("positive_axis");
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = Util.make(() -> {
        ImmutableMap.Builder<Direction, BooleanProperty> builder = ImmutableMap.builder();

        builder.put(Direction.NORTH, NORTH);
        builder.put(Direction.SOUTH, SOUTH);
        builder.put(Direction.EAST, EAST);
        builder.put(Direction.WEST, WEST);
        builder.put(Direction.UP, ABOVE);
        builder.put(Direction.DOWN, BELOW);

        return Maps.newEnumMap(builder.build());
    });

    public WiredRoboBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FORCED_DEFAULT, false).setValue(NORTH, false).setValue(SOUTH, false).setValue(EAST, false).setValue(WEST, false).setValue(ABOVE, false).setValue(BELOW, false).setValue(POSITIVE_AXIS, true));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FORCED_DEFAULT, NORTH, SOUTH, EAST, WEST, ABOVE, BELOW);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        return getConnectionPlacementState(pContext.getLevel(), defaultBlockState().setValue(AXIS, pContext.getClickedFace().getAxis()).setValue(POSITIVE_AXIS, pContext.getClickedFace().getAxisDirection() == Direction.AxisDirection.POSITIVE), pContext.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pState.setValue(PROPERTY_BY_DIRECTION.get(pFacing), isValidConnection(pLevel, pCurrentPos, pFacing));
    }

    @Override
    public void onPlace(BlockState pState, World pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if ((!pLevel.isClientSide() || pIsMoving) && !pOldState.is(pState.getBlock())) {
            for (Direction curDir : Direction.values()) {
                BooleanProperty curDirProp = PROPERTY_BY_DIRECTION.get(curDir);

                if (pState.getValue(curDirProp)) pLevel.updateNeighborsAt(pPos.relative(curDir), this);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState pState, World pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pIsMoving) {
            super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, false);

            pLevel.updateNeighborsAt(pFromPos, this);

            for (Direction curDir : Direction.values()) {
                BooleanProperty curDirProp = PROPERTY_BY_DIRECTION.get(curDir);

                if (isValidConnection(pLevel, pPos, curDir)) pState = pState.setValue(curDirProp, true).setValue(POSITIVE_AXIS, curDir.getAxisDirection() == Direction.AxisDirection.POSITIVE);
                else pState = pState.setValue(curDirProp, false).setValue(POSITIVE_AXIS, curDir.getAxisDirection() == Direction.AxisDirection.POSITIVE);

                if (pState.getValue(curDirProp)) pLevel.updateNeighborsAt(pPos.relative(curDir), this);
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving) {
            super.onRemove(pState, pLevel, pPos, pNewState, false);

            for (Direction curDir : Direction.values()) {
                BooleanProperty curDirProp = PROPERTY_BY_DIRECTION.get(curDir);

                if (pState.getValue(curDirProp)) pLevel.updateNeighborsAt(pPos.relative(curDir), this);
            }
        }
    }

    protected BlockState getConnectionPlacementState(IBlockReader level, BlockState targetState, BlockPos targetPos) {
        boolean isDefault = isDefault(targetState) || targetState.getValue(FORCED_DEFAULT);

        if (isDefault) return targetState;
        else {
            for (Direction curDir : Direction.values()) {
                BooleanProperty curDirProp = PROPERTY_BY_DIRECTION.get(curDir);

                if (isValidConnection(level, targetPos, curDir)) targetState = targetState.setValue(curDirProp, true).setValue(POSITIVE_AXIS, curDir.getAxisDirection() == Direction.AxisDirection.POSITIVE);
            }
        }

        return targetState;
    }

    protected boolean isValidConnection(IBlockReader level, BlockPos originPos, Direction relativeDir) {
        return level.getBlockState(originPos.relative(relativeDir)).getBlock() == this;
    }

    protected static boolean isDefault(BlockState targetState) {
        return !targetState.getValue(NORTH) && !targetState.getValue(SOUTH) && !targetState.getValue(EAST) && !targetState.getValue(WEST) && !targetState.getValue(ABOVE) && !targetState.getValue(BELOW);
    }
}
