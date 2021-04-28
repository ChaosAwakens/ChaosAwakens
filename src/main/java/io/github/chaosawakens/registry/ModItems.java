package io.github.chaosawakens.registry;

import io.github.chaosawakens.enums.ArmorMaterials;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.enums.ToolMaterials;
import io.github.chaosawakens.items.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	
    // FOOD
    public static final Food FOOD_RAW_CORNDOG = new Food.Builder().hunger(4).saturation(0.6F).build();
    public static final Food FOOD_COOKED_CORNDOG = new Food.Builder().hunger(14).saturation(1.5F).build();
    public static final Food FOOD_RAW_BACON = new Food.Builder().hunger(8).saturation(1.5F).build();
    public static final Food FOOD_COOKED_BACON = new Food.Builder().hunger(14).saturation(1.0F).build();
    public static final Food FOOD_CORN = new Food.Builder().hunger(6).saturation(0.75F).build();
    public static final Food FOOD_TOMATO = new Food.Builder().hunger(4).saturation(0.55F).build();
    public static final Food FOOD_LETTUCE = new Food.Builder().hunger(3).saturation(0.45F).build();
    public static final Food FOOD_CHEESE = new Food.Builder().hunger(4).saturation(0.5F).build();
    public static final Food FOOD_GARDEN_SALAD = new Food.Builder().hunger(10).saturation(0.95F).build();
    public static final Food FOOD_BLT = new Food.Builder().hunger(12).saturation(0.95F).build();
    public static final Food FOOD_STRAWBERRY = new Food.Builder().hunger(2).saturation(0.65F).build();
    public static final Food FOOD_RADISH = new Food.Builder().hunger(2).saturation(0.45F).build();
    public static final Food FOOD_RADISH_STEW = new Food.Builder().hunger(8).saturation(6.4F).build();
    public static final Food FOOD_CHERRIES = new Food.Builder().hunger(3).saturation(0.45F).build();
    public static final Food FOOD_SPARK_FISH = new Food.Builder().hunger(3).saturation(0.2F).build();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

    // FOODS
    public static final RegistryObject<Item> RAW_CORNDOG = ITEMS.register("raw_corndog", () -> new Item(new Item.Properties().food(ModItems.FOOD_RAW_CORNDOG).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> COOKED_CORNDOG = ITEMS.register("cooked_corndog", () -> new Item(new Item.Properties().food(ModItems.FOOD_COOKED_CORNDOG).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> RAW_BACON = ITEMS.register("raw_bacon", () -> new Item(new Item.Properties().food(ModItems.FOOD_RAW_BACON).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> COOKED_BACON = ITEMS.register("cooked_bacon", () -> new Item(new Item.Properties().food(ModItems.FOOD_COOKED_BACON).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new Item(new Item.Properties().food(ModItems.FOOD_CORN).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties().food(ModItems.FOOD_TOMATO).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry", () -> new Item(new Item.Properties().food(ModItems.FOOD_STRAWBERRY).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> RADISH = ITEMS.register("radish", () -> new Item(new Item.Properties().food(ModItems.FOOD_RADISH).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> RADISH_STEW = ITEMS.register("radish_stew", () -> new Item(new Item.Properties().food(ModItems.FOOD_RADISH_STEW).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce", () -> new Item(new Item.Properties().food(ModItems.FOOD_LETTUCE).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item(new Item.Properties().food(ModItems.FOOD_CHEESE).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> GARDEN_SALAD = ITEMS.register("garden_salad", () -> new SoupItem(new Item.Properties().food(ModItems.FOOD_GARDEN_SALAD).maxStackSize(1).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> BLT = ITEMS.register("blt", () -> new Item(new Item.Properties().food(ModItems.FOOD_BLT).group(ModItemGroups.itemsItemGroup)));

    public static final RegistryObject<Item> GREEN_FISH = ITEMS.register("green_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> ROCK_FISH = ITEMS.register("rock_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> WOOD_FISH = ITEMS.register("wood_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> SPARK_FISH = ITEMS.register("spark_fish", () -> new Item(new Item.Properties().food(ModItems.FOOD_SPARK_FISH).group(ModItemGroups.itemsItemGroup)));

    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> APPLE_SEEDS = ITEMS.register("apple_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));

    public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));

    // GEMST0NES
    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> TIGERS_EYE = ITEMS.register("tigers_eye", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> URANIUM_NUGGET = ITEMS.register("uranium_nugget", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));
    public static final RegistryObject<Item> ALUMINIUM_INGOT = ITEMS.register("aluminium_ingot", () -> new Item(new Item.Properties().group(ModItemGroups.itemsItemGroup)));

    // TOOLS
    // Ultimate
    public static final RegistryObject<EnchantedSword> ULTIMATE_SWORD = ITEMS.register("ultimate_sword", () -> new EnchantedSword(ToolMaterials.TOOL_ULTIMATE, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.SHARPNESS,Enchantments.SMITE,Enchantments.BANE_OF_ARTHROPODS,Enchantments.KNOCKBACK,Enchantments.LOOTING,Enchantments.UNBREAKING,Enchantments.FIRE_ASPECT},new int[]{5,5,5,3,3,2,2}));
    public static final RegistryObject<EnchantedShovel> ULTIMATE_SHOVEL = ITEMS.register("ultimate_shovel", () -> new EnchantedShovel(ToolMaterials.TOOL_ULTIMATE, 1.5F, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.EFFICIENCY,Enchantments.FORTUNE,Enchantments.UNBREAKING},new int[]{5,5,2}));
    public static final RegistryObject<EnchantedPickaxe> ULTIMATE_PICKAXE = ITEMS.register("ultimate_pickaxe", () -> new EnchantedPickaxe(ToolMaterials.TOOL_ULTIMATE, 1, -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.EFFICIENCY,Enchantments.FORTUNE,Enchantments.UNBREAKING},new int[]{5,5,2}));
    public static final RegistryObject<EnchantedAxe> ULTIMATE_AXE = ITEMS.register("ultimate_axe", () -> new EnchantedAxe(ToolMaterials.TOOL_ULTIMATE, 5, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.EFFICIENCY,Enchantments.FORTUNE,Enchantments.UNBREAKING},new int[]{5,5,2}));
    public static final RegistryObject<EnchantedHoe> ULTIMATE_HOE = ITEMS.register("ultimate_hoe", () -> new EnchantedHoe(ToolMaterials.TOOL_ULTIMATE, -36, 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.EFFICIENCY,Enchantments.FORTUNE,Enchantments.UNBREAKING},new int[]{5,5,2}));

    // Nightmare Sword
    public static final RegistryObject<EnchantedSword> NIGHTMARE_SWORD = ITEMS.register("nightmare_sword", () -> new EnchantedSword(ToolMaterials.TOOL_NIGHTMARE, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.SHARPNESS,Enchantments.KNOCKBACK,Enchantments.FIRE_ASPECT},new int[]{1,3,1}));

    // Emerald
    public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ShovelItem> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new ShovelItem(ToolMaterials.TOOL_EMERALD, 1.5F, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<EnchantedPickaxe> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new EnchantedPickaxe(ToolMaterials.TOOL_EMERALD, 1, -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.SILK_TOUCH},new int[]{1}));
    public static final RegistryObject<AxeItem> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new AxeItem(ToolMaterials.TOOL_EMERALD, 5, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<HoeItem> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new HoeItem(ToolMaterials.TOOL_EMERALD, -6, 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Misc. Swords
    public static final RegistryObject<EnchantedSword> EXPERIENCE_SWORD = ITEMS.register("experience_sword", () -> new EnchantedSword(ToolMaterials.TOOL_EMERALD, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.SHARPNESS,Enchantments.UNBREAKING,Enchantments.MENDING},new int[]{2,3,1}));
    public static final RegistryObject<SwordItem> POISON_SWORD = ITEMS.register("poison_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<SwordItem> RAT_SWORD = ITEMS.register("rat_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<SwordItem> FAIRY_SWORD = ITEMS.register("fairy_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<SwordItem> BIG_HAMMER = ITEMS.register("big_hammer", () -> new SwordItem(ToolMaterials.TOOL_AMETHYST, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Ruby
    public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(ToolMaterials.TOOL_RUBY, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(ToolMaterials.TOOL_RUBY, 1.5F, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_RUBY, 1, -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(ToolMaterials.TOOL_RUBY, 5, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(ToolMaterials.TOOL_RUBY, -16, 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Amethyst
    public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new SwordItem(ToolMaterials.TOOL_AMETHYST, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ShovelItem> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel", () -> new ShovelItem(ToolMaterials.TOOL_AMETHYST, 1.5F, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<PickaxeItem> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_AMETHYST, 1, -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<AxeItem> AMETHYST_AXE = ITEMS.register("amethyst_axe", () -> new AxeItem(ToolMaterials.TOOL_AMETHYST, 5, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<HoeItem> AMETHYST_HOE = ITEMS.register("amethyst_hoe", () -> new HoeItem(ToolMaterials.TOOL_AMETHYST, -11, 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Tiger's Eye
    public static final RegistryObject<SwordItem> TIGERS_EYE_SWORD = ITEMS.register("tigers_eye_sword", () -> new SwordItem(ToolMaterials.TOOL_TIGERS_EYE, 3, -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ShovelItem> TIGERS_EYE_SHOVEL = ITEMS.register("tigers_eye_shovel", () -> new ShovelItem(ToolMaterials.TOOL_TIGERS_EYE, 1.5F, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<PickaxeItem> TIGERS_EYE_PICKAXE = ITEMS.register("tigers_eye_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_TIGERS_EYE, 1, -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<AxeItem> TIGERS_EYE_AXE = ITEMS.register("tigers_eye_axe", () -> new AxeItem(ToolMaterials.TOOL_TIGERS_EYE, 5, -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<HoeItem> TIGERS_EYE_HOE = ITEMS.register("tigers_eye_hoe", () -> new HoeItem(ToolMaterials.TOOL_TIGERS_EYE, -8, 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // STAFFS/MISC WEAPONS
    public static final RegistryObject<ThunderStaffItem> THUNDER_STAFF = ITEMS.register("thunder_staff", () -> new ThunderStaffItem(new Item.Properties().group(ModItemGroups.equipmentItemGroup).maxDamage(50)));

    // ARMOR
    // Ultimate
    public static final RegistryObject<EnchantedArmor> ULTIMATE_HELMET = ITEMS.register("ultimate_helmet", () -> new EnchantedArmor(ArmorMaterials.ULTIMATE, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.FIRE_PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.PROJECTILE_PROTECTION,Enchantments.RESPIRATION,Enchantments.AQUA_AFFINITY},new int[]{5,5,5,5,3,1}));
    public static final RegistryObject<EnchantedArmor> ULTIMATE_CHESTPLATE = ITEMS.register("ultimate_chestplate", () -> new EnchantedArmor(ArmorMaterials.ULTIMATE, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.FIRE_PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{5,5,5,5}));
    public static final RegistryObject<EnchantedArmor> ULTIMATE_LEGGINGS = ITEMS.register("ultimate_leggings", () -> new EnchantedArmor(ArmorMaterials.ULTIMATE, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.FIRE_PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{5,5,5,5}));
    public static final RegistryObject<EnchantedArmor> ULTIMATE_BOOTS = ITEMS.register("ultimate_boots", () -> new EnchantedArmor(ArmorMaterials.ULTIMATE, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.FIRE_PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.PROJECTILE_PROTECTION,Enchantments.FEATHER_FALLING},new int[]{5,5,5,5,3}));

    // Emerald
    public static final RegistryObject<ArmorItem> EMERALD_HELMET = ITEMS.register("emerald_helmet", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> EMERALD_BOOTS = ITEMS.register("emerald_boots", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Experience
    public static final RegistryObject<EnchantedArmor> EXPERIENCE_HELMET = ITEMS.register("experience_helmet", () -> new EnchantedArmor(ArmorMaterials.EXPERIENCE, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.MENDING},new int[]{2,1,1}));
    public static final RegistryObject<EnchantedArmor> EXPERIENCE_CHESTPLATE = ITEMS.register("experience_chestplate", () -> new EnchantedArmor(ArmorMaterials.EXPERIENCE, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.MENDING},new int[]{2,1,1}));
    public static final RegistryObject<EnchantedArmor> EXPERIENCE_LEGGINGS = ITEMS.register("experience_leggings", () -> new EnchantedArmor(ArmorMaterials.EXPERIENCE, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.MENDING},new int[]{2,1,1}));
    public static final RegistryObject<EnchantedArmor> EXPERIENCE_BOOTS = ITEMS.register("experience_boots", () -> new EnchantedArmor(ArmorMaterials.EXPERIENCE, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.FEATHER_FALLING,Enchantments.MENDING},new int[]{2,1,1,1}));

    // Ruby
    public static final RegistryObject<ArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet", () -> new ArmorItem(ArmorMaterials.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate", () -> new ArmorItem(ArmorMaterials.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> RUBY_LEGGINGS = ITEMS.register("ruby_leggings", () -> new ArmorItem(ArmorMaterials.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots", () -> new ArmorItem(ArmorMaterials.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Amethyst
    public static final RegistryObject<ArmorItem> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new ArmorItem(ArmorMaterials.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new ArmorItem(ArmorMaterials.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> AMETHYST_LEGGINGS = ITEMS.register("amethyst_leggings", () -> new ArmorItem(ArmorMaterials.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> AMETHYST_BOOTS = ITEMS.register("amethyst_boots", () -> new ArmorItem(ArmorMaterials.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Tiger's Eye
    public static final RegistryObject<ArmorItem> TIGERS_EYE_HELMET = ITEMS.register("tigers_eye_helmet", () -> new ArmorItem(ArmorMaterials.TIGERS_EYE, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> TIGERS_EYE_CHESTPLATE = ITEMS.register("tigers_eye_chestplate", () -> new ArmorItem(ArmorMaterials.TIGERS_EYE, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> TIGERS_EYE_LEGGINGS = ITEMS.register("tigers_eye_leggings", () -> new ArmorItem(ArmorMaterials.TIGERS_EYE, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> TIGERS_EYE_BOOTS = ITEMS.register("tigers_eye_boots", () -> new ArmorItem(ArmorMaterials.TIGERS_EYE, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Lapis Lazuli
    public static final RegistryObject<EnchantedArmor> LAPIS_HELMET = ITEMS.register("lapis_helmet", () -> new EnchantedArmor(ArmorMaterials.LAPIS, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.PROJECTILE_PROTECTION,Enchantments.RESPIRATION,Enchantments.AQUA_AFFINITY},new int[]{1,1,1,1}));
    public static final RegistryObject<EnchantedArmor> LAPIS_CHESTPLATE = ITEMS.register("lapis_chestplate", () -> new EnchantedArmor(ArmorMaterials.LAPIS, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{1,1}));
    public static final RegistryObject<EnchantedArmor> LAPIS_LEGGINGS = ITEMS.register("lapis_leggings", () -> new EnchantedArmor(ArmorMaterials.LAPIS, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{1,1}));
    public static final RegistryObject<EnchantedArmor> LAPIS_BOOTS = ITEMS.register("lapis_boots", () -> new EnchantedArmor(ArmorMaterials.LAPIS, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{1,1}));
}
