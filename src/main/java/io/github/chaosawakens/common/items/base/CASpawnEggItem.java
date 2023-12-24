package io.github.chaosawakens.common.items.base;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeSpawnEggItem;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CASpawnEggItem extends ForgeSpawnEggItem {
	protected final Supplier<? extends EntityType<?>> typeGetter;
	private final boolean isEnchanted;

	public CASpawnEggItem(Supplier<EntityType<?>> typeIn, Properties properties, boolean isEnchanted) {
		super(typeIn, 0xFFFFFFF, 0xFFFFFFF, properties);
		this.typeGetter = typeIn;
		this.isEnchanted = isEnchanted;
	}

	public CASpawnEggItem(Supplier<EntityType<?>> typeIn, Properties properties) {
		this(typeIn, properties, false);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		SpawnEggItem.BY_ID.put(getType(null), this);
		super.fillItemCategory(group, items);
	}

	@Override
	public EntityType<?> getType(@Nullable CompoundNBT nbt) {
		return typeGetter.get();
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return this.isEnchanted || super.isFoil(stack);
	}
}