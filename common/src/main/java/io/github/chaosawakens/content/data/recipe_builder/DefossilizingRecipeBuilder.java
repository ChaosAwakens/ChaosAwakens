package io.github.chaosawakens.content.data.recipe_builder;

import com.google.gson.JsonObject;
import io.github.chaosawakens.content.data.recipe.defossilizing.AbstractDefossilizingRecipe;
import io.github.chaosawakens.content.registry.CARecipeData;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Consumer;

public class DefossilizingRecipeBuilder implements RecipeBuilder {
    protected final AbstractDefossilizingRecipe.DefossilizationCategory category;
    protected final Ingredient bucketIngredient;
    protected final Ingredient fossilizedIngredient;
    protected final Ingredient powerChipIngredient;
    protected final Item defossilizedResult;
    protected final int defossilizationTime;
    protected final float experience;
    protected final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    protected final RecipeSerializer<? extends AbstractDefossilizingRecipe> serializer;
    @Nullable
    protected String group;

    protected DefossilizingRecipeBuilder(AbstractDefossilizingRecipe.DefossilizationCategory category, Ingredient bucketIngredient, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, Item defossilizedResult, int defossilizationTime, float experience, RecipeSerializer<? extends AbstractDefossilizingRecipe> serializer) {
        this.category = category;
        this.bucketIngredient = bucketIngredient;
        this.fossilizedIngredient = fossilizedIngredient;
        this.powerChipIngredient = powerChipIngredient;
        this.defossilizedResult = defossilizedResult;
        this.defossilizationTime = defossilizationTime;
        this.experience = experience;
        this.serializer = serializer;
    }

    public static DefossilizingRecipeBuilder defossilizing(Ingredient bucketIngredient, AbstractDefossilizingRecipe.DefossilizationCategory category, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, Item defossilizedResult, int defossilizationTime, float experience, RecipeSerializer<? extends AbstractDefossilizingRecipe> serializer) {
        return new DefossilizingRecipeBuilder(category, bucketIngredient, fossilizedIngredient, powerChipIngredient, defossilizedResult, defossilizationTime, experience, serializer);
    }

    public static DefossilizingRecipeBuilder defossilizing(Ingredient bucketIngredient, AbstractDefossilizingRecipe.DefossilizationCategory category, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, Item defossilizedResult, RecipeSerializer<? extends AbstractDefossilizingRecipe> serializer) {
        return defossilizing(bucketIngredient, category, fossilizedIngredient, powerChipIngredient, defossilizedResult, 200, 1.0F, serializer);
    }

    public static DefossilizingRecipeBuilder ironDefossilizing(Ingredient bucketIngredient, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, Item defossilizedResult, int defossilizationTime, float experience) {
        return defossilizing(bucketIngredient, AbstractDefossilizingRecipe.DefossilizationCategory.IRON, fossilizedIngredient, powerChipIngredient, defossilizedResult, defossilizationTime, experience, CARecipeData.CARecipeSerializers.IRON_DEFOSSILIZING.get());
    }

    public static DefossilizingRecipeBuilder ironDefossilizing(Ingredient bucketIngredient, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, Item defossilizedResult) {
        return ironDefossilizing(bucketIngredient, fossilizedIngredient, powerChipIngredient, defossilizedResult, 200, 1.0F);
    }

    public static DefossilizingRecipeBuilder crystalDefossilizing(Ingredient bucketIngredient, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, Item defossilizedResult, int defossilizationTime, float experience) {
        return defossilizing(bucketIngredient, AbstractDefossilizingRecipe.DefossilizationCategory.CRYSTAL, fossilizedIngredient, powerChipIngredient, defossilizedResult, defossilizationTime, experience, CARecipeData.CARecipeSerializers.CRYSTAL_DEFOSSILIZING.get());
    }

    public static DefossilizingRecipeBuilder crystalDefossilizing(Ingredient bucketIngredient, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, Item defossilizedResult) {
        return crystalDefossilizing(bucketIngredient, fossilizedIngredient, powerChipIngredient, defossilizedResult, 200, 1.0F);
    }

    @Override
    public @NotNull DefossilizingRecipeBuilder unlockedBy(String criterionKey, CriterionTriggerInstance criterionTriggerInstance) {
        this.advancement.addCriterion(criterionKey, criterionTriggerInstance);
        return this;
    }

    @Override
    public @NotNull DefossilizingRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return defossilizedResult;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        validate(recipeId);

        consumer.accept(new DefossilizingResult(recipeId.withPrefix("defossilizing/" + category.name().toLowerCase(Locale.ROOT) + "/"), pickGroup(group), category, bucketIngredient, fossilizedIngredient, powerChipIngredient, defossilizedResult, defossilizationTime, experience, advancement, recipeId.withPrefix("recipes/defossilizing/" + category.name().toLowerCase(Locale.ROOT) + "/"), serializer));
    }

    protected void validate(ResourceLocation recipeId) {
        if (advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException(String.format("No way of obtaining recipe %s", recipeId));
        }
    }

    protected String pickGroup(String group) {
        return group == null ? "" : group;
    }

    public record DefossilizingResult(ResourceLocation id, String group,
                                      AbstractDefossilizingRecipe.DefossilizationCategory category,
                                      Ingredient bucketIngredient, Ingredient fossilizedIngredient,
                                      Ingredient powerChipIngredient, Item defossilizedResult, int defossilizationTime,
                                      float experience, Advancement.Builder advancement, ResourceLocation advancementId,
                                      RecipeSerializer<? extends AbstractDefossilizingRecipe> serializer) implements FinishedRecipe {

        @Override
        public void serializeRecipeData(JsonObject serializedRecipeObj) {
            if (!this.group.isEmpty()) {
                serializedRecipeObj.addProperty("group", this.group);
            }

            serializedRecipeObj.addProperty("category", this.category.name().toLowerCase(Locale.ROOT));
            serializedRecipeObj.add("bucket_ingredient", this.bucketIngredient.toJson());
            serializedRecipeObj.add("fossilized_ingredient", this.fossilizedIngredient.toJson());
            serializedRecipeObj.add("power_chip_ingredient", this.powerChipIngredient.toJson());
            serializedRecipeObj.addProperty("result", BuiltInRegistries.ITEM.getKey(this.defossilizedResult).toString());
            serializedRecipeObj.addProperty("experience", this.experience);
            serializedRecipeObj.addProperty("defossilizing_time", this.defossilizationTime);
        }

        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return serializer;
        }

        @Override
        public @NotNull JsonObject serializeAdvancement() {
            return advancement.serializeToJson();
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return advancementId;
        }
    }
}

