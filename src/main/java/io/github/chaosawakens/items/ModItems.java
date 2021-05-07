package io.github.chaosawakens.items;

import io.github.chaosawakens.config.Config;
import io.github.chaosawakens.items.custom.ThunderStaff;
import io.github.chaosawakens.items.util.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

import javax.tools.Tool;

public class ModItems implements ModInitializer {
    static Config config = AutoConfig.getConfigHolder(Config.class).getConfig();

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
    public static final Item STRAWBERRY = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item RADISH = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item RADISH_STEW = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CHERRIES = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SPARK_FISH = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item LAVA_EEL = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CRAB_MEAT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item COOKED_CRAB_MEAT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SEAFOOD_PATTY = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item PEACH = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item PEACOCK_LEG = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item COOKED_PEACOCK_LEG = new Item(new Item.Settings().group(ItemGroup.MISC));

    public static final Item BUTTER = new Item(new Item.Settings().group(ItemGroup.MISC));

    //Gemstones
    public static final Item AMETHYST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item RUBY = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item TIGERS_EYE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item TITANIUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item TITANIUM_NUGGET = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item URANIUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item URANIUM_NUGGET = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item ALUMINIUM_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item ALUMINIUM_NUGGET = new Item(new Item.Settings().group(ItemGroup.MISC));


    public static final Item DRUID_KEY = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SALT = new Item(new Item.Settings().group(ItemGroup.MISC));

    //Misc Items

    //Ultimate
    public static final ToolMaterial ULTIMATE_TOOL_MATERIAL = new GenericToolMaterial(5, 3000, 15, 36, 64, null);
    public static final ArmorMaterial ULTIMATE_ARMOR_MATERIAL = new GenericArmorMaterial(48, new int[]{6, 10, 12, 6}, 64, (int) 2.5f, null, "ultimate");

    public static final ToolItem ULTIMATE_SWORD = new SwordItem(ULTIMATE_TOOL_MATERIAL, (int) (config.ultimate.ultimateSwordDamage - 37), -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem ULTIMATE_SHOVEL = new ShovelItem(ULTIMATE_TOOL_MATERIAL, (int) (config.ultimate.ultimateAxeDamage - 37), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem ULTIMATE_PICKAXE = new GenericPickaxeItem(ULTIMATE_TOOL_MATERIAL, (int) (config.ultimate.ultimatePickaxeDamage - 37), -2.8f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem ULTIMATE_AXE = new GenericAxeItem(ULTIMATE_TOOL_MATERIAL, (int) (config.ultimate.ultimateAxeDamage - 37), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem ULTIMATE_HOE = new GenericHoeItem(ULTIMATE_TOOL_MATERIAL, (int) (config.ultimate.ultimateHoeDamage - 37), 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item ULTIMATE_HELMET = new ArmorItem(ULTIMATE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ULTIMATE_CHESTPLATE = new ArmorItem(ULTIMATE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ULTIMATE_LEGGINGS = new ArmorItem(ULTIMATE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ULTIMATE_BOOTS = new ArmorItem(ULTIMATE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Emerald
    public static final ToolMaterial EMERALD_TOOL_MATERIAL = new GenericToolMaterial(3, 2000, 10, 6, 24, Items.EMERALD);
    public static final ArmorMaterial EMERALD_ARMOR_MATERIAL = new GenericArmorMaterial(35 , new int[]{3, 6, 8, 3}, 24, 2, Items.EMERALD, "emerald");

    public static final ToolItem EMERALD_SWORD = new SwordItem(EMERALD_TOOL_MATERIAL, (int) (config.emerald.emeraldSwordDamage - 7), -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem EMERALD_SHOVEL = new ShovelItem(EMERALD_TOOL_MATERIAL, (int) (config.emerald.emeraldShovelDamage - 7), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_PICKAXE = new GenericPickaxeItem(EMERALD_TOOL_MATERIAL, (int) (config.emerald.emeraldPickaxeDamage - 7), -2.8f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_AXE = new GenericAxeItem(EMERALD_TOOL_MATERIAL, (int) (config.emerald.emeraldAxeDamage - 7), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem EMERALD_HOE = new GenericHoeItem(EMERALD_TOOL_MATERIAL, (int) (config.emerald.emeraldHoeDamage - 7), 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item EMERALD_HELMET = new ArmorItem(EMERALD_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_CHESTPLATE = new ArmorItem(EMERALD_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_LEGGINGS = new ArmorItem(EMERALD_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EMERALD_BOOTS = new ArmorItem(EMERALD_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Ruby
    public static final ToolMaterial RUBY_TOOL_MATERIAL = new GenericToolMaterial(4, 1800, 11, 16, 22, ModItems.RUBY);
    public static final ArmorMaterial RUBY_ARMOR_MATERIAL = new GenericArmorMaterial(37, new int[]{4, 8, 9, 4}, 22, (int) 2.5f, ModItems.RUBY, "ruby");

    public static final ToolItem RUBY_SWORD = new SwordItem(RUBY_TOOL_MATERIAL, (int) (config.ruby.rubySwordDamage - 17), -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem RUBY_SHOVEL = new ShovelItem(RUBY_TOOL_MATERIAL, (int) (config.ruby.rubyShovelDamage - 17), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_PICKAXE = new GenericPickaxeItem(RUBY_TOOL_MATERIAL, (int) (config.ruby.rubyPickaxeDamage - 17), -2.8f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_AXE = new GenericAxeItem(RUBY_TOOL_MATERIAL, (int) (config.ruby.rubyAxeDamage - 17), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem RUBY_HOE = new GenericHoeItem(RUBY_TOOL_MATERIAL, (int) (config.ruby.rubyHoeDamage - 17), 0.0f, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item RUBY_HELMET = new ArmorItem(RUBY_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_CHESTPLATE = new ArmorItem(RUBY_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_LEGGINGS = new ArmorItem(RUBY_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item RUBY_BOOTS = new ArmorItem(RUBY_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Amethyst
    public static final ToolMaterial AMETHYST_TOOL_MATERIAL = new GenericToolMaterial(3, 2000, 11, 11, 18, ModItems.AMETHYST);
    public static final ArmorMaterial AMETHYST_ARMOR_MATERIAL = new GenericArmorMaterial(39, new int[]{3, 7, 8, 4}, 18, 2, ModItems.AMETHYST, "amethyst");

    public static final ToolItem AMETHYST_SWORD = new SwordItem(AMETHYST_TOOL_MATERIAL, (int) (config.amethyst.amethystSwordDamage - 12), -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem AMETHYST_SHOVEL = new ShovelItem(AMETHYST_TOOL_MATERIAL, (int) (config.amethyst.amethystShovelDamage - 12), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_PICKAXE = new GenericPickaxeItem(AMETHYST_TOOL_MATERIAL, (int) (config.amethyst.amethystPickaxeDamage - 12), -2.8f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_AXE = new GenericAxeItem(AMETHYST_TOOL_MATERIAL, (int) (config.amethyst.amethystAxeDamage - 12), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem AMETHYST_HOE = new GenericHoeItem(AMETHYST_TOOL_MATERIAL, (int) (config.amethyst.amethystHoeDamage - 12), 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item AMETHYST_HELMET = new ArmorItem(AMETHYST_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_CHESTPLATE = new ArmorItem(AMETHYST_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_LEGGINGS = new ArmorItem(AMETHYST_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item AMETHYST_BOOTS = new ArmorItem(AMETHYST_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Tiger's Eye
    public static final ToolMaterial TIGERS_EYE_TOOL_MATERIAL = new GenericToolMaterial(3, 600, 11, 8, 20, ModItems.TIGERS_EYE);
    public static final ArmorMaterial TIGERS_EYE_ARMOR_MATERIAL = new GenericArmorMaterial(36, new int[]{4, 7, 8, 4}, 20, (int) 2.5f, ModItems.TIGERS_EYE, "tigers_eye");

    public static final ToolItem TIGERS_EYE_SWORD = new SwordItem(TIGERS_EYE_TOOL_MATERIAL, (int) (config.tigersEye.tigersEyeSwordDamage - 9), -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem TIGERS_EYE_SHOVEL = new ShovelItem(TIGERS_EYE_TOOL_MATERIAL, (int) (config.tigersEye.tigersEyeShovelDamage - 9), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem TIGERS_EYE_PICKAXE = new GenericPickaxeItem(TIGERS_EYE_TOOL_MATERIAL, (int) (config.tigersEye.tigersEyePickaxeDamage - 9), -2.8f, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem TIGERS_EYE_AXE = new GenericAxeItem(TIGERS_EYE_TOOL_MATERIAL, (int) (config.tigersEye.tigersEyeAxeDamage - 9), -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ToolItem TIGERS_EYE_HOE = new GenericHoeItem(TIGERS_EYE_TOOL_MATERIAL, (int) (config.tigersEye.tigersEyeHoeDamage - 9), 0, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item TIGERS_EYE_HELMET = new ArmorItem(TIGERS_EYE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item TIGERS_EYE_CHESTPLATE = new ArmorItem(TIGERS_EYE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item TIGERS_EYE_LEGGINGS = new ArmorItem(TIGERS_EYE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item TIGERS_EYE_BOOTS = new ArmorItem(TIGERS_EYE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Experience
    public static final ToolMaterial EXPERIENCE_TOOL_MATERIAL = new GenericToolMaterial(500, 5, 3.0F, 2, 15, null);
    public static final ArmorMaterial EXPERIENCE_ARMOR_MATERIAL = new GenericArmorMaterial(39, new int[]{4, 7, 9, 5}, 32, (int) 2.5f, Items.EMERALD, "experience");

    public static final ToolItem EXPERIENCE_SWORD = new SwordItem(EXPERIENCE_TOOL_MATERIAL, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item EXPERIENCE_HELMET = new ArmorItem(EXPERIENCE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EXPERIENCE_CHESTPLATE = new ArmorItem(EXPERIENCE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EXPERIENCE_LEGGINGS = new ArmorItem(EXPERIENCE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item EXPERIENCE_BOOTS = new ArmorItem(EXPERIENCE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Royal Guardian
    public static final ToolMaterial ROYAL_GUARDIAN_TOOL_MATERIAL = new GenericToolMaterial(500, 5, 3.0F, 2, 15, null);
    public static final ArmorMaterial ROYAL_GUARDIAN_ARMOR_MATERIAL = new GenericArmorMaterial(30, new int[]{3, 6, 8, 3}, 37, 10, null, "royal_guardian");

    public static final ToolItem ROYAL_GUARDIAN_SWORD = new SwordItem(ROYAL_GUARDIAN_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item ROYAL_GUARDIAN_HELMET = new ArmorItem(ROYAL_GUARDIAN_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROYAL_GUARDIAN_CHESTPLATE = new ArmorItem(ROYAL_GUARDIAN_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROYAL_GUARDIAN_LEGGINGS = new ArmorItem(ROYAL_GUARDIAN_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item ROYAL_GUARDIAN_BOOTS = new ArmorItem(ROYAL_GUARDIAN_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Queen Scale
    public static final ToolMaterial QUEEN_SCALE_TOOL_MATERIAL = new GenericToolMaterial(500, 5, 3.0F, 2, 15, null);
    public static final ArmorMaterial QUEEN_SCALE_ARMOR_MATERIAL = new GenericArmorMaterial(30, new int[]{10, 15, 15, 10}, 37, 10, null, "queen_scale");

    public static final ToolItem QUEEN_SCALE_BATTLE_AXE = new SwordItem(QUEEN_SCALE_TOOL_MATERIAL, 0, 0, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Item QUEEN_SCALE_HELMET = new ArmorItem(QUEEN_SCALE_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item QUEEN_SCALE_CHESTPLATE = new ArmorItem(QUEEN_SCALE_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item QUEEN_SCALE_LEGGINGS = new ArmorItem(QUEEN_SCALE_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item QUEEN_SCALE_BOOTS = new ArmorItem(QUEEN_SCALE_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Lava Eel
    public static final ArmorMaterial LAVA_EEL_ARMOR_MATERIAL = new GenericArmorMaterial(34, new int[]{2, 5, 7, 2}, 9, 0, ModItems.LAVA_EEL, "lava_eel");

    public static final Item LAVA_EEL_HELMET = new ArmorItem(LAVA_EEL_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item LAVA_EEL_CHESTPLATE = new ArmorItem(LAVA_EEL_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item LAVA_EEL_LEGGINGS = new ArmorItem(LAVA_EEL_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item LAVA_EEL_BOOTS = new ArmorItem(LAVA_EEL_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Lapis Lazuli
    public static final ArmorMaterial LAPIS_LAZULI_ARMOR_MATERIAL = new GenericArmorMaterial(27, new int[]{2, 5, 7, 2}, 24, (int) 1.75f, Items.LAPIS_BLOCK, "lapis_lazuli");

    public static final Item LAPIS_LAZULI_HELMET = new ArmorItem(LAPIS_LAZULI_ARMOR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item LAPIS_LAZULI_CHESTPLATE = new ArmorItem(LAPIS_LAZULI_ARMOR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item LAPIS_LAZULI_LEGGINGS = new ArmorItem(LAPIS_LAZULI_ARMOR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static final Item LAPIS_LAZULI_BOOTS = new ArmorItem(LAPIS_LAZULI_ARMOR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

    //Misc Weapons
    public static final ToolMaterial NIGHTMARE_TOOL_MATERIAL = new GenericToolMaterial(500, 5, 3.0F, 2, 15, null);

    public static final ToolItem FAIRY_SWORD = new SwordItem(EMERALD_TOOL_MATERIAL, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem NIGHTMARE_SWORD = new SwordItem(NIGHTMARE_TOOL_MATERIAL, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem POISON_SWORD = new SwordItem(EMERALD_TOOL_MATERIAL, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem RAT_SWORD = new SwordItem(EMERALD_TOOL_MATERIAL, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem BIG_HAMMER = new SwordItem(AMETHYST_TOOL_MATERIAL, 3, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    public static final ToolItem PRISMATIC_REAPER = new SwordItem(ULTIMATE_TOOL_MATERIAL, (int) (config.misc.prismaticReaperDamage - 9), -1.9f, new Item.Settings().group(ItemGroup.COMBAT));

    //Ranged Weapons
    public static final BowItem ULTIMATE_BOW = new GenericBowItem(new Item.Settings().group(ItemGroup.COMBAT), null, 5);
    public static final ToolMaterial THUNDER_STAFF_TOOL_MATERIAL = new GenericToolMaterial(500, 5, 3.0F, 2, 15, null);
    public static final Item THUNDER_STAFF = new ThunderStaff(new Item.Settings().group(ItemGroup.COMBAT));
    //Ray gun



    @Override
    public void onInitialize() {
        RegisterItem.register("corndog", RAW_CORNDOG);
        RegisterItem.register("cooked_corndog", COOKED_CORNDOG);
        RegisterItem.register("bacon", RAW_BACON);
        RegisterItem.register("cooked_bacon", COOKED_BACON);
        RegisterItem.register("corn", CORN);
        RegisterItem.register("tomato", TOMATO);
        RegisterItem.register("lettuce", LETTUCE);
        RegisterItem.register("cheese", CHEESE);
        RegisterItem.register("garden_salad", GARDEN_SALAD);
        RegisterItem.register("blt", BLT);
        RegisterItem.register("strawberry", STRAWBERRY);
        RegisterItem.register("radish", RADISH);
        RegisterItem.register("radish_stew", RADISH_STEW);
        RegisterItem.register("cherries", CHERRIES);
        RegisterItem.register("spark_fish", SPARK_FISH);
        RegisterItem.register("lava_eel", LAVA_EEL);
        RegisterItem.register("crab_meat", CRAB_MEAT);
        RegisterItem.register("cooked_crab_meat", COOKED_CRAB_MEAT);
        RegisterItem.register("seafood_patty", SEAFOOD_PATTY);
        RegisterItem.register("peach", PEACH);
        RegisterItem.register("peacock_leg", PEACOCK_LEG);
        RegisterItem.register("cooked_peacock_leg", COOKED_PEACOCK_LEG);

        RegisterItem.register("butter", BUTTER);

        RegisterItem.register("aluminium_ingot", ALUMINIUM_INGOT);
        RegisterItem.register("aluminium_nugget", ALUMINIUM_NUGGET);
        RegisterItem.register("druid_key", DRUID_KEY);
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

        RegisterItem.register("tigers_eye", TIGERS_EYE);
        RegisterItem.register("tigers_eye_sword", TIGERS_EYE_SWORD);
        RegisterItem.register("tigers_eye_shovel", TIGERS_EYE_SHOVEL);
        RegisterItem.register("tigers_eye_pickaxe", TIGERS_EYE_PICKAXE);
        RegisterItem.register("tigers_eye_axe", TIGERS_EYE_AXE);
        RegisterItem.register("tigers_eye_hoe", TIGERS_EYE_HOE);
        RegisterItem.register("tigers_eye_helmet", TIGERS_EYE_HELMET);
        RegisterItem.register("tigers_eye_chestplate", TIGERS_EYE_CHESTPLATE);
        RegisterItem.register("tigers_eye_leggings", TIGERS_EYE_LEGGINGS);
        RegisterItem.register("tigers_eye_boots", TIGERS_EYE_BOOTS);

        RegisterItem.register("experience_sword", EXPERIENCE_SWORD);
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

        RegisterItem.register("lava_eel_helmet", LAVA_EEL_HELMET);
        RegisterItem.register("lava_eel_chestplate", LAVA_EEL_CHESTPLATE);
        RegisterItem.register("lava_eel_leggings", LAVA_EEL_LEGGINGS);
        RegisterItem.register("lava_eel_boots", LAVA_EEL_BOOTS);

        RegisterItem.register("lapis_helmet", LAPIS_LAZULI_HELMET);
        RegisterItem.register("lapis_chestplate", LAPIS_LAZULI_CHESTPLATE);
        RegisterItem.register("lapis_leggings", LAPIS_LAZULI_LEGGINGS);
        RegisterItem.register("lapis_boots", LAPIS_LAZULI_BOOTS);

        RegisterItem.register("fairy_sword", FAIRY_SWORD);
        RegisterItem.register("nightmare_sword", NIGHTMARE_SWORD);
        RegisterItem.register("poison_sword", POISON_SWORD);
        RegisterItem.register("rat_sword", RAT_SWORD);
        RegisterItem.register("big_hammer", BIG_HAMMER);
        RegisterItem.register("prismatic_reaper", PRISMATIC_REAPER);

        RegisterItem.register("ultimate_bow", ULTIMATE_BOW);
        RegisterItem.register("thunder_staff", THUNDER_STAFF);


    }
}