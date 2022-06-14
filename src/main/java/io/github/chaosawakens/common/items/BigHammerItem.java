package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.registry.CATags;
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

<<<<<<< HEAD
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (!target.level.isClientSide) {
			target.push(0.0D, Math.abs((1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)) * 1.25F), 0.0D);
		}
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}
=======
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.level.isClientSide) {
            target.push(0.0D, Math.abs(target.level.random.nextFloat() * 1.8F), 0.0D);
        }
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        return true;
    }
>>>>>>> 07fb30b8cfd30fed51b85aaa25508814dff5a0fc
}
