package io.github.chaosawakens.data;

import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

import io.github.chaosawakens.common.registry.CAItems;
import io.github.chaosawakens.common.registry.CALootTables;

public class CAFishingLootTables extends FishingLootTables {

    @Override
    public void accept(BiConsumer<ResourceLocation, Builder> p_accept_1_) {
        p_accept_1_.accept(LootTables.GAMEPLAY_FISHING, LootTable.builder().addLootPool(LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_FISH).weight(85).quality(-1))
                .addEntry(TableLootEntry.builder(CALootTables.CHAOS_AWAKENS_FISHING_FISH).weight(75).quality(-1))
                .addEntry(TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_TREASURE).weight(10).quality(2))
                .addEntry(TableLootEntry.builder(CALootTables.CHAOS_AWAKENS_FISHING_TREASURE).weight(5).quality(2))
                .addEntry(TableLootEntry.builder(LootTables.GAMEPLAY_FISHING_JUNK).weight(15).quality(-2))
                .addEntry(TableLootEntry.builder(CALootTables.CHAOS_AWAKENS_FISHING_JUNK).weight(10).quality(-2))));

        p_accept_1_.accept(CALootTables.CHAOS_AWAKENS_FISHING_FISH, LootTable.builder()
                .addLootPool(LootPool.builder()
                        .addEntry(ItemLootEntry.builder(CAItems.GREEN_FISH.get()))
                        .addEntry(ItemLootEntry.builder(CAItems.ROCK_FISH.get()))
                        .addEntry(ItemLootEntry.builder(CAItems.SPARK_FISH.get()))
                        .addEntry(ItemLootEntry.builder(CAItems.WOOD_FISH.get()))));

        p_accept_1_.accept(CALootTables.CHAOS_AWAKENS_FISHING_TREASURE, LootTable.builder()
                .addLootPool(LootPool.builder()
                        .addEntry(ItemLootEntry.builder(CAItems.AMETHYST.get()))
                        .addEntry(ItemLootEntry.builder(Items.DIAMOND))
                        .addEntry(ItemLootEntry.builder(Items.EMERALD))
                        .addEntry(ItemLootEntry.builder(CAItems.RUBY.get()))
                        .addEntry(ItemLootEntry.builder(CAItems.TIGERS_EYE.get()))));

        p_accept_1_.accept(CALootTables.CHAOS_AWAKENS_FISHING_JUNK, LootTable.builder()
                .addLootPool(LootPool.builder()
                        .addEntry(ItemLootEntry.builder(CAItems.CANDYCANE.get()))
                        .addEntry(ItemLootEntry.builder(CAItems.CHEESE.get()))));


    }
}