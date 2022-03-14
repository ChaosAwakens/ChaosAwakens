package io.github.chaosawakens.items.util;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class FoodComponents {
    public static final FoodComponent RAW_CORNDOG;
    public static final FoodComponent COOKED_CORNDOG;
    public static final FoodComponent RAW_BACON;
    public static final FoodComponent COOKED_BACON;
    public static final FoodComponent CORN;
    public static final FoodComponent TOMATO;
    public static final FoodComponent LETTUCE;
    public static final FoodComponent CHEESE;
    public static final FoodComponent GARDEN_SALAD;
    public static final FoodComponent BLT;
    public static final FoodComponent STRAWBERRY;
    public static final FoodComponent RADISH;
    public static final FoodComponent RADISH_STEW;
    public static final FoodComponent CHERRIES;
    public static final FoodComponent SPARK_FISH;
    public static final FoodComponent LAVA_EEL;
    public static final FoodComponent CRAB_MEAT;
    public static final FoodComponent COOKED_CRAB_MEAT;
    public static final FoodComponent SEAFOOD_PATTY;
    public static final FoodComponent PEACH;
    public static final FoodComponent PEACOCK_LEG;
    public static final FoodComponent COOKED_PEACOCK_LEG;
    public static final FoodComponent SHINY_BREAD;
    public static final FoodComponent SHINY_CHICKEN;
    public static final FoodComponent SHINY_TROPICAL_FISH;
    public static final FoodComponent SHINY_COD;
    public static final FoodComponent SHINY_PORKCHOP;
    public static final FoodComponent SHINY_MELON_SLICE;
    public static final FoodComponent SHINY_MUSHROOM_STEW;
    public static final FoodComponent SHINY_STEAK;
    public static final FoodComponent CANDY_CANE;
    public static final FoodComponent CRYSTAL_APPLE;
    public static final FoodComponent BUTTER;

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

    static {
        CRYSTAL_APPLE = build(14, 1.0f, true, false, true).statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 150, 1), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 150, 0), 1.0f).build(); // add
        BUTTER = build(1, 1.0f).build(); // add
        CANDY_CANE = build(2, 0.15f, false, false, true).statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 400, 1), 1.0f).build();
        RAW_CORNDOG = build(4, 0.6f).build();
        COOKED_CORNDOG = build(14, 1.5f).build();
        RAW_BACON = build(8, 1.5f, true).build();
        COOKED_BACON = build(14, 1.0f, true, false, true).statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 2000, 0), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 2000, 0), 1.0f).build();
        CORN = build(6, 0.75f).build();
        TOMATO = build(4, 0.55f).build();
        LETTUCE = build(3, 0.45f).build();
        CHEESE = build(4, 0.5f).build();
        GARDEN_SALAD = build(10, 0.95f).build();
        BLT = build(12, 0.95f).build();
        STRAWBERRY = build(2, 0.65f).build();
        RADISH = build(2, 0.45f).build();
        RADISH_STEW = build(8, 6.4f).build();
        CHERRIES = build(3, 0.45f, false, true).build();
        SPARK_FISH = build(3, 0.2f, false, false, true).statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100, 0), 1.0f).build();
        LAVA_EEL = build(2, 0.6f, false, false, true).statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, 0), 1.0f).build();
        CRAB_MEAT = build(4, 0.25f, true).build();
        COOKED_CRAB_MEAT = build(6, 0.75f, true).build();
        SEAFOOD_PATTY = build(16, 2.35f).build();
        PEACH = build(4, 0.55f).build();
        PEACOCK_LEG = build(6, 0.7f, true).build();
        COOKED_PEACOCK_LEG = build(12, 1.4f, true).build();
        SHINY_BREAD = build(8, 0.8f, true).build();
        SHINY_CHICKEN = build(6, 0.45f, true).build();
        SHINY_TROPICAL_FISH = build(4, 0.2f, true).build();
        SHINY_COD = build(5, 0.3f, true).build();
        SHINY_PORKCHOP = build(8, 1.1f, true).build();
        SHINY_MELON_SLICE = build(8, 0.45f, true).build();
        SHINY_MUSHROOM_STEW = build(6, 0.25f, true).build();
        SHINY_STEAK = build(8, 1.1f, true).build();
    }
}
