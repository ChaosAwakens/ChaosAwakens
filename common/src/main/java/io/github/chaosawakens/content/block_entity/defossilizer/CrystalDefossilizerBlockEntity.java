package io.github.chaosawakens.content.block_entity.defossilizer;

import io.github.chaosawakens.content.registry.CABlockEntityTypes;
import io.github.chaosawakens.content.registry.CARecipeData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CrystalDefossilizerBlockEntity extends AbstractDefossilizerBlockEntity {

    public CrystalDefossilizerBlockEntity(BlockPos pos, BlockState blockState) {
        super(CABlockEntityTypes.CRYSTAL_DEFOSSILIZER.get(), pos, blockState, CARecipeData.CARecipeTypes.DEFOSSILIZING.get());
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.chaosawakens.crystal_defossilizer");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }
}