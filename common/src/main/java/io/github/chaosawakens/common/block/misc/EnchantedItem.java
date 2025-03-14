package io.github.chaosawakens.common.block.misc;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EnchantedItem extends Item {

    public EnchantedItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack targetStack) {
        return true;
    }
}
