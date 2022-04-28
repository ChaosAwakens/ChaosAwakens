package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.IAutoEnchantable;
import io.github.chaosawakens.common.config.CAConfig;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;

public class LavaEelArmorItem extends EnchantedArmorItem implements IAutoEnchantable {
	private final EnchantmentData[] enchantments;

	public LavaEelArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn, EnchantmentData[] enchantments) {
		super(materialIn, slot, builderIn, enchantments);
		this.enchantments = enchantments;
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (player.level.dimension().location() == Dimension.NETHER.location() || player.isInLava() || player.isOnFire()) {
			player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0, true, false));
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return CAConfig.COMMON.enableAutoEnchanting.get() || super.isFoil(stack);
	}

	@Override
	public EnchantmentData[] enchantments() {
		return this.enchantments;
	}
}
