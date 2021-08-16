package io.github.chaosawakens.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedItem extends Item {

    public EnchantedItem(Item.Properties builderIn) {
        super(builderIn);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}