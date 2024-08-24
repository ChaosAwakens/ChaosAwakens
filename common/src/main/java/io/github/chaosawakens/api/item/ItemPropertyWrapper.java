package io.github.chaosawakens.api.item;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * A wrapper class used to store information referenced in datagen to simplify creating data entries for items.
 */
public class ItemPropertyWrapper {
    private static final Object2ObjectLinkedOpenHashMap<Supplier<Item>, ItemPropertyWrapper> MAPPED_IPWS = new Object2ObjectLinkedOpenHashMap<>();


    /**
     * A builder class used to construct certain item-related data for datagen.
     */
    public static class IPWBuilder {
        private final ItemPropertyWrapper ownerWrapper;
        private final Supplier<Item> itemParent;

        private IPWBuilder(ItemPropertyWrapper ownerWrapper, Supplier<Item> itemParent) {
            this.ownerWrapper = ownerWrapper;
            this.itemParent = itemParent;
        }
    }
}
