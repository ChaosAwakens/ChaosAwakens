package io.github.chaosawakens.registry;

import io.github.chaosawakens.config.CAConfig;
import io.github.chaosawakens.enums.ArmorMaterials;
import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.enums.ToolMaterials;
import io.github.chaosawakens.items.*;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
	
    // ORESPAWN FOOD
    public static final Food FOOD_RAW_CORNDOG = new Food.Builder().hunger(4).saturation(0.6F).build();
    public static final Food FOOD_COOKED_CORNDOG = new Food.Builder().hunger(14).saturation(1.5F).build();
    public static final Food FOOD_RAW_BACON = new Food.Builder().hunger(8).saturation(1.5F).meat().build();
    public static final Food FOOD_COOKED_BACON = new Food.Builder().hunger(14).saturation(1.0F).meat().effect(new EffectInstance(Effects.STRENGTH, 2000, 0), 1.0F).effect(new EffectInstance(Effects.REGENERATION, 2000, 0), 1.0F).build();
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
    public static final Food FOOD_SPARK_FISH = new Food.Builder().hunger(3).saturation(0.2F).setAlwaysEdible().effect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0), 1.0F).build();
    public static final Food FOOD_LAVA_EEL = new Food.Builder().hunger(2).saturation(0.6F).setAlwaysEdible().effect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0), 1.0F).build();
    public static final Food FOOD_CRAB_MEAT = new Food.Builder().hunger(4).saturation(0.25F).meat().build();
    public static final Food FOOD_COOKED_CRAB_MEAT = new Food.Builder().hunger(6).saturation(0.75F).meat().build();
    public static final Food FOOD_SEAFOOD_PATTY = new Food.Builder().hunger(16).saturation(2.35F).build();
    public static final Food FOOD_PEACH = new Food.Builder().hunger(4).saturation(0.55F).build();
    public static final Food FOOD_PEACOCK_LEG = new Food.Builder().hunger(6).saturation(0.7F).build();
    public static final Food FOOD_COOKED_PEACOCK_LEG = new Food.Builder().hunger(12).saturation(1.4F).build();

    // SHINY FOOD
    public static final Food FOOD_CANDYCANE = new Food.Builder().hunger(2).saturation(0.15F).setAlwaysEdible().build();
    public static final Food FOOD_GOLDEN_BREAD = new Food.Builder().hunger(8).saturation(0.8F).setAlwaysEdible().build();
    public static final Food FOOD_GOLDEN_CHICKEN = new Food.Builder().hunger(6).saturation(0.45F).meat().setAlwaysEdible().effect(new EffectInstance(Effects.STRENGTH, 600, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_TROPICAL_FISH = new Food.Builder().hunger(4).saturation(0.2F).setAlwaysEdible().effect(new EffectInstance(Effects.REGENERATION, 2400, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_COD = new Food.Builder().hunger(5).saturation(0.3F).setAlwaysEdible().effect(new EffectInstance(Effects.REGENERATION, 200, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_PORKCHOP = new Food.Builder().hunger(8).saturation(1.1F).meat().setAlwaysEdible().effect(new EffectInstance(Effects.STRENGTH, 600, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_MELON_SLICE = new Food.Builder().hunger(8).saturation(0.45F).setAlwaysEdible().build();
    public static final Food FOOD_GOLDEN_MUSHROOM_STEW = new Food.Builder().hunger(6).saturation(0.25F).setAlwaysEdible().effect(new EffectInstance(Effects.SPEED, 700, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_STEAK = new Food.Builder().hunger(8).saturation(1.1F).meat().setAlwaysEdible().effect(new EffectInstance(Effects.STRENGTH, 400, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_COOKIE = new Food.Builder().hunger(2).saturation(0.15F).setAlwaysEdible().effect(new EffectInstance(Effects.REGENERATION, 100, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_POTATO = new Food.Builder().hunger(6).saturation(0.65F).setAlwaysEdible().effect(new EffectInstance(Effects.REGENERATION, 400, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_PUMPKIN_PIE = new Food.Builder().hunger(8).saturation(9).setAlwaysEdible().effect(new EffectInstance(Effects.NIGHT_VISION, 600, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_FLESH = new Food.Builder().hunger(4).saturation(0.3F).setAlwaysEdible().effect(new EffectInstance(Effects.SATURATION, 40, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_PUFFERFISH = new Food.Builder().hunger(4).saturation(0.3F).setAlwaysEdible().effect(new EffectInstance(Effects.POISON, 100, 0), 1.0F).effect(new EffectInstance(Effects.NIGHT_VISION, 1800, 0), 1.0F).effect(new EffectInstance(Effects.WATER_BREATHING, 3600, 0), 1.0F).build();
    public static final Food FOOD_GOLDEN_SALMON = new Food.Builder().hunger(8).saturation(0.85F).setAlwaysEdible().effect(new EffectInstance(Effects.JUMP_BOOST, 1800, 0), 1.0F).effect(new EffectInstance(Effects.SPEED, 1800, 1), 1.0F).build();
    public static final Food FOOD_GOLDEN_CANDYCANE = new Food.Builder().hunger(4).saturation(0.25F).setAlwaysEdible().effect(new EffectInstance(Effects.HASTE, 300, 0), 1.0F).effect(new EffectInstance(Effects.JUMP_BOOST, 600, 0), 1.0F).effect(new EffectInstance(Effects.SPEED, 600, 0), 1.0F).build();
    public static final Food FOOD_ULTIMATE_APPLE = new Food.Builder().hunger(10).saturation(1.5F).setAlwaysEdible().effect(new EffectInstance(Effects.STRENGTH, 3900, 2), 1.0F).effect(new EffectInstance(Effects.REGENERATION, 4800, 3), 1.0F).effect(new EffectInstance(Effects.HEALTH_BOOST, 5400, 2), 1.0F).effect(new EffectInstance(Effects.RESISTANCE, 7200, 0), 1.0F).effect(new EffectInstance(Effects.FIRE_RESISTANCE, 7200, 0), 1.0F).build();
    public static final Food FOOD_ENCHANTED_GOLDEN_CARROT = new Food.Builder().hunger(8).saturation(0.85F).setAlwaysEdible().effect(new EffectInstance(Effects.REGENERATION, 600, 2), 1.0F).effect(new EffectInstance(Effects.NIGHT_VISION, 1200, 0), 1.0F).effect(new EffectInstance(Effects.ABSORPTION, 1200, 0), 1.0F).build();
    public static final Food FOOD_ENCHANTED_GOLDEN_STEAK = new Food.Builder().hunger(10).saturation(1.2F).setAlwaysEdible().effect(new EffectInstance(Effects.ABSORPTION, 900, 0), 1.0F).effect(new EffectInstance(Effects.STRENGTH, 1800, 1), 1.0F).effect(new EffectInstance(Effects.REGENERATION, 1800, 0), 1.0F).build();
    public static final Food FOOD_ENCHANTED_GOLDEN_COD = new Food.Builder().hunger(8).saturation(0.85F).setAlwaysEdible().effect(new EffectInstance(Effects.REGENERATION, 600, 2), 1.0F).effect(new EffectInstance(Effects.WATER_BREATHING, 900, 0), 1.0F).build();
    public static final Food FOOD_ENCHANTED_GOLDEN_COOKIE = new Food.Builder().hunger(4).saturation(0.85F).setAlwaysEdible().effect(new EffectInstance(Effects.REGENERATION, 600, 1), 1.0F).effect(new EffectInstance(Effects.SPEED, 1000, 1), 1.0F).build();
    public static final Food FOOD_ENCHANTED_GOLDEN_CANDYCANE = new Food.Builder().hunger(6).saturation(0.3F).setAlwaysEdible().effect(new EffectInstance(Effects.STRENGTH, 300, 0), 1.0F).effect(new EffectInstance(Effects.REGENERATION, 300, 0), 1.0F).effect(new EffectInstance(Effects.HASTE, 600, 1), 1.0F).effect(new EffectInstance(Effects.JUMP_BOOST, 900, 1), 1.0F).effect(new EffectInstance(Effects.SPEED, 900, 1), 1.0F).build();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

    // ORESPAWN FOODS
    public static final RegistryObject<Item> CORNDOG = ITEMS.register("corndog", () -> new Item(new Item.Properties().food(ModItems.FOOD_RAW_CORNDOG).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> COOKED_CORNDOG = ITEMS.register("cooked_corndog", () -> new Item(new Item.Properties().food(ModItems.FOOD_COOKED_CORNDOG).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> BACON = ITEMS.register("bacon", () -> new Item(new Item.Properties().food(ModItems.FOOD_RAW_BACON).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> COOKED_BACON = ITEMS.register("cooked_bacon", () -> new Item(new Item.Properties().food(ModItems.FOOD_COOKED_BACON).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> PEACOCK_LEG = ITEMS.register("peacock_leg", () -> new Item(new Item.Properties().food(ModItems.FOOD_PEACOCK_LEG).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> COOKED_PEACOCK_LEG = ITEMS.register("cooked_peacock_leg", () -> new Item(new Item.Properties().food(ModItems.FOOD_COOKED_PEACOCK_LEG).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> CRAB_MEAT = ITEMS.register("crab_meat", () -> new Item(new Item.Properties().food(ModItems.FOOD_CRAB_MEAT).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> COOKED_CRAB_MEAT = ITEMS.register("cooked_crab_meat", () -> new Item(new Item.Properties().food(ModItems.FOOD_COOKED_CRAB_MEAT).group(ModItemGroups.foodItemGroup)));

    public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new Item(new Item.Properties().food(ModItems.FOOD_CORN).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties().food(ModItems.FOOD_TOMATO).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry", () -> new Item(new Item.Properties().food(ModItems.FOOD_STRAWBERRY).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> CHERRIES = ITEMS.register("cherries", () -> new Item(new Item.Properties().food(ModItems.FOOD_CHERRIES).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> PEACH = ITEMS.register("peach", () -> new Item(new Item.Properties().food(ModItems.FOOD_PEACH).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> RADISH = ITEMS.register("radish", () -> new Item(new Item.Properties().food(ModItems.FOOD_RADISH).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce", () -> new Item(new Item.Properties().food(ModItems.FOOD_LETTUCE).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item(new Item.Properties().food(ModItems.FOOD_CHEESE).group(ModItemGroups.foodItemGroup)));

    public static final RegistryObject<Item> RADISH_STEW = ITEMS.register("radish_stew", () -> new SoupItem(new Item.Properties().food(ModItems.FOOD_RADISH_STEW).maxStackSize(1).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GARDEN_SALAD = ITEMS.register("garden_salad", () -> new SoupItem(new Item.Properties().food(ModItems.FOOD_GARDEN_SALAD).maxStackSize(1).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> BLT = ITEMS.register("blt", () -> new Item(new Item.Properties().food(ModItems.FOOD_BLT).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> SEAFOOD_PATTY = ITEMS.register("seafood_patty", () -> new Item(new Item.Properties().food(ModItems.FOOD_SEAFOOD_PATTY).group(ModItemGroups.foodItemGroup)));

    public static final RegistryObject<Item> GREEN_FISH = ITEMS.register("green_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> ROCK_FISH = ITEMS.register("rock_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> WOOD_FISH = ITEMS.register("wood_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> SPARK_FISH = ITEMS.register("spark_fish", () -> new Item(new Item.Properties().food(ModItems.FOOD_SPARK_FISH).isImmuneToFire().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> LAVA_EEL = ITEMS.register("lava_eel", () -> new Item(new Item.Properties().food(ModItems.FOOD_LAVA_EEL).isImmuneToFire().group(ModItemGroups.foodItemGroup)));

    public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));

    public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> CHERRY_SEEDS = ITEMS.register("cherry_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> APPLE_SEEDS = ITEMS.register("apple_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> PEACH_SEED = ITEMS.register("peach_seed", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> LETTUCE_SEEDS = ITEMS.register("lettuce_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> RADISH_SEEDS = ITEMS.register("radish_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));

    public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));

    // SHINY FOOD
    public static final RegistryObject<Item> GOLDEN_MELON_SEEDS = ITEMS.register("golden_melon_seeds", () -> new Item(new Item.Properties().group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> CANDYCANE = ITEMS.register("candycane", () -> new Item(new Item.Properties().rarity(Rarity.COMMON).food(ModItems.FOOD_CANDYCANE).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_BREAD = ITEMS.register("golden_bread", () -> new Item(new Item.Properties().rarity(Rarity.COMMON).food(ModItems.FOOD_GOLDEN_BREAD).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_CHICKEN = ITEMS.register("golden_chicken", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_CHICKEN).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_TROPICAL_FISH = ITEMS.register("golden_tropical_fish", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_TROPICAL_FISH).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_COD = ITEMS.register("golden_cod", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_COD).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_PORKCHOP = ITEMS.register("golden_porkchop", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_PORKCHOP).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_MELON_SLICE = ITEMS.register("golden_melon_slice", () -> new Item(new Item.Properties().rarity(Rarity.COMMON).food(ModItems.FOOD_GOLDEN_MELON_SLICE).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_MUSHROOM_STEW = ITEMS.register("golden_mushroom_stew", () -> new SoupItem(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_MUSHROOM_STEW).maxStackSize(1).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_STEAK = ITEMS.register("golden_steak", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_STEAK).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_COOKIE = ITEMS.register("golden_cookie", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_COOKIE).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_POTATO = ITEMS.register("golden_potato", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_POTATO).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_PUMPKIN_PIE = ITEMS.register("golden_pumpkin_pie", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_PUMPKIN_PIE).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_FLESH = ITEMS.register("golden_flesh", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_FLESH).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_PUFFERFISH = ITEMS.register("golden_pufferfish", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_PUFFERFISH).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_SALMON = ITEMS.register("golden_salmon", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_SALMON).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<Item> GOLDEN_CANDYCANE = ITEMS.register("golden_candycane", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(ModItems.FOOD_GOLDEN_CANDYCANE).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<EnchantedItem> ULTIMATE_APPLE = ITEMS.register("ultimate_apple", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(ModItems.FOOD_ULTIMATE_APPLE).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<EnchantedItem> ENCHANTED_GOLDEN_CARROT = ITEMS.register("enchanted_golden_carrot", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(ModItems.FOOD_ENCHANTED_GOLDEN_CARROT).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<EnchantedItem> ENCHANTED_GOLDEN_STEAK = ITEMS.register("enchanted_golden_steak", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(ModItems.FOOD_ENCHANTED_GOLDEN_STEAK).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<EnchantedItem> ENCHANTED_GOLDEN_COOKIE = ITEMS.register("enchanted_golden_cookie", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(ModItems.FOOD_ENCHANTED_GOLDEN_COOKIE).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<EnchantedItem> ENCHANTED_GOLDEN_COD = ITEMS.register("enchanted_golden_cod", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(ModItems.FOOD_ENCHANTED_GOLDEN_COD).group(ModItemGroups.foodItemGroup)));
    public static final RegistryObject<EnchantedItem> ENCHANTED_GOLDEN_CANDYCANE = ITEMS.register("enchanted_golden_candycane", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(ModItems.FOOD_ENCHANTED_GOLDEN_CANDYCANE).group(ModItemGroups.foodItemGroup)));

    // DEV ITEMS
    public static final RegistryObject<DevItem> DEV_ITEM1 = ITEMS.register("dev_item1", () -> new DevItem(new Item.Properties().maxStackSize(1)));
    public static final RegistryObject<DevItem> DEV_ITEM16 = ITEMS.register("dev_item16", () -> new DevItem(new Item.Properties().maxStackSize(16)));
    public static final RegistryObject<DevItem> DEV_ITEM64 = ITEMS.register("dev_item64", () -> new DevItem(new Item.Properties().maxStackSize(64)));
    public static final RegistryObject<DevItem> DEV_ITEM_DAMAGE = ITEMS.register("dev_item_damage", () -> new DevItem(new Item.Properties().maxDamage(50)));

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
    public static final RegistryObject<EnchantedSwordItem> ULTIMATE_SWORD = ITEMS.register("ultimate_sword", () -> new EnchantedSwordItem(ToolMaterials.TOOL_ULTIMATE, (CAConfig.COMMON.ultimateSwordDamage.get() - 37), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.SHARPNESS,Enchantments.SMITE,Enchantments.BANE_OF_ARTHROPODS,Enchantments.KNOCKBACK,Enchantments.LOOTING,Enchantments.UNBREAKING,Enchantments.FIRE_ASPECT},new int[]{5,5,5,3,3,2,2}));
    public static final RegistryObject<EnchantedAxeItem> ULTIMATE_AXE = ITEMS.register("ultimate_axe", () -> new EnchantedAxeItem(ToolMaterials.TOOL_ULTIMATE, (CAConfig.COMMON.ultimateAxeDamage.get() - 37), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.EFFICIENCY,Enchantments.FORTUNE,Enchantments.UNBREAKING},new int[]{5,5,2}));
    public static final RegistryObject<EnchantedPickaxeItem> ULTIMATE_PICKAXE = ITEMS.register("ultimate_pickaxe", () -> new EnchantedPickaxeItem(ToolMaterials.TOOL_ULTIMATE, (CAConfig.COMMON.ultimatePickaxeDamage.get() - 37), -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.EFFICIENCY,Enchantments.FORTUNE,Enchantments.UNBREAKING},new int[]{5,5,2}));
    public static final RegistryObject<EnchantedShovelItem> ULTIMATE_SHOVEL = ITEMS.register("ultimate_shovel", () -> new EnchantedShovelItem(ToolMaterials.TOOL_ULTIMATE, (CAConfig.COMMON.ultimateShovelDamage.get() - 37), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.EFFICIENCY,Enchantments.FORTUNE,Enchantments.UNBREAKING},new int[]{5,5,2}));
    public static final RegistryObject<EnchantedHoeItem> ULTIMATE_HOE = ITEMS.register("ultimate_hoe", () -> new EnchantedHoeItem(ToolMaterials.TOOL_ULTIMATE, (CAConfig.COMMON.ultimateHoeDamage.get() - 37), 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.EFFICIENCY,Enchantments.FORTUNE,Enchantments.UNBREAKING},new int[]{5,5,2}));
    public static final RegistryObject<UltimateBowItem> ULTIMATE_BOW = ITEMS.register("ultimate_bow", () -> new UltimateBowItem(new Item.Properties().group(ModItemGroups.equipmentItemGroup).maxStackSize(1).maxDamage(1000).defaultMaxDamage(1000),new Enchantment[]{Enchantments.POWER,Enchantments.FLAME,Enchantments.PUNCH,Enchantments.INFINITY},new int[]{5,3,2,1}));

    // Emerald
    public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.emeraldSwordDamage.get() - 7), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<AxeItem> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new AxeItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.emeraldAxeDamage.get() - 7), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<EnchantedPickaxeItem> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new EnchantedPickaxeItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.emeraldPickaxeDamage.get() - 7), -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.SILK_TOUCH},new int[]{1}));
    public static final RegistryObject<ShovelItem> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new ShovelItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.emeraldShovelDamage.get() - 7), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<HoeItem> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new HoeItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.emeraldHoeDamage.get() - 7), 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Ruby
    public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(ToolMaterials.TOOL_RUBY, (CAConfig.COMMON.rubySwordDamage.get() - 17), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(ToolMaterials.TOOL_RUBY, (CAConfig.COMMON.rubyAxeDamage.get() - 17), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_RUBY, (CAConfig.COMMON.rubyPickaxeDamage.get() - 17), -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(ToolMaterials.TOOL_RUBY, (CAConfig.COMMON.rubyShovelDamage.get() - 17), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(ToolMaterials.TOOL_RUBY, (CAConfig.COMMON.rubyHoeDamage.get() - 17), 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Amethyst
    public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new SwordItem(ToolMaterials.TOOL_AMETHYST, (CAConfig.COMMON.amethystSwordDamage.get() - 12), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<AxeItem> AMETHYST_AXE = ITEMS.register("amethyst_axe", () -> new AxeItem(ToolMaterials.TOOL_AMETHYST, (CAConfig.COMMON.amethystAxeDamage.get() - 12), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<PickaxeItem> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_AMETHYST, (CAConfig.COMMON.amethystPickaxeDamage.get() - 12), -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ShovelItem> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel", () -> new ShovelItem(ToolMaterials.TOOL_AMETHYST, (CAConfig.COMMON.amethystShovelDamage.get() - 12), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<HoeItem> AMETHYST_HOE = ITEMS.register("amethyst_hoe", () -> new HoeItem(ToolMaterials.TOOL_AMETHYST, (CAConfig.COMMON.amethystHoeDamage.get() - 12), 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Tiger's Eye
    public static final RegistryObject<SwordItem> TIGERS_EYE_SWORD = ITEMS.register("tigers_eye_sword", () -> new SwordItem(ToolMaterials.TOOL_TIGERS_EYE, (CAConfig.COMMON.tigersEyeSwordDamage.get() - 9), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<AxeItem> TIGERS_EYE_AXE = ITEMS.register("tigers_eye_axe", () -> new AxeItem(ToolMaterials.TOOL_TIGERS_EYE, (CAConfig.COMMON.tigersEyeAxeDamage.get() - 9), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<PickaxeItem> TIGERS_EYE_PICKAXE = ITEMS.register("tigers_eye_pickaxe", () -> new PickaxeItem(ToolMaterials.TOOL_TIGERS_EYE, (CAConfig.COMMON.tigersEyePickaxeDamage.get() - 9), -2.8F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ShovelItem> TIGERS_EYE_SHOVEL = ITEMS.register("tigers_eye_shovel", () -> new ShovelItem(ToolMaterials.TOOL_TIGERS_EYE, (CAConfig.COMMON.tigersEyeShovelDamage.get() - 9), -3, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<HoeItem> TIGERS_EYE_HOE = ITEMS.register("tigers_eye_hoe", () -> new HoeItem(ToolMaterials.TOOL_TIGERS_EYE, (CAConfig.COMMON.tigersEyeHoeDamage.get() - 9), 0.0F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Misc. Weapons
    public static final RegistryObject<EnchantedSwordItem> NIGHTMARE_SWORD = ITEMS.register("nightmare_sword", () -> new EnchantedSwordItem(ToolMaterials.TOOL_NIGHTMARE, (CAConfig.COMMON.nightmareSwordDamage.get() - 27), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.SHARPNESS,Enchantments.KNOCKBACK,Enchantments.FIRE_ASPECT},new int[]{1,3,1}));
    public static final RegistryObject<EnchantedSwordItem> EXPERIENCE_SWORD = ITEMS.register("experience_sword", () -> new EnchantedSwordItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.experienceSwordDamage.get() - 7), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.SHARPNESS,Enchantments.UNBREAKING,Enchantments.MENDING},new int[]{2,3,1}));
    public static final RegistryObject<SwordItem> POISON_SWORD = ITEMS.register("poison_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.poisonSwordDamage.get() - 7), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<SwordItem> RAT_SWORD = ITEMS.register("rat_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.ratSwordDamage.get() - 7), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<SwordItem> FAIRY_SWORD = ITEMS.register("fairy_sword", () -> new SwordItem(ToolMaterials.TOOL_EMERALD, (CAConfig.COMMON.fairySwordDamage.get() - 7), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<SwordItem> BIG_HAMMER = ITEMS.register("big_hammer", () -> new SwordItem(ToolMaterials.TOOL_AMETHYST, (CAConfig.COMMON.bigHammerDamage.get() - 12), -2.4F, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<EnchantedScytheItem> PRISMATIC_REAPER = ITEMS.register("prismatic_reaper", () -> new EnchantedScytheItem(ToolMaterials.TOOL_ULTIMATE, (CAConfig.COMMON.tigersEyeHoeDamage.get() - 9), -1.9F, new Item.Properties().group(ModItemGroups.equipmentItemGroup), new Enchantment[]{Enchantments.UNBREAKING,Enchantments.SWEEPING},new int[]{4,4}));

    // Staffs
    public static final RegistryObject<ThunderStaffItem> THUNDER_STAFF = ITEMS.register("thunder_staff", () -> new ThunderStaffItem(new Item.Properties().group(ModItemGroups.equipmentItemGroup).maxDamage(50)));

	// ARMOR
    // Ultimate
    public static final RegistryObject<EnchantedArmorItem> ULTIMATE_HELMET = ITEMS.register("ultimate_helmet", () -> new EnchantedArmorItem(ArmorMaterials.ULTIMATE, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.FIRE_PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.PROJECTILE_PROTECTION,Enchantments.RESPIRATION,Enchantments.AQUA_AFFINITY},new int[]{5,5,5,5,3,1}));
    public static final RegistryObject<EnchantedArmorItem> ULTIMATE_CHESTPLATE = ITEMS.register("ultimate_chestplate", () -> new EnchantedArmorItem(ArmorMaterials.ULTIMATE, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.FIRE_PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{5,5,5,5}));
    public static final RegistryObject<EnchantedArmorItem> ULTIMATE_LEGGINGS = ITEMS.register("ultimate_leggings", () -> new EnchantedArmorItem(ArmorMaterials.ULTIMATE, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.FIRE_PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{5,5,5,5}));
    public static final RegistryObject<EnchantedArmorItem> ULTIMATE_BOOTS = ITEMS.register("ultimate_boots", () -> new EnchantedArmorItem(ArmorMaterials.ULTIMATE, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.FIRE_PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.PROJECTILE_PROTECTION,Enchantments.FEATHER_FALLING},new int[]{5,5,5,5,3}));

    // Lava Eel
    public static final RegistryObject<EnchantedArmorItem> LAVA_EEL_HELMET = ITEMS.register("lava_eel_helmet", () -> new EnchantedArmorItem(ArmorMaterials.LAVA_EEL, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup).isImmuneToFire(),new Enchantment[]{Enchantments.FIRE_PROTECTION,Enchantments.AQUA_AFFINITY},new int[]{4,1}));
    public static final RegistryObject<EnchantedArmorItem> LAVA_EEL_CHESTPLATE = ITEMS.register("lava_eel_chestplate", () -> new EnchantedArmorItem(ArmorMaterials.LAVA_EEL, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup).isImmuneToFire(),new Enchantment[]{Enchantments.FIRE_PROTECTION},new int[]{4}));
    public static final RegistryObject<EnchantedArmorItem> LAVA_EEL_LEGGINGS = ITEMS.register("lava_eel_leggings", () -> new EnchantedArmorItem(ArmorMaterials.LAVA_EEL, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup).isImmuneToFire(),new Enchantment[]{Enchantments.FIRE_PROTECTION},new int[]{4}));
    public static final RegistryObject<EnchantedArmorItem> LAVA_EEL_BOOTS = ITEMS.register("lava_eel_boots", () -> new EnchantedArmorItem(ArmorMaterials.LAVA_EEL, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup).isImmuneToFire(),new Enchantment[]{Enchantments.FIRE_PROTECTION},new int[]{4}));

    // Emerald
    public static final RegistryObject<ArmorItem> EMERALD_HELMET = ITEMS.register("emerald_helmet", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));
    public static final RegistryObject<ArmorItem> EMERALD_BOOTS = ITEMS.register("emerald_boots", () -> new ArmorItem(ArmorMaterials.EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup)));

    // Experience
    public static final RegistryObject<EnchantedArmorItem> EXPERIENCE_HELMET = ITEMS.register("experience_helmet", () -> new EnchantedArmorItem(ArmorMaterials.EXPERIENCE, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.MENDING},new int[]{2,1,1}));
    public static final RegistryObject<EnchantedArmorItem> EXPERIENCE_CHESTPLATE = ITEMS.register("experience_chestplate", () -> new EnchantedArmorItem(ArmorMaterials.EXPERIENCE, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.MENDING},new int[]{2,1,1}));
    public static final RegistryObject<EnchantedArmorItem> EXPERIENCE_LEGGINGS = ITEMS.register("experience_leggings", () -> new EnchantedArmorItem(ArmorMaterials.EXPERIENCE, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.MENDING},new int[]{2,1,1}));
    public static final RegistryObject<EnchantedArmorItem> EXPERIENCE_BOOTS = ITEMS.register("experience_boots", () -> new EnchantedArmorItem(ArmorMaterials.EXPERIENCE, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.BLAST_PROTECTION,Enchantments.FEATHER_FALLING,Enchantments.MENDING},new int[]{2,1,1,1}));

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
    public static final RegistryObject<EnchantedArmorItem> LAPIS_HELMET = ITEMS.register("lapis_helmet", () -> new EnchantedArmorItem(ArmorMaterials.LAPIS, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.PROJECTILE_PROTECTION,Enchantments.RESPIRATION,Enchantments.AQUA_AFFINITY},new int[]{1,1,1,1}));
    public static final RegistryObject<EnchantedArmorItem> LAPIS_CHESTPLATE = ITEMS.register("lapis_chestplate", () -> new EnchantedArmorItem(ArmorMaterials.LAPIS, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{1,1}));
    public static final RegistryObject<EnchantedArmorItem> LAPIS_LEGGINGS = ITEMS.register("lapis_leggings", () -> new EnchantedArmorItem(ArmorMaterials.LAPIS, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{1,1}));
    public static final RegistryObject<EnchantedArmorItem> LAPIS_BOOTS = ITEMS.register("lapis_boots", () -> new EnchantedArmorItem(ArmorMaterials.LAPIS, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.equipmentItemGroup),new Enchantment[]{Enchantments.PROTECTION,Enchantments.PROJECTILE_PROTECTION},new int[]{1,1}));

    // SPAWN EGGS
    public static final RegistryObject<ModSpawnEggItem> ENT_SPAWN_EGG = ITEMS.register("ent_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.ENT, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> RED_ANT_SPAWN_EGG = ITEMS.register("red_ant_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.RED_ANT, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> BROWN_ANT_SPAWN_EGG = ITEMS.register("brown_ant_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.BROWN_ANT, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> RAINBOW_ANT_SPAWN_EGG = ITEMS.register("rainbow_ant_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.RAINBOW_ANT, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> UNSTABLE_ANT_SPAWN_EGG = ITEMS.register("unstable_ant_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.UNSTABLE_ANT, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> TERMITE_SPAWN_EGG = ITEMS.register("termite_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.TERMITE, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> HERCULES_BEETLE_SPAWN_EGG = ITEMS.register("hercules_beetle_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.HERCULES_BEETLE, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> RUBY_BUG_SPAWN_EGG = ITEMS.register("ruby_bug_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.RUBY_BUG, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> EMERALD_GATOR_SPAWN_EGG = ITEMS.register("emerald_gator_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.EMERALD_GATOR, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> ROBO_SNIPER_SPAWN_EGG = ITEMS.register("robo_sniper_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.ROBO_SNIPER, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> BEAVER_SPAWN_EGG = ITEMS.register("beaver_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.BEAVER, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> APPLE_COW_SPAWN_EGG = ITEMS.register("apple_cow_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.APPLE_COW, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<ModSpawnEggItem> GOLDEN_APPLE_COW_SPAWN_EGG = ITEMS.register("golden_apple_cow_spawn_egg", () -> new ModSpawnEggItem(ModEntityTypes.GOLDEN_APPLE_COW, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<EnchantedModSpawnEggItem> ENCHANTED_GOLDEN_APPLE_COW_SPAWN_EGG = ITEMS.register("enchanted_golden_apple_cow_spawn_egg", () -> new EnchantedModSpawnEggItem(ModEntityTypes.ENCHANTED_GOLDEN_APPLE_COW, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<SpawnEggItem> IRON_GOLEM_SPAWN_EGG = ITEMS.register("iron_golem_spawn_egg", () -> new SpawnEggItem(EntityType.IRON_GOLEM, 0xe2dbd6, 0x74a332, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
    public static final RegistryObject<SpawnEggItem> SNOW_GOLEM_SPAWN_EGG = ITEMS.register("snow_golem_spawn_egg", () -> new SpawnEggItem(EntityType.SNOW_GOLEM, 0xffffff, 0xe38a1d, new Item.Properties().group(ModItemGroups.eggsItemGroup)));
}
