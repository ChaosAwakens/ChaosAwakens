package io.github.chaosawakens.content.block.block_entity.basic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class OrientableContainerBlock<BE extends BaseContainerBlockEntity & MenuProvider> extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    protected final Class<BE> blockEntityClass;

    protected OrientableContainerBlock(Properties properties, Class<BE> blockEntityClass) {
        super(properties);

        this.blockEntityClass = blockEntityClass;

        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    public @NotNull InteractionResult use(BlockState targetState, Level curLevel, BlockPos targetPos, Player interactingPlayer, InteractionHand hand, BlockHitResult result) {
        if (curLevel.isClientSide) return InteractionResult.SUCCESS;
        else {
            BlockEntity targetBlockEntity = curLevel.getBlockEntity(targetPos);

            if (blockEntityClass.isInstance(targetBlockEntity)) {
                interactingPlayer.openMenu((BE) targetBlockEntity);

                awardStats(interactingPlayer, (BE) targetBlockEntity);
            }

            return InteractionResult.CONSUME;
        }
    }

    protected abstract void awardStats(Player interactingPlayer, BE blockEntity);

    @Override
    public void onRemove(BlockState targetState, Level curLevel, BlockPos targetPos, BlockState newState, boolean isMoving) {
        if (!targetState.is(newState.getBlock())) {
            BlockEntity targetBlockEntity = curLevel.getBlockEntity(targetPos);

            if (targetBlockEntity instanceof Container targetContainer) {
                Containers.dropContents(curLevel, targetPos, targetContainer);
                curLevel.updateNeighbourForOutputSignal(targetPos, this);

                performRemoveAction(curLevel, targetPos, targetBlockEntity);
            }

            super.onRemove(targetState, curLevel, targetPos, newState, isMoving);
        }
    }

    protected abstract void performRemoveAction(Level curLevel, BlockPos targetPos, BlockEntity targetBlockEntity);

    @Override
    public void tick(BlockState targetState, ServerLevel curServerLevel, BlockPos targetPos, RandomSource randSrc) {
        BlockEntity targetBlockEntity = curServerLevel.getBlockEntity(targetPos);

        if (blockEntityClass.isInstance(targetBlockEntity)) {
            performTickAction(curServerLevel, targetPos, blockEntityClass.cast(targetBlockEntity));
        }
    }

    protected abstract void performTickAction(ServerLevel curServerLevel, BlockPos targetPos, BE targetBlockEntity);

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState targetState) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level curLevel, BlockPos targetPos, BlockState targetState, LivingEntity placer, ItemStack heldStack) {
        if (heldStack.hasCustomHoverName()) {
            BlockEntity targetBlockEntity = curLevel.getBlockEntity(targetPos);

            if (blockEntityClass.isInstance(targetBlockEntity)) {
                blockEntityClass.cast(targetBlockEntity).setCustomName(heldStack.getHoverName());
            }
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState targetState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState targetState, Level curLevel, BlockPos targetPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(curLevel.getBlockEntity(targetPos));
    }

    @Override
    public @NotNull BlockState rotate(BlockState targetState, Rotation curRotation) {
        return targetState.setValue(FACING, curRotation.rotate(targetState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState targetState, Mirror mirrorTarget) {
        return targetState.rotate(mirrorTarget.getRotation(targetState.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection().getOpposite());
    }

    public Class<BE> getBlockEntityClass() {
        return blockEntityClass;
    }
}
