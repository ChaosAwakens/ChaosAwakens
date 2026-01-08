package io.github.chaosawakens.content.item.misc;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnchantedItem extends Item {

    public EnchantedItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
