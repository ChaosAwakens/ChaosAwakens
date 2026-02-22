package io.github.chaosawakens.content.client.slot.defossilizer;

import io.github.chaosawakens.content.block_entity.defossilizer.AbstractDefossilizerBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DefossilizerFossilSlot extends Slot {

    public DefossilizerFossilSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return AbstractDefossilizerBlockEntity.isFossil(stack);
    }
}
