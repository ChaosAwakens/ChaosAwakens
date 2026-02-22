package io.github.chaosawakens.content.client.slot.defossilizer;

import io.github.chaosawakens.content.item.utility.PowerChipItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DefossilizerPowerChipSlot extends Slot {

    public DefossilizerPowerChipSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof PowerChipItem;
    }
}
