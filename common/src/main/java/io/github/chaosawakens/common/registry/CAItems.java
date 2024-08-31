package io.github.chaosawakens.common.registry;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.asm.annotations.RegistrarEntry;
import io.github.chaosawakens.api.item.ItemPropertyWrapper;
import io.github.chaosawakens.api.platform.CAServices;
import io.github.chaosawakens.util.ModelUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

@RegistrarEntry
public final class CAItems {
    private static final ObjectArrayList<Supplier<Item>> ITEMS = new ObjectArrayList<>();

    // Dairy
    public static final Supplier<Item> BUTTER = ItemPropertyWrapper.create(registerItem("butter", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BUTTER))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/dairy/butter")))
            .build()
            .getParentItem();
    public static final Supplier<Item> CHEESE = ItemPropertyWrapper.create(registerItem("cheese", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CHEESE))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/dairy/cheese")))
            .build()
            .getParentItem();

    // Meat
    public static final Supplier<Item> BACON = ItemPropertyWrapper.create(registerItem("raw_bacon", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_BACON))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/raw/bacon")))
            .build()
            .getParentItem();
    public static final Supplier<Item> COOKED_BACON = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_bacon", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_BACON))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/cooked/cooked_bacon")))
            .build()
            .getParentItem();
    public static final Supplier<Item> CORNDOG = ItemPropertyWrapper.create(registerItem("raw_corndog", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_CORNDOG))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/raw/corndog")))
            .build()
            .getParentItem();
    public static final Supplier<Item> COOKED_CORNDOG = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_corndog", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_CORNDOG))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/cooked/cooked_corndog")))
            .build()
            .getParentItem();
    public static final Supplier<Item> CRAB_MEAT = ItemPropertyWrapper.create(registerItem("raw_crab_meat", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_CRAB_MEAT))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/raw/crab_meat")))
            .build()
            .getParentItem();
    public static final Supplier<Item> COOKED_CRAB_MEAT = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_crab_meat", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_CRAB_MEAT))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/cooked/cooked_crab_meat")))
            .build()
            .getParentItem();
    public static final Supplier<Item> PEACOCK_LEG = ItemPropertyWrapper.create(registerItem("raw_peacock_leg", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_PEACOCK_LEG))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/raw/peacock_leg")))
            .build()
            .getParentItem();
    public static final Supplier<Item> COOKED_PEACOCK_LEG = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_peacock_leg", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_PEACOCK_LEG))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/cooked/cooked_peacock_leg")))
            .build()
            .getParentItem();
    public static final Supplier<Item> VENISON = ItemPropertyWrapper.create(registerItem("raw_venison", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_VENISON))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/raw/venison")))
            .build()
            .getParentItem();
    public static final Supplier<Item> COOKED_VENISON = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_venison", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_VENISON))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/cooked/cooked_venison")))
            .build()
            .getParentItem();

    public static final Supplier<Item> BLUE_FISH = ItemPropertyWrapper.create(registerItem("blue_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_BLUE_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/blue_fish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> FIRE_FISH = ItemPropertyWrapper.create(registerItem("fire_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_FIRE_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/fire_fish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> GRAY_FISH = ItemPropertyWrapper.create(registerItem("gray_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_GRAY_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/gray_fish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> GREEN_FISH = ItemPropertyWrapper.create(registerItem("green_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_GREEN_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/green_fish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> LAVA_EEL = ItemPropertyWrapper.create(registerItem("lava_eel", () -> new Item(new Item.Properties().food(CAFoods.FOOD_LAVA_EEL))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/lava_eel")))
            .build()
            .getParentItem();
    public static final Supplier<Item> PINK_FISH = ItemPropertyWrapper.create(registerItem("pink_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_PINK_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/pink_fish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> ROCK_FISH = ItemPropertyWrapper.create(registerItem("rock_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_ROCK_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/rock_fish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> SPARK_FISH = ItemPropertyWrapper.create(registerItem("spark_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_SPARK_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/spark_fish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> SUN_FISH = ItemPropertyWrapper.create(registerItem("sun_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_SUN_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/sun_fish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> WOOD_FISH = ItemPropertyWrapper.create(registerItem("wood_fish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_WOOD_FISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/fish/wood_fish")))
            .build()
            .getParentItem();

    // Plant
    public static final Supplier<Item> CHERRIES = ItemPropertyWrapper.create(registerItem("cherries", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CHERRIES))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/fruit/cherries")))
            .build()
            .getParentItem();
    public static final Supplier<Item> CORN = ItemPropertyWrapper.create(registerItem("corn", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CORN))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/fruit/corn")))
            .build()
            .getParentItem();
    public static final Supplier<Item> LETTUCE = ItemPropertyWrapper.create(registerItem("lettuce", () -> new Item(new Item.Properties().food(CAFoods.FOOD_LETTUCE))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/vegetable/lettuce")))
            .build()
            .getParentItem();
    public static final Supplier<Item> PEACH = ItemPropertyWrapper.create(registerItem("peach", () -> new Item(new Item.Properties().food(CAFoods.FOOD_PEACH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/fruit/peach")))
            .build()
            .getParentItem();
    public static final Supplier<Item> QUINOA = ItemPropertyWrapper.create(registerItem("quinoa", () -> new Item(new Item.Properties().food(CAFoods.FOOD_QUINOA))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/vegetable/quinoa")))
            .build()
            .getParentItem();
    public static final Supplier<Item> RADISH = ItemPropertyWrapper.create(registerItem("radish", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RADISH))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/vegetable/radish")))
            .build()
            .getParentItem();
    public static final Supplier<Item> STRAWBERRY = ItemPropertyWrapper.create(registerItem("strawberry", () -> new Item(new Item.Properties().food(CAFoods.FOOD_STRAWBERRY))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/fruit/strawberry")))
            .build()
            .getParentItem();
    public static final Supplier<Item> TOMATO = ItemPropertyWrapper.create(registerItem("tomato", () -> new Item(new Item.Properties().food(CAFoods.FOOD_TOMATO))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/vegetable/tomato")))
            .build()
            .getParentItem();

    public static final Supplier<Item> CRYSTAL_APPLE = ItemPropertyWrapper.create(registerItem("crystal_apple", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_APPLE))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/fruit/crystal_apple")))
            .build()
            .getParentItem();
    public static final Supplier<Item> CRYSTAL_BEETROOT = ItemPropertyWrapper.create(registerItem("crystal_beetroot", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_BEETROOT))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/vegetable/crystal_beetroot")))
            .build()
            .getParentItem();
    public static final Supplier<Item> CRYSTAL_CARROT = ItemPropertyWrapper.create(registerItem("crystal_carrot", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_CARROT))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/vegetable/crystal_carrot")))
            .build()
            .getParentItem();
    public static final Supplier<Item> CRYSTAL_POTATO = ItemPropertyWrapper.create(registerItem("crystal_potato", () -> new Item(new Item.Properties().food(CAFoods.FOOD_CRYSTAL_POTATO))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/plant/vegetable/crystal_potato")))
            .build()
            .getParentItem();

    // Manufactured

    // Candy

    // Snack

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
