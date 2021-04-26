package io.github.chaosawakens.enums;

import io.github.chaosawakens.registry.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ToolMaterials implements IItemTier {
    TOOL_EMERALD(2, 2000, 10.0F, 9.0F, 10, () -> Ingredient.fromItems(Items.EMERALD.getItem())),
    TOOL_AMETHYST(8, 5000, 20.0F, 18.0F, 20, () -> Ingredient.fromItems(ModItems.AMETHYST.get())),
    TOOL_RUBY(12, 8000, 20.0F, 30.0F, 30, () -> Ingredient.fromItems(ModItems.RUBY.get())),
    TOOL_TIGERS_EYE(15, 3500, 15.0F, 10.0F, 15, () -> Ingredient.fromItems(ModItems.TIGERS_EYE.get())),
    TOOL_ULTIMATE(15, 13000, 75.0F, 8.0F, 45, () -> null);

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ToolMaterials(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, Supplier<Ingredient> repairMaterial) {
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