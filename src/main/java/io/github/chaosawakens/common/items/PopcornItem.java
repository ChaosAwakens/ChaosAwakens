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

	@Override
	public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity user) {
		ItemStack resultUseItem = super.finishUsingItem(stack, world, user);
		return user instanceof PlayerEntity && ((PlayerEntity)user).abilities.instabuild ? resultUseItem : new ItemStack(CAItems.EMPTY_POPCORN_BAG.get());
	}
}
