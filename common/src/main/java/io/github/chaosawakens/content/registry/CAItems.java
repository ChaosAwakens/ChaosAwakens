package io.github.chaosawakens.content.registry;

import com.google.common.collect.ImmutableList;
import com.mememan.nexus.asm.annotations.RegistrarEntry;
import com.mememan.nexus.template.property_wrapper.ItemPropertyWrapperTemplates;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.item.food.BaggedPopcornItem;
import io.github.chaosawakens.content.item.misc.BlackListItemNameBlockItem;
import io.github.chaosawakens.content.item.misc.DeferredItemNameBlockItem;
import io.github.chaosawakens.content.item.misc.EnchantedItem;
import io.github.chaosawakens.content.item.utility.MinersDreamItem;
import io.github.chaosawakens.content.item.utility.PowerChipItem;
import io.github.chaosawakens.core.template.CAArmorMaterialTemplates;
import io.github.chaosawakens.core.template.CAIPWTemplates;
import io.github.chaosawakens.core.template.CAItemTierTemplates;
import io.github.chaosawakens.core.template.CARecipeTemplates;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

@RegistrarEntry
public final class CAItems {
    protected static final ObjectArrayList<Supplier<Item>> ITEMS = new ObjectArrayList<>();
    protected static final ObjectArrayList<Supplier<Item>> EQUIPMENT = new ObjectArrayList<>();

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

    // Seeds
    public static final Supplier<Item> CORN_SEEDS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("corn_seeds"), () -> new BlackListItemNameBlockItem(() -> CABlocks.CORN_HEAD_BLOCK, new Item.Properties(), new Supplier[] {() -> CABlocks.CORN_HEAD_BLOCK, () -> CABlocks.CORN_BODY_BLOCK}), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> LETTUCE_SEEDS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lettuce_seeds"), () -> new DeferredItemNameBlockItem(() -> CABlocks.LETTUCE, new Item.Properties()), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> RADISH_SEEDS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("radish_seeds"), () -> new DeferredItemNameBlockItem(() -> CABlocks.RADISH, new Item.Properties()), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> QUINOA_SEEDS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("quinoa_seeds"), () -> new DeferredItemNameBlockItem(() -> CABlocks.QUINOA, new Item.Properties()), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> STRAWBERRY_SEEDS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("strawberry_seeds"), () -> new DeferredItemNameBlockItem(() -> CABlocks.STRAWBERRY_BUSH, new Item.Properties()), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> TOMATO_SEEDS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("tomato_seeds"), () -> new BlackListItemNameBlockItem(() -> CABlocks.TOMATO_HEAD_BLOCK, new Item.Properties(), new Supplier[] {() -> CABlocks.TOMATO_HEAD_BLOCK, () -> CABlocks.TOMATO_BODY_BLOCK}), CAIPWTemplates.FOOD, ITEMS);

    // Crystal Plants
    public static final Supplier<Item> CRYSTAL_APPLE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystal_apple"), () -> new Item(food(CAFood.FOOD_CRYSTAL_APPLE)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CRYSTAL_BEETROOT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystal_beetroot"), () -> new Item(food(CAFood.FOOD_CRYSTAL_BEETROOT)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CRYSTAL_CARROT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystal_carrot"), () -> new Item(food(CAFood.FOOD_CRYSTAL_CARROT)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> CRYSTAL_POTATO = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystal_potato"), () -> new Item(food(CAFood.FOOD_CRYSTAL_POTATO)), CAIPWTemplates.FOOD, ITEMS);

    // Manufactured
    public static final Supplier<Item> BLT_SANDWICH = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("blt_sandwich"), () -> new Item(food(CAFood.FOOD_BLT_SANDWICH)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> GARDEN_SALAD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("garden_salad"), () -> new Item(food(CAFood.FOOD_GARDEN_SALAD)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> SEAFOOD_PATTY = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("seafood_patty"), () -> new Item(food(CAFood.FOOD_SEAFOOD_PATTY)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> RADISH_STEW = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("radish_stew"), () -> new Item(food(CAFood.FOOD_RADISH_STEW).stacksTo(1)), CAIPWTemplates.FOOD, ITEMS);
    public static final Supplier<Item> QUINOA_SALAD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("quinoa_salad"), () -> new Item(food(CAFood.FOOD_QUINOA_SALAD).stacksTo(1)), CAIPWTemplates.FOOD, ITEMS);

    public static final Supplier<Item> EMPTY_POPCORN_BAG = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("empty_popcorn_bag"), () -> new Item(new Item.Properties().stacksTo(16)), CAIPWTemplates.FOOD, ITEMS)
            .withRecipe(CARecipeTemplates::popcornBagRecipe)
            .buildAndGet();
    public static final Supplier<Item> POPCORN = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("popcorn"), () -> new Item(food(CAFood.FOOD_POPCORN).stacksTo(16)), CAIPWTemplates.FOOD, ITEMS)
            .withRecipe(CARecipeTemplates::popcornRecipe)
            .buildAndGet();
    public static final Supplier<BaggedPopcornItem> POPCORN_BAG = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("popcorn_bag"), () -> new BaggedPopcornItem(food(CAFood.FOOD_POPCORN_BAG).stacksTo(1)), CAIPWTemplates.FOOD, ITEMS)
            .withRecipe(CARecipeTemplates::baggedPopcornRecipe)
            .buildAndGet();
    public static final Supplier<BaggedPopcornItem> SALTED_POPCORN_BAG = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("salted_popcorn_bag"), () -> new BaggedPopcornItem(food(CAFood.FOOD_SALTED_POPCORN_BAG).stacksTo(1)), CAIPWTemplates.FOOD, ITEMS)
            .withRecipe(CARecipeTemplates::baggedSaltedPopcornRecipe)
            .buildAndGet();
    public static final Supplier<BaggedPopcornItem> BUTTERED_POPCORN_BAG = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("buttered_popcorn_bag"), () -> new BaggedPopcornItem(food(CAFood.FOOD_BUTTERED_POPCORN_BAG).stacksTo(1)), CAIPWTemplates.FOOD, ITEMS)
            .withRecipe(CARecipeTemplates::baggedButteredPopcornRecipe)
            .buildAndGet();

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

    public static final Supplier<Item> SUNSTONE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("sunstone"), () -> new Item(new Item.Properties()), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);

    public static final Supplier<Item> PLATINUM_LUMP = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("platinum_lump"), () -> new Item(new Item.Properties()), CAIPWTemplates.LUMP_MATERIAL, ITEMS);

    public static final Supplier<Item> ALUMINUM_INGOT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("aluminum_ingot"), () -> new Item(new Item.Properties()), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);

    public static final Supplier<Item> TITANIUM_INGOT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("titanium_ingot"), () -> new Item(new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);
    public static final Supplier<Item> URANIUM_INGOT = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("uranium_ingot"), () -> new Item(new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.MATERIAL, ITEMS);

    public static final Supplier<Item> ALUMINUM_NUGGET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("aluminum_nugget"), () -> new Item(new Item.Properties()), ItemPropertyWrapperTemplates.MATERIAL_PIECE, ITEMS);

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

    public static final Supplier<Item> PEACOCK_FEATHER = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("peacock_feather"), () -> new Item(new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> DEAD_IRUKANDJI = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("dead_irukandji"), () -> new Item(new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);

    // Weapon Components
    public static final Supplier<Item> BIG_BERTHA_BLADE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("big_bertha_blade"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> BIG_BERTHA_GUARD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("big_bertha_guard"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);
    public static final Supplier<Item> BIG_BERTHA_HANDLE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("big_bertha_handle"), () -> new Item(new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);

    // Machine Components
    public static final Supplier<PowerChipItem> ALUMINUM_POWER_CHIP = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("aluminum_power_chip"), () -> new PowerChipItem(new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS);

    // Utility
    public static final Supplier<MinersDreamItem> MINERS_DREAM = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("miners_dream"), () -> new MinersDreamItem(new Item.Properties().stacksTo(16)), ItemPropertyWrapperTemplates.BASIC_GENERATED, ITEMS)
            .withRecipe(recipeConsumer -> CARecipeTemplates.threeRowRecipe(recipeConsumer, KUNZITE.get(), Items.REDSTONE_BLOCK, Items.GUNPOWDER))
            .buildAndGet();

    // Material Weapons and Tools
    public static final Supplier<SwordItem> ULTIMATE_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_sword"), () -> new SwordItem(CAItemTierTemplates.ULTIMATE, 22, -2.4F, new Item.Properties().fireResistant().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<ShovelItem> ULTIMATE_SHOVEL = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_shovel"), () -> new ShovelItem(CAItemTierTemplates.ULTIMATE, 8, -3.0F, new Item.Properties().fireResistant().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<PickaxeItem> ULTIMATE_PICKAXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_pickaxe"), () -> new PickaxeItem(CAItemTierTemplates.ULTIMATE, 6, -2.8F, new Item.Properties().fireResistant().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<AxeItem> ULTIMATE_AXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_axe"), () -> new AxeItem(CAItemTierTemplates.ULTIMATE, 28, -3.0F, new Item.Properties().fireResistant().fireResistant().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<HoeItem> ULTIMATE_HOE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_hoe"), () -> new HoeItem(CAItemTierTemplates.ULTIMATE, -4, -2.0F, new Item.Properties().fireResistant().fireResistant().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);

    public static final Supplier<SwordItem> RUBY_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_sword"), () -> new SwordItem(CAItemTierTemplates.RUBY, 16, -2.4F, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<ShovelItem> RUBY_SHOVEL = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_shovel"), () -> new ShovelItem(CAItemTierTemplates.RUBY, 5, -3.0F, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<PickaxeItem> RUBY_PICKAXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_pickaxe"), () -> new PickaxeItem(CAItemTierTemplates.RUBY, 4, -2.8F, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<AxeItem> RUBY_AXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_axe"), () -> new AxeItem(CAItemTierTemplates.RUBY, 18, -3.0F, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<HoeItem> RUBY_HOE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_hoe"), () -> new HoeItem(CAItemTierTemplates.RUBY, -3, -2.0F, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);

    public static final Supplier<SwordItem> KUNZITE_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_sword"), () -> new SwordItem(CAItemTierTemplates.KUNZITE, 11, -2.4F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<ShovelItem> KUNZITE_SHOVEL = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_shovel"), () -> new ShovelItem(CAItemTierTemplates.KUNZITE, 4, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<PickaxeItem> KUNZITE_PICKAXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_pickaxe"), () -> new PickaxeItem(CAItemTierTemplates.KUNZITE, 3, -2.8F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<AxeItem> KUNZITE_AXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_axe"), () -> new AxeItem(CAItemTierTemplates.KUNZITE, 13, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<HoeItem> KUNZITE_HOE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_hoe"), () -> new HoeItem(CAItemTierTemplates.KUNZITE, -3, -2.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);

    public static final Supplier<SwordItem> EMERALD_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_sword"), () -> new SwordItem(CAItemTierTemplates.EMERALD, 3, -2.4F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<ShovelItem> EMERALD_SHOVEL = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_shovel"), () -> new ShovelItem(CAItemTierTemplates.EMERALD, 2, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<PickaxeItem> EMERALD_PICKAXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_pickaxe"), () -> new PickaxeItem(CAItemTierTemplates.EMERALD, 1, -2.8F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<AxeItem> EMERALD_AXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_axe"), () -> new AxeItem(CAItemTierTemplates.EMERALD, 6, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<HoeItem> EMERALD_HOE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_hoe"), () -> new HoeItem(CAItemTierTemplates.EMERALD, -2, -2.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);

    public static final Supplier<SwordItem> CATS_EYE_SWORD = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_sword"), () -> new SwordItem(CAItemTierTemplates.CATS_EYE, 8, -2.4F, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT).withCustomName("Cat's Eye Sword").buildAndGet();
    public static final Supplier<ShovelItem> CATS_EYE_SHOVEL = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_shovel"), () -> new ShovelItem(CAItemTierTemplates.CATS_EYE, 5, -3.0F, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT).withCustomName("Cat's Eye Shovel").buildAndGet();
    public static final Supplier<PickaxeItem> CATS_EYE_PICKAXE = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_pickaxe"), () -> new PickaxeItem(CAItemTierTemplates.CATS_EYE, 4, -2.8F, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT).withCustomName("Cat's Eye Pickaxe").buildAndGet();
    public static final Supplier<AxeItem> CATS_EYE_AXE = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_axe"), () -> new AxeItem(CAItemTierTemplates.CATS_EYE, 10, -3.0F, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT).withCustomName("Cat's Eye Axe").buildAndGet();
    public static final Supplier<HoeItem> CATS_EYE_HOE = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_hoe"), () -> new HoeItem(CAItemTierTemplates.CATS_EYE, -3, -2.0F, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT).withCustomName("Cat's Eye Hoe").buildAndGet();

    public static final Supplier<SwordItem> PINK_TOURMALINE_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_sword"), () -> new SwordItem(CAItemTierTemplates.PINK_TOURMALINE, 7, -2.4F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<ShovelItem> PINK_TOURMALINE_SHOVEL = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_shovel"), () -> new ShovelItem(CAItemTierTemplates.PINK_TOURMALINE, 3, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<PickaxeItem> PINK_TOURMALINE_PICKAXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_pickaxe"), () -> new PickaxeItem(CAItemTierTemplates.PINK_TOURMALINE, 2, -2.8F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<AxeItem> PINK_TOURMALINE_AXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_axe"), () -> new AxeItem(CAItemTierTemplates.PINK_TOURMALINE, 9, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<HoeItem> PINK_TOURMALINE_HOE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_hoe"), () -> new HoeItem(CAItemTierTemplates.PINK_TOURMALINE, -3, -2.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);

    public static final Supplier<SwordItem> KYANITE_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kyanite_sword"), () -> new SwordItem(CAItemTierTemplates.KYANITE, 3, -2.4F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<ShovelItem> KYANITE_SHOVEL = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kyanite_shovel"), () -> new ShovelItem(CAItemTierTemplates.KYANITE, 1, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<PickaxeItem> KYANITE_PICKAXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kyanite_pickaxe"), () -> new PickaxeItem(CAItemTierTemplates.KYANITE, 0, -2.8F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<AxeItem> KYANITE_AXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kyanite_axe"), () -> new AxeItem(CAItemTierTemplates.KYANITE, 6, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<HoeItem> KYANITE_HOE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kyanite_hoe"), () -> new HoeItem(CAItemTierTemplates.KYANITE, -2, -2.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);

    public static final Supplier<SwordItem> CRYSTALWOOD_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystalwood_sword"), () -> new SwordItem(CAItemTierTemplates.CRYSTALWOOD, 3, -2.4F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<ShovelItem> CRYSTALWOOD_SHOVEL = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystalwood_shovel"), () -> new ShovelItem(CAItemTierTemplates.CRYSTALWOOD, 1.5F, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<PickaxeItem> CRYSTALWOOD_PICKAXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystalwood_pickaxe"), () -> new PickaxeItem(CAItemTierTemplates.CRYSTALWOOD, 1, -2.8F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<AxeItem> CRYSTALWOOD_AXE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystalwood_axe"), () -> new AxeItem(CAItemTierTemplates.CRYSTALWOOD, 6, -3.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<HoeItem> CRYSTALWOOD_HOE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("crystalwood_hoe"), () -> new HoeItem(CAItemTierTemplates.CRYSTALWOOD, 0, -2.0F, new Item.Properties().rarity(Rarity.COMMON)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);

    public static final Supplier<SwordItem> BASILISK_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("basilisk_sword"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_LOW, 25, -2.4F, new Item.Properties().durability(3000).rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<SwordItem> BIG_HAMMER = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("big_hammer"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_LOW, 14, -2.4F, new Item.Properties().durability(2000).rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<SwordItem> EXPERIENCE_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("experience_sword"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_LOW, 7, -2.4F, new Item.Properties().durability(1300).rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<SwordItem> FAIRY_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("fairy_sword"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_LOW, 9, -2.4F, new Item.Properties().durability(1024).rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<SwordItem> MANTIS_CLAW = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("mantis_claw"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_LOW, 9, -2.4F, new Item.Properties().durability(1024).rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<SwordItem> NIGHTMARE_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("nightmare_sword"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_LOW, 30, -2.4F, new Item.Properties().durability(3000).rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<SwordItem> POISON_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("poison_sword"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_LOW, 10, -2.4F, new Item.Properties().durability(1300).rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);
    public static final Supplier<SwordItem> RAT_SWORD = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("rat_sword"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_LOW, 9, -2.4F, new Item.Properties().durability(1024).rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_HANDHELD, EQUIPMENT);

    public static final Supplier<SwordItem> PRISMATIC_REAPER = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("prismatic_reaper"), () -> new SwordItem(CAItemTierTemplates.SPECIAL_MID, 28, -2.4F, new Item.Properties().durability(6000).rarity(Rarity.EPIC)), CAIPWTemplates.HANDHELD_LONG, EQUIPMENT);

    // Armor
    public static final Supplier<ArmorItem> ULTIMATE_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.ULTIMATE, ArmorItem.Type.HELMET, new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> ULTIMATE_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.ULTIMATE, ArmorItem.Type.CHESTPLATE, new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> ULTIMATE_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.ULTIMATE, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> ULTIMATE_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ultimate_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.ULTIMATE, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> RUBY_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.RUBY, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> RUBY_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.RUBY, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> RUBY_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.RUBY, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> RUBY_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("ruby_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.RUBY, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> KUNZITE_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.KUNZITE, ArmorItem.Type.HELMET, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> KUNZITE_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.KUNZITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> KUNZITE_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.KUNZITE, ArmorItem.Type.LEGGINGS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> KUNZITE_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("kunzite_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.KUNZITE, ArmorItem.Type.BOOTS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> EXPERIENCE_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("experience_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.EXPERIENCE, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> EXPERIENCE_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("experience_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.EXPERIENCE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> EXPERIENCE_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("experience_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.EXPERIENCE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> EXPERIENCE_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("experience_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.EXPERIENCE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> EMERALD_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.EMERALD, ArmorItem.Type.HELMET, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> EMERALD_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.EMERALD, ArmorItem.Type.CHESTPLATE, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> EMERALD_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.EMERALD, ArmorItem.Type.LEGGINGS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> EMERALD_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("emerald_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.EMERALD, ArmorItem.Type.BOOTS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> PINK_TOURMALINE_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.PINK_TOURMALINE, ArmorItem.Type.HELMET, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> PINK_TOURMALINE_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.PINK_TOURMALINE, ArmorItem.Type.CHESTPLATE, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> PINK_TOURMALINE_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.PINK_TOURMALINE, ArmorItem.Type.LEGGINGS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> PINK_TOURMALINE_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("pink_tourmaline_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.PINK_TOURMALINE, ArmorItem.Type.BOOTS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> CATS_EYE_HELMET = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.CATS_EYE, ArmorItem.Type.HELMET, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT).withCustomName("Cat's Eye Helmet").buildAndGet();
    public static final Supplier<ArmorItem> CATS_EYE_CHESTPLATE = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.CATS_EYE, ArmorItem.Type.CHESTPLATE, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT).withCustomName("Cat's Eye Chestplate").buildAndGet();
    public static final Supplier<ArmorItem> CATS_EYE_LEGGINGS = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.CATS_EYE, ArmorItem.Type.LEGGINGS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT).withCustomName("Cat's Eye Leggings").buildAndGet();
    public static final Supplier<ArmorItem> CATS_EYE_BOOTS = ItemPropertyWrapperTemplates.registerAndChain(CAConstants.prefix("cats_eye_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.CATS_EYE, ArmorItem.Type.BOOTS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT).withCustomName("Cat's Eye Boots").buildAndGet();

    public static final Supplier<ArmorItem> LAPIS_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lapis_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.LAPIS, ArmorItem.Type.HELMET, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> LAPIS_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lapis_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.LAPIS, ArmorItem.Type.CHESTPLATE, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> LAPIS_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lapis_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.LAPIS, ArmorItem.Type.LEGGINGS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> LAPIS_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lapis_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.LAPIS, ArmorItem.Type.BOOTS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> LAVA_EEL_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lava_eel_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.LAVA_EEL, ArmorItem.Type.HELMET, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> LAVA_EEL_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lava_eel_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.LAVA_EEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> LAVA_EEL_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lava_eel_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.LAVA_EEL, ArmorItem.Type.LEGGINGS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> LAVA_EEL_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("lava_eel_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.LAVA_EEL, ArmorItem.Type.BOOTS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> MOBZILLA_SCALE_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("mobzilla_scale_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.MOBZILLA_SCALE, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> MOBZILLA_SCALE_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("mobzilla_scale_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.MOBZILLA_SCALE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> MOBZILLA_SCALE_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("mobzilla_scale_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.MOBZILLA_SCALE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> MOBZILLA_SCALE_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("mobzilla_scale_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.MOBZILLA_SCALE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.RARE)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> MOTH_SCALE_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("moth_scale_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.MOTH_SCALE, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> MOTH_SCALE_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("moth_scale_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.MOTH_SCALE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> MOTH_SCALE_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("moth_scale_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.MOTH_SCALE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> MOTH_SCALE_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("moth_scale_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.MOTH_SCALE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.UNCOMMON)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> PEACOCK_FEATHER_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("peacock_feather_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.PEACOCK_FEATHER, ArmorItem.Type.HELMET, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> PEACOCK_FEATHER_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("peacock_feather_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.PEACOCK_FEATHER, ArmorItem.Type.CHESTPLATE, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> PEACOCK_FEATHER_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("peacock_feather_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.PEACOCK_FEATHER, ArmorItem.Type.LEGGINGS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> PEACOCK_FEATHER_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("peacock_feather_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.PEACOCK_FEATHER, ArmorItem.Type.BOOTS, new Item.Properties()), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> QUEEN_SCALE_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("queen_scale_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.QUEEN_SCALE, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> QUEEN_SCALE_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("queen_scale_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.QUEEN_SCALE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> QUEEN_SCALE_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("queen_scale_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.QUEEN_SCALE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> QUEEN_SCALE_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("queen_scale_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.QUEEN_SCALE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    public static final Supplier<ArmorItem> ROYAL_GUARDIAN_HELMET = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("royal_guardian_helmet"), () -> new ArmorItem(CAArmorMaterialTemplates.ROYAL_GUARDIAN, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> ROYAL_GUARDIAN_CHESTPLATE = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("royal_guardian_chestplate"), () -> new ArmorItem(CAArmorMaterialTemplates.ROYAL_GUARDIAN, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> ROYAL_GUARDIAN_LEGGINGS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("royal_guardian_leggings"), () -> new ArmorItem(CAArmorMaterialTemplates.ROYAL_GUARDIAN, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);
    public static final Supplier<ArmorItem> ROYAL_GUARDIAN_BOOTS = ItemPropertyWrapperTemplates.registerItemFromTemplate(CAConstants.prefix("royal_guardian_boots"), () -> new ArmorItem(CAArmorMaterialTemplates.ROYAL_GUARDIAN, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.EPIC)), ItemPropertyWrapperTemplates.BASIC_GENERATED, EQUIPMENT);

    private static Item.Properties food(FoodProperties foodProperties) {
        return new Item.Properties().food(foodProperties);
    }

    public static ImmutableList<Supplier<Item>> getItems() {
        return ImmutableList.copyOf(ITEMS);
    }

    public static ImmutableList<Supplier<Item>> getEquipment() {
        return ImmutableList.copyOf(EQUIPMENT);
    }
}