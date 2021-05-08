package io.github.chaosawakens.items.util;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class Foods {

    public static class foods {
        public static final FoodComponent RAW_CORNDOG = build(4, 0.6f).build();
        public static final FoodComponent COOKED_CORNDOG = build(14, 1.5f).build();
        public static final FoodComponent RAW_BACON = build(8, 1.5f, true).build();
        public static final FoodComponent COOKED_BACON = build(14, 1.0f, true, false, true)
                .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 2000, 0), 1.0f)
                .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2000, 0), 1.0f).build();
        public static final FoodComponent CORN = build(6, 0.75f).build();
        public static final FoodComponent TOMATO = build(4, 0.55f).build();
        public static final FoodComponent LETTUCE = build(3, 0.45f).build();
        public static final FoodComponent CHEESE = build(4, 0.5f).build();
        public static final FoodComponent GARDEN_SALAD = build(10, 0.95f).build();
        public static final FoodComponent BLT = build(12, 0.95f).build();
        public static final FoodComponent STRAWBERRY = build(2, 0.65f).build();
        public static final FoodComponent RADISH = build(2, 0.45f).build();
        public static final FoodComponent RADISH_STEW = build(8, 6.4f).build();
        public static final FoodComponent CHERRIES = build(3, 0.45f).build();
        public static final FoodComponent SPARK_FISH = build(3, 0.2f, false, false, true)
                .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100, 0), 1.0f).build();
        public static final FoodComponent LAVA_EEL = build(2, 0.6f, false, false, true)
                .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, 0), 1.0f).build();
        public static final FoodComponent CRAB_MEAT = build(4, 0.25f, true).build();
        public static final FoodComponent COOKED_CRAB_MEAT = build(6, 0.75f, true).build();
        public static final FoodComponent SEAFOOD_PATTY = build(16, 2.35f).build();
        public static final FoodComponent PEACH = build(4, 0.55f).build();
        public static final FoodComponent PEACOCK_LEG = build(6, 0.7f, true).build();
        public static final FoodComponent COOKED_PEACOCK_LEG = build(12, 1.4f, true).build();
    }

    public static class shinyFoods {
        public static final FoodComponent BREAD = build(8, 0.8f, true).build();
        public static final FoodComponent CHICKEN = build(6, 0.45f, true).build();
        public static final FoodComponent TROPICAL_FISH = build(4, 0.2f, true).build();
        public static final FoodComponent COD = build(5, 0.3f, true).build();
        public static final FoodComponent PORKCHOP = build(8, 1.1f, true).build();
        public static final FoodComponent MELON_SLICE = build(8, 0.45f, true).build();
        public static final FoodComponent MUSHROOM_STEW = build(6, 0.25f, true).build();
        public static final FoodComponent STEAK = build(8, 1.1f, true).build();
    }

    private static FoodComponent.Builder build(int hunger, float saturation) { return build(hunger, saturation, false); }
    private static FoodComponent.Builder build(int hunger, float saturation, boolean meat) { return build(hunger, saturation, meat, false); }
    private static FoodComponent.Builder build(int hunger, float saturation, boolean meat, boolean snack) { return build(hunger, saturation, meat, snack, false); }
    private static FoodComponent.Builder build(int hunger, float saturation, boolean meat, boolean snack, boolean alwaysEdible ) {
        FoodComponent.Builder builder = new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation);
        if (meat) { builder.meat(); }
        if (snack) { builder.snack(); }
        if (alwaysEdible) { builder.alwaysEdible(); }
        return builder;
    }
}
