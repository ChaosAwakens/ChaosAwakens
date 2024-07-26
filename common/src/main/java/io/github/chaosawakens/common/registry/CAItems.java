package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.api.platform.Services;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class CAItems {
    private static final ObjectArrayList<Supplier<Item>> ITEMS = new ObjectArrayList<>();

    public static final Supplier<Item> TEST = registerItem("test", () -> new Item(new Item.Properties()));

    private static Supplier<Item> registerItem(String id, Supplier<Item> attribSup) {
        return Services.REGISTRAR.registerObject(CAConstants.prefix(id), attribSup, BuiltInRegistries.ITEM);
    }
}
