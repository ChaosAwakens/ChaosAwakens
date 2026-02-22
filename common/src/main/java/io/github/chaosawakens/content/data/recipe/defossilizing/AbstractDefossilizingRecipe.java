package io.github.chaosawakens.content.data.recipe.defossilizing;

import io.github.chaosawakens.content.block_entity.defossilizer.AbstractDefossilizerBlockEntity;
import io.github.chaosawakens.content.registry.CARecipeData;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public abstract class AbstractDefossilizingRecipe implements Recipe<Container> {
    protected final ResourceLocation recipeId;
    protected final DefossilizationCategory defossilizationCategory;
    protected final Ingredient bucketIngredient;
    protected final Ingredient fossilizedIngredient;
    protected final Ingredient powerChipIngredient;
    protected final ItemStack defossilizedResult;
    protected final int defossilizationTime;
    protected final float experience;

    public AbstractDefossilizingRecipe(ResourceLocation recipeId, DefossilizationCategory defossilizationCategory, Ingredient bucketIngredient, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, ItemStack defossilizedResult, int defossilizationTime, float experience) {
        this.recipeId = recipeId;
        this.defossilizationCategory = defossilizationCategory;
        this.bucketIngredient = bucketIngredient;
        this.fossilizedIngredient = fossilizedIngredient;
        this.powerChipIngredient = powerChipIngredient;
        this.defossilizedResult = defossilizedResult;
        this.defossilizationTime = defossilizationTime;
        this.experience = experience;
    }

    public DefossilizationCategory getDefossilizationCategory() {
        return defossilizationCategory;
    }

    public Ingredient getFossilizedIngredient() {
        return fossilizedIngredient;
    }

    public Ingredient getBucketIngredient() {
        return bucketIngredient;
    }

    public Ingredient getPowerChipIngredient() {
        return powerChipIngredient;
    }

    public ItemStack getDefossilizedResult() {
        return defossilizedResult;
    }

    public int getDefossilizationTime() {
        return defossilizationTime;
    }

    public float getExperience() {
        return experience;
    }

    public abstract Predicate<ItemStack> getBucketStackPredicate();
    public abstract Predicate<ItemStack> getFossilizedStackPredicate();
    public abstract Predicate<ItemStack> getPowerChipStackPredicate();

    @Override
    public @NotNull ResourceLocation getId() {
        return recipeId;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return getFossilizedStackPredicate().test(container.getItem(AbstractDefossilizerBlockEntity.FOSSIL_INPUT_SLOT_INDEX))
                || getBucketStackPredicate().test(container.getItem(AbstractDefossilizerBlockEntity.BUCKET_INPUT_SLOT_INDEX))
                || getPowerChipStackPredicate().test(container.getItem(AbstractDefossilizerBlockEntity.POWER_CHIP_INPUT_SLOT_INDEX));
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return getResultItem(registryAccess).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return defossilizedResult;
    }

    @Override
    public @NotNull RecipeType<? extends AbstractDefossilizingRecipe> getType() {
        return CARecipeData.CARecipeTypes.DEFOSSILIZING.get();
    }

    public enum DefossilizationCategory {
        IRON,
        CRYSTAL
    }
}
