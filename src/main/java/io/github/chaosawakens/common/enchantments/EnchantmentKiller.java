package io.github.chaosawakens.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

//coded by ya boi meme man
public class EnchantmentKiller extends Enchantment {

	public final int type;

	public EnchantmentKiller(Rarity rarity, int dmgtype, EnchantmentType type, EquipmentSlotType[] slotType) {
		super(rarity, EnchantmentType.WEAPON, slotType);
		this.type = dmgtype;
	}

	@Override
	public int getMinCost(int cost) {
		return 50;
	}
	
	@Override
	public int getMaxCost(int cost) {
		return super.getMinCost(cost) + 10;
	}
	
     @Override
    public boolean isTradeable() {
    	return true;
    }
	
     @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
    	return true;
    }
	
     @Override
    public int getMinLevel() {
    	return 1;
    }
     
	@Override
	public int getMaxLevel() {
		return 3;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return true;
	}
	
	@Override
	protected boolean checkCompatibility(Enchantment ench) {
		return super.checkCompatibility(ench) && ench != Enchantments.SHARPNESS && ench != Enchantments.BANE_OF_ARTHROPODS && ench != Enchantments.SMITE;
	}
	
	@Override
	public float getDamageBonus(int damage, CreatureAttribute attribute) {
	      if (this.type == 5) {
	    	  return 1.0F + (float)Math.max(0, damage - 1) * 4.5F;
	       } else if (this.type == 5 && attribute == CreatureAttribute.UNDEAD) {
	          return (float)damage * 4.5F;
	       } else {
	          return this.type == 5 && attribute == CreatureAttribute.ARTHROPOD ? (float)damage * 5.5F : 0.0F;
	       }
	}

	@Override
	public void doPostAttack(LivingEntity liveentity, Entity entity, int duration) {
		    if (entity instanceof LivingEntity) {
		       LivingEntity livingentity = (LivingEntity)entity;
		       if (this.type == 5) {
		          int d = 20 + liveentity.getRandom().nextInt(40 * duration);
		          livingentity.addEffect(new EffectInstance(Effects.WITHER, d, 3));
		          livingentity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, d, 4));
		          livingentity.addEffect(new EffectInstance(Effects.WEAKNESS, d, 2));
			       }
		          }
		        }
	       
public float getDuration(int duration) {
	if (this.type == 5) {
		return 1.0F + (float)Math.max(0, duration - 1) + 1.0F;
	}
	return duration;
   }
}

