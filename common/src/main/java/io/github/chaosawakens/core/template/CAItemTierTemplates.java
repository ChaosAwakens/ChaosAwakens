package io.github.chaosawakens.core.template;

import io.github.chaosawakens.content.registry.CAItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class CAItemTierTemplates {
    public static final Tier BIG_SWORD = new ExtendedTier(10000, 10.0F, 0.0F, 6, 15, () -> Ingredient.EMPTY);
    public static final Tier ULTIMATE = new ExtendedTier(6000, 14.0F, 4.0F, 5, 15, () -> Ingredient.EMPTY);
    public static final Tier RUBY = new ExtendedTier(3000, 8.5F, 3.0F, 3, 15, () -> Ingredient.of(CAItems.RUBY.get()));
    public static final Tier KUNZITE = new ExtendedTier(2700, 8.0F, 3.0F, 3, 14, () -> Ingredient.of(CAItems.KUNZITE.get()));
    public static final Tier EMERALD = new ExtendedTier(1300, 8.0F, 2.0F, 2, 12, () -> Ingredient.of(Items.EMERALD));

    public static final Tier CATS_EYE = new ExtendedTier(1600, 8.0F, 3.0F, 3, 12, () -> Ingredient.EMPTY);
    public static final Tier PINK_TOURMALINE = new ExtendedTier(1100, 7.5F, 3.0F, 2, 8, () -> Ingredient.EMPTY);
    public static final Tier KYANITE = new ExtendedTier(1100, 6.5F, 2.0F, 2, 8, () -> Ingredient.EMPTY);
    public static final Tier CRYSTALWOOD = new ExtendedTier(119, 2.0F, 0.0F, 0, 15, () -> Ingredient.EMPTY);

    public static final Tier SPECIAL_LOW = new ExtendedTier(100, 2.0F, 0.0F, 3, 15, () -> Ingredient.EMPTY);
    public static final Tier SPECIAL_MID = new ExtendedTier(100, 2.0F, 0.0F, 3, 15, () -> Ingredient.EMPTY);
    public static final Tier SPECIAL_HIGH = new ExtendedTier(100, 2.0F, 0.0F, 3, 15, () -> Ingredient.EMPTY);

    public record ExtendedTier(int uses, float speed, float attackDamageBonus, int level, int enchantmentValue, Supplier<Ingredient> repairIngredient) implements Tier {

        @Override
        public int getUses() {
            return uses;
        }

        @Override
        public float getSpeed() {
            return speed;
        }

        @Override
        public float getAttackDamageBonus() {
            return attackDamageBonus;
        }

        @Override
        public int getLevel() {
            return level;
        }

        @Override
        public int getEnchantmentValue() {
            return enchantmentValue;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return repairIngredient.get();
        }
    }
}
