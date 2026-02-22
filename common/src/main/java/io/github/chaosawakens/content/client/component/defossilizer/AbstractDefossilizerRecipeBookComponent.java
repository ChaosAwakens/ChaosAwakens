package io.github.chaosawakens.content.client.component.defossilizer;

import io.github.chaosawakens.content.data.recipe.defossilizing.AbstractDefossilizingRecipe;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractDefossilizerRecipeBookComponent extends RecipeBookComponent {

    protected AbstractDefossilizerRecipeBookComponent() {

    }

    @Override
    protected void initFilterButtonTextures() {
        filterButton.initTextureValues(152, 182, 28, 18, RECIPE_BOOK_LOCATION);
    }

    @Override
    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);

        if (slot != null && slot.index < menu.getSize()) ghostRecipe.clear();
    }

    @Override
    public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        AbstractDefossilizingRecipe defossilizingRecipe = (AbstractDefossilizingRecipe) recipe;
        ItemStack resultStack = recipe.getResultItem(minecraft.level.registryAccess());
        Slot resultSlot = slots.get(3);

        ghostRecipe.setRecipe(recipe);
        ghostRecipe.addIngredient(Ingredient.of(resultStack), resultSlot.x, resultSlot.y);

        Slot fossilInputSlot = slots.get(0);
        Slot bucketInputSlot = slots.get(1);
        Slot powerChipInputSlot = slots.get(2);

        ghostRecipe.addIngredient(defossilizingRecipe.getFossilizedIngredient(), fossilInputSlot.x, fossilInputSlot.y);
        ghostRecipe.addIngredient(defossilizingRecipe.getBucketIngredient(), bucketInputSlot.x, bucketInputSlot.y);
        ghostRecipe.addIngredient(defossilizingRecipe.getPowerChipIngredient(), powerChipInputSlot.x, powerChipInputSlot.y);
    }
}
