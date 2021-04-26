package io.github.chaosawakens.data;

import io.github.chaosawakens.registry.ModEntityTypes;
import io.github.chaosawakens.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.KilledByPlayer;
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
        registerLootTable(ModEntityTypes.ENT.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.OAK_LOG)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(18.0F, 24.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 3.0F)))))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(3))
                                .addEntry(ItemLootEntry.builder(Items.BROWN_MUSHROOM)
                                        .acceptCondition(RandomChance.builder(0.5F))))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(3))
                                .addEntry(ItemLootEntry.builder(Items.RED_MUSHROOM)
                                        .acceptCondition(RandomChance.builder(0.5F))))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(2))
                                .addEntry(ItemLootEntry.builder(Items.DIAMOND)
                                        .acceptCondition(RandomChance.builder(0.85F)))));
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