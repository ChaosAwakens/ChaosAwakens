package io.github.chaosawakens.items.util.generic.enchanted;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.IPreEnchanted;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.collection.DefaultedList;

public class GenericEnchantedHoeItem extends HoeItem implements IPreEnchanted {
    private final EnchantmentLevelEntry[] enchantments;

    public GenericEnchantedHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings, EnchantmentLevelEntry[] enchantments) {
        super(material, attackDamage, attackSpeed, settings);
        this.enchantments = enchantments;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            ItemStack stack = new ItemStack(this);
            if (ChaosAwakens.config.misc.enableAutoEnchanting)
                for(EnchantmentLevelEntry enchant : enchantments) {
                    stack.addEnchantment(enchant.enchantment, enchant.level);
                }
            stacks.add(stack);
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return ChaosAwakens.config.misc.enableAutoEnchanting || super.hasGlint(stack);
    }


    @Override
    public EnchantmentLevelEntry[] enchant() {
        return this.enchantments;
    }
}
