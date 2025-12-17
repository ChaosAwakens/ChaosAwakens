package io.github.chaosawakens.datagen;

import io.github.chaosawakens.common.registry.CAChestLootTables;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class CAChestLootTableProvider implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {
        CAChestLootTables.LOOT_TABLES.forEach((pair) -> pOutput.accept(pair.getLeft(), pair.getRight().get()));
    }
}
