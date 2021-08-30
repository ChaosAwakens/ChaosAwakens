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

public enum CAArmorMaterial implements IArmorMaterial {

    //Name, Durability multiplier, Damage Reduction multiplier, Damage Reduction, Enchantability, Sound Events, Toughness, Knockback Resistance, Repair Material
    EMERALD("emerald", 35, new int[]{3, 6, 8, 3}, 24, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f, () -> {
        return Ingredient.of(Items.EMERALD);
    }),

    EXPERIENCE("experience", 39, new int[]{4, 7, 9, 5}, 32, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.of(Items.EMERALD);
    }),

    AMETHYST("amethyst", 38, new int[]{3, 7, 8, 4}, 18, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f, () -> {
        return Ingredient.of(CAItems.AMETHYST.get());
    }),

    RUBY("ruby", 37, new int[]{4, 8, 9, 4}, 22, SoundEvents.ARMOR_EQUIP_DIAMOND, 3f, 0f, () -> {
        return Ingredient.of(CAItems.RUBY.get());
    }),

    TIGERS_EYE("tigers_eye", 36, new int[]{4, 7, 8, 4}, 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.of(CAItems.TIGERS_EYE.get());
    }),

    LAPIS("lapis", 22, new int[]{2, 5, 7, 2}, 24, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.75f, 0f, () -> {
        return Ingredient.of(Items.LAPIS_BLOCK);
    }),

    ULTIMATE("ultimate", 48, new int[]{6, 10, 12, 6}, 64, SoundEvents.ARMOR_EQUIP_DIAMOND, 4.5f, 0.125f, () -> {
        return Ingredient.of(CAItems.TITANIUM_INGOT.get(), CAItems.URANIUM_INGOT.get());
    }),

    LAVA_EEL("lava_eel", 33, new int[]{2, 5, 7, 2}, 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, () -> {
        return Ingredient.of(CAItems.LAVA_EEL.get());
    }),

    MOTH_SCALE("moth_scale", 35, new int[]{2, 5, 7, 2}, 16, SoundEvents.ARMOR_EQUIP_GENERIC, 1.75f, 0.05f, () -> {
        return Ingredient.of(CAItems.MOTH_SCALE.get());
    }),

    PEACOCK_FEATHER("peacock_feather", 8, new int[]{2, 4, 5, 2}, 8, SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, () -> {
        return Ingredient.of(CAItems.PEACOCK_FEATHER.get());
    }),

    PINK_TOURMALINE("pink_tourmaline", 24, new int[]{2, 5, 7, 3}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.25f, 0f, () -> {
        return Ingredient.of(CAItems.PINK_TOURMALINE_INGOT.get());
    }),

    CATS_EYE("cats_eye", 36, new int[]{4, 7, 8, 4}, 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.of(CAItems.CATS_EYE_INGOT.get());
    }),

    COPPER("copper", 9, new int[]{1, 3, 4, 1}, 6, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, () -> {
        return Ingredient.of(CAItems.COPPER_LUMP.get());
    }),

    TIN("tin", 12, new int[]{1, 4, 5, 2}, 8, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, () -> {
        return Ingredient.of(CAItems.TIN_LUMP.get());
    }),

    SILVER("silver", 21, new int[]{2, 4, 7, 3}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 1f, 0f, () -> {
        return Ingredient.of(CAItems.SILVER_LUMP.get());
    }),

    PLATINUM("platinum", 35, new int[]{3, 6, 8, 3}, 12, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5f, 0f, () -> {
        return Ingredient.of(CAItems.PLATINUM_LUMP.get());
    }),

    ROYAL_GUARDIAN("royal_guardian", 72, new int[]{8, 12, 14, 8}, 84, SoundEvents.ARMOR_EQUIP_DIAMOND, 7.5f, 0.4f, null),

    QUEEN_SCALE("queen_scale", 68, new int[]{9, 14, 16, 9}, 96, SoundEvents.ARMOR_EQUIP_DIAMOND, 8f, 0.3f, () -> {
        return Ingredient.of(CAItems.QUEEN_SCALE.get());
    });

    private final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durability;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundOnEquip;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairMaterial;

    CAArmorMaterial(String nameIn, int durabilityIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent soundOnEquip, float toughnessIn,
                    float knockbackResistanceIn, Supplier<Ingredient> repairMaterialIn) {

        this.name = ChaosAwakens.MODID + ":" + nameIn;
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
    public int getDurabilityForSlot(EquipmentSlotType slotIn) {

        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.durability;

    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slotIn) {

        return this.damageReductionAmountArray[slotIn.getIndex()];

    }

    @Override
    public int getEnchantmentValue() {

        return this.enchantability;

    }

    @Override
    public SoundEvent getEquipSound() {
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
    public Ingredient getRepairIngredient() {

        return this.repairMaterial.get();

    }

}
