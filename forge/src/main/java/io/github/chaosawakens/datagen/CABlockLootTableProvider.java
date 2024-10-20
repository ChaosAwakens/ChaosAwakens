package io.github.chaosawakens.datagen;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.block.standard.BlockPropertyWrapper;
import io.github.chaosawakens.common.registry.CABlocks;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

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
        BlockPropertyWrapper.getMappedBpws().forEach(((blockSupEntry, curBpw) -> {
            Function<Supplier<Block>, LootTable.Builder> mappedBuilderFunc = curBpw.getBlockLootTableMappingFunction();

            if (mappedBuilderFunc != null) {
                CAConstants.LOGGER.debug("[Adding Loot Table]: " + blockSupEntry.get().getDescriptionId());
                add(blockSupEntry.get(), mappedBuilderFunc.apply(blockSupEntry));
            }
        }));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CABlocks.getBlocks().stream()
                .map(Supplier::get)
                .collect(Collectors.toCollection(ObjectArrayList::new));
    }
}
