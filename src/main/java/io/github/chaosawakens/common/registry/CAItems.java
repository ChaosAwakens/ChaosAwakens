package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.client.entity.render.AttitudeAdjusterItemRenderer;
import io.github.chaosawakens.client.entity.render.BattleAxeItemRenderer;
import io.github.chaosawakens.client.entity.render.BigBerthaItemRenderer;
import io.github.chaosawakens.client.entity.render.QueenScaleBattleAxeItemRenderer;
import io.github.chaosawakens.client.entity.render.RoyalGuardianSwordItemRenderer;
import io.github.chaosawakens.client.entity.render.SlayerChainsawItemRenderer;
import io.github.chaosawakens.common.config.CACommonConfig;
import io.github.chaosawakens.common.items.BasiliskSwordItem;
import io.github.chaosawakens.common.items.BeetrootOnAStickItem;
import io.github.chaosawakens.common.items.BigHammerItem;
import io.github.chaosawakens.common.items.CABoatItem;
import io.github.chaosawakens.common.items.CABoatWoodTypes;
import io.github.chaosawakens.common.items.CASpawnEggItem;
import io.github.chaosawakens.common.items.CritterCageItem;
import io.github.chaosawakens.common.items.DevItem;
import io.github.chaosawakens.common.items.EnchantedArmorItem;
import io.github.chaosawakens.common.items.EnchantedAxeItem;
import io.github.chaosawakens.common.items.EnchantedHoeItem;
import io.github.chaosawakens.common.items.EnchantedItem;
import io.github.chaosawakens.common.items.EnchantedPickaxeItem;
import io.github.chaosawakens.common.items.EnchantedShovelItem;
import io.github.chaosawakens.common.items.EnchantedSwordItem;
import io.github.chaosawakens.common.items.EnderScaleArmorItem;
import io.github.chaosawakens.common.items.IrukandjiArrowItem;
import io.github.chaosawakens.common.items.LavaEelArmorItem;
import io.github.chaosawakens.common.items.LeafyChickenEggItem;
import io.github.chaosawakens.common.items.MantisClawItem;
import io.github.chaosawakens.common.items.MinersDreamItem;
import io.github.chaosawakens.common.items.PinkTourmalineBucketItem;
import io.github.chaosawakens.common.items.PoisonSwordItem;
import io.github.chaosawakens.common.items.PopcornItem;
import io.github.chaosawakens.common.items.PowerChipItem;
import io.github.chaosawakens.common.items.RayGunItem;
import io.github.chaosawakens.common.items.SkateBowItem;
import io.github.chaosawakens.common.items.SlowFallBoots;
import io.github.chaosawakens.common.items.StructureItem;
import io.github.chaosawakens.common.items.ThunderStaffItem;
import io.github.chaosawakens.common.items.UltimateAxeItem;
import io.github.chaosawakens.common.items.UltimateBowItem;
import io.github.chaosawakens.common.items.UltimateCrossbowItem;
import io.github.chaosawakens.common.items.UltimateFishingRodItem;
import io.github.chaosawakens.common.items.UltimateHoeItem;
import io.github.chaosawakens.common.items.UltimatePickaxeItem;
import io.github.chaosawakens.common.items.UltimateShovelItem;
import io.github.chaosawakens.common.items.extended.AttitudeAdjusterItem;
import io.github.chaosawakens.common.items.extended.BattleAxeItem;
import io.github.chaosawakens.common.items.extended.BigBerthaItem;
import io.github.chaosawakens.common.items.extended.EnchantedScytheItem;
import io.github.chaosawakens.common.items.extended.QueenScaleBattleAxeItem;
import io.github.chaosawakens.common.items.extended.RoyalGuardianSwordItem;
import io.github.chaosawakens.common.items.extended.SlayerChainsawItem;
import io.github.chaosawakens.common.util.EnumUtils;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SignItem;
import net.minecraft.item.SoupItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CAItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChaosAwakens.MODID);

	// RARITIES
	public static final Rarity RARITY_ROYALTY = Rarity.create(ChaosAwakens.MODID + ":royalty", TextFormatting.GOLD);

	// MEATS
	public static final RegistryObject<Item> BACON = ITEMS.register("bacon", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_BACON).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> COOKED_BACON = ITEMS.register("cooked_bacon", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_BACON).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CORNDOG = ITEMS.register("corndog", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_CORNDOG).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> COOKED_CORNDOG = ITEMS.register("cooked_corndog", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_CORNDOG).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CRAB_MEAT = ITEMS.register("crab_meat", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRAB_MEAT).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> COOKED_CRAB_MEAT = ITEMS.register("cooked_crab_meat", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_CRAB_MEAT).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> PEACOCK_LEG = ITEMS.register("peacock_leg", () -> new Item(new Item.Properties().food(CAFoods.FOOD_PEACOCK_LEG).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> COOKED_PEACOCK_LEG = ITEMS.register("cooked_peacock_leg", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_PEACOCK_LEG).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> VENISON = ITEMS.register("venison", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_VENISON).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> COOKED_VENISON = ITEMS.register("cooked_venison", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_VENISON).tab(CAItemGroups.FOOD)));

	// PLANTS
	public static final RegistryObject<Item> CHERRIES = ITEMS.register("cherries", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CHERRIES).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CORN = ITEMS.register("corn", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CORN).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce", () -> new Item(new Item.Properties().food(CAFoods.FOOD_LETTUCE).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> PEACH = ITEMS.register("peach", () -> new Item(new Item.Properties().food(CAFoods.FOOD_PEACH).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> RADISH = ITEMS.register("radish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RADISH).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry", () -> new Item(new Item.Properties().food(CAFoods.FOOD_STRAWBERRY).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties().food(CAFoods.FOOD_TOMATO).tab(CAItemGroups.FOOD)));

	// CRYSTAL PLANTS
	public static final RegistryObject<Item> CRYSTAL_APPLE = ITEMS.register("crystal_apple", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_APPLE).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CRYSTAL_CARROT = ITEMS.register("crystal_carrot", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_CARROT).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CRYSTAL_BEETROOT = ITEMS.register("crystal_beetroot", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_BEETROOT).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CRYSTAL_POTATO = ITEMS.register("crystal_potato", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_POTATO).tab(CAItemGroups.FOOD)));

	// MANUFACTURED
	public static final RegistryObject<Item> BLT_SANDWICH = ITEMS.register("blt_sandwich", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BLT_SANDWICH).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> BUTTER = ITEMS.register("butter", () -> new Item(new Item.Properties().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CHEESE).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> GARDEN_SALAD = ITEMS.register("garden_salad", () -> new SoupItem(new Item.Properties().food(CAFoods.FOOD_GARDEN_SALAD).stacksTo(1).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> RADISH_STEW = ITEMS.register("radish_stew", () -> new SoupItem(new Item.Properties().food(CAFoods.FOOD_RADISH_STEW).stacksTo(1).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> SEAFOOD_PATTY = ITEMS.register("seafood_patty", () -> new Item(new Item.Properties().food(CAFoods.FOOD_SEAFOOD_PATTY).tab(CAItemGroups.FOOD)));

	// POPCORN
	public static final RegistryObject<Item> EMPTY_POPCORN_BAG = ITEMS.register("empty_popcorn_bag", () -> new Item(new Item.Properties().stacksTo(16).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> POPCORN = ITEMS.register("popcorn", () -> new Item(new Item.Properties().stacksTo(16).food(CAFoods.FOOD_POPCORN).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> POPCORN_BAG = ITEMS.register("popcorn_bag", () -> new PopcornItem(new Item.Properties().stacksTo(1).food(CAFoods.FOOD_POPCORN_BAG).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> SALTED_POPCORN_BAG = ITEMS.register("salted_popcorn_bag", () -> new PopcornItem(new Item.Properties().stacksTo(1).food(CAFoods.FOOD_SALTED_POPCORN_BAG).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> BUTTERED_POPCORN_BAG = ITEMS.register("buttered_popcorn_bag", () -> new PopcornItem(new Item.Properties().stacksTo(1).food(CAFoods.FOOD_BUTTERED_POPCORN_BAG).tab(CAItemGroups.FOOD)));

	// CANDY
	public static final RegistryObject<Item> BUTTER_CANDY = ITEMS.register("butter_candy", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BUTTER_CANDY).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CANDYCANE = ITEMS.register("candycane", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CANDYCANE).tab(CAItemGroups.FOOD)));

	// FISH
	public static final RegistryObject<Item> BLUE_FISH = ITEMS.register("blue_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BLUE_FISH).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> GRAY_FISH = ITEMS.register("gray_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_GRAY_FISH).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> GREEN_FISH = ITEMS.register("green_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_GREEN_FISH).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> PINK_FISH = ITEMS.register("pink_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_PINK_FISH).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> ROCK_FISH = ITEMS.register("rock_fish", () -> new Item(new Item.Properties().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> FIRE_FISH = ITEMS.register("fire_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_FIRE_FISH).fireResistant().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> SPARK_FISH = ITEMS.register("spark_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_SPARK_FISH).fireResistant().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> SUN_FISH = ITEMS.register("sun_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_SUN_FISH).fireResistant().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> WOOD_FISH = ITEMS.register("wood_fish", () -> new Item(new Item.Properties().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> LAVA_EEL = ITEMS.register("lava_eel", () -> new Item(new Item.Properties().food(CAFoods.FOOD_LAVA_EEL).fireResistant().tab(CAItemGroups.FOOD)));

	// GOLDEN FOOD
	public static final RegistryObject<Item> GOLDEN_MELON_SLICE = ITEMS.register("golden_melon_slice", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAFoods.FOOD_GOLDEN_MELON_SLICE).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> GOLDEN_BEETROOT = ITEMS.register("golden_beetroot", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAFoods.FOOD_GOLDEN_BEETROOT).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> GOLDEN_POTATO = ITEMS.register("golden_potato", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAFoods.FOOD_GOLDEN_POTATO).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> GOLDEN_BAKED_POTATO = ITEMS.register("golden_baked_potato", () -> new Item(new Item.Properties().rarity(Rarity.RARE).food(CAFoods.FOOD_GOLDEN_BAKED_POTATO).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> ULTIMATE_APPLE = ITEMS.register("ultimate_apple", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(CAFoods.FOOD_ULTIMATE_APPLE).tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> ENCHANTED_GOLDEN_CARROT = ITEMS.register("enchanted_golden_carrot", () -> new EnchantedItem(new Item.Properties().rarity(Rarity.EPIC).food(CAFoods.FOOD_ENCHANTED_GOLDEN_CARROT).tab(CAItemGroups.FOOD)));

	// SEEDS
	public static final RegistryObject<Item> STRAWBERRY_SEEDS = ITEMS.register("strawberry_seeds", () -> new BlockNamedItem(CABlocks.STRAWBERRY_BUSH.get(), new Item.Properties().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", () -> new BlockNamedItem(CABlocks.TOMATO_TOP_BLOCK.get(), new Item.Properties().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<Item> CORN_SEEDS = ITEMS.register("corn_seeds", () -> new BlockNamedItem(CABlocks.CORN_TOP_BLOCK.get(), new Item.Properties().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<BlockNamedItem> LETTUCE_SEEDS = ITEMS.register("lettuce_seeds", () -> new BlockNamedItem(CABlocks.LETTUCE.get(), new Item.Properties().tab(CAItemGroups.FOOD)));
	public static final RegistryObject<BlockNamedItem> RADISH_SEEDS = ITEMS.register("radish_seeds", () -> new BlockNamedItem(CABlocks.RADISH.get(), new Item.Properties().tab(CAItemGroups.FOOD)));

	// SALT
	public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties().tab(CAItemGroups.FOOD)));

	// MINERALS
	public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> TIGERS_EYE = ITEMS.register("tigers_eye", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> TITANIUM_NUGGET = ITEMS.register("titanium_nugget", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> URANIUM_NUGGET = ITEMS.register("uranium_nugget", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> ALUMINUM_NUGGET = ITEMS.register("aluminum_nugget", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> COPPER_LUMP = ITEMS.register("copper_lump", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> TIN_LUMP = ITEMS.register("tin_lump", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> SILVER_LUMP = ITEMS.register("silver_lump", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> PLATINUM_LUMP = ITEMS.register("platinum_lump", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> PINK_TOURMALINE_INGOT = ITEMS.register("pink_tourmaline_ingot", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> PINK_TOURMALINE_NUGGET = ITEMS.register("pink_tourmaline_nugget", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> PINK_TOURMALINE_SHARD = ITEMS.register("pink_tourmaline_shard", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> CATS_EYE_INGOT = ITEMS.register("cats_eye_ingot", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> CATS_EYE_NUGGET = ITEMS.register("cats_eye_nugget", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> CATS_EYE_SHARD = ITEMS.register("cats_eye_shard", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> SUNSTONE = ITEMS.register("sunstone", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> BLOODSTONE = ITEMS.register("bloodstone", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));

	// MOB ITEMS
	public static final RegistryObject<Item> BASILISK_SCALE = ITEMS.register("basilisk_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> DEAD_STINK_BUG = ITEMS.register("dead_stink_bug", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> EMPEROR_SCORPION_SCALE = ITEMS.register("emperor_scorpion_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> ENDER_DRAGON_SCALE = ITEMS.register("ender_dragon_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> MOTH_SCALE = ITEMS.register("moth_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> NIGHTMARE_SCALE = ITEMS.register("nightmare_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> SEA_VIPER_TONGUE = ITEMS.register("sea_viper_tongue", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> TRIFFID_GOO = ITEMS.register("triffid_goo", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> VORTEX_EYE = ITEMS.register("vortex_eye", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> WATER_DRAGON_SCALE = ITEMS.register("water_dragon_scale", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> WORM_TOOTH = ITEMS.register("worm_tooth", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.ITEMS)));

	public static final RegistryObject<Item> MOBZILLA_SCALE = ITEMS.register("mobzilla_scale", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> ROYAL_GUARDIAN_SCALE = ITEMS.register("royal_guardian_scale", () -> new Item(new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> QUEEN_SCALE = ITEMS.register("queen_scale", () -> new Item(new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.ITEMS)));

	public static final RegistryObject<Item> BIG_BERTHA_BLADE = ITEMS.register("big_bertha_blade", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> BIG_BERTHA_GUARD = ITEMS.register("big_bertha_guard", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> BIG_BERTHA_HANDLE = ITEMS.register("big_bertha_handle", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.ITEMS)));

	public static final RegistryObject<LeafyChickenEggItem> LEAFY_CHICKEN_EGG = ITEMS.register("leafy_chicken_egg", () -> new LeafyChickenEggItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.ITEMS)));

	// CARROT PIG
	public static final RegistryObject<BeetrootOnAStickItem> BEETROOT_ON_A_STICK = ITEMS.register("beetroot_on_a_stick",() -> new BeetrootOnAStickItem(new Item.Properties().durability(120).tab(CAItemGroups.ITEMS), 3));
	public static final RegistryObject<BeetrootOnAStickItem> CRYSTAL_BEETROOT_ON_A_STICK = ITEMS.register("crystal_beetroot_on_a_stick",() -> new BeetrootOnAStickItem(new Item.Properties().durability(80).tab(CAItemGroups.ITEMS), 5));
	public static final RegistryObject<BeetrootOnAStickItem> GOLDEN_BEETROOT_ON_A_STICK = ITEMS.register("golden_beetroot_on_a_stick",() -> new BeetrootOnAStickItem(new Item.Properties().durability(150).tab(CAItemGroups.ITEMS), 8));

	//CRITTER CAGES
	public static final RegistryObject<CritterCageItem> CRITTER_CAGE = ITEMS.register("critter_cage", () -> new CritterCageItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.ITEMS)));

	// MISC
	public static final RegistryObject<PowerChipItem> ALUMINUM_POWER_CHIP = ITEMS.register("aluminum_power_chip", () -> new PowerChipItem(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<PowerChipItem> CRYSTAL_POWER_CHIP = ITEMS.register("crystal_power_chip", () -> new PowerChipItem(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> CRYSTAL_SHARD = ITEMS.register("crystal_shard", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> PEACOCK_FEATHER = ITEMS.register("peacock_feather", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<Item> DEAD_IRUKANDJI = ITEMS.register("dead_irukandji", () -> new Item(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<IrukandjiArrowItem> IRUKANDJI_ARROW = ITEMS.register("irukandji_arrow", () -> new IrukandjiArrowItem(new Item.Properties().tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<WallOrFloorItem> CRYSTAL_TORCH = ITEMS.register("crystal_torch", () -> new WallOrFloorItem(CABlocks.CRYSTAL_TORCH.get(), CABlocks.WALL_CRYSTAL_TORCH.get(), new Item.Properties().tab(CAItemGroups.BLOCKS)));
	public static final RegistryObject<WallOrFloorItem> SUNSTONE_TORCH = ITEMS.register("sunstone_torch", () -> new WallOrFloorItem(CABlocks.SUNSTONE_TORCH.get(), CABlocks.WALL_SUNSTONE_TORCH.get(), new Item.Properties().tab(CAItemGroups.BLOCKS)));
	public static final RegistryObject<WallOrFloorItem> EXTREME_TORCH = ITEMS.register("extreme_torch", () -> new WallOrFloorItem(CABlocks.EXTREME_TORCH.get(), CABlocks.WALL_EXTREME_TORCH.get(), new Item.Properties().tab(CAItemGroups.BLOCKS)));
	
	// PINK TOURMALINE BUCKET
	public static final RegistryObject<BucketItem> PINK_TOURMALINE_BUCKET = ITEMS.register("pink_tourmaline_bucket", () -> new PinkTourmalineBucketItem(() -> Fluids.EMPTY, new Item.Properties().stacksTo(16).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<BucketItem> LAVA_PINK_TOURMALINE_BUCKET = ITEMS.register("lava_pink_tourmaline_bucket", () -> new PinkTourmalineBucketItem(() -> Fluids.LAVA, new Item.Properties().stacksTo(1).craftRemainder(CAItems.PINK_TOURMALINE_BUCKET.get()).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<BucketItem> WATER_PINK_TOURMALINE_BUCKET = ITEMS.register("water_pink_tourmaline_bucket", () -> new PinkTourmalineBucketItem(() -> Fluids.WATER, new Item.Properties().stacksTo(1).craftRemainder(CAItems.PINK_TOURMALINE_BUCKET.get()).tab(CAItemGroups.ITEMS)));
	
	// SIGNS
	public static final RegistryObject<SignItem> APPLE_SIGN = ITEMS.register("apple_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.BLOCKS), CABlocks.APPLE_SIGN.get(), CABlocks.APPLE_WALL_SIGN.get()));
	public static final RegistryObject<SignItem> CHERRY_SIGN = ITEMS.register("cherry_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.BLOCKS), CABlocks.CHERRY_SIGN.get(), CABlocks.CHERRY_WALL_SIGN.get()));
	public static final RegistryObject<SignItem> DUPLICATION_SIGN = ITEMS.register("duplication_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.BLOCKS), CABlocks.DUPLICATION_SIGN.get(), CABlocks.DUPLICATION_WALL_SIGN.get()));
	public static final RegistryObject<SignItem> GINKGO_SIGN = ITEMS.register("ginkgo_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.BLOCKS), CABlocks.GINKGO_SIGN.get(), CABlocks.GINKGO_WALL_SIGN.get()));
	public static final RegistryObject<SignItem> MESOZOIC_SIGN = ITEMS.register("mesozoic_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.BLOCKS), CABlocks.MESOZOIC_SIGN.get(), CABlocks.MESOZOIC_WALL_SIGN.get()));
	public static final RegistryObject<SignItem> DENSEWOOD_SIGN = ITEMS.register("densewood_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.BLOCKS), CABlocks.DENSEWOOD_SIGN.get(), CABlocks.DENSEWOOD_WALL_SIGN.get()));
	public static final RegistryObject<SignItem> PEACH_SIGN = ITEMS.register("peach_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.BLOCKS), CABlocks.PEACH_SIGN.get(), CABlocks.PEACH_WALL_SIGN.get()));
	public static final RegistryObject<SignItem> SKYWOOD_SIGN = ITEMS.register("skywood_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(CAItemGroups.BLOCKS), CABlocks.SKYWOOD_SIGN.get(), CABlocks.SKYWOOD_WALL_SIGN.get()));

	//BOATS
	public static final RegistryObject<CABoatItem> APPLE_BOAT = ITEMS.register("apple_boat", () -> new CABoatItem(CABoatWoodTypes.APPLE.name(), new Item.Properties().stacksTo(1).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<CABoatItem> CHERRY_BOAT = ITEMS.register("cherry_boat", () -> new CABoatItem(CABoatWoodTypes.CHERRY.name(), new Item.Properties().stacksTo(1).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<CABoatItem> DUPLICATOR_BOAT = ITEMS.register("duplication_boat", () -> new CABoatItem(CABoatWoodTypes.DUPLICATOR.name(), new Item.Properties().stacksTo(1).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<CABoatItem> GINKGO_BOAT = ITEMS.register("ginkgo_boat", () -> new CABoatItem(CABoatWoodTypes.GINKGO.name(), new Item.Properties().stacksTo(1).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<CABoatItem> MESOZOIC_BOAT = ITEMS.register("mesozoic_boat", () -> new CABoatItem(CABoatWoodTypes.MESOZOIC.name(), new Item.Properties().stacksTo(1).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<CABoatItem> DENSEWOOD_BOAT = ITEMS.register("densewood_boat", () -> new CABoatItem(CABoatWoodTypes.DENSEWOOD.name(), new Item.Properties().stacksTo(1).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<CABoatItem> PEACH_BOAT = ITEMS.register("peach_boat", () -> new CABoatItem(CABoatWoodTypes.PEACH.name(), new Item.Properties().stacksTo(1).tab(CAItemGroups.ITEMS)));
	public static final RegistryObject<CABoatItem> SKYWOOD_BOAT = ITEMS.register("skywood_boat", () -> new CABoatItem(CABoatWoodTypes.SKYWOOD.name(), new Item.Properties().stacksTo(1).tab(CAItemGroups.ITEMS)));

	// TOOLS
	// Ultimate
	public static final RegistryObject<EnchantedSwordItem> ULTIMATE_SWORD = ITEMS.register("ultimate_sword", () -> new EnchantedSwordItem(EnumUtils.CAItemTier.TOOL_ULTIMATE, CACommonConfig.COMMON.ultimateSwordDamage.get() - 37, -2.4F, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.KNOCKBACK, 2), new EnchantmentData(Enchantments.MOB_LOOTING, 3), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.FIRE_ASPECT, 2)}));
	public static final RegistryObject<UltimateShovelItem> ULTIMATE_SHOVEL = ITEMS.register("ultimate_shovel", () -> new UltimateShovelItem(EnumUtils.CAItemTier.TOOL_ULTIMATE, CACommonConfig.COMMON.ultimateShovelDamage.get() - 37, -3, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BLOCK_EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<UltimatePickaxeItem> ULTIMATE_PICKAXE = ITEMS.register("ultimate_pickaxe", () -> new UltimatePickaxeItem(EnumUtils.CAItemTier.TOOL_ULTIMATE, CACommonConfig.COMMON.ultimatePickaxeDamage.get() - 37, -2.8F, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BLOCK_EFFICIENCY, 5), new EnchantmentData(Enchantments.BLOCK_FORTUNE, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<UltimateAxeItem> ULTIMATE_AXE = ITEMS.register("ultimate_axe", () -> new UltimateAxeItem(EnumUtils.CAItemTier.TOOL_ULTIMATE, CACommonConfig.COMMON.ultimateAxeDamage.get() - 37, -3, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BLOCK_EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<UltimateHoeItem> ULTIMATE_HOE = ITEMS.register("ultimate_hoe", () -> new UltimateHoeItem(EnumUtils.CAItemTier.TOOL_ULTIMATE, CACommonConfig.COMMON.ultimateHoeDamage.get() - 37, 0.0F, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BLOCK_EFFICIENCY, 5), new EnchantmentData(Enchantments.BLOCK_FORTUNE, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<UltimateBowItem> ULTIMATE_BOW = ITEMS.register("ultimate_bow", () -> new UltimateBowItem(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT).stacksTo(1).durability(1024),
			new EnchantmentData[]{new EnchantmentData(Enchantments.POWER_ARROWS, 5), new EnchantmentData(Enchantments.FLAMING_ARROWS, 1), new EnchantmentData(Enchantments.PUNCH_ARROWS, 2), new EnchantmentData(Enchantments.INFINITY_ARROWS, 1)}));
	public static final RegistryObject<UltimateCrossbowItem> ULTIMATE_CROSSBOW = ITEMS.register("ultimate_crossbow", () -> new UltimateCrossbowItem(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT).stacksTo(1).durability(1024),
			new EnchantmentData[]{new EnchantmentData(Enchantments.PIERCING, 4), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<UltimateFishingRodItem> ULTIMATE_FISHING_ROD = ITEMS.register("ultimate_fishing_rod", () -> new UltimateFishingRodItem(new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT).stacksTo(1).durability(1024),
			new EnchantmentData[]{new EnchantmentData(Enchantments.UNBREAKING, 3)}));

	// Emerald
	public static final RegistryObject<SwordItem> EMERALD_SWORD = ITEMS.register("emerald_sword", () -> new EnchantedSwordItem(EnumUtils.CAItemTier.TOOL_EMERALD, CACommonConfig.COMMON.emeraldSwordDamage.get() - 7, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.MOB_LOOTING, 1)}));
	public static final RegistryObject<EnchantedShovelItem> EMERALD_SHOVEL = ITEMS.register("emerald_shovel", () -> new EnchantedShovelItem(EnumUtils.CAItemTier.TOOL_EMERALD, CACommonConfig.COMMON.emeraldShovelDamage.get() - 7, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SILK_TOUCH, 1)}));
	public static final RegistryObject<EnchantedPickaxeItem> EMERALD_PICKAXE = ITEMS.register("emerald_pickaxe", () -> new EnchantedPickaxeItem(EnumUtils.CAItemTier.TOOL_EMERALD, CACommonConfig.COMMON.emeraldPickaxeDamage.get() - 7, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SILK_TOUCH, 1)}));
	public static final RegistryObject<EnchantedAxeItem> EMERALD_AXE = ITEMS.register("emerald_axe", () -> new EnchantedAxeItem(EnumUtils.CAItemTier.TOOL_EMERALD, CACommonConfig.COMMON.emeraldAxeDamage.get() - 7, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SILK_TOUCH, 1)}));
	public static final RegistryObject<EnchantedHoeItem> EMERALD_HOE = ITEMS.register("emerald_hoe", () -> new EnchantedHoeItem(EnumUtils.CAItemTier.TOOL_EMERALD, CACommonConfig.COMMON.emeraldHoeDamage.get() - 7, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SILK_TOUCH, 1)}));

	// Ruby
	public static final RegistryObject<SwordItem> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new EnchantedSwordItem(EnumUtils.CAItemTier.TOOL_RUBY, CACommonConfig.COMMON.rubySwordDamage.get() - 17, -2.4F, new Item.Properties().fireResistant().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.FIRE_ASPECT, 1)}));
	public static final RegistryObject<ShovelItem> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_RUBY, CACommonConfig.COMMON.rubyShovelDamage.get() - 17, -3, new Item.Properties().fireResistant().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_RUBY, CACommonConfig.COMMON.rubyPickaxeDamage.get() - 17, -2.8F, new Item.Properties().fireResistant().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_RUBY, CACommonConfig.COMMON.rubyAxeDamage.get() - 17, -3, new Item.Properties().fireResistant().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_RUBY, CACommonConfig.COMMON.rubyHoeDamage.get() - 17, 0.0F, new Item.Properties().fireResistant().tab(CAItemGroups.EQUIPMENT)));

	// Amethyst
	public static final RegistryObject<SwordItem> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_AMETHYST, CACommonConfig.COMMON.amethystSwordDamage.get() - 12, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_AMETHYST, CACommonConfig.COMMON.amethystShovelDamage.get() - 12, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_AMETHYST, CACommonConfig.COMMON.amethystPickaxeDamage.get() - 12, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> AMETHYST_AXE = ITEMS.register("amethyst_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_AMETHYST, CACommonConfig.COMMON.amethystAxeDamage.get() - 12, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> AMETHYST_HOE = ITEMS.register("amethyst_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_AMETHYST, CACommonConfig.COMMON.amethystHoeDamage.get() - 12, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Tiger's Eye
	public static final RegistryObject<SwordItem> TIGERS_EYE_SWORD = ITEMS.register("tigers_eye_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_TIGERS_EYE, CACommonConfig.COMMON.tigersEyeSwordDamage.get() - 9, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> TIGERS_EYE_SHOVEL = ITEMS.register("tigers_eye_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_TIGERS_EYE, CACommonConfig.COMMON.tigersEyeShovelDamage.get() - 9, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> TIGERS_EYE_PICKAXE = ITEMS.register("tigers_eye_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_TIGERS_EYE, CACommonConfig.COMMON.tigersEyePickaxeDamage.get() - 9, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> TIGERS_EYE_AXE = ITEMS.register("tigers_eye_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_TIGERS_EYE, CACommonConfig.COMMON.tigersEyeAxeDamage.get() - 9, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> TIGERS_EYE_HOE = ITEMS.register("tigers_eye_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_TIGERS_EYE, CACommonConfig.COMMON.tigersEyeHoeDamage.get() - 9, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Crystal Wood
	public static final RegistryObject<SwordItem> CRYSTAL_WOOD_SWORD = ITEMS.register("crystal_wood_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_CRYSTAL_WOOD, CACommonConfig.COMMON.crystalWoodSwordDamage.get() - 2, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> CRYSTAL_WOOD_SHOVEL = ITEMS.register("crystal_wood_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_CRYSTAL_WOOD, CACommonConfig.COMMON.crystalWoodShovelDamage.get() - 2, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> CRYSTAL_WOOD_PICKAXE = ITEMS.register("crystal_wood_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_CRYSTAL_WOOD, CACommonConfig.COMMON.crystalWoodPickaxeDamage.get() - 2, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> CRYSTAL_WOOD_AXE = ITEMS.register("crystal_wood_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_CRYSTAL_WOOD, CACommonConfig.COMMON.crystalWoodAxeDamage.get() - 2, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> CRYSTAL_WOOD_HOE = ITEMS.register("crystal_wood_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_CRYSTAL_WOOD, CACommonConfig.COMMON.crystalWoodHoeDamage.get() - 2, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Kyanite
	public static final RegistryObject<SwordItem> KYANITE_SWORD = ITEMS.register("kyanite_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_KYANITE, CACommonConfig.COMMON.kyaniteSwordDamage.get() - 3, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> KYANITE_SHOVEL = ITEMS.register("kyanite_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_KYANITE, CACommonConfig.COMMON.kyaniteShovelDamage.get() - 3, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> KYANITE_PICKAXE = ITEMS.register("kyanite_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_KYANITE, CACommonConfig.COMMON.kyanitePickaxeDamage.get() - 3, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> KYANITE_AXE = ITEMS.register("kyanite_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_KYANITE, CACommonConfig.COMMON.kyaniteAxeDamage.get() - 3, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> KYANITE_HOE = ITEMS.register("kyanite_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_KYANITE, CACommonConfig.COMMON.kyaniteHoeDamage.get() - 3, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Cat's Eye
	public static final RegistryObject<SwordItem> CATS_EYE_SWORD = ITEMS.register("cats_eye_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_CATS_EYE, CACommonConfig.COMMON.catsEyeSwordDamage.get() - 9, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> CATS_EYE_SHOVEL = ITEMS.register("cats_eye_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_CATS_EYE, CACommonConfig.COMMON.catsEyeShovelDamage.get() - 9, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> CATS_EYE_PICKAXE = ITEMS.register("cats_eye_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_CATS_EYE, CACommonConfig.COMMON.catsEyePickaxeDamage.get() - 9, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> CATS_EYE_AXE = ITEMS.register("cats_eye_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_CATS_EYE, CACommonConfig.COMMON.catsEyeAxeDamage.get() - 9, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> CATS_EYE_HOE = ITEMS.register("cats_eye_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_CATS_EYE, CACommonConfig.COMMON.catsEyeHoeDamage.get() - 9, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Pink Tourmaline
	public static final RegistryObject<SwordItem> PINK_TOURMALINE_SWORD = ITEMS.register("pink_tourmaline_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_PINK_TOURMALINE, CACommonConfig.COMMON.pinkTourmSwordDamage.get() - 9, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> PINK_TOURMALINE_SHOVEL = ITEMS.register("pink_tourmaline_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_PINK_TOURMALINE, CACommonConfig.COMMON.pinkTourmShovelDamage.get() - 9, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> PINK_TOURMALINE_PICKAXE = ITEMS.register("pink_tourmaline_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_PINK_TOURMALINE, CACommonConfig.COMMON.pinkTourmPickaxeDamage.get() - 9, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> PINK_TOURMALINE_AXE = ITEMS.register("pink_tourmaline_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_PINK_TOURMALINE, CACommonConfig.COMMON.pinkTourmAxeDamage.get() - 9, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> PINK_TOURMALINE_HOE = ITEMS.register("pink_tourmaline_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_PINK_TOURMALINE, CACommonConfig.COMMON.pinkTourmHoeDamage.get() - 9, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Copper
	public static final RegistryObject<SwordItem> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_COPPER, CACommonConfig.COMMON.copperSwordDamage.get() - 3, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_COPPER, CACommonConfig.COMMON.copperShovelDamage.get() - 3, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_COPPER, CACommonConfig.COMMON.copperPickaxeDamage.get() - 3, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_COPPER, CACommonConfig.COMMON.copperAxeDamage.get() - 3, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_COPPER, CACommonConfig.COMMON.copperHoeDamage.get() - 3, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Tin
	public static final RegistryObject<SwordItem> TIN_SWORD = ITEMS.register("tin_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_TIN, CACommonConfig.COMMON.tinSwordDamage.get() - 4, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> TIN_SHOVEL = ITEMS.register("tin_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_TIN, CACommonConfig.COMMON.tinShovelDamage.get() - 4, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> TIN_PICKAXE = ITEMS.register("tin_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_TIN, CACommonConfig.COMMON.tinPickaxeDamage.get() - 4, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> TIN_AXE = ITEMS.register("tin_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_TIN, CACommonConfig.COMMON.tinAxeDamage.get() - 4, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> TIN_HOE = ITEMS.register("tin_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_TIN, CACommonConfig.COMMON.tinHoeDamage.get() - 4, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Silver
	public static final RegistryObject<SwordItem> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_SILVER, CACommonConfig.COMMON.silverSwordDamage.get() - 5, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_SILVER, CACommonConfig.COMMON.silverShovelDamage.get() - 5, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_SILVER, CACommonConfig.COMMON.silverPickaxeDamage.get() - 5, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_SILVER, CACommonConfig.COMMON.silverAxeDamage.get() - 5, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_SILVER, CACommonConfig.COMMON.silverHoeDamage.get() - 5, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Platinum
	public static final RegistryObject<SwordItem> PLATINUM_SWORD = ITEMS.register("platinum_sword", () -> new SwordItem(EnumUtils.CAItemTier.TOOL_PLATINUM, CACommonConfig.COMMON.platinumSwordDamage.get() - 7, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ShovelItem> PLATINUM_SHOVEL = ITEMS.register("platinum_shovel", () -> new ShovelItem(EnumUtils.CAItemTier.TOOL_PLATINUM, CACommonConfig.COMMON.platinumShovelDamage.get() - 7, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<PickaxeItem> PLATINUM_PICKAXE = ITEMS.register("platinum_pickaxe", () -> new PickaxeItem(EnumUtils.CAItemTier.TOOL_PLATINUM, CACommonConfig.COMMON.platinumPickaxeDamage.get() - 7, -2.8F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<AxeItem> PLATINUM_AXE = ITEMS.register("platinum_axe", () -> new AxeItem(EnumUtils.CAItemTier.TOOL_PLATINUM, CACommonConfig.COMMON.platinumAxeDamage.get() - 7, -3, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<HoeItem> PLATINUM_HOE = ITEMS.register("platinum_hoe", () -> new HoeItem(EnumUtils.CAItemTier.TOOL_PLATINUM, CACommonConfig.COMMON.platinumHoeDamage.get() - 7, 0.0F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Misc. Weapons
	public static final RegistryObject<EnchantedSwordItem> NIGHTMARE_SWORD = ITEMS.register("nightmare_sword", () -> new EnchantedSwordItem(EnumUtils.CAItemTier.TOOL_NIGHTMARE, CACommonConfig.COMMON.nightmareSwordDamage.get() - 27, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SHARPNESS, 1), new EnchantmentData(Enchantments.KNOCKBACK, 3), new EnchantmentData(Enchantments.FIRE_ASPECT, 1)}));
	public static final RegistryObject<BasiliskSwordItem> BASILISK_SWORD = ITEMS.register("basilisk_sword", () -> new BasiliskSwordItem(EnumUtils.CAItemTier.TOOL_BASILISK, CACommonConfig.COMMON.basiliskSwordDamage.get() - 27, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SHARPNESS, 2)}));
	public static final RegistryObject<EnchantedSwordItem> EXPERIENCE_SWORD = ITEMS.register("experience_sword", () -> new EnchantedSwordItem(EnumUtils.CAItemTier.TOOL_EMERALD, CACommonConfig.COMMON.experienceSwordDamage.get() - 7, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SHARPNESS, 2), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.MENDING, 1)}));
	public static final RegistryObject<EnchantedSwordItem> POISON_SWORD = ITEMS.register("poison_sword", () -> new PoisonSwordItem(EnumUtils.CAItemTier.TOOL_EMERALD, CACommonConfig.COMMON.poisonSwordDamage.get() - 7, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SHARPNESS, 1)}));
	public static final RegistryObject<SwordItem> RAT_SWORD = ITEMS.register("rat_sword", () -> new SwordItem(EnumUtils.CAItemTier.WEAPON_GENERIC, CACommonConfig.COMMON.ratSwordDamage.get() - 7, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<SwordItem> FAIRY_SWORD = ITEMS.register("fairy_sword", () -> new SwordItem(EnumUtils.CAItemTier.WEAPON_GENERIC, CACommonConfig.COMMON.fairySwordDamage.get() - 7, -2.4F, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<MantisClawItem> MANTIS_CLAW = ITEMS.register("mantis_claw", () -> new MantisClawItem(EnumUtils.CAItemTier.WEAPON_GENERIC, CACommonConfig.COMMON.mantisClawDamage.get() - 7, -3F, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<BigHammerItem> BIG_HAMMER = ITEMS.register("big_hammer", () -> new BigHammerItem(EnumUtils.CAItemTier.WEAPON_BIG_HAMMER, CACommonConfig.COMMON.bigHammerDamage.get() - 12, -3F, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<EnchantedScytheItem> PRISMATIC_REAPER = ITEMS.register("prismatic_reaper", () -> new EnchantedScytheItem(EnumUtils.CAItemTier.TOOL_ULTIMATE, CACommonConfig.COMMON.prismaticReaperDamage.get() - 37, -1.9F, 2.0D, 0, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			new EnchantmentData[]{new EnchantmentData(Enchantments.UNBREAKING, 4), new EnchantmentData(Enchantments.SWEEPING_EDGE, 4)}));
	public static final RegistryObject<SkateBowItem> SKATE_STRING_BOW = ITEMS.register("skate_string_bow", () -> new SkateBowItem(new Item.Properties().tab(CAItemGroups.EQUIPMENT).stacksTo(1).durability(384)));
	public static final RegistryObject<RayGunItem> RAY_GUN = ITEMS.register("ray_gun", () -> new RayGunItem(EnumUtils.CAItemTier.WEAPON_RAY_GUN, new Item.Properties().tab(CAItemGroups.EQUIPMENT).stacksTo(1).durability(50)));

	// Big Weapons
	public static final RegistryObject<AttitudeAdjusterItem> ATTITUDE_ADJUSTER = ITEMS.register("attitude_adjuster", () -> new AttitudeAdjusterItem(EnumUtils.CAItemTier.WEAPON_ATTITUDE_ADJUSTER, CACommonConfig.COMMON.attitudeAdjusterDamage.get() - 12, -3F, 3.5D, 1, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT).setISTER(() -> AttitudeAdjusterItemRenderer::new)));
	public static final RegistryObject<BigBerthaItem> BIG_BERTHA = ITEMS.register("big_bertha", () -> new BigBerthaItem(EnumUtils.CAItemTier.WEAPON_BERTHA, CACommonConfig.COMMON.berthaDamage.get() - 497, -3.2F, 5.0D, 0, new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.EQUIPMENT).setISTER(() -> BigBerthaItemRenderer::new),
			new EnchantmentData[]{new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, 3), new EnchantmentData(Enchantments.FIRE_ASPECT, 2), new EnchantmentData(Enchantments.KNOCKBACK, 2)}));
	public static final RegistryObject<BattleAxeItem> BATTLE_AXE = ITEMS.register("battle_axe", () -> new BattleAxeItem(EnumUtils.CAItemTier.WEAPON_BATTLEAXE, CACommonConfig.COMMON.battleAxeDamage.get() - 48, -3.35F, 3.0D, 0, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT).setISTER(() -> BattleAxeItemRenderer::new),
			new EnchantmentData[]{new EnchantmentData(Enchantments.MOB_LOOTING, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<QueenScaleBattleAxeItem> QUEEN_SCALE_BATTLE_AXE = ITEMS.register("queen_scale_battle_axe", () -> new QueenScaleBattleAxeItem(EnumUtils.CAItemTier.WEAPON_QUEEN_BATTLEAXE, CACommonConfig.COMMON.queenBattleAxeDamage.get() - 664, -3.25F, 4.0D, 0, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT).setISTER(() -> QueenScaleBattleAxeItemRenderer::new),
			new EnchantmentData[]{new EnchantmentData(Enchantments.SHARPNESS, 5), new EnchantmentData(Enchantments.SMITE, 5), new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, 5), new EnchantmentData(Enchantments.KNOCKBACK, 3), new EnchantmentData(Enchantments.MOB_LOOTING, 3), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.FIRE_ASPECT, 2)}));
	public static final RegistryObject<RoyalGuardianSwordItem> ROYAL_GUARDIAN_SWORD = ITEMS.register("royal_guardian_sword", () -> new RoyalGuardianSwordItem(EnumUtils.CAItemTier.WEAPON_ROYAL_GUARDIAN_SWORD, CACommonConfig.COMMON.royalGuardianSwordDamage.get() - 749, -3.25F, 5.5D, 0, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT).setISTER(() -> RoyalGuardianSwordItemRenderer::new),
			new EnchantmentData[]{new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<SlayerChainsawItem> SLAYER_CHAINSAW = ITEMS.register("slayer_chainsaw", () -> new SlayerChainsawItem(EnumUtils.CAItemTier.WEAPON_SLAYER_CHAINSAW, CACommonConfig.COMMON.slayerChainsawDamage.get() - 1, -3F, 2.5D, 0, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT).setISTER(() -> SlayerChainsawItemRenderer::new)));

	// Staffs
	public static final RegistryObject<ThunderStaffItem> THUNDER_STAFF = ITEMS.register("thunder_staff", () -> new ThunderStaffItem(new Item.Properties().tab(CAItemGroups.EQUIPMENT).durability(50)));

	// ARMOR
	// Ultimate
	public static final RegistryObject<EnchantedArmorItem> ULTIMATE_HELMET = ITEMS.register("ultimate_helmet", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.ULTIMATE, EquipmentSlotType.HEAD, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.RESPIRATION, 3), new EnchantmentData(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<EnchantedArmorItem> ULTIMATE_CHESTPLATE = ITEMS.register("ultimate_chestplate", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.ULTIMATE, EquipmentSlotType.CHEST, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<EnchantedArmorItem> ULTIMATE_LEGGINGS = ITEMS.register("ultimate_leggings", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.ULTIMATE, EquipmentSlotType.LEGS, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.FIRE_PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<EnchantedArmorItem> ULTIMATE_BOOTS = ITEMS.register("ultimate_boots", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.ULTIMATE, EquipmentSlotType.FEET, new Item.Properties().rarity(Rarity.RARE).tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.BLAST_PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.FALL_PROTECTION, 4)}));

	// Lava Eel
	public static final RegistryObject<EnchantedArmorItem> LAVA_EEL_HELMET = ITEMS.register("lava_eel_helmet", () -> new LavaEelArmorItem(EnumUtils.CAArmorMaterial.LAVA_EEL, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT).fireResistant(),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.FIRE_PROTECTION, 5), new EnchantmentData(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<EnchantedArmorItem> LAVA_EEL_CHESTPLATE = ITEMS.register("lava_eel_chestplate", () -> new LavaEelArmorItem(EnumUtils.CAArmorMaterial.LAVA_EEL, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT).fireResistant(),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.FIRE_PROTECTION, 5)}));
	public static final RegistryObject<EnchantedArmorItem> LAVA_EEL_LEGGINGS = ITEMS.register("lava_eel_leggings", () -> new LavaEelArmorItem(EnumUtils.CAArmorMaterial.LAVA_EEL, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT).fireResistant(),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.FIRE_PROTECTION, 5)}));
	public static final RegistryObject<EnchantedArmorItem> LAVA_EEL_BOOTS = ITEMS.register("lava_eel_boots", () -> new LavaEelArmorItem(EnumUtils.CAArmorMaterial.LAVA_EEL, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT).fireResistant(),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.FIRE_PROTECTION, 5)}));

	// Moth Scale
	public static final RegistryObject<EnchantedArmorItem> MOTH_SCALE_HELMET = ITEMS.register("moth_scale_helmet", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.MOTH_SCALE, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 3), new EnchantmentData(Enchantments.FIRE_PROTECTION, 3), new EnchantmentData(Enchantments.BLAST_PROTECTION, 3)}));
	public static final RegistryObject<EnchantedArmorItem> MOTH_SCALE_CHESTPLATE = ITEMS.register("moth_scale_chestplate", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.MOTH_SCALE, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 3), new EnchantmentData(Enchantments.FIRE_PROTECTION, 3), new EnchantmentData(Enchantments.BLAST_PROTECTION, 3)}));
	public static final RegistryObject<EnchantedArmorItem> MOTH_SCALE_LEGGINGS = ITEMS.register("moth_scale_leggings", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.MOTH_SCALE, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 3), new EnchantmentData(Enchantments.FIRE_PROTECTION, 3), new EnchantmentData(Enchantments.BLAST_PROTECTION, 3)}));
	public static final RegistryObject<EnchantedArmorItem> MOTH_SCALE_BOOTS = ITEMS.register("moth_scale_boots", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.MOTH_SCALE, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 3), new EnchantmentData(Enchantments.FIRE_PROTECTION, 3), new EnchantmentData(Enchantments.BLAST_PROTECTION, 3), new EnchantmentData(Enchantments.FALL_PROTECTION, 3)}));
	
	//Ender Dragon Scale
	public static final RegistryObject<EnderScaleArmorItem> ENDER_DRAGON_SCALE_HELMET = ITEMS.register("ender_dragon_scale_helmet", () -> new EnderScaleArmorItem(EnumUtils.CAArmorMaterial.ENDER_DRAGON_SCALE, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[] {new EnchantmentData(Enchantments.BLAST_PROTECTION, 4), new EnchantmentData(Enchantments.FIRE_PROTECTION, 4), new EnchantmentData(Enchantments.THORNS, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<EnderScaleArmorItem> ENDER_DRAGON_SCALE_CHESTPLATE = ITEMS.register("ender_dragon_scale_chestplate", () -> new EnderScaleArmorItem(EnumUtils.CAArmorMaterial.ENDER_DRAGON_SCALE, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[] {new EnchantmentData(Enchantments.BLAST_PROTECTION, 4), new EnchantmentData(Enchantments.FIRE_PROTECTION, 4), new EnchantmentData(Enchantments.THORNS, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<EnderScaleArmorItem> ENDER_DRAGON_SCALE_LEGGINGS = ITEMS.register("ender_dragon_scale_leggings", () -> new EnderScaleArmorItem(EnumUtils.CAArmorMaterial.ENDER_DRAGON_SCALE, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[] {new EnchantmentData(Enchantments.BLAST_PROTECTION, 4), new EnchantmentData(Enchantments.FIRE_PROTECTION, 4), new EnchantmentData(Enchantments.THORNS, 3), new EnchantmentData(Enchantments.UNBREAKING, 3)}));
	public static final RegistryObject<EnderScaleArmorItem> ENDER_DRAGON_SCALE_BOOTS = ITEMS.register("ender_dragon_scale_boots", () -> new EnderScaleArmorItem(EnumUtils.CAArmorMaterial.ENDER_DRAGON_SCALE, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[] {new EnchantmentData(Enchantments.BLAST_PROTECTION, 4), new EnchantmentData(Enchantments.FIRE_PROTECTION, 4), new EnchantmentData(Enchantments.THORNS, 3), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.FALL_PROTECTION, 4)}));
	
	// Emerald
	public static final RegistryObject<ArmorItem> EMERALD_HELMET = ITEMS.register("emerald_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.EMERALD, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.EMERALD, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.EMERALD, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> EMERALD_BOOTS = ITEMS.register("emerald_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.EMERALD, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Experience
	public static final RegistryObject<EnchantedArmorItem> EXPERIENCE_HELMET = ITEMS.register("experience_helmet",
			() -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.EXPERIENCE, EquipmentSlotType.HEAD, new Item.Properties()
					.tab(CAItemGroups.EQUIPMENT),
					() -> new EnchantmentData[]{new EnchantmentData(CAEnchantments.HOPLOLOGY.get(), 1)}));
	public static final RegistryObject<EnchantedArmorItem> EXPERIENCE_CHESTPLATE = ITEMS.register("experience_chestplate",
			() -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.EXPERIENCE, EquipmentSlotType.CHEST, new Item.Properties()
					.tab(CAItemGroups.EQUIPMENT),
					() -> new EnchantmentData[]{new EnchantmentData(CAEnchantments.HOPLOLOGY.get(), 1)}));
	public static final RegistryObject<EnchantedArmorItem> EXPERIENCE_LEGGINGS = ITEMS.register("experience_leggings",
			() -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.EXPERIENCE, EquipmentSlotType.LEGS, new Item.Properties()
					.tab(CAItemGroups.EQUIPMENT),
					() -> new EnchantmentData[]{new EnchantmentData(CAEnchantments.HOPLOLOGY.get(), 1)}));
	public static final RegistryObject<EnchantedArmorItem> EXPERIENCE_BOOTS = ITEMS.register("experience_boots",
			() -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.EXPERIENCE, EquipmentSlotType.FEET, new Item.Properties()
					.tab(CAItemGroups.EQUIPMENT),
					() -> new EnchantmentData[]{new EnchantmentData(CAEnchantments.HOPLOLOGY.get(), 1)}));

	// Ruby
	public static final RegistryObject<EnchantedArmorItem> RUBY_HELMET = ITEMS.register("ruby_helmet",
			() -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.RUBY, EquipmentSlotType.HEAD, new Item.Properties()
					.tab(CAItemGroups.EQUIPMENT),
					() -> new EnchantmentData[] {new EnchantmentData(CAEnchantments.IGNITE.get(), 1)}));
	public static final RegistryObject<EnchantedArmorItem> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate",
			() -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.RUBY, EquipmentSlotType.CHEST, new Item.Properties()
					.tab(CAItemGroups.EQUIPMENT),
					() -> new EnchantmentData[] {new EnchantmentData(CAEnchantments.IGNITE.get(), 1)}));
	public static final RegistryObject<EnchantedArmorItem> RUBY_LEGGINGS = ITEMS.register("ruby_leggings",
			() -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.RUBY, EquipmentSlotType.LEGS, new Item.Properties()
					.tab(CAItemGroups.EQUIPMENT),
					() -> new EnchantmentData[] {new EnchantmentData(CAEnchantments.IGNITE.get(), 1)}));
	public static final RegistryObject<EnchantedArmorItem> RUBY_BOOTS = ITEMS.register("ruby_boots",
			() -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.RUBY, EquipmentSlotType.FEET, new Item.Properties()
					.tab(CAItemGroups.EQUIPMENT),
					() -> new EnchantmentData[] {new EnchantmentData(CAEnchantments.IGNITE.get(), 1)}));

	// Amethyst
	public static final RegistryObject<ArmorItem> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> AMETHYST_LEGGINGS = ITEMS.register("amethyst_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> AMETHYST_BOOTS = ITEMS.register("amethyst_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Tiger's Eye
	public static final RegistryObject<ArmorItem> TIGERS_EYE_HELMET = ITEMS.register("tigers_eye_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.TIGERS_EYE, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> TIGERS_EYE_CHESTPLATE = ITEMS.register("tigers_eye_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.TIGERS_EYE, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> TIGERS_EYE_LEGGINGS = ITEMS.register("tigers_eye_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.TIGERS_EYE, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> TIGERS_EYE_BOOTS = ITEMS.register("tigers_eye_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.TIGERS_EYE, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Lapis Lazuli
	public static final RegistryObject<ArmorItem> LAPIS_HELMET = ITEMS.register("lapis_helmet", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.LAPIS, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 2), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 1), new EnchantmentData(Enchantments.RESPIRATION, 1), new EnchantmentData(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<ArmorItem> LAPIS_CHESTPLATE = ITEMS.register("lapis_chestplate", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.LAPIS, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 2), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 1)}));
	public static final RegistryObject<ArmorItem> LAPIS_LEGGINGS = ITEMS.register("lapis_leggings", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.LAPIS, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 2), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 1)}));
	public static final RegistryObject<ArmorItem> LAPIS_BOOTS = ITEMS.register("lapis_boots", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.LAPIS, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 2), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 1)}));

	// Copper
	public static final RegistryObject<ArmorItem> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.COPPER, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.COPPER, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.COPPER, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.COPPER, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Tin
	public static final RegistryObject<ArmorItem> TIN_HELMET = ITEMS.register("tin_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.TIN, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> TIN_CHESTPLATE = ITEMS.register("tin_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.TIN, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> TIN_LEGGINGS = ITEMS.register("tin_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.TIN, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> TIN_BOOTS = ITEMS.register("tin_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.TIN, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Silver
	public static final RegistryObject<ArmorItem> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.SILVER, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.SILVER, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> SILVER_LEGGINGS = ITEMS.register("silver_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.SILVER, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.SILVER, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Platinum
	public static final RegistryObject<ArmorItem> PLATINUM_HELMET = ITEMS.register("platinum_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PLATINUM, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> PLATINUM_CHESTPLATE = ITEMS.register("platinum_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PLATINUM, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> PLATINUM_LEGGINGS = ITEMS.register("platinum_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PLATINUM, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> PLATINUM_BOOTS = ITEMS.register("platinum_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PLATINUM, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Peacock Feather
	public static final RegistryObject<ArmorItem> PEACOCK_FEATHER_HELMET = ITEMS.register("peacock_feather_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PEACOCK_FEATHER, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> PEACOCK_FEATHER_CHESTPLATE = ITEMS.register("peacock_feather_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PEACOCK_FEATHER, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> PEACOCK_FEATHER_LEGGINGS = ITEMS.register("peacock_feather_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PEACOCK_FEATHER, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<SlowFallBoots> PEACOCK_FEATHER_BOOTS = ITEMS.register("peacock_feather_boots", () -> new SlowFallBoots(EnumUtils.CAArmorMaterial.PEACOCK_FEATHER, new Item.Properties().tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.FALL_PROTECTION, 4)}));

	// Pink Tourmaline
	public static final RegistryObject<ArmorItem> PINK_TOURMALINE_HELMET = ITEMS.register("pink_tourmaline_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PINK_TOURMALINE, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> PINK_TOURMALINE_CHESTPLATE = ITEMS.register("pink_tourmaline_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PINK_TOURMALINE, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> PINK_TOURMALINE_LEGGINGS = ITEMS.register("pink_tourmaline_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PINK_TOURMALINE, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> PINK_TOURMALINE_BOOTS = ITEMS.register("pink_tourmaline_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.PINK_TOURMALINE, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Cat's Eye
	public static final RegistryObject<ArmorItem> CATS_EYE_HELMET = ITEMS.register("cats_eye_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.CATS_EYE, EquipmentSlotType.HEAD, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> CATS_EYE_CHESTPLATE = ITEMS.register("cats_eye_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.CATS_EYE, EquipmentSlotType.CHEST, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> CATS_EYE_LEGGINGS = ITEMS.register("cats_eye_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.CATS_EYE, EquipmentSlotType.LEGS, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> CATS_EYE_BOOTS = ITEMS.register("cats_eye_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.CATS_EYE, EquipmentSlotType.FEET, new Item.Properties().tab(CAItemGroups.EQUIPMENT)));

	// Royal Guardian
	public static final RegistryObject<EnchantedArmorItem> ROYAL_GUARDIAN_HELMET = ITEMS.register("royal_guardian_helmet", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.ROYAL_GUARDIAN, EquipmentSlotType.HEAD, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT).setNoRepair(),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 10), new EnchantmentData(Enchantments.FIRE_PROTECTION, 10), new EnchantmentData(Enchantments.BLAST_PROTECTION, 10), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentData(Enchantments.UNBREAKING, 5), new EnchantmentData(Enchantments.RESPIRATION, 3), new EnchantmentData(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<EnchantedArmorItem> ROYAL_GUARDIAN_CHESTPLATE = ITEMS.register("royal_guardian_chestplate", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.ROYAL_GUARDIAN, EquipmentSlotType.CHEST, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT).setNoRepair(),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 10), new EnchantmentData(Enchantments.FIRE_PROTECTION, 10), new EnchantmentData(Enchantments.BLAST_PROTECTION, 10), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentData(Enchantments.UNBREAKING, 5)}));
	public static final RegistryObject<EnchantedArmorItem> ROYAL_GUARDIAN_LEGGINGS = ITEMS.register("royal_guardian_leggings", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.ROYAL_GUARDIAN, EquipmentSlotType.LEGS, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT).setNoRepair(),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 10), new EnchantmentData(Enchantments.FIRE_PROTECTION, 10), new EnchantmentData(Enchantments.BLAST_PROTECTION, 10), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentData(Enchantments.UNBREAKING, 5)}));
	public static final RegistryObject<SlowFallBoots> ROYAL_GUARDIAN_BOOTS = ITEMS.register("royal_guardian_boots", () -> new SlowFallBoots(EnumUtils.CAArmorMaterial.ROYAL_GUARDIAN, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT).setNoRepair(),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 10), new EnchantmentData(Enchantments.FIRE_PROTECTION, 10), new EnchantmentData(Enchantments.BLAST_PROTECTION, 10), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentData(Enchantments.UNBREAKING, 5), new EnchantmentData(Enchantments.FALL_PROTECTION, 4)}));

	// Queen Scale
	public static final RegistryObject<EnchantedArmorItem> QUEEN_SCALE_HELMET = ITEMS.register("queen_scale_helmet", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.QUEEN_SCALE, EquipmentSlotType.HEAD, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 10), new EnchantmentData(Enchantments.FIRE_PROTECTION, 10), new EnchantmentData(Enchantments.BLAST_PROTECTION, 10), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentData(Enchantments.UNBREAKING, 5), new EnchantmentData(Enchantments.RESPIRATION, 3), new EnchantmentData(Enchantments.AQUA_AFFINITY, 1)}));
	public static final RegistryObject<EnchantedArmorItem> QUEEN_SCALE_CHESTPLATE = ITEMS.register("queen_scale_chestplate", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.QUEEN_SCALE, EquipmentSlotType.CHEST, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 10), new EnchantmentData(Enchantments.FIRE_PROTECTION, 10), new EnchantmentData(Enchantments.BLAST_PROTECTION, 10), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentData(Enchantments.UNBREAKING, 5)}));
	public static final RegistryObject<EnchantedArmorItem> QUEEN_SCALE_LEGGINGS = ITEMS.register("queen_scale_leggings", () -> new EnchantedArmorItem(EnumUtils.CAArmorMaterial.QUEEN_SCALE, EquipmentSlotType.LEGS, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 10), new EnchantmentData(Enchantments.FIRE_PROTECTION, 10), new EnchantmentData(Enchantments.BLAST_PROTECTION, 10), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentData(Enchantments.UNBREAKING, 5)}));
	public static final RegistryObject<SlowFallBoots> QUEEN_SCALE_BOOTS = ITEMS.register("queen_scale_boots", () -> new SlowFallBoots(EnumUtils.CAArmorMaterial.QUEEN_SCALE, new Item.Properties().rarity(RARITY_ROYALTY).tab(CAItemGroups.EQUIPMENT),
			() -> new EnchantmentData[]{new EnchantmentData(Enchantments.ALL_DAMAGE_PROTECTION, 10), new EnchantmentData(Enchantments.FIRE_PROTECTION, 10), new EnchantmentData(Enchantments.BLAST_PROTECTION, 10), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 10), new EnchantmentData(Enchantments.UNBREAKING, 5), new EnchantmentData(Enchantments.FALL_PROTECTION, 4)}));

	// Mobzilla Scale
	public static final RegistryObject<ArmorItem> MOBZILLA_SCALE_HELMET = ITEMS.register("mobzilla_scale_helmet", () -> new ArmorItem(EnumUtils.CAArmorMaterial.MOBZILLA_SCALE, EquipmentSlotType.HEAD, new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> MOBZILLA_SCALE_CHESTPLATE = ITEMS.register("mobzilla_scale_chestplate", () -> new ArmorItem(EnumUtils.CAArmorMaterial.MOBZILLA_SCALE, EquipmentSlotType.CHEST, new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> MOBZILLA_SCALE_LEGGINGS = ITEMS.register("mobzilla_scale_leggings", () -> new ArmorItem(EnumUtils.CAArmorMaterial.MOBZILLA_SCALE, EquipmentSlotType.LEGS, new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.EQUIPMENT)));
	public static final RegistryObject<ArmorItem> MOBZILLA_SCALE_BOOTS = ITEMS.register("mobzilla_scale_boots", () -> new ArmorItem(EnumUtils.CAArmorMaterial.MOBZILLA_SCALE, EquipmentSlotType.FEET, new Item.Properties().rarity(Rarity.EPIC).tab(CAItemGroups.EQUIPMENT)));

	// Miners Dream
	public static final RegistryObject<Item> MINERS_DREAM = ITEMS.register("miners_dream", () -> new MinersDreamItem(new Item.Properties().tab(CAItemGroups.ITEMS).stacksTo(16)));

	// SPAWN EGGS
	public static final RegistryObject<CASpawnEggItem> ACACIA_ENT_SPAWN_EGG = ITEMS.register("acacia_ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ACACIA_ENT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> BIRCH_ENT_SPAWN_EGG = ITEMS.register("birch_ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.BIRCH_ENT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> CRIMSON_ENT_SPAWN_EGG = ITEMS.register("crimson_ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.CRIMSON_ENT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> DARK_OAK_ENT_SPAWN_EGG = ITEMS.register("dark_oak_ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.DARK_OAK_ENT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> JUNGLE_ENT_SPAWN_EGG = ITEMS.register("jungle_ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.JUNGLE_ENT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> OAK_ENT_SPAWN_EGG = ITEMS.register("oak_ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.OAK_ENT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> SPRUCE_ENT_SPAWN_EGG = ITEMS.register("spruce_ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.SPRUCE_ENT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> WARPED_ENT_SPAWN_EGG = ITEMS.register("warped_ent_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.WARPED_ENT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> RED_ANT_SPAWN_EGG = ITEMS.register("red_ant_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.RED_ANT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> BROWN_ANT_SPAWN_EGG = ITEMS.register("brown_ant_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.BROWN_ANT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> RAINBOW_ANT_SPAWN_EGG = ITEMS.register("rainbow_ant_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.RAINBOW_ANT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> UNSTABLE_ANT_SPAWN_EGG = ITEMS.register("unstable_ant_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.UNSTABLE_ANT, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> TERMITE_SPAWN_EGG = ITEMS.register("termite_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.TERMITE, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> TREE_FROG_SPAWN_EGG = ITEMS.register("tree_frog_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.TREE_FROG, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> LEAFY_CHICKEN_SPAWN_EGG = ITEMS.register("leafy_chicken_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.LEAFY_CHICKEN, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> HERCULES_BEETLE_SPAWN_EGG = ITEMS.register("hercules_beetle_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.HERCULES_BEETLE, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> DIMETRODON_SPAWN_EGG = ITEMS.register("dimetrodon_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.DIMETRODON, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> EMERALD_GATOR_SPAWN_EGG = ITEMS.register("emerald_gator_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.EMERALD_GATOR, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> CRYSTAL_GATOR_SPAWN_EGG = ITEMS.register("crystal_gator_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.CRYSTAL_GATOR, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> RUBY_BUG_SPAWN_EGG = ITEMS.register("ruby_bug_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.RUBY_BUG, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> STINK_BUG_SPAWN_EGG = ITEMS.register("stink_bug_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.STINK_BUG, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> BIRD_SPAWN_EGG = ITEMS.register("bird_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.BIRD, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> ROBO_SNIPER_SPAWN_EGG = ITEMS.register("robo_sniper_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ROBO_SNIPER, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> ROBO_WARRIOR_SPAWN_EGG = ITEMS.register("robo_warrior_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ROBO_WARRIOR, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> ROBO_POUNDER_SPAWN_EGG = ITEMS.register("robo_pounder_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ROBO_POUNDER, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> BEAVER_SPAWN_EGG = ITEMS.register("beaver_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.BEAVER, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> GAZELLE_SPAWN_EGG = ITEMS.register("gazelle_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.GAZELLE, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> APPLE_COW_SPAWN_EGG = ITEMS.register("apple_cow_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.APPLE_COW, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> GOLDEN_APPLE_COW_SPAWN_EGG = ITEMS.register("golden_apple_cow_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.GOLDEN_APPLE_COW, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> ENCHANTED_GOLDEN_APPLE_COW_SPAWN_EGG = ITEMS.register("enchanted_golden_apple_cow_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ENCHANTED_GOLDEN_APPLE_COW, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS), true));
	public static final RegistryObject<CASpawnEggItem> CRYSTAL_APPLE_COW_SPAWN_EGG = ITEMS.register("crystal_apple_cow_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.CRYSTAL_APPLE_COW, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> CARROT_PIG_SPAWN_EGG = ITEMS.register("carrot_pig_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.CARROT_PIG, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> GOLDEN_CARROT_PIG_SPAWN_EGG = ITEMS.register("golden_carrot_pig_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.GOLDEN_CARROT_PIG, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> ENCHANTED_GOLDEN_CARROT_PIG_SPAWN_EGG = ITEMS.register("enchanted_golden_carrot_pig_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ENCHANTED_GOLDEN_CARROT_PIG, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS), true));
	public static final RegistryObject<CASpawnEggItem> CRYSTAL_CARROT_PIG_SPAWN_EGG = ITEMS.register("crystal_carrot_pig_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.CRYSTAL_CARROT_PIG, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> WASP_SPAWN_EGG = ITEMS.register("wasp_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.WASP, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<CASpawnEggItem> WHALE_SPAWN_EGG = ITEMS.register("whale_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.WHALE, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));

	public static final RegistryObject<CASpawnEggItem> GREEN_FISH_SPAWN_EGG = ITEMS.register("green_fish_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.GREEN_FISH, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<FishBucketItem> GREEN_FISH_BUCKET = ITEMS.register("green_fish_bucket", () -> new FishBucketItem(CAEntityTypes.GREEN_FISH, () -> Fluids.WATER, (new Item.Properties().stacksTo(1).tab(CAItemGroups.SPAWN_EGGS))));
	public static final RegistryObject<CASpawnEggItem> ROCK_FISH_SPAWN_EGG = ITEMS.register("rock_fish_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.ROCK_FISH, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<FishBucketItem> ROCK_FISH_BUCKET = ITEMS.register("rock_fish_bucket", () -> new FishBucketItem(CAEntityTypes.ROCK_FISH, () -> Fluids.WATER, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS).stacksTo(1)));
	public static final RegistryObject<CASpawnEggItem> SPARK_FISH_SPAWN_EGG = ITEMS.register("spark_fish_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.SPARK_FISH, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<FishBucketItem> SPARK_FISH_BUCKET = ITEMS.register("spark_fish_bucket", () -> new FishBucketItem(CAEntityTypes.SPARK_FISH, () -> Fluids.WATER, (new Item.Properties().stacksTo(1).tab(CAItemGroups.SPAWN_EGGS))));
	public static final RegistryObject<CASpawnEggItem> WOOD_FISH_SPAWN_EGG = ITEMS.register("wood_fish_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.WOOD_FISH, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<FishBucketItem> WOOD_FISH_BUCKET = ITEMS.register("wood_fish_bucket", () -> new FishBucketItem(CAEntityTypes.WOOD_FISH, () -> Fluids.WATER, (new Item.Properties().stacksTo(1).tab(CAItemGroups.SPAWN_EGGS))));
	public static final RegistryObject<CASpawnEggItem> LAVA_EEL_SPAWN_EGG = ITEMS.register("lava_eel_spawn_egg", () -> new CASpawnEggItem(CAEntityTypes.LAVA_EEL, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<FishBucketItem> LAVA_EEL_BUCKET = ITEMS.register("lava_eel_bucket", () -> new FishBucketItem(CAEntityTypes.LAVA_EEL, () -> Fluids.LAVA, (new Item.Properties().stacksTo(1).tab(CAItemGroups.SPAWN_EGGS))));

	public static final RegistryObject<SpawnEggItem> IRON_GOLEM_SPAWN_EGG = ITEMS.register("iron_golem_spawn_egg", () -> new SpawnEggItem(EntityType.IRON_GOLEM, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<SpawnEggItem> SNOW_GOLEM_SPAWN_EGG = ITEMS.register("snow_golem_spawn_egg", () -> new SpawnEggItem(EntityType.SNOW_GOLEM, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<SpawnEggItem> ILLUSIONER_SPAWN_EGG = ITEMS.register("illusioner_spawn_egg", () -> new SpawnEggItem(EntityType.ILLUSIONER, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<SpawnEggItem> GIANT_SPAWN_EGG = ITEMS.register("giant_spawn_egg", () -> new SpawnEggItem(EntityType.GIANT, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<SpawnEggItem> WITHER_SPAWN_EGG = ITEMS.register("wither_spawn_egg", () -> new SpawnEggItem(EntityType.WITHER, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));
	public static final RegistryObject<SpawnEggItem> ENDER_DRAGON_SPAWN_EGG = ITEMS.register("ender_dragon_spawn_egg", () -> new SpawnEggItem(EntityType.ENDER_DRAGON, 0xFFFFFF, 0xFFFFFF, new Item.Properties().tab(CAItemGroups.SPAWN_EGGS)));

	// STRUCTURE SPAWN
	public static final RegistryObject<Item> INSTANT_SURVIVAL_SHELTER = ITEMS.register("instant_survival_shelter", () -> new StructureItem(new Item.Properties().tab(CAItemGroups.ITEMS).stacksTo(16), "survival_shelter"));
	public static final RegistryObject<Item> ZOO_CAGE_EXTRA_SMALL = ITEMS.register("zoo_cage_extra_small", () -> new StructureItem(new Item.Properties().tab(CAItemGroups.ITEMS).stacksTo(16), "cage_xs"));
	public static final RegistryObject<Item> ZOO_CAGE_SMALL = ITEMS.register("zoo_cage_small", () -> new StructureItem(new Item.Properties().tab(CAItemGroups.ITEMS).stacksTo(16), "cage_s"));
	public static final RegistryObject<Item> ZOO_CAGE_MEDIUM = ITEMS.register("zoo_cage_medium", () -> new StructureItem(new Item.Properties().tab(CAItemGroups.ITEMS).stacksTo(16), "cage_m"));
	public static final RegistryObject<Item> ZOO_CAGE_LARGE = ITEMS.register("zoo_cage_large", () -> new StructureItem(new Item.Properties().tab(CAItemGroups.ITEMS).stacksTo(16), "cage_l"));
	public static final RegistryObject<Item> ZOO_CAGE_EXTRA_LARGE = ITEMS.register("zoo_cage_extra_large", () -> new StructureItem(new Item.Properties().tab(CAItemGroups.ITEMS).stacksTo(16), "cage_xl"));

	// DEV ITEMS FOR DATAPACK CREATORS
	public static final RegistryObject<DevItem> DEV_ITEM1 = ITEMS.register("dev_item1", () -> new DevItem(new Item.Properties().stacksTo(1).tab(ChaosAwakens.DEVELOPMENT)));
	public static final RegistryObject<DevItem> DEV_ITEM16 = ITEMS.register("dev_item16", () -> new DevItem(new Item.Properties().stacksTo(16).tab(ChaosAwakens.DEVELOPMENT)));
	public static final RegistryObject<DevItem> DEV_ITEM64 = ITEMS.register("dev_item64", () -> new DevItem(new Item.Properties().stacksTo(64).tab(ChaosAwakens.DEVELOPMENT)));
	public static final RegistryObject<DevItem> DEV_ITEM_DAMAGE = ITEMS.register("dev_item_damage", () -> new DevItem(new Item.Properties().durability(50).tab(ChaosAwakens.DEVELOPMENT)));
}
