package io.github.chaosawakens.util;

import io.github.chaosawakens.CAConstants;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Utility class containing helper methods (generally also found in datagen classes) aimed at reducing boilerplate code by providing
 * common {@link RecipeBuilder} patterns.
 */
public final class RecipeUtil { //TODO Frick u method overloading functional interfaces

    private RecipeUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    public static Consumer<Supplier<Block>> materialToBlock(Consumer<FinishedRecipe> recipeConsumer, ItemLike materialItem) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get())
                .define('M', materialItem)
                .pattern("MMM")
                .pattern("MMM")
                .pattern("MMM")
                .unlockedBy("has_" + RegistryUtil.getItemName(materialItem), PredicateUtil.has(materialItem))
                .save(recipeConsumer, CAConstants.prefix(RegistryUtil.getItemName(materialItem) + "_to_" + RegistryUtil.getItemName(resultBlockSup.get())));
    }

    public static Consumer<Supplier<Block>> logToPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike logILReference) {
        return (resultBlockSup) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 4)
                .requires(logILReference)
                .unlockedBy("has_" + RegistryUtil.getItemName(logILReference), PredicateUtil.has(logILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> logToPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> {
            Supplier<Block> logBlock = RegistryUtil.getLogFrom(resultBlockSup);

            if (logBlock != null) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 4)
                        .requires(logBlock.get())
                        .unlockedBy("has_" + RegistryUtil.getItemName(logBlock.get()), PredicateUtil.has(logBlock.get()))
                        .save(recipeConsumer);
            }
        };
    }

    public static Consumer<Supplier<Block>> logToWood(Consumer<FinishedRecipe> recipeConsumer, ItemLike logILReference) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 3)
                .define('L', logILReference)
                .pattern("LL")
                .pattern("LL")
                .unlockedBy("has_" + RegistryUtil.getItemName(logILReference), PredicateUtil.has(logILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> logToWood(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> {
            Supplier<Block> logBlock = RegistryUtil.getLogFrom(resultBlockSup);

            if (logBlock != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 3)
                        .define('L', logBlock.get())
                        .pattern("LL")
                        .pattern("LL")
                        .unlockedBy("has_" + RegistryUtil.getItemName(logBlock.get()), PredicateUtil.has(logBlock.get()))
                        .save(recipeConsumer);
            }
        };
    }

    public static Consumer<Supplier<Block>> stairsFromPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planksILReference) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 4)
                .define('P', planksILReference)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .unlockedBy("has_" + RegistryUtil.getItemName(planksILReference), PredicateUtil.has(planksILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> stairsFromPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> {
            Supplier<Block> planksBlock = RegistryUtil.getPlanksFrom(resultBlockSup);

            if (planksBlock != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 4)
                        .define('P', planksBlock.get())
                        .pattern("P  ")
                        .pattern("PP ")
                        .pattern("PPP")
                        .unlockedBy("has_" + RegistryUtil.getItemName(planksBlock.get()), PredicateUtil.has(planksBlock.get()))
                        .save(recipeConsumer);
            }
        };
    }
}
