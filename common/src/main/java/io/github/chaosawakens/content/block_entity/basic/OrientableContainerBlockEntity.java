package io.github.chaosawakens.content.block_entity.basic;

import io.github.chaosawakens.content.block.block_entity.basic.OrientableContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class OrientableContainerBlockEntity extends RandomizableContainerBlockEntity {
    protected final ContainerOpenersCounter openersCounter;
    protected NonNullList<ItemStack> storedItems;

    protected OrientableContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);

        this.storedItems = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {

            @Override
            protected void onOpen(Level curLevel, BlockPos targetPos, BlockState targetState) {
                playSound(targetState, getOpenSound());
                updateBlockState(targetState, true);
            }

            @Override
            protected void onClose(Level curLevel, BlockPos targetPos, BlockState targetState) {
                playSound(targetState, getCloseSound());
                updateBlockState(targetState, false);
            }

            @Override
            protected void openerCountChanged(Level curLevel, BlockPos targetPos, BlockState targetState, int oldOpenCount, int newOpenCount) {
            }

            @Override
            protected boolean isOwnContainer(Player interactingPlayer) {
                if (interactingPlayer.containerMenu instanceof ChestMenu chestContainerMenu) {
                    Container chestContainer = chestContainerMenu.getContainer();
                    return chestContainer == OrientableContainerBlockEntity.this;
                } else return false;
            }
        };
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return storedItems;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> storedItems) {
        this.storedItems = storedItems;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        if (!trySaveLootTable(tag)) ContainerHelper.saveAllItems(tag, storedItems);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        this.storedItems = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);

        if (!tryLoadLootTable(tag)) ContainerHelper.loadAllItems(tag, storedItems);
    }

    @Override
    public void startOpen(Player interactingPlayer) {
        if (!remove && !interactingPlayer.isSpectator()) {
            openersCounter.incrementOpeners(interactingPlayer, getLevel(), getBlockPos(), getBlockState());
        }
    }

    @Override
    public void stopOpen(Player interactingPlayer) {
        if (!remove && !interactingPlayer.isSpectator()) {
            openersCounter.decrementOpeners(interactingPlayer, getLevel(), getBlockPos(), getBlockState());
        }
    }

    public void recheckOpen() {
        if (!remove) openersCounter.recheckOpeners(getLevel(), getBlockPos(), getBlockState());
    }

    protected void updateBlockState(BlockState targetState, boolean isOpen) {
        level.setBlock(getBlockPos(), targetState.setValue(OrientableContainerBlock.OPEN, isOpen), Block.UPDATE_ALL);
    }

    protected void playSound(BlockState targetState, SoundEvent soundToPlay) {
        Vec3i facingDir = targetState.getValue(OrientableContainerBlock.FACING).getNormal();

        double targetX = worldPosition.getX() + 0.5D + facingDir.getX() / 2.0D;
        double targetY = worldPosition.getY() + 0.5D + facingDir.getY() / 2.0D;
        double targetZ = worldPosition.getZ() + 0.5D + facingDir.getZ() / 2.0D;

        level.playSound(null, targetX, targetY, targetZ, soundToPlay, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    public abstract SoundEvent getOpenSound();

    public abstract SoundEvent getCloseSound();
}
