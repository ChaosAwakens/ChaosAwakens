package io.github.chaosawakens.common.blocks.tileentities;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.crafting.recipe.AbstractDefossilizingRecipe;
import io.github.chaosawakens.common.registry.CAContainerTypes;
import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CARecipes;
import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public class DefossilizerContainer extends Container {
    private final IInventory inventory;
    private IIntArray fields;
    protected final World level;
    private final IRecipeType<? extends AbstractDefossilizingRecipe> recipeType;

    public DefossilizerContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(CARecipes.DEFOSSILIZING_RECIPE_TYPE, id, playerInventory, new DefossilizerTileEntity(), new IntArray(buffer.readByte()));
    }

    public DefossilizerContainer(IRecipeType<? extends AbstractDefossilizingRecipe> recipeType, int id, PlayerInventory playerInventory, IInventory inventory, IIntArray fields) {
        super(CAContainerTypes.DEFOSSILIZER.get(), id);
        this.recipeType = recipeType;
        this.level = playerInventory.player.level;
        this.inventory = inventory;
        this.fields = fields;

        this.addSlot(new Slot(this.inventory, 0, 56, 17));
        this.addSlot(new Slot(this.inventory, 1, 45, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return !stack.getItem().getTags().contains(CATags.BUCKETS);
            }
        });
        this.addSlot(new Slot(this.inventory, 2, 63, 53) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return !stack.getItem().equals(CAItems.ALUMINUM_POWER_CHIP);
            }
        });
        this.addSlot(new Slot(this.inventory, 3, 100, 50) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // Player backpack
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                int index = 9 + x + y * 9;
                int posX = 8 + x * 18;
                int posY = 84 + y * 18;
                this.addSlot(new Slot(playerInventory, index, posX, posY));
            }
        }

        // Player hotbar
        for (int x = 0; x < 9; ++x) {
            int index = x;
            int posX = 8 + x * 18;
            int posY = 142;
            this.addSlot(new Slot(playerInventory, index, posX, posY));
        }

        this.addDataSlots(fields);
    }

    public  int getProgressArrowScale() {
        int progress = fields.get(0);
        if (progress > 0) {
            return progress * 24 / DefossilizerTileEntity.WORK_TIME;
        }
        return 0;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();

            final int inventorySize = 4;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == 3) {
                if (!this.moveItemStackTo(itemStack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack1, itemStack);
            } else if (index != 0 && index != 1 && index != 2) {
                if (this.canDefossilize(itemStack1)) {
                    if (!this.moveItemStackTo(itemStack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isPowerChip(itemStack1)) {
                    if (!this.moveItemStackTo(itemStack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isBucket(itemStack1)) {
                    if (!this.moveItemStackTo(itemStack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 4 && index < 30) {
                    if (!this.moveItemStackTo(itemStack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemStack1, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack1);
        }

        return itemStack;
    }

    protected boolean canDefossilize(ItemStack stack) {
        ChaosAwakens.LOGGER.debug("Chaos Awakens canDefossilize: ERROR!");
        return this.level.getRecipeManager().getRecipeFor((IRecipeType)this.recipeType, new Inventory(stack), this.level).isPresent();
    }

    protected boolean isBucket(ItemStack stack) {
        ChaosAwakens.LOGGER.debug("Chaos Awakens isBucket: ERROR!");
        if (slots.isEmpty() && stack.getTag().equals(CATags.BUCKETS)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isPowerChip(ItemStack stack) {
        ChaosAwakens.LOGGER.debug("Chaos Awakens isPowerChip: ERROR!");
        if (slots.isEmpty() && stack.equals(CAItems.ALUMINUM_POWER_CHIP)) {
            return true;
        } else if (!slots.isEmpty() && slots.get(2).getItem().equals(CAItems.ALUMINUM_POWER_CHIP) && stack.equals(CAItems.ALUMINUM_POWER_CHIP)) {
            return true;
        } else {
            return false;
        }
    }
}