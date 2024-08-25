package io.github.chaosawakens.util;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Utility class providing commonly used predicates represented as shortcut methods.
 */
public final class PredicateUtil {

    private PredicateUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    /**
     * Dummy {@code static} OR (object-represented) predicate, typically used in {@link BlockBehaviour.Properties#isValidSpawn(BlockBehaviour.StateArgumentPredicate)} in order to prevent any kind of entity
     * from any given {@link MobCategory} from spawning on the parent {@link Block} composed of said properties.
     *
     * @param targetState The target {@link BlockState} on which no entity from any given {@link MobCategory} can naturally spawn.
     * @param targetBlockGetter The current level represented as a {@link BlockGetter}.
     * @param targetPos The {@link BlockPos} of the {@code targetState}.
     * @param typeToSpawn The {@link EntityType<?>} to test against (either way, none can naturally spawn on a {@link Block} whose {@link BlockBehaviour.Properties#isValidSpawn(BlockBehaviour.StateArgumentPredicate)}
     *                    method references this).
     *
     * @return {@code false}.
     */
    public static Boolean neverSpawnOnBlock(BlockState targetState, BlockGetter targetBlockGetter, BlockPos targetPos, EntityType<?> typeToSpawn) {
        return false;
    }

    /**
     * Predicate method representing an advancement criterion instance (in this case, {@link InventoryChangeTrigger.TriggerInstance}) whose flags will only return {@code true} if and only if a given
     * {@linkplain Entity Entity's} (in this case, {@linkplain Player Player's}) inventory contains a set of items that match the {@linkplain ItemPredicate ItemPredicates} passed in.
     *
     * @param predicates The array of {@linkplain ItemPredicate ItemPredicates} used to validate the contents of a given {@linkplain Player Player's} inventory for this criterion instance to trigger.
     *
     * @return {@code true} if the given {@linkplain Player Player's} inventory contains any items that pass the validation of each {@link ItemPredicate} passed in, otherwise returns {@code false}.
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

    /**
     *
     * @param stackToTest
     *
     * @return
     */
    public static boolean isFood(ItemStack stackToTest) {
        return stackToTest.getItem().getFoodProperties() != null;
    }

    /**
     *
     * @param stackToTest
     *
     * @return
     */
    public static boolean isArmor(ItemStack stackToTest) {
        return stackToTest.getItem() instanceof ArmorItem;
    }

    /**
     *
     * @param stackToTest
     *
     * @return
     */
    public static boolean isEquipable(ItemStack stackToTest) {
        return stackToTest.getItem() instanceof Equipable;
    }

    /**
     *
     * @param stackToTest
     *
     * @return
     */
    public static boolean isTiered(ItemStack stackToTest) {
        return stackToTest.getItem() instanceof TieredItem;
    }

    /**
     *
     * @param stackToTest
     *
     * @return
     */
    public static boolean isRegularItem(ItemStack stackToTest) {
        return !isFood(stackToTest) && !isArmor(stackToTest) && !isEquipable(stackToTest) && !isTiered(stackToTest) && !isBlockItem(stackToTest);
    }

    /**
     *
     * @param stackToTest
     *
     * @return
     */
    public static boolean isBlockItem(ItemStack stackToTest) {
        return stackToTest.getItem() instanceof BlockItem;
    }


    /**
     *
     * @param stackToTest
     *
     * @return
     */
    public static boolean isRegularBlock(ItemStack stackToTest) { //TODO Add misc checks for stuff like fossil blocks and such later on
        return isBlockItem(stackToTest);
    }
}
