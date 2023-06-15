package io.github.chaosawakens.common.blocks.tileentities.slots;

import io.github.chaosawakens.common.registry.CATags;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class CrystalFurnaceFuelSlot extends Slot {
	
	public CrystalFurnaceFuelSlot(IInventory inv, int slotIndex, int xPos, int yPos) {
		super(inv, slotIndex, xPos, yPos);
	}

	@Override
	public boolean mayPlace(ItemStack itemStack) {
		return itemStack.getItem().is(CATags.Items.CRYSTAL_FURNACE_FUEL);
	}
}
