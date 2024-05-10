package io.github.chaosawakens.common.enchantments;

import io.github.chaosawakens.common.util.EnchantmentUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.stream.Collectors;

public class HoplologyEnchantment extends ProtectionEnchantment {
	private static final int MAX_PROTECTION_LEVEL = 50;
	private int protection;
	
	public HoplologyEnchantment(EquipmentSlotType... slotType) {
		super(Rarity.VERY_RARE, ProtectionEnchantment.Type.ALL, slotType);
	}
	
	@Override
	public int getDamageProtection(int pLevel, DamageSource pSource) {
		if (pSource.isBypassArmor() || pSource.isBypassInvul()) return 0;
		else return protection;
	}
	
	@Override
	public boolean checkCompatibility(Enchantment pEnch) {
		return !(pEnch instanceof ProtectionEnchantment);
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	public int getProtection() {
		return protection;
	}
	
	public void incrementProtection(int protection) {
		this.protection += protection;
	}
	
	public void decrementProtection(int protection) {
		this.protection -= protection;
	}
	
	public void setProtection(int protection) {
		this.protection = protection;
	}
	
	public static int getMaxHoplologyProtectionLevel() {
		return MAX_PROTECTION_LEVEL;
	}
	
	public static void handleHoplologyProtection(LivingEntity owner, HoplologyEnchantment hoplologyEnchantment) {
		ObjectArrayList<ItemStack> curStacks = EnchantmentUtil.getItemStacksWithEnchantment(hoplologyEnchantment.getSlotItems(owner).values(), hoplologyEnchantment);
		
		if (curStacks == null) return;
		
		ObjectArrayList<ItemStack> targetStacks = hoplologyEnchantment.getSlotItems(owner).values().stream()
				.filter((targetStack) -> !targetStack.isEmpty() && curStacks.contains(targetStack))
				.collect(Collectors.toCollection(ObjectArrayList::new));
		
		if (!targetStacks.isEmpty()) {
			targetStacks.forEach((targetStack) -> {				
				if (!(owner instanceof PlayerEntity)) return;
				
				PlayerEntity playerOwner = (PlayerEntity) owner;
				int playerXPLevel = playerOwner.experienceLevel;
				
				if (playerXPLevel > MAX_PROTECTION_LEVEL) playerXPLevel = MAX_PROTECTION_LEVEL;
				
				hoplologyEnchantment.setProtection(Math.abs(playerXPLevel / 10));
			});
		}
	}
}
