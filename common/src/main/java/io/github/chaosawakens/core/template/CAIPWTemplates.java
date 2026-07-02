package io.github.chaosawakens.core.template;

import com.mememan.nexus.client.model.item.ItemModelDefinition;
import com.mememan.nexus.property_wrapper.def.item.ItemPropertyWrapper;
import com.mememan.nexus.template.property_wrapper.ItemPropertyWrapperTemplates;
import com.mememan.nexus.util.RegistryUtil;
import io.github.chaosawakens.content.registry.CACreativeModeTabs;
import io.github.chaosawakens.content.registry.CAExtraLocalizations;
import net.minecraft.data.models.model.TextureMapping;
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

    public static final ItemPropertyWrapper<Item> LUMP_MATERIAL = new ItemPropertyWrapper<>()
            .builder()
            .copyFrom(ItemPropertyWrapperTemplates.MATERIAL)
            .withRecipe(CARecipeTemplates::lumpMaterialRecipe)
            .build();

    public static final ItemPropertyWrapper<Item> HANDHELD_LONG = new ItemPropertyWrapper<>()
            .builder()
            .withModelDefinition(parentItem -> new ItemModelDefinition(CAModelTemplates.HANDHELD_LONG)
                .withTextureMapping(TextureMapping.layer0(RegistryUtil.getTextureLocationOrDefault(parentItem, "item"))))
            .build();

    public static final ItemPropertyWrapper<Item> CROSSBOW = new ItemPropertyWrapper<>()
            .builder()
            .copyFrom(ItemPropertyWrapperTemplates.MATERIAL)
            .withModelDefinition(CAModelTemplates::crossbow)
            .build();

    public static final ItemPropertyWrapper<Item> SET_BONUS_IPW = new ItemPropertyWrapper<>()
            .builder()
            .copyFrom(ItemPropertyWrapperTemplates.BASIC_GENERATED)
            .withAdditionalLocalizationKey(CAExtraLocalizations.SET_BONUS_LABEL.key(), CAExtraLocalizations.SET_BONUS_LABEL.value())
            .withAdditionalLocalizationKey(CAExtraLocalizations.EXPERIENCE_BONUS_DESCRIPTION.key(), CAExtraLocalizations.EXPERIENCE_BONUS_DESCRIPTION.value())
            .withAdditionalLocalizationKey(CAExtraLocalizations.LAVA_EEL_BONUS_DESCRIPTION.key(), CAExtraLocalizations.LAVA_EEL_BONUS_DESCRIPTION.value())
            .withAdditionalLocalizationKey(CAExtraLocalizations.EMERALD_BONUS_DESCRIPTION.key(), CAExtraLocalizations.EMERALD_BONUS_DESCRIPTION.value())
            .withAdditionalLocalizationKey(CAExtraLocalizations.LAPIS_BONUS_DESCRIPTION.key(), CAExtraLocalizations.LAPIS_BONUS_DESCRIPTION.value())
            .build();

    public static final ItemPropertyWrapper<Item> BOW = new ItemPropertyWrapper<>()
            .builder()
            .copyFrom(ItemPropertyWrapperTemplates.MATERIAL)
            .withModelDefinition(CAModelTemplates::bow)
            .build();

    private CAIPWTemplates() {
        throw new IllegalAccessError("Attempted to construct instance of template class! (CAIPWTemplates)");
    }
}
