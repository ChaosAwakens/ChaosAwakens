package io.github.chaosawakens.common.items;

import java.util.function.Supplier;

import io.github.chaosawakens.common.items.base.CASwordItem;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class MantisClawItem extends CASwordItem {
	
	public MantisClawItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties) {
		super(pTier, configDmg, pProperties);
	}

	@Override
	public boolean hurtEnemy(ItemStack targetStack, LivingEntity target, LivingEntity attacker) {
		int damageMultiplier = attacker.getHealth() >= attacker.getMaxHealth() ? 2 : 1;
		
		if (target != null && !target.level.isClientSide) {
			attacker.heal(1F);
			target.hurt(attacker instanceof PlayerEntity ? DamageSource.playerAttack((PlayerEntity) attacker) : DamageSource.mobAttack(attacker), 1F);
		}
		
		targetStack.hurtAndBreak(damageMultiplier, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}
}
