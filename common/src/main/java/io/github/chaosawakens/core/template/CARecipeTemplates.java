package io.github.chaosawakens.core.template;

import com.mememan.nexus.platform.NexusServices;
import com.mememan.nexus.property_wrapper.base.generic.DataGenPropertyWrapper;
import com.mememan.nexus.util.PredicateUtil;
import com.mememan.nexus.util.RecipeUtil;
import com.mememan.nexus.util.RegistryUtil;
import com.mememan.nexus.util.StringUtil;
import io.github.chaosawakens.content.data.recipe_builder.DefossilizingRecipeBuilder;
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

    //
    //
    //

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
