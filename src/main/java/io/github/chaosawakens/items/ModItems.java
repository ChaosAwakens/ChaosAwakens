package io.github.chaosawakens.items;

import io.github.chaosawakens.items.util.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems implements ModInitializer {
    //Amethyst
    public static final ToolMaterial AMETHYST_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, ModItems.AMETHYST);
    public static final ArmorMaterial AMETHYST_ARMOR_MATERIAL = new GenericArmorMaterial(new int[]{13, 15, 16, 11}, new int[]{3, 6, 8, 3}, 37, 10, ModItems.AMETHYST, "amethyst");
    public static final Item AMETHYST = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static final ToolItem AMETHYST_SWORD = new SwordItem(AMETHYST_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem AMETHYST_SHOVEL = new ShovelItem(AMETHYST_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_PICKAXE = new GenericPickaxeItem(AMETHYST_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_AXE = new GenericAxeItem(AMETHYST_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_HOE = new GenericHoeItem(AMETHYST_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item AMETHYST_HELMET = new ArmorItem(AMETHYST_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_CHESTPLATE = new ArmorItem(AMETHYST_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_LEGGINGS = new ArmorItem(AMETHYST_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_BOOTS = new ArmorItem(AMETHYST_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Emerald
    public static final ToolMaterial EMERALD_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, Items.EMERALD);
    public static final ArmorMaterial EMERALD_ARMOR_MATERIAL = new GenericArmorMaterial(new int[]{13, 15, 16, 11}, new int[]{3, 6, 8, 3}, 37, 10, Items.EMERALD, "emerald");

    public static final ToolItem EMERALD_SWORD = new SwordItem(EMERALD_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem EMERALD_SHOVEL = new ShovelItem(EMERALD_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_PICKAXE = new GenericPickaxeItem(EMERALD_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_AXE = new GenericAxeItem(EMERALD_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_HOE = new GenericHoeItem(EMERALD_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item EMERALD_HELMET = new ArmorItem(EMERALD_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(EMERALD_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_LEGGINGS = new ArmorItem(EMERALD_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_BOOTS = new ArmorItem(EMERALD_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Ruby
    public static final ToolMaterial RUBY_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, ModItems.RUBY);
    public static final ArmorMaterial RUBY_ARMOR_MATERIAL = new GenericArmorMaterial(new int[]{13, 15, 16, 11}, new int[]{3, 6, 8, 3}, 37, 10, ModItems.RUBY, "ruby");
    public static final Item RUBY = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static final ToolItem RUBY_SWORD = new SwordItem(RUBY_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem RUBY_SHOVEL = new ShovelItem(RUBY_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_PICKAXE = new GenericPickaxeItem(RUBY_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_AXE = new GenericAxeItem(RUBY_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_HOE = new GenericHoeItem(RUBY_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item RUBY_HELMET = new ArmorItem(RUBY_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_CHESTPLATE = new ArmorItem(RUBY_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_LEGGINGS = new ArmorItem(RUBY_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_BOOTS = new ArmorItem(RUBY_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    @Override
    public void onInitialize() {
        RegisterItem.register("amethyst", AMETHYST);
        RegisterItem.register("amethyst_sword", AMETHYST_SWORD);
        RegisterItem.register("amethyst_shovel", AMETHYST_SHOVEL);
        RegisterItem.register("amethyst_pickaxe", AMETHYST_PICKAXE);
        RegisterItem.register("amethyst_axe", AMETHYST_AXE);
        RegisterItem.register("amethyst_hoe", AMETHYST_HOE);
        RegisterItem.register("amethyst_helmet", AMETHYST_HELMET);
        RegisterItem.register("amethyst_chestplate", AMETHYST_CHESTPLATE);
        RegisterItem.register("amethyst_leggings", AMETHYST_LEGGINGS);
        RegisterItem.register("amethyst_boots", AMETHYST_BOOTS);

        RegisterItem.register("emerald_sword", EMERALD_SWORD);
        RegisterItem.register("emerald_shovel", EMERALD_SHOVEL);
        RegisterItem.register("emerald_pickaxe", EMERALD_PICKAXE);
        RegisterItem.register("emerald_axe", EMERALD_AXE);
        RegisterItem.register("emerald_hoe", EMERALD_HOE);
        RegisterItem.register("emerald_helmet", EMERALD_HELMET);
        RegisterItem.register("emerald_chestplate", EMERALD_CHESTPLATE);
        RegisterItem.register("emerald_leggings", EMERALD_LEGGINGS);
        RegisterItem.register("emerald_boots", EMERALD_BOOTS);

        RegisterItem.register("ruby", RUBY);
        RegisterItem.register("ruby_sword", RUBY_SWORD);
        RegisterItem.register("ruby_shovel", RUBY_SHOVEL);
        RegisterItem.register("ruby_pickaxe", RUBY_PICKAXE);
        RegisterItem.register("ruby_axe", RUBY_AXE);
        RegisterItem.register("ruby_hoe", RUBY_HOE);
        RegisterItem.register("ruby_helmet", RUBY_HELMET);
        RegisterItem.register("ruby_chestplate", RUBY_CHESTPLATE);
        RegisterItem.register("ruby_leggings", RUBY_LEGGINGS);
        RegisterItem.register("ruby_boots", RUBY_BOOTS);
    }
}
