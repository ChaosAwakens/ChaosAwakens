package io.github.chaosawakens.items.util.generic.enchanted;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.IPreEnchanted;
import io.github.chaosawakens.items.util.generic.GenericScytheItem;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.collection.DefaultedList;

public class GenericEnchantedScytheItem extends GenericScytheItem implements IPreEnchanted {
    private final EnchantmentLevelEntry[] enchantments;

    public GenericEnchantedScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, EnchantmentLevelEntry[] enchantments) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
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
