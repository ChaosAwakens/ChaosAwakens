package io.github.chaosawakens.common.integration.jei;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.crafting.recipe.DefossilizingRecipe;
import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CARecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class DefossilizerRecipeCategory implements IRecipeCategory<DefossilizingRecipe> {
	static final ResourceLocation ID = new ResourceLocation(CARecipes.DEFOSSILIZING_RECIPE_TYPE.toString());
	private IDrawable bg;
	private IDrawable icon;
//	private IDrawable arrow;
	public DefossilizingRecipe recipe;

	public DefossilizerRecipeCategory(IGuiHelper helper) {
		bg = helper.drawableBuilder(new ResourceLocation(ChaosAwakens.MODID, "textures/gui/container/jei/defossilizer.png"), 0, 0, 170, 80).setTextureSize(170, 80).build();
		icon = helper.createDrawableIngredient(new ItemStack(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get()));
//		arrow = helper.createAnimatedDrawable(null, 80, StartDirection.LEFT, false);
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
		List<List<ItemStack>> in = new ArrayList<>();
		List<ItemStack> electricBoogaloo = new ArrayList<>();

		Collections.addAll(electricBoogaloo, recipe.placeIng(0));
		in.add(electricBoogaloo);
		electricBoogaloo = new ArrayList<>();
		Collections.addAll(electricBoogaloo, recipe.placeIng(1));
		in.add(electricBoogaloo);
		electricBoogaloo = new ArrayList<>();
		Collections.addAll(electricBoogaloo, recipe.placeIng(2));
		in.add(electricBoogaloo);
		ingredients.setInputLists(VanillaTypes.ITEM, in);
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

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<ItemStack> input = null;
		for (int i = 0; i <= 2; i++) {
			input = inputs.get(i);
			if (input != null && !input.isEmpty()) guiItemStacks.set(i, input);
		}
		if (Objects.equals(recipe.getDefossilizerType(), "copper")) {
			guiItemStacks.set(4, Arrays.asList(CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.COPPER.getId())).get().asItem().getDefaultInstance(),
					CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get().asItem().getDefaultInstance()));
		} else if (Objects.equals(recipe.getDefossilizerType(), "iron")) {
			guiItemStacks.set(4, CABlocks.DEFOSSILIZER_BLOCKS.get(CABlocks.DefossilizerType.byId(CABlocks.DefossilizerType.IRON.getId())).get().asItem().getDefaultInstance());
		}
	}
}
