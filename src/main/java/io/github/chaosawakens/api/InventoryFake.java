package io.github.chaosawakens.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryFake implements IInventory {

    private ItemStack stack;

    public InventoryFake() { }

    public InventoryFake(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public boolean isEmpty() { 
    	return false; 
    }

	@Override
	public void clearContent() {
		
	}

	@Override
	public int getContainerSize() {
		return stack == null? 0 : 1;
	}

	@Override
	public ItemStack getItem(int i) {
		return stack;
	}

	@Override
	public ItemStack removeItem(int amount, int invslot) {
		return null;
	}

	@Override
	public ItemStack removeItemNoUpdate(int i) {
		return null;
	}

	@Override
	public void setChanged() {
		
	}

	@Override
	public void setItem(int i, ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public boolean stillValid(PlayerEntity arg0) {
		return false;
	}
}