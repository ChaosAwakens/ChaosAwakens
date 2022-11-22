package io.github.chaosawakens.data;

import com.google.common.collect.ImmutableList;

import com.mojang.datafixers.util.Pair;
import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CALootTableProvider extends LootTableProvider {
	public CALootTableProvider(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	public String getName() {
		return ChaosAwakens.MODNAME + ": Loot Tables";
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables() {
		return ImmutableList.of(Pair.of(CABlockLootTables::new, LootParameterSets.BLOCK),
				Pair.of(CAEntityLootTables::new, LootParameterSets.ENTITY),
				Pair.of(CAChestLootTables::new, LootParameterSets.CHEST),
				Pair.of(CAFishingLootTables::new, LootParameterSets.FISHING)
				/* Pair.of(CAGiftLootTable::new, LootParameterSets.GIFT) */);
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
	}
}
