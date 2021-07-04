package io.github.chaosawakens.items.util;

import io.github.chaosawakens.items.Items;
import io.github.chaosawakens.items.util.generic.GenericArmorMaterial;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvents;

public class ArmorMaterials {
    public static final ArmorMaterial EMERALD_ARMOR_MATERIAL;
    public static final ArmorMaterial EXPERIENCE_ARMOR_MATERIAL;
    public static final ArmorMaterial AMETHYST_ARMOR_MATERIAL;
    public static final ArmorMaterial RUBY_ARMOR_MATERIAL;
    public static final ArmorMaterial TIGERS_EYE_ARMOR_MATERIAL;
    public static final ArmorMaterial LAPIS_LAZULI_ARMOR_MATERIAL;
    public static final ArmorMaterial ULTIMATE_ARMOR_MATERIAL;
    public static final ArmorMaterial LAVA_EEL_ARMOR_MATERIAL;
    public static final ArmorMaterial MOTH_SCALE_ARMOR_MATERIAL;
    public static final ArmorMaterial PEACOCK_FEATHER_ARMOR_MATERIAL;
    public static final ArmorMaterial PINK_TOURMALINE_ARMOR_MATERIAL;
    public static final ArmorMaterial CATS_EYE_ARMOR_MATERIAL;
    public static final ArmorMaterial COPPER_ARMOR_MATERIAL;
    public static final ArmorMaterial TIN_ARMOR_MATERIAL;
    public static final ArmorMaterial SILVER_ARMOR_MATERIAL;
    public static final ArmorMaterial PLATINUM_ARMOR_MATERIAL;
    public static final ArmorMaterial ROYAL_GUARDIAN_ARMOR_MATERIAL;
    public static final ArmorMaterial QUEEN_SCALE_ARMOR_MATERIAL;

    static {
        EMERALD_ARMOR_MATERIAL = new GenericArmorMaterial(35, new int[]{3, 6, 8, 3}, 24, 2.0f, Ingredient.ofItems(net.minecraft.item.Items.EMERALD), "emerald");
        EXPERIENCE_ARMOR_MATERIAL = new GenericArmorMaterial(39, new int[]{4, 7, 9, 5}, 32, 2.5f, Ingredient.ofItems(net.minecraft.item.Items.EMERALD), "experience");
        AMETHYST_ARMOR_MATERIAL = new GenericArmorMaterial(38, new int[]{3, 7, 8, 4}, 18, 2.0f, Ingredient.ofItems(Items.AMETHYST), "amethyst");
        RUBY_ARMOR_MATERIAL = new GenericArmorMaterial(37, new int[]{4, 8, 9, 4}, 22, 3.0f, Ingredient.ofItems(Items.RUBY), "ruby");
        TIGERS_EYE_ARMOR_MATERIAL = new GenericArmorMaterial(36, new int[]{4, 7, 8, 4}, 20, 2.5f, Ingredient.ofItems(Items.TIGERS_EYE), "tigers_eye");
        LAPIS_LAZULI_ARMOR_MATERIAL = new GenericArmorMaterial(22, new int[]{2, 5, 7, 2}, 24, 1.75f, Ingredient.ofItems(net.minecraft.item.Items.LAPIS_BLOCK), "lapis_lazuli");
        ULTIMATE_ARMOR_MATERIAL = new GenericArmorMaterial(48, new int[]{6, 10, 12, 6}, 64, 4.5f, Ingredient.ofItems(Items.TITANIUM_INGOT, Items.URANIUM_INGOT),"ultimate", 0.125f);
        LAVA_EEL_ARMOR_MATERIAL = new GenericArmorMaterial(33, new int[]{2, 5, 7, 2}, 9, 0.0f, Ingredient.ofItems(Items.LAVA_EEL), "lava_eel", SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        MOTH_SCALE_ARMOR_MATERIAL = new GenericArmorMaterial(35, new int[]{2, 5, 7, 2}, 16, 1.75f, null, "moth_scale", 0.05f, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        PEACOCK_FEATHER_ARMOR_MATERIAL = new GenericArmorMaterial(8, new int[]{2, 4, 5, 2}, 8, 0.0f, null, "peacock_feather", SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
        PINK_TOURMALINE_ARMOR_MATERIAL = new GenericArmorMaterial(8, new int[]{2, 5, 7, 3}, 10, 1.25f, null, "pink_tourmaline");
        CATS_EYE_ARMOR_MATERIAL = new GenericArmorMaterial(36, new int[]{4, 7, 8, 4}, 20, 2.5f, null, "cats_eye");
        COPPER_ARMOR_MATERIAL = new GenericArmorMaterial(9, new int[]{1, 3, 4, 1}, 6, 0.0f, null, "copper");
        TIN_ARMOR_MATERIAL = new GenericArmorMaterial(12, new int[]{1, 4, 5, 2}, 8, 0.0f, null, "tin");
        SILVER_ARMOR_MATERIAL = new GenericArmorMaterial(21, new int[]{2, 4, 7, 3}, 10, 1.0f, null, "silver");
        PLATINUM_ARMOR_MATERIAL = new GenericArmorMaterial(35, new int[]{3, 6, 8, 3}, 12, 2.5f, null, "platinum");
        ROYAL_GUARDIAN_ARMOR_MATERIAL = new GenericArmorMaterial(72, new int[]{8, 12, 14, 8}, 84, 7.5f, null, "royal_guardian", 0.4f);
        QUEEN_SCALE_ARMOR_MATERIAL = new GenericArmorMaterial(68, new int[]{9, 14, 16, 9}, 96, 8.0f, null, "queen_scale", 0.3f);
    }
}
