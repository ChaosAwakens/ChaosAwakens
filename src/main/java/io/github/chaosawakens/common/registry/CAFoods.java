package io.github.chaosawakens.common.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class CAFoods {
	// MEATS
	public static final Food FOOD_RAW_BACON = new Food.Builder().nutrition(5).saturationMod(1.5F).meat().build();
	public static final Food FOOD_COOKED_BACON = new Food.Builder().nutrition(10).saturationMod(1.5F).meat().effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 2000, 0), 1.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 2000, 0), 1.0F).build();
	public static final Food FOOD_RAW_CORNDOG = new Food.Builder().nutrition(4).saturationMod(0.6F).build();
	public static final Food FOOD_COOKED_CORNDOG = new Food.Builder().nutrition(8).saturationMod(1.5F).build();
	public static final Food FOOD_CRAB_MEAT = new Food.Builder().nutrition(4).saturationMod(0.25F).meat().build();
	public static final Food FOOD_COOKED_CRAB_MEAT = new Food.Builder().nutrition(6).saturationMod(0.75F).meat().build();
	public static final Food FOOD_PEACOCK_LEG = new Food.Builder().nutrition(4).saturationMod(0.7F).build();
	public static final Food FOOD_COOKED_PEACOCK_LEG = new Food.Builder().nutrition(9).saturationMod(1.4F).build();
	public static final Food FOOD_RAW_VENISON = new Food.Builder().nutrition(5).saturationMod(0.5F).meat().build();
	public static final Food FOOD_COOKED_VENISON = new Food.Builder().nutrition(9).saturationMod(1.2F).meat().build();

	// PLANTS
	public static final Food FOOD_CHERRIES = new Food.Builder().nutrition(3).saturationMod(0.45F).build();
	public static final Food FOOD_CORN = new Food.Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food FOOD_LETTUCE = new Food.Builder().nutrition(3).saturationMod(0.45F).build();
	public static final Food FOOD_PEACH = new Food.Builder().nutrition(4).saturationMod(0.55F).build();
	public static final Food FOOD_RADISH = new Food.Builder().nutrition(2).saturationMod(0.45F).build();
	public static final Food FOOD_STRAWBERRY = new Food.Builder().nutrition(2).saturationMod(0.65F).build();
	public static final Food FOOD_TOMATO = new Food.Builder().nutrition(4).saturationMod(0.55F).build();

	// CRYSTAL PLANTS
	public static final Food FOOD_CRYSTAL_APPLE = new Food.Builder().nutrition(5).saturationMod(0.85F).alwaysEat().effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 3000, 0), 1.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 3000, 0), 1.0F).build();
	public static final Food FOOD_CRYSTAL_BEETROOT = new Food.Builder().nutrition(1).saturationMod(0.15F).alwaysEat().effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 3000, 0), 1.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 3000, 0), 1.0F).build();
	public static final Food FOOD_CRYSTAL_CARROT = new Food.Builder().nutrition(3).saturationMod(0.35F).alwaysEat().effect(() -> new EffectInstance(Effects.NIGHT_VISION, 3000, 0), 1.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 3000, 0), 1.0F).build();
	public static final Food FOOD_CRYSTAL_POTATO = new Food.Builder().nutrition(2).saturationMod(0.30F).alwaysEat().effect(() -> new EffectInstance(Effects.SATURATION, 3000, 0), 1.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 3000, 0), 1.0F).build();

	// MANUFACTURED
	public static final Food FOOD_BLT_SANDWICH = new Food.Builder().nutrition(9).saturationMod(0.95F).build();
	public static final Food FOOD_CHEESE = new Food.Builder().nutrition(4).saturationMod(0.5F).build();
	public static final Food FOOD_GARDEN_SALAD = new Food.Builder().nutrition(5).saturationMod(0.95F).build();
	public static final Food FOOD_SEAFOOD_PATTY = new Food.Builder().nutrition(7).saturationMod(0.35F).build();
	public static final Food FOOD_RADISH_STEW = new Food.Builder().nutrition(4).saturationMod(0.25F).build();

	// POPCORN
	public static final Food FOOD_POPCORN = new Food.Builder().nutrition(2).saturationMod(0.2F).build();
	public static final Food FOOD_POPCORN_BAG = new Food.Builder().nutrition(4).saturationMod(0.25F).build();
	public static final Food FOOD_SALTED_POPCORN_BAG = new Food.Builder().nutrition(5).saturationMod(0.275F).build();
	public static final Food FOOD_BUTTERED_POPCORN_BAG = new Food.Builder().nutrition(6).saturationMod(0.3F).build();

	// CANDY
	public static final Food FOOD_BUTTER_CANDY = new Food.Builder().nutrition(2).saturationMod(0.15F).alwaysEat().effect(() -> new EffectInstance(Effects.MOVEMENT_SPEED, 400, 1), 1.0F).build();
	public static final Food FOOD_CANDYCANE = new Food.Builder().nutrition(2).saturationMod(0.15F).alwaysEat().effect(() -> new EffectInstance(Effects.DIG_SPEED, 400, 1), 1.0F).build();

	// FISH
	public static final Food FOOD_BLUE_FISH = new Food.Builder().nutrition(5).saturationMod(0.6F).build();
	public static final Food FOOD_GRAY_FISH = new Food.Builder().nutrition(5).saturationMod(0.6F).build();
	public static final Food FOOD_GREEN_FISH = new Food.Builder().nutrition(5).saturationMod(0.6F).build();
	public static final Food FOOD_PINK_FISH = new Food.Builder().nutrition(5).saturationMod(0.6F).build();
	public static final Food FOOD_SPARK_FISH = new Food.Builder().nutrition(3).saturationMod(0.2F).alwaysEat().effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 100, 0), 1.0F).build();
	public static final Food FOOD_FIRE_FISH = new Food.Builder().nutrition(3).saturationMod(0.4F).alwaysEat().effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 200, 0), 1.0F).build();
	public static final Food FOOD_LAVA_EEL = new Food.Builder().nutrition(4).saturationMod(0.4F).alwaysEat().effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 400, 0), 1.0F).build();
	public static final Food FOOD_SUN_FISH = new Food.Builder().nutrition(4).saturationMod(0.6F).alwaysEat().effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0), 1.0F).build();

	// GOLDEN FOOD
	public static final Food FOOD_GOLDEN_MELON_SLICE = new Food.Builder().nutrition(2).saturationMod(0.45F).alwaysEat().build();
	public static final Food FOOD_GOLDEN_BEETROOT = new Food.Builder().nutrition(3).saturationMod(0.45F).alwaysEat().build();
	public static final Food FOOD_GOLDEN_POTATO = new Food.Builder().nutrition(3).saturationMod(0.65F).alwaysEat().effect(() -> new EffectInstance(Effects.REGENERATION, 400, 0), 1.0F).build();
	public static final Food FOOD_GOLDEN_BAKED_POTATO = new Food.Builder().nutrition(6).saturationMod(0.65F).alwaysEat().effect(() -> new EffectInstance(Effects.REGENERATION, 400, 0), 1.0F).build();
	public static final Food FOOD_ENCHANTED_GOLDEN_CARROT = new Food.Builder().nutrition(6).saturationMod(0.85F).alwaysEat().effect(() -> new EffectInstance(Effects.REGENERATION, 600, 2), 1.0F).effect(() -> new EffectInstance(Effects.NIGHT_VISION, 1200, 0), 1.0F).effect(() -> new EffectInstance(Effects.ABSORPTION, 1200, 0), 1.0F).build();
	public static final Food FOOD_ULTIMATE_APPLE = new Food.Builder().nutrition(6).saturationMod(1.5F).alwaysEat().effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 3900, 2), 1.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 4800, 3), 1.0F).effect(() -> new EffectInstance(Effects.ABSORPTION, 2400, 3), 1.0F).effect(() -> new EffectInstance(Effects.HEALTH_BOOST, 5400, 2), 1.0F).effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 7200, 0), 1.0F).effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 7200, 0), 1.0F).build();
}
