package io.github.chaosawakens.items.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class GenericArmorMaterial implements ArmorMaterial {
    public int[] durability = new int[] {13, 15, 16, 11};
    public int durabilityMod;
    public int[] protection;
    public int toughness;
    public int enchantability;
    public Item repairIngredient;
    public String name;

    public GenericArmorMaterial(int durabilityMod, int[] protection, int enchantability, int toughness, Item repairIngredient, String name) {
        this.durabilityMod = durabilityMod;
        this.protection = protection;
        this.toughness = toughness;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return this.durability[slot.getEntitySlotId()] * durabilityMod;
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
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
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
