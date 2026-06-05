package io.github.chaosawakens.content.item.misc;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public class EnchantedHoeItem extends HoeItem {

    private final Map<Enchantment, Integer> defaultEnchantments;

    public EnchantedHoeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, Map<Enchantment, Integer> enchantments) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
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
