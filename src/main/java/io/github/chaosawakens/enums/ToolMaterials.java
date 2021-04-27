package io.github.chaosawakens.enums;

import io.github.chaosawakens.registry.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ToolMaterials implements IItemTier {
    TOOL_EMERALD(3, 2000, 10, 6, 24, () -> Ingredient.fromItems(Items.EMERALD.getItem())),
    TOOL_AMETHYST(3, 2000, 11, 11, 18, () -> Ingredient.fromItems(ModItems.AMETHYST.get())),
    TOOL_RUBY(4, 1800, 11, 16, 22, () -> Ingredient.fromItems(ModItems.RUBY.get())),
    TOOL_TIGERS_EYE(3, 600, 11, 8, 20, () -> Ingredient.fromItems(ModItems.TIGERS_EYE.get())),
    TOOL_ULTIMATE(10, 3000, 15, 36, 64, () -> Ingredient.fromItems(ModItems.TITANIUM_INGOT.get(), ModItems.URANIUM_INGOT.get())),
    TOOL_NIGHTMARE(3, 1800, 12, 26, 24, () -> Ingredient.fromItems(ModItems.TITANIUM_INGOT.get()));

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
