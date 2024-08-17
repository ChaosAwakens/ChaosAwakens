package io.github.chaosawakens.util;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Utility class providing shortcut methods for commonly used registry operations.
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
}
