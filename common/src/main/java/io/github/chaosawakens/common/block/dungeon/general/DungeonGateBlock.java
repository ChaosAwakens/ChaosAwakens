package io.github.chaosawakens.common.block.dungeon.general;

import io.github.chaosawakens.common.registry.CABlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DungeonGateBlock extends Block {
    protected static final BooleanProperty ACTIVE = CABlockStateProperties.ACTIVE;
    protected static final BooleanProperty VANISHED = CABlockStateProperties.VANISHED;
    protected static final VoxelShape VANISHED_SHAPE = box(6, 6, 6, 10, 10, 10);
    protected final boolean isConstantlyUpdated;
    protected final int baseUpdateTickTime;

    public DungeonGateBlock(boolean isConstantlyUpdated, int baseUpdateTickTime, Properties properties) {
        super(properties);
        this.isConstantlyUpdated = isConstantlyUpdated;
        this.baseUpdateTickTime = baseUpdateTickTime;

        registerDefaultState(getStateDefinition().any().setValue(ACTIVE, false).setValue(VANISHED, false));
    }

    public DungeonGateBlock(boolean isConstantlyUpdated, Properties properties) {
        this(isConstantlyUpdated, 2, properties);
    }

    public DungeonGateBlock(int baseUpdateTickTime, Properties properties) {
        this(false, baseUpdateTickTime, properties);
    }

    public DungeonGateBlock(Properties properties) {
        this(false, properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ACTIVE).add(VANISHED);
    }

    @Override
    public InteractionResult use(BlockState targetState, Level curLevel, BlockPos targetPos, Player interactingPlayer, InteractionHand usedHand, BlockHitResult result) {
        return !curLevel.isClientSide ? checkAndActivate(targetState, targetPos, curLevel) : super.use(targetState, curLevel, targetPos, interactingPlayer, usedHand, result);
    }

    @Override
    public void tick(BlockState targetState, ServerLevel curServerLevel, BlockPos targetPos, RandomSource rand) {
        if (isActive(targetState)) {
            if (!hasVanished(targetState)) {
                curServerLevel.setBlockAndUpdate(targetPos, targetState.setValue(VANISHED, true));

                for (Direction curDir : Direction.values()) checkAndActivate(curServerLevel.getBlockState(targetPos.relative(curDir)), targetPos.relative(curDir), curServerLevel);

                curServerLevel.scheduleTick(targetPos, targetState.getBlock(), 1);
            } else {
                curServerLevel.setBlockAndUpdate(targetPos, targetState.setValue(ACTIVE, false).setValue(VANISHED, false));
                curServerLevel.removeBlock(targetPos, false);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState targetState, Level curLevel, BlockPos targetPos, Block targetBlock, BlockPos originPos, boolean isMoving) {
        if (!curLevel.isClientSide && curLevel.hasNeighborSignal(targetPos) && !isActive(targetState) && !hasVanished(targetState)) checkAndActivate(targetState, targetPos, curLevel);
    }

    @Override
    public float getDestroyProgress(BlockState targetState, Player miningPlayer, BlockGetter curLevel, BlockPos targetPos) {
        return isActive(targetState) ? 0.0F : super.getDestroyProgress(targetState, miningPlayer, curLevel, targetPos);
    }

    protected static InteractionResult checkAndActivate(BlockState targetState, BlockPos targetPos, Level curLevel) {
        if (!isActive(targetState) && !hasVanished(targetState) && targetState.getBlock() instanceof DungeonGateBlock targetDungeonGateBlock) {
            curLevel.setBlockAndUpdate(targetPos, targetState.setValue(ACTIVE, true));
            curLevel.scheduleTick(targetPos, targetDungeonGateBlock, targetDungeonGateBlock.isConstantlyUpdated() ? targetDungeonGateBlock.getBaseUpdateTickTime() : targetDungeonGateBlock.getBaseUpdateTickTime() + RandomSource.create().nextInt(5));

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public boolean isConstantlyUpdated() {
        return isConstantlyUpdated;
    }

    public int getBaseUpdateTickTime() {
        return baseUpdateTickTime;
    }

    public static boolean isActive(BlockState targetState) {
        return targetState.hasProperty(ACTIVE) && targetState.getValue(ACTIVE);
    }

    public static void setActive(BlockState targetState, boolean active) {
        if (targetState.hasProperty(ACTIVE)) targetState.setValue(ACTIVE, active);
    }

    public static boolean hasVanished(BlockState targetState) {
        return targetState.hasProperty(VANISHED) && targetState.getValue(VANISHED);
    }

    public static void setVanished(BlockState targetState, boolean vanished) {
        if (targetState.hasProperty(VANISHED)) targetState.setValue(VANISHED, vanished);
    }
}
