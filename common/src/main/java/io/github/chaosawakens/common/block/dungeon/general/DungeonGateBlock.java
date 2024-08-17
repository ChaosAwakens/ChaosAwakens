package io.github.chaosawakens.common.block.dungeon.general;

import io.github.chaosawakens.common.registry.CABlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DungeonGateBlock extends Block {
    protected static final BooleanProperty ACTIVE = CABlockStateProperties.ACTIVE;
    protected static final BooleanProperty VANISHED = CABlockStateProperties.VANISHED;
    protected static final VoxelShape VANISHED_SHAPE = box(6, 6, 6, 10, 10, 10);

    public DungeonGateBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(ACTIVE, false).setValue(VANISHED, false));
    }

    @Override
    public InteractionResult use(BlockState targetState, Level curLevel, BlockPos targetPos, Player interactingPlayer, InteractionHand usedHand, BlockHitResult result) {
        if (!curLevel.isClientSide) {

        }
        return super.use(targetState, curLevel, targetPos, interactingPlayer, usedHand, result);
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
