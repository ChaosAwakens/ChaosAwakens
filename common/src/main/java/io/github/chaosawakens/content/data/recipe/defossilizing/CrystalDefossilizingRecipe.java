package io.github.chaosawakens.content.data.recipe.defossilizing;

import io.github.chaosawakens.content.block_entity.defossilizer.AbstractDefossilizerBlockEntity;
import io.github.chaosawakens.content.registry.CARecipeData;
import io.github.chaosawakens.content.registry.CATags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class CrystalDefossilizingRecipe extends AbstractDefossilizingRecipe {

    public CrystalDefossilizingRecipe(ResourceLocation recipeId, Ingredient bucketIngredient, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, ItemStack defossilizedResult, int defossilizationTime, float experience) {
        super(recipeId, DefossilizationCategory.CRYSTAL, bucketIngredient, fossilizedIngredient, powerChipIngredient, defossilizedResult, defossilizationTime, experience);
    }

    @Override
    public Predicate<ItemStack> getBucketStackPredicate() {
        return getBucketIngredient().and(bucketStack -> AbstractDefossilizerBlockEntity.isBucketComponent(bucketStack) && bucketStack.is(CATags.CAItemTags.CRYSTAL_DEFOSSILIZER_BUCKETS.get()));
    }

    @Override
    public Predicate<ItemStack> getFossilizedStackPredicate() {
        return getFossilizedIngredient().and(fossilStack -> AbstractDefossilizerBlockEntity.isFossil(fossilStack) && fossilStack.is(CATags.CAItemTags.FOSSILS.get()));
    }

    @Override
    public Predicate<ItemStack> getPowerChipStackPredicate() {
        return getPowerChipIngredient().and(AbstractDefossilizerBlockEntity::isPowerChipComponent);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CARecipeData.CARecipeSerializers.CRYSTAL_DEFOSSILIZING.get();
    }
}
