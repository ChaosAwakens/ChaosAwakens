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

    // Plant

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
