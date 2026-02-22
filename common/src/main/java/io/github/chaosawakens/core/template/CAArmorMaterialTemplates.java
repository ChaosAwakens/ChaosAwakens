package io.github.chaosawakens.core.template;

import io.github.chaosawakens.CAConstants;
import io.github.chaosawakens.content.registry.CAItems;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public final class CAArmorMaterialTemplates {
    public static final ExtendedArmorMaterial ULTIMATE = new ExtendedArmorMaterial("ultimate", 384, Map.of(
            ArmorItem.Type.BOOTS, 6,
            ArmorItem.Type.LEGGINGS, 10,
            ArmorItem.Type.CHESTPLATE, 12,
            ArmorItem.Type.HELMET, 6
    ), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.5F, 0.125F, () -> Ingredient.EMPTY);

    public static final ExtendedArmorMaterial RUBY = new ExtendedArmorMaterial("ruby", 58, Map.of(
            ArmorItem.Type.BOOTS, 4,
            ArmorItem.Type.LEGGINGS, 8,
            ArmorItem.Type.CHESTPLATE, 9,
            ArmorItem.Type.HELMET, 4
    ), 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 3F, 0.0F, () -> Ingredient.of(CAItems.RUBY.get()));
    public static final ExtendedArmorMaterial KUNZITE = new ExtendedArmorMaterial("kunzite", 56, Map.of(
            ArmorItem.Type.BOOTS, 3,
            ArmorItem.Type.LEGGINGS, 7,
            ArmorItem.Type.CHESTPLATE, 8,
            ArmorItem.Type.HELMET, 4
    ), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2F, 0.0F, () -> Ingredient.of(CAItems.KUNZITE.get()));

    public static final ExtendedArmorMaterial EXPERIENCE = new ExtendedArmorMaterial("experience", 56, Map.of(
            ArmorItem.Type.BOOTS, 4,
            ArmorItem.Type.LEGGINGS, 8,
            ArmorItem.Type.CHESTPLATE, 9,
            ArmorItem.Type.HELMET, 5
    ), 18, SoundEvents.ARMOR_EQUIP_GOLD, 2.5F, 0.0F, () -> Ingredient.EMPTY);
    public static final ExtendedArmorMaterial EMERALD = new ExtendedArmorMaterial("emerald", 69, Map.of(
            ArmorItem.Type.BOOTS, 2,
            ArmorItem.Type.LEGGINGS, 5,
            ArmorItem.Type.CHESTPLATE, 6,
            ArmorItem.Type.HELMET, 2
    ), 10, SoundEvents.ARMOR_EQUIP_IRON, 0F, 0.0F, () -> Ingredient.of(Items.EMERALD));

    public static final ExtendedArmorMaterial PINK_TOURMALINE = new ExtendedArmorMaterial("pink_tourmaline", 24, Map.of(
            ArmorItem.Type.BOOTS, 2,
            ArmorItem.Type.LEGGINGS, 5,
            ArmorItem.Type.CHESTPLATE, 7,
            ArmorItem.Type.HELMET, 3
    ), 10, SoundEvents.ARMOR_EQUIP_IRON, 1.25F, 0.0F, () -> Ingredient.EMPTY);
    public static final ExtendedArmorMaterial CATS_EYE = new ExtendedArmorMaterial("cats_eye", 24, Map.of(
            ArmorItem.Type.BOOTS, 3,
            ArmorItem.Type.LEGGINGS, 7,
            ArmorItem.Type.CHESTPLATE, 8,
            ArmorItem.Type.HELMET, 4
    ), 14, SoundEvents.ARMOR_EQUIP_IRON, 2.5F, 0.0F, () -> Ingredient.EMPTY);

    public static final ExtendedArmorMaterial LAPIS = new ExtendedArmorMaterial("lapis", 24, Map.of(
            ArmorItem.Type.BOOTS, 2,
            ArmorItem.Type.LEGGINGS, 5,
            ArmorItem.Type.CHESTPLATE, 7,
            ArmorItem.Type.HELMET, 3
    ), 10, SoundEvents.ARMOR_EQUIP_IRON, 1.75F, 0.0F, () -> Ingredient.of(Items.LAPIS_LAZULI));

    public static final ExtendedArmorMaterial LAVA_EEL = new ExtendedArmorMaterial("lava_eel", 33, Map.of(
            ArmorItem.Type.BOOTS, 2,
            ArmorItem.Type.LEGGINGS, 5,
            ArmorItem.Type.CHESTPLATE, 7,
            ArmorItem.Type.HELMET, 2
    ), 12, SoundEvents.ARMOR_EQUIP_IRON, 0F, 0.0F, () -> Ingredient.EMPTY);

    public static final ExtendedArmorMaterial MOBZILLA_SCALE = new ExtendedArmorMaterial("mobzilla_scale", 1200, Map.of(
            ArmorItem.Type.BOOTS, 7,
            ArmorItem.Type.LEGGINGS, 11,
            ArmorItem.Type.CHESTPLATE, 13,
            ArmorItem.Type.HELMET, 7
    ), 20, SoundEvents.ARMOR_EQUIP_IRON, 6F, 0.2F, () -> Ingredient.EMPTY);
    public static final ExtendedArmorMaterial MOTH_SCALE = new ExtendedArmorMaterial("moth_scale", 226, Map.of(
            ArmorItem.Type.BOOTS, 4,
            ArmorItem.Type.LEGGINGS, 8,
            ArmorItem.Type.CHESTPLATE, 9,
            ArmorItem.Type.HELMET, 4
    ), 15, SoundEvents.ARMOR_EQUIP_IRON, 3F, 0.05F, () -> Ingredient.EMPTY);

    public static final ExtendedArmorMaterial PEACOCK_FEATHER = new ExtendedArmorMaterial("peacock_feather", 8, Map.of(
            ArmorItem.Type.BOOTS, 2,
            ArmorItem.Type.LEGGINGS, 4,
            ArmorItem.Type.CHESTPLATE, 5,
            ArmorItem.Type.HELMET, 2
    ), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0F, 0.0F, () -> Ingredient.EMPTY);

    public static final ExtendedArmorMaterial QUEEN_SCALE = new ExtendedArmorMaterial("queen_scale", 1440, Map.of(
            ArmorItem.Type.BOOTS, 9,
            ArmorItem.Type.LEGGINGS, 14,
            ArmorItem.Type.CHESTPLATE, 16,
            ArmorItem.Type.HELMET, 9
    ), 25, SoundEvents.ARMOR_EQUIP_LEATHER, 8F, 0.3F, () -> Ingredient.EMPTY);
    public static final ExtendedArmorMaterial ROYAL_GUARDIAN = new ExtendedArmorMaterial("royal_guardian", 1380, Map.of(
            ArmorItem.Type.BOOTS, 8,
            ArmorItem.Type.LEGGINGS, 12,
            ArmorItem.Type.CHESTPLATE, 14,
            ArmorItem.Type.HELMET, 8
    ), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 7.5F, 0.4F, () -> Ingredient.EMPTY);

    public record ExtendedArmorMaterial(ResourceLocation name, int durabilityMultiplier, Map<ArmorItem.Type, Integer> protectionFunctionForType, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) implements ArmorMaterial {
        private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (armorTypes) -> {
            armorTypes.put(ArmorItem.Type.BOOTS, 13);
            armorTypes.put(ArmorItem.Type.LEGGINGS, 15);
            armorTypes.put(ArmorItem.Type.CHESTPLATE, 16);
            armorTypes.put(ArmorItem.Type.HELMET, 11);
        });

        public ExtendedArmorMaterial(String name, int durabilityMultiplier, Map<ArmorItem.Type, Integer> protectionFunctionForType, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
            this(CAConstants.prefix(name), durabilityMultiplier, protectionFunctionForType, enchantmentValue, sound, toughness, knockbackResistance, repairIngredient);
        }

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            return durabilityMultiplier * HEALTH_FUNCTION_FOR_TYPE.get(type);
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return protectionFunctionForType.get(type);
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public @NotNull SoundEvent getEquipSound() {
            return sound;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }

        @Override
        public @NotNull String getName() {
            return name.toString();
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }
}
