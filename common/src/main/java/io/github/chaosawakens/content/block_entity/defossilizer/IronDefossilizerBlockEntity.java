package io.github.chaosawakens.content.block_entity.defossilizer;

import io.github.chaosawakens.content.client.menu.defossilizer.IronDefossilizerMenu;
import io.github.chaosawakens.content.registry.CABlockEntityTypes;
import io.github.chaosawakens.content.registry.CAMenuTypes;
import io.github.chaosawakens.content.registry.CARecipeData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IronDefossilizerBlockEntity extends AbstractDefossilizerBlockEntity {

    public IronDefossilizerBlockEntity(BlockPos pos, BlockState blockState) {
        super(CABlockEntityTypes.IRON_DEFOSSILIZER.get(), pos, blockState, CARecipeData.CARecipeTypes.DEFOSSILIZING.get());
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.chaosawakens.iron_defossilizer");
    }

    @Override
    protected @NotNull IronDefossilizerMenu createMenu(int containerId, Inventory inventory) {
        return new IronDefossilizerMenu(CAMenuTypes.IRON_DEFOSSILIZER.get(), containerId, inventory, this, dataAccess);
    }
}
