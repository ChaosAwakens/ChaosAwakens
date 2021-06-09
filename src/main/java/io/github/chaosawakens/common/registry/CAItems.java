package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.api.EnchantmentAndLevel;
import io.github.chaosawakens.common.config.CAConfig;
import io.github.chaosawakens.common.enums.CAArmorMaterial;
import io.github.chaosawakens.common.enums.CAToolMaterial;
import io.github.chaosawakens.common.items.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SoupItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ChaosAwakens.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CAItems {

	// RARITIES
	public static final Rarity RARITY_ROYALTY = Rarity.create(ChaosAwakens.MODID + ":royalty", TextFormatting.GOLD);

	// ORESPAWN FOOD
	public static final Food FOOD_RAW_CORNDOG = new Food.Builder().hunger(4).saturation(0.6F).build();
	public static final Food FOOD_COOKED_CORNDOG = new Food.Builder().hunger(14).saturation(1.5F).build();
	public static final Food FOOD_RAW_BACON = new Food.Builder().hunger(8).saturation(1.5F).meat().build();
	public static final Food FOOD_COOKED_BACON = new Food.Builder().hunger(14).saturation(1.0F).meat().effect(() -> new EffectInstance(Effects.STRENGTH, 2000, 0), 1.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 2000, 0), 1.0F).build();
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
	public static final Food FOOD_SPARK_FISH = new Food.Builder().hunger(3).saturation(0.2F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0), 1.0F).build();
	public static final Food FOOD_LAVA_EEL = new Food.Builder().hunger(2).saturation(0.6F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0), 1.0F).build();
	public static final Food FOOD_CRAB_MEAT = new Food.Builder().hunger(4).saturation(0.25F).meat().build();
	public static final Food FOOD_COOKED_CRAB_MEAT = new Food.Builder().hunger(6).saturation(0.75F).meat().build();
	public static final Food FOOD_SEAFOOD_PATTY = new Food.Builder().hunger(16).saturation(2.35F).build();
	public static final Food FOOD_PEACH = new Food.Builder().hunger(4).saturation(0.55F).build();
	public static final Food FOOD_PEACOCK_LEG = new Food.Builder().hunger(6).saturation(0.7F).build();
	public static final Food FOOD_COOKED_PEACOCK_LEG = new Food.Builder().hunger(12).saturation(1.4F).build();
	public static final Food FOOD_CRYSTAL_APPLE = new Food.Builder().hunger(5).saturation(0.85F).effect(  () -> new EffectInstance(Effects.STRENGTH, 3000, 0), 1.0F).effect( () -> new EffectInstance(Effects.REGENERATION, 3000, 0), 1.0F).build();

	// SHINY FOOD
	public static final Food FOOD_CANDYCANE = new Food.Builder().hunger(2).saturation(0.15F).setAlwaysEdible().build();
	public static final Food FOOD_GOLDEN_BREAD = new Food.Builder().hunger(8).saturation(0.8F).setAlwaysEdible().build();
	public static final Food FOOD_GOLDEN_CHICKEN = new Food.Builder().hunger(6).saturation(0.45F).meat().setAlwaysEdible().effect( () -> new EffectInstance(Effects.STRENGTH, 600, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_TROPICAL_FISH = new Food.Builder().hunger(4).saturation(0.2F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.REGENERATION, 2400, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_COD = new Food.Builder().hunger(5).saturation(0.3F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.REGENERATION, 200, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_PORKCHOP = new Food.Builder().hunger(8).saturation(1.1F).meat().setAlwaysEdible().effect( () -> new EffectInstance(Effects.STRENGTH, 600, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_MELON_SLICE = new Food.Builder().hunger(8).saturation(0.45F).setAlwaysEdible().build();
	public static final Food FOOD_GOLDEN_MUSHROOM_STEW = new Food.Builder().hunger(6).saturation(0.25F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.SPEED, 700, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_STEAK = new Food.Builder().hunger(8).saturation(1.1F).meat().setAlwaysEdible().effect( () -> new EffectInstance(Effects.STRENGTH, 400, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_COOKIE = new Food.Builder().hunger(2).saturation(0.15F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.REGENERATION, 100, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_POTATO = new Food.Builder().hunger(6).saturation(0.65F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.REGENERATION, 400, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_PUMPKIN_PIE = new Food.Builder().hunger(8).saturation(9).setAlwaysEdible().effect( () -> new EffectInstance(Effects.NIGHT_VISION, 600, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_FLESH = new Food.Builder().hunger(4).saturation(0.3F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.SATURATION, 40, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_PUFFERFISH = new Food.Builder().hunger(4).saturation(0.3F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.POISON, 100, 0), 1.0F).effect( () -> new EffectInstance(Effects.NIGHT_VISION, 1800, 0), 1.0F).effect( () -> new EffectInstance(Effects.WATER_BREATHING, 3600, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_SALMON = new Food.Builder().hunger(8).saturation(0.85F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.JUMP_BOOST, 1800, 0), 1.0F).effect( () -> new EffectInstance(Effects.SPEED, 1800, 1), 1.0F).build();
	public static final Food FOOD_GOLDEN_CANDYCANE = new Food.Builder().hunger(4).saturation(0.25F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.HASTE, 300, 0), 1.0F).effect( () -> new EffectInstance(Effects.JUMP_BOOST, 600, 0), 1.0F).effect( () -> new EffectInstance(Effects.SPEED, 600, 0), 1.0F).build();
	public static final Food FOOD_ULTIMATE_APPLE = new Food.Builder().hunger(10).saturation(1.5F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.STRENGTH, 3900, 2), 1.0F).effect( () -> new EffectInstance(Effects.REGENERATION, 4800, 3), 1.0F).effect( () -> new EffectInstance(Effects.HEALTH_BOOST, 5400, 2), 1.0F).effect( () -> new EffectInstance(Effects.RESISTANCE, 7200, 0), 1.0F).effect( () -> new EffectInstance(Effects.FIRE_RESISTANCE, 7200, 0), 1.0F).build();
	public static final Food FOOD_ENCHANTED_GOLDEN_CARROT = new Food.Builder().hunger(8).saturation(0.85F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.REGENERATION, 600, 2), 1.0F).effect( () -> new EffectInstance(Effects.NIGHT_VISION, 1200, 0), 1.0F).effect( () -> new EffectInstance(Effects.ABSORPTION, 1200, 0), 1.0F).build();
	public static final Food FOOD_ENCHANTED_GOLDEN_STEAK = new Food.Builder().hunger(10).saturation(1.2F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.ABSORPTION, 900, 0), 1.0F).effect( () -> new EffectInstance(Effects.STRENGTH, 1800, 1), 1.0F).effect( () -> new EffectInstance(Effects.REGENERATION, 1800, 0), 1.0F).build();
	public static final Food FOOD_ENCHANTED_GOLDEN_COD = new Food.Builder().hunger(8).saturation(0.85F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.REGENERATION, 600, 2), 1.0F).effect( () -> new EffectInstance(Effects.WATER_BREATHING, 900, 0), 1.0F).build();
	public static final Food FOOD_ENCHANTED_GOLDEN_COOKIE = new Food.Builder().hunger(4).saturation(0.85F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.REGENERATION, 600, 1), 1.0F).effect( () -> new EffectInstance(Effects.SPEED, 1000, 1), 1.0F).build();
	public static final Food FOOD_ENCHANTED_GOLDEN_CANDYCANE = new Food.Builder().hunger(6).saturation(0.3F).setAlwaysEdible().effect( () -> new EffectInstance(Effects.STRENGTH, 300, 0), 1.0F).effect( () -> new EffectInstance(Effects.REGENERATION, 300, 0), 1.0F).effect( () -> new EffectInstance(Effects.HASTE, 600, 1), 1.0F).effect( () -> new EffectInstance(Effects.JUMP_BOOST, 900, 1), 1.0F).effect( () -> new EffectInstance(Effects.SPEED, 900, 1), 1.0F).build();

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

	// ORESPAWN FOODS
	public static final RegistryObject<Item> CORNDOG = ITEMS.register("corndog", () -> new Item(new Item.Properties().food(CAItems.FOOD_RAW_CORNDOG).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> COOKED_CORNDOG = ITEMS.register("cooked_corndog", () -> new Item(new Item.Properties().food(CAItems.FOOD_COOKED_CORNDOG).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> BACON = ITEMS.register("bacon", () -> new Item(new Item.Properties().food(CAItems.FOOD_RAW_BACON).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> COOKED_BACON = ITEMS.register("cooked_bacon", () -> new Item(new Item.Properties().food(CAItems.FOOD_COOKED_BACON).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> PEACOCK_LEG = ITEMS.register("peacock_leg", () -> new Item(new Item.Properties().food(CAItems.FOOD_PEACOCK_LEG).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> COOKED_PEACOCK_LEG = ITEMS.register("cooked_peacock_leg", () -> new Item(new Item.Properties().food(CAItems.FOOD_COOKED_PEACOCK_LEG).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> CRAB_MEAT = ITEMS.register("crab_meat", () -> new Item(new Item.Properties().food(CAItems.FOOD_CRAB_MEAT).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> COOKED_CRAB_MEAT = ITEMS.register("cooked_crab_meat", () -> new Item(new Item.Properties().food(CAItems.FOOD_COOKED_CRAB_MEAT).group(CAItemGroups.foodItemGroup)));

	public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new Item(new Item.Properties().food(CAItems.FOOD_CORN).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties().food(CAItems.FOOD_TOMATO).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry", () -> new Item(new Item.Properties().food(CAItems.FOOD_STRAWBERRY).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> CHERRIES = ITEMS.register("cherries", () -> new Item(new Item.Properties().food(CAItems.FOOD_CHERRIES).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> PEACH = ITEMS.register("peach", () -> new Item(new Item.Properties().food(CAItems.FOOD_PEACH).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> RADISH = ITEMS.register("radish", () -> new Item(new Item.Properties().food(CAItems.FOOD_RADISH).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce", () -> new Item(new Item.Properties().food(CAItems.FOOD_LETTUCE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item(new Item.Properties().food(CAItems.FOOD_CHEESE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> CRYSTAL_APPLE = ITEMS.register("crystal_apple", () -> new Item(new Item.Properties().food(CAItems.FOOD_CRYSTAL_APPLE).group(CAItemGroups.foodItemGroup)));

	public static final RegistryObject<Item> RADISH_STEW = ITEMS.register("radish_stew", () -> new SoupItem(new Item.Properties().food(CAItems.FOOD_RADISH_STEW).maxStackSize(1).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GARDEN_SALAD = ITEMS.register("garden_salad", () -> new SoupItem(new Item.Properties().food(CAItems.FOOD_GARDEN_SALAD).maxStackSize(1).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> BLT = ITEMS.register("blt", () -> new Item(new Item.Properties().food(CAItems.FOOD_BLT).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> SEAFOOD_PATTY = ITEMS.register("seafood_patty", () -> new Item(new Item.Properties().food(CAItems.FOOD_SEAFOOD_PATTY).group(CAItemGroups.foodItemGroup)));

	public static final RegistryObject<Item> GREEN_FISH = ITEMS.register("green_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> ROCK_FISH = ITEMS.register("rock_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> WOOD_FISH = ITEMS.register("wood_fish", () -> new Item(new Item.Properties().food(Foods.COD).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> SPARK_FISH = ITEMS.register("spark_fish", () -> new Item(new Item.Properties().food(CAItems.FOOD_SPARK_FISH).isImmuneToFire().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> LAVA_EEL = ITEMS.register("lava_eel", () -> new Item(new Item.Properties().food(CAItems.FOOD_LAVA_EEL).isImmuneToFire().group(CAItemGroups.foodItemGroup)));

	public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));

	public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> CHERRY_SEEDS = ITEMS.register("cherry_seeds", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> APPLE_SEEDS = ITEMS.register("apple_seeds", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> PEACH_SEED = ITEMS.register("peach_seed", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> LETTUCE_SEEDS = ITEMS.register("lettuce_seeds", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> RADISH_SEEDS = ITEMS.register("radish_seeds", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));

	public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));

	// SHINY FOOD
	public static final RegistryObject<Item> GOLDEN_MELON_SEEDS = ITEMS.register("golden_melon_seeds", () -> new Item(new Item.Properties().group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> CANDYCANE = ITEMS.register("candycane", () -> new Item(new Item.Properties().rarity(Rarity.COMMON).food(CAItems.FOOD_CANDYCANE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_BREAD = ITEMS.register("golden_bread", () -> new Item(new Item.Properties().rarity(Rarity.COMMON).food(CAItems.FOOD_GOLDEN_BREAD).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_CHICKEN = ITEMS.register("golden_chicken", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_CHICKEN).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_TROPICAL_FISH = ITEMS.register("golden_tropical_fish", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_TROPICAL_FISH).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_COD = ITEMS.register("golden_cod", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_COD).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_PORKCHOP = ITEMS.register("golden_porkchop", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_PORKCHOP).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_MELON_SLICE = ITEMS.register("golden_melon_slice", () -> new Item(new Item.Properties().rarity(Rarity.COMMON).food(CAItems.FOOD_GOLDEN_MELON_SLICE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_MUSHROOM_STEW = ITEMS.register("golden_mushroom_stew", () -> new SoupItem(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_MUSHROOM_STEW).maxStackSize(1).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_STEAK = ITEMS.register("golden_steak", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_STEAK).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_COOKIE = ITEMS.register("golden_cookie", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_COOKIE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_POTATO = ITEMS.register("golden_potato", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_POTATO).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_PUMPKIN_PIE = ITEMS.register("golden_pumpkin_pie", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_PUMPKIN_PIE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_FLESH = ITEMS.register("golden_flesh", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_FLESH).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_PUFFERFISH = ITEMS.register("golden_pufferfish", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_PUFFERFISH).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_SALMON = ITEMS.register("golden_salmon", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_SALMON).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> GOLDEN_CANDYCANE = ITEMS.register("golden_candycane", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAItems.FOOD_GOLDEN_CANDYCANE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> ULTIMATE_APPLE = ITEMS.register("ultimate_apple", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(CAItems.FOOD_ULTIMATE_APPLE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> ENCHANTED_GOLDEN_CARROT = ITEMS.register("enchanted_golden_carrot", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(CAItems.FOOD_ENCHANTED_GOLDEN_CARROT).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> ENCHANTED_GOLDEN_STEAK = ITEMS.register("enchanted_golden_steak", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(CAItems.FOOD_ENCHANTED_GOLDEN_STEAK).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> ENCHANTED_GOLDEN_COOKIE = ITEMS.register("enchanted_golden_cookie", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(CAItems.FOOD_ENCHANTED_GOLDEN_COOKIE).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> ENCHANTED_GOLDEN_COD = ITEMS.register("enchanted_golden_cod", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(CAItems.FOOD_ENCHANTED_GOLDEN_COD).group(CAItemGroups.foodItemGroup)));
	public static final RegistryObject<Item> ENCHANTED_GOLDEN_CANDYCANE = ITEMS.register("enchanted_golden_candycane", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(CAItems.FOOD_ENCHANTED_GOLDEN_CANDYCANE).group(CAItemGroups.foodItemGroup)));

	// DEV ITEMS
	public static final RegistryObject<DevItem> DEV_ITEM1 = ITEMS.register("dev_item1", () -> new DevItem(new Item.Properties().maxStackSize(1)));
	public static final RegistryObject<DevItem> DEV_ITEM16 = ITEMS.register("dev_item16", () -> new DevItem(new Item.Properties().maxStackSize(16)));
	public static final RegistryObject<DevItem> DEV_ITEM64 = ITEMS.register("dev_item64", () -> new DevItem(new Item.Properties().maxStackSize(64)));
	public static final RegistryObject<DevItem> DEV_ITEM_DAMAGE = ITEMS.register("dev_item_damage", () -> new DevItem(new Item.Properties().maxDamage(50)));

	// MINERALS
	public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> TIGERS_EYE = ITEMS.register("tigers_eye", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> URANIUM_NUGGET = ITEMS.register("uranium_nugget", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> COPPER_LUMP = ITEMS.register("copper_lump", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> TIN_LUMP = ITEMS.register("tin_lump", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> SILVER_LUMP = ITEMS.register("silver_lump", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> PLATINUM_LUMP = ITEMS.register("platinum_lump", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> PINK_TOURMALINE_INGOT = ITEMS.register("pink_tourmaline_ingot", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> PINK_TOURMALINE_NUGGET = ITEMS.register("pink_tourmaline_nugget", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> CATS_EYE_INGOT = ITEMS.register("cats_eye_ingot", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> CATS_EYE_NUGGET = ITEMS.register("cats_eye_nugget", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> SUNSTONE = ITEMS.register("sunstone", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> BLOODSTONE = ITEMS.register("bloodstone", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> QUEEN_SCALE = ITEMS.register("queen_scale", () -> new Item(new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> ENDER_DRAGON_SCALE = ITEMS.register("ender_dragon_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> TRIFFID_GOO = ITEMS.register("triffid_goo", () -> new Item(new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> BIG_BERTHA_BLADE = ITEMS.register("big_bertha_blade", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> BIG_BERTHA_GUARD = ITEMS.register("big_bertha_guard", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> BIG_BERTHA_HANDLE = ITEMS.register("big_bertha_handle", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> SILVER_COIN = ITEMS.register("silver_coin", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> GOLD_COIN = ITEMS.register("gold_coin", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> PLATINUM_COIN = ITEMS.register("platinum_coin", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> DEAD_STINK_BUG = ITEMS.register("dead_stink_bug", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));

	// MISC
	public static final RegistryObject<Item> CRYSTAL_WOOD_SHARD = ITEMS.register("crystal_wood_shard", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> PEACOCK_FEATHER = ITEMS.register("peacock_feather", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<Item> DEAD_IRUKANDJI = ITEMS.register("dead_irukandji", () -> new Item(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<IrukandjiArrowItem> IRUKANDJI_ARROW = ITEMS.register("irukandji_arrow", () -> new IrukandjiArrowItem(new Item.Properties().group(CAItemGroups.itemsItemGroup)));
	public static final RegistryObject<WallOrFloorItem> CRYSTAL_TORCH = ITEMS.register("crystal_torch", () -> new WallOrFloorItem(CABlocks.CRYSTAL_TORCH.get(), CABlocks.WALL_CRYSTAL_TORCH.get(), new Item.Properties().group(CAItemGroups.blocksItemGroup)));
	public static final RegistryObject<WallOrFloorItem> SUNSTONE_TORCH = ITEMS.register("sunstone_torch", () -> new WallOrFloorItem(CABlocks.SUNSTONE_TORCH.get(), CABlocks.WALL_SUNSTONE_TORCH.get(), new Item.Properties().group(CAItemGroups.blocksItemGroup)));
	public static final RegistryObject<WallOrFloorItem> EXTREME_TORCH = ITEMS.register("extreme_torch", () -> new WallOrFloorItem(CABlocks.EXTREME_TORCH.get(), CABlocks.WALL_EXTREME_TORCH.get(), new Item.Properties().group(CAItemGroups.blocksItemGroup)));

	// TOOLS
	// Ultimate
	public static final RegistryObject<SwordItem> ULTIMATE_SWORD = ITEMS.register("ultimate_sword", () -> new EnchantedSwordItem(CAToolMaterial.TOOL_ULTIMATE, CAConfig.COMMON.ultimateSwordDamage.get() - 40, -2.4F, new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.SHARPNESS, 5), new EnchantmentAndLevel(Enchantments.SMITE, 5), new EnchantmentAndLevel(Enchantments.BANE_OF_ARTHROPODS, 5), new EnchantmentAndLevel(Enchantments.KNOCKBACK, 3), new EnchantmentAndLevel(Enchantments.LOOTING, 3), new EnchantmentAndLevel(Enchantments.UNBREAKING, 2), new EnchantmentAndLevel(Enchantments.FIRE_ASPECT, 2) }));
	public static final RegistryObject<ShovelItem> ULTIMATE_SHOVEL = ITEMS.register("ultimate_shovel", () -> new EnchantedShovelItem(CAToolMaterial.TOOL_ULTIMATE, CAConfig.COMMON.ultimateShovelDamage.get() - 37, -3, new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.EFFICIENCY, 5), new EnchantmentAndLevel(Enchantments.FORTUNE, 5), new EnchantmentAndLevel(Enchantments.UNBREAKING, 2)}));
	public static final RegistryObject<PickaxeItem> ULTIMATE_PICKAXE = ITEMS.register("ultimate_pickaxe", () -> new EnchantedPickaxeItem(CAToolMaterial.TOOL_ULTIMATE, CAConfig.COMMON.ultimatePickaxeDamage.get() - 37, -2.8F, new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.EFFICIENCY, 5), new EnchantmentAndLevel(Enchantments.FORTUNE, 5), new EnchantmentAndLevel(Enchantments.UNBREAKING, 2)}));
	public static final RegistryObject<AxeItem> ULTIMATE_AXE = ITEMS.register("ultimate_axe", () -> new EnchantedAxeItem(CAToolMaterial.TOOL_ULTIMATE, CAConfig.COMMON.ultimateAxeDamage.get() - 37, -3, new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.EFFICIENCY, 5), new EnchantmentAndLevel(Enchantments.FORTUNE, 5),  new EnchantmentAndLevel(Enchantments.UNBREAKING, 2) }));
	public static final RegistryObject<HoeItem> ULTIMATE_HOE = ITEMS.register("ultimate_hoe", () -> new UltimateHoeItem(CAToolMaterial.TOOL_ULTIMATE, CAConfig.COMMON.ultimateHoeDamage.get() - 37, 0.0F, new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.EFFICIENCY, 5), new EnchantmentAndLevel(Enchantments.FORTUNE, 5), new EnchantmentAndLevel(Enchantments.UNBREAKING, 2)}));
	public static final RegistryObject<UltimateBowItem> ULTIMATE_BOW = ITEMS.register("ultimate_bow", () -> new UltimateBowItem(new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup).maxStackSize(1).maxDamage(1000).defaultMaxDamage(1000),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.POWER, 5), new EnchantmentAndLevel(Enchantments.FLAME, 3), new EnchantmentAndLevel(Enchantments.PUNCH, 2), new EnchantmentAndLevel(Enchantments.INFINITY, 1)}));
	public static final RegistryObject<UltimateFishingRodItem> ULTIMATE_FISHING_ROD = ITEMS.register("ultimate_fishing_rod", () -> new UltimateFishingRodItem(new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup).maxStackSize(1).maxDamage(1000).defaultMaxDamage(1000)));

	// Emerald
	public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new SwordItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.emeraldSwordDamage.get() - 7, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.emeraldShovelDamage.get() - 7, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new EnchantedPickaxeItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.emeraldPickaxeDamage.get() - 7, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup), new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.SILK_TOUCH, 1)}));
	public static final RegistryObject<AxeItem> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new AxeItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.emeraldAxeDamage.get() - 7, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new HoeItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.emeraldHoeDamage.get() - 7, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Ruby
	public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(CAToolMaterial.TOOL_RUBY, CAConfig.COMMON.rubySwordDamage.get() - 17, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_RUBY, CAConfig.COMMON.rubyShovelDamage.get() - 17, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_RUBY, CAConfig.COMMON.rubyPickaxeDamage.get() - 17, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(CAToolMaterial.TOOL_RUBY, CAConfig.COMMON.rubyAxeDamage.get() - 17, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(CAToolMaterial.TOOL_RUBY, CAConfig.COMMON.rubyHoeDamage.get() - 17, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Amethyst
	public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new SwordItem(CAToolMaterial.TOOL_AMETHYST, CAConfig.COMMON.amethystSwordDamage.get() - 12, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_AMETHYST, CAConfig.COMMON.amethystShovelDamage.get() - 12, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_AMETHYST, CAConfig.COMMON.amethystPickaxeDamage.get() - 12, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> AMETHYST_AXE = ITEMS.register("amethyst_axe", () -> new AxeItem(CAToolMaterial.TOOL_AMETHYST, CAConfig.COMMON.amethystAxeDamage.get() - 12, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> AMETHYST_HOE = ITEMS.register("amethyst_hoe", () -> new HoeItem(CAToolMaterial.TOOL_AMETHYST, CAConfig.COMMON.amethystHoeDamage.get() - 12, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Tiger's Eye
	public static final RegistryObject<SwordItem> TIGERS_EYE_SWORD = ITEMS.register("tigers_eye_sword", () -> new SwordItem(CAToolMaterial.TOOL_TIGERS_EYE, CAConfig.COMMON.tigersEyeSwordDamage.get() - 9, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> TIGERS_EYE_SHOVEL = ITEMS.register("tigers_eye_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_TIGERS_EYE, CAConfig.COMMON.tigersEyeShovelDamage.get() - 9, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> TIGERS_EYE_PICKAXE = ITEMS.register("tigers_eye_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_TIGERS_EYE, CAConfig.COMMON.tigersEyePickaxeDamage.get() - 9, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> TIGERS_EYE_AXE = ITEMS.register("tigers_eye_axe", () -> new AxeItem(CAToolMaterial.TOOL_TIGERS_EYE, CAConfig.COMMON.tigersEyeAxeDamage.get() - 9, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> TIGERS_EYE_HOE = ITEMS.register("tigers_eye_hoe", () -> new HoeItem(CAToolMaterial.TOOL_TIGERS_EYE, CAConfig.COMMON.tigersEyeHoeDamage.get() - 9, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Crystal Wood
	public static final RegistryObject<SwordItem> CRYSTAL_WOOD_SWORD = ITEMS.register("crystal_wood_sword", () -> new SwordItem(CAToolMaterial.TOOL_CRYSTAL_WOOD, CAConfig.COMMON.crystalWoodSwordDamage.get() - 2, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> CRYSTAL_WOOD_SHOVEL = ITEMS.register("crystal_wood_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_CRYSTAL_WOOD, CAConfig.COMMON.crystalWoodShovelDamage.get() - 2, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> CRYSTAL_WOOD_PICKAXE = ITEMS.register("crystal_wood_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_CRYSTAL_WOOD, CAConfig.COMMON.crystalWoodPickaxeDamage.get() - 2, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> CRYSTAL_WOOD_AXE = ITEMS.register("crystal_wood_axe", () -> new AxeItem(CAToolMaterial.TOOL_CRYSTAL_WOOD, CAConfig.COMMON.crystalWoodAxeDamage.get() - 2, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> CRYSTAL_WOOD_HOE = ITEMS.register("crystal_wood_hoe", () -> new HoeItem(CAToolMaterial.TOOL_CRYSTAL_WOOD, CAConfig.COMMON.crystalWoodHoeDamage.get() - 2, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Kyanite
	public static final RegistryObject<SwordItem> KYANITE_SWORD = ITEMS.register("kyanite_sword", () -> new SwordItem(CAToolMaterial.TOOL_KYANITE, CAConfig.COMMON.kyaniteSwordDamage.get() - 3, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> KYANITE_SHOVEL = ITEMS.register("kyanite_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_KYANITE, CAConfig.COMMON.kyaniteShovelDamage.get() - 3, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> KYANITE_PICKAXE = ITEMS.register("kyanite_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_KYANITE, CAConfig.COMMON.kyanitePickaxeDamage.get() - 3, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> KYANITE_AXE = ITEMS.register("kyanite_axe", () -> new AxeItem(CAToolMaterial.TOOL_KYANITE, CAConfig.COMMON.kyaniteAxeDamage.get() - 3, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> KYANITE_HOE = ITEMS.register("kyanite_hoe", () -> new HoeItem(CAToolMaterial.TOOL_KYANITE, CAConfig.COMMON.kyaniteHoeDamage.get() - 3, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Cat's Eye
	public static final RegistryObject<SwordItem> CATS_EYE_SWORD = ITEMS.register("cats_eye_sword", () -> new SwordItem(CAToolMaterial.TOOL_CATS_EYE, CAConfig.COMMON.catsEyeSwordDamage.get() - 9, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> CATS_EYE_SHOVEL = ITEMS.register("cats_eye_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_CATS_EYE, CAConfig.COMMON.catsEyeShovelDamage.get() - 9, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> CATS_EYE_PICKAXE = ITEMS.register("cats_eye_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_CATS_EYE, CAConfig.COMMON.catsEyePickaxeDamage.get() - 9, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> CATS_EYE_AXE = ITEMS.register("cats_eye_axe", () -> new AxeItem(CAToolMaterial.TOOL_CATS_EYE, CAConfig.COMMON.catsEyeAxeDamage.get() - 9, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> CATS_EYE_HOE = ITEMS.register("cats_eye_hoe", () -> new HoeItem(CAToolMaterial.TOOL_CATS_EYE, CAConfig.COMMON.catsEyeHoeDamage.get() - 9, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Pink Tourmaline
	public static final RegistryObject<SwordItem> PINK_TOURMALINE_SWORD = ITEMS.register("pink_tourmaline_sword", () -> new SwordItem(CAToolMaterial.TOOL_PINK_TOURMALINE, CAConfig.COMMON.pinkTourmSwordDamage.get() - 9, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> PINK_TOURMALINE_SHOVEL = ITEMS.register("pink_tourmaline_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_PINK_TOURMALINE, CAConfig.COMMON.pinkTourmShovelDamage.get() - 9, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> PINK_TOURMALINE_PICKAXE = ITEMS.register("pink_tourmaline_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_PINK_TOURMALINE, CAConfig.COMMON.pinkTourmPickaxeDamage.get() - 9, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> PINK_TOURMALINE_AXE = ITEMS.register("pink_tourmaline_axe", () -> new AxeItem(CAToolMaterial.TOOL_PINK_TOURMALINE, CAConfig.COMMON.pinkTourmAxeDamage.get() - 9, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> PINK_TOURMALINE_HOE = ITEMS.register("pink_tourmaline_hoe", () -> new HoeItem(CAToolMaterial.TOOL_PINK_TOURMALINE, CAConfig.COMMON.pinkTourmHoeDamage.get() - 9, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Copper
	public static final RegistryObject<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(CAToolMaterial.TOOL_COPPER, CAConfig.COMMON.copperSwordDamage.get() - 3, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_COPPER, CAConfig.COMMON.copperShovelDamage.get() - 3, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_COPPER, CAConfig.COMMON.copperPickaxeDamage.get() - 3, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(CAToolMaterial.TOOL_COPPER, CAConfig.COMMON.copperAxeDamage.get() - 3, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(CAToolMaterial.TOOL_COPPER, CAConfig.COMMON.copperHoeDamage.get() - 3, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Tin
	public static final RegistryObject<SwordItem> TIN_SWORD = ITEMS.register("tin_sword", () -> new SwordItem(CAToolMaterial.TOOL_TIN, CAConfig.COMMON.tinSwordDamage.get() - 4, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> TIN_SHOVEL = ITEMS.register("tin_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_TIN, CAConfig.COMMON.tinShovelDamage.get() - 4, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> TIN_PICKAXE = ITEMS.register("tin_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_TIN, CAConfig.COMMON.tinPickaxeDamage.get() - 4, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> TIN_AXE = ITEMS.register("tin_axe", () -> new AxeItem(CAToolMaterial.TOOL_TIN, CAConfig.COMMON.tinAxeDamage.get() - 4, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> TIN_HOE = ITEMS.register("tin_hoe", () -> new HoeItem(CAToolMaterial.TOOL_TIN, CAConfig.COMMON.tinHoeDamage.get() - 4, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Silver
	public static final RegistryObject<SwordItem> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SwordItem(CAToolMaterial.TOOL_SILVER, CAConfig.COMMON.silverSwordDamage.get() - 5, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_SILVER, CAConfig.COMMON.silverShovelDamage.get() - 5, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_SILVER, CAConfig.COMMON.silverPickaxeDamage.get() - 5, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(CAToolMaterial.TOOL_SILVER, CAConfig.COMMON.silverAxeDamage.get() - 5, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(CAToolMaterial.TOOL_SILVER, CAConfig.COMMON.silverHoeDamage.get() - 5, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Platinum
	public static final RegistryObject<SwordItem> PLATINUM_SWORD = ITEMS.register("platinum_sword", () -> new SwordItem(CAToolMaterial.TOOL_PLATINUM, CAConfig.COMMON.platinumSwordDamage.get() - 7, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ShovelItem> PLATINUM_SHOVEL = ITEMS.register("platinum_shovel", () -> new ShovelItem(CAToolMaterial.TOOL_PLATINUM, CAConfig.COMMON.platinumShovelDamage.get() - 7, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<PickaxeItem> PLATINUM_PICKAXE = ITEMS.register("platinum_pickaxe", () -> new PickaxeItem(CAToolMaterial.TOOL_PLATINUM, CAConfig.COMMON.platinumPickaxeDamage.get() - 7, -2.8F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<AxeItem> PLATINUM_AXE = ITEMS.register("platinum_axe", () -> new AxeItem(CAToolMaterial.TOOL_PLATINUM, CAConfig.COMMON.platinumAxeDamage.get() - 7, -3, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<HoeItem> PLATINUM_HOE = ITEMS.register("platinum_hoe", () -> new HoeItem(CAToolMaterial.TOOL_PLATINUM, CAConfig.COMMON.platinumHoeDamage.get() - 7, 0.0F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Misc. Weapons
	public static final RegistryObject<SwordItem> NIGHTMARE_SWORD = ITEMS.register("nightmare_sword", () -> new EnchantedSwordItem(CAToolMaterial.TOOL_NIGHTMARE, CAConfig.COMMON.nightmareSwordDamage.get() - 28, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.SHARPNESS, 1), new EnchantmentAndLevel(Enchantments.KNOCKBACK, 3), new EnchantmentAndLevel(Enchantments.FIRE_ASPECT, 1)}));
	public static final RegistryObject<SwordItem> EXPERIENCE_SWORD = ITEMS.register("experience_sword", () -> new EnchantedSwordItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.experienceSwordDamage.get() - 8, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.SHARPNESS, 2), new EnchantmentAndLevel(Enchantments.UNBREAKING, 3), new EnchantmentAndLevel(Enchantments.MENDING, 1) }));
	public static final RegistryObject<SwordItem> POISON_SWORD = ITEMS.register("poison_sword", () -> new PoisonSwordItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.poisonSwordDamage.get() - 8, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.SHARPNESS, 1)}));
	public static final RegistryObject<SwordItem> RAT_SWORD = ITEMS.register("rat_sword", () -> new SwordItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.ratSwordDamage.get() - 7, -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<SwordItem> FAIRY_SWORD = ITEMS.register("fairy_sword", () -> new SwordItem(CAToolMaterial.TOOL_EMERALD, CAConfig.COMMON.fairySwordDamage.get() -7 , -2.4F, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<SwordItem> BIG_HAMMER = ITEMS.register("big_hammer", () -> new BigHammerItem(CAToolMaterial.TOOL_AMETHYST, CAConfig.COMMON.bigHammerDamage.get() - 12, -3F, new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ScytheItem> PRISMATIC_REAPER = ITEMS.register("prismatic_reaper", () -> new EnchantedScytheItem(CAToolMaterial.TOOL_ULTIMATE, CAConfig.COMMON.prismaticReaperDamage.get() - 37, -1.9F, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.UNBREAKING, 4), new EnchantmentAndLevel(Enchantments.SWEEPING, 4)}));
	public static final RegistryObject<SkateBowItem> SKATE_STRING_BOW = ITEMS.register("skate_string_bow", () -> new SkateBowItem(new Item.Properties().group(CAItemGroups.equipmentItemGroup).maxStackSize(1).maxDamage(384).defaultMaxDamage(384)));
	public static final RegistryObject<RayGunItem> RAY_GUN = ITEMS.register("ray_gun", () -> new RayGunItem(new Item.Properties().group(CAItemGroups.equipmentItemGroup).maxStackSize(1).maxDamage(50).defaultMaxDamage(50)));
	public static final RegistryObject<AxeItem> BATTLE_AXE = ITEMS.register("battle_axe", () -> new EnchantedAxeItem(CAToolMaterial.WEAPON_BATTLEAXE, CAConfig.COMMON.battleAxeDamage.get() - 47, -3F, new Item.Properties().rarity(Rarity.RARE).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.LOOTING, 3), new EnchantmentAndLevel(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<AxeItem> QUEEN_SCALE_BATTLE_AXE = ITEMS.register("queen_scale_battle_axe", () -> new EnchantedAxeItem(CAToolMaterial.WEAPON_QUEEN_BATTLEAXE, CAConfig.COMMON.queenAxeDamage.get() - 663, -2.8F, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.SHARPNESS, 5), new EnchantmentAndLevel(Enchantments.SMITE, 5), new EnchantmentAndLevel(Enchantments.BANE_OF_ARTHROPODS, 5), new EnchantmentAndLevel(Enchantments.KNOCKBACK, 3), new EnchantmentAndLevel(Enchantments.LOOTING, 3), new EnchantmentAndLevel(Enchantments.UNBREAKING, 2), new EnchantmentAndLevel(Enchantments.FIRE_ASPECT, 2) }));

	// Staffs
	public static final RegistryObject<ThunderStaffItem> THUNDER_STAFF = ITEMS.register("thunder_staff", () -> new ThunderStaffItem(new Item.Properties().group(CAItemGroups.equipmentItemGroup).maxDamage(50)));

	// ARMOR
	// Ultimate
	public static final RegistryObject<ArmorItem> ULTIMATE_HELMET = ITEMS.register("ultimate_helmet", () -> new EnchantedArmorItem(CAArmorMaterial.ULTIMATE, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 5), new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.RESPIRATION, 3), new EnchantmentAndLevel(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<ArmorItem> ULTIMATE_CHESTPLATE = ITEMS.register("ultimate_chestplate", () -> new EnchantedArmorItem(CAArmorMaterial.ULTIMATE, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 5), new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 5)}));
	public static final RegistryObject<ArmorItem> ULTIMATE_LEGGINGS = ITEMS.register("ultimate_leggings", () -> new EnchantedArmorItem(CAArmorMaterial.ULTIMATE, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 5), new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 5)}));
	public static final RegistryObject<ArmorItem> ULTIMATE_BOOTS = ITEMS.register("ultimate_boots", () -> new EnchantedArmorItem(CAArmorMaterial.ULTIMATE, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 5), new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 5), new EnchantmentAndLevel(Enchantments.FEATHER_FALLING, 3)}));

	// Lava Eel
	public static final RegistryObject<ArmorItem> LAVA_EEL_HELMET = ITEMS.register("lava_eel_helmet", () -> new EnchantedArmorItem(CAArmorMaterial.LAVA_EEL, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup).isImmuneToFire(),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 4), new EnchantmentAndLevel(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<ArmorItem> LAVA_EEL_CHESTPLATE = ITEMS.register("lava_eel_chestplate", () -> new EnchantedArmorItem(CAArmorMaterial.LAVA_EEL, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup).isImmuneToFire(),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 4)}));
	public static final RegistryObject<ArmorItem> LAVA_EEL_LEGGINGS = ITEMS.register("lava_eel_leggings", () -> new EnchantedArmorItem(CAArmorMaterial.LAVA_EEL, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup).isImmuneToFire(),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 4)}));
	public static final RegistryObject<ArmorItem> LAVA_EEL_BOOTS = ITEMS.register("lava_eel_boots", () -> new EnchantedArmorItem(CAArmorMaterial.LAVA_EEL, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup).isImmuneToFire(),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 4)}));

	// Emerald
	public static final RegistryObject<ArmorItem> EMERALD_HELMET = ITEMS.register("emerald_helmet", () -> new ArmorItem(CAArmorMaterial.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate", () -> new ArmorItem(CAArmorMaterial.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings", () -> new ArmorItem(CAArmorMaterial.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> EMERALD_BOOTS = ITEMS.register("emerald_boots", () -> new ArmorItem(CAArmorMaterial.EMERALD, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Experience
	public static final RegistryObject<ArmorItem> EXPERIENCE_HELMET = ITEMS.register("experience_helmet", () -> new EnchantedArmorItem(CAArmorMaterial.EXPERIENCE, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 2), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 1), new EnchantmentAndLevel(Enchantments.MENDING, 1)}));
	public static final RegistryObject<ArmorItem> EXPERIENCE_CHESTPLATE = ITEMS.register("experience_chestplate", () -> new EnchantedArmorItem(CAArmorMaterial.EXPERIENCE, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 2), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 1), new EnchantmentAndLevel(Enchantments.MENDING, 1)}));
	public static final RegistryObject<ArmorItem> EXPERIENCE_LEGGINGS = ITEMS.register("experience_leggings", () -> new EnchantedArmorItem(CAArmorMaterial.EXPERIENCE, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 2), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 1), new EnchantmentAndLevel(Enchantments.MENDING, 1)}));
	public static final RegistryObject<ArmorItem> EXPERIENCE_BOOTS = ITEMS.register("experience_boots", () -> new EnchantedArmorItem(CAArmorMaterial.EXPERIENCE, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 2), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 1), new EnchantmentAndLevel(Enchantments.FEATHER_FALLING, 1), new EnchantmentAndLevel(Enchantments.MENDING, 1)}));

	// Ruby
	public static final RegistryObject<ArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet", () -> new ArmorItem(CAArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate", () -> new ArmorItem(CAArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> RUBY_LEGGINGS = ITEMS.register("ruby_leggings", () -> new ArmorItem(CAArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots", () -> new ArmorItem(CAArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Amethyst
	public static final RegistryObject<ArmorItem> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new ArmorItem(CAArmorMaterial.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new ArmorItem(CAArmorMaterial.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> AMETHYST_LEGGINGS = ITEMS.register("amethyst_leggings", () -> new ArmorItem(CAArmorMaterial.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> AMETHYST_BOOTS = ITEMS.register("amethyst_boots", () -> new ArmorItem(CAArmorMaterial.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Tiger's Eye
	public static final RegistryObject<ArmorItem> TIGERS_EYE_HELMET = ITEMS.register("tigers_eye_helmet", () -> new ArmorItem(CAArmorMaterial.TIGERS_EYE, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> TIGERS_EYE_CHESTPLATE = ITEMS.register("tigers_eye_chestplate", () -> new ArmorItem(CAArmorMaterial.TIGERS_EYE, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> TIGERS_EYE_LEGGINGS = ITEMS.register("tigers_eye_leggings", () -> new ArmorItem(CAArmorMaterial.TIGERS_EYE, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> TIGERS_EYE_BOOTS = ITEMS.register("tigers_eye_boots", () -> new ArmorItem(CAArmorMaterial.TIGERS_EYE, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Lapis Lazuli
	public static final RegistryObject<ArmorItem> LAPIS_HELMET = ITEMS.register("lapis_helmet", () -> new EnchantedArmorItem(CAArmorMaterial.LAPIS, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 2), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 1), new EnchantmentAndLevel(Enchantments.RESPIRATION, 1), new EnchantmentAndLevel(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<ArmorItem> LAPIS_CHESTPLATE = ITEMS.register("lapis_chestplate", () -> new EnchantedArmorItem(CAArmorMaterial.LAPIS, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 2), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 1)}));
	public static final RegistryObject<ArmorItem> LAPIS_LEGGINGS = ITEMS.register("lapis_leggings", () -> new EnchantedArmorItem(CAArmorMaterial.LAPIS, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 2), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 1)}));
	public static final RegistryObject<ArmorItem> LAPIS_BOOTS = ITEMS.register("lapis_boots", () -> new EnchantedArmorItem(CAArmorMaterial.LAPIS, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 2), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 1)}));

	// Copper
	public static final RegistryObject<ArmorItem> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new ArmorItem(CAArmorMaterial.COPPER, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new ArmorItem(CAArmorMaterial.COPPER, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new ArmorItem(CAArmorMaterial.COPPER, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new ArmorItem(CAArmorMaterial.COPPER, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Tin
	public static final RegistryObject<ArmorItem> TIN_HELMET = ITEMS.register("tin_helmet", () -> new ArmorItem(CAArmorMaterial.TIN, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> TIN_CHESTPLATE = ITEMS.register("tin_chestplate", () -> new ArmorItem(CAArmorMaterial.TIN, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> TIN_LEGGINGS = ITEMS.register("tin_leggings", () -> new ArmorItem(CAArmorMaterial.TIN, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> TIN_BOOTS = ITEMS.register("tin_boots", () -> new ArmorItem(CAArmorMaterial.TIN, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Silver
	public static final RegistryObject<ArmorItem> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new ArmorItem(CAArmorMaterial.SILVER, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new ArmorItem(CAArmorMaterial.SILVER, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> SILVER_LEGGINGS = ITEMS.register("silver_leggings", () -> new ArmorItem(CAArmorMaterial.SILVER, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new ArmorItem(CAArmorMaterial.SILVER, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Platinum
	public static final RegistryObject<ArmorItem> PLATINUM_HELMET = ITEMS.register("platinum_helmet", () -> new ArmorItem(CAArmorMaterial.PLATINUM, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PLATINUM_CHESTPLATE = ITEMS.register("platinum_chestplate", () -> new ArmorItem(CAArmorMaterial.PLATINUM, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PLATINUM_LEGGINGS = ITEMS.register("platinum_leggings", () -> new ArmorItem(CAArmorMaterial.PLATINUM, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PLATINUM_BOOTS = ITEMS.register("platinum_boots", () -> new ArmorItem(CAArmorMaterial.PLATINUM, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Peacock Feather
	public static final RegistryObject<ArmorItem> PEACOCK_FEATHER_HELMET = ITEMS.register("peacock_feather_helmet", () -> new ArmorItem(CAArmorMaterial.PEACOCK_FEATHER, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PEACOCK_FEATHER_CHESTPLATE = ITEMS.register("peacock_feather_chestplate", () -> new ArmorItem(CAArmorMaterial.PEACOCK_FEATHER, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PEACOCK_FEATHER_LEGGINGS = ITEMS.register("peacock_feather_leggings", () -> new ArmorItem(CAArmorMaterial.PEACOCK_FEATHER, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PEACOCK_FEATHER_BOOTS = ITEMS.register("peacock_feather_boots", () -> new PeacockBootsItem(CAArmorMaterial.PEACOCK_FEATHER, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Pink Tourmaline
	public static final RegistryObject<ArmorItem> PINK_TOURMALINE_HELMET = ITEMS.register("pink_tourmaline_helmet", () -> new ArmorItem(CAArmorMaterial.PINK_TOURMALINE, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PINK_TOURMALINE_CHESTPLATE = ITEMS.register("pink_tourmaline_chestplate", () -> new ArmorItem(CAArmorMaterial.PINK_TOURMALINE, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PINK_TOURMALINE_LEGGINGS = ITEMS.register("pink_tourmaline_leggings", () -> new ArmorItem(CAArmorMaterial.PINK_TOURMALINE, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> PINK_TOURMALINE_BOOTS = ITEMS.register("pink_tourmaline_boots", () -> new ArmorItem(CAArmorMaterial.PINK_TOURMALINE, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Cat's Eye
	public static final RegistryObject<ArmorItem> CATS_EYE_HELMET = ITEMS.register("cats_eye_helmet", () -> new ArmorItem(CAArmorMaterial.CATS_EYE, EquipmentSlotType.HEAD, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> CATS_EYE_CHESTPLATE = ITEMS.register("cats_eye_chestplate", () -> new ArmorItem(CAArmorMaterial.CATS_EYE, EquipmentSlotType.CHEST, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> CATS_EYE_LEGGINGS = ITEMS.register("cats_eye_leggings", () -> new ArmorItem(CAArmorMaterial.CATS_EYE, EquipmentSlotType.LEGS, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> CATS_EYE_BOOTS = ITEMS.register("cats_eye_boots", () -> new ArmorItem(CAArmorMaterial.CATS_EYE, EquipmentSlotType.FEET, new Item.Properties().group(CAItemGroups.equipmentItemGroup)));

	// Royal Guardian
	public static final RegistryObject<ArmorItem> ROYAL_GUARDIAN_HELMET = ITEMS.register("royal_guardian_helmet", () -> new EnchantedArmorItem(CAArmorMaterial.ROYAL_GUARDIAN, EquipmentSlotType.HEAD, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 10), new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.UNBREAKING, 5), new EnchantmentAndLevel(Enchantments.RESPIRATION, 3), new EnchantmentAndLevel(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<ArmorItem> ROYAL_GUARDIAN_CHESTPLATE = ITEMS.register("royal_guardian_chestplate", () -> new EnchantedArmorItem(CAArmorMaterial.ROYAL_GUARDIAN, EquipmentSlotType.CHEST, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 10), new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.UNBREAKING, 5)}));
	public static final RegistryObject<ArmorItem> ROYAL_GUARDIAN_LEGGINGS = ITEMS.register("royal_guardian_leggings", () -> new EnchantedArmorItem(CAArmorMaterial.ROYAL_GUARDIAN, EquipmentSlotType.LEGS, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 10), new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.UNBREAKING, 5)}));
	public static final RegistryObject<ArmorItem> ROYAL_GUARDIAN_BOOTS = ITEMS.register("royal_guardian_boots", () -> new RoyaltyBootsItem(CAArmorMaterial.ROYAL_GUARDIAN, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup),
			new EnchantmentAndLevel[] { new EnchantmentAndLevel(Enchantments.PROTECTION, 10), new EnchantmentAndLevel(Enchantments.FIRE_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.BLAST_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentAndLevel(Enchantments.UNBREAKING, 5), new EnchantmentAndLevel(Enchantments.FROST_WALKER, 5)}));

	// Queen Scale
	public static final RegistryObject<ArmorItem> QUEEN_SCALE_HELMET = ITEMS.register("queen_scale_helmet", () -> new ArmorItem(CAArmorMaterial.QUEEN_SCALE, EquipmentSlotType.HEAD, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> QUEEN_SCALE_CHESTPLATE = ITEMS.register("queen_scale_chestplate", () -> new ArmorItem(CAArmorMaterial.QUEEN_SCALE, EquipmentSlotType.CHEST, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> QUEEN_SCALE_LEGGINGS = ITEMS.register("queen_scale_leggings", () -> new ArmorItem(CAArmorMaterial.QUEEN_SCALE, EquipmentSlotType.LEGS, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup)));
	public static final RegistryObject<ArmorItem> QUEEN_SCALE_BOOTS = ITEMS.register("queen_scale_boots", () -> new PeacockBootsItem(CAArmorMaterial.QUEEN_SCALE, new Item.Properties().rarity(RARITY_ROYALTY).group(CAItemGroups.equipmentItemGroup)));

	// SPAWN EGGS
	public static final RegistryObject<CASpawnEggItem> ENT_SPAWN_EGG = ITEMS.register("ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ENT, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> RED_ANT_SPAWN_EGG = ITEMS.register("red_ant_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.RED_ANT, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> BROWN_ANT_SPAWN_EGG = ITEMS.register("brown_ant_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.BROWN_ANT, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> RAINBOW_ANT_SPAWN_EGG = ITEMS.register("rainbow_ant_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.RAINBOW_ANT, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> UNSTABLE_ANT_SPAWN_EGG = ITEMS.register("unstable_ant_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.UNSTABLE_ANT, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> TERMITE_SPAWN_EGG = ITEMS.register("termite_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.TERMITE, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> HERCULES_BEETLE_SPAWN_EGG = ITEMS.register("hercules_beetle_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.HERCULES_BEETLE, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> EMERALD_GATOR_SPAWN_EGG = ITEMS.register("emerald_gator_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.EMERALD_GATOR, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> RUBY_BUG_SPAWN_EGG = ITEMS.register("ruby_bug_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.RUBY_BUG, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> STINK_BUG_SPAWN_EGG = ITEMS.register("stink_bug_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.STINK_BUG, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> ROBO_SNIPER_SPAWN_EGG = ITEMS.register("robo_sniper_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ROBO_SNIPER, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> ROBO_WARRIOR_SPAWN_EGG = ITEMS.register("robo_warrior_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ROBO_WARRIOR, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> BEAVER_SPAWN_EGG = ITEMS.register("beaver_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.BEAVER, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> APPLE_COW_SPAWN_EGG = ITEMS.register("apple_cow_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.APPLE_COW, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> GOLDEN_APPLE_COW_SPAWN_EGG = ITEMS.register("golden_apple_cow_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.GOLDEN_APPLE_COW, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<EnchantedCASpawnEggItem> ENCHANTED_GOLDEN_APPLE_COW_SPAWN_EGG = ITEMS.register("enchanted_golden_apple_cow_spawn_egg", () -> new EnchantedCASpawnEggItem(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<CASpawnEggItem> CRYSTAL_APPLE_COW_SPAWN_EGG = ITEMS.register("crystal_apple_cow_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.CRYSTAL_APPLE_COW, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<SpawnEggItem> IRON_GOLEM_SPAWN_EGG = ITEMS.register("iron_golem_spawn_egg", () -> new SpawnEggItem(EntityType.IRON_GOLEM, 0xe2dbd6, 0x74a332, new Item.Properties().group(CAItemGroups.eggsItemGroup)));
	public static final RegistryObject<SpawnEggItem> SNOW_GOLEM_SPAWN_EGG = ITEMS.register("snow_golem_spawn_egg", () -> new SpawnEggItem(EntityType.SNOW_GOLEM, 0xffffff, 0xe38a1d, new Item.Properties().group(CAItemGroups.eggsItemGroup)));

	// STRUCTURE SPAWN
	public static final RegistryObject<Item> INSTANT_SURVIVAL_SHELTER = ITEMS.register("instant_survival_shelter", () -> new StructureItem(new Item.Properties().group(CAItemGroups.itemsItemGroup).maxStackSize(16), "shelter"));
	public static final RegistryObject<Item> ZOO_CAGE_EXTRA_SMALL = ITEMS.register("zoo_cage_extra_small", () -> new StructureItem(new Item.Properties().group(CAItemGroups.itemsItemGroup).maxStackSize(16), "cage_xs"));
	public static final RegistryObject<Item> ZOO_CAGE_SMALL = ITEMS.register("zoo_cage_small", () -> new StructureItem(new Item.Properties().group(CAItemGroups.itemsItemGroup).maxStackSize(16), "cage_s"));
	public static final RegistryObject<Item> ZOO_CAGE_MEDIUM = ITEMS.register("zoo_cage_medium", () -> new StructureItem(new Item.Properties().group(CAItemGroups.itemsItemGroup).maxStackSize(16), "cage_m"));
	public static final RegistryObject<Item> ZOO_CAGE_LARGE = ITEMS.register("zoo_cage_large", () -> new StructureItem(new Item.Properties().group(CAItemGroups.itemsItemGroup).maxStackSize(16), "cage_l"));
	public static final RegistryObject<Item> ZOO_CAGE_EXTRA_LARGE = ITEMS.register("zoo_cage_extra_large", () -> new StructureItem(new Item.Properties().group(CAItemGroups.itemsItemGroup).maxStackSize(16), "cage_xl"));
}
