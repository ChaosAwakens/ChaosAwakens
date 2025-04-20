package io.github.chaosawakens.api.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Side-safe variant of {@link net.minecraft.client.renderer.item.ItemPropertyFunction} to avoid accidental classloading
 * via imports or other means. Mainly used for specific item-mapped model predicates.
 */
@FunctionalInterface
public interface WrappedClampedItemPropertyFunction extends WrappedItemPropertyFunction {

    /**
     * Attempts to compute a {@code float} value representing the validity of a given item model predicate to apply
     * different item models to a given {@link ItemStack}.
     *
     * @param targetStack The target {@link ItemStack} to check.
     * @param curLevel The current {@link net.minecraft.client.multiplayer.ClientLevel}, if applicable.
     *                 May be {@code null}. Passed as a {@link Level} here for side-safety.
     * @param livingOwner The entity currently holding the item, if any. May be {@code null}.
     * @param seed A pre-computed pseudorandom seed value used to apply menial rendering shifts to the item model within
     *             the GUI.
     *
     * @return A clamped {@code float} value representing the validity of the model predicate between 0 and 1.
     *
     * @see net.minecraft.client.gui.Gui#renderHotbar(float, GuiGraphics) GUI#renderHotbar for more info on how seed is
     * computed.
     */
    @Override
    default float getValueForStack(ItemStack targetStack, @Nullable Level curLevel, @Nullable LivingEntity livingOwner, int seed) {
        return Mth.clamp(getUnclampedValueForStack(targetStack, curLevel, livingOwner, seed), 0.0F, 1.0F);
    }

    /**
     * Raw unclamped variant of {@link #getValueForStack(ItemStack, Level, LivingEntity, int)}. Attempts to compute a
     * {@code float} value representing the validity of a given item model predicate to apply different item models to
     * a given {@link ItemStack}.
     *
     * @param targetStack The target {@link ItemStack} to check.
     * @param curLevel The current {@link net.minecraft.client.multiplayer.ClientLevel}, if applicable.
     *                 May be {@code null}. Passed as a {@link Level} here for side-safety.
     * @param livingOwner The entity currently holding the item, if any. May be {@code null}.
     * @param seed A pre-computed pseudorandom seed value used to apply menial rendering shifts to the item model within
     *             the GUI.
     *
     * @return An unclamped {@code float} value representing the validity of the model predicate.
     *
     * @see net.minecraft.client.gui.Gui#renderHotbar(float, GuiGraphics) GUI#renderHotbar for more info on how seed is
     * computed.
     */
    float getUnclampedValueForStack(ItemStack targetStack, @Nullable Level curLevel, @Nullable LivingEntity livingOwner, int seed);
}
