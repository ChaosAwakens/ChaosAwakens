package io.github.chaosawakens.api.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

/**
 * A builder wrapper class used primarily in datagen for wrapping around created {@link LootTable.Builder} instances and allowing more type-flexibility.
 */
public class BlockLootTableDefinition {
    private final LootTable.Builder parentLootTable;
    @Nullable
    private ItemLike storedItemLikeRef;

    private BlockLootTableDefinition(LootTable.Builder parentLootTable) {
        this.parentLootTable = parentLootTable;
    }

    public static BlockLootTableDefinition createLootTable() {
        return new BlockLootTableDefinition(LootTable.lootTable());
    }

    public BlockLootTableDefinition defineOwnerItem(String regName) {
        return this;
    }
}
