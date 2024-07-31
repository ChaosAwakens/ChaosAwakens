package io.github.chaosawakens.common.items.base;

import io.github.chaosawakens.api.item.IAutoEnchantable;
import io.github.chaosawakens.common.util.EnumUtil.CAItemTier;
import io.github.chaosawakens.manager.CAConfigManager;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

import java.util.function.Supplier;

public class EnchantedAxeItem extends CAAxeItem implements IAutoEnchantable {
	private final Supplier<EnchantmentData[]> enchantments;
	
	public EnchantedAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, double reach, double attackKnockback, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pAttackSpeedModifier, reach, attackKnockback, pProperties);
		this.enchantments = enchantments;
	}
	
	public EnchantedAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, double reach, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pAttackSpeedModifier, reach, pProperties);
		this.enchantments = enchantments;
	}
	
	public EnchantedAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, Double attackKnockback, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pAttackSpeedModifier, attackKnockback, pProperties);
		this.enchantments = enchantments;
	}
	
	public EnchantedAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, double reach, double attackKnockback, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, reach, attackKnockback, pProperties);
		this.enchantments = enchantments;
	}
	
	public EnchantedAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, float pAttackSpeedModifier, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pAttackSpeedModifier, pProperties);
		this.enchantments = enchantments;
	}
	
	public EnchantedAxeItem(CAItemTier pTier, Supplier<IntValue> configDmg, Properties pProperties, Supplier<EnchantmentData[]> enchantments) {
		super(pTier, configDmg, pProperties);
		this.enchantments = enchantments;
	}
	
	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (allowdedIn(group)) {
			ItemStack axeStack = new ItemStack(this);
			
			if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) {
				for (EnchantmentData curEnch : enchantments.get()) {
					axeStack.enchant(curEnch.enchantment, curEnch.level);
				}
			}
			items.add(axeStack);
		}
	}

	@Override
	public void onCraftedBy(ItemStack itemStack, World world, PlayerEntity playerEntity) {
		if (CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get()) {
			for (EnchantmentData curEnch : enchantments.get()) {
				itemStack.enchant(curEnch.enchantment, curEnch.level);
			}
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CAConfigManager.MAIN_COMMON.enableAutoEnchanting.get() && super.isFoil(stack) || super.isFoil(stack);
	}

	@Override
	public EnchantmentData[] getEnchantments() {
		return this.enchantments.get();
	}
}
