package io.github.chaosawakens.core.template;

import com.mememan.nexus.platform.NexusServices;
import com.mememan.nexus.property_wrapper.base.generic.DataGenPropertyWrapper;
import com.mememan.nexus.template.object.block.misc.WoodenBlockGroup;
import com.mememan.nexus.util.PredicateUtil;
import com.mememan.nexus.util.RecipeUtil;
import com.mememan.nexus.util.RegistryUtil;
import com.mememan.nexus.util.StringUtil;
import io.github.chaosawakens.content.data.recipe_builder.DefossilizingRecipeBuilder;
import io.github.chaosawakens.content.registry.CABlocks;
import io.github.chaosawakens.content.registry.CAItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class CARecipeTemplates {

    private CARecipeTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CARecipeTemplates)");
    }
/*
    public static <I extends Item> Consumer<Supplier<I>> Recipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("   ")
                .pattern("   ")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> bootsRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> Recipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }
*/
    public static <B extends Block> Consumer<Supplier<B>> patternBlockRecipeFrom(Consumer<FinishedRecipe> finishedRecipe, Function<B, B> bricksComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return parentItemLikeSup -> {
            B parentItemLike = parentItemLikeSup.get();
            ResourceLocation parentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(parentItemLike);

            B componentItemLike = bricksComponentMapper.apply(parentItemLike);

            if (componentItemLike != null) {
                ResourceLocation componentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(componentItemLike);
                ResourceLocation baseRecipeId = recipeIdMapper.apply(parentItemLikeId);

                SingleItemRecipeBuilder.stonecutting(Ingredient.of(componentItemLike), RecipeCategory.BUILDING_BLOCKS, parentItemLike)
                        .unlockedBy("has_" + componentItemLikeId.getPath(), PredicateUtil.has(componentItemLike))
                        .save(finishedRecipe, baseRecipeId.withPath(baseRecipeId.getPath() + "_from_" + componentItemLikeId.getPath() + "_stonecutting"));
            }
        };
    }

    public static <B extends Block> Consumer<Supplier<B>> patternBlockRecipeFrom(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return patternBlockRecipeFrom(finishedRecipe, parentBricks -> RegistryUtil.getObjectFrom(parentBricks, parentBricksId -> parentBricksId.withPath(curPath -> curPath.replace("pattern_", ""))).orElse(null), recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> patternBlockRecipeFrom(Consumer<FinishedRecipe> finishedRecipe) {
        return patternBlockRecipeFrom(finishedRecipe, Function.identity());
    }

    public static <B extends Block> Consumer<Supplier<B>> leafCarpetRecipeFrom(Consumer<FinishedRecipe> finishedRecipe, Function<B, B> leafCarpetComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return parentItemLikeSup -> {
            B parentItemLike = parentItemLikeSup.get();
            ResourceLocation parentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(parentItemLike);

            B componentItemLike = leafCarpetComponentMapper.apply(parentItemLike);

            if (componentItemLike != null) {
                ResourceLocation componentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(componentItemLike);
                ResourceLocation baseRecipeId = recipeIdMapper.apply(parentItemLikeId);

                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, parentItemLike)
                        .define('L', componentItemLike)
                        .pattern("LL")
                        .unlockedBy("has_" + componentItemLikeId.getPath(), PredicateUtil.has(componentItemLike))
                        .save(finishedRecipe, baseRecipeId);
            }
        };
    }

    public static <B extends Block> Consumer<Supplier<B>> leafCarpetRecipeFrom(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return leafCarpetRecipeFrom(finishedRecipe, parentLeafCarpet -> RegistryUtil.getObjectFrom(parentLeafCarpet, parentLeafCarpetId -> parentLeafCarpetId.withPath(curPath -> curPath.replace("_leaf_carpet", "_leaves")))
                .or(() -> RegistryUtil.getObjectFrom(parentLeafCarpet, parentLeafCarpetId -> parentLeafCarpetId.withPath(curPath -> curPath.replace("_carpet", ""))))
                .or(() -> RegistryUtil.getObjectFrom(parentLeafCarpet, parentLeafCarpetId -> new ResourceLocation(parentLeafCarpetId.getPath().replace("_leaf_carpet", "_leaves"))))
                .or(() -> RegistryUtil.getObjectFrom(parentLeafCarpet, parentLeafCarpetId -> new ResourceLocation(parentLeafCarpetId.getPath().replace("_carpet", ""))))
                .orElse(null), recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> leafCarpetRecipeFrom(Consumer<FinishedRecipe> finishedRecipe) {
        return leafCarpetRecipeFrom(finishedRecipe, Function.identity());
    }

    // Overloaded Ultimate Tools
    public static <I extends Item> Consumer<Supplier<I>> ultSwordRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, I itemPlatinum, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .define('P', itemPlatinum)
                .pattern(" T ")
                .pattern(" U ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPlatinum), PredicateUtil.has(itemPlatinum))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultSwordRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultSwordRecipe(recipeConsumer, itemUranium, itemTitanium, itemPlatinum, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultPickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, I itemPlatinum, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .define('P', itemPlatinum)
                .pattern("TUT")
                .pattern(" U ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPlatinum), PredicateUtil.has(itemPlatinum))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultPickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultPickaxeRecipe(recipeConsumer, itemUranium, itemTitanium, itemPlatinum, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, I itemPlatinum, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .define('P', itemPlatinum)
                .pattern("TT ")
                .pattern("UP ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPlatinum), PredicateUtil.has(itemPlatinum))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultAxeRecipe(recipeConsumer, itemUranium, itemTitanium, itemPlatinum, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultShovelRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, I itemPlatinum, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .define('P', itemPlatinum)
                .pattern(" U ")
                .pattern(" T ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPlatinum), PredicateUtil.has(itemPlatinum))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultShovelRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultShovelRecipe(recipeConsumer, itemUranium, itemTitanium, itemPlatinum, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultHoeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, I itemPlatinum, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .define('P', itemPlatinum)
                .pattern("UU ")
                .pattern(" T ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPlatinum), PredicateUtil.has(itemPlatinum))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultHoeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultHoeRecipe(recipeConsumer, itemUranium, itemTitanium, itemPlatinum, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Tools
    public static <I extends Item> Consumer<Supplier<I>> swordRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" M ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> swordRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> swordRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> pickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> pickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> pickaxeRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> axeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern("MS ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> axeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> axeRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> shovelRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> shovelRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> shovelRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> hoeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> hoeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> hoeRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Overloaded Kyanite Tools
    public static <I extends Item, B extends Block> Consumer<Supplier<I>> swordRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" M ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> swordRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> swordRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> pickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> pickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> pickaxeRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> axeRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern("MS ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> axeRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> axeRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> shovelRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> shovelRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> shovelRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> hoeRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> hoeRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> hoeRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Overloaded Crystalwood Tools
    /*
    public static <I extends Item, B extends Block> Consumer<Supplier<I>> swordWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" M ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> swordWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> swordWoodRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> pickaxeWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> pickaxeWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> pickaxeWoodRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> axeWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern("MS ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> axeWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> axeWoodRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> shovelWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> shovelWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> shovelWoodRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> hoeWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial), PredicateUtil.has(itemBlockMaterial))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> hoeWoodRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> hoeWoodRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }
    */
    // Overloaded Ultimate Armour
    public static <I extends Item> Consumer<Supplier<I>> ultHelmetRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, I itemPlatinum, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .define('P', itemPlatinum)
                .pattern("TPT")
                .pattern("U U")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPlatinum), PredicateUtil.has(itemPlatinum))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultHelmetRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultHelmetRecipe(recipeConsumer, itemUranium, itemTitanium, itemPlatinum, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultChestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, I itemPlatinum, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .define('P', itemPlatinum)
                .pattern("P P")
                .pattern("TTT")
                .pattern("UUU")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPlatinum), PredicateUtil.has(itemPlatinum))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultChestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultChestplateRecipe(recipeConsumer, itemUranium, itemTitanium, itemPlatinum, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultLeggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, I itemPlatinum, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .define('P', itemPlatinum)
                .pattern("TTT")
                .pattern("U U")
                .pattern("P P")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPlatinum), PredicateUtil.has(itemPlatinum))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultLeggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultLeggingsRecipe(recipeConsumer, itemUranium, itemTitanium, itemPlatinum, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultBootsRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUranium, I itemTitanium, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', itemUranium)
                .define('T', itemTitanium)
                .pattern("T T")
                .pattern("U U")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUranium), PredicateUtil.has(itemUranium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultBootsRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUranium, Item itemTitanium, Item itemPlatinum) {
        return (resultItemSup) -> ultBootsRecipe(recipeConsumer, itemUranium, itemTitanium, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Armour
    public static <I extends Item> Consumer<Supplier<I>> helmetRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("MMM")
                .pattern("M M")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> helmetRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> helmetRecipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> chestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> chestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> chestplateRecipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> leggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> leggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> leggingsRecipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> bootsRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("M M")
                .pattern("M M")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial), PredicateUtil.has(itemMaterial))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> bootsRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> bootsRecipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }

    //Prismatic Reaper
    public static <I extends Item> Consumer<Supplier<I>> prismaticReaperRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemKunzite, I itemRuby, I itemDiamond, I itemEmerald, I itemStick, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('K', itemKunzite)
                .define('R', itemRuby)
                .define('D', itemDiamond)
                .define('E', itemEmerald)
                .define('S', itemStick)
                .pattern("KDE")
                .pattern("RS ")
                .pattern("S  ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemKunzite), PredicateUtil.has(itemKunzite))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemRuby), PredicateUtil.has(itemRuby))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemDiamond), PredicateUtil.has(itemDiamond))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemEmerald), PredicateUtil.has(itemEmerald))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick), PredicateUtil.has(itemStick))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> prismaticReaperRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemKunzite, Item itemRuby, Item itemDiamond, Item itemEmerald, Item itemStick) {
        return (resultItemSup) -> prismaticReaperRecipe(recipeConsumer, itemKunzite, itemRuby, itemDiamond, itemEmerald, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Nightmare Sword
    public static <I extends Item> Consumer<Supplier<I>> nightmareSwordRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemNightmare, I itemDiamond, I itemTitanium, I itemRedstone, I itemIron, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('N', itemNightmare)
                .define('D', itemDiamond)
                .define('T', itemTitanium)
                .define('R', itemRedstone)
                .define('I', itemIron)
                .pattern("NDN")
                .pattern("RTR")
                .pattern("NIN")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemNightmare), PredicateUtil.has(itemNightmare))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemDiamond), PredicateUtil.has(itemDiamond))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTitanium), PredicateUtil.has(itemTitanium))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemRedstone), PredicateUtil.has(itemRedstone))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemIron), PredicateUtil.has(itemIron))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> nightmareSwordRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemNightmare, Item itemDiamond, Item itemTitanium, Item itemRedstone, Item itemIron) {
        return (resultItemSup) -> nightmareSwordRecipe(recipeConsumer, itemNightmare, itemDiamond, itemTitanium, itemRedstone, itemIron, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Big Bethra Parts
    public static <I extends Item> Consumer<Supplier<I>> bigBerthaHandelRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemJefferyCore, I itemHammer, I itemMantisClaw, I itemWaterDragon, I itemTriffidGoo, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('C', itemJefferyCore)
                .define('H', itemHammer)
                .define('M', itemMantisClaw)
                .define('W', itemWaterDragon)
                .define('G', itemTriffidGoo)
                .pattern("  W")
                .pattern("MG ")
                .pattern("CH ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemJefferyCore), PredicateUtil.has(itemJefferyCore))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemHammer), PredicateUtil.has(itemHammer))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMantisClaw), PredicateUtil.has(itemMantisClaw))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemWaterDragon), PredicateUtil.has(itemWaterDragon))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTriffidGoo), PredicateUtil.has(itemTriffidGoo))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> bigBerthaHandelRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemJefferyCore, SwordItem itemHammer, SwordItem itemMantisClaw, Item itemWaterDragon, Item itemTriffidGoo) {
        return (resultItemSup) -> bigBerthaHandelRecipe(recipeConsumer, itemJefferyCore, itemHammer, itemMantisClaw, itemWaterDragon, itemTriffidGoo, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> bigBerthaGuardRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemEnderDragon, I itemNightmareSword, I itemVortexEye, I itemMothScale, I itemBascaliscScale, I itemScorpionScale, I itemNightmareScale, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('E', itemEnderDragon)
                .define('S', itemNightmareSword)
                .define('V', itemVortexEye)
                .define('O', itemMothScale)
                .define('B', itemBascaliscScale)
                .define('C', itemScorpionScale)
                .define('N', itemNightmareScale)
                .pattern("O B")
                .pattern("EV ")
                .pattern("CSN")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemEnderDragon), PredicateUtil.has(itemEnderDragon))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemNightmareSword), PredicateUtil.has(itemNightmareSword))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemVortexEye), PredicateUtil.has(itemVortexEye))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMothScale), PredicateUtil.has(itemMothScale))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBascaliscScale), PredicateUtil.has(itemBascaliscScale))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemScorpionScale), PredicateUtil.has(itemScorpionScale))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemNightmareScale), PredicateUtil.has(itemNightmareScale))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> bigBerthaGuardRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemEnderDragon, Item itemNightmareSword, Item itemVortexEye, Item itemMothScale, Item itemBascaliscScale, Item itemScorpionScale, Item itemNightmareScale) {
        return (resultItemSup) -> bigBerthaGuardRecipe(recipeConsumer, itemEnderDragon, itemNightmareSword, itemVortexEye, itemMothScale, itemBascaliscScale, itemScorpionScale, itemNightmareScale, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> bigBerthaBladeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemWormTooth, I itemVexEye, I itemTrexTooth, I itemUltimateSword, I itemKrakenTooth, I itemCatakillerJaw, I itemViperTounge, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('W', itemWormTooth)
                .define('V', itemVexEye)
                .define('T', itemTrexTooth)
                .define('U', itemUltimateSword)
                .define('K', itemKrakenTooth)
                .define('C', itemCatakillerJaw)
                .define('I', itemViperTounge)
                .pattern(" WV")
                .pattern("TUK")
                .pattern("CI ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemWormTooth), PredicateUtil.has(itemWormTooth))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemVexEye), PredicateUtil.has(itemVexEye))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTrexTooth), PredicateUtil.has(itemTrexTooth))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUltimateSword), PredicateUtil.has(itemUltimateSword))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemKrakenTooth), PredicateUtil.has(itemKrakenTooth))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemCatakillerJaw), PredicateUtil.has(itemCatakillerJaw))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemViperTounge), PredicateUtil.has(itemViperTounge))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> bigBerthaBladeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemWormTooth, Item itemVexEye, Item itemTrexTooth, Item itemUltimateSword, Item itemKrakenTooth, Item itemCatakillerJaw, Item itemViperTounge) {
        return (resultItemSup) -> bigBerthaBladeRecipe(recipeConsumer, itemWormTooth, itemVexEye, itemTrexTooth, itemUltimateSword, itemKrakenTooth, itemCatakillerJaw, itemViperTounge, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Big Weapons
    // BattleAxe
    public static <I extends Item> Consumer<Supplier<I>> battleAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemUltAxe, I itemRedstoneBlock, I itemTriffidGoo, I itemUltSword, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('A', itemUltAxe)
                .define('B', itemRedstoneBlock)
                .define('C', itemTriffidGoo)
                .define('D', itemUltSword)
                .pattern("ABA")
                .pattern(" C ")
                .pattern(" D ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUltAxe), PredicateUtil.has(itemUltAxe))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemRedstoneBlock), PredicateUtil.has(itemRedstoneBlock))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTriffidGoo), PredicateUtil.has(itemTriffidGoo))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemUltSword), PredicateUtil.has(itemUltSword))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> battleAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemUltAxe, Item itemRedstoneBlock, Item itemTriffidGoo, Item itemUltSword) {
        return (resultItemSup) -> battleAxeRecipe(recipeConsumer, itemUltAxe, itemRedstoneBlock, itemTriffidGoo, itemUltSword, 1).accept((Supplier<Item>) resultItemSup);
    }

    // General shapes
    public static <I extends Item> Consumer<Supplier<I>> crossDotRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemDot, I itemCross, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('C', itemCross)
                .define('D', itemDot)
                .pattern(" C ")
                .pattern("CDC")
                .pattern(" C ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemDot), PredicateUtil.has(itemDot))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemCross), PredicateUtil.has(itemCross))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> crossDotRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemCross, Item itemDot) {
        return (resultItemSup) -> crossDotRecipe(recipeConsumer, itemCross, itemDot, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> crossTwoDotRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemDot, I itemTopBotCross, I itemSideCross, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('T', itemTopBotCross)
                .define('S', itemSideCross)
                .define('D', itemDot)
                .pattern(" T ")
                .pattern("SDS")
                .pattern(" T ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemDot), PredicateUtil.has(itemDot))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTopBotCross), PredicateUtil.has(itemTopBotCross))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemSideCross), PredicateUtil.has(itemSideCross))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> crossTwoDotRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemTopBotCross, Item itemSideCross, Item itemDot) {
        return (resultItemSup) -> crossTwoDotRecipe(recipeConsumer, itemTopBotCross, itemSideCross, itemDot, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> dotRingRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemRing, I itemDot, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('R', itemRing)
                .define('D', itemDot)
                .pattern("RRR")
                .pattern("RDR")
                .pattern("RRR")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemRing), PredicateUtil.has(itemRing))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemDot), PredicateUtil.has(itemDot))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> dotRingRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemRing, Item itemDot) {
        return (resultItemSup) -> dotRingRecipe(recipeConsumer, itemRing, itemDot, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> threeRowRecipe(Consumer<FinishedRecipe> recipeConsumer, I topItemReference, I middleItemReference, I bottomItemReference, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('T', topItemReference)
                .define('M', middleItemReference)
                .define('B', bottomItemReference)
                .pattern("TTT")
                .pattern("MMM")
                .pattern("BBB")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(topItemReference), PredicateUtil.has(topItemReference))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(middleItemReference), PredicateUtil.has(middleItemReference))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(bottomItemReference), PredicateUtil.has(bottomItemReference))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> threeRowRecipe(Consumer<FinishedRecipe> recipeConsumer, Item topItemReference, Item middleItemReference, Item bottomItemReference) {
        return (resultItemSup) -> threeRowRecipe(recipeConsumer, topItemReference, middleItemReference, bottomItemReference, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> diagonalLeftRecipe(Consumer<FinishedRecipe> recipeConsumer, I topItemReference, I middleItemReference, I bottomItemReference, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('T', topItemReference)
                .define('M', middleItemReference)
                .define('B', bottomItemReference)
                .pattern("  T")
                .pattern(" M ")
                .pattern("B  ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(topItemReference), PredicateUtil.has(topItemReference))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(middleItemReference), PredicateUtil.has(middleItemReference))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(bottomItemReference), PredicateUtil.has(bottomItemReference))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> diagonalLeftRecipe(Consumer<FinishedRecipe> recipeConsumer, Item topItemReference, Item middleItemReference, Item bottomItemReference) {
        return (resultItemSup) -> diagonalLeftRecipe(recipeConsumer, topItemReference, middleItemReference, bottomItemReference, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Fossiles
    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilCrystal(Consumer<FinishedRecipe> finishedRecipe, ItemLike bucketItemLike, ItemLike powerChipItemLike, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return parentItemLikeSup -> {
            B parentItemLike = parentItemLikeSup.get();
            ResourceLocation parentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(parentItemLike);

            ItemLike resultItemLike = resultComponentMapper.apply(parentItemLike);

            if (resultItemLike != null) {
                ResourceLocation resultItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(resultItemLike);
                ResourceLocation baseRecipeId = recipeIdMapper.apply(resultItemLikeId);

                Ingredient fossilIngredient = Ingredient.of(parentItemLike);
                Ingredient bucketIngredient = Ingredient.of(bucketItemLike);
                Ingredient powerChipIngredient = Ingredient.of(powerChipItemLike);

                DefossilizingRecipeBuilder.crystalDefossilizing(bucketIngredient, fossilIngredient, powerChipIngredient, resultItemLike.asItem(), 200, 1.0F)
                        .unlockedBy("has_" + parentItemLikeId.getPath(), PredicateUtil.has(parentItemLike))
                        .save(finishedRecipe, (new ResourceLocation(parentItemLikeId.getNamespace(), baseRecipeId.getPath())).withSuffix("_from_defossilizing_" + parentItemLikeId.getPath()));
            }
        };
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWaterCrystal(Consumer<FinishedRecipe> finishedRecipe, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilCrystal(finishedRecipe, Items.WATER_BUCKET, CAItems.ALUMINUM_POWER_CHIP.get(), resultComponentMapper, recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWaterCrystal(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilWaterCrystal(finishedRecipe, findSpawnEgg(), recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWaterCrystal(Consumer<FinishedRecipe> finishedRecipe) {
        return spawnEggFromFossilWaterCrystal(finishedRecipe, Function.identity());
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLavaCrystal(Consumer<FinishedRecipe> finishedRecipe, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilCrystal(finishedRecipe, Items.LAVA_BUCKET, CAItems.ALUMINUM_POWER_CHIP.get(), resultComponentMapper, recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLavaCrystal(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilLavaCrystal(finishedRecipe, findSpawnEgg(), recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLavaCrystal(Consumer<FinishedRecipe> finishedRecipe) {
        return spawnEggFromFossilLavaCrystal(finishedRecipe, Function.identity());
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilIron(Consumer<FinishedRecipe> finishedRecipe, ItemLike bucketItemLike, ItemLike powerChipItemLike, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return parentItemLikeSup -> {
            B parentItemLike = parentItemLikeSup.get();
            ResourceLocation parentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(parentItemLike);

            ItemLike resultItemLike = resultComponentMapper.apply(parentItemLike);

            if (resultItemLike != null) {
                ResourceLocation resultItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(resultItemLike);
                ResourceLocation baseRecipeId = recipeIdMapper.apply(resultItemLikeId);

                Ingredient fossilIngredient = Ingredient.of(parentItemLike);
                Ingredient bucketIngredient = Ingredient.of(bucketItemLike);
                Ingredient powerChipIngredient = Ingredient.of(powerChipItemLike);

                DefossilizingRecipeBuilder.ironDefossilizing(bucketIngredient, fossilIngredient, powerChipIngredient, resultItemLike.asItem(), 200, 1.0F)
                        .unlockedBy("has_" + parentItemLikeId.getPath(), PredicateUtil.has(parentItemLike))
                        .save(finishedRecipe, (new ResourceLocation(parentItemLikeId.getNamespace(), baseRecipeId.getPath())).withSuffix("_from_defossilizing_" + parentItemLikeId.getPath()));
            }
        };
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWaterIron(Consumer<FinishedRecipe> finishedRecipe, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilIron(finishedRecipe, Items.WATER_BUCKET, CAItems.ALUMINUM_POWER_CHIP.get(), resultComponentMapper, recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWaterIron(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilWaterIron(finishedRecipe, findSpawnEgg(), recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWaterIron(Consumer<FinishedRecipe> finishedRecipe) {
        return spawnEggFromFossilWaterIron(finishedRecipe, Function.identity());
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLavaIron(Consumer<FinishedRecipe> finishedRecipe, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilIron(finishedRecipe, Items.LAVA_BUCKET, CAItems.ALUMINUM_POWER_CHIP.get(), resultComponentMapper, recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLavaIron(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilLavaIron(finishedRecipe, findSpawnEgg(), recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLavaIron(Consumer<FinishedRecipe> finishedRecipe) {
        return spawnEggFromFossilLavaIron(finishedRecipe, Function.identity());
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWater(Consumer<FinishedRecipe> finishedRecipe) {
        return parentItemLikeSup -> {
            spawnEggFromFossilWaterIron(finishedRecipe).accept((Supplier<Block>) parentItemLikeSup);
            spawnEggFromFossilWaterCrystal(finishedRecipe).accept((Supplier<Block>) parentItemLikeSup);
        };
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLava(Consumer<FinishedRecipe> finishedRecipe) {
        return parentItemLikeSup -> {
            spawnEggFromFossilLavaIron(finishedRecipe).accept((Supplier<Block>) parentItemLikeSup);
            spawnEggFromFossilLavaCrystal(finishedRecipe).accept((Supplier<Block>) parentItemLikeSup);
        };
    }

    // FOOD
    public static <I extends Item> Consumer<Supplier<I>> BLTRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemBread, I itemLetuce, I itemTomato, I itemBacon, I itemButter, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', itemBread)
                .define('L', itemLetuce)
                .define('T', itemTomato)
                .define('A', itemBacon)
                .define('R', itemButter)
                .pattern("RB ")
                .pattern("LAT")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBread), PredicateUtil.has(itemBread))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemLetuce), PredicateUtil.has(itemLetuce))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTomato), PredicateUtil.has(itemTomato))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBacon), PredicateUtil.has(itemBacon))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemButter), PredicateUtil.has(itemButter))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> BLTRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemBread, Item itemLetuce, Item itemTomato, Item itemBacon, Item itemButter) {
        return (resultItemSup) -> BLTRecipe(recipeConsumer, itemBread, itemLetuce, itemTomato, itemBacon, itemButter, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> gardenSaladRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemBowl, I itemLetuce, I itemTomato, I itemCorn, I itemRadish, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', itemBowl)
                .define('L', itemLetuce)
                .define('T', itemTomato)
                .define('C', itemCorn)
                .define('R', itemRadish)
                .pattern(" R ")
                .pattern("LTC")
                .pattern(" B ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBowl), PredicateUtil.has(itemBowl))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemLetuce), PredicateUtil.has(itemLetuce))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTomato), PredicateUtil.has(itemTomato))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemCorn), PredicateUtil.has(itemCorn))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemRadish), PredicateUtil.has(itemRadish))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> gardenSaladRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemBread, Item itemLetuce, Item itemTomato, Item itemCorn, Item itemRadish) {
        return (resultItemSup) -> gardenSaladRecipe(recipeConsumer, itemBread, itemLetuce, itemTomato, itemCorn, itemRadish, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> seafoodPattyRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemBread, I itemLetuce, Item itemCrab, Item itemTomato, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', itemBread)
                .define('L', itemLetuce)
                .define('C', itemCrab)
                .define('T', itemTomato)
                .pattern(" B ")
                .pattern("LCT")
                .pattern(" B ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBread), PredicateUtil.has(itemBread))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemLetuce), PredicateUtil.has(itemLetuce))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemCrab), PredicateUtil.has(itemCrab))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTomato), PredicateUtil.has(itemTomato))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> seafoodPattyRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemBread, Item itemLetuce, Item itemCrab, Item itemTomato) {
        return (resultItemSup) -> seafoodPattyRecipe(recipeConsumer, itemBread, itemLetuce, itemCrab, itemTomato, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> radishStewRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemBowl, I itemRadish, Item itemPotato, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', itemBowl)
                .define('R', itemRadish)
                .define('P', itemPotato)
                .pattern(" R ")
                .pattern("RPR")
                .pattern(" B ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBowl), PredicateUtil.has(itemBowl))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemRadish), PredicateUtil.has(itemRadish))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemPotato), PredicateUtil.has(itemPotato))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> radishStewRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemBowl, Item itemRadish, Item itemPotato) {
        return (resultItemSup) -> radishStewRecipe(recipeConsumer, itemBowl, itemRadish, itemPotato, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> quinoaSaladRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemBowl, I itemLetuce, Item itemQuinoa, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', itemBowl)
                .define('L', itemLetuce)
                .define('Q', itemQuinoa)
                .pattern(" Q ")
                .pattern("QLQ")
                .pattern(" B ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBowl), PredicateUtil.has(itemBowl))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemLetuce), PredicateUtil.has(itemLetuce))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemQuinoa), PredicateUtil.has(itemQuinoa))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> quinoaSaladRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemBowl, Item itemLetuce, Item itemQuinoa) {
        return (resultItemSup) -> quinoaSaladRecipe(recipeConsumer, itemBowl, itemLetuce, itemQuinoa, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Popcorn Bag
    public static <I extends Item> Consumer<Supplier<I>> popcornBagRecipe(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return parentItemLikeSup -> {
            I parentItemLike = parentItemLikeSup.get();
            ResourceLocation parentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(parentItemLike);
            String paperName = Items.PAPER.builtInRegistryHolder().key().location().getPath();
            String redDyeName = Items.RED_DYE.builtInRegistryHolder().key().location().getPath();

            ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, parentItemLike, 3)
                    .define('P', Items.PAPER)
                    .define('R', Items.RED_DYE)
                    .pattern("PPP")
                    .pattern("PRR")
                    .pattern("RRR")
                    .unlockedBy("has_" + paperName + "_and_" + redDyeName, PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(Items.PAPER, Items.RED_DYE).build()))
                    .save(finishedRecipe, recipeIdMapper.apply(parentItemLikeId));
        };
    }

    public static <I extends Item> Consumer<Supplier<I>> popcornBagRecipe(Consumer<FinishedRecipe> finishedRecipe) {
        return popcornBagRecipe(finishedRecipe, Function.identity());
    }

    public static <I extends Item> Consumer<Supplier<I>> popcornRecipe(Consumer<FinishedRecipe> finishedRecipe,  Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return RecipeUtil.cookedFoodFromSmelting(finishedRecipe, parentPopcorn -> (I) CAItems.CORN.get(), recipeIdMapper);
    }

    public static <I extends Item> Consumer<Supplier<I>> popcornRecipe(Consumer<FinishedRecipe> finishedRecipe) {
        return popcornRecipe(finishedRecipe, Function.identity());
    }

    public static <I extends Item> Consumer<Supplier<I>> baggedPopcornRecipeFrom(Consumer<FinishedRecipe> finishedRecipe, Function<I, I> recipeComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper, boolean fromEmptyBag) {
        return parentItemLikeSup -> {
            I parentItemLike = parentItemLikeSup.get();
            ResourceLocation parentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(parentItemLike);
            I componentItemLike = recipeComponentMapper.apply(parentItemLike);

            if (componentItemLike != null) {
                ResourceLocation componentItemLikeId = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(componentItemLike);

                ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, parentItemLike)
                        .define('P', fromEmptyBag ? CAItems.EMPTY_POPCORN_BAG.get() : CAItems.POPCORN_BAG.get())
                        .define('C', componentItemLike)
                        .pattern("PCC")
                        .pattern("CC ")
                        .unlockedBy("has_" + componentItemLikeId.getPath(), PredicateUtil.has(componentItemLike))
                        .save(finishedRecipe, recipeIdMapper.apply(parentItemLikeId).withSuffix("_from_" + componentItemLikeId.getPath()));
            }
        };
    }

    public static <I extends Item> Consumer<Supplier<I>> baggedPopcornRecipeFrom(Consumer<FinishedRecipe> finishedRecipe, Function<I, I> recipeComponentMapper, boolean fromEmptyBag) {
        return baggedPopcornRecipeFrom(finishedRecipe, recipeComponentMapper, Function.identity(), fromEmptyBag);
    }

    public static <I extends Item> Consumer<Supplier<I>> baggedPopcornRecipe(Consumer<FinishedRecipe> finishedRecipe) {
        return baggedPopcornRecipeFrom(finishedRecipe, parentBaggedPopcorn -> (I) CAItems.POPCORN.get(), true);
    }

    public static <I extends Item> Consumer<Supplier<I>> baggedSaltedPopcornRecipe(Consumer<FinishedRecipe> finishedRecipe) {
        return baggedPopcornRecipeFrom(finishedRecipe, parentBaggedPopcorn -> (I) CAItems.SALT.get(), false);
    }

    public static <I extends Item> Consumer<Supplier<I>> baggedButteredPopcornRecipe(Consumer<FinishedRecipe> finishedRecipe) {
        return baggedPopcornRecipeFrom(finishedRecipe, parentBaggedPopcorn -> (I) CAItems.BUTTER.get(), false);
    }

    private static <B extends Block> Function<B, ItemLike> findSpawnEgg() {
        return parentBlock -> {
            AtomicReference<Item> foundSpawnEgg = new AtomicReference<>();
            AtomicBoolean found = new AtomicBoolean(false);
            BiFunction<ResourceLocation, Boolean, ResourceLocation> itemMapper = (parentFossilBlockId, onlyVanilla) -> {
                String path = parentFossilBlockId.getPath();
                boolean isMulticharSuffix = false;

                for (String curSuffix : io.github.chaosawakens.util.StringUtil.MULTICHAR_FOSSIL_SUFFIXES) {
                    if (path.endsWith(curSuffix)) {
                        path = path.substring(0, path.indexOf(curSuffix));
                        isMulticharSuffix = true;
                    }
                }

                return new ResourceLocation(onlyVanilla ? "minecraft" : parentFossilBlockId.getNamespace(), (isMulticharSuffix ? path : StringUtil.subLastToken(path)).substring(path.indexOf('_') + 1).concat("_spawn_egg"));
            };

            foundSpawnEgg.set(RegistryUtil.getObjectFrom(parentBlock.asItem(), parentFossilBlockId -> itemMapper.apply(parentFossilBlockId, true)).orElse(null));

            found.set(foundSpawnEgg.get() != null); // Short-circuit if found in MC

            if (!found.get()) {
                NexusServices.PLATFORM_MANAGER.getModData().forEach(loadedMod -> {
                    if (found.get()) return;

                    foundSpawnEgg.set(RegistryUtil.getObjectFrom(parentBlock.asItem(), parentFossilBlockId -> itemMapper.apply(parentFossilBlockId, false)).orElse(null));
                    found.set(foundSpawnEgg.get() != null);
                });
            }

            return foundSpawnEgg.get();
        };
    }

    public static <B extends Block> Consumer<Supplier<B>> crystalBlockRecipeFrom(Consumer<FinishedRecipe> finishedRecipe) {
        return parentItemLikeSup -> RecipeUtil.materialBlockFrom(finishedRecipe, parentBlock -> RegistryUtil.getObjectFrom(parentBlock.asItem(), parentBlockId -> RegistryUtil.pickMaterialId(parentBlock::asItem, "_crystal")).orElse(null), Function.identity()).accept((Supplier<Block>) parentItemLikeSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> lumpMaterialRecipe(Consumer<FinishedRecipe> finishedRecipe) {
        return parentItemLikeSup -> RecipeUtil.materialFromBlock(finishedRecipe, parentLump -> BuiltInRegistries.BLOCK.getOptional(DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(parentLump).withPath(p -> p.replace("_lump", "_block"))).orElse(null), Function.identity()).accept((Supplier<Item>) parentItemLikeSup);
    }
}


// template
/*
    public static <I extends Item> Consumer<Supplier<I>> nameRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemA, I itemB, I itemC, I itemD, I itemE, I itemF, I itemG, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('A', itemA)
                .define('B', itemB)
                .define('C', itemC)
                .define('D', itemD)
                .define('E', itemE)
                .define('F', itemF)
                .define('G', itemG)
                .pattern("   ")
                .pattern("   ")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemA), PredicateUtil.has(itemA))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemB), PredicateUtil.has(itemB))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemC), PredicateUtil.has(itemC))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemD), PredicateUtil.has(itemD))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemE), PredicateUtil.has(itemE))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemF), PredicateUtil.has(itemF))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemG), PredicateUtil.has(itemG))
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> nameRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemA, Item itemB, Item itemC, Item itemD, Item itemE, Item itemF, Item itemG) {
        return (resultItemSup) -> nameRecipe(recipeConsumer, itemA, itemB, itemC, itemD, itemE, itemF, itemG, 1).accept((Supplier<Item>) resultItemSup);
    }
*/