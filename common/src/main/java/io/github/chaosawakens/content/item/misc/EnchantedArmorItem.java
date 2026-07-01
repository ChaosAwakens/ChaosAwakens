package io.github.chaosawakens.content.item.misc;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

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
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        if (world.isClientSide) return;
        if (!EnchantmentHelper.getEnchantments(stack).isEmpty()) return;
        for (Map.Entry<Enchantment, Integer> entry : defaultEnchantments.entrySet()) {
            stack.enchant(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }


}