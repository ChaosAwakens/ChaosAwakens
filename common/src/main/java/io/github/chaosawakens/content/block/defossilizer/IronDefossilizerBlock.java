package io.github.chaosawakens.content.block.defossilizer;

import io.github.chaosawakens.content.block_entity.defossilizer.IronDefossilizerBlockEntity;
import io.github.chaosawakens.content.registry.CABlockEntityTypes;
import io.github.chaosawakens.content.registry.CAStats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class IronDefossilizerBlock extends AbstractDefossilizerBlock {

    public IronDefossilizerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void openContainer(Level curLevel, BlockPos targetPos, Player interactingPlayer) {
        BlockEntity targetBlockEntity = curLevel.getBlockEntity(targetPos);

        if (targetBlockEntity instanceof IronDefossilizerBlockEntity targetIronDefossilizerBlockEntity) {
            interactingPlayer.openMenu(targetIronDefossilizerBlockEntity);
            interactingPlayer.awardStat(CAStats.INTERACT_WITH_IRON_DEFOSSILIZER.get());
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IronDefossilizerBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <BE extends BlockEntity> BlockEntityTicker<BE> getTicker(Level level, BlockState state, BlockEntityType<BE> blockEntityType) {
        return createDefossilizerTicker(level, blockEntityType, CABlockEntityTypes.IRON_DEFOSSILIZER.get());
    }
}
