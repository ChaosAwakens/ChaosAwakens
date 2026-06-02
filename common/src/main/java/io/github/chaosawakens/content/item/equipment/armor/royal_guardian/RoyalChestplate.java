package io.github.chaosawakens.content.item.equipment.armor.royal_guardian;

import io.github.chaosawakens.content.item.misc.EnchantedArmorItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

public class RoyalChestplate extends EnchantedArmorItem {
    public RoyalChestplate(ArmorMaterial material, Type type, Properties properties, Map<Enchantment, Integer> enchantments) {
        super(material, type, properties, enchantments);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        if (world.isClientSide) return;
        if (!EnchantmentHelper.getEnchantments(stack).isEmpty()) return;
        stack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 10);
        stack.enchant(Enchantments.FIRE_PROTECTION, 10);
        stack.enchant(Enchantments.BLAST_PROTECTION, 10);
        stack.enchant(Enchantments.PROJECTILE_PROTECTION, 10);
        stack.enchant(Enchantments.UNBREAKING, 5);
    }
}
