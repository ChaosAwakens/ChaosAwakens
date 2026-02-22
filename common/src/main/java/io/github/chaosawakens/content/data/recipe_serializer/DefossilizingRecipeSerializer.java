package io.github.chaosawakens.content.data.recipe_serializer;

import com.google.gson.JsonObject;
import io.github.chaosawakens.content.data.recipe.defossilizing.AbstractDefossilizingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public record DefossilizingRecipeSerializer<ADR extends AbstractDefossilizingRecipe>(FossilCache<ADR> fossilCache, int defaultDefossilizationTime) implements RecipeSerializer<ADR> {

    @Override
    public @NotNull ADR fromJson(ResourceLocation recipeId, JsonObject recipeJson) {
        Ingredient bucketIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(recipeJson, "bucket_ingredient"));
        Ingredient fossilizedIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(recipeJson, "fossilized_ingredient"));
        Ingredient powerChipIngredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(recipeJson, "power_chip_ingredient"));

        ResourceLocation resultStackId = new ResourceLocation(GsonHelper.getAsString(recipeJson, "result"));
        ItemStack resultStack = new ItemStack(BuiltInRegistries.ITEM.getOptional(resultStackId).orElseThrow(() -> new IllegalStateException(String.format("Item '%s' does not exist", resultStackId))));
        float earnedXp = GsonHelper.getAsFloat(recipeJson, "experience", 0.0F);
        int defossilizationTime = GsonHelper.getAsInt(recipeJson, "defossilizing_time", this.defaultDefossilizationTime);

        return this.fossilCache.create(recipeId, bucketIngredient, fossilizedIngredient, powerChipIngredient, resultStack, defossilizationTime, earnedXp);
    }

    @Override
    public @NotNull ADR fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
        Ingredient bucketIngredient = Ingredient.fromNetwork(buf);
        Ingredient fossilizedIngredient = Ingredient.fromNetwork(buf);
        Ingredient powerChipIngredient = Ingredient.fromNetwork(buf);
        ItemStack defossilizedResult = buf.readItem();
        int defossilizationTime = buf.readVarInt();
        float experience = buf.readFloat();

        return this.fossilCache.create(recipeId, bucketIngredient, fossilizedIngredient, powerChipIngredient, defossilizedResult, defossilizationTime, experience);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ADR recipeObject) {
        recipeObject.getBucketIngredient().toNetwork(buf);
        recipeObject.getFossilizedIngredient().toNetwork(buf);
        recipeObject.getPowerChipIngredient().toNetwork(buf);

        buf.writeItem(recipeObject.getDefossilizedResult());
        buf.writeVarInt(recipeObject.getDefossilizationTime());
        buf.writeFloat(recipeObject.getExperience());
    }

    @FunctionalInterface
    public interface FossilCache<ADR extends AbstractDefossilizingRecipe> {

        ADR create(ResourceLocation recipeId, Ingredient bucketIngredient, Ingredient fossilizedIngredient, Ingredient powerChipIngredient, ItemStack defossilizedResult, int defossilizationTime, float experience);
    }
}
