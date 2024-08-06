package io.github.chaosawakens.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.Supplier;

/**
 * Utility class containing helper methods (generally also found in datagen classes) aimed at reducing boilerplate code by providing
 * common {@link LootTable.Builder} patterns.
 */
public final class LootUtil {

    private LootUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    /**
     * Creates {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed.
     *
     * <h2>LOOT TABLE</h2>
     * <h3>Pool 1</h3>
     * <ul>
     *  <li><b>Rolls:</b> 1.0</li>
     *  <li><b>When:</b> {@link ExplosionCondition#survivesExplosion()}</li>
     *  <li><b>Drops:</b> {@code targetBlock}</li>
     * </ul>
     *
     * @param targetBlock The {@link Supplier<Block>} that will be dropped.
     *
     * @return A {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed.
     */
    public static LootTable.Builder dropSelf(Supplier<Block> targetBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(targetBlock.get())));
    }
}
