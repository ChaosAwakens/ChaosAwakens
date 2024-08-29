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

    // Meat
    public static final Supplier<Item> BACON = ItemPropertyWrapper.create(registerItem("bacon", () -> new Item(new Item.Properties().food(CAFoods.FOOD_RAW_BACON))))
            .builder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/raw/bacon")))
            .build()
            .getParentItem();
    public static final Supplier<Item> COOKED_BACON = ItemPropertyWrapper.of(CAItemPropertyWrappers.COOKED_FOOD, registerItem("cooked_bacon", () -> new Item(new Item.Properties().food(CAFoods.FOOD_COOKED_BACON))))
            .cachedBuilder()
            .withCustomModelDefinition(ModelUtil.generated(CAConstants.prefix("food/organic/meat/cooked/cooked_bacon")))
            .build()
            .getParentItem();

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
