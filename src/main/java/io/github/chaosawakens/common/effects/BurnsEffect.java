package io.github.chaosawakens.common.effects;

import java.util.Map.Entry;

import io.github.chaosawakens.common.registry.CADamageSources;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class BurnsEffect extends Effect {

	public BurnsEffect() {
		super(EffectType.HARMFUL, 14241294);
	}
	
	public void applyEffectTick(LivingEntity targetEntity, int exhaustion) {
		Entry<EquipmentSlotType, ItemStack> fireResSlot = EnchantmentHelper.getRandomItemWith(Enchantments.FIRE_PROTECTION, targetEntity);
		if(fireResSlot == null)
			targetEntity.hurt(CADamageSources.BURNS, 1.0f);
	}
	
	public boolean isDurationEffectTick(int tickCount, int level) {
		return tickCount % (20 - level * 5) == 0;
	}

}
