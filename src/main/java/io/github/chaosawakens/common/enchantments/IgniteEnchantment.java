package io.github.chaosawakens.common.enchantments;

import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

import java.util.Map.Entry;
import java.util.Random;

public class IgniteEnchantment extends Enchantment {

	public IgniteEnchantment(EquipmentSlotType[] slotType) {
		super(Rarity.RARE, EnchantmentType.ARMOR, slotType);
	}

	public void doPostHurt(LivingEntity target, Entity attacker, int level) {
		Random rand = target.getRandom();
		Entry<EquipmentSlotType, ItemStack> igniteEnchantedItem = EnchantmentHelper.getRandomItemWith(CAEnchantments.IGNITE.get(), target);

		if (shouldHit(level, rand)) {
			if (attacker != null && attacker instanceof LivingEntity) ((LivingEntity) attacker).addEffect(new EffectInstance(CAEffects.BURNS_EFFECT.get(), 20 + level * 60, level - 1));
			if (igniteEnchantedItem != null) igniteEnchantedItem.getValue().hurtAndBreak(2, target, (entity) -> entity.broadcastBreakEvent(igniteEnchantedItem.getKey()));
		}
	}

	public static boolean shouldHit(int level, Random rand) {
		return level > 0 && rand.nextFloat() < 0.15F * (float) level;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}
}
