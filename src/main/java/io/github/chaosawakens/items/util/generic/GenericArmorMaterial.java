package io.github.chaosawakens.items.util.generic;

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
    public float toughness = 0.0f;
    public int enchantability;
    public Ingredient repairIngredient = Ingredient.ofItems(null);
    public String name;
    public float knockbackResistance = 0.0f;
    public SoundEvent soundEvent = SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;

    public GenericArmorMaterial(int durabilityMod, int[] protection, int enchantability, float toughness, Ingredient repairIngredient, String name) {
        this.durabilityMod = durabilityMod;
        this.protection = protection;
        this.toughness = toughness;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
    }

    public GenericArmorMaterial(int durabilityMod, int[] protection, int enchantability, float toughness, Ingredient repairIngredient, String name, float knockbackResistance) {
        this.durabilityMod = durabilityMod;
        this.protection = protection;
        this.toughness = toughness;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
        this.knockbackResistance = knockbackResistance;
    }

    public GenericArmorMaterial(int durabilityMod, int[] protection, int enchantability, float toughness, Ingredient repairIngredient, String name, float knockbackResistance, SoundEvent soundEvent) {
        this.durabilityMod = durabilityMod;
        this.protection = protection;
        this.toughness = toughness;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
        this.knockbackResistance = knockbackResistance;
        this.soundEvent = soundEvent;
    }

    public GenericArmorMaterial(int durabilityMod, int[] protection, int enchantability, float toughness, Ingredient repairIngredient, String name, SoundEvent soundEvent) {
        this.durabilityMod = durabilityMod;
        this.protection = protection;
        this.toughness = toughness;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
        this.name = name;
        this.soundEvent = soundEvent;
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
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }
}
