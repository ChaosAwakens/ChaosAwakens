package io.github.chaosawakens.util;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Utility class providing shortcut methods for commonly used registry operations (primarily arbitrary).
 */
public final class RegistryUtil {

    private RegistryUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    /**
     * Retrieves the registry name of the provided {@link ItemLike} via {@link DefaultedRegistry#getKey(Object)} (from the {@link BuiltInRegistries#ITEM} registry).
     *
     * @param targetItemLike The {@link ItemLike} to retrieve the registry name of.
     *
     * @return The registry name of the provided {@link ItemLike}.
     */
    public static String getItemName(ItemLike targetItemLike) {
        return BuiltInRegistries.ITEM.getKey(targetItemLike.asItem()).getPath();
    }

    @Nullable
    public static Supplier<Block> getWoodFrom(Supplier<Block> targetBlock, String regNameSuffix) {
        ResourceLocation targetBlockKey = BuiltInRegistries.BLOCK.getKey(targetBlock.get());
        String copiedPath = BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath();

        return targetBlockKey.getPath().startsWith("stripped")
                ? targetBlockKey.getPath().endsWith("wood") && targetBlockKey.getPath().charAt(targetBlockKey.getPath().indexOf("wood") - 1) != '_'
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.concat(regNameSuffix)))
                : () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(StringUtils.substring(copiedPath, 0, copiedPath.lastIndexOf("_")).concat(regNameSuffix)))
                : targetBlockKey.getPath().endsWith("_wood")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(StringUtils.substring(copiedPath, 0, copiedPath.lastIndexOf("_")).concat(regNameSuffix)))
                : targetBlockKey.getPath().endsWith("wood") && targetBlockKey.getPath().charAt(targetBlockKey.getPath().indexOf("wood") - 1) != '_'
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.concat(regNameSuffix)))
                : targetBlockKey.getPath().contains("_")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(StringUtils.substringBefore(copiedPath, "_").concat(regNameSuffix)))
                : null; // Force nullity rather than air delegate
    }

    @Nullable
    public static Supplier<Block> getPlanksFrom(Supplier<Block> targetBlock) {
        return getWoodFrom(targetBlock, "_planks");
    }

    @Nullable
    public static Supplier<Block> getLogFrom(Supplier<Block> targetBlock) {
        return getWoodFrom(targetBlock, "_log");
    }

    @Nullable
    public static Supplier<Block> getLeavesFrom(Supplier<Block> targetBlock) {
        ResourceLocation targetBlockKey = BuiltInRegistries.BLOCK.getKey(targetBlock.get());
        String copiedPath = BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath();

        return targetBlockKey.getPath().endsWith("leaf_carpet")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.replace("leaf_carpet", "leaves")))
                : null;
    }

    @Nullable
    public static Supplier<Block> getFromLeaves(Supplier<Block> targetBlock, String regNameSuffix) {
        ResourceLocation targetBlockKey = BuiltInRegistries.BLOCK.getKey(targetBlock.get());
        String copiedPath = BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath();

        return targetBlockKey.getPath().endsWith("leaves")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.replace("leaves", regNameSuffix)))
                : null;
    }

    @Nullable
    public static Supplier<Block> getLeafCarpetFrom(Supplier<Block> targetBlock) {
        return getFromLeaves(targetBlock, "leaf_carpet");
    }

    @Nullable
    public static Supplier<Block> getSaplingFrom(Supplier<Block> targetBlock) {
        return getFromLeaves(targetBlock, "sapling");
    }

    @Nullable
    public static Supplier<Item> getCookedFoodFrom(Supplier<Item> targetItem) {
        ResourceLocation targetItemKey = BuiltInRegistries.ITEM.getKey(targetItem.get());
        String copiedPath = BuiltInRegistries.ITEM.getKey(targetItem.get()).getPath();

        return !BuiltInRegistries.ITEM.get(targetItemKey.withPrefix("cooked_")).getDescriptionId().equals("block.minecraft.air")
                ? () -> BuiltInRegistries.ITEM.get(targetItemKey.withPrefix("cooked_"))
                : null;
    }

    @Nullable
    public static Supplier<Item> getFromCookedFood(Supplier<Item> targetItem) {
        ResourceLocation targetItemKey = BuiltInRegistries.ITEM.getKey(targetItem.get());
        String copiedPath = BuiltInRegistries.ITEM.getKey(targetItem.get()).getPath();
        Supplier<Item> assumedCookedFood = () -> BuiltInRegistries.ITEM.get(targetItemKey.withPath(StringUtils.substringAfter(copiedPath, "cooked_")));

        return copiedPath.startsWith("cooked_")
                ? assumedCookedFood.get().getDescriptionId().equals("block.minecraft.air")
                ? () -> BuiltInRegistries.ITEM.get(targetItemKey.withPath(copiedPath.replace("cooked_", "raw_")))
                : assumedCookedFood
                : null;
    }
}
