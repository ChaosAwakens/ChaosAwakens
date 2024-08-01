package io.github.chaosawakens.common.potion.effects.harmful;

import io.github.chaosawakens.common.registry.CADamageSources;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import java.util.Map.Entry;

public class BurnsEffect extends Effect {

	public BurnsEffect() {
		super(EffectType.HARMFUL, 14241294);
	}
	
	@Override
	public void applyEffectTick(LivingEntity targetEntity, int exhaustion) {
		Entry<EquipmentSlotType, ItemStack> fireResSlot = EnchantmentHelper.getRandomItemWith(Enchantments.FIRE_PROTECTION, targetEntity);
		
		if (fireResSlot == null)
			targetEntity.hurt(CADamageSources.BURNS, 1.0f);
	}
	
	@Override
	public boolean isDurationEffectTick(int tickCount, int level) {
		return tickCount % (level < 4 ? (20 - level * 5) : 5) == 0;
	}
}
