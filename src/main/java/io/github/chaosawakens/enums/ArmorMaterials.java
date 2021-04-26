package io.github.chaosawakens.enums;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.registry.ModItems;
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
    EMERALD(ChaosAwakens.MODID + ":emerald", 25, new int[] {3, 8, 6, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 20.0f, 1.0f, () -> {
        return Ingredient.fromItems(Items.EMERALD);
    }),

    AMETHYST(ChaosAwakens.MODID + ":amethyst", 25, new int[] {4, 8, 7, 3}, 50, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 20.0f, 1.0f, () -> {
        return Ingredient.fromItems(ModItems.AMETHYST.get());
    }),

    RUBY(ChaosAwakens.MODID + ":ruby", 50, new int[] {4, 9, 8, 4}, 60, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 40.0F, 1.0f, () -> {
        return Ingredient.fromItems(ModItems.RUBY.get());
    }),

    TIGERS_EYE(ChaosAwakens.MODID + ":tigers_eye", 35, new int[] {4, 8, 7, 4}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 25.0f, 1.0f, () -> {
        return Ingredient.fromItems(ModItems.TIGERS_EYE.get());
    }),

    ULTIMATE(ChaosAwakens.MODID + ":ultimate", 35, new int[] {6, 12, 10, 6}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 25.0f, 1.0f, () -> {
        return null;
    });

    private final int[] MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};
    private final String name;
    private final int durability;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private SoundEvent soundOnEquip;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairMaterial;

    private ArmorMaterials(String nameIn, int durabilityIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent soundOnEquip, float toughnessIn,
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