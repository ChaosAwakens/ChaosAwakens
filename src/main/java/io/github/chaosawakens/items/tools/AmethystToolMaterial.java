package io.github.chaosawakens.items.tools;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class AmethystToolMaterial implements ToolMaterial {

    public static final AmethystToolMaterial INSTANCE = new AmethystToolMaterial();

    @Override
    public int getDurability() {
        return 500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 5.0F;
    }

    @Override
    public float getAttackDamage() {
        return 3.0F;
    }

    @Override
    public int getMiningLevel() {
        return 2;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null; //Ingredient.ofItems(Items.POTATO);
    }
}
