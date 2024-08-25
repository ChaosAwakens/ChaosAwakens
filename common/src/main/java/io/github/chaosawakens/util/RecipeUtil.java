package io.github.chaosawakens.util;

import io.github.chaosawakens.CAConstants;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Utility class containing helper methods (generally also found in datagen classes) aimed at reducing boilerplate code by providing
 * common/redundant {@link RecipeBuilder} patterns.
 */
public final class RecipeUtil {

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
        return (resultBlockSup) -> logToPlanks(recipeConsumer, RegistryUtil.getLogFrom(resultBlockSup).get()).accept(resultBlockSup);
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
        return (resultBlockSup) -> logToWood(recipeConsumer, RegistryUtil.getLogFrom(resultBlockSup).get()).accept(resultBlockSup);
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
        return (resultBlockSup) -> stairsFromPlanks(recipeConsumer, RegistryUtil.getPlanksFrom(resultBlockSup).get()).accept(resultBlockSup);
    }

    public static Consumer<Supplier<Block>> doorsFromPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planksILReference) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 3)
                .define('P', planksILReference)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .unlockedBy("has_" + RegistryUtil.getItemName(planksILReference), PredicateUtil.has(planksILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> doorsFromPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> doorsFromPlanks(recipeConsumer, RegistryUtil.getPlanksFrom(resultBlockSup).get()).accept(resultBlockSup);
    }

    public static Consumer<Supplier<Block>> slabsFromPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planksILReference) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 6)
                .define('P', planksILReference)
                .pattern("PPP")
                .unlockedBy("has_" + RegistryUtil.getItemName(planksILReference), PredicateUtil.has(planksILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> slabsFromPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> slabsFromPlanks(recipeConsumer, RegistryUtil.getPlanksFrom(resultBlockSup).get()).accept(resultBlockSup);
    }

    public static Consumer<Supplier<Block>> buttonFromPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planksILReference) {
        return (resultBlockSup) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 1)
                .requires(planksILReference,1)
                .unlockedBy("has_" + RegistryUtil.getItemName(planksILReference), PredicateUtil.has(planksILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> buttonFromPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> buttonFromPlanks(recipeConsumer, RegistryUtil.getPlanksFrom(resultBlockSup).get()).accept(resultBlockSup);
    }

    public static Consumer<Supplier<Block>> trapdoorsFromPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planksILReference) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 2)
                .define('P', planksILReference)
                .pattern("PPP")
                .pattern("PPP")
                .unlockedBy("has_" + RegistryUtil.getItemName(planksILReference), PredicateUtil.has(planksILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> trapdoorsFromPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> trapdoorsFromPlanks(recipeConsumer, RegistryUtil.getPlanksFrom(resultBlockSup).get()).accept(resultBlockSup);
    }

    public static Consumer<Supplier<Block>> pressurePlateFromPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planksILReference) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 1)
                .define('P', planksILReference)
                .pattern("PP")
                .unlockedBy("has_" + RegistryUtil.getItemName(planksILReference), PredicateUtil.has(planksILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> pressurePlateFromPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> pressurePlateFromPlanks(recipeConsumer, RegistryUtil.getPlanksFrom(resultBlockSup).get()).accept(resultBlockSup);
    }

    public static Consumer<Supplier<Block>> fencesFromPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planksILReference) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 3)
                .define('P', planksILReference)
                .define('S', Items.STICK)
                .pattern("PSP")
                .pattern("PSP")
                .unlockedBy("has_" + RegistryUtil.getItemName(planksILReference), PredicateUtil.has(planksILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> fencesFromPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> fencesFromPlanks(recipeConsumer, RegistryUtil.getPlanksFrom(resultBlockSup).get()).accept(resultBlockSup);
    }

    public static Consumer<Supplier<Block>> fenceGateFromPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planksILReference) {
        return (resultBlockSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultBlockSup.get(), 1)
                .define('P', planksILReference)
                .define('S', Items.STICK)
                .pattern("SPS")
                .pattern("SPS")
                .unlockedBy("has_" + RegistryUtil.getItemName(planksILReference), PredicateUtil.has(planksILReference))
                .save(recipeConsumer);
    }

    public static Consumer<Supplier<Block>> fenceGateFromPlanks(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultBlockSup) -> fenceGateFromPlanks(recipeConsumer, RegistryUtil.getPlanksFrom(resultBlockSup).get()).accept(resultBlockSup);
    }

    public static Consumer<Supplier<Item>> cookedFoodFromSmelting(Consumer<FinishedRecipe> recipeConsumer, ItemLike rawFoodILReference) {
        return (resultItemSup) -> SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawFoodILReference), RecipeCategory.FOOD, resultItemSup.get(), 0.35F, 200)
                .group(CAConstants.MODID)
                .unlockedBy("has_" + RegistryUtil.getItemName(rawFoodILReference), PredicateUtil.has(rawFoodILReference))
                .save(recipeConsumer, CAConstants.prefix(RegistryUtil.getItemName(rawFoodILReference) + "_from_smelting"));
    }

    public static Consumer<Supplier<Item>> foodSmokingRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike rawFoodILReference) {
        return (resultItemSup) -> SimpleCookingRecipeBuilder.smoking(Ingredient.of(rawFoodILReference), RecipeCategory.FOOD, resultItemSup.get(), 0.35F, 100)
                .group(CAConstants.MODID)
                .unlockedBy("has_" + RegistryUtil.getItemName(rawFoodILReference), PredicateUtil.has(rawFoodILReference))
                .save(recipeConsumer, CAConstants.prefix(RegistryUtil.getItemName(rawFoodILReference) + "_from_smoking"));
    }

    public static Consumer<Supplier<Item>> foodCampfireSmokingRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike rawFoodILReference) {
        return (resultItemSup) -> SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(rawFoodILReference), RecipeCategory.FOOD, resultItemSup.get(), 0.35F, 600)
                .group(CAConstants.MODID)
                .unlockedBy("has_" + RegistryUtil.getItemName(rawFoodILReference), PredicateUtil.has(rawFoodILReference))
                .save(recipeConsumer, CAConstants.prefix(RegistryUtil.getItemName(rawFoodILReference) + "_from_campfire_cooking"));
    }

    public static Consumer<Supplier<Item>> cookedFood(Consumer<FinishedRecipe> recipeConsumer, ItemLike rawFoodILReference) {
        return (resultItemSup) -> {
            cookedFoodFromSmelting(recipeConsumer, rawFoodILReference).accept(resultItemSup);
            foodSmokingRecipe(recipeConsumer, rawFoodILReference).accept(resultItemSup);
            foodCampfireSmokingRecipe(recipeConsumer, rawFoodILReference).accept(resultItemSup);
        };
    }

    public static Consumer<Supplier<Item>> cookedFood(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> cookedFood(recipeConsumer, RegistryUtil.getFromCookedFood(resultItemSup).get()).accept(resultItemSup);
    }
}
