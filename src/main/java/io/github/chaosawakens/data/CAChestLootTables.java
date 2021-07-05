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
        register.accept(CATreasure.wasp_dungeon.lootTable,
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(3))
                                //common loot
                                .addEntry(ItemLootEntry.builder(Items.HONEYCOMB).acceptFunction(SetCount.builder(RandomValueRange.of(8, 14))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.HONEYCOMB).acceptFunction(SetCount.builder(RandomValueRange.of(8, 14))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.HONEY_BOTTLE).acceptFunction(SetCount.builder(RandomValueRange.of(3, 5))).weight(30))
                                .addEntry(ItemLootEntry.builder(Items.GLASS_BOTTLE).acceptFunction(SetCount.builder(RandomValueRange.of(3, 5))).weight(40))
                                .addEntry(ItemLootEntry.builder(Items.GLASS_BOTTLE).acceptFunction(SetCount.builder(RandomValueRange.of(3, 5))).weight(40)))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(2))
                                //common loot
                                .addEntry(ItemLootEntry.builder(Items.HONEYCOMB_BLOCK).acceptFunction(SetCount.builder(RandomValueRange.of(2, 6))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.HONEYCOMB_BLOCK).acceptFunction(SetCount.builder(RandomValueRange.of(1, 5))).weight(80))
                                .addEntry(ItemLootEntry.builder(Items.HONEY_BLOCK).acceptFunction(SetCount.builder(RandomValueRange.of(1, 4))).weight(20)))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(2))
                                //common loot
                                .addEntry(ItemLootEntry.builder(Items.GOLD_NUGGET).acceptFunction(SetCount.builder(RandomValueRange.of(7,16))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.GOLD_NUGGET).acceptFunction(SetCount.builder(RandomValueRange.of(7,16))).weight(100))
                                .addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).acceptFunction(SetCount.builder(RandomValueRange.of(1, 3))).weight(20)))
                        .addLootPool(LootPool.builder()
                                .rolls(RandomValueRange.of(0, 1))
                                //rare loot
                                .addEntry(ItemLootEntry.builder(Items.DIAMOND).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))).acceptCondition(RandomChance.builder(0.35F)).weight(110))
                                .addEntry(ItemLootEntry.builder(Items.EMERALD).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))).acceptCondition(RandomChance.builder(0.35F)).weight(70))
                                .addEntry(ItemLootEntry.builder(CAItems.AMETHYST.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))).acceptCondition(RandomChance.builder(0.35F)).weight(50))
                                .addEntry(ItemLootEntry.builder(CAItems.TIGERS_EYE.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0, 1))).acceptCondition(RandomChance.builder(0.35F)).weight(50))));
    }
}