package io.github.chaosawakens.content.block.robo;

import io.github.chaosawakens.content.block.block_entity.basic.OrientableContainerBlock;
import io.github.chaosawakens.content.block_entity.robo.RoboCrateBlockEntity;
import io.github.chaosawakens.content.registry.CAStats;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RoboCrateBlock extends OrientableContainerBlock<RoboCrateBlockEntity> {

    public RoboCrateBlock(Properties properties) {
        super(properties, RoboCrateBlockEntity.class);
    }

    @Override
    protected void awardStats(Player interactingPlayer, RoboCrateBlockEntity blockEntity) {
        interactingPlayer.awardStat(CAStats.OPEN_ROBO_CRATE.get());
    }

    @Override
    protected void performRemoveAction(Level curLevel, BlockPos targetPos, BlockEntity targetBlockEntity) {

    }

    @Override
    protected void performTickAction(ServerLevel curServerLevel, BlockPos targetPos, RoboCrateBlockEntity targetBlockEntity) {
        targetBlockEntity.recheckOpen();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RoboCrateBlockEntity(blockPos, blockState);
    }
}
