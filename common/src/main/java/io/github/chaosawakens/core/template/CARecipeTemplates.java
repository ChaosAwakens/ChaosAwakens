package io.github.chaosawakens.core.template;

import com.mememan.nexus.platform.NexusServices;
import com.mememan.nexus.property_wrapper.base.generic.DataGenPropertyWrapper;
import com.mememan.nexus.util.PredicateUtil;
import com.mememan.nexus.util.RecipeUtil;
import com.mememan.nexus.util.RegistryUtil;
import com.mememan.nexus.util.StringUtil;
import io.github.chaosawakens.content.data.recipe_builder.DefossilizingRecipeBuilder;
import io.github.chaosawakens.content.registry.CAItems;
import io.github.chaosawakens.content.registry.CATags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockCollisions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.chaosawakens.content.registry.CAItems.*;
import static io.github.chaosawakens.content.registry.CATags.CAItemTags.SPOON;

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
    public static <I extends Item> Consumer<Supplier<I>> ultSwordRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern(" T ")
                .pattern(" U ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get()), PredicateUtil.has(PLATINUM_LUMP.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultSwordRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultSwordRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultPickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("TUT")
                .pattern(" U ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get()), PredicateUtil.has(PLATINUM_LUMP.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultPickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultPickaxeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("TT ")
                .pattern("UP ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get()), PredicateUtil.has(PLATINUM_LUMP.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultAxeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultAxeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultShovelRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern(" U ")
                .pattern(" T ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(URANIUM_INGOT.get()), PredicateUtil.has(URANIUM_INGOT.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultShovelRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultShovelRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultHoeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("UU ")
                .pattern(" T ")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get()), PredicateUtil.has(PLATINUM_LUMP.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultHoeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultHoeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultBowRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .define('B', Items.BOW)
                .define('S', Items.STRING)
                .pattern(" TS")
                .pattern("PBS")
                .pattern(" US")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(URANIUM_INGOT.get()), PredicateUtil.has(URANIUM_INGOT.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultBowRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultBowRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultCrossBowRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .define('B', Items.CROSSBOW)
                .define('S', Items.STRING)
                .pattern("TST")
                .pattern("UBU")
                .pattern(" P ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(URANIUM_INGOT.get()), PredicateUtil.has(URANIUM_INGOT.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultCrossBowRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultCrossBowRecipe(recipeConsumer,1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultFishRodRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .define('S', Items.STRING)
                .pattern("  T")
                .pattern(" US")
                .pattern("P S")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(URANIUM_INGOT.get()), PredicateUtil.has(URANIUM_INGOT.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultFishRodRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultFishRodRecipe(recipeConsumer,1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultBoltRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('S', SUNSTONE.get())
                .define('U', URANIUM_NUGGET.get())
                .define('T', TITANIUM_NUGGET.get())
                .define('P', PLATINUM_LUMP.get())
                .define('F', Items.FEATHER)
                .pattern("ST ")
                .pattern("TPU")
                .pattern(" UF")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(SUNSTONE.get()), PredicateUtil.has(SUNSTONE.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultBoltRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultBoltRecipe(recipeConsumer,8).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> irukandjiArrowRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('I', DEAD_IRUKANDJI.get())
                .define('C', CRYSTAL_STICK.get())
                .define('P', PEACOCK_FEATHER.get())
                .pattern("  I")
                .pattern(" C ")
                .pattern("P  ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(DEAD_IRUKANDJI.get()), PredicateUtil.has(DEAD_IRUKANDJI.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> irukandjiArrowRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> irukandjiArrowRecipe(recipeConsumer,8).accept((Supplier<Item>) resultItemSup);
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

    // ThunderStaff Recipe stuff
    public static <I extends Item> Consumer<Supplier<I>> thunderStaffRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('R', RUBY.get())
                .define('D', Items.DIAMOND)
                .pattern(" RD")
                .pattern(" RR")
                .pattern("R  ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RUBY.get()), PredicateUtil.has(RUBY.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> thunderStaffRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> thunderStaffRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Overloaded Crystal Wood Tools
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
    public static <I extends Item> Consumer<Supplier<I>> ultHelmetRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("TPT")
                .pattern("U U")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get()), PredicateUtil.has(PLATINUM_LUMP.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultHelmetRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultHelmetRecipe(recipeConsumer,1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultChestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("P P")
                .pattern("TTT")
                .pattern("UUU")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get()), PredicateUtil.has(PLATINUM_LUMP.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultChestplateRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultChestplateRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultLeggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("TTT")
                .pattern("U U")
                .pattern("P P")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get()), PredicateUtil.has(PLATINUM_LUMP.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultLeggingsRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultLeggingsRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultBootsRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .pattern("T T")
                .pattern("U U")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(URANIUM_INGOT.get()), PredicateUtil.has(URANIUM_INGOT.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultBootsRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultBootsRecipe(recipeConsumer,1).accept((Supplier<Item>) resultItemSup);
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
    public static <I extends Item> Consumer<Supplier<I>> prismaticReaperRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('K', KUNZITE.get())
                .define('R', RUBY.get())
                .define('D', Items.DIAMOND)
                .define('E', Items.EMERALD)
                .define('S', Items.STICK)
                .pattern("KDE")
                .pattern("RS ")
                .pattern("S  ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RUBY.get()), PredicateUtil.has(RUBY.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> prismaticReaperRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> prismaticReaperRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Nightmare Sword
    public static <I extends Item> Consumer<Supplier<I>> nightmareSwordRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('N', NIGHTMARE_SCALE.get())
                .define('D', Items.DIAMOND)
                .define('T', TITANIUM_INGOT.get())
                .define('R', Items.REDSTONE)
                .define('I', Items.IRON_INGOT)
                .pattern("NDN")
                .pattern("RTR")
                .pattern("NIN")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(NIGHTMARE_SCALE.get()), PredicateUtil.has(NIGHTMARE_SCALE.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> nightmareSwordRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> nightmareSwordRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Big Bertha Parts
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

    public static <I extends Item> Consumer<Supplier<I>> bigBerthaGuardRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemEnderDragon, I itemNightmareSword, I itemVortexEye, I itemMothScale, I itemBasilisk, I itemScorpionScale, I itemNightmareScale, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('E', itemEnderDragon)
                .define('S', itemNightmareSword)
                .define('V', itemVortexEye)
                .define('O', itemMothScale)
                .define('B', itemBasilisk)
                .define('C', itemScorpionScale)
                .define('N', itemNightmareScale)
                .pattern("O B")
                .pattern("EV ")
                .pattern("CSN")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemEnderDragon), PredicateUtil.has(itemEnderDragon))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemNightmareSword), PredicateUtil.has(itemNightmareSword))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemVortexEye), PredicateUtil.has(itemVortexEye))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMothScale), PredicateUtil.has(itemMothScale))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBasilisk), PredicateUtil.has(itemBasilisk))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemScorpionScale), PredicateUtil.has(itemScorpionScale))
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemNightmareScale), PredicateUtil.has(itemNightmareScale))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> bigBerthaGuardRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemEnderDragon, Item itemNightmareSword, Item itemVortexEye, Item itemMothScale, Item itemBasiliskScale, Item itemScorpionScale, Item itemNightmareScale) {
        return (resultItemSup) -> bigBerthaGuardRecipe(recipeConsumer, itemEnderDragon, itemNightmareSword, itemVortexEye, itemMothScale, itemBasiliskScale, itemScorpionScale, itemNightmareScale, 1).accept((Supplier<Item>) resultItemSup);
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
    public static <I extends Item> Consumer<Supplier<I>> battleAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('A', ULTIMATE_AXE.get())
                .define('B', Items.REDSTONE_BLOCK)
                .define('C', TRIFFID_GOO.get())
                .define('D', ULTIMATE_SWORD.get())
                .pattern("ABA")
                .pattern(" C ")
                .pattern(" D ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(ULTIMATE_AXE.get()), PredicateUtil.has(ULTIMATE_AXE.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> battleAxeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> battleAxeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    // General shapes
    public static <I extends Item> Consumer<Supplier<I>> foodOnStickRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemFood, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('F', itemFood)
                .define('R', Items.FISHING_ROD)
                .pattern("R  ")
                .pattern(" F ")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemFood), PredicateUtil.has(itemFood))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> foodOnStickRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemFood) {
        return (resultItemSup) -> foodOnStickRecipe(recipeConsumer, itemFood, 1).accept((Supplier<Item>) resultItemSup);
    }

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

    // Ultimate Apple Recipe
    public static <I extends Item, T extends TagKey<Item>> Consumer<Supplier<I>> ultAppleRecipe(Consumer<FinishedRecipe> recipeConsumer, T itemDot, I itemTopBotCross, I itemSideCross, I itemCorner, I itemKorner, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('T', itemTopBotCross)
                .define('S', itemSideCross)
                .define('D', itemDot)
                .define('C', itemCorner)
                .define('K', itemKorner)
                .pattern("CTK")
                .pattern("SDS")
                .pattern("KTC")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTopBotCross), PredicateUtil.has(itemTopBotCross))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultAppleRecipe(Consumer<FinishedRecipe> recipeConsumer, TagKey<Item> itemDot, Item itemTopBotCross, Item itemSideCross, Item itemCorner, Item itemKorner) {
        return (resultItemSup) -> ultAppleRecipe(recipeConsumer, itemDot, itemTopBotCross, itemSideCross, itemCorner, itemKorner,  1).accept((Supplier<Item>) resultItemSup);
    }

    // Slayer Chainsaw
    public static <I extends Item> Consumer<Supplier<I>> slayerChainSawRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('R', RUBY.get())
                .define('A', ULTIMATE_AXE.get())
                .define('S', Items.REDSTONE_BLOCK)
                .pattern(" RR")
                .pattern("SAR")
                .pattern("SS ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(ULTIMATE_AXE.get()), PredicateUtil.has(ULTIMATE_AXE.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> slayerChainSawRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> slayerChainSawRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
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

    // ender eye and pearl blocks
    public static <I extends Item, B extends Block> Consumer<Supplier<B>> twoByTwoRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemReferenced, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('#', itemReferenced)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemReferenced), PredicateUtil.has(itemReferenced))
                .save(recipeConsumer);
    }
    public static <B extends Block> Consumer<Supplier<B>> twoByTwoRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemReferenced) {
        return (resultItemSup) -> twoByTwoRecipe(recipeConsumer, itemReferenced, 1).accept((Supplier<Block>) resultItemSup);
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
    public static <I extends Item> Consumer<Supplier<I>> BLTRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', Items.BREAD)
                .define('L', LETTUCE.get())
                .define('T', TOMATO.get())
                .define('A', COOKED_BACON.get())
                .define('R', BUTTER.get())
                .pattern("RB ")
                .pattern("LAT")
                .pattern("   ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BREAD), PredicateUtil.has(Items.BREAD))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> BLTRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> BLTRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> gardenSaladRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', Items.BOWL)
                .define('L', LETTUCE.get())
                .define('T', TOMATO.get())
                .define('C', CORN.get())
                .define('R', RADISH.get())
                .pattern(" R ")
                .pattern("LTC")
                .pattern(" B ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BOWL), PredicateUtil.has(Items.BOWL))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> gardenSaladRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> gardenSaladRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> seafoodPattyRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', Items.BREAD)
                .define('L', LETTUCE.get())
                .define('C', COOKED_CRAB_MEAT.get())
                .define('T', TOMATO.get())
                .pattern(" B ")
                .pattern("LCT")
                .pattern(" B ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BREAD), PredicateUtil.has(Items.BREAD))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> seafoodPattyRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> seafoodPattyRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> radishStewRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', Items.BOWL)
                .define('R', RADISH.get())
                .define('P', Items.POTATO)
                .pattern(" R ")
                .pattern("RPR")
                .pattern(" B ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BOWL), PredicateUtil.has(Items.BOWL))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> radishStewRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> radishStewRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> quinoaSaladRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', Items.BOWL)
                .define('L', LETTUCE.get())
                .define('Q', QUINOA.get())
                .pattern(" Q ")
                .pattern("QLQ")
                .pattern(" B ")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BOWL), PredicateUtil.has(Items.BOWL))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> quinoaSaladRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> quinoaSaladRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> goldenBakedPotatoRecipe(Consumer<FinishedRecipe> finishedRecipe,  Function<ResourceLocation, ResourceLocation> recipeIdMapper) {
        return RecipeUtil.cookedFoodFromSmelting(finishedRecipe, parentBakedGoldenPotato -> (I) GOLDEN_POTATO.get(), recipeIdMapper);
    }
    public static <I extends Item> Consumer<Supplier<I>> goldenBakedPotatoRecipe(Consumer<FinishedRecipe> finishedRecipe) {
        return goldenBakedPotatoRecipe(finishedRecipe, Function.identity());
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

    public static <I extends Item> Consumer<Supplier<I>> butterRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', Items.MILK_BUCKET)
                .define('S', Items.WOODEN_SHOVEL) // TODO: Needs damage modifing to given tool
                .pattern("S")
                .pattern("B")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.MILK_BUCKET), PredicateUtil.has(Items.MILK_BUCKET))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> butterRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> butterRecipe(recipeConsumer, 4).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> cheeseRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('B', Items.MILK_BUCKET)
                .define('W', Items.BOWL)
                .pattern("BWB")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.MILK_BUCKET), PredicateUtil.has(Items.MILK_BUCKET))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> cheeseRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> cheeseRecipe(recipeConsumer, 4).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> powerChipRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, resultItemSup.get(), resultItemCount)
                .define('A', ALUMINUM_INGOT.get())
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .pattern("ARA")
                .pattern("RIR")
                .pattern("ARA")
                .unlockedBy("has_" + DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(ALUMINUM_INGOT.get()), PredicateUtil.has(ALUMINUM_INGOT.get()))
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> powerChipRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> powerChipRecipe(recipeConsumer, 3).accept((Supplier<Item>) resultItemSup);
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