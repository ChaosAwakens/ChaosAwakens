package io.github.chaosawakens.data;

import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CALootTables;
import net.minecraft.data.loot.GiftLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

public class CAGiftLootTable  extends GiftLootTables {
	@Override
	public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
		consumer.accept(CALootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_DIMENSIONAL_TRADER_GIFT,
				LootTable.builder()
						.addLootPool(LootPool.builder()
								.rolls(ConstantRange.of(1))
								.addEntry(ItemLootEntry.builder(Items.EMERALD))
								.addEntry(ItemLootEntry.builder(CAItems.TIN_LUMP.get()))
								.addEntry(ItemLootEntry.builder(CAItems.COPPER_LUMP.get()))
								.addEntry(ItemLootEntry.builder(CAItems.PINK_TOURMALINE_INGOT.get()))
								.addEntry(ItemLootEntry.builder(CAItems.CATS_EYE_INGOT.get()))
								.addEntry(ItemLootEntry.builder(CAItems.PLATINUM_LUMP.get()))));
	}
}