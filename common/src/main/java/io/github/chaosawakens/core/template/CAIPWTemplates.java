package io.github.chaosawakens.core.template;

import com.mememan.nexus.property_wrapper.def.item.ItemPropertyWrapper;
import com.mememan.nexus.template.property_wrapper.ItemPropertyWrapperTemplates;
import io.github.chaosawakens.content.registry.CACreativeModeTabs;
import net.minecraft.world.item.Item;

public final class CAIPWTemplates {
    public static final ItemPropertyWrapper<Item> FOOD = new ItemPropertyWrapper<>()
            .builder()
            .copyFrom(ItemPropertyWrapperTemplates.BASIC_GENERATED)
            .withParentTab(CACreativeModeTabs.FOOD)
            .build();
    public static final ItemPropertyWrapper<Item> COOKED_FOOD = new ItemPropertyWrapper<>()
            .builder()
            .copyFrom(ItemPropertyWrapperTemplates.COOKED_FOOD)
            .withParentTab(CACreativeModeTabs.FOOD)
            .build();

    public static final ItemPropertyWrapper<Item> FOOD_COMPONENT = new ItemPropertyWrapper<>()
            .builder()
            .copyFrom(ItemPropertyWrapperTemplates.MATERIAL)
            .withParentTab(CACreativeModeTabs.FOOD)
            .build();
    public static final ItemPropertyWrapper<Item> FOOD_ON_A_STICK = new ItemPropertyWrapper<>()
            .builder()
            .copyFrom(ItemPropertyWrapperTemplates.ROD)
            .withParentTab(CACreativeModeTabs.FOOD)
            .build();

    private CAIPWTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CAIPWTemplates)");
    }
}
