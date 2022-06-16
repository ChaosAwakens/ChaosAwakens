package io.github.chaosawakens.common.blocks.tileentities;

import io.github.chaosawakens.common.registry.CABlocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class CrystalFurnaceFuelSlot extends Slot {
	public CrystalFurnaceFuelSlot(IInventory p_i50084_2_, int p_i50084_3_, int p_i50084_4_, int p_i50084_5_) {
		super(p_i50084_2_, p_i50084_3_, p_i50084_4_, p_i50084_5_);
	}

	public boolean mayPlace(ItemStack itemStack) {
		return itemStack.getItem() == CABlocks.CRYSTAL_ENERGY.get().asItem();
	}
}
