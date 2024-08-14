package io.github.chaosawakens.util;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
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
    public static Supplier<Block> getPlanksFrom(Supplier<Block> logBlock) {
        ResourceLocation logBlockKey = BuiltInRegistries.BLOCK.getKey(logBlock.get());
        return logBlockKey.getPath().contains("log") ? () -> BuiltInRegistries.BLOCK.get(logBlockKey.withPath(BuiltInRegistries.BLOCK.getKey(logBlock.get()).getPath().replace("log", "planks"))) : null;
    }

    @Nullable
    public static Supplier<Block> getLogFrom(Supplier<Block> targetBlock) {
        ResourceLocation targetBlockKey = BuiltInRegistries.BLOCK.getKey(targetBlock.get());
        return targetBlockKey.getPath().contains("planks")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath().replace("planks", "log")))
                : targetBlockKey.getPath().endsWith("_wood")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath().replace("wood", "log")))
                : targetBlockKey.getPath().endsWith("wood")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath().replace("wood", "_log")))
                : null;
    }
}
