package io.github.chaosawakens.common.items.weapons;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.EnchantedSwordItem;
import io.github.chaosawakens.common.registry.CAEffects;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

public class BasiliskSwordItem extends EnchantedSwordItem {
	
	public BasiliskSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn, Supplier<EnchantmentData[]> enchantments) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn, enchantments);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.addEffect(new EffectInstance(CAEffects.PARALYSIS_EFFECT.get(), (3 + target.getRandom().nextInt(6)) * 20, 0));
		return super.hurtEnemy(stack, target, attacker);
	}
}
