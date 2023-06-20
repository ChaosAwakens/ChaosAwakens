package io.github.chaosawakens.common.items.weapons;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.CASwordItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class BigHammerItem extends CASwordItem {
	
	public BigHammerItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties) {
		super(pTier, configDmg, pProperties);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level.isClientSide)  target.push(0.0D, Math.abs((1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)) * 1.25F), 0.0D);
		
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}
}
