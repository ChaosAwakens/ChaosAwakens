package io.github.chaosawakens.content.block_entity.robo;

import io.github.chaosawakens.content.block_entity.basic.OrientableContainerBlockEntity;
import io.github.chaosawakens.content.registry.CABlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class RoboCrateBlockEntity extends OrientableContainerBlockEntity {

    public RoboCrateBlockEntity(BlockPos pos, BlockState blockState) {
        super(CABlockEntityTypes.ROBO_CRATE.get(), pos, blockState);
    }

    @Override
    public SoundEvent getOpenSound() {
        return SoundEvents.BARREL_OPEN;
    }

    @Override
    public SoundEvent getCloseSound() {
        return SoundEvents.BARREL_CLOSE;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.chaosawakens.robo_crate");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new ChestMenu(MenuType.GENERIC_9x4, containerId, inventory, this, 4);
    }

    @Override
    public int getContainerSize() {
        return 36;
    }
}
