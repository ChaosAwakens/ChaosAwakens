package io.github.chaosawakens.common.crafting.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AbstractDefossilizingRecipe implements IRecipe<IInventory> {
    protected final IRecipeType<?> type;
    protected final IRecipeSerializer<?> serializer;
    protected final ResourceLocation recipeId;
    protected final Ingredient ingredient1;
    protected final Ingredient ingredient2;
    protected final Ingredient ingredient3;
    protected final ItemStack result;
    protected final float experience;
    protected final int defossilizingTime;

    public AbstractDefossilizingRecipe(IRecipeType<?> type, IRecipeSerializer<?> serializer, ResourceLocation recipeId, Ingredient ingredient1, Ingredient ingredient2, Ingredient ingredient3, ItemStack result, float experience, int defossilizingTime) {
        this.type = type;
        this.serializer = serializer;
        this.recipeId = recipeId;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.result = result;
        this.experience = experience;
        this.defossilizingTime = defossilizingTime;
    }

    public boolean matches(IInventory inv, World world) {
        return this.ingredient1.test(inv.getItem(0)) && this.ingredient2.test(inv.getItem(1)) && this.ingredient3.test(inv.getItem(2));
    }

    public ItemStack assemble(IInventory p_77572_1_) {
        return this.result.copy();
    }

    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient1);
        nonnulllist.add(this.ingredient2);
        nonnulllist.add(this.ingredient3);
        return nonnulllist;
    }

    public float getExperience() {
        return this.experience;
    }

    public ItemStack getResultItem() {
        return this.result;
    }

    public int getCookingTime() {
        return this.defossilizingTime;
    }

    public ResourceLocation getId() {
        return this.recipeId;
    }

    public IRecipeType<?> getType() {
        return this.type;
    }

    public IRecipeSerializer<?> getSerializer() {
        return this.serializer;
    }
}