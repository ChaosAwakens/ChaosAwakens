package io.github.chaosawakens.common.items;

import io.github.chaosawakens.api.EnchantmentAndLevel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class RoyaltyBootsItem extends EnchantedArmorItem {

	public RoyaltyBootsItem(IArmorMaterial materialIn, Properties builderIn, EnchantmentAndLevel[] enchantments) {
		super(materialIn, EquipmentSlotType.FEET, builderIn, enchantments);
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (!player.isOnGround())
			player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 60, 0, false, false));
	}
}