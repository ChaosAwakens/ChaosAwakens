package io.github.chaosawakens.util;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Utility class providing commonly used predicates represented as shortcut methods.
 */
public final class PredicateUtil {

    private PredicateUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    /**
     *
     *
     * @param targetState
     * @param targetBlockGetter
     * @param targetPos
     * @param typeToSpawn
     *
     * @return
     */
    public static Boolean neverSpawnOnBlock(BlockState targetState, BlockGetter targetBlockGetter, BlockPos targetPos, EntityType<?> typeToSpawn) {
        return false;
    }

    /**
     *
     * @param predicates
     *
     * @return
     */
    public static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... predicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, predicates);
    }

    /**
     *
     * @param targetItemLike
     *
     * @return
     */
    public static InventoryChangeTrigger.TriggerInstance has(ItemLike targetItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(targetItemLike).build());
    }

    /**
     *
     * @param targetTag
     *
     * @return
     */
    public static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> targetTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(targetTag).build());
    }
}
