package io.github.chaosawakens.items.util;

import io.github.chaosawakens.items.Items;
import io.github.chaosawakens.items.util.generic.GenericToolMaterial;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ToolMaterials {
    public static final ToolMaterial EMERALD_TOOL_MATERIAL;
    public static final ToolMaterial AMETHYST_TOOL_MATERIAL;
    public static final ToolMaterial RUBY_TOOL_MATERIAL;
    public static final ToolMaterial TIGERS_EYE_TOOL_MATERIAL;
    public static final ToolMaterial CRYSTAL_WOOD_TOOL_MATERIAL;
    public static final ToolMaterial KYANITE_TOOL_MATERIAL;
    public static final ToolMaterial PINK_TOURMALINE_TOOL_MATERIAL;
    public static final ToolMaterial CATS_EYE_TOOL_MATERIAL;
    public static final ToolMaterial ULTIMATE_TOOL_MATERIAL;
    public static final ToolMaterial NIGHTMARE_TOOL_MATERIAL;
    public static final ToolMaterial COPPER_TOOL_MATERIAL;
    public static final ToolMaterial TIN_TOOL_MATERIAL;
    public static final ToolMaterial SILVER_TOOL_MATERIAL;
    public static final ToolMaterial PLATINUM_TOOL_MATERIAL;
    public static final ToolMaterial BATTLEAXE_WEAPON_TOOL_MATERIAL;
    public static final ToolMaterial QUEEN_BATTLEAXE_WEAPON_TOOL_MATERIAL;
    public static final ToolMaterial RAY_GUN_WEAPON_TOOL_MATERIAL;
    public static final ToolMaterial GENERIC_WEAPON_TOOL_MATERIAL;
    public static final ToolMaterial BIG_HAMMER_WEAPON_TOOL_MATERIAL;
    public static final ToolMaterial BERTHA_WEAPON_TOOL_MATERIAL;

    static {
        EMERALD_TOOL_MATERIAL = new GenericToolMaterial(3, 1300, 8, 6, 24, Ingredient.ofItems(net.minecraft.item.Items.EMERALD));
        AMETHYST_TOOL_MATERIAL = new GenericToolMaterial(3, 2000, 9, 11, 18, Ingredient.ofItems(Items.AMETHYST));
        RUBY_TOOL_MATERIAL = new GenericToolMaterial(4, 1800, 10, 16, 22, Ingredient.ofItems(Items.RUBY));
        TIGERS_EYE_TOOL_MATERIAL = new GenericToolMaterial(3, 1600, 10, 8, 20, Ingredient.ofItems(Items.TIGERS_EYE));
        CRYSTAL_WOOD_TOOL_MATERIAL = new GenericToolMaterial(0, 300, 2, 1, 6, Ingredient.ofItems(null));
        KYANITE_TOOL_MATERIAL = new GenericToolMaterial(1, 800, 3, 2, 6, Ingredient.ofItems(null));
        PINK_TOURMALINE_TOOL_MATERIAL = new GenericToolMaterial(2, 1100, 7, 8, 6, Ingredient.ofItems(null));
        CATS_EYE_TOOL_MATERIAL = new GenericToolMaterial(3, 1600, 8, 8, 20, Ingredient.ofItems(null));
        ULTIMATE_TOOL_MATERIAL = new GenericToolMaterial(5, 3000, 15, 36, 64, Ingredient.ofItems(Items.TITANIUM_INGOT, Items.URANIUM_INGOT));
        NIGHTMARE_TOOL_MATERIAL = new GenericToolMaterial(3, 1800, 12, 26, 24, Ingredient.ofItems(null));
        COPPER_TOOL_MATERIAL = new GenericToolMaterial(1, 150, 4, 2, 6, Ingredient.ofItems(null));
        TIN_TOOL_MATERIAL = new GenericToolMaterial(1, 180, 5, 3, 8, Ingredient.ofItems(null));
        SILVER_TOOL_MATERIAL = new GenericToolMaterial(2, 450, 7, 4, 10, Ingredient.ofItems(null));
        PLATINUM_TOOL_MATERIAL = new GenericToolMaterial(3, 1600, 8, 6, 12, Ingredient.ofItems(null));
        BATTLEAXE_WEAPON_TOOL_MATERIAL = new GenericToolMaterial(5, 1200, 15, 47, 64, Ingredient.ofItems(Items.TITANIUM_INGOT, Items.URANIUM_INGOT));
        QUEEN_BATTLEAXE_WEAPON_TOOL_MATERIAL = new GenericToolMaterial(3, 10000, 15, 663, 96, Ingredient.ofItems(null));
        RAY_GUN_WEAPON_TOOL_MATERIAL = new GenericToolMaterial(0, 50, 0, 0, 0, Ingredient.ofItems(net.minecraft.item.Items.REDSTONE_BLOCK));
        GENERIC_WEAPON_TOOL_MATERIAL = new GenericToolMaterial(2, 1024, 6, 6, 8, Ingredient.ofItems(null));
        BIG_HAMMER_WEAPON_TOOL_MATERIAL = new GenericToolMaterial(2, 2000, 6, 11, 9, Ingredient.ofItems(null));
        BERTHA_WEAPON_TOOL_MATERIAL = new GenericToolMaterial(5, 6000, 16, 57, 72, Ingredient.ofItems(null));
    }
}
