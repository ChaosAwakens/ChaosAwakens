package io.github.chaosawakens.items;

import io.github.chaosawakens.items.custom.ThunderStaff;
import io.github.chaosawakens.items.util.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

public class ModItems implements ModInitializer {
    //Misc Items
    public static final Item ALUMINUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item DRUID_KEY = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item OIL = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SALT = new Item(new Item.Settings().group(ItemGroup.MISC));

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

    //Ultimate
    public static final ToolMaterial ULTIMATE_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null);
    public static final ArmorMaterial ULTIMATE_ARMOR_MATERIAL = new GenericArmorMaterial(new int[]{13, 15, 16, 11}, new int[]{3, 6, 8, 3}, 37, 10, null, "ultimate");
    public static final Item URANIUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item URANIUM_NUGGET = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item TITANIUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item TITANIUM_NUGGET = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static final ToolItem ULTIMATE_SWORD = new SwordItem(ULTIMATE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem ULTIMATE_SHOVEL = new ShovelItem(ULTIMATE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem ULTIMATE_PICKAXE = new GenericPickaxeItem(ULTIMATE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem ULTIMATE_AXE = new GenericAxeItem(ULTIMATE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem ULTIMATE_HOE = new GenericHoeItem(ULTIMATE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item ULTIMATE_HELMET = new ArmorItem(ULTIMATE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ULTIMATE_CHESTPLATE = new ArmorItem(ULTIMATE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ULTIMATE_LEGGINGS = new ArmorItem(ULTIMATE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ULTIMATE_BOOTS = new ArmorItem(ULTIMATE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Tiger's Eye
    public static final ToolMaterial TIGERSEYE_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, ModItems.TIGERSEYE);
    public static final ArmorMaterial TIGERSEYE_ARMOR_MATERIAL = new GenericArmorMaterial(new int[]{13, 15, 16, 11}, new int[]{3, 6, 8, 3}, 37, 10, ModItems.TIGERSEYE, "tigerseye");
    public static final Item TIGERSEYE = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static final ToolItem TIGERSEYE_SWORD = new SwordItem(TIGERSEYE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem TIGERSEYE_SHOVEL = new ShovelItem(TIGERSEYE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem TIGERSEYE_PICKAXE = new GenericPickaxeItem(TIGERSEYE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem TIGERSEYE_AXE = new GenericAxeItem(TIGERSEYE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem TIGERSEYE_HOE = new GenericHoeItem(TIGERSEYE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item TIGERSEYE_HELMET = new ArmorItem(TIGERSEYE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item TIGERSEYE_CHESTPLATE = new ArmorItem(TIGERSEYE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item TIGERSEYE_LEGGINGS = new ArmorItem(TIGERSEYE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item TIGERSEYE_BOOTS = new ArmorItem(TIGERSEYE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Optimised
    public static final ToolMaterial OPTIMISED_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null);
    public static final ToolItem OPTIMISED_SHOVEL = new ShovelItem(OPTIMISED_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem OPTIMISED_PICKAXE = new GenericPickaxeItem(OPTIMISED_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    //Experience
    public static final ToolMaterial EXPERIENCE_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null);
    public static final ArmorMaterial EXPERIENCE_ARMOR_MATERIAL = new GenericArmorMaterial(new int[]{13, 15, 16, 11}, new int[]{3, 6, 8, 3}, 37, 10, null, "experience");

    public static final ToolItem EXPERIENCE_SWORD = new SwordItem(EXPERIENCE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem EXPERIENCE_SHOVEL = new ShovelItem(EXPERIENCE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EXPERIENCE_PICKAXE = new GenericPickaxeItem(EXPERIENCE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EXPERIENCE_AXE = new GenericAxeItem(EXPERIENCE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EXPERIENCE_HOE = new GenericHoeItem(EXPERIENCE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item EXPERIENCE_HELMET = new ArmorItem(EXPERIENCE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EXPERIENCE_CHESTPLATE = new ArmorItem(EXPERIENCE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EXPERIENCE_LEGGINGS = new ArmorItem(EXPERIENCE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EXPERIENCE_BOOTS = new ArmorItem(EXPERIENCE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Royal Guardian
    public static final ToolMaterial ROYAL_GUARDIAN_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null);
    public static final ArmorMaterial ROYAL_GUARDIAN_ARMOR_MATERIAL = new GenericArmorMaterial(new int[]{13, 15, 16, 11}, new int[]{3, 6, 8, 3}, 37, 10, null, "royal_guardian");

    public static final ToolItem ROYAL_GUARDIAN_SWORD = new SwordItem(ROYAL_GUARDIAN_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item ROYAL_GUARDIAN_HELMET = new ArmorItem(ROYAL_GUARDIAN_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROYAL_GUARDIAN_CHESTPLATE = new ArmorItem(ROYAL_GUARDIAN_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROYAL_GUARDIAN_LEGGINGS = new ArmorItem(ROYAL_GUARDIAN_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROYAL_GUARDIAN_BOOTS = new ArmorItem(ROYAL_GUARDIAN_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Queen Scale
    public static final ToolMaterial QUEEN_SCALE_TOOL_MATERIAL = new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null);
    public static final ArmorMaterial QUEEN_SCALE_ARMOR_MATERIAL = new GenericArmorMaterial(new int[]{13, 15, 16, 11}, new int[]{3, 6, 8, 3}, 37, 10, null, "queen_scale");

    public static final ToolItem QUEEN_SCALE_BATTLE_AXE = new SwordItem(QUEEN_SCALE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item QUEEN_SCALE_HELMET = new ArmorItem(QUEEN_SCALE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item QUEEN_SCALE_CHESTPLATE = new ArmorItem(QUEEN_SCALE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item QUEEN_SCALE_LEGGINGS = new ArmorItem(QUEEN_SCALE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item QUEEN_SCALE_BOOTS = new ArmorItem(QUEEN_SCALE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));


    //Misc Weapons
    public static final ToolItem FAIRY_SWORD = new SwordItem(new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null), 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem NIGHTMARE_SWORD = new SwordItem(new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null), 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem POISON_SWORD = new SwordItem(new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null), 0, 0, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem RAT_SWORD = new SwordItem(new GenericToolMaterial(500, 5.0F, 3.0F, 2, 15, null), 0, 0, new Item.Settings().group(ItemGroup.COMBAT));

    //Ranged Weapons
    public static final BowItem ULTIMATE_BOW = new GenericBowItem(new Item.Settings().group(ItemGroup.COMBAT), null, 5);
    public static final Item THUNDER_STAFF = new ThunderStaff(new Item.Settings().group(ItemGroup.COMBAT));
    //Ray gun

    //Food Items
    public static final Item RAW_CORNDOG = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item COOKED_CORNDOG = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item RAW_BACON = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item COOKED_BACON = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CORN = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item TOMATO = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item LETTUCE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CHEESE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item GARDEN_SALAD = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item BLT = new Item(new Item.Settings().group(ItemGroup.MISC));

    @Override
    public void onInitialize() {
        RegisterItem.register("aluminum_ingot", ALUMINUM_INGOT);
        RegisterItem.register("druid_key", DRUID_KEY);
        RegisterItem.register("oil", OIL);
        RegisterItem.register("salt", SALT);
        
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

        RegisterItem.register("uranium_ingot", URANIUM_INGOT);
        RegisterItem.register("uranium_nugget", URANIUM_NUGGET);
        RegisterItem.register("titanium_ingot", TITANIUM_INGOT);
        RegisterItem.register("titanium_nugget", TITANIUM_NUGGET);
        RegisterItem.register("ultimate_sword", ULTIMATE_SWORD);
        RegisterItem.register("ultimate_shovel", ULTIMATE_SHOVEL);
        RegisterItem.register("ultimate_pickaxe", ULTIMATE_PICKAXE);
        RegisterItem.register("ultimate_axe", ULTIMATE_AXE);
        RegisterItem.register("ultimate_hoe", ULTIMATE_HOE);
        RegisterItem.register("ultimate_helmet", ULTIMATE_HELMET);
        RegisterItem.register("ultimate_chestplate", ULTIMATE_CHESTPLATE);
        RegisterItem.register("ultimate_leggings", ULTIMATE_LEGGINGS);
        RegisterItem.register("ultimate_boots", ULTIMATE_BOOTS);

        RegisterItem.register("tigerseye", TIGERSEYE);
        RegisterItem.register("tigerseye_sword", TIGERSEYE_SWORD);
        RegisterItem.register("tigerseye_shovel", TIGERSEYE_SHOVEL);
        RegisterItem.register("tigerseye_pickaxe", TIGERSEYE_PICKAXE);
        RegisterItem.register("tigerseye_axe", TIGERSEYE_AXE);
        RegisterItem.register("tigerseye_hoe", TIGERSEYE_HOE);
        RegisterItem.register("tigerseye_helmet", TIGERSEYE_HELMET);
        RegisterItem.register("tigerseye_chestplate", TIGERSEYE_CHESTPLATE);
        RegisterItem.register("tigerseye_leggings", TIGERSEYE_LEGGINGS);
        RegisterItem.register("tigerseye_boots", TIGERSEYE_BOOTS);

        RegisterItem.register("optimised_shovel", OPTIMISED_SHOVEL);
        RegisterItem.register("optimised_pickaxe", OPTIMISED_PICKAXE);

        RegisterItem.register("experience_sword", EXPERIENCE_SWORD);
        RegisterItem.register("experience_shovel", EXPERIENCE_SHOVEL);
        RegisterItem.register("experience_pickaxe", EXPERIENCE_PICKAXE);
        RegisterItem.register("experience_axe", EXPERIENCE_AXE);
        RegisterItem.register("experience_hoe", EXPERIENCE_HOE);
        RegisterItem.register("experience_helmet", EXPERIENCE_HELMET);
        RegisterItem.register("experience_chestplate", EXPERIENCE_CHESTPLATE);
        RegisterItem.register("experience_leggings", EXPERIENCE_LEGGINGS);
        RegisterItem.register("experience_boots", EXPERIENCE_BOOTS);

        RegisterItem.register("royal_guardian_sword", ROYAL_GUARDIAN_SWORD);
        RegisterItem.register("royal_guardian_helmet", ROYAL_GUARDIAN_HELMET);
        RegisterItem.register("royal_guardian_chestplate", ROYAL_GUARDIAN_CHESTPLATE);
        RegisterItem.register("royal_guardian_leggings", ROYAL_GUARDIAN_LEGGINGS);
        RegisterItem.register("royal_guardian_boots", ROYAL_GUARDIAN_BOOTS);

        RegisterItem.register("queen_scale_battle_axe", QUEEN_SCALE_BATTLE_AXE);
        RegisterItem.register("queen_scale_helmet", QUEEN_SCALE_HELMET);
        RegisterItem.register("queen_scale_chestplate", QUEEN_SCALE_CHESTPLATE);
        RegisterItem.register("queen_scale_leggings", QUEEN_SCALE_LEGGINGS);
        RegisterItem.register("queen_scale_boots", QUEEN_SCALE_BOOTS);

        RegisterItem.register("fairy_sword", FAIRY_SWORD);
        RegisterItem.register("nightmare_sword", NIGHTMARE_SWORD);
        RegisterItem.register("poison_sword", POISON_SWORD);
        RegisterItem.register("rat_sword", RAT_SWORD);

        RegisterItem.register("ultimate_bow", ULTIMATE_BOW);
        RegisterItem.register("thunder_staff", THUNDER_STAFF);

        RegisterItem.register("raw_corndog", RAW_CORNDOG);
        RegisterItem.register("cooked_corndog", COOKED_CORNDOG);
        RegisterItem.register("raw_bacon", RAW_BACON);
        RegisterItem.register("cooked_bacon", COOKED_BACON);
        RegisterItem.register("corn", CORN);
        RegisterItem.register("tomato", TOMATO);
        RegisterItem.register("lettuce", LETTUCE);
        RegisterItem.register("cheese", CHEESE);
        RegisterItem.register("garden_salad", GARDEN_SALAD);
        RegisterItem.register("blt", BLT);
    }
}