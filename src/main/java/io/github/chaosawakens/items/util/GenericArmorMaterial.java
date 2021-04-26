package io.github.chaosawakens.items.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class GenericArmorMaterial implements ArmorMaterial {
    private int[] durability;
    private int[] protection;
    private int toughness;
    private int enchantability;
    private Item repairIngredient;
    private String name;

    public GenericArmorMaterial(int[] durability, int[] protection, int toughness, int enchantability, Item repairIngredient, String name) {
        this.durability = durability;
        this.protection = protection;
        this.toughness = toughness;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return this.protection[slot.getEntitySlotId()] * toughness;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return this.protection[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(this.repairIngredient);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return (float) this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
