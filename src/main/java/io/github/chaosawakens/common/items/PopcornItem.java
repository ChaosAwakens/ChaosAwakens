package io.github.chaosawakens.common.items;

import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PopcornItem extends Item {
	public PopcornItem(Properties properties) {
		super(properties);
	}

	public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity livingEntity) {
		ItemStack itemstack = super.finishUsingItem(stack, world, livingEntity);
		return livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.instabuild ? itemstack : new ItemStack(CAItems.EMPTY_POPCORN_BAG.get());
	}
}
