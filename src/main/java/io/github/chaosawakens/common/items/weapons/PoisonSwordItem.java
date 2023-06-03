package io.github.chaosawakens.common.items.weapons;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.EnchantedSwordItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class PoisonSwordItem extends EnchantedSwordItem {

	public PoisonSwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pProperties, enchantments);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target != null) {
			target.addEffect(new EffectInstance(Effects.CONFUSION, (10 + target.getRandom().nextInt(10)) * 20, 0));
			target.addEffect(new EffectInstance(Effects.POISON, (10 + target.getRandom().nextInt(10)) * 20, 0));
			target.addEffect(new EffectInstance(Effects.WEAKNESS, (10 + target.getRandom().nextInt(10)) * 20, 0));
			target.addEffect(new EffectInstance(Effects.WITHER, (10 + target.getRandom().nextInt(10)) * 20, 0));
		}
		return super.hurtEnemy(stack, target, attacker);
	}
}
