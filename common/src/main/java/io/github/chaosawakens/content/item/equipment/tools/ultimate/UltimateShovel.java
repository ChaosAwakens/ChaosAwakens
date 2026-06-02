package io.github.chaosawakens.content.item.equipment.tools.ultimate;

import io.github.chaosawakens.content.item.misc.EnchantedShovelItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

public class UltimateShovel extends EnchantedShovelItem {
    public UltimateShovel(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, Map<Enchantment, Integer> enchantments) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties, enchantments);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        if (world.isClientSide) return;
        if (!EnchantmentHelper.getEnchantments(stack).isEmpty()) return;
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, 5);
        stack.enchant(Enchantments.UNBREAKING, 3);
    }
}
