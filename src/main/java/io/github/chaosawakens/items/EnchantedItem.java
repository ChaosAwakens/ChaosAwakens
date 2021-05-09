package io.github.chaosawakens.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedItem extends Item {

    public EnchantedItem(Item.Properties builderIn) {
        super(builderIn);
    }

    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
