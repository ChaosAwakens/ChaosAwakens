package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.IAutoEnchantable;
import io.github.chaosawakens.common.config.CACommonConfig;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class EnchantedArmorItem extends ArmorItem implements IAutoEnchantable {
	private final EnchantmentData[] enchantments;

	public EnchantedArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, EnchantmentData[] enchantments) {
		super(materialIn, slot, builderIn);
		this.enchantments = enchantments;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (allowdedIn(group)) {
			ItemStack stack = new ItemStack(this);
			if (CACommonConfig.COMMON.enableAutoEnchanting.get()) {
				for (EnchantmentData enchant : enchantments) {
					stack.enchant(enchant.enchantment, enchant.level);
				}
			}
			items.add(stack);
		}
	}

	@Override
	public void onCraftedBy(ItemStack itemStack, World world, PlayerEntity playerEntity) {
		if (CACommonConfig.COMMON.enableAutoEnchanting.get()) {
			for (EnchantmentData enchant : enchantments) {
				itemStack.enchant(enchant.enchantment, enchant.level);
			}
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CACommonConfig.COMMON.enableAutoEnchanting.get() && super.isFoil(stack) || super.isFoil(stack);
	}

	@Override
	public EnchantmentData[] enchantments() {
		return this.enchantments;
	}
}
