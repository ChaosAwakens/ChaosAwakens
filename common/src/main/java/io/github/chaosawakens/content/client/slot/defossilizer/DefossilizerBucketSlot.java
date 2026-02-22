package io.github.chaosawakens.content.client.slot.defossilizer;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;

public class DefossilizerBucketSlot extends Slot {

    public DefossilizerBucketSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof BucketItem;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
