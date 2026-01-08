package io.github.chaosawakens.util;

import com.mememan.nexus.property_wrapper.base.generic.PropertyWrapper;
import com.mememan.nexus.property_wrapper.base.specialised.vanilla.VanillaBasedPropertyWrapper;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class PredicateUtil {

    private PredicateUtil() {
        throw new IllegalAccessError("Attempted to construct instance of utility class! (PredicateUtil)");
    }

    public static <I extends ItemLike> boolean hasSpecifiedCreativeModeTabs(I targetObj) {
        return PropertyWrapper.PropertyWrappersContainer.getWrapperFor(targetObj)
                .filter(wrapper -> wrapper instanceof VanillaBasedPropertyWrapper)
                .map(wrapper -> (List<Supplier<CreativeModeTab>>) ((VanillaBasedPropertyWrapper) wrapper).getParentCreativeModeTabs())
                .map(tabs -> !tabs.isEmpty())
                .orElse(false);
    }

    public static <I extends ItemLike> boolean hasSpecifiedCreativeModeTabs(Supplier<I> targetObjSup) {
        return hasSpecifiedCreativeModeTabs(targetObjSup.get());
    }

    public static <I extends ItemLike> boolean hasSpecifiedCreativeModeTab(I targetObj, Supplier<CreativeModeTab> targetTab) {
        return PropertyWrapper.PropertyWrappersContainer.getWrapperFor(targetObj)
                .filter(wrapper -> wrapper instanceof VanillaBasedPropertyWrapper)
                .map(wrapper -> (List<Supplier<CreativeModeTab>>) ((VanillaBasedPropertyWrapper) wrapper).getParentCreativeModeTabs())
                .map(tabs -> tabs.stream().map(Supplier::get).anyMatch(tab -> Objects.equals(tab, targetTab.get())))
                .orElse(false);
    }

    public static <I extends ItemLike> boolean hasSpecifiedCreativeModeTab(Supplier<I> targetObjSup, Supplier<CreativeModeTab> targetTab) {
        return hasSpecifiedCreativeModeTab(targetObjSup.get(), targetTab);
    }

    public static <I extends ItemLike> boolean hasSpecifiedCreativeModeTab(I targetObj, String targetTabName) {
        return PropertyWrapper.PropertyWrappersContainer.getWrapperFor(targetObj)
                .filter(wrapper -> wrapper instanceof VanillaBasedPropertyWrapper)
                .map(wrapper -> (List<Supplier<CreativeModeTab>>) ((VanillaBasedPropertyWrapper) wrapper).getParentCreativeModeTabs())
                .map(tabs -> tabs.stream().map(Supplier::get).anyMatch(tab -> tab.getDisplayName().getString().contains(targetTabName)))
                .orElse(false);
    }

    public static <T extends ItemLike> boolean hasSpecifiedCreativeModeTab(Supplier<T> targetObjSup, String targetTabName) {
        return hasSpecifiedCreativeModeTab(targetObjSup.get(), targetTabName);
    }

    public static boolean isFood(Supplier<Item> targetItem) {
        return targetItem.get().getFoodProperties() != null;
    }
}
