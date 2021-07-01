package io.github.chaosawakens.items.util.generic.enchanted;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GenericEnchantedItem extends Item {
    public GenericEnchantedItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
