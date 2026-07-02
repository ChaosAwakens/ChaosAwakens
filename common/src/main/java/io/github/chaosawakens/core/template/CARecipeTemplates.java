package io.github.chaosawakens.core.template;

import com.mememan.nexus.asm.annotations.DatagenRegistrarEntry;
import com.mememan.nexus.platform.NexusServices;
import com.mememan.nexus.property_wrapper.base.generic.DataGenPropertyWrapper;
import com.mememan.nexus.util.PredicateUtil;
import com.mememan.nexus.util.RecipeUtil;
import com.mememan.nexus.util.RegistryUtil;
import com.mememan.nexus.util.StringUtil;
import com.mojang.datafixers.optics.Optic;
import io.github.chaosawakens.content.data.recipe_builder.DefossilizingRecipeBuilder;
import io.github.chaosawakens.content.registry.CAItems;
import io.github.chaosawakens.content.registry.CATags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.tags.TagKey;
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

import static io.github.chaosawakens.content.registry.CAItems.*;
import static io.github.chaosawakens.content.registry.CATags.CAItemTags.SPOON;

public final class CARecipeTemplates {
    // item predicate names for unlocked
    private static final ResourceLocation URANIUM_NAME = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(URANIUM_INGOT.get());
    private static final ResourceLocation TITANIUM_NAME = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(TITANIUM_INGOT.get());
    private static final ResourceLocation PLATINUM_NAME = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get());

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

    // Overloaded Ultimate Tools
    public static <I extends Item> Consumer<Supplier<I>> ultSwordRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern(" T ")
                .pattern(" U ")
                .pattern(" P ")
                .unlockedBy(
                        "has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultSwordRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultSwordRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultPickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("TUT")
                .pattern(" U ")
                .pattern(" P ")
                .unlockedBy(
                        "has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultPickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultPickaxeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("TT ")
                .pattern("UP ")
                .pattern(" P ")
                .unlockedBy(
                        "has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultAxeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultAxeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultShovelRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern(" U ")
                .pattern(" T ")
                .pattern(" P ")
                .unlockedBy("has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultShovelRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultShovelRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultHoeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("TU ")
                .pattern(" P ")
                .pattern(" P ")
                .unlockedBy("has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultHoeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultHoeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultBowRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation bowName = Items.BOW.builtInRegistryHolder().key().location();
        ResourceLocation stringName = Items.BOW.builtInRegistryHolder().key().location();
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .define('B', Items.BOW)
                .define('S', Items.STRING)
                .pattern(" TS")
                .pattern("PBS")
                .pattern(" US")
                .unlockedBy("has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME + "_and_" + bowName + "_and_" + stringName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get(), Items.BOW, Items.STRING).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultBowRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultBowRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultCrossBowRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation crossbowName = Items.CROSSBOW.builtInRegistryHolder().key().location();
        ResourceLocation stringName = Items.STRING.builtInRegistryHolder().key().location();
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .define('B', Items.CROSSBOW)
                .define('S', Items.STRING)
                .pattern("TST")
                .pattern("UBU")
                .pattern(" P ")
                .unlockedBy("has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME + "_and_" + crossbowName + "_and_" + stringName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get(), Items.CROSSBOW, Items.STRING).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultCrossBowRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultCrossBowRecipe(recipeConsumer,1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultFishRodRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation stringName = Items.STRING.builtInRegistryHolder().key().location();
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .define('S', Items.STRING)
                .pattern("  T")
                .pattern(" US")
                .pattern("P S")
                .unlockedBy("has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME + "_and_" + stringName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get(), Items.STRING).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultFishRodRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultFishRodRecipe(recipeConsumer,1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultBoltRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation sunstoneName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(SUNSTONE.get());
        ResourceLocation featherName = Items.FEATHER.builtInRegistryHolder().key().location();
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('S', SUNSTONE.get())
                .define('U', URANIUM_NUGGET.get())
                .define('T', TITANIUM_NUGGET.get())
                .define('P', PLATINUM_LUMP.get())
                .define('F', Items.FEATHER)
                .pattern("ST ")
                .pattern("TPU")
                .pattern(" UF")
                .unlockedBy("has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME + "_and_" + sunstoneName + "_and_" + featherName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultBoltRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultBoltRecipe(recipeConsumer,8).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> irukandjiArrowRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation deadIrukandjiName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(DEAD_IRUKANDJI.get());
        ResourceLocation crystalStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(CRYSTAL_STICK.get());
        ResourceLocation peacockFeatherName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PEACOCK_FEATHER.get());
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('I', DEAD_IRUKANDJI.get())
                .define('C', CRYSTAL_STICK.get())
                .define('P', PEACOCK_FEATHER.get())
                .pattern("  I")
                .pattern(" C ")
                .pattern("P  ")
                .unlockedBy("has_" + deadIrukandjiName + "_and_" + crystalStickName + "_and_" + peacockFeatherName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(DEAD_IRUKANDJI.get(), CRYSTAL_STICK.get(), PEACOCK_FEATHER.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> irukandjiArrowRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> irukandjiArrowRecipe(recipeConsumer,8).accept((Supplier<Item>) resultItemSup);
    }

    // Tools
    public static <I extends Item> Consumer<Supplier<I>> swordRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" M ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> swordRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> swordRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> pickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> pickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> pickaxeRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> axeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern("MS ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> axeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> axeRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> shovelRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> shovelRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> shovelRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> hoeRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> hoeRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial, Item itemStick) {
        return (resultItemSup) -> hoeRecipe(recipeConsumer, itemMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Overloaded Kyanite Tools
    public static <I extends Item, B extends Block> Consumer<Supplier<I>> swordRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" M ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemBlockMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> swordRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> swordRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> pickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemBlockMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> pickaxeRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> pickaxeRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> axeRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern("MS ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemBlockMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> axeRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> axeRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> shovelRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern(" M ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemBlockMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> shovelRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> shovelRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item, B extends Block> Consumer<Supplier<I>> hoeRecipe(Consumer<FinishedRecipe> recipeConsumer, B itemBlockMaterial, I itemStick, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBlockMaterial);
        ResourceLocation itemStickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemStick);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('M', itemBlockMaterial)
                .define('S', itemStick)
                .pattern("MM ")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_" + itemMaterialName + "_has_" + itemStickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemBlockMaterial, itemStick).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> hoeRecipe(Consumer<FinishedRecipe> recipeConsumer, Block itemBlockMaterial, Item itemStick) {
        return (resultItemSup) -> hoeRecipe(recipeConsumer, itemBlockMaterial, itemStick, 1).accept((Supplier<Item>) resultItemSup);
    }

    // ThunderStaff Recipe stuff
    public static <I extends Item> Consumer<Supplier<I>> thunderStaffRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation rubyName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RUBY.get());
        ResourceLocation diamondName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.DIAMOND);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('R', RUBY.get())
                .define('D', Items.DIAMOND)
                .pattern(" RD")
                .pattern(" RR")
                .pattern("R  ")
                .unlockedBy("has_" + rubyName + "_has_" + diamondName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(RUBY.get(), Items.DIAMOND).build())
                )
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
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("TPT")
                .pattern("U U")
                .pattern("   ")
                .unlockedBy(
                        "has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultHelmetRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultHelmetRecipe(recipeConsumer,1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultChestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("P P")
                .pattern("TTT")
                .pattern("UUU")
                .unlockedBy(
                        "has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultChestplateRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultChestplateRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultLeggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("PPP")
                .pattern("T T")
                .pattern("U U")
                .unlockedBy(
                        "has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get(), PLATINUM_LUMP.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultLeggingsRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultLeggingsRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> ultBootsRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('U', URANIUM_INGOT.get())
                .define('T', TITANIUM_INGOT.get())
                .pattern("T T")
                .pattern("U U")
                .pattern("   ")
                .unlockedBy(
                        "has_" + URANIUM_NAME + "_and_" + TITANIUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(URANIUM_INGOT.get(), TITANIUM_INGOT.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultBootsRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultBootsRecipe(recipeConsumer,1).accept((Supplier<Item>) resultItemSup);
    }

    // Armour
    public static <I extends Item> Consumer<Supplier<I>> helmetRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("MMM")
                .pattern("M M")
                .pattern("   ")
                .unlockedBy(
                        "has_" + itemMaterialName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> helmetRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> helmetRecipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> chestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .unlockedBy(
                        "has_" + itemMaterialName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> chestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> chestplateRecipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> leggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .unlockedBy(
                        "has_" + itemMaterialName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> leggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> leggingsRecipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> bootsRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemMaterial, int resultItemCount) {
        ResourceLocation itemMaterialName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMaterial);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('M', itemMaterial)
                .pattern("M M")
                .pattern("M M")
                .pattern("   ")
                .unlockedBy(
                        "has_" + itemMaterialName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemMaterial).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> bootsRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemMaterial) {
        return (resultItemSup) -> bootsRecipe(recipeConsumer, itemMaterial, 1).accept((Supplier<Item>) resultItemSup);
    }

    //Prismatic Reaper
/*
    public static <I extends Item> Consumer<Supplier<I>> prismaticReaperRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation kunziteName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(KUNZITE.get());
        ResourceLocation rubyName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RUBY.get());
        ResourceLocation diamondName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.DIAMOND);
        ResourceLocation emeraldName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.EMERALD);
        ResourceLocation stickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.STICK);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('K', KUNZITE.get())
                .define('R', RUBY.get())
                .define('D', Items.DIAMOND)
                .define('E', Items.EMERALD)
                .define('S', Items.STICK)
                .pattern("KDE")
                .pattern("RS ")
                .pattern("S  ")
                .unlockedBy(
                        "has_" + kunziteName + "_and_" + rubyName + "_and_" +  diamondName + "_and_" + emeraldName + "_and_" + stickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(KUNZITE.get(), RUBY.get(), Items.DIAMOND, Items.EMERALD, Items.STICK).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> prismaticReaperRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> prismaticReaperRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }
*/
    // Nightmare Sword
    public static <I extends Item> Consumer<Supplier<I>> nightmareSwordRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation nightmareScaleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(NIGHTMARE_SCALE.get());
        ResourceLocation diamondName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.DIAMOND);
        ResourceLocation redstoneName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.REDSTONE);
        ResourceLocation platinumLumpName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get());
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('N', NIGHTMARE_SCALE.get())
                .define('D', Items.DIAMOND)
                .define('T', TITANIUM_INGOT.get())
                .define('R', Items.REDSTONE)
                .define('P', PLATINUM_LUMP.get())
                .pattern("NDN")
                .pattern("RTR")
                .pattern("NPN")
                .unlockedBy(
                        "has_" + nightmareScaleName + "_and_" + diamondName + "_and_" +  TITANIUM_NAME + "_and_" + redstoneName + "_and_" + platinumLumpName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(NIGHTMARE_SCALE.get(), Items.DIAMOND, TITANIUM_INGOT.get(), Items.REDSTONE, Items.IRON_INGOT).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> nightmareSwordRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> nightmareSwordRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Big Bertha Parts
/*
    public static <I extends Item> Consumer<Supplier<I>> bigBerthaHandelRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation jefferyCoreName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(JEFFERY_CORE.get());
        ResourceLocation bigHammerName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(BIG_HAMMER.get());
        ResourceLocation mantisClawName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(MANTIS_CLAW.get());
        ResourceLocation waterDragonScaleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(WATER_DRAGON_SCALE.get());
        ResourceLocation triffidGooName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(TRIFFID_GOO.get());

        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('C', JEFFERY_CORE.get())
                .define('H', BIG_HAMMER.get())
                .define('M', MANTIS_CLAW.get())
                .define('W', WATER_DRAGON_SCALE.get())
                .define('G', TRIFFID_GOO.get())
                .pattern("  W")
                .pattern("MG ")
                .pattern("CH ")
                .unlockedBy(
                        "has_" + jefferyCoreName + "_and_" + bigHammerName + "_and_" +  mantisClawName + "_and_" + waterDragonScaleName + "_and_" + triffidGooName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(JEFFERY_CORE.get(), BIG_HAMMER.get(), MANTIS_CLAW.get(), WATER_DRAGON_SCALE.get(), TRIFFID_GOO.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> bigBerthaHandelRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> bigBerthaHandelRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> bigBerthaGuardRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation enderDragonScaleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(ENDER_DRAGON_SCALE.get());
        ResourceLocation nightmareSwordName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(NIGHTMARE_SWORD.get());
        ResourceLocation vortexEyeName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(VORTEX_EYE.get());
        ResourceLocation mothScaleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(MOTH_SCALE.get());
        ResourceLocation basiliskScaleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(BASILISK_SCALE.get());
        ResourceLocation emperorScorpionScaleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(EMPEROR_SCORPION_SCALE.get());
        ResourceLocation nightmareScaleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(NIGHTMARE_SCALE.get());

        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('E', ENDER_DRAGON_SCALE.get())
                .define('S', NIGHTMARE_SWORD.get())
                .define('V', VORTEX_EYE.get())
                .define('O', MOTH_SCALE.get())
                .define('B', BASILISK_SCALE.get())
                .define('C', EMPEROR_SCORPION_SCALE.get())
                .define('N', NIGHTMARE_SCALE.get())
                .pattern("O B")
                .pattern("EV ")
                .pattern("CSN")
                .unlockedBy(
                        "has_" + enderDragonScaleName + "_and_" + nightmareSwordName + "_and_" +  vortexEyeName + "_and_" + mothScaleName + "_and_" + basiliskScaleName + "_and_" + emperorScorpionScaleName + "_and_" + nightmareScaleName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(ENDER_DRAGON_SCALE.get(), NIGHTMARE_SWORD.get(), VORTEX_EYE.get(), MOTH_SCALE.get(), BASILISK_SCALE.get(), EMPEROR_SCORPION_SCALE.get(), NIGHTMARE_SCALE.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> bigBerthaGuardRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> bigBerthaGuardRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> bigBerthaBladeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation wormToothName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(WORM_TOOTH.get());
        ResourceLocation waspStingerName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(WASP_STINGER.get());
        ResourceLocation seaViperName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(SEA_VIPER_TONGUE.get());
        ResourceLocation ultimateSwordName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(ULTIMATE_SWORD.get());
        ResourceLocation ratSwordName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RAT_SWORD.get());
        ResourceLocation poisonSwordName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(POISON_SWORD.get());
        ResourceLocation fairySwordName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(FAIRY_SWORD.get());

        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('W', WORM_TOOTH.get())
                .define('V', WASP_STINGER.get())
                .define('T', SEA_VIPER_TONGUE.get())
                .define('U', ULTIMATE_SWORD.get())
                .define('K', RAT_SWORD.get())
                .define('C', POISON_SWORD.get())
                .define('I', FAIRY_SWORD.get())
                .pattern(" WV")
                .pattern("TUK")
                .pattern("CI ")
                .unlockedBy(
                        "has_" + wormToothName + "_and_" + waspStingerName + "_and_" +  seaViperName + "_and_" + ultimateSwordName + "_and_" + ratSwordName + "_and_" + poisonSwordName + "_and_" + fairySwordName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(WORM_TOOTH.get(), WASP_STINGER.get(), SEA_VIPER_TONGUE.get(), ULTIMATE_SWORD.get(), RAT_SWORD.get(), POISON_SWORD.get(), FAIRY_SWORD.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> bigBerthaBladeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> bigBerthaBladeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }
*/
    // Big Weapons
    // BattleAxe
    public static <I extends Item> Consumer<Supplier<I>> battleAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation ultimateAxeName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(ULTIMATE_AXE.get());
        ResourceLocation rubyName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RUBY.get());
        ResourceLocation triffidGooName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(TRIFFID_GOO.get());
        ResourceLocation stickName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.STICK);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('A', ULTIMATE_AXE.get())
                .define('R', RUBY.get())
                .define('T', TRIFFID_GOO.get())
                .define('S', Items.STICK)
                .pattern(" AT")
                .pattern(" RA")
                .pattern("S  ")
                .unlockedBy(
                        "has_" + ultimateAxeName + "_and_" + rubyName + "_and_" +  triffidGooName + "_and_" + stickName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(ULTIMATE_AXE.get(), RUBY.get(), TRIFFID_GOO.get(), Items.STICK).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> battleAxeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> battleAxeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> queenBattleAxeRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation battleAxeName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(BATTLE_AXE.get());
        ResourceLocation queenBattleAxeName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(QUEEN_SCALE.get());
        ResourceLocation platinumLumpName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(PLATINUM_LUMP.get());
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItemSup.get(), resultItemCount)
                .define('A', BATTLE_AXE.get())
                .define('Q', QUEEN_SCALE.get())
                .define('P', PLATINUM_LUMP.get())
                .pattern("QAQ")
                .pattern("QPQ")
                .pattern(" P ")
                .unlockedBy(
                        "has_" + battleAxeName + "_and_" + queenBattleAxeName + "_and_" +  platinumLumpName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(ULTIMATE_AXE.get(), RUBY.get(), TRIFFID_GOO.get(), Items.STICK).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> queenBattleAxeRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> queenBattleAxeRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }
    // General shapes
    public static <I extends Item> Consumer<Supplier<I>> foodOnStickRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemFood, int resultItemCount) {
        ResourceLocation itemFoodName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemFood);
        ResourceLocation fishingRodName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.FISHING_ROD);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, resultItemSup.get(), resultItemCount)
                .define('F', itemFood)
                .define('R', Items.FISHING_ROD)
                .pattern("R  ")
                .pattern(" F ")
                .pattern("   ")
                .unlockedBy(
                        "has_" + itemFoodName + "_and_" + fishingRodName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(ULTIMATE_AXE.get(), Items.REDSTONE_BLOCK, TRIFFID_GOO.get(), ULTIMATE_SWORD.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> foodOnStickRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemFood) {
        return (resultItemSup) -> foodOnStickRecipe(recipeConsumer, itemFood, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> crossDotRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemDot, I itemCross, int resultItemCount) {
        ResourceLocation itemCrossName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemCross);
        ResourceLocation itemDotName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemDot);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('C', itemCross)
                .define('D', itemDot)
                .pattern(" C ")
                .pattern("CDC")
                .pattern(" C ")
                .unlockedBy(
                        "has_" + itemCrossName + "_and_" + itemDotName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(ULTIMATE_AXE.get(), Items.REDSTONE_BLOCK, TRIFFID_GOO.get(), ULTIMATE_SWORD.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> crossDotRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemCross, Item itemDot) {
        return (resultItemSup) -> crossDotRecipe(recipeConsumer, itemCross, itemDot, 1).accept((Supplier<Item>) resultItemSup);
    }

    // Ultimate Apple Recipe
    public static <I extends Item, T extends TagKey<Item>> Consumer<Supplier<I>> ultAppleRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation appleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.ENCHANTED_GOLDEN_APPLE);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('T', TITANIUM_INGOT.get())
                .define('U', URANIUM_INGOT.get())
                .define('P', PLATINUM_LUMP.get())
                .define('A', Items.ENCHANTED_GOLDEN_APPLE)
                .pattern("UPT")
                .pattern("PAP")
                .pattern("TPU")
                .unlockedBy(
                        "has_" + appleName + "_and_" + TITANIUM_NAME + "_and_" + URANIUM_NAME + "_and_" + PLATINUM_NAME,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(TITANIUM_INGOT.get(), URANIUM_INGOT.get(), PLATINUM_LUMP.get()).of(Items.ENCHANTED_GOLDEN_APPLE).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> ultAppleRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> ultAppleRecipe(recipeConsumer,  1).accept((Supplier<Item>) resultItemSup);
    }

    // Slayer Chainsaw
    public static <I extends Item> Consumer<Supplier<I>> slayerChainSawRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation rubyName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RUBY.get());
        ResourceLocation ultimateAxeName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(ULTIMATE_AXE.get());
        ResourceLocation redstoneBlockName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.REDSTONE_BLOCK);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('R', RUBY.get())
                .define('A', ULTIMATE_AXE.get())
                .define('S', Items.REDSTONE_BLOCK)
                .pattern("RRR")
                .pattern("SAR")
                .pattern("SSR")
                .unlockedBy(
                        "has_" + rubyName + "_and_" + ultimateAxeName + "_and_" + redstoneBlockName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(RUBY.get(), ULTIMATE_AXE.get(), Items.REDSTONE_BLOCK).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> slayerChainSawRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> slayerChainSawRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> dotRingRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemRing, I itemDot, int resultItemCount) {
        ResourceLocation itemRingName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemRing);
        ResourceLocation itemDotName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemDot);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('R', itemRing)
                .define('D', itemDot)
                .pattern("RRR")
                .pattern("RDR")
                .pattern("RRR")
                .unlockedBy(
                        "has_" + itemRingName + "_and_" + itemDotName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemRing, itemDot).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> dotRingRecipe(Consumer<FinishedRecipe> recipeConsumer, Item itemRing, Item itemDot) {
        return (resultItemSup) -> dotRingRecipe(recipeConsumer, itemRing, itemDot, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> threeRowRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemTop, I itemMiddle, I itemBottom, int resultItemCount) {
        ResourceLocation itemTopName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTop);
        ResourceLocation itemMiddleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMiddle);
        ResourceLocation itemBottomName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBottom);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('T', itemTop)
                .define('M', itemMiddle)
                .define('B', itemBottom)
                .pattern("TTT")
                .pattern("MMM")
                .pattern("BBB")
                .unlockedBy(
                        "has_" + itemTopName + "_and_" + itemMiddleName + "_and_" + itemBottomName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemTop, itemMiddle, itemBottom).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> threeRowRecipe(Consumer<FinishedRecipe> recipeConsumer, Item topItemReference, Item middleItemReference, Item bottomItemReference) {
        return (resultItemSup) -> threeRowRecipe(recipeConsumer, topItemReference, middleItemReference, bottomItemReference, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> diagonalLeftRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemTop, I itemMiddle, I itemBottom, int resultItemCount) {
        ResourceLocation itemTopName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemTop);
        ResourceLocation itemMiddleName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemMiddle);
        ResourceLocation itemBottomName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemBottom);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('T', itemTop)
                .define('M', itemMiddle)
                .define('B', itemBottom)
                .pattern("  T")
                .pattern(" M ")
                .pattern("B  ")
                .unlockedBy(
                        "has_" + itemTopName + "_and_" + itemMiddleName + "_and_" + itemBottomName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(itemTop, itemMiddle, itemBottom).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> diagonalLeftRecipe(Consumer<FinishedRecipe> recipeConsumer, Item topItemReference, Item middleItemReference, Item bottomItemReference) {
        return (resultItemSup) -> diagonalLeftRecipe(recipeConsumer, topItemReference, middleItemReference, bottomItemReference, 1).accept((Supplier<Item>) resultItemSup);
    }

    // ender eye and pearl blocks
    public static <I extends Item, B extends Block> Consumer<Supplier<B>> twoByTwoRecipe(Consumer<FinishedRecipe> recipeConsumer, I itemReferenced, int resultItemCount) {
        ResourceLocation itemReferencedName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(itemReferenced);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
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
        ResourceLocation breadName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BREAD);
        ResourceLocation lettuceName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(LETTUCE.get());
        ResourceLocation tomatoName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(TOMATO.get());
        ResourceLocation cookedBaconName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(COOKED_BACON.get());
        ResourceLocation butterName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(BUTTER.get());
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultItemSup.get(), resultItemCount)
                .define('B', Items.BREAD)
                .define('L', LETTUCE.get())
                .define('T', TOMATO.get())
                .define('A', COOKED_BACON.get())
                .define('R', BUTTER.get())
                .pattern("RB ")
                .pattern("LAT")
                .pattern("   ")
                .unlockedBy(
                        "has_" + breadName + "_and_" + lettuceName + "_and_" + tomatoName + "_and_" + cookedBaconName + "_and_" + butterName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(Items.BREAD, LETTUCE.get(), TOMATO.get(), COOKED_BACON.get(), BUTTER.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> BLTRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> BLTRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> gardenSaladRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation breadName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BREAD);
        ResourceLocation lettuceName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(LETTUCE.get());
        ResourceLocation tomatoName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(TOMATO.get());
        ResourceLocation cookedBaconName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(COOKED_BACON.get());
        ResourceLocation butterName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(BUTTER.get());
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultItemSup.get(), resultItemCount)
                .define('B', Items.BOWL)
                .define('L', LETTUCE.get())
                .define('T', TOMATO.get())
                .define('C', CORN.get())
                .define('R', RADISH.get())
                .pattern(" R ")
                .pattern("LTC")
                .pattern(" B ")
                .unlockedBy(
                        "has_" + breadName + "_and_" + lettuceName + "_and_" + tomatoName + "_and_" + cookedBaconName + "_and_" + butterName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(Items.BREAD, LETTUCE.get(), TOMATO.get(), COOKED_BACON.get(), BUTTER.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> gardenSaladRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> gardenSaladRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> seafoodPattyRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation breadName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BREAD);
        ResourceLocation lettuceName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(LETTUCE.get());
        ResourceLocation tomatoName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(TOMATO.get());
        ResourceLocation cookedCrabName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(COOKED_CRAB_MEAT.get());
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultItemSup.get(), resultItemCount)
                .define('B', Items.BREAD)
                .define('L', LETTUCE.get())
                .define('C', COOKED_CRAB_MEAT.get())
                .define('T', TOMATO.get())
                .pattern(" B ")
                .pattern("LCT")
                .pattern(" B ")
                .unlockedBy(
                        "has_" + breadName + "_and_" + lettuceName + "_and_" + tomatoName + "_and_" + cookedCrabName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(Items.BREAD, LETTUCE.get(), TOMATO.get(), COOKED_CRAB_MEAT.get(), BUTTER.get()).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> seafoodPattyRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> seafoodPattyRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> radishStewRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation bowlName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BOWL);
        ResourceLocation radishName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RADISH.get());
        ResourceLocation potatoName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.POTATO);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultItemSup.get(), resultItemCount)
                .define('B', Items.BOWL)
                .define('R', RADISH.get())
                .define('P', Items.POTATO)
                .pattern(" R ")
                .pattern("RPR")
                .pattern(" B ")
                .unlockedBy(
                        "has_" + bowlName + "_and_" + radishName + "_and_" + potatoName,
                    PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(Items.BOWL, RADISH.get(), Items.POTATO).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> radishStewRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> radishStewRecipe(recipeConsumer, 1).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> quinoaSaladRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation bowlName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BOWL);
        ResourceLocation lettuceName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(LETTUCE.get());
        ResourceLocation quinoaName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(QUINOA.get());
        ResourceLocation beetrootName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.BEETROOT);
        ResourceLocation radishName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(RADISH.get());
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, resultItemSup.get(), resultItemCount)
                .define('O', Items.BOWL)
                .define('L', LETTUCE.get())
                .define('Q', QUINOA.get())
                .define('B', Items.BEETROOT)
                .define('R', RADISH.get())
                .pattern(" Q ")
                .pattern("RLB")
                .pattern(" O ")
                .unlockedBy(
                        "has_" + bowlName + "_and_" + lettuceName + "_and_" + quinoaName + "_and_" + beetrootName + "_and_" + radishName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(Items.BOWL, RADISH.get(), Items.POTATO).build())
                )
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
        ResourceLocation saltName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(SALT.get());
        ResourceLocation milkBucketName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.MILK_BUCKET);
        return (resultItemSup) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, resultItemSup.get(), resultItemCount)
                .requires(SALT.get())
                .requires(Items.MILK_BUCKET)
                .unlockedBy(
                        "has_" + saltName + "_and_" + milkBucketName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(SALT.get(), Items.MILK_BUCKET).build())
                )
                .save(recipeConsumer);
    }

    public static <I extends Item> Consumer<Supplier<I>> butterRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> butterRecipe(recipeConsumer, 4).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> cheeseRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation milkBucketName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.MILK_BUCKET);
        return (resultItemSup) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, resultItemSup.get(), resultItemCount)
                .requires(Items.MILK_BUCKET)
                .unlockedBy(
                        "has_" + milkBucketName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(Items.BOWL, Items.MILK_BUCKET).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> cheeseRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> cheeseRecipe(recipeConsumer, 4).accept((Supplier<Item>) resultItemSup);
    }

    public static <I extends Item> Consumer<Supplier<I>> powerChipRecipe(Consumer<FinishedRecipe> recipeConsumer, int resultItemCount) {
        ResourceLocation aluminiumIngotName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(ALUMINUM_INGOT.get());
        ResourceLocation ironIngotName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.IRON_INGOT);
        ResourceLocation redstoneName = DataGenPropertyWrapper.RegistryLookupContainer.getObjectRegistryIdOrThrow(Items.REDSTONE);
        return (resultItemSup) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItemSup.get(), resultItemCount)
                .define('A', ALUMINUM_INGOT.get())
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .pattern("ARA")
                .pattern("RIR")
                .pattern("ARA")
                .unlockedBy(
                        "has_" + aluminiumIngotName + "_and_" + ironIngotName + "_and_" + redstoneName,
                        PredicateUtil.inventoryTrigger(ItemPredicate.Builder.item().of(ALUMINUM_INGOT.get(), Items.IRON_INGOT, Items.REDSTONE).build())
                )
                .save(recipeConsumer);
    }
    public static <I extends Item> Consumer<Supplier<I>> powerChipRecipe(Consumer<FinishedRecipe> recipeConsumer) {
        return (resultItemSup) -> powerChipRecipe(recipeConsumer, 3).accept((Supplier<Item>) resultItemSup);
    }
}