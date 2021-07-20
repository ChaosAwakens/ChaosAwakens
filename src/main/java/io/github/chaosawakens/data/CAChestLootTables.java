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
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantRange.exactly(4))
                                //common loot
                                .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS).apply(SetCount.setCount(RandomValueRange.between(8, 14))).setWeight(70))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS).apply(SetCount.setCount(RandomValueRange.between(8, 14))).setWeight(70))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS).apply(SetCount.setCount(RandomValueRange.between(8, 14))).setWeight(70))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS).apply(SetCount.setCount(RandomValueRange.between(8, 14))).setWeight(70))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_LOG).apply(SetCount.setCount(RandomValueRange.between(3, 5))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_LOG).apply(SetCount.setCount(RandomValueRange.between(3, 5))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_LOG).apply(SetCount.setCount(RandomValueRange.between(3, 5))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_LOG).apply(SetCount.setCount(RandomValueRange.between(3, 5))).setWeight(100)))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantRange.exactly(3))
                                //common loot
                                .add(ItemLootEntry.lootTableItem(Items.RED_MUSHROOM).apply(SetCount.setCount(RandomValueRange.between(3, 7))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.RED_MUSHROOM).apply(SetCount.setCount(RandomValueRange.between(1, 5))).setWeight(50))
                                .add(ItemLootEntry.lootTableItem(Items.BROWN_MUSHROOM).apply(SetCount.setCount(RandomValueRange.between(3, 7))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.BROWN_MUSHROOM).apply(SetCount.setCount(RandomValueRange.between(1, 5))).setWeight(50)))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantRange.exactly(2))
                                //common loot
                                .add(ItemLootEntry.lootTableItem(Items.VINE).apply(SetCount.setCount(RandomValueRange.between(1, 5))).setWeight(25))
                                .add(ItemLootEntry.lootTableItem(Items.VINE).apply(SetCount.setCount(RandomValueRange.between(1, 5))).setWeight(25))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_SAPLING).apply(SetCount.setCount(RandomValueRange.between(3, 7))).setWeight(50))
                                .add(ItemLootEntry.lootTableItem(Items.OAK_SAPLING).apply(SetCount.setCount(RandomValueRange.between(3, 7))).setWeight(50)))
                        .withPool(LootPool.lootPool()
                                .setRolls(RandomValueRange.between(0, 1))
                                //rare loot
                                .add(ItemLootEntry.lootTableItem(Items.DIAMOND).apply(SetCount.setCount(RandomValueRange.between(0, 2))).when(RandomChance.randomChance(0.65F)).setWeight(110))
                                .add(ItemLootEntry.lootTableItem(Items.EMERALD).apply(SetCount.setCount(RandomValueRange.between(0, 2))).when(RandomChance.randomChance(0.65F)).setWeight(70))
                                .add(ItemLootEntry.lootTableItem(CAItems.AMETHYST.get()).apply(SetCount.setCount(RandomValueRange.between(0, 2))).when(RandomChance.randomChance(0.65F)).setWeight(50))
                                .add(ItemLootEntry.lootTableItem(CAItems.TIGERS_EYE.get()).apply(SetCount.setCount(RandomValueRange.between(0, 2))).when(RandomChance.randomChance(0.65F)).setWeight(50))
                                .add(ItemLootEntry.lootTableItem(CAItems.RUBY.get()).apply(SetCount.setCount(RandomValueRange.between(0, 2))).when(RandomChance.randomChance(0.65F)).setWeight(25))));
        register.accept(CATreasure.wasp_dungeon.lootTable,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantRange.exactly(3))
                                //common loot
                                .add(ItemLootEntry.lootTableItem(Items.HONEYCOMB).apply(SetCount.setCount(RandomValueRange.between(8, 14))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.HONEYCOMB).apply(SetCount.setCount(RandomValueRange.between(8, 14))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.HONEY_BOTTLE).apply(SetCount.setCount(RandomValueRange.between(3, 5))).setWeight(30))
                                .add(ItemLootEntry.lootTableItem(Items.GLASS_BOTTLE).apply(SetCount.setCount(RandomValueRange.between(3, 5))).setWeight(40))
                                .add(ItemLootEntry.lootTableItem(Items.GLASS_BOTTLE).apply(SetCount.setCount(RandomValueRange.between(3, 5))).setWeight(40)))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantRange.exactly(2))
                                //common loot
                                .add(ItemLootEntry.lootTableItem(Items.HONEYCOMB_BLOCK).apply(SetCount.setCount(RandomValueRange.between(2, 6))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.HONEYCOMB_BLOCK).apply(SetCount.setCount(RandomValueRange.between(1, 5))).setWeight(80))
                                .add(ItemLootEntry.lootTableItem(Items.HONEY_BLOCK).apply(SetCount.setCount(RandomValueRange.between(1, 4))).setWeight(20)))
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantRange.exactly(2))
                                //common loot
                                .add(ItemLootEntry.lootTableItem(Items.GOLD_NUGGET).apply(SetCount.setCount(RandomValueRange.between(7,16))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.GOLD_NUGGET).apply(SetCount.setCount(RandomValueRange.between(7,16))).setWeight(100))
                                .add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT).apply(SetCount.setCount(RandomValueRange.between(1, 3))).setWeight(20)))
                        .withPool(LootPool.lootPool()
                                .setRolls(RandomValueRange.between(0, 1))
                                //rare loot
                                .add(ItemLootEntry.lootTableItem(Items.DIAMOND).apply(SetCount.setCount(RandomValueRange.between(0, 1))).when(RandomChance.randomChance(0.35F)).setWeight(110))
                                .add(ItemLootEntry.lootTableItem(Items.EMERALD).apply(SetCount.setCount(RandomValueRange.between(0, 1))).when(RandomChance.randomChance(0.35F)).setWeight(70))
                                .add(ItemLootEntry.lootTableItem(CAItems.AMETHYST.get()).apply(SetCount.setCount(RandomValueRange.between(0, 1))).when(RandomChance.randomChance(0.35F)).setWeight(50))
                                .add(ItemLootEntry.lootTableItem(CAItems.TIGERS_EYE.get()).apply(SetCount.setCount(RandomValueRange.between(0, 1))).when(RandomChance.randomChance(0.35F)).setWeight(50))));
    }
}