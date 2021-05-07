package io.github.chaosawakens.items.util;

import io.github.chaosawakens.ChaosAwakens;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterItem {
    public static void register(String identifier, Item item) {
        Registry.register(Registry.ITEM, new Identifier(ChaosAwakens.modId, identifier), item);
    }
}