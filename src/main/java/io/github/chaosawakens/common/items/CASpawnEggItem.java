package io.github.chaosawakens.common.items;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;

public class CASpawnEggItem extends SpawnEggItem {
	private final Supplier<? extends EntityType<?>> typeGetter;
	private boolean isEnchanted;
	
	public CASpawnEggItem(Supplier<? extends EntityType<?>> typeIn, Properties properties, boolean isEnchanted) {
		super(null, 0, 0, properties);
		this.typeGetter = typeIn;
		this.isEnchanted = isEnchanted;
	}
	
	public CASpawnEggItem(Supplier<? extends EntityType<?>> typeIn, Properties properties) {
		this(typeIn, properties, false);
	}
	
	@Override
	public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
		return typeGetter.get();
	}
	
	public boolean hasEffect() {
		return this.isEnchanted;
	}
}