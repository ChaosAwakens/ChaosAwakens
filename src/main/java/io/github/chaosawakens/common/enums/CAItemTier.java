package io.github.chaosawakens.common.enums;

import io.github.chaosawakens.common.registry.CABlocks;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum CAItemTier implements IItemTier {
    TOOL_EMERALD(3, 1300, 8, 6, 24, () -> Ingredient.fromItems(Items.EMERALD.getItem())),

    TOOL_AMETHYST(3, 2000, 9, 11, 18, () -> Ingredient.fromItems(CAItems.AMETHYST.get())),

    TOOL_RUBY(4, 1800, 10, 16, 22, () -> Ingredient.fromItems(CAItems.RUBY.get())),

    TOOL_TIGERS_EYE(3, 1600, 10, 8, 20, () -> Ingredient.fromItems(CAItems.TIGERS_EYE.get())),

    TOOL_CRYSTAL_WOOD(0, 300, 2, 1, 6, () -> Ingredient.fromItems(CABlocks.CRYSTAL_WOOD.get())),

    TOOL_KYANITE(1, 800, 3, 2, 6, () -> Ingredient.fromItems(CABlocks.KYANITE.get())),

    TOOL_PINK_TOURMALINE(2, 1100, 7, 8, 6, () -> Ingredient.fromItems(CAItems.PINK_TOURMALINE_INGOT.get())),

    TOOL_CATS_EYE(3, 1600, 8, 8, 20, () -> Ingredient.fromItems(CAItems.CATS_EYE_INGOT.get())),

    TOOL_ULTIMATE(5, 3000, 15, 36, 64, () -> Ingredient.fromItems(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get())),

    TOOL_NIGHTMARE(3, 1800, 12, 26, 24, () -> Ingredient.fromItems(CAItems.NIGHTMARE_SCALE.get())),

    TOOL_COPPER(1, 150, 4, 2, 6, () -> Ingredient.fromItems(CAItems.COPPER_LUMP.get())),

    TOOL_TIN(1, 180, 5, 3, 8, () -> Ingredient.fromItems(CAItems.TIN_LUMP.get())),

    TOOL_SILVER(2, 450, 7, 4, 10, () -> Ingredient.fromItems(CAItems.SILVER_LUMP.get())),

    TOOL_PLATINUM(3, 1600, 8, 6, 12, () -> Ingredient.fromItems(CAItems.PLATINUM_LUMP.get())),

    WEAPON_BATTLEAXE(5, 1200, 15, 47, 64, () -> Ingredient.fromItems(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get())),

    WEAPON_QUEEN_BATTLEAXE(3, 10000, 15, 663, 96, () -> Ingredient.fromItems(CAItems.QUEEN_SCALE.get())),

    WEAPON_RAY_GUN(0, 50, 0, 0, 0, () -> Ingredient.fromItems(Blocks.REDSTONE_BLOCK)),

    WEAPON_GENERIC(2, 1024, 6, 6, 8, Ingredient::fromItems),

    WEAPON_BIG_HAMMER(2, 2000, 6, 11, 9, Ingredient::fromItems),

    WEAPON_BERTHA(5, 6000, 16, 496, 72, () -> Ingredient.fromItems(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get()));


    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    CAItemTier(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = damage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }
}
