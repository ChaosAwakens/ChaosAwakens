package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.item.ItemPropertyWrapper;
import io.github.chaosawakens.api.platform.CAServices;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

@RegistrarEntry
public final class CAItems {
    private static final ObjectArrayList<Supplier<Item>> ITEMS = new ObjectArrayList<>();

    // Dairy
    public static final Supplier<Item> BUTTER = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("butter", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BUTTER)))).getParentItem();
    public static final Supplier<Item> CHEESE = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("cheese", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CHEESE)))).getParentItem();

    // Meat
    public static final Supplier<Item> BACON = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("raw_bacon", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_BACON)))).getParentItem();
    public static final Supplier<Item> COOKED_BACON = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_bacon", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_BACON)))).getParentItem();
    public static final Supplier<Item> CORNDOG = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("raw_corndog", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_CORNDOG)))).getParentItem();
    public static final Supplier<Item> COOKED_CORNDOG = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_corndog", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_CORNDOG)))).getParentItem();
    public static final Supplier<Item> CRAB_MEAT = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("raw_crab_meat", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_CRAB_MEAT)))).getParentItem();
    public static final Supplier<Item> COOKED_CRAB_MEAT = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_crab_meat", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_CRAB_MEAT)))).getParentItem();
    public static final Supplier<Item> PEACOCK_LEG = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("raw_peacock_leg", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_PEACOCK_LEG)))).getParentItem();
    public static final Supplier<Item> COOKED_PEACOCK_LEG = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_peacock_leg", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_PEACOCK_LEG)))).getParentItem();
    public static final Supplier<Item> VENISON = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("raw_venison", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_VENISON)))).getParentItem();
    public static final Supplier<Item> COOKED_VENISON = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_venison", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_VENISON)))).getParentItem();

    public static final Supplier<Item> BLUE_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("blue_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BLUE_FISH)))).getParentItem();
    public static final Supplier<Item> FIRE_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("fire_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_FIRE_FISH)))).getParentItem();
    public static final Supplier<Item> GRAY_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("gray_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_GRAY_FISH)))).getParentItem();
    public static final Supplier<Item> GREEN_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("green_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_GREEN_FISH)))).getParentItem();
    public static final Supplier<Item> LAVA_EEL = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("lava_eel", () -> new Item(new Item.Properties().food(CAFoods.FOOD_LAVA_EEL)))).getParentItem();
    public static final Supplier<Item> PINK_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("pink_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_PINK_FISH)))).getParentItem();
    public static final Supplier<Item> ROCK_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("rock_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_ROCK_FISH)))).getParentItem();
    public static final Supplier<Item> SPARK_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("spark_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_SPARK_FISH)))).getParentItem();
    public static final Supplier<Item> SUN_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("sun_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_SUN_FISH)))).getParentItem();
    public static final Supplier<Item> WOOD_FISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("wood_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_WOOD_FISH)))).getParentItem();

    // Plant
    public static final Supplier<Item> CHERRIES = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("cherries", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CHERRIES)))).getParentItem();
    public static final Supplier<Item> CORN = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("corn", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CORN)))).getParentItem();
    public static final Supplier<Item> LETTUCE = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("lettuce", () -> new Item(new Item.Properties().food(CAFoods.FOOD_LETTUCE)))).getParentItem();
    public static final Supplier<Item> PEACH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("peach", () -> new Item(new Item.Properties().food(CAFoods.FOOD_PEACH)))).getParentItem();
    public static final Supplier<Item> QUINOA = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("quinoa", () -> new Item(new Item.Properties().food(CAFoods.FOOD_QUINOA)))).getParentItem();
    public static final Supplier<Item> RADISH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("radish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RADISH)))).getParentItem();
    public static final Supplier<Item> STRAWBERRY = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("strawberry", () -> new Item(new Item.Properties().food(CAFoods.FOOD_STRAWBERRY)))).getParentItem();
    public static final Supplier<Item> TOMATO = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("tomato", () -> new Item(new Item.Properties().food(CAFoods.FOOD_TOMATO)))).getParentItem();

    public static final Supplier<Item> CRYSTAL_APPLE = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("crystal_apple", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_APPLE)))).getParentItem();
    public static final Supplier<Item> CRYSTAL_BEETROOT = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("crystal_beetroot", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_BEETROOT)))).getParentItem();
    public static final Supplier<Item> CRYSTAL_CARROT = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("crystal_carrot", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_CARROT)))).getParentItem();
    public static final Supplier<Item> CRYSTAL_POTATO = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("crystal_potato", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_POTATO)))).getParentItem();

    // Candy
    public static final Supplier<Item> BUTTER_CANDY = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("butter_candy", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BUTTER_CANDY)))).getParentItem();
    public static final Supplier<Item> CANDYCANE = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("candycane", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CANDYCANE)))).getParentItem();

    // Manufactured
    public static final Supplier<Item> BLT_SANDWICH = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("blt_sandwich", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BLT_SANDWICH)))).getParentItem();
    public static final Supplier<Item> GARDEN_SALAD = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("garden_salad", () -> new Item(new Item.Properties().food(CAFoods.FOOD_GARDEN_SALAD)))).getParentItem();
    public static final Supplier<Item> QUINOA_SALAD = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("quinoa_salad", () -> new Item(new Item.Properties().food(CAFoods.FOOD_QUINOA_SALAD)))).getParentItem();
    public static final Supplier<Item> RADISH_STEW = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("radish_stew", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RADISH_STEW)))).getParentItem();
    public static final Supplier<Item> SEAFOOD_PATTY = ItemPropertyWrapper.of(CAItemPropertyWrappers.BASIC_GENERATED, registerItem("seafood_patty", () -> new Item(new Item.Properties().food(CAFoods.FOOD_SEAFOOD_PATTY)))).getParentItem();

    // Snack

    // Special

    // Minerals

    private static Supplier<Item> registerItem(String id, Supplier<Item> itemSup) {
        Supplier<Item> registeredItemSup = CAServices.REGISTRAR.registerObject(CAConstants.prefix(id), itemSup, BuiltInRegistries.ITEM); // Otherwise reference to the item sup is null cuz it needs to be registered b4hand
        ITEMS.add(registeredItemSup);
        return registeredItemSup;
    }

    public static Supplier<Item> registerExternalItem(String id, Supplier<Item> itemSup) {
        return registerItem(id, itemSup);
    }

    public static ImmutableList<Supplier<Item>> getItems() {
        return ImmutableList.copyOf(ITEMS);
    }
}
