package io.github.chaosawakens.common.enchantments;

import java.util.Map.Entry;
import java.util.Random;

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

/**
 * @author invalid2
 */
public class IgnitionEnchantment extends Enchantment {

	/**
	 * 
	 */
	public IgnitionEnchantment(Rarity rarity, EquipmentSlotType[] slots) {
		super(rarity, EnchantmentType.ARMOR, slots);
	}
	
	public int getMinCost(int level) { return 10 + 20 * (level - 1); }	
	public int getMaxCost(int level) { return super.getMinCost(level) + 50; }	
	public int getMaxLevel() { return 3; }
   
	public void doPostHurt(LivingEntity attacked, Entity attacker, int level) {
		Random random = attacked.getRandom();
		
		Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWith(CAEnchantments.IGNITION.get(), attacked);
		if (shouldHit(level, random)) {
			if (attacker != null && attacker instanceof LivingEntity) {
				((LivingEntity) attacker).addEffect( new EffectInstance(CAEffects.BURNS.get(), 60, level - 1));
			}
			
			if (entry != null)
				entry.getValue().hurtAndBreak(2, attacked, (entity) -> entity.broadcastBreakEvent(entry.getKey()) );
		}
	}
	
	public static boolean shouldHit(int level, Random rand) {
		if (level <= 0) {
			return false;
		} else {
			//return true;
			return rand.nextFloat() < 0.15F * (float) level;
		}
	}
}
