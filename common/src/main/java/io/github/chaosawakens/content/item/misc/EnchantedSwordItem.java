package io.github.chaosawakens.content.item.misc;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import java.util.Map;
import java.util.UUID;

public class EnchantedSwordItem extends SwordItem {
    public static final UUID ATTACK_RANGE_UUID = UUID.fromString("bfef09b6-ee2a-4da4-a2ff-58b5d46efd5e");
    private final Map<Enchantment, Integer> defaultEnchantments;

    public EnchantedSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, Map<Enchantment, Integer> enchantments) {
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
    } // Naturally true but can be overriden in respective class
}
