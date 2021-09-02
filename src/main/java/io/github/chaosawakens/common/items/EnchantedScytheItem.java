package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.IAutoEnchantable;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class EnchantedScytheItem extends ScytheItem implements IAutoEnchantable {

    private final EnchantmentData[] enchantments;

    public EnchantedScytheItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.enchantments = enchantments;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            ItemStack stack = new ItemStack(this);
            if (CAConfig.COMMON.enableAutoEnchanting.get())
                for (EnchantmentData enchant : enchantments) {
                    stack.enchant(enchant.enchantment, enchant.level);
                }
            items.add(stack);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return CAConfig.COMMON.enableAutoEnchanting.get() || super.isFoil(stack);
    }

    @Override
    public EnchantmentData[] enchantments() {
        return this.enchantments;
    }

}