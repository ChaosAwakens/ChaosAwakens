package io.github.chaosawakens.items.util;

import io.github.chaosawakens.items.Items;
import io.github.chaosawakens.items.util.generic.GenericArmorMaterial;
import net.minecraft.item.ArmorMaterial;

public class ArmorMaterials {
    public static final ArmorMaterial ULTIMATE_ARMOR_MATERIAL;
    public static final ArmorMaterial EMERALD_ARMOR_MATERIAL;
    public static final ArmorMaterial RUBY_ARMOR_MATERIAL;
    public static final ArmorMaterial AMETHYST_ARMOR_MATERIAL;
    public static final ArmorMaterial TIGERS_EYE_ARMOR_MATERIAL;
    public static final ArmorMaterial EXPERIENCE_ARMOR_MATERIAL;
    public static final ArmorMaterial LAVA_EEL_ARMOR_MATERIAL;
    public static final ArmorMaterial LAPIS_LAZULI_ARMOR_MATERIAL;

    static {
        ULTIMATE_ARMOR_MATERIAL = new GenericArmorMaterial(48, new int[]{6, 10, 12, 6}, 64, (int) 2.5f, null,"ultimate");
        EMERALD_ARMOR_MATERIAL = new GenericArmorMaterial(35, new int[]{3, 6, 8, 3}, 24, 2, net.minecraft.item.Items.EMERALD, "emerald");
        RUBY_ARMOR_MATERIAL = new GenericArmorMaterial(37, new int[]{4, 8, 9, 4}, 22, (int) 2.5f, Items.RUBY, "ruby");
        AMETHYST_ARMOR_MATERIAL = new GenericArmorMaterial(39, new int[]{3, 7, 8, 4}, 18, 2, Items.AMETHYST, "amethyst");
        TIGERS_EYE_ARMOR_MATERIAL = new GenericArmorMaterial(36, new int[]{4, 7, 8, 4}, 20, (int) 2.5f, Items.TIGERS_EYE, "tigers_eye");
        EXPERIENCE_ARMOR_MATERIAL = new GenericArmorMaterial(39, new int[]{4, 7, 9, 5}, 32, (int) 2.5f, net.minecraft.item.Items.EMERALD, "experience");
        LAVA_EEL_ARMOR_MATERIAL = new GenericArmorMaterial(34, new int[]{2, 5, 7, 2}, 9, 0, Items.LAVA_EEL, "lava_eel");
        LAPIS_LAZULI_ARMOR_MATERIAL = new GenericArmorMaterial(27, new int[]{2, 5, 7, 2}, 24, (int) 1.75f, net.minecraft.item.Items.LAPIS_BLOCK, "lapis_lazuli");
    }
}
