package io.github.chaosawakens.content.item.equipment.tools.ruby;

import io.github.chaosawakens.content.item.misc.EnchantedSwordItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

public class RubySword extends EnchantedSwordItem {
    public static final int ATTACK_DAMAGE = 10;

    public RubySword(Tier tier, int attackDamage, float attackSpeedModifier, Properties properties, Map<Enchantment, Integer> enchantment) {
        super(tier, attackDamage, attackSpeedModifier, properties, enchantment);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        if (world.isClientSide) return;
        if (!EnchantmentHelper.getEnchantments(stack).isEmpty()) return;
        stack.enchant(Enchantments.FIRE_ASPECT, 1);
    }
}
