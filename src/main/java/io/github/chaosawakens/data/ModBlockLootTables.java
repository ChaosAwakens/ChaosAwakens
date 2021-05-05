package io.github.chaosawakens.data;

import io.github.chaosawakens.registry.ModBlocks;
import io.github.chaosawakens.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;

public class ModBlockLootTables extends BlockLootTables
{
    @Override
    protected void addTables()
    {
        // ORES
        registerLootTable(ModBlocks.AMETHYST_ORE.get(), (ore) -> {
            return droppingItemWithFortune(ore, ModItems.AMETHYST.get());
        });
        registerLootTable(ModBlocks.RUBY_ORE.get(), (ore) -> {
            return droppingItemWithFortune(ore, ModItems.RUBY.get());
        });
        registerLootTable(ModBlocks.TIGERS_EYE_ORE.get(), (ore) -> {
            return droppingItemWithFortune(ore, ModItems.TIGERS_EYE.get());
        });
        registerLootTable(ModBlocks.SALT_ORE.get(), (ore) -> {
            return droppingItemWithFortune(ore, ModItems.SALT.get());
        });
        registerLootTable(ModBlocks.GOLDEN_MELON.get(), (food) -> {
            return droppingItemWithFortune(food, ModItems.GOLDEN_MELON_SLICE.get());
        });

        // BLOCKS
        registerDropSelfLootTable(ModBlocks.AMETHYST_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.RUBY_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.TIGERS_EYE_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.URANIUM_ORE.get());
        registerDropSelfLootTable(ModBlocks.URANIUM_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.TITANIUM_ORE.get());
        registerDropSelfLootTable(ModBlocks.TITANIUM_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.ALUMINIUM_ORE.get());
        registerDropSelfLootTable(ModBlocks.ALUMINIUM_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.BROWN_ANT_NEST.get());
        registerDropSelfLootTable(ModBlocks.RAINBOW_ANT_NEST.get());
        registerDropSelfLootTable(ModBlocks.RED_ANT_NEST.get());
        registerDropSelfLootTable(ModBlocks.UNSTABLE_ANT_NEST.get());
        registerDropSelfLootTable(ModBlocks.TERMITE_NEST.get());
        registerDropSelfLootTable(ModBlocks.FOSSILISED_ENT.get());
        registerDropSelfLootTable(ModBlocks.FOSSILISED_HERCULES_BEETLE.get());
        registerDropSelfLootTable(ModBlocks.FOSSILISED_RUBY_BUG.get());
        registerDropSelfLootTable(ModBlocks.FOSSILISED_EMERALD_GATOR.get());
        registerDropSelfLootTable(ModBlocks.GATE_BLOCK.get());
        registerDropSelfLootTable(ModBlocks.ENT_DUNGEON_WOOD.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}