package io.github.chaosawakens.data;

import com.google.gson.JsonObject;
import io.github.chaosawakens.common.util.NameUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class FossilRecipeBuilder {
	private final IRecipeSerializer<?> serializer;
	private final Collection<Consumer<JsonObject>> extraData = new ArrayList<>();
	private final Ingredient ingredient1;
	private final Ingredient ingredient2;
	private final Ingredient ingredient3;
	private final Item result;
	private final int experience;
	private final int defossilizingTime;
	private final String defossilizerType;
	private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();
	private boolean hasAdvancementCriterion = false;
	private String group = "";

	protected FossilRecipeBuilder(IRecipeSerializer<?> serializer, Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3, IItemProvider result, int experience, int defossilizingTime, String defossilizerType) {
		this.serializer = serializer;
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.ingredient3 = ingredient3;
		this.result = result.asItem();
		this.experience = experience;
		this.defossilizingTime = defossilizingTime;
		this.defossilizerType = defossilizerType;
	}

	public static FossilRecipeBuilder builder(IRecipeSerializer<?> serializer, Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3, IItemProvider result, String defossilizerType) {
		return builder(serializer, ingredient1, ingredient2, ingredient3, result, 1, 10, defossilizerType);
	}

	public static FossilRecipeBuilder builder(IRecipeSerializer<?> serializer, Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3, IItemProvider result, int experience, int defossilizingTime, String defossilizerType) {
		return new FossilRecipeBuilder(serializer, ingredient1, ingredient2, ingredient3, result, experience, defossilizingTime, defossilizerType);
	}

	/**
	 * Override to quickly add additional data to serialization
	 *
	 * @param json The recipe JSON
	 */
	protected void serializeExtra(JsonObject json) {
		this.extraData.forEach(consumer -> consumer.accept(json));
	}

	public FossilRecipeBuilder setGroup(String group) {
		this.group = group;
		return this;
	}

	public void build(Consumer<IFinishedRecipe> consumer) {
		ResourceLocation itemId = NameUtils.from(this.result);
		ResourceLocation serializerId = NameUtils.from(this.serializer);
		build(consumer, new ResourceLocation(itemId.getNamespace(), serializerId.getPath() + "/" + itemId.getPath()));
	}

	public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
		if (this.hasAdvancementCriterion && !this.advancementBuilder.getCriteria().isEmpty()) {
			this.advancementBuilder.parent(new ResourceLocation("recipes/root"))
					.addCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(EntityPredicate.AndPredicate.ANY, id))
					.rewards(AdvancementRewards.Builder.recipe(id)).requirements(IRequirementsStrategy.OR);
		}
		ResourceLocation advancementId = new ResourceLocation(id.getNamespace(), "recipes/defossilizing/" + id.getPath());
		consumer.accept(new FossilRecipeBuilder.Result(id, this, advancementId));
	}

	public static class Result implements IFinishedRecipe {
		private final ResourceLocation id;
		private final FossilRecipeBuilder builder;
		private final ResourceLocation advancementId;

		public Result(ResourceLocation id, FossilRecipeBuilder builder, ResourceLocation advancementId) {
			this.id = id;
			this.builder = builder;
			this.advancementId = advancementId;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			if (!builder.group.isEmpty()) json.addProperty("group", builder.group);

			json.add("fossil_ingredient", builder.ingredient1.toJson());
			json.add("bucket_ingredient", builder.ingredient2.toJson());
			json.add("power_chip_ingredient", builder.ingredient3.toJson());
			json.addProperty("result", NameUtils.from(builder.result).toString());
			json.addProperty("experience", builder.experience);
			json.addProperty("defossilizing_time", builder.defossilizingTime);
			json.addProperty("defossilizer_type", builder.defossilizerType);

			builder.serializeExtra(json);
		}

		@Override
		public IRecipeSerializer<?> getType() {
			return builder.serializer;
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return builder.hasAdvancementCriterion ? builder.advancementBuilder.serializeToJson() : null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return builder.hasAdvancementCriterion ? advancementId : null;
		}
	}
}
