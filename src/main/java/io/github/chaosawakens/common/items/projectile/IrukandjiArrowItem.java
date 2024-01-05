package io.github.chaosawakens.common.items.projectile;

import io.github.chaosawakens.common.entity.projectile.arrow.IrukandjiArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IrukandjiArrowItem extends ArrowItem {
	
	public IrukandjiArrowItem(Properties builder) {
		super(builder);
	}

	@Override
	public IrukandjiArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		IrukandjiArrowEntity irukandjiArrow = new IrukandjiArrowEntity(worldIn, shooter);
		return irukandjiArrow;
	}
}
