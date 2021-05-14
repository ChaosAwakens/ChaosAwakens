package io.github.chaosawakens.data;

import io.github.chaosawakens.registry.CABlocks;
import io.github.chaosawakens.registry.CAItems;
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
        registerLootTable(CABlocks.AMETHYST_ORE.get(), (ore) -> {
            return droppingItemWithFortune(ore, CAItems.AMETHYST.get());
        });
        registerLootTable(CABlocks.RUBY_ORE.get(), (ore) -> {
            return droppingItemWithFortune(ore, CAItems.RUBY.get());
        });
        registerLootTable(CABlocks.TIGERS_EYE_ORE.get(), (ore) -> {
            return droppingItemWithFortune(ore, CAItems.TIGERS_EYE.get());
        });
        registerLootTable(CABlocks.SALT_ORE.get(), (ore) -> {
            return droppingItemWithFortune(ore, CAItems.SALT.get());
        });
        registerLootTable(CABlocks.GOLDEN_MELON.get(), (food) -> {
            return droppingItemWithFortune(food, CAItems.GOLDEN_MELON_SLICE.get());
        });
        registerLootTable(CABlocks.CRYSTAL_GRASS_BLOCK.get(), (food) -> {
            return dropping(CABlocks.KYANITE.get());
        });

        // BLOCKS
        registerDropSelfLootTable(CABlocks.AMETHYST_BLOCK.get());
        registerDropSelfLootTable(CABlocks.RUBY_BLOCK.get());
        registerDropSelfLootTable(CABlocks.TIGERS_EYE_BLOCK.get());
        registerDropSelfLootTable(CABlocks.URANIUM_ORE.get());
        registerDropSelfLootTable(CABlocks.URANIUM_BLOCK.get());
        registerDropSelfLootTable(CABlocks.TITANIUM_ORE.get());
        registerDropSelfLootTable(CABlocks.TITANIUM_BLOCK.get());
        registerDropSelfLootTable(CABlocks.ALUMINIUM_ORE.get());
        registerDropSelfLootTable(CABlocks.ALUMINIUM_BLOCK.get());
        registerDropSelfLootTable(CABlocks.BROWN_ANT_NEST.get());
        registerDropSelfLootTable(CABlocks.RAINBOW_ANT_NEST.get());
        registerDropSelfLootTable(CABlocks.RED_ANT_NEST.get());
        registerDropSelfLootTable(CABlocks.UNSTABLE_ANT_NEST.get());
        registerDropSelfLootTable(CABlocks.TERMITE_NEST.get());
        registerDropSelfLootTable(CABlocks.FOSSILISED_ENT.get());
        registerDropSelfLootTable(CABlocks.FOSSILISED_HERCULES_BEETLE.get());
        registerDropSelfLootTable(CABlocks.FOSSILISED_RUBY_BUG.get());
        registerDropSelfLootTable(CABlocks.FOSSILISED_EMERALD_GATOR.get());
        registerDropSelfLootTable(CABlocks.GATE_BLOCK.get());
        registerDropSelfLootTable(CABlocks.ENT_DUNGEON_WOOD.get());
        registerDropSelfLootTable(CABlocks.KYANITE.get());
        registerDropSelfLootTable(CABlocks.CRYSTAL_LOG.get());
        registerDropSelfLootTable(CABlocks.CRYSTAL_WOOD.get());
        registerDropSelfLootTable(CABlocks.CRYSTAL_WOOD_PLANKS.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CABlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}