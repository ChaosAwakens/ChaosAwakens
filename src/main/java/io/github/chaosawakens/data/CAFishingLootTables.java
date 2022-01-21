package io.github.chaosawakens.data;

import io.github.chaosawakens.common.registry.CAItems;


import io.github.chaosawakens.common.registry.CALootTables;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

public class CAFishingLootTables extends FishingLootTables {
	
    @Override
    public void accept(BiConsumer<ResourceLocation, Builder> p_accept_1_) {
        p_accept_1_.accept(LootTables.FISHING, LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(TableLootEntry.lootTableReference(LootTables.FISHING_FISH).setWeight(85).setQuality(-1))
                .add(TableLootEntry.lootTableReference(CALootTables.FISHING_FISH).setWeight(75).setQuality(-1))
                .add(TableLootEntry.lootTableReference(LootTables.FISHING_TREASURE).setWeight(10).setQuality(2))
                .add(TableLootEntry.lootTableReference(CALootTables.FISHING_TREASURE).setWeight(5).setQuality(2))
                .add(TableLootEntry.lootTableReference(LootTables.FISHING_JUNK).setWeight(15).setQuality(-2))
                .add(TableLootEntry.lootTableReference(CALootTables.FISHING_JUNK).setWeight(10).setQuality(-2))));
          //      .add(TableLootEntry.lootTableReference(CALootTables.FISHING_LAVA_FISH).setWeight(50).setQuality(2))
        	//	.add(TableLootEntry.lootTableReference(CALootTables.FISHING_NETHER_FISH).setWeight(50).setQuality(2))));
 
        p_accept_1_.accept(CALootTables.FISHING_FISH, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(ItemLootEntry.lootTableItem(CAItems.GREEN_FISH.get()))
                        .add(ItemLootEntry.lootTableItem(CAItems.ROCK_FISH.get()))
                        .add(ItemLootEntry.lootTableItem(CAItems.SPARK_FISH.get()))
                        .add(ItemLootEntry.lootTableItem(CAItems.WOOD_FISH.get()))));

        p_accept_1_.accept(CALootTables.FISHING_TREASURE, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(ItemLootEntry.lootTableItem(CAItems.AMETHYST.get()))
                        .add(ItemLootEntry.lootTableItem(Items.DIAMOND))
                        .add(ItemLootEntry.lootTableItem(Items.EMERALD))
                        .add(ItemLootEntry.lootTableItem(CAItems.RUBY.get()))
                        .add(ItemLootEntry.lootTableItem(CAItems.ALUMINUM_INGOT.get()))
                        .add(ItemLootEntry.lootTableItem(CAItems.GOLD_COIN.get()))
                        .add(ItemLootEntry.lootTableItem(CAItems.TIGERS_EYE.get()))));

        p_accept_1_.accept(CALootTables.FISHING_JUNK, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(ItemLootEntry.lootTableItem(CAItems.CANDYCANE.get()))
                        .add(ItemLootEntry.lootTableItem(CAItems.CHEESE.get()))));
        
        p_accept_1_.accept(CALootTables.FISHING_LAVA_FISH, LootTable.lootTable()
        		.withPool(LootPool.lootPool()
        				.add(ItemLootEntry.lootTableItem(CAItems.LAVA_EEL.get()))));
        
      /*  p_accept_1_.accept(CALootTables.FISHING_LAVA_TREASURE, LootTable.lootTable()
        		.withPool(LootPool.lootPool()
        				.add(ItemLootEntry.lootTableItem(CAItems.RUBY.get()))));*/
        
        p_accept_1_.accept(CALootTables.FISHING_NETHER_FISH, LootTable.lootTable()
        		.withPool(LootPool.lootPool()
        				.add(ItemLootEntry.lootTableItem(CAItems.RUBY.get()).setWeight(10))
        				.add(ItemLootEntry.lootTableItem(CAItems.LAVA_EEL_BOOTS.get()).setWeight(5))
        				.add(ItemLootEntry.lootTableItem(CAItems.URANIUM_NUGGET.get()).setWeight(1))
        				.add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT).setWeight(25))
        				.add(ItemLootEntry.lootTableItem(CAItems.GOLD_COIN.get()).setWeight(30))
        				.add(ItemLootEntry.lootTableItem(CAItems.LAVA_EEL.get()).setWeight(55))));
        
 /*       p_accept_1_.accept(CALootTables.FISHING_NETHER_TREASURE, LootTable.lootTable()
        		.withPool(LootPool.lootPool()
        				.add(ItemLootEntry.lootTableItem(CAItems.RUBY.get()).setWeight(10))
        				.add(ItemLootEntry.lootTableItem(CAItems.URANIUM_NUGGET.get()).setWeight(1))
        				.add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT).setWeight(25))));*/
    }
}