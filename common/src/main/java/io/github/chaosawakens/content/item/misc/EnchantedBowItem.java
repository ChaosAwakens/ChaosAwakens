package io.github.chaosawakens.content.item.misc;

import io.github.chaosawakens.content.item.equipment.tools.UltimateBow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public class EnchantedBowItem extends BowItem {
    private final Map<Enchantment, Integer> defaultEnchantments;

    public EnchantedBowItem(Properties properties, Map<Enchantment, Integer> enchantments) {
        super(properties);
        this.defaultEnchantments = enchantments;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        for (Map.Entry<Enchantment, Integer> entry : defaultEnchantments.entrySet()) {
            stack.enchant(entry.getKey(), entry.getValue());
        }
        return stack;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

}
