package io.github.chaosawakens.content.client.menu.defossilizer;

import io.github.chaosawakens.content.block_entity.defossilizer.AbstractDefossilizerBlockEntity;
import io.github.chaosawakens.content.client.slot.defossilizer.DefossilizerBucketSlot;
import io.github.chaosawakens.content.client.slot.defossilizer.DefossilizerFossilSlot;
import io.github.chaosawakens.content.client.slot.defossilizer.DefossilizerPowerChipSlot;
import io.github.chaosawakens.content.client.slot.defossilizer.DefossilizerResultSlot;
import io.github.chaosawakens.content.data.recipe.defossilizing.AbstractDefossilizingRecipe;
import io.github.chaosawakens.content.registry.CARecipeData;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDefossilizerMenu extends RecipeBookMenu<Container> {
    protected final Level level;
    protected final RecipeType<? extends AbstractDefossilizingRecipe> recipeType;
    protected final Container container;
    protected final ContainerData data;

    public AbstractDefossilizerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, Container container, ContainerData data) {
        super(menuType, containerId);

        this.level = playerInventory.player.level();
        this.recipeType = CARecipeData.CARecipeTypes.DEFOSSILIZING.get();

        checkContainerSize(container, 4);
        checkContainerDataCount(data, 2);

        this.container = container;
        this.data = data;

        addSlot(new DefossilizerFossilSlot(container, AbstractDefossilizerBlockEntity.FOSSIL_INPUT_SLOT_INDEX, 56, 17));
        addSlot(new DefossilizerBucketSlot(container, AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX, 47, 53));
        addSlot(new DefossilizerPowerChipSlot(container, AbstractDefossilizerBlockEntity.POWER_CHIP_INPUT_SLOT_INDEX, 65, 53));
        addSlot(new DefossilizerResultSlot(container, AbstractDefossilizerBlockEntity.DEFOSSILIZED_OUTPUT_SLOT_INDEX, 116, 35, playerInventory.player));

        addInventorySlots(playerInventory);
        addDataSlots(data);
    }

    public AbstractDefossilizerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory) {
        this(menuType, containerId, playerInventory, new SimpleContainer(4), new SimpleContainerData(2));
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents stackedContents) {
        if (container instanceof StackedContentsCompatible stackedContentsCompatibleContainer) {
            stackedContentsCompatibleContainer.fillStackedContents(stackedContents);
        }
    }

    @Override
    public void clearCraftingContent() {
        getSlot(AbstractDefossilizerBlockEntity.FOSSIL_INPUT_SLOT_INDEX).set(ItemStack.EMPTY);
        getSlot(AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX).set(ItemStack.EMPTY);
        getSlot(AbstractDefossilizerBlockEntity.POWER_CHIP_INPUT_SLOT_INDEX).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe<? super Container> recipe) {
        return recipe.matches(container, level);
    }

    @Override
    public int getResultSlotIndex() {
        return AbstractDefossilizerBlockEntity.DEFOSSILIZED_OUTPUT_SLOT_INDEX;
    }

    @Override
    public int getGridWidth() {
        return 1;
    }

    @Override
    public int getGridHeight() {
        return 1;
    }

    @Override
    public int getSize() {
        return AbstractDefossilizerBlockEntity.TOTAL_DEFOSSILIZER_SLOTS;
    }

    @Override
    public @NotNull RecipeBookType getRecipeBookType() {
        return RecipeBookType.FURNACE; // TODO
    }

    @Override
    public boolean shouldMoveToInventory(int slotIdx) {
        return slotIdx != AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX && slotIdx != AbstractDefossilizerBlockEntity.POWER_CHIP_INPUT_SLOT_INDEX;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player interactingPlayer, int slotIdx) {
        ItemStack movedStack = ItemStack.EMPTY;
        Slot targetSlot = getSlot(slotIdx);

        if (targetSlot != null && targetSlot.hasItem()) {
            ItemStack slotStack = targetSlot.getItem();
            movedStack = slotStack.copy();

            // Actual slot handling
            if (slotIdx == AbstractDefossilizerBlockEntity.DEFOSSILIZED_OUTPUT_SLOT_INDEX) {
                if (!moveItemStackTo(slotStack, AbstractDefossilizerBlockEntity.INV_SLOT_START, AbstractDefossilizerBlockEntity.USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotIdx != AbstractDefossilizerBlockEntity.FOSSIL_INPUT_SLOT_INDEX && slotIdx != AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX && slotIdx != AbstractDefossilizerBlockEntity.POWER_CHIP_INPUT_SLOT_INDEX) {
                if (canDefossilize(slotStack)) {
                    if (!moveItemStackTo(slotStack, AbstractDefossilizerBlockEntity.FOSSIL_INPUT_SLOT_INDEX, AbstractDefossilizerBlockEntity.FOSSIL_INPUT_SLOT_INDEX + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isPowerChipComponent(slotStack)) {
                    if (!moveItemStackTo(slotStack, AbstractDefossilizerBlockEntity.POWER_CHIP_INPUT_SLOT_INDEX, AbstractDefossilizerBlockEntity.POWER_CHIP_INPUT_SLOT_INDEX + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isBucketComponent(slotStack)) {
                    if (!moveItemStackTo(slotStack, AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX, AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIdx >= AbstractDefossilizerBlockEntity.INV_SLOT_START && slotIdx < AbstractDefossilizerBlockEntity.INV_SLOT_END) {
                    if (!moveItemStackTo(slotStack, AbstractDefossilizerBlockEntity.USE_ROW_SLOT_START, AbstractDefossilizerBlockEntity.USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotIdx >= AbstractDefossilizerBlockEntity.USE_ROW_SLOT_START && slotIdx < AbstractDefossilizerBlockEntity.USE_ROW_SLOT_END) {
                    if (!moveItemStackTo(slotStack, AbstractDefossilizerBlockEntity.INV_SLOT_START, AbstractDefossilizerBlockEntity.INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!moveItemStackTo(slotStack, AbstractDefossilizerBlockEntity.INV_SLOT_START, AbstractDefossilizerBlockEntity.USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) targetSlot.set(ItemStack.EMPTY);
            else targetSlot.setChanged();

            if (slotStack.getCount() == movedStack.getCount()) return movedStack;

            targetSlot.onTake(interactingPlayer, slotStack);
        }

        return movedStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    public int getDefossilizationProgress() {
        int defossilizationProgress = data.get(0);
        int totalDefossilizationTime = data.get(1);

        return defossilizationProgress > 0 && totalDefossilizationTime > 0
                ? defossilizationProgress * 24 / totalDefossilizationTime
                : 0;
    }

    public boolean isDefossilizing() {
        return getDefossilizationProgress() > 0;
    }

    protected boolean canDefossilize(ItemStack fossilStack) {
        return level.getRecipeManager().getRecipeFor(recipeType, new SimpleContainer(fossilStack), level).isPresent();
    }

    protected boolean isPowerChipComponent(ItemStack targetStack) {
        return AbstractDefossilizerBlockEntity.isPowerChipComponent(targetStack);
    }

    protected boolean isBucketComponent(ItemStack targetStack) {
        return AbstractDefossilizerBlockEntity.isBucketComponent(targetStack);
    }

    protected void addInventorySlots(Inventory playerInventory) {
        for (int invColumn = 0; invColumn < 3; ++invColumn) {
            for (int invRow = 0; invRow < 9; ++invRow) {
                addSlot(new Slot(playerInventory, invRow + invColumn * 9 + 9, 8 + invRow * 18, 84 + invColumn * 18));
            }
        }

        for (int slotIdx = 0; slotIdx < 9; ++slotIdx) {
            addSlot(new Slot(playerInventory, slotIdx, 8 + slotIdx * 18, 142));
        }
    }
}
