package io.github.chaosawakens.common.items;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class EnchantedCASpawnEggItem extends CASpawnEggItem {
	
	public EnchantedCASpawnEggItem(Supplier<? extends EntityType<?>> typeIn, Properties properties) {
		super(typeIn, properties);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}