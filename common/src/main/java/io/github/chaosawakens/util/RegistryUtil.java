package io.github.chaosawakens.util;

import io.github.chaosawakens.common.registry.CABlocks;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class providing shortcut methods for commonly used registry operations (primarily arbitrary).
 */
public final class RegistryUtil {
    private static final ObjectArrayList<File> CACHED_PNG_TEXTURES = new ObjectArrayList<>(); // Cache datagen found textures instead of creating a new list for each iteration/method call

    private RegistryUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    /**
     * Retrieves the registry name of the provided {@link ItemLike} via {@link DefaultedRegistry#getKey(Object)} (from the {@link BuiltInRegistries#ITEM}} registry).
     *
     * @param targetItemLike The {@link ItemLike} to retrieve the registry name of.
     *
     * @return The registry name of the provided {@link ItemLike}.
     */
    public static String getItemName(ItemLike targetItemLike) {
        return BuiltInRegistries.ITEM.getKey(targetItemLike.asItem()).getPath();
    }

    /**
     * Attempts to retrieve the texture for the supplied {@link Item} filename. <b>IDE/DEV-ENV ONLY!!!</b> This will not work outside of datagen in the dev environment, as it's only meant to be used to generate data
     * based on located textures. Mind that the registry name of the object attempting to utilise this method must EXACTLY match the name of the target texture to locate. This method (and its equivalents) is meant
     * to substitute for a lack of {@link Minecraft} in datagen, which by extension means a lack of texture management and whatnot.
     *
     * @param modid The modid under which the texture file should be validated against and located.
     * @param targetItemFileName The {@link String} for which a PNG file with a matching name should be located.
     *
     * @return A {@link ResourceLocation}, formatted and leading to the location of the target {@linkplain Item Item's} texture path, or {@code null} if none could be found.
     *
     * @see #getItemTexture(Supplier)
     * @see #getBlockTexture(String, String)
     * @see #getBlockTexture(Supplier)
     */
    @Nullable
    public static ResourceLocation getItemTexture(String modid, String targetItemFileName) {
        String formattedRegName = targetItemFileName.concat(".png");
        String classpath = System.getProperty("java.class.path");

        if (CACHED_PNG_TEXTURES.isEmpty()) {
            for (String curClassPath : classpath.split(File.pathSeparator)) {
                File curFile = new File(curClassPath);

                if (curFile.isDirectory()) {
                    try (Stream<Path> allExistingPaths = Files.walk(curFile.toPath(), FileVisitOption.FOLLOW_LINKS)) {
                        allExistingPaths.filter(Files::isRegularFile)
                                .filter(curPath -> curPath.toString().endsWith(".png"))
                                .forEach(curVerifiedPath -> CACHED_PNG_TEXTURES.add(curVerifiedPath.toFile()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (File curFile : CACHED_PNG_TEXTURES) {
            if (curFile.getName().equals(formattedRegName) && curFile.getPath().contains(modid) && curFile.getPath().contains("textures\\item")) {
                String name = curFile.getPath().replace("\\", "/");
                name = name.substring(name.indexOf("item") + "item/".length(), name.indexOf(".png"));

                return new ResourceLocation(modid, name);
            }
        }

        return null;
    }

    /**
     * Attempts to retrieve the texture for the supplied {@link Block} filename. <b>IDE/DEV-ENV ONLY!!!</b> This will not work outside of datagen in the dev environment, as it's only meant to be used to generate data
     * based on located textures. Mind that the registry name of the object attempting to utilise this method must EXACTLY match the name of the target texture to locate. This method (and its equivalents) is meant
     * to substitute for a lack of {@link Minecraft} in datagen, which by extension means a lack of texture management and whatnot.
     *
     * @param modid The modid under which the texture file should be validated against and located.
     * @param targetBlockFileName The {@link String} for which a PNG file with a matching name should be located.
     *
     * @return A {@link ResourceLocation}, formatted and leading to the location of the target {@linkplain Block Block's} texture path, or {@code null} if none could be found.
     *
     * @see #getBlockTexture(Supplier)
     * @see #getItemTexture(String, String)
     * @see #getItemTexture(Supplier)
     */
    @Nullable
    public static ResourceLocation getBlockTexture(String modid, String targetBlockFileName) {
        String formattedRegName = targetBlockFileName.concat(".png");
        String classpath = System.getProperty("java.class.path");

        if (CACHED_PNG_TEXTURES.isEmpty()) {
            for (String curClassPath : classpath.split(File.pathSeparator)) {
                File curFile = new File(curClassPath);

                if (curFile.isDirectory()) {
                    try (Stream<Path> allExistingPaths = Files.walk(curFile.toPath(), FileVisitOption.FOLLOW_LINKS)) {
                        allExistingPaths.filter(Files::isRegularFile)
                                .filter(curPath -> curPath.toString().endsWith(".png"))
                                .forEach(curVerifiedPath -> CACHED_PNG_TEXTURES.add(curVerifiedPath.toFile()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (File curFile : CACHED_PNG_TEXTURES) {
            if (curFile.getName().equals(formattedRegName) && curFile.getPath().contains(modid) && curFile.getPath().contains("textures\\block")) {
                String name = curFile.getPath().replace("\\", "/");
                name = name.substring(name.indexOf("block") + "block/".length(), name.indexOf(".png"));

                return new ResourceLocation(modid, name);
            }
        }

        return null;
    }

    /**
     * Overloaded variant of {@link #getItemTexture(String, String)}. Attempts to retrieve the texture for the supplied {@link Item}. <b>IDE/DEV-ENV ONLY!!!</b> This will not work outside of datagen
     * in the dev environment, as it's only meant to be used to generate data based on located textures. Mind that the registry name of the object attempting to utilise this method must EXACTLY match
     * the name of the target texture to locate. This method (and its equivalents) is meant to substitute for a lack of {@link Minecraft} in datagen, which by extension means a lack of texture management
     * and whatnot.
     *
     * @param targetItem The {@link Item} for which a PNG file with a matching name should be located.
     *
     * @return A {@link ResourceLocation}, formatted and leading to the location of the target {@linkplain Item Item's} texture path, or {@code null} if none could be found.
     *
     * @see #getItemTexture(String, String)
     * @see #getBlockTexture(String, String)
     * @see #getBlockTexture(Supplier)
     */
    @Nullable
    public static ResourceLocation getItemTexture(Supplier<Item> targetItem) {
        return getItemTexture(BuiltInRegistries.ITEM.getKey(targetItem.get()).getNamespace(), BuiltInRegistries.ITEM.getKey(targetItem.get()).getPath());
    }

    /**
     * Overloaded variant of {@link #getBlockTexture(String, String)}}. Attempts to retrieve the texture for the supplied {@link Block}. <b>IDE/DEV-ENV ONLY!!!</b> This will not work outside of datagen
     * in the dev environment, as it's only meant to be used to generate data based on located textures. Mind that the registry name of the object attempting to utilise this method must EXACTLY match
     * the name of the target texture to locate. This method (and its equivalents) is meant to substitute for a lack of {@link Minecraft} in datagen, which by extension means a lack of texture management
     * and whatnot.
     *
     * @param targetBlock The {@link Block} for which a PNG file with a matching name should be located.
     *
     * @return A {@link ResourceLocation}, formatted and leading to the location of the target {@linkplain Block Block's} texture path, or {@code null} if none could be found.
     *
     * @see #getBlockTexture(String, String)
     * @see #getItemTexture(String, String)
     * @see #getItemTexture(Supplier)
     */
    @Nullable
    public static ResourceLocation getBlockTexture(Supplier<Block> targetBlock) {
        return getBlockTexture(BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getNamespace(), BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath());
    }

    public static ResourceLocation pickPrefix(ResourceLocation baseLoc, String prefix) {
        return baseLoc.getPath().startsWith(prefix) ? baseLoc : baseLoc.withPrefix(prefix);
    }

    public static ResourceLocation pickBlockPrefix(ResourceLocation baseBlockLoc) {
        return pickPrefix(baseBlockLoc, "block/");
    }

    public static ResourceLocation pickItemPrefix(ResourceLocation baseItemLoc) {
        return pickPrefix(baseItemLoc, "item/");
    }

    @Nullable
    public static Supplier<Block> getFromWood(Supplier<Block> targetBlock, String regNameSuffix) {
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
    public static Supplier<Item> getBlockAsItemSup(Supplier<Block> targetBlock) {
        return CABlocks.getBlockItems().get(CABlocks.getBlocks().indexOf(targetBlock)); // TOP 10 UNSAFE PRACTICES: NUMBER 10: WHY
    }

    public static List<Supplier<Item>> getBlocksAsItemSups(List<Supplier<Block>> targetBlocks) {
        return targetBlocks.stream()
                .filter(curBlockSup -> CABlocks.getBlocks().contains(curBlockSup) && CABlocks.getBlockItems().get(CABlocks.getBlocks().indexOf(curBlockSup)) != null)
                .map(RegistryUtil::getBlockAsItemSup)
                .collect(Collectors.toCollection(ObjectArrayList::new));
    }

    public static List<Supplier<Item>> getBlocksAsItemSups(Supplier<Block>... targetBlocks) {
        return targetBlocks == null || targetBlocks.length == 0 ? ObjectArrayList.of() : getBlocksAsItemSups(ObjectArrayList.of(targetBlocks));
    }

    @Nullable
    public static Supplier<Block> getPlanksFrom(Supplier<Block> targetBlock) {
        return getFromWood(targetBlock, "_planks");
    }

    @Nullable
    public static Supplier<Block> getLogFrom(Supplier<Block> targetBlock) {
        return getFromWood(targetBlock, "_log");
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

        return !targetItemKey.getPath().startsWith("raw")
                ? () -> BuiltInRegistries.ITEM.get(targetItemKey.withPrefix("cooked_"))
                : copiedPath.startsWith("raw")
                ? () -> BuiltInRegistries.ITEM.get(targetItemKey.withPath(copiedPath.replace("raw_", "cooked_")))
                : null;
    }

    @Nullable
    public static Supplier<Item> getFromCookedFood(Supplier<Item> targetItem) {
        ResourceLocation targetItemKey = BuiltInRegistries.ITEM.getKey(targetItem.get());
        String copiedPath = BuiltInRegistries.ITEM.getKey(targetItem.get()).getPath();
        Supplier<Item> assumedCookedFood = () -> BuiltInRegistries.ITEM.get(targetItemKey.withPath(StringUtils.substringAfter(copiedPath, "cooked_")));

        return targetItemKey.getPath().startsWith("cooked_")
                ? assumedCookedFood.get().getDescriptionId().equals("block.minecraft.air")
                ? () -> BuiltInRegistries.ITEM.get(targetItemKey.withPath(copiedPath.replace("cooked_", "raw_")))
                : assumedCookedFood
                : null;
    }

    @Nullable
    public static Supplier<Block> getFromSolidBlock(Supplier<Block> targetBlock, String regNameSuffix) {
        ResourceLocation targetBlockKey = BuiltInRegistries.BLOCK.getKey(targetBlock.get());
        String copiedPath = BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath();

        if (copiedPath.contains("bricks")) copiedPath.replace("bricks", "brick");

        return targetBlockKey.getPath().endsWith("_block")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.replace("_block", regNameSuffix)))
                : !BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.concat(regNameSuffix))).getDescriptionId().equals("block.minecraft.air")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.concat(regNameSuffix)))
                : null;
    }

    @Nullable
    public static Supplier<Block> getSlabFromSolidBlock(Supplier<Block> targetBlock) {
        return getFromSolidBlock(targetBlock, "_slab");
    }

    @Nullable
    public static Supplier<Block> getStairsFromSolidBlock(Supplier<Block> targetBlock) {
        return getFromSolidBlock(targetBlock, "_stairs");
    }

    @Nullable
    public static Supplier<Block> getPillarFromSolidBlock(Supplier<Block> targetBlock) {
        return getFromSolidBlock(targetBlock, "_pillar");
    }

    @Nullable
    public static Supplier<Block> getWallFromSolidBlock(Supplier<Block> targetBlock) {
        return getFromSolidBlock(targetBlock, "_wall");
    }

    @Nullable
    public static Supplier<Block> getSolidBlockFrom(Supplier<Block> targetBlock, String targetRegNameSuffix) {
        ResourceLocation targetBlockKey = BuiltInRegistries.BLOCK.getKey(targetBlock.get());
        String copiedPath = BuiltInRegistries.BLOCK.getKey(targetBlock.get()).getPath();

        if (copiedPath.contains("brick")) copiedPath.replace("brick", "bricks");

        return targetBlockKey.getPath().endsWith(targetRegNameSuffix)
                ? !BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.replace(targetRegNameSuffix, "_block"))).getDescriptionId().equals("block.minecraft.air")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.replace(targetRegNameSuffix, "_block")))
                : !BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.replace(targetRegNameSuffix, ""))).getDescriptionId().equals("block.minecraft.air")
                ? () -> BuiltInRegistries.BLOCK.get(targetBlockKey.withPath(copiedPath.replace(targetRegNameSuffix, "")))
                : null
                : null;
    }

    @Nullable
    public static Supplier<Block> getSolidBlockFromSlab(Supplier<Block> targetBlock) {
        return getFromSolidBlock(targetBlock, "_slab");
    }

    @Nullable
    public static Supplier<Block> getSolidBlockFromStairs(Supplier<Block> targetBlock) {
        return getFromSolidBlock(targetBlock, "_stairs");
    }

    @Nullable
    public static Supplier<Block> getSolidBlockFromPillar(Supplier<Block> targetBlock) {
        return getFromSolidBlock(targetBlock, "_pillar");
    }

    @Nullable
    public static Supplier<Block> getSolidBlockFromWall(Supplier<Block> targetBlock) {
        return getFromSolidBlock(targetBlock, "_wall");
    }
}
