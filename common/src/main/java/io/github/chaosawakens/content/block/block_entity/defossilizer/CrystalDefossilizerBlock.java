package io.github.chaosawakens.content.block.block_entity.defossilizer;

import io.github.chaosawakens.content.block_entity.defossilizer.CrystalDefossilizerBlockEntity;
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

public class CrystalDefossilizerBlock extends AbstractDefossilizerBlock {

    public CrystalDefossilizerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void openContainer(Level curLevel, BlockPos targetPos, Player interactingPlayer) {
        BlockEntity targetBlockEntity = curLevel.getBlockEntity(targetPos);

        if (targetBlockEntity instanceof CrystalDefossilizerBlockEntity targetCrystalDefossilizerBlockEntity) {
            interactingPlayer.openMenu(targetCrystalDefossilizerBlockEntity);
            interactingPlayer.awardStat(CAStats.INTERACT_WITH_CRYSTAL_DEFOSSILIZER.get());
        }
    }

    @Override
    public @Nullable CrystalDefossilizerBlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CrystalDefossilizerBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <BE extends BlockEntity> BlockEntityTicker<BE> getTicker(Level level, BlockState state, BlockEntityType<BE> blockEntityType) {
        return createDefossilizerTicker(level, blockEntityType, CABlockEntityTypes.CRYSTAL_DEFOSSILIZER.get());
    }
}
