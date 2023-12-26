package io.github.chaosawakens.common.blocks.robo;


import io.github.chaosawakens.common.util.EnumUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WiredRoboBlock extends Block {
    public static final EnumProperty<EnumUtil.WireDirection> NORTH = EnumProperty.create("north_direction", EnumUtil.WireDirection.class);
    public static final EnumProperty<EnumUtil.WireDirection> SOUTH = EnumProperty.create("south_direction", EnumUtil.WireDirection.class);
    public static final EnumProperty<EnumUtil.WireDirection> EAST = EnumProperty.create("east_direction", EnumUtil.WireDirection.class);
    public static final EnumProperty<EnumUtil.WireDirection> WEST = EnumProperty.create("west_direction", EnumUtil.WireDirection.class);
    public static final EnumProperty<EnumUtil.WireDirection> ABOVE = EnumProperty.create("above_direction", EnumUtil.WireDirection.class);
    public static final EnumProperty<EnumUtil.WireDirection> BELOW = EnumProperty.create("below_direction", EnumUtil.WireDirection.class);

    public WiredRoboBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(NORTH, EnumUtil.WireDirection.DEFAULT).setValue(SOUTH, EnumUtil.WireDirection.DEFAULT).setValue(EAST, EnumUtil.WireDirection.DEFAULT).setValue(WEST, EnumUtil.WireDirection.DEFAULT).setValue(ABOVE, EnumUtil.WireDirection.DEFAULT).setValue(BELOW, EnumUtil.WireDirection.DEFAULT));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        return getConnectionPlacementState(pContext.getLevel(), defaultBlockState(), pContext.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public void onPlace(BlockState pState, World pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, world, pos, neighbor);
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return super.rotate(state, world, pos, direction);
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return super.mirror(pState, pMirror);
    }

    protected BlockState getConnectionPlacementState(IBlockReader level, BlockState targetState, BlockPos targetPos) {
        boolean isDefault = isDefault(targetState);
        boolean isExclusivelyConnectedVertically = isConnectedVertically(targetState, true);
        boolean isExclusivelyConnectedHorizontally = isConnectedHorizontally(targetState, true);

        if (isDefault) return targetState;
        else {
            if (isExclusivelyConnectedVertically) {
                boolean isConnectedAbove = targetState.getValue(ABOVE).hasConnection();
                boolean isConnectedBelow = targetState.getValue(BELOW).hasConnection();

                if (isConnectedAbove) targetState = targetState.setValue(ABOVE, EnumUtil.WireDirection.SIDE_VERTICAL);
                if (isConnectedBelow) targetState = targetState.setValue(BELOW, EnumUtil.WireDirection.SIDE_VERTICAL);
            }

            if (isExclusivelyConnectedHorizontally) {
                boolean isConnectedEast = targetState.getValue(EAST).hasConnection();
                boolean isConnectedWest = targetState.getValue(WEST).hasConnection();
                boolean isConnectedNorth = targetState.getValue(NORTH).hasConnection();
                boolean isConnectedSouth = targetState.getValue(SOUTH).hasConnection();

                if (isConnectedEast) targetState = targetState.setValue(EAST, EnumUtil.WireDirection.SIDE_HORIZONTAL);
                if (isConnectedWest) targetState = targetState.setValue(WEST, EnumUtil.WireDirection.SIDE_HORIZONTAL);
                if (isConnectedNorth) targetState = targetState.setValue(NORTH, EnumUtil.WireDirection.SIDE_HORIZONTAL);
                if (isConnectedSouth) targetState = targetState.setValue(SOUTH, EnumUtil.WireDirection.SIDE_HORIZONTAL);
            }
        }

        return null;
    }

    protected static boolean isDefault(BlockState targetState) {
        return !targetState.getValue(NORTH).hasConnection() && !targetState.getValue(SOUTH).hasConnection() && !targetState.getValue(EAST).hasConnection() && !targetState.getValue(WEST).hasConnection() && !targetState.getValue(ABOVE).hasConnection() && !targetState.getValue(BELOW).hasConnection();
    }

    protected static boolean isConnectedVertically(BlockState targetState, boolean checkExclusively) {
        return (targetState.getValue(ABOVE).hasConnection() || targetState.getValue(BELOW).hasConnection()) && checkExclusively ? !targetState.getValue(EAST).hasConnection() && !targetState.getValue(WEST).hasConnection() && !targetState.getValue(NORTH).hasConnection() && !targetState.getValue(SOUTH).hasConnection() : true;
    }

    protected static boolean isConnectedHorizontally(BlockState targetState, boolean checkExclusively) {
        return (targetState.getValue(EAST).hasConnection() || targetState.getValue(WEST).hasConnection() || targetState.getValue(NORTH).hasConnection() || targetState.getValue(SOUTH).hasConnection()) && checkExclusively ? !targetState.getValue(ABOVE).hasConnection() && !targetState.getValue(BELOW).hasConnection() : true;
    }

    protected static boolean canConnect(IBlockReader level, BlockState targetState, BlockPos targetPos, @Nullable Direction targetDir) {
        return true;
    }
}
