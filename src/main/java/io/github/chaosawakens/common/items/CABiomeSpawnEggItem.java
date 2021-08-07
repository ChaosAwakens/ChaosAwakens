package io.github.chaosawakens.common.items;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.registry.CAEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.world.biome.Biome;

public class CABiomeSpawnEggItem extends CASpawnEggItem {
	private Supplier<? extends EntityType<?>> mutableGetter;
	
	public CABiomeSpawnEggItem(Supplier<? extends EntityType<?>> defaultType, Properties properties, boolean isEnchanted) {
		super(defaultType, properties, isEnchanted);
		this.mutableGetter = defaultType;
	}
	
	public CABiomeSpawnEggItem(Supplier<? extends EntityType<?>> defaultType, Properties properties) {
		super(defaultType, properties);
		this.mutableGetter = defaultType;
	}
	
	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		SpawnEggItem.BY_ID.put(CAEntityTypes.ACACIA_ENT.get(), this);
		SpawnEggItem.BY_ID.put(CAEntityTypes.BIRCH_ENT.get(), this);
		SpawnEggItem.BY_ID.put(CAEntityTypes.DARK_OAK_ENT.get(), this);
		SpawnEggItem.BY_ID.put(CAEntityTypes.JUNGLE_ENT.get(), this);
		SpawnEggItem.BY_ID.put(CAEntityTypes.OAK_ENT.get(), this);
		SpawnEggItem.BY_ID.put(CAEntityTypes.SPRUCE_ENT.get(), this);
		SpawnEggItem.BY_ID.put(CAEntityTypes.CRIMSON_ENT.get(), this);
		SpawnEggItem.BY_ID.put(CAEntityTypes.WARPED_ENT.get(), this);
		super.fillItemCategory(group, items);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		Supplier<? extends EntityType<?>> type = this.getEntType(context);
		this.mutableGetter = type == null ? this.typeGetter : type;
		
		return super.useOn(context);
	}
	
	private Supplier<? extends EntityType<?>> getEntType(ItemUseContext context) {
		Biome targetBiome = context.getLevel().getBiome(context.getClickedPos());
		
		if(targetBiome.getRegistryName().toString().contains("birch"))return CAEntityTypes.BIRCH_ENT;
		if(targetBiome.getRegistryName().toString().contains("dark"))return CAEntityTypes.DARK_OAK_ENT;
		if(targetBiome.getRegistryName().toString().contains("crimson"))return CAEntityTypes.CRIMSON_ENT;
		if(targetBiome.getRegistryName().toString().contains("warped"))return CAEntityTypes.WARPED_ENT;
		
		switch(targetBiome.getBiomeCategory()) {
			case JUNGLE:
				return CAEntityTypes.JUNGLE_ENT;
			case SAVANNA:
				return CAEntityTypes.ACACIA_ENT;
			case TAIGA:
				return CAEntityTypes.SPRUCE_ENT;
			default:
				return null;
		}
	}
	
	public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
		return this.mutableGetter.get();
	}
}
