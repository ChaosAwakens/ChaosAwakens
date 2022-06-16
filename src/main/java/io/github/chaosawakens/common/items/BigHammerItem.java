package io.github.chaosawakens.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class BigHammerItem extends SwordItem {
	public BigHammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
		super(tier, attackDamageIn, attackSpeedIn, builderIn);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level.isClientSide) {
			target.push(0.0D, Math.abs((1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)) * 1.25F), 0.0D);
		}
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}
}
