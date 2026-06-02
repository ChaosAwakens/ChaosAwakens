package io.github.chaosawakens.content.item.equipment.big_wepons;

import io.github.chaosawakens.content.item.misc.EnchantedSwordItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Map;

public class QueenBattleAxe extends EnchantedSwordItem {
    public static final int ATTACK_DAMAGE = 665;

    public QueenBattleAxe(Tier tier, int attackDamage, float attackSpeedModifier, Item.Properties properties, Map<Enchantment, Integer> enchantment) {
        super(tier, attackDamage, attackSpeedModifier, properties, enchantment);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {
        super.onCraftedBy(stack, world, player);
        if (world.isClientSide) return;
        if (!EnchantmentHelper.getEnchantments(stack).isEmpty()) return;
        stack.enchant(Enchantments.SHARPNESS, 5);
        stack.enchant(Enchantments.SMITE, 5);
        stack.enchant(Enchantments.BANE_OF_ARTHROPODS, 5);
        stack.enchant(Enchantments.KNOCKBACK, 3);
        stack.enchant(Enchantments.MOB_LOOTING, 3);
        stack.enchant(Enchantments.UNBREAKING, 3);
        stack.enchant(Enchantments.FIRE_ASPECT, 2);
    }
}
