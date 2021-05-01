package io.github.chaosawakens.data;

import io.github.chaosawakens.registry.ModEntityTypes;
import io.github.chaosawakens.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;

import java.util.HashSet;
import java.util.Set;

public class ModEntityLootTables extends net.minecraft.data.loot.EntityLootTables {

    private final Set<EntityType<?>> knownEntities = new HashSet<>();

    @Override
    public void registerLootTable(EntityType<?> entity, LootTable.Builder builder) {
        super.registerLootTable(entity, builder);
        knownEntities.add(entity);
    }

    @Override
    protected void addTables() {
        registerLootTable(ModEntityTypes.RUBY_BUG.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(ModItems.RUBY.get())
                                        .acceptCondition(RandomChance.builder(0.1F)))));
        registerLootTable(ModEntityTypes.APPLE_COW.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.BEEF)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F))))));
        registerLootTable(ModEntityTypes.GOLDEN_APPLE_COW.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.BEEF)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.GOLDEN_APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))))));
    }

    public LootTable.Builder emptyLootTable() {
        return LootTable.builder();
    }

    public LootTable.Builder fromEntityLootTable(EntityType<?> parent) {
        return LootTable.builder()
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(TableLootEntry.builder(parent.getLootTable())));
    }

    @Override
    public Set<EntityType<?>> getKnownEntities() {
        return knownEntities;
    }
}