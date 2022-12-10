package io.github.chaosawakens.common.enchantments;

import java.util.Map.Entry;
import java.util.Random;

import io.github.chaosawakens.common.registry.CAEffects;
import io.github.chaosawakens.common.registry.CAEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

/**
 * @author invalid2
 */
public class HoplologyEnchantment extends ProtectionEnchantment {

	/**
	 * @param p_i46731_1_
	 * @param p_i46731_2_
	 * @param p_i46731_3_
	 */
	public HoplologyEnchantment(Rarity rarity, EquipmentSlotType[] slotType) {
		super(rarity, ProtectionEnchantment.Type.ALL, slotType);
	}
	
	public void doPostHurt(LivingEntity attacked, Entity attacker, int level) {
		Random random = attacked.getRandom();
		
		Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWith(CAEnchantments.IGNITION.get(), attacked);
		//if (shouldHit(level, random)) {
			if (attacker != null && attacker instanceof LivingEntity) {
				//hit.hurt(DamageSource.MAGIC, 0);
				((LivingEntity) attacker).addEffect( new EffectInstance(CAEffects.BURNS_EFFECT.get(), 60, level - 1));
			}
			//((PlayerEntity) attacked).totalExperience
			if (entry != null)
				entry.getValue().hurtAndBreak(2, attacked, (entity) -> entity.broadcastBreakEvent(entry.getKey()) );
		//}
	}
}
