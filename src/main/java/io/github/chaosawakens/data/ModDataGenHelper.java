package io.github.chaosawakens.data;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;

public final class ModDataGenHelper {

    public static ItemModelBuilder simpleItem(ItemModelProvider provider, final String name,
                                              final ResourceLocation parent, final ResourceLocation texture) {
        return provider.withExistingParent(name, parent)
                .texture("layer0", texture);
    }

    public static ItemModelBuilder simpleItem(ItemModelProvider provider, final Item item,
                                              final ResourceLocation parent, final ResourceLocation texture) {
        return simpleItem(provider, itemName(item), parent, texture);
    }

    public static ItemModelBuilder generatedItem(ItemModelProvider provider, final String name,
                                                 final ResourceLocation texture) {
        return provider.withExistingParent(name, ITEM_PARENT_GENERATED)
                .texture("layer0", texture);
    }

    public static ItemModelBuilder generatedItem(ItemModelProvider provider, final Item item,
                                                 final ResourceLocation texture) {
        return generatedItem(provider, itemName(item), texture);
    }

    public static ItemModelBuilder generatedItem(ItemModelProvider provider, final Item item) {
        return generatedItem(provider, itemName(item), defaultItemTexture(provider, item));
    }

    public static ItemModelBuilder handHeldItem(ItemModelProvider provider, final String name,
                                                final ResourceLocation texture) {
        return provider.withExistingParent(name, ITEM_PARENT_HANDHELD)
                .texture("layer0", texture);
    }

    public static ItemModelBuilder handHeldItem(ItemModelProvider provider, final Item item,
                                                final ResourceLocation texture) {
        return handHeldItem(provider, itemName(item), texture);
    }

    public static ItemModelBuilder handHeldItem(ItemModelProvider provider, final Item item) {
        return handHeldItem(provider, itemName(item), defaultItemTexture(provider, item));
    }

    private static String itemName(Item item) {
        return item.getRegistryName().getPath();
    }

    private static ResourceLocation defaultItemTexture(ItemModelProvider provider, final Item item) {
        return provider.modLoc(ItemModelProvider.ITEM_FOLDER + "/" + itemName(item));
    }

    private static final ResourceLocation ITEM_PARENT_GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation ITEM_PARENT_HANDHELD = new ResourceLocation("item/handheld");

    private ModDataGenHelper() { }
}