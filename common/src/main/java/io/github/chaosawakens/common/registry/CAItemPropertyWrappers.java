package io.github.chaosawakens.common.registry;

import io.github.chaosawakens.api.item.ItemPropertyWrapper;
import io.github.chaosawakens.util.RecipeUtil;

public class CAItemPropertyWrappers {
    // Food
    public static final ItemPropertyWrapper COOKED_FOOD = ItemPropertyWrapper.createTemplate()
            .builder()
            .withRecipe(RecipeUtil::cookedFood)
            .build();
}
