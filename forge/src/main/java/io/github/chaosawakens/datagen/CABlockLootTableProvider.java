package io.github.chaosawakens.datagen;

import io.github.chaosawakens.api.block.BlockPropertyWrapper;
import io.github.chaosawakens.common.registry.CABlocks;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CABlockLootTableProvider extends BlockLootSubProvider {

    public CABlockLootTableProvider() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        BlockPropertyWrapper.getMappedBwps().forEach(((blockSupEntry, curBpw) -> {
            Function<Supplier<Block>, LootTable.Builder> mappedBuilderFunc = curBpw.getBlockLootTableMappingFunction();

            if (mappedBuilderFunc != null) add(blockSupEntry.get(), mappedBuilderFunc.apply(blockSupEntry));
        }));
    }

    protected LootTable.Builder createRangedOreDrop(Block oreBlock, Item drop, int minItemDrops, int maxItemDrops) {
        return createSilkTouchDispatchTable(oreBlock, applyExplosionDecay(oreBlock, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(UniformGenerator.between(minItemDrops, maxItemDrops))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CABlocks.getBlocks().stream()
                .map(Supplier::get)
                .collect(Collectors.toCollection(ObjectArrayList::new));
    }
}
