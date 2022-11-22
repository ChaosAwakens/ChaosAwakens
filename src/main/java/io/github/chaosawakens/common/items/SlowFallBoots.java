package io.github.chaosawakens.common.items;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class SlowFallBoots extends EnchantedArmorItem {
	public SlowFallBoots(IArmorMaterial materialIn, Properties builderIn, EnchantmentData[] enchantments) {
		super(materialIn, EquipmentSlotType.FEET, builderIn, enchantments);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (!player.isOnGround() && player.isCrouching()) player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 200, 0, true, false));
	}
}
