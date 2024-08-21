package io.github.chaosawakens.util;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.function.Supplier;

/**
 * Utility class containing helper methods (generally also found in datagen classes) aimed at reducing boilerplate code by providing
 * common {@link LootTable.Builder} patterns.
 */
public final class LootUtil {
    public static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    public static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    public static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    public static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    public static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    private LootUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    /**
     * Creates {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed.
     * <p>
     * <h2>LOOT TABLE</h2>
     * <h3>Pool 1</h3>
     * <ul>
     *  <li><b>Rolls:</b> 1.0</li>
     *  <li><b>When:</b> {@link ExplosionCondition#survivesExplosion()}</li>
     *  <li><b>Drops:</b> {@code targetBlock}</li>
     * </ul>
     *
     * @param targetBlock The {@link Supplier<Block>} representing the {@link Block} that will be dropped.
     *
     * @return A {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed.
     */
    public static LootTable.Builder dropSelf(Supplier<Block> targetBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(targetBlock.get())));
    }

    /**
     * Creates {@link LootTable.Builder} that will treat the given {@link Block} as a slab and drop it based on whether it's a half or double slab when it's destroyed, if it has the {@link SlabBlock#TYPE} property.
     * <p>
     * <h2>LOOT TABLE</h2>
     * <h3>Pool 1</h3>
     * <ul>
     *  <li><b>Rolls:</b> 1.0</li>
     *  <li><b>Applies:</b> {@link ApplyExplosionDecay#explosionDecay()}</li>
     *  <li><b>Loot Pool Entries:</b> <ul>
     *      <li><b>Loot Table Item:</b> {@code targetBlock}</li>
     *      <li><b>Applies:</b> {@link SetItemCountFunction#setCount(NumberProvider)} (Drops 2.0F)</li>
     *      <li><b>When:</b> {@link LootItemBlockStatePropertyCondition#hasBlockStateProperties(Block)} (Has {@link SlabBlock#TYPE}, and it's {@link SlabType#DOUBLE})</li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@link Supplier<Block>} representing the {@link Block} that will be treated as a slab on drop.
     *
     * @return A {@link LootTable.Builder} that will treat the given {@link Block} as a slab and drop it based on whether it's a half or double slab when it's destroyed.
     */
    public static LootTable.Builder dropSlab(Supplier<Block> targetBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .apply(ApplyExplosionDecay.explosionDecay())
                .add(LootItem.lootTableItem(targetBlock.get())
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(targetBlock.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))));
    }

    /**
     * Creates {@link LootTable.Builder} that will treat the given {@link Block} as a door and drop it based on whether it has its lower half, if it has the {@link DoorBlock#HALF} property.
     * <p>
     * <h2>LOOT TABLE</h2>
     * <h3>Pool 1</h3>
     * <ul>
     *  <li><b>Rolls:</b> 1.0</li>
     *  <li><b>Applies:</b> {@link ApplyExplosionDecay#explosionDecay()}</li>
     *  <li><b>Loot Pool Entries:</b> <ul>
     *      <li><b>Loot Table Item:</b> {@code targetBlock}</li>
     *      <li><b>When:</b> {@link LootItemBlockStatePropertyCondition#hasBlockStateProperties(Block)} (Has {@link DoorBlock#HALF}, and it's {@link DoubleBlockHalf#LOWER})</li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@link Supplier<Block>} representing the {@link Block} that will be treated as a door on drop.
     *
     * @return A {@link LootTable.Builder} that will treat the given {@link Block} as a door and drop it based on whether it has its lower half, if it has the {@link DoorBlock#HALF} property.
     */
    public static LootTable.Builder dropDoor(Supplier<Block> targetBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .apply(ApplyExplosionDecay.explosionDecay())
                .add(LootItem.lootTableItem(targetBlock.get())
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(targetBlock.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(DoorBlock.HALF, DoubleBlockHalf.LOWER)))));
    }

    /**
     * Creates {@link LootTable.Builder} that will treat the given {@link Block} as a bed and drop it based on whether it has its head, if it has the {@link BedBlock#PART} property.
     * <p>
     * <h2>LOOT TABLE</h2>
     * <h3>Pool 1</h3>
     * <ul>
     *  <li><b>Rolls:</b> 1.0</li>
     *  <li><b>Applies:</b> {@link ApplyExplosionDecay#explosionDecay()}</li>
     *  <li><b>Loot Pool Entries:</b> <ul>
     *      <li><b>Loot Table Item:</b> {@code targetBlock}</li>
     *      <li><b>When:</b> {@link LootItemBlockStatePropertyCondition#hasBlockStateProperties(Block)} (Has {@link BedPart#HEAD}, and it's {@link BedBlock#PART})</li>
     *  </ul></li>
     * </ul>
     *
     * @param targetBlock The {@link Supplier<Block>} representing the {@link Block} that will be treated as a bed on drop.
     *
     * @return A {@link LootTable.Builder} that will treat the given {@link Block} as a bed and drop it based on whether it has its head, if it has the {@link BedBlock#PART} property.
     */
    public static LootTable.Builder dropBed(Supplier<Block> targetBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .apply(ApplyExplosionDecay.explosionDecay())
                .add(LootItem.lootTableItem(targetBlock.get())
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(targetBlock.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(BedBlock.PART, BedPart.HEAD)))));
    }

    /**
     * Creates {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed, but only if it's destroyed with shears.
     * <p>
     * <h2>LOOT TABLE</h2>
     * <h3>Pool 1</h3>
     * <ul>
     *  <li><b>Rolls:</b> 1.0</li>
     *  <li><b>When:</b> {@link #HAS_SHEARS}</li>
     *  <li><b>Drops:</b> {@code targetBlock}</li>
     * </ul>
     *
     * @param targetBlock The {@link Supplier<Block>} representing the {@link Block} that will be dropped when shears are used to mine it.
     *
     * @return A {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed, but only if it's destroyed with shears.
     */
    public static LootTable.Builder dropShearsOnly(Supplier<Block> targetBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(HAS_SHEARS)
                .add(LootItem.lootTableItem(targetBlock.get())));
    }

    /**
     * Creates {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed, but only if it's destroyed with a tool enchanted with silk touch.
     * <p>
     * <h2>LOOT TABLE</h2>
     * <h3>Pool 1</h3>
     * <ul>
     *  <li><b>Rolls:</b> 1.0</li>
     *  <li><b>When:</b> {@link #HAS_SILK_TOUCH}</li>
     *  <li><b>Drops:</b> {@code targetBlock}</li>
     * </ul>
     *
     * @param targetBlock The {@link Supplier<Block>} representing the {@link Block} that will be dropped when any silk touch tool is used to mine it.
     *
     * @return A {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed, but only if it's destroyed with a tool enchanted with silk touch.
     */
    public static LootTable.Builder dropSilkTouchOnly(Supplier<Block> targetBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(HAS_SILK_TOUCH)
                .add(LootItem.lootTableItem(targetBlock.get())));
    }

    /**
     * Creates {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed, but only if it's destroyed with shears or a tool enchanted with silk touch.
     * <p>
     * <h2>LOOT TABLE</h2>
     * <h3>Pool 1</h3>
     * <ul>
     *  <li><b>Rolls:</b> 1.0</li>
     *  <li><b>When:</b> {@link #HAS_SHEARS_OR_SILK_TOUCH}</li>
     *  <li><b>Drops:</b> {@code targetBlock}</li>
     * </ul>
     *
     * @param targetBlock The {@link Supplier<Block>} representing the {@link Block} that will be dropped when any shears or silk touch tool is used to mine it.
     *
     * @return A {@link LootTable.Builder} that will drop the given {@link Block} when it's destroyed, but only if it's destroyed with a shears or any tool enchanted with silk touch.
     */
    public static LootTable.Builder dropSilkTouchOrShears(Supplier<Block> targetBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(HAS_SHEARS_OR_SILK_TOUCH)
                .add(LootItem.lootTableItem(targetBlock.get())));
    }

    /**
     * Creates a {@link LootTable.Builder} that will not drop anything. No loot table.
     *
     * @param targetBlock Dummy parameter for convenient function-lambda method reference.. convenience.
     *
     * @return A {@link LootTable.Builder} that will not drop anything.
     */
    public static LootTable.Builder noDrop(Supplier<Block> targetBlock) {
        return LootTable.lootTable();
    }
}
