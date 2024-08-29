package io.github.chaosawakens.common.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public final class CAFoods {
    // DAIRY
    public static final FoodProperties FOOD_BUTTER = new FoodProperties.Builder().nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties FOOD_CHEESE = new FoodProperties.Builder().nutrition(4).saturationMod(0.5F).build();

    // MEAT
    public static final FoodProperties FOOD_RAW_BACON = new FoodProperties.Builder().nutrition(3).saturationMod(0.33F).meat().build();
    public static final FoodProperties FOOD_COOKED_BACON = new FoodProperties.Builder().nutrition(8).saturationMod(1F).meat().build();
    public static final FoodProperties FOOD_RAW_CORNDOG = new FoodProperties.Builder().nutrition(4).saturationMod(0.25F).build();
    public static final FoodProperties FOOD_COOKED_CORNDOG = new FoodProperties.Builder().nutrition(8).saturationMod(1F).build();
    public static final FoodProperties FOOD_RAW_CRAB_MEAT = new FoodProperties.Builder().nutrition(3).saturationMod(0.25F).meat().build();
    public static final FoodProperties FOOD_COOKED_CRAB_MEAT = new FoodProperties.Builder().nutrition(6).saturationMod(0.75F).meat().build();
    public static final FoodProperties FOOD_RAW_PEACOCK_LEG = new FoodProperties.Builder().nutrition(2).saturationMod(0.5F).build();
    public static final FoodProperties FOOD_COOKED_PEACOCK_LEG = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).build();
    public static final FoodProperties FOOD_RAW_VENISON = new FoodProperties.Builder().nutrition(2).saturationMod(0.5F).meat().build();
    public static final FoodProperties FOOD_COOKED_VENISON = new FoodProperties.Builder().nutrition(6).saturationMod(0.8F).meat().build();

    // PLANTS
    public static final FoodProperties FOOD_CHERRIES = new FoodProperties.Builder().nutrition(2).saturationMod(0.45F).build();
    public static final FoodProperties FOOD_CORN = new FoodProperties.Builder().nutrition(2).saturationMod(0.5F).build();
    public static final FoodProperties FOOD_LETTUCE = new FoodProperties.Builder().nutrition(3).saturationMod(0.45F).build();
    public static final FoodProperties FOOD_PEACH = new FoodProperties.Builder().nutrition(3).saturationMod(0.55F).build();
    public static final FoodProperties FOOD_RADISH = new FoodProperties.Builder().nutrition(2).saturationMod(0.45F).build();
    public static final FoodProperties FOOD_QUINOA = new FoodProperties.Builder().nutrition(2).saturationMod(0.6F).build();
    public static final FoodProperties FOOD_STRAWBERRY = new FoodProperties.Builder().nutrition(2).saturationMod(0.65F).build();
    public static final FoodProperties FOOD_TOMATO = new FoodProperties.Builder().nutrition(2).saturationMod(0.55F).build();

    // CRYSTAL PLANTS
    public static final FoodProperties FOOD_CRYSTAL_APPLE = new FoodProperties.Builder().nutrition(5).saturationMod(0.85F).alwaysEat().effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION, 3000, 0), 1.0F).build();
    public static final FoodProperties FOOD_CRYSTAL_BEETROOT = new FoodProperties.Builder().nutrition(1).saturationMod(0.15F).alwaysEat().effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION, 3000, 0), 1.0F).build();
    public static final FoodProperties FOOD_CRYSTAL_CARROT = new FoodProperties.Builder().nutrition(3).saturationMod(0.35F).alwaysEat().effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 3000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION, 3000, 0), 1.0F).build();
    public static final FoodProperties FOOD_CRYSTAL_POTATO = new FoodProperties.Builder().nutrition(2).saturationMod(0.30F).alwaysEat().effect(new MobEffectInstance(MobEffects.SATURATION, 3000, 0), 1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION, 3000, 0), 1.0F).build();

    // MANUFACTURED
    public static final FoodProperties FOOD_BLT_SANDWICH = new FoodProperties.Builder().nutrition(9).saturationMod(1.1F).build();
    public static final FoodProperties FOOD_GARDEN_SALAD = new FoodProperties.Builder().nutrition(5).saturationMod(0.95F).build();
    public static final FoodProperties FOOD_SEAFOOD_PATTY = new FoodProperties.Builder().nutrition(8).saturationMod(0.9F).build();
    public static final FoodProperties FOOD_RADISH_STEW = new FoodProperties.Builder().nutrition(5).saturationMod(0.75F).build();
    public static final FoodProperties FOOD_QUINOA_SALAD = new FoodProperties.Builder().nutrition(7).saturationMod(0.9F).build();

    // POPCORN
    public static final FoodProperties FOOD_POPCORN = new FoodProperties.Builder().nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties FOOD_POPCORN_BAG = new FoodProperties.Builder().nutrition(4).saturationMod(0.25F).build();
    public static final FoodProperties FOOD_SALTED_POPCORN_BAG = new FoodProperties.Builder().nutrition(5).saturationMod(0.275F).build();
    public static final FoodProperties FOOD_BUTTERED_POPCORN_BAG = new FoodProperties.Builder().nutrition(6).saturationMod(0.3F).build();

    // CANDY
    public static final FoodProperties FOOD_BUTTER_CANDY = new FoodProperties.Builder().nutrition(2).saturationMod(0.15F).alwaysEat().effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 1), 1.0F).build();
    public static final FoodProperties FOOD_CANDYCANE = new FoodProperties.Builder().nutrition(2).saturationMod(0.15F).alwaysEat().effect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 1), 1.0F).build();

    // FISH
    public static final FoodProperties FOOD_BLUE_FISH = new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties FOOD_GRAY_FISH = new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties FOOD_GREEN_FISH = new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties FOOD_PINK_FISH = new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties FOOD_SPARK_FISH = new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).alwaysEat().effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0), 1.0F).build();
    public static final FoodProperties FOOD_FIRE_FISH = new FoodProperties.Builder().nutrition(3).saturationMod(0.4F).alwaysEat().effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0), 1.0F).build();
    public static final FoodProperties FOOD_LAVA_EEL = new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).alwaysEat().effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0), 1.0F).build();
    public static final FoodProperties FOOD_SUN_FISH = new FoodProperties.Builder().nutrition(4).saturationMod(0.6F).alwaysEat().effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0F).build();

    // GOLDEN FOOD
    public static final FoodProperties FOOD_GOLDEN_MELON_SLICE = new FoodProperties.Builder().nutrition(2).saturationMod(0.45F).alwaysEat().build();
    public static final FoodProperties FOOD_GOLDEN_BEETROOT = new FoodProperties.Builder().nutrition(3).saturationMod(0.45F).alwaysEat().build();
    public static final FoodProperties FOOD_GOLDEN_POTATO = new FoodProperties.Builder().nutrition(3).saturationMod(0.65F).alwaysEat().effect(new MobEffectInstance(MobEffects.REGENERATION, 400, 0), 1.0F).build();
    public static final FoodProperties FOOD_GOLDEN_BAKED_POTATO = new FoodProperties.Builder().nutrition(6).saturationMod(0.65F).alwaysEat().effect(new MobEffectInstance(MobEffects.REGENERATION, 400, 0), 1.0F).build();
    public static final FoodProperties FOOD_ENCHANTED_GOLDEN_CARROT = new FoodProperties.Builder().nutrition(6).saturationMod(0.85F).alwaysEat().effect(new MobEffectInstance(MobEffects.REGENERATION, 600, 2), 1.0F).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 1.0F).build();
    public static final FoodProperties FOOD_ULTIMATE_APPLE = new FoodProperties.Builder().nutrition(6).saturationMod(1.5F).alwaysEat().effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3900, 2), 1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION, 4800, 3), 1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3), 1.0F).effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 5400, 2), 1.0F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 7200, 0), 1.0F).effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 7200, 0), 1.0F).build();
}
