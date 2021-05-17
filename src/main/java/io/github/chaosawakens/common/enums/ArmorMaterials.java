package io.github.chaosawakens.common.enums;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.registry.CAItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ArmorMaterials implements IArmorMaterial {

    //Name, Durability multiplier, Damage Reduction multiplier, Damage Reduction, Enchantability, Sound Events, Toughness, Knockback Resistance, Repair Material
    EMERALD(ChaosAwakens.MODID + ":emerald", 35, new int[] {3, 6, 8, 3}, 24, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2f, 0f, () -> {
        return Ingredient.fromItems(Items.EMERALD);
    }),

    EXPERIENCE(ChaosAwakens.MODID + ":experience", 39, new int[] {4, 7, 9, 5}, 32, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.fromItems(Items.EMERALD);
    }),

    AMETHYST(ChaosAwakens.MODID + ":amethyst", 39, new int[] {3, 7, 8, 4}, 18, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2f, 0f, () -> {
        return Ingredient.fromItems(CAItems.AMETHYST.get());
    }),

    RUBY(ChaosAwakens.MODID + ":ruby", 37, new int[] {4, 8, 9, 4}, 22, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.fromItems(CAItems.RUBY.get());
    }),

    TIGERS_EYE(ChaosAwakens.MODID + ":tigers_eye", 36, new int[] {4, 7, 8, 4}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.fromItems(CAItems.TIGERS_EYE.get());
    }),

    LAPIS(ChaosAwakens.MODID + ":lapis", 22, new int[] {2, 5, 7, 2}, 24, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.75f, 0f, () -> {
        return Ingredient.fromItems(Items.LAPIS_BLOCK);
    }),

    ULTIMATE(ChaosAwakens.MODID + ":ultimate", 48, new int[] {6, 10, 12, 6}, 64, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3f, 0.025f, () -> {
        return Ingredient.fromItems(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get());
    }),

    LAVA_EEL(ChaosAwakens.MODID + ":lava_eel", 34, new int[] {2, 5, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f, () -> {
        return Ingredient.fromItems(CAItems.LAVA_EEL.get());
    }),

    PEACOCK_FEATHER(ChaosAwakens.MODID + ":peacock_feather", 8, new int[] {2, 4, 5, 2}, 8, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f, () -> {
        return Ingredient.fromItems(CAItems.PEACOCK_FEATHER.get());
    }),

    PINK_TOURMALINE(ChaosAwakens.MODID + ":pink_tourmaline", 24, new int[] {2, 5, 7, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.25f, 0f, () -> {
        return Ingredient.fromItems(CAItems.PINK_TOURMALINE_INGOT.get());
    }),

    CATS_EYE(ChaosAwakens.MODID + ":cats_eye", 36, new int[] {4, 7, 8, 4}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.fromItems(CAItems.CATS_EYE_INGOT.get());
    }),

    COPPER(ChaosAwakens.MODID + ":copper", 9, new int[] {1, 3, 4, 1}, 6, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0f, 0f, () -> {
        return Ingredient.fromItems(CAItems.COPPER_LUMP.get());
    }),

    TIN(ChaosAwakens.MODID + ":tin", 12, new int[] {1, 4, 5, 2}, 8, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0f, 0f, () -> {
        return Ingredient.fromItems(CAItems.TIN_LUMP.get());
    }),

    SILVER(ChaosAwakens.MODID + ":silver", 21, new int[] {2, 4, 7, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1f, 0f, () -> {
        return Ingredient.fromItems(CAItems.SILVER_LUMP.get());
    }),

    PLATINUM(ChaosAwakens.MODID + ":platinum", 35, new int[] {3, 6, 8, 3}, 12, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.fromItems(CAItems.PLATINUM_LUMP.get());
    });

    private final int[] MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};
    private final String name;
    private final int durability;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundOnEquip;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairMaterial;

    ArmorMaterials(String nameIn, int durabilityIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent soundOnEquip, float toughnessIn,
                   float knockbackResistanceIn, Supplier<Ingredient> repairMaterialIn) {

        this.name = nameIn;
        this.durability = durabilityIn;
        this.damageReductionAmountArray = damageReductionAmountArrayIn;
        this.enchantability = enchantabilityIn;
        this.soundOnEquip = soundOnEquip;
        this.toughness = toughnessIn;
        this.knockbackResistance = knockbackResistanceIn;
        this.repairMaterial = repairMaterialIn;

    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {

        return this.name;

    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {

        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.durability;

    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {

        return this.damageReductionAmountArray[slotIn.getIndex()];

    }

    @Override
    public int getEnchantability() {

        return this.enchantability;

    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundOnEquip;
    }

    @Override
    public float getToughness() {

        return this.toughness;

    }

    @Override
    public float getKnockbackResistance() {

        return this.knockbackResistance;

    }

    @Override
    public Ingredient getRepairMaterial() {

        return this.repairMaterial.get();

    }

}
