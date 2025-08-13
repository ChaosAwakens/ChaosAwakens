package io.github.chaosawakens.util;

import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.stream.Stream;

public final class NoiseRouterUtil {

    private NoiseRouterUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    /**
     * Creates a density function that interpolates between two functions within a specified Y range,
     * and returns a constant value outside that range.
     *
     * @param inputDensity The primary density function to interpolate
     * @param interpolationRange The density function used for interpolation
     * @param minY The minimum Y level where interpolation starts
     * @param maxY The maximum Y level where interpolation ends
     * @param defaultValue The constant value to use outside the Y range
     * @return A new density function with the specified interpolation behavior
     */
    public static DensityFunction createYLimitedInterpolation(
            DensityFunction inputDensity,
            DensityFunction interpolationRange,
            int minY,
            int maxY,
            int defaultValue) {
        // Create a range choice that selects between the interpolated function and a constant
        // The interpolation happens between minY and maxY (inclusive)
        return DensityFunctions.interpolated(
                DensityFunctions.rangeChoice(
                        inputDensity,
                        (double) minY,
                        (double) (maxY + 1),  // Add 1 to make maxY inclusive
                        interpolationRange,
                        DensityFunctions.constant((double) defaultValue)
                )
        );
    }

    /**
     * Refactored variant of {@link NoiseRouterData#slide(DensityFunction, int, int, int, int, double, int, int, double)}.
     * Takes an input density and smoothly transitions it towards a target value at the top and bottom of the world,
     * effectively clamping the terrain shape.
     *
     * @param inputDensity The density function defining the base terrain shape.
     * @param minWorldY The minimum Y level of the dimension (e.g., -64).
     * @param worldHeight The total height of the dimension (e.g., 384).
     * @param topSlideStartFromTop How many blocks from the world's ceiling the top slide should begin.
     * @param topSlideSize The vertical distance over which the top slide occurs.
     * @param topSlideTarget The density value to slide towards at the top. (Negative for air).
     * @param bottomSlideStartFromBottom How many blocks from the world's floor the bottom slide should begin.
     * @param bottomSlideSize The vertical distance over which the bottom slide occurs.
     * @param bottomSlideTarget The density value to slide towards at the bottom. (Positive for solid).
     *
     * @return A new density function with the sliding behavior applied.
     */
    public static DensityFunction createTerrainSlide(DensityFunction inputDensity, int minWorldY, int worldHeight, int topSlideStartFromTop, int topSlideSize, double topSlideTarget, int bottomSlideStartFromBottom, int bottomSlideSize, double bottomSlideTarget) {
        // --- Top Slide Calculation ---
        // Defines the Y-range where the terrain will be slid towards the topSlideTarget.
        int topSlideStartY = minWorldY + worldHeight - topSlideStartFromTop;
        int topSlideEndY = topSlideStartY - topSlideSize;

        // Creates a vertical gradient that smoothly transitions from 1.0 (at topSlideEndY) to 0.0 (at topSlideStartY).
        // This gradient acts as the 'alpha' for the interpolation.
        DensityFunction topSlideAlpha = DensityFunctions.yClampedGradient(topSlideEndY, topSlideStartY, 1.0, 0.0);

        // Lerps from the original inputDensity to the topSlideTarget.
        // When topSlideAlpha is 1.0 (at lower altitudes), the result is 100% topSlideTarget.
        // When topSlideAlpha is 0.0 (at higher altitudes), the result is 100% inputDensity.
        // The vanilla lerp is (from, to, alpha) -> from * (1-alpha) + to * alpha.
        // Here, we are lerping FROM inputDensity TO topSlideTarget.
        DensityFunction densityWithTopSlide = DensityFunctions.lerp(topSlideAlpha, topSlideTarget, inputDensity);

        // --- Bottom Slide Calculation ---
        // Defines the Y-range where the terrain will be slid towards the bottomSlideTarget.
        int bottomSlideStartY = minWorldY + bottomSlideStartFromBottom;
        int bottomSlideEndY = bottomSlideStartY + bottomSlideSize;

        // Creates a vertical gradient that smoothly transitions from 0.0 (at bottomSlideStartY) to 1.0 (at bottomSlideEndY).
        DensityFunction bottomSlideAlpha = DensityFunctions.yClampedGradient(bottomSlideStartY, bottomSlideEndY, 0.0, 1.0);

        // Interpolates from the result of the top slide to the bottomSlideTarget.
        // When bottomSlideAlpha is 1.0 (at lower altitudes), the result is 100% bottomSlideTarget.
        // When bottomSlideAlpha is 0.0 (at higher altitudes), the result is untouched from the previous step.
        DensityFunction finalDensity = DensityFunctions.lerp(bottomSlideAlpha, bottomSlideTarget, densityWithTopSlide);

        return finalDensity;
    }
}
