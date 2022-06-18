package io.github.chaosawakens.common.crafting.recipe;

import com.google.gson.JsonObject;
import io.github.chaosawakens.common.registry.CARecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class DefossilizingRecipe extends AbstractDefossilizingRecipe {
	public DefossilizingRecipe(ResourceLocation recipeId, Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3, ItemStack result, float experience, int defossilizingTime, String defossilizerType) {
		super(CARecipes.DEFOSSILIZING_RECIPE_TYPE, CARecipes.DEFOSSILIZING_SERIALIZER.get(), recipeId, ingredient1, ingredient2, ingredient3, result, experience, defossilizingTime, defossilizerType);
	}

	public ItemStack getResult() {
		return result;
	}

	public IRecipeType<?> getType() {
		return CARecipes.DEFOSSILIZING_RECIPE_TYPE;
	}

	@Override
	public boolean matches(IInventory inv, World world) {
		return this.ingredient1.test(inv.getItem(0)) && this.ingredient2.test(inv.getItem(1));
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DefossilizingRecipe> {
		@Override
		public DefossilizingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			Ingredient fossilIngredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "fossil_ingredient"));
			Ingredient bucketIngredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "bucket_ingredient"));
			Ingredient powerChipIngredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(json, "power_chip_ingredient"));
			ResourceLocation itemId = new ResourceLocation(JSONUtils.getAsString(json, "result"));
			float experience = JSONUtils.getAsFloat(json, "experience", 0);
			int defossilizingTime = JSONUtils.getAsInt(json, "defossilizing_time", 10);
			String defossilizerType = JSONUtils.getAsString(json, "defossilizer_type");
			ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(itemId));
			return new DefossilizingRecipe(recipeId, fossilIngredient, bucketIngredient, powerChipIngredient, result, experience, defossilizingTime, defossilizerType);
		}

		@Nullable
		@Override
		public DefossilizingRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			Ingredient fossilIngredient = Ingredient.fromNetwork(buffer);
			Ingredient bucketIngredient = Ingredient.fromNetwork(buffer);
			Ingredient powerChipIngredient = Ingredient.fromNetwork(buffer);
			ItemStack result = buffer.readItem();
			float experience = buffer.readFloat();
			int defossilizingTime = buffer.readInt();
			String defossilizerType = buffer.readUtf();
			return new DefossilizingRecipe(recipeId, fossilIngredient, bucketIngredient, powerChipIngredient, result, experience, defossilizingTime, defossilizerType);
		}

		@SuppressWarnings("static-access")
		@Override
		public void toNetwork(PacketBuffer buffer, DefossilizingRecipe recipe) {
			recipe.ingredient1.toNetwork(buffer);
			recipe.ingredient2.toNetwork(buffer);
			recipe.ingredient3.toNetwork(buffer);
			buffer.writeItem(recipe.result);
			buffer.writeFloat(recipe.experience);
			buffer.writeInt(recipe.defossilizingTime);
			buffer.writeUtf(recipe.defossilizerType);
		}
	}
}
