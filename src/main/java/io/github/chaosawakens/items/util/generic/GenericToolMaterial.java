package io.github.chaosawakens.items.util.generic;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class GenericToolMaterial implements ToolMaterial {
    private int durability;
    private float miningSpeedMultiplier;
    private float attackDamage;
    private int miningLevel;
    private int enchantability;
    private Item repairIngredient;

    public GenericToolMaterial(int miningLevel, int durability, float miningSpeedMultiplier, float attackDamage, int enchantability, Item repairIngredient) {
        this.durability = durability;
        this.miningSpeedMultiplier = miningSpeedMultiplier;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeedMultiplier;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(this.repairIngredient);
    }
}
