package io.github.chaosawakens.common.items.weapons;

import io.github.chaosawakens.common.items.base.EnchantedSwordItem;
import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.function.Supplier;

public class BasiliskSwordItem extends EnchantedSwordItem {

	public BasiliskSwordItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pProperties, enchantments);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.addEffect(new EffectInstance(CAEffects.PARALYSIS_EFFECT.get(), (3 + target.getRandom().nextInt(6)) * 20, 0));
		return super.hurtEnemy(stack, target, attacker);
	}
}