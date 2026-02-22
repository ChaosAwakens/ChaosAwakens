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

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossil(Consumer<FinishedRecipe> finishedRecipe, ItemLike bucketItemLike, ItemLike powerChipItemLike, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
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

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWater(Consumer<FinishedRecipe> finishedRecipe, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossil(finishedRecipe, Items.WATER_BUCKET, CAItems.ALUMINUM_POWER_CHIP.get(), resultComponentMapper, recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWater(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilWater(finishedRecipe, findSpawnEgg(), recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilWater(Consumer<FinishedRecipe> finishedRecipe) {
        return spawnEggFromFossilWater(finishedRecipe, Function.identity());
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLava(Consumer<FinishedRecipe> finishedRecipe, Function<B, ItemLike> resultComponentMapper, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossil(finishedRecipe, Items.LAVA_BUCKET, CAItems.ALUMINUM_POWER_CHIP.get(), resultComponentMapper, recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLava(Consumer<FinishedRecipe> finishedRecipe, Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return spawnEggFromFossilLava(finishedRecipe, findSpawnEgg(), recipeIdMapper);
    }

    public static <B extends Block> Consumer<Supplier<B>> spawnEggFromFossilLava(Consumer<FinishedRecipe> finishedRecipe) {
        return spawnEggFromFossilLava(finishedRecipe, Function.identity());
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
}
