package io.github.chaosawakens.content.item.misc;

import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public class EnchantedArmorItem extends ArmorItem {

    private final Map<Enchantment, Integer> defaultEnchantments;

    public EnchantedArmorItem(ArmorMaterial material, Type type, Properties properties, Map<Enchantment, Integer> enchantments) {
        super(material, type, properties);
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