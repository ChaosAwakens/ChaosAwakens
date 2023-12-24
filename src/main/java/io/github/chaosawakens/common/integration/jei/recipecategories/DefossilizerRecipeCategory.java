package io.github.chaosawakens.common.integration.jei.recipecategories;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.blocks.tileentities.DefossilizerBlock.DefossilizerType;
import io.github.chaosawakens.common.crafting.recipe.DefossilizingRecipe;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CARecipeTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DefossilizerRecipeCategory implements IRecipeCategory<DefossilizingRecipe> {
	public static final ResourceLocation ID = ChaosAwakens.prefix(CARecipeTypes.DEFOSSILIZING_RECIPE_TYPE.toString());
	private IDrawable bg;
	private IDrawable icon;

	public DefossilizerRecipeCategory(IGuiHelper helper) {
		this.bg = helper.drawableBuilder(ChaosAwakens.prefix("textures/gui/container/jei/defossilizer.png"), 0, 0, 170, 80).setTextureSize(170, 80).build();
		this.icon = helper.createDrawableIngredient(new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get()));
	}

	@Override
	public ResourceLocation getUid() {
		return ID;
	}

	@Override
	public Class<? extends DefossilizingRecipe> getRecipeClass() {
		return DefossilizingRecipe.class;
	}

	@Override
	public String getTitle() {
		return "Defossilizer";
	}

	@Override
	public IDrawable getBackground() {
		return bg;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setIngredients(DefossilizingRecipe recipe, IIngredients ingredients) {
		ObjectArrayList<List<ItemStack>> itemIngredients = new ObjectArrayList<>();
		ObjectArrayList<ItemStack> actualIngredients = new ObjectArrayList<>();

		Collections.addAll(actualIngredients, recipe.placeIng(0));
		itemIngredients.add(actualIngredients);
		actualIngredients = new ObjectArrayList<>();
		Collections.addAll(actualIngredients, recipe.placeIng(1));
		itemIngredients.add(actualIngredients);
		actualIngredients = new ObjectArrayList<>();
		Collections.addAll(actualIngredients, recipe.placeIng(2));
		itemIngredients.add(actualIngredients);
		
		ingredients.setInputLists(VanillaTypes.ITEM, itemIngredients);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getResult());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DefossilizingRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 52, 13);
		guiItemStacks.init(1, true, 43, 49);
		guiItemStacks.init(2, true, 61, 49);
		guiItemStacks.init(3, false, 112, 31);
		guiItemStacks.init(4, false, 10, 31);
		guiItemStacks.set(3, recipe.getResult());

		List<List<ItemStack>> ingredientInputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<ItemStack> actualIngredients = null;
		
		for (int i = 0; i <= 2; i++) {
			actualIngredients = ingredientInputs.get(i);
			if (actualIngredients != null && !actualIngredients.isEmpty()) guiItemStacks.set(i, actualIngredients);
		}
		
		if (Objects.equals(recipe.getDefossilizerType(), DefossilizerType.COPPER.getName())) guiItemStacks.set(4, Arrays.asList(CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.COPPER.getId())).get().asItem().getDefaultInstance(), CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get().asItem().getDefaultInstance()));
		else if (Objects.equals(recipe.getDefossilizerType(), DefossilizerType.CRYSTAL.getName())) guiItemStacks.set(4, CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.CRYSTAL.getId())).get().asItem().getDefaultInstance());
		else if (Objects.equals(recipe.getDefossilizerType(), DefossilizerType.IRON.getName())) guiItemStacks.set(4, CABlocks.DEFOSSILIZER_BLOCKS.get(DefossilizerType.byId(DefossilizerType.IRON.getId())).get().asItem().getDefaultInstance());
	}
}
