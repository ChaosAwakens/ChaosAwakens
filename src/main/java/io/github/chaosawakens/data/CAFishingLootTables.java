package io.github.chaosawakens.data;

import io.github.chaosawakens.ChaosAwakens;

import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CALootTables;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

public class CAFishingLootTables extends FishingLootTables {
	public String getName() {
		return ChaosAwakens.MODNAME + ": Fishing Loot Tables";
	}

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> biConsumer) {
		biConsumer.accept(CALootTables.FISHING_LAVA_FISH,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.add(ItemLootEntry.lootTableItem(CAItems.LAVA_EEL.get()))));

		biConsumer.accept(CALootTables.FISHING_NETHER_FISH,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.add(ItemLootEntry.lootTableItem(CAItems.LAVA_EEL.get()).setWeight(55))));

		biConsumer.accept(CALootTables.FISHING_NETHER_TREASURE,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.add(ItemLootEntry.lootTableItem(CAItems.RUBY.get()).setWeight(10))
								.add(ItemLootEntry.lootTableItem(CAItems.URANIUM_NUGGET.get()).setWeight(1))));

		biConsumer.accept(CALootTables.FISHING_NETHER_JUNK,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.add(ItemLootEntry.lootTableItem(CAItems.LAVA_EEL_BOOTS.get()).setWeight(5))
								.add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT).setWeight(25))));

	}
}
