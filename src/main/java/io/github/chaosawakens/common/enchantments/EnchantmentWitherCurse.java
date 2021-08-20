package io.github.chaosawakens.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

/**
 * @author Meme Man
 */

public class EnchantmentWitherCurse extends Enchantment{
	
	public final int curseType;

	public EnchantmentWitherCurse(Rarity rarity, int curseType, EnchantmentType type, EquipmentSlotType...slotType) {
		super(rarity, type, slotType);
		this.curseType = 1;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}
	
	@Override
	public boolean isCurse() {
		return true;
	}
	
	@Override
	public boolean isDiscoverable() {
		return true;
	}
	
	@Override
	public boolean isTreasureOnly() {
		return true;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}
	
	@Override
	public void doPostHurt(LivingEntity livingentity, Entity entity, int d) {
		if(entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entity;
			if(this.curseType == 1) {
				 int p = 20 + player.getRandom().nextInt(60 * d);
		    		player.addEffect(new EffectInstance(Effects.WITHER, p, 10));
			}
		 }
	  }
 }


