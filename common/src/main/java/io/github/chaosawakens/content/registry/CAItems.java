package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.template.property_wrapper.ItemPropertyWrapperTemplates;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.item.misc.EnchantedItem;
import io.github.chaosawakens.core.template.CAIPWTemplates;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

@RegistrarEntry
public final class CAItems {
    protected static final ObjectArrayList<Supplier<Item>> ITEMS = new ObjectArrayList<>();

    // Dairy
    public static final Supplier<Item> BUTTER = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("butter"), () -> new Item(food(CAFood.FOOD_BUTTER)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CHEESE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("cheese"), () -> new Item(food(CAFood.FOOD_CHEESE)), CAIPWTemplates.FOOD, ITEMS);

    // Meat
    public static final Supplier<Item> RAW_BACON = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("raw_bacon"), () -> new Item(food(CAFood.FOOD_RAW_BACON)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> COOKED_BACON = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("cooked_bacon"), () -> new Item(food(CAFood.FOOD_COOKED_BACON)), CAIPWTemplates.COOKED_FOOD, ITEMS);
    public static final Supplier<Item> RAW_CORNDOG = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("raw_corndog"), () -> new Item(food(CAFood.FOOD_RAW_CORNDOG)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> COOKED_CORNDOG = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("cooked_corndog"), () -> new Item(food(CAFood.FOOD_COOKED_CORNDOG)), CAIPWTemplates.COOKED_FOOD, ITEMS);
    public static final Supplier<Item> RAW_CRAB_MEAT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("raw_crab_meat"), () -> new Item(food(CAFood.FOOD_RAW_CRAB_MEAT)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> COOKED_CRAB_MEAT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("cooked_crab_meat"), () -> new Item(food(CAFood.FOOD_COOKED_CRAB_MEAT)), CAIPWTemplates.COOKED_FOOD, ITEMS);
    public static final Supplier<Item> RAW_PEACOCK_LEG = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("raw_peacock_leg"), () -> new Item(food(CAFood.FOOD_RAW_PEACOCK_LEG)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> COOKED_PEACOCK_LEG = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("cooked_peacock_leg"), () -> new Item(food(CAFood.FOOD_COOKED_PEACOCK_LEG)), CAIPWTemplates.COOKED_FOOD, ITEMS);
    public static final Supplier<Item> RAW_VENISON = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("raw_venison"), () -> new Item(food(CAFood.FOOD_RAW_VENISON)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> COOKED_VENISON = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("cooked_venison"), () -> new Item(food(CAFood.FOOD_COOKED_VENISON)), CAIPWTemplates.COOKED_FOOD, ITEMS);

    // Plants
    public static final Supplier<Item> CHERRIES = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("cherries"), () -> new Item(food(CAFood.FOOD_CHERRIES)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CORN = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("corn"), () -> new Item(food(CAFood.FOOD_CORN)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> LETTUCE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lettuce"), () -> new Item(food(CAFood.FOOD_LETTUCE)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> PEACH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("peach"), () -> new Item(food(CAFood.FOOD_PEACH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> RADISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("radish"), () -> new Item(food(CAFood.FOOD_RADISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> QUINOA = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("quinoa"), () -> new Item(food(CAFood.FOOD_QUINOA)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> STRAWBERRY = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("strawberry"), () -> new Item(food(CAFood.FOOD_STRAWBERRY)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> TOMATO = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("tomato"), () -> new Item(food(CAFood.FOOD_TOMATO)), CAIPWTemplates.FOOD, ITEMS);

    // Crystal Plants
    public static final Supplier<Item> CRYSTAL_APPLE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystal_apple"), () -> new Item(food(CAFood.FOOD_CRYSTAL_APPLE)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CRYSTAL_BEETROOT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystal_beetroot"), () -> new Item(food(CAFood.FOOD_CRYSTAL_BEETROOT)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CRYSTAL_CARROT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystal_carrot"), () -> new Item(food(CAFood.FOOD_CRYSTAL_CARROT)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CRYSTAL_POTATO = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystal_potato"), () -> new Item(food(CAFood.FOOD_CRYSTAL_POTATO)), CAIPWTemplates.FOOD, ITEMS);

    // Manufactured
    public static final Supplier<Item> BLT_SANDWICH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("blt_sandwich"), () -> new Item(food(CAFood.FOOD_BLT_SANDWICH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> GARDEN_SALAD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("garden_salad"), () -> new Item(food(CAFood.FOOD_GARDEN_SALAD)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> SEAFOOD_PATTY = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("seafood_patty"), () -> new Item(food(CAFood.FOOD_SEAFOOD_PATTY)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> RADISH_STEW = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("radish_stew"), () -> new Item(food(CAFood.FOOD_RADISH_STEW)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> QUINOA_SALAD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("quinoa_salad"), () -> new Item(food(CAFood.FOOD_QUINOA_SALAD)), CAIPWTemplates.FOOD, ITEMS);

    // Candy
    public static final Supplier<Item> BUTTER_CANDY = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("butter_candy"), () -> new Item(food(CAFood.FOOD_BUTTER_CANDY)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CANDYCANE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("candycane"), () -> new Item(food(CAFood.FOOD_CANDYCANE)), CAIPWTemplates.FOOD, ITEMS);

    // Fish
    public static final Supplier<Item> BLUE_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("blue_fish"), () -> new Item(food(CAFood.FOOD_BLUE_FISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> GRAY_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("gray_fish"), () -> new Item(food(CAFood.FOOD_GRAY_FISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> GREEN_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("green_fish"), () -> new Item(food(CAFood.FOOD_GREEN_FISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> ROCK_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("rock_fish"), () -> new Item(food(CAFood.FOOD_ROCK_FISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> PINK_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_fish"), () -> new Item(food(CAFood.FOOD_PINK_FISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> SPARK_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("spark_fish"), () -> new Item(food(CAFood.FOOD_SPARK_FISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> FIRE_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("fire_fish"), () -> new Item(food(CAFood.FOOD_FIRE_FISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> LAVA_EEL = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lava_eel"), () -> new Item(food(CAFood.FOOD_LAVA_EEL)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> SUN_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("sun_fish"), () -> new Item(food(CAFood.FOOD_SUN_FISH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> WOOD_FISH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("wood_fish"), () -> new Item(food(CAFood.FOOD_WOOD_FISH)), CAIPWTemplates.FOOD, ITEMS);

    // Golden Food
    public static final Supplier<Item> GOLDEN_MELON_SLICE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("golden_melon_slice"), () -> new Item(food(CAFood.FOOD_GOLDEN_MELON_SLICE)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> GOLDEN_BEETROOT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("golden_beetroot"), () -> new Item(food(CAFood.FOOD_GOLDEN_BEETROOT)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> GOLDEN_POTATO = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("golden_potato"), () -> new Item(food(CAFood.FOOD_GOLDEN_POTATO)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> BAKED_GOLDEN_POTATO = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("baked_golden_potato"), () -> new Item(food(CAFood.FOOD_BAKED_GOLDEN_POTATO)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<EnchantedItem> ENCHANTED_GOLDEN_CARROT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("enchanted_golden_carrot"), () -> new EnchantedItem(food(CAFood.FOOD_ENCHANTED_GOLDEN_CARROT)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<EnchantedItem> ULTIMATE_APPLE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_apple"), () -> new EnchantedItem(food(CAFood.FOOD_ULTIMATE_APPLE)), CAIPWTemplates.FOOD, ITEMS);

    // Components
    public static final Supplier<Item> SALT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("salt"), () -> new Item(new Item.Properties()), CAIPWTemplates.FOOD_COMPONENT, ITEMS);

    // Food on a Stick

    // Materials
    public static final Supplier<Item> KUNZITE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite"), () -> new Item(new Item.Properties()), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> RUBY = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby"), () -> new Item(new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);

    public static final Supplier<Item> TITANIUM_INGOT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("titanium_ingot"), () -> new Item(new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> URANIUM_INGOT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("uranium_ingot"), () -> new Item(new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);

    public static final Supplier<Item> TITANIUM_NUGGET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("titanium_nugget"), () -> new Item(new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.MATERIAL_PIECE, ITEMS);
    public static final Supplier<Item> URANIUM_NUGGET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("uranium_nugget"), () -> new Item(new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.MATERIAL_PIECE, ITEMS);

    // Mob Parts
    public static final Supplier<Item> WASP_STINGER = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("wasp_stinger"), () -> new Item(new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> DEAD_STINK_BUG = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("dead_stink_bug"), () -> new Item(new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> SEA_VIPER_TONGUE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("sea_viper_tongue"), () -> new Item(new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> TRIFFID_GOO = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("triffid_goo"), () -> new Item(new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> VORTEX_EYE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("vortex_eye"), () -> new Item(new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> WORM_TOOTH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("worm_tooth"), () -> new Item(new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);

    public static final Supplier<Item> BASILISK_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("basilisk_scale"), () -> new Item(new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> EMPEROR_SCORPION_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emperor_scorpion_scale"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> ENDER_DRAGON_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ender_dragon_scale"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> MOTH_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("moth_scale"), () -> new Item(new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> NIGHTMARE_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("nightmare_scale"), () -> new Item(new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> WATER_DRAGON_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("water_dragon_scale"), () -> new Item(new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);

    public static final Supplier<Item> MOBZILLA_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("mobzilla_scale"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> QUEEN_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("queen_scale"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> ROYAL_GUARDIAN_SCALE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("royal_guardian_scale"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);

    public static final Supplier<Item> JEFFERY_CORE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("jeffery_core"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);

    // Weapon Components
    public static final Supplier<Item> BIG_BERTHA_BLADE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("big_bertha_blade"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> BIG_BERTHA_GUARD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("big_bertha_guard"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> BIG_BERTHA_HANDLE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("big_bertha_handle"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);

    private static Item.Properties food(FoodProperties foodProperties) {
        return new Item.Properties().food(foodProperties);
    }

    public static ImmutableList<Supplier<Item>> getItems() {
        return ImmutableList.copyOf(ITEMS);
    }
}