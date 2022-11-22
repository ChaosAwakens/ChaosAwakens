package io.github.chaosawakens.common.items;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CASpawnEggItem extends SpawnEggItem {
	protected final Supplier<? extends EntityType<?>> typeGetter;
	private final boolean isEnchanted;

	@SuppressWarnings("deprecation")
	public CASpawnEggItem(Supplier<? extends EntityType<?>> typeIn, Properties properties, boolean isEnchanted) {
		super(null, 0xFFFFFFF, 0xFFFFFFF, properties);
		this.typeGetter = typeIn;
		this.isEnchanted = isEnchanted;
	}

	public CASpawnEggItem(Supplier<? extends EntityType<?>> typeIn, Properties properties) {
		this(typeIn, properties, false);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		SpawnEggItem.BY_ID.put(this.getType(null), this);
		super.fillItemCategory(group, items);
	}

	@Override
	public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
		return typeGetter.get();
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return this.isEnchanted || super.isFoil(stack);
	}
}