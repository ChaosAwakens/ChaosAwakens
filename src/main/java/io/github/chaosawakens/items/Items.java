package io.github.chaosawakens.items;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.config.Config;
import io.github.chaosawakens.entities.Entities;
import io.github.chaosawakens.items.util.FoodComponents;
import io.github.chaosawakens.items.util.generic.GenericAxeItem;
import io.github.chaosawakens.items.util.generic.GenericHoeItem;
import io.github.chaosawakens.items.util.generic.GenericPickaxeItem;
import net.minecraft.item.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.chaosawakens.items.util.ToolMaterials.*;

public class Items implements ModInitializer {

    public static final Item RAW_CORNDOG;
    public static final Item COOKED_CORNDOG;
    public static final Item RAW_BACON;
    public static final Item COOKED_BACON;
    public static final Item CORN;
    public static final Item TOMATO;
    public static final Item LETTUCE;
    public static final Item CHEESE;
    public static final Item GARDEN_SALAD;
    public static final Item BLT;
    public static final Item STRAWBERRY;
    public static final Item RADISH;
    public static final Item RADISH_STEW;
    public static final Item CHERRIES;
    public static final Item SPARK_FISH;
    public static final Item LAVA_EEL;
    public static final Item CRAB_MEAT;
    public static final Item COOKED_CRAB_MEAT;
    public static final Item SEAFOOD_PATTY;
    public static final Item PEACH;
    public static final Item PEACOCK_LEG;
    public static final Item COOKED_PEACOCK_LEG;
    public static final Item BUTTER;
    public static final Item GOLDEN_BREAD;
    public static final Item GOLDEN_CHICKEN;
    public static final Item GOLDEN_TROPICAL_FISH;
    public static final Item GOLDEN_COD;
    public static final Item GOLDEN_PORKCHOP;
    public static final Item GOLDEN_MELON_SLICE;
    public static final Item GOLDEN_MUSHROOM_STEW;
    public static final Item GOLDEN_STEAK;
    public static final Item AMETHYST;
    public static final Item RUBY;
    public static final Item TIGERS_EYE;
    public static final Item TITANIUM_INGOT;
    public static final Item TITANIUM_NUGGET;
    public static final Item URANIUM_INGOT;
    public static final Item URANIUM_NUGGET;
    public static final Item ALUMINIUM_INGOT;
    public static final Item ALUMINIUM_NUGGET;
    public static final Item DRUID_KEY;
    public static final Item SALT;
    public static final ToolItem ULTIMATE_SWORD;
    //public static final ToolItem ULTIMATE_SHOVEL;
    //public static final ToolItem ULTIMATE_PICKAXE;
    //public static final ToolItem ULTIMATE_AXE;
    //public static final ToolItem ULTIMATE_HOE;
    //public static final Item ULTIMATE_HELMET;
    //public static final Item ULTIMATE_CHESTPLATE;
    //public static final Item ULTIMATE_LEGGINGS;
    //public static final Item ULTIMATE_BOOTS;
    public static final Item ENT_EGG;
    public static final Item RED_ANT_EGG;
    public static final Item BROWN_ANT_EGG;
    public static final Item RAINBOW_ANT_EGG;
    public static final Item UNSTABLE_ANT_EGG;
    public static final Item TERMITE_EGG;
    public static final Item HERCULES_BEETLE_EGG;
    public static final Item RUBY_BUG_EGG;
    public static final Item EMERALD_GATOR_EGG;
    public static final Item ROBO_SNIPER_EGG;
    public static final Item BEAVER_EGG;
    public static final Item APPLE_COW_EGG;
    public static final Item GOLDEN_APPLE_COW_EGG;
    public static final Item IRON_GOLEM_EGG;
    public static final Item SNOW_GOLEM_EGG;

    public static ArmorItem register(String identifier, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new ArmorItem(armorMaterial, equipmentSlot, new FabricItemSettings().group(ItemGroup.COMBAT)));
    }
    public static HoeItem registerHoe(String identifier, ToolMaterial toolMaterial, Config.Default config) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new GenericHoeItem(toolMaterial, (int) config.hoeDamage, (float) config.hoeAttackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)));
    }
    public static AxeItem registerAxe(String identifier, ToolMaterial toolMaterial, Config.Default config) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new GenericAxeItem(toolMaterial, (float) config.axeDamage, (float) config.axeAttackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)));
    }
    public static PickaxeItem registerPickaxe(String identifier, ToolMaterial toolMaterial, Config.Default config) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new GenericPickaxeItem(toolMaterial, (int) config.pickaxeDamage, (float) config.pickaxeAttackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)));
    }
    public static ShovelItem registerShovel(String identifier, ToolMaterial toolMaterial, Config.Default config) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new ShovelItem(toolMaterial, (float) config.shovelDamage, (float) config.shovelAttackSpeed, new FabricItemSettings().group(ItemGroup.TOOLS)));
    }
    public static SwordItem registerSword(String identifier, ToolMaterial toolMaterial, Config.Default config) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new SwordItem(toolMaterial, (int) config.swordDamage, (float) config.swordAttackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)));
    }
    public static SwordItem registerSword(String identifier, ToolMaterial toolMaterial, int damage, float attackSpeed) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new SwordItem(toolMaterial, damage, attackSpeed, new FabricItemSettings().group(ItemGroup.COMBAT)));
    }
    public static Item registerFood(String identifier, FoodComponent foodComponent) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new Item(new FabricItemSettings().group(ItemGroup.FOOD).food(foodComponent)));
    }
    public static SpawnEggItem registerSpawnEgg(String identifier, EntityType entityType, int primaryColor, int secondaryColor) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new SpawnEggItem(entityType, primaryColor, secondaryColor, new FabricItemSettings().group(ItemGroup.MISC)));
    }
    public static SpawnEggItem registerSpawnEgg(String identifier, EntityType entityType) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new SpawnEggItem(entityType, 0, 0, new FabricItemSettings().group(ItemGroup.MISC)));
    }
    public static Item register(String identifier, Item.Settings settings) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new Item(settings));
    }
    public static Item register(String identifier) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    }
    public static Item register(String identifier, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modID, identifier), item);
    }

    static {
        RAW_CORNDOG = registerFood("corndog", FoodComponents.RAW_CORNDOG);
        COOKED_CORNDOG = registerFood("cooked_corndog", FoodComponents.COOKED_CORNDOG);
        RAW_BACON = registerFood("bacon", FoodComponents.RAW_BACON);
        COOKED_BACON = registerFood("cooked_bacon", FoodComponents.COOKED_BACON);
        CORN = registerFood("corn", FoodComponents.CORN);
        TOMATO = registerFood("tomato", FoodComponents.TOMATO);
        LETTUCE = registerFood("lettuce", FoodComponents.LETTUCE);
        CHEESE = registerFood("cheese", FoodComponents.CHEESE);
        GARDEN_SALAD = registerFood("garden_salad", FoodComponents.GARDEN_SALAD);
        BLT = registerFood("blt", FoodComponents.BLT);
        STRAWBERRY = registerFood("strawberry", FoodComponents.STRAWBERRY);
        RADISH = registerFood("radish", FoodComponents.RADISH);
        RADISH_STEW = registerFood("radish_stew", FoodComponents.RADISH_STEW);
        CHERRIES = registerFood("cherries", FoodComponents.CHERRIES);
        SPARK_FISH = registerFood("spark_fish", FoodComponents.SPARK_FISH);
        LAVA_EEL = registerFood("lava_eel", FoodComponents.LAVA_EEL);
        CRAB_MEAT = registerFood("crab_meat", FoodComponents.CRAB_MEAT);
        COOKED_CRAB_MEAT = registerFood("cooked_crab_meat", FoodComponents.COOKED_CRAB_MEAT);
        SEAFOOD_PATTY = registerFood("seafood_patty", FoodComponents.SEAFOOD_PATTY);
        PEACH = registerFood("peach", FoodComponents.PEACH);
        PEACOCK_LEG = registerFood("peacock_leg", FoodComponents.PEACOCK_LEG);
        COOKED_PEACOCK_LEG = registerFood("cooked_peacock_leg", FoodComponents.COOKED_PEACOCK_LEG);
        BUTTER = register("butter");
        GOLDEN_BREAD = registerFood("golden_bread", FoodComponents.SHINY_BREAD);
        GOLDEN_CHICKEN = registerFood("golden_chicken", FoodComponents.SHINY_CHICKEN);
        GOLDEN_TROPICAL_FISH = registerFood("golden_tropical_fish", FoodComponents.SHINY_TROPICAL_FISH);
        GOLDEN_COD = registerFood("golden_cod", FoodComponents.SHINY_COD);
        GOLDEN_PORKCHOP = registerFood("golden_porkchop", FoodComponents.SHINY_PORKCHOP);
        GOLDEN_MELON_SLICE = registerFood("golden_melon_slice", FoodComponents.SHINY_MELON_SLICE);
        GOLDEN_MUSHROOM_STEW = registerFood("golden_mushroom_stew", FoodComponents.SHINY_MUSHROOM_STEW);
        GOLDEN_STEAK = registerFood("golden_steak", FoodComponents.SHINY_STEAK);
        AMETHYST = register("amethyst");
        RUBY = register("ruby");
        TIGERS_EYE = register("tigers_eye");
        TITANIUM_INGOT = register("titanium_ingot");
        TITANIUM_NUGGET = register("titanium_nugget");
        URANIUM_INGOT = register("uranium_ingot");
        URANIUM_NUGGET = register("uranium_nugget");
        ALUMINIUM_INGOT = register("aluminum_ingot");
        ALUMINIUM_NUGGET = register("aluminum_nugget");
        DRUID_KEY = register("druid_key");
        SALT = register("salt");
        ULTIMATE_SWORD = registerSword("ultimate_sword", ULTIMATE_TOOL_MATERIAL, new Config.Ultimate());






        ENT_EGG = registerSpawnEgg("ent_spawn_egg", Entities.ENT);
        RED_ANT_EGG = registerSpawnEgg("red_ant_egg", Entities.ENT);
        BROWN_ANT_EGG = registerSpawnEgg("brown_ant_egg", Entities.ENT);
        RAINBOW_ANT_EGG = registerSpawnEgg("rainbow_ant_egg", Entities.ENT);
        UNSTABLE_ANT_EGG = registerSpawnEgg("unstable_ant_egg", Entities.ENT);
        TERMITE_EGG = registerSpawnEgg("termite_egg", Entities.ENT);
        HERCULES_BEETLE_EGG = registerSpawnEgg("hercules_beetle_egg", Entities.ENT);
        RUBY_BUG_EGG = registerSpawnEgg("ruby_bug_egg", Entities.ENT);
        EMERALD_GATOR_EGG = registerSpawnEgg("emerald_gator_egg", Entities.ENT);
        ROBO_SNIPER_EGG = registerSpawnEgg("robo_sniper_egg", Entities.ENT);
        BEAVER_EGG = registerSpawnEgg("beaver_egg", Entities.ENT);
        APPLE_COW_EGG = registerSpawnEgg("apple_cow_egg", Entities.ENT);
        GOLDEN_APPLE_COW_EGG = registerSpawnEgg("golden_apple_cow_egg", Entities.ENT);
        IRON_GOLEM_EGG = registerSpawnEgg("iron_golem_egg", EntityType.IRON_GOLEM, 0xe2dbd6, 0x74a332);
        SNOW_GOLEM_EGG = registerSpawnEgg("snow_golem_egg", EntityType.SNOW_GOLEM, 0xffffff, 0xe38a1d);
    }

    @Override
    public void onInitialize() { }
}