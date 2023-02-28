package io.github.chaosawakens.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

/**
 * @author invalid2
 */
public class HoplologyEnchantment extends ProtectionEnchantment {

	private int protection;
	
	public HoplologyEnchantment(EquipmentSlotType... slotType) {
		super(Rarity.VERY_RARE, ProtectionEnchantment.Type.ALL, slotType);
	}
	
	@Override
	public int getDamageProtection(int pLevel, DamageSource pSource) {
		if(pSource.isBypassArmor() || pSource.isBypassInvul())
			return 0;
		else
			return protection;
	}
	
	@Override
	public boolean checkCompatibility(Enchantment pEnch) {
		return !(pEnch instanceof ProtectionEnchantment);
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	public void changeProtection(int protection) {
		this.protection += protection;
	}
	
	public void setProtection(int protection) {
		this.protection = protection;
	}
}
