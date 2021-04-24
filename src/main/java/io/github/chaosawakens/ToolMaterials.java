package io.github.chaosawakens;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ToolMaterials implements IItemTier {
    TOOL_EMERALD(2, 2000, 10.0F, 9.0F, 10, () -> Ingredient.fromItems(Items.EMERALD.getItem()));

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

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }
}