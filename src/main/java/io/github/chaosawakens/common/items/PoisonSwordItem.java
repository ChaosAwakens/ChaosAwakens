package io.github.chaosawakens.common.items;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import net.minecraft.item.Item.Properties;

public class PoisonSwordItem extends EnchantedSwordItem {

    public PoisonSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, EnchantmentData[] enchantments) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target != null) {
            target.addEffect(new EffectInstance(Effects.POISON,(10 + target.getRandom().nextInt(10)) * 20,0));
            target.addEffect(new EffectInstance(Effects.WITHER,(10 + target.getRandom().nextInt(10)) * 20,0));
            target.addEffect(new EffectInstance(Effects.WEAKNESS,(10 + target.getRandom().nextInt(10)) * 20,0));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}

