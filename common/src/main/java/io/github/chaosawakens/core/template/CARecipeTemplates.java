package io.github.chaosawakens.core.template;

import com.mememan.nexus.property_wrapper.base.generic.DataGenPropertyWrapper;
import com.mememan.nexus.util.PredicateUtil;
import com.mememan.nexus.util.RegistryUtil;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

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
}
