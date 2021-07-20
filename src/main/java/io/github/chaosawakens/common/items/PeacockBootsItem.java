package io.github.chaosawakens.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class PeacockBootsItem extends ArmorItem {
	
	public PeacockBootsItem(IArmorMaterial materialIn, Properties builderIn) {
		super(materialIn, EquipmentSlotType.FEET, builderIn);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		super.onArmorTick(stack, world, player);
		if (!player.isOnGround())
			player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 60, 0, false, false));
	}
}