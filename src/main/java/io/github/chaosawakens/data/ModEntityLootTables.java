package io.github.chaosawakens.data;

import io.github.chaosawakens.registry.CAEntityTypes;
import io.github.chaosawakens.registry.CAItems;
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
        registerLootTable(CAEntityTypes.ENT.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.OAK_LOG)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(18.0F, 24.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 3.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.RED_MUSHROOM)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))
                                .addEntry(ItemLootEntry.builder(Items.BROWN_MUSHROOM)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(RandomValueRange.of(1, 2))
                                .addEntry(ItemLootEntry.builder(Items.DIAMOND)
                                        .acceptCondition(RandomChance.builder(0.85F))
                                        .acceptCondition(KilledByPlayer.builder()))));
        registerLootTable(CAEntityTypes.HERCULES_BEETLE.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(CAItems.BIG_HAMMER.get())
                                        .acceptCondition(KilledByPlayer.builder()))));
        registerLootTable(CAEntityTypes.RUBY_BUG.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(CAItems.RUBY.get())
                                        .acceptCondition(RandomChance.builder(0.3F))
                                        .acceptCondition(KilledByPlayer.builder()))));
        registerLootTable(CAEntityTypes.EMERALD_GATOR.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.EMERALD)
                                        .acceptCondition(RandomChance.builder(0.3F))
                                        .acceptCondition(KilledByPlayer.builder()))));
        registerLootTable(CAEntityTypes.APPLE_COW.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.BEEF)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))));
        registerLootTable(CAEntityTypes.GOLDEN_APPLE_COW.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.BEEF)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.GOLDEN_APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))));
        registerLootTable(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.BEEF)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.GOLDEN_APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.ENCHANTED_GOLDEN_APPLE))
                                .acceptCondition(KilledByPlayer.builder())));
        registerLootTable(CAEntityTypes.CRYSTAL_APPLE_COW.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.BEEF)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.APPLE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(1.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(CAItems.CRYSTAL_APPLE.get())
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))));
        registerLootTable(CAEntityTypes.BEAVER.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.PORKCHOP)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))));
        registerLootTable(CAEntityTypes.ROBO_SNIPER.get(),
                LootTable.builder()
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.REDSTONE_BLOCK)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 4.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))
                                .addEntry(ItemLootEntry.builder(Items.REDSTONE)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(20.0F, 32.0F)))
                                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.REPEATER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))
                                .addEntry(ItemLootEntry.builder(Items.COMPARATOR)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.PISTON)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))
                                .addEntry(ItemLootEntry.builder(Items.STICKY_PISTON)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.DROPPER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))
                                .addEntry(ItemLootEntry.builder(Items.DISPENSER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.HOPPER)
                                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                        .acceptCondition(KilledByPlayer.builder()))
                                .addEntry(ItemLootEntry.builder(Items.OBSERVER)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder())))
                        .addLootPool(LootPool.builder()
                                .rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(Items.REDSTONE_TORCH)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))
                                .addEntry(ItemLootEntry.builder(Items.REDSTONE_LAMP)
                                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                        .acceptCondition(KilledByPlayer.builder()))));
    }

    @Override
    public Set<EntityType<?>> getKnownEntities() {
        return knownEntities;
    }
}