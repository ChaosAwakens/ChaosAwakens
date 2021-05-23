package io.github.chaosawakens.data;


import io.github.chaosawakens.common.loot.CATreasure;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CAChestLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {


    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> register) {
        register.accept(CATreasure.ent_dungeon.lootTable,
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(4))
                                //common loot
                                .addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(8, 14))).weight(70))
                                .addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(8, 14))).weight(70))
                                .addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(8, 14))).weight(70))
                                .addEntry(ItemLootEntry.builder(Items.OAK_PLANKS).acceptFunction(SetCount.builder(RandomValueRange.of(8, 14))).weight(70))
                                .addEntry(ItemLootEntry.builder(Items.OAK_LOG).acceptFunction(SetCount.builder(RandomValueRange.of(3, 5))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.OAK_LOG).acceptFunction(SetCount.builder(RandomValueRange.of(3, 5))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.OAK_LOG).acceptFunction(SetCount.builder(RandomValueRange.of(3, 5))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.OAK_LOG).acceptFunction(SetCount.builder(RandomValueRange.of(3, 5))).weight(100)))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(3))
                                //common loot
                                .addEntry(ItemLootEntry.builder(Items.RED_MUSHROOM).acceptFunction(SetCount.builder(RandomValueRange.of(3, 7))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.RED_MUSHROOM).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))).weight(50))
                                .addEntry(ItemLootEntry.builder(Items.BROWN_MUSHROOM).acceptFunction(SetCount.builder(RandomValueRange.of(3, 7))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.BROWN_MUSHROOM).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))).weight(50)))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(2))
                                //common loot
                                .addEntry(ItemLootEntry.builder(Items.VINE).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))).weight(25))
                                .addEntry(ItemLootEntry.builder(Items.VINE).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))).weight(25))
                                .addEntry(ItemLootEntry.builder(Items.OAK_SAPLING).acceptFunction(SetCount.builder(RandomValueRange.of(3, 7))).weight(50))
                                .addEntry(ItemLootEntry.builder(Items.OAK_SAPLING).acceptFunction(SetCount.builder(RandomValueRange.of(3, 7))).weight(50)))
                        .addLootPool(LootPool.builder()
                                .rolls(RandomValueRange.of(0, 1))
                                //rare loot
                                .addEntry(ItemLootEntry.builder(Items.DIAMOND).acceptFunction(SetCount.builder(RandomValueRange.of(0, 2))).acceptCondition(RandomChance.builder(0.65F)).weight(110))
                                .addEntry(ItemLootEntry.builder(Items.EMERALD).acceptFunction(SetCount.builder(RandomValueRange.of(0, 2))).acceptCondition(RandomChance.builder(0.65F)).weight(70))
                                .addEntry(ItemLootEntry.builder(CAItems.AMETHYST.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0, 2))).acceptCondition(RandomChance.builder(0.65F)).weight(50))
                                .addEntry(ItemLootEntry.builder(CAItems.TIGERS_EYE.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0, 2))).acceptCondition(RandomChance.builder(0.65F)).weight(50))
                                .addEntry(ItemLootEntry.builder(CAItems.RUBY.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0, 2))).acceptCondition(RandomChance.builder(0.65F)).weight(25))));
    }
}