package io.github.chaosawakens.items;

import io.github.chaosawakens.items.util.generic.enchanted.GenericEnchantedSwordItem;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class PoisonSword extends GenericEnchantedSwordItem {
    public PoisonSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, EnchantmentLevelEntry[] enchantments) {
        super(toolMaterial, attackDamage, attackSpeed, settings, enchantments);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (target != null) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, (10 + target.getRandom().nextInt(10)) * 20, 0));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, (10 + target.getRandom().nextInt(10)) * 20, 0));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, (10 + target.getRandom().nextInt(10)) * 20, 0));
        }
        return true;
    }
}
