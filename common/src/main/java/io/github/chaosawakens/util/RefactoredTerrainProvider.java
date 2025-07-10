package io.github.chaosawakens.util;

import net.minecraft.util.CubicSpline;
import net.minecraft.util.Mth;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.level.levelgen.NoiseRouterData;

public class RefactoredTerrainProvider {
    // --- Constants for Continentalness Thresholds ---
    // These values define different biome regions based on continentalness noise.
    // Continentalness: How "land-like" or "ocean-like" a point is.
    // Negative values are typically water, positive values are land.
    private static final float DEEP_OCEAN_CONTINENTALNESS = -0.51F;
    private static final float OCEAN_CONTINENTALNESS = -0.4F;
    private static final float PLAINS_CONTINENTALNESS = 0.1F; // Example, not directly used here but indicates scale
    private static final float BEACH_CONTINENTALNESS = -0.15F; // Near-shore areas

    // --- Transformation Functions ---
    // These functions can modify the output of splines, often for "amplified" worlds.
    private static final ToFloatFunction<Float> NO_TRANSFORM = ToFloatFunction.IDENTITY; // No change
    private static final ToFloatFunction<Float> AMPLIFIED_OFFSET = ToFloatFunction.createUnlimited((value) -> value < 0.0F ? value : value * 2.0F); // Doubles positive offsets
    private static final ToFloatFunction<Float> AMPLIFIED_FACTOR = ToFloatFunction.createUnlimited((value) -> 1.25F - 6.25F / (value + 5.0F)); // Non-linear scaling
    private static final ToFloatFunction<Float> AMPLIFIED_JAGGEDNESS = ToFloatFunction.createUnlimited((value) -> value * 2.0F); // Doubles jaggedness

    /**
     * Creates the main terrain height offset spline for the Overworld.
     * This determines the base elevation of the terrain.
     *
     * @param continentalnessInput Function providing continentalness noise.
     * @param erosionInput         Function providing erosion noise.
     * @param weirdnessInput       Function providing weirdness noise (often related to peaks/valleys for specific sub-splines).
     * @param isAmplified          Whether to use amplified world generation settings.
     * @param <C>                  The context type for the ToFloatFunction (e.g., DensityFunction.FunctionContext).
     * @param <I>                  The ToFloatFunction type.
     * @return A CubicSpline defining terrain offset based on continentalness.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> overworldOffset(
            I continentalnessInput, I erosionInput, I weirdnessInput, boolean isAmplified) {
        ToFloatFunction<Float> offsetTransform = isAmplified ? AMPLIFIED_OFFSET : NO_TRANSFORM;

        // Splines for different continentalness regions, influenced by erosion and weirdness.
        // Note: 'weirdnessInput' is often used as a peaks/valleys input for these sub-splines.
        CubicSpline<C, I> beachAndShallowOceanOffset = buildErosionOffsetSpline(erosionInput, weirdnessInput,
                BEACH_CONTINENTALNESS, 0.0F, 0.0F, 0.1F, 0.0F, -0.03F, false, false, offsetTransform);
        CubicSpline<C, I> nearPlainsOffset = buildErosionOffsetSpline(erosionInput, weirdnessInput,
                -0.1F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, offsetTransform);
        CubicSpline<C, I> inlandPlainsOffset = buildErosionOffsetSpline(erosionInput, weirdnessInput,
                -0.1F, 0.03F, 0.1F, 0.7F, 0.01F, -0.03F, true, true, offsetTransform);
        CubicSpline<C, I> highlandsOffset = buildErosionOffsetSpline(erosionInput, weirdnessInput,
                -0.05F, 0.03F, 0.1F, 1.0F, 0.01F, 0.01F, true, true, offsetTransform);

        // Combine the regional splines based on continentalness.
        return CubicSpline.builder(continentalnessInput, offsetTransform)
                // Deep Ocean
                .addPoint(-1.1F, 0.044F) // Far deep ocean
                .addPoint(-1.02F, -0.2222F) // Approaching deep ocean edge
                // Ocean
                .addPoint(DEEP_OCEAN_CONTINENTALNESS, -0.2222F) // Deep ocean to standard ocean transition
                .addPoint(-0.44F, -0.12F) // Standard ocean
                // Shore / Beach
                .addPoint(-0.18F, -0.12F) // Approaching shore
                .addPoint(-0.16F, beachAndShallowOceanOffset) // Beach area start
                .addPoint(BEACH_CONTINENTALNESS, beachAndShallowOceanOffset) // Beach area end
                // Land (Plains, Highlands)
                .addPoint(-0.1F, nearPlainsOffset) // Transition from beach to plains
                .addPoint(0.25F, inlandPlainsOffset) // Inland plains / rolling hills
                .addPoint(1.0F, highlandsOffset) // Highlands / mountains
                .build();
    }

    /**
     * Creates a terrain height scaling factor spline for the Overworld.
     * This factor multiplies the terrain height, affecting how extreme features are.
     *
     * @param continentalnessInput Function providing continentalness noise.
     * @param erosionInput         Function providing erosion noise.
     * @param weirdnessInput       Function providing weirdness noise.
     * @param peaksAndValleysInput Function providing peaks and valleys noise.
     * @param isAmplified          Whether to use amplified world generation settings.
     * @return A CubicSpline defining a terrain scaling factor.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> overworldFactor(
            I continentalnessInput, I erosionInput, I weirdnessInput, I peaksAndValleysInput, boolean isAmplified) {
        ToFloatFunction<Float> factorTransform = isAmplified ? AMPLIFIED_FACTOR : NO_TRANSFORM;

        // Defines how much terrain features are scaled up or down based on continentalness,
        // further modified by erosion, weirdness, and peaks/valleys.
        return CubicSpline.builder(continentalnessInput, NO_TRANSFORM) // Note: NO_TRANSFORM on the main spline output here
                .addPoint(-0.19F, 3.95F) // High factor for certain coastal/shallow areas
                .addPoint(-0.15F, getErosionFactor(erosionInput, weirdnessInput, peaksAndValleysInput, 6.25F, true, NO_TRANSFORM))
                .addPoint(-0.1F, getErosionFactor(erosionInput, weirdnessInput, peaksAndValleysInput, 5.47F, true, factorTransform))
                .addPoint(0.03F, getErosionFactor(erosionInput, weirdnessInput, peaksAndValleysInput, 5.08F, true, factorTransform))
                .addPoint(0.06F, getErosionFactor(erosionInput, weirdnessInput, peaksAndValleysInput, 4.69F, false, factorTransform))
                .build();
    }

    /**
     * Creates a terrain jaggedness spline for the Overworld.
     * This determines how rough or noisy the terrain surface is.
     *
     * @param continentalnessInput Function providing continentalness noise.
     * @param erosionInput         Function providing erosion noise.
     * @param weirdnessInput       Function providing weirdness noise.
     * @param peaksAndValleysInput Function providing peaks and valleys noise.
     * @param isAmplified          Whether to use amplified world generation settings.
     * @return A CubicSpline defining terrain jaggedness.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> overworldJaggedness(
            I continentalnessInput, I erosionInput, I weirdnessInput, I peaksAndValleysInput, boolean isAmplified) {
        ToFloatFunction<Float> jaggednessTransform = isAmplified ? AMPLIFIED_JAGGEDNESS : NO_TRANSFORM;
        float midPointContinentalness = 0.65F; // A continentalness value used as a transition point.

        // Defines the roughness of the terrain.
        return CubicSpline.builder(continentalnessInput, jaggednessTransform)
                .addPoint(-0.11F, 0.0F) // Smooth in/near water
                .addPoint(0.03F, buildErosionJaggednessSpline(erosionInput, weirdnessInput, peaksAndValleysInput,
                        1.0F, 0.5F, 0.0F, 0.0F, jaggednessTransform)) // Jaggedness increases on land
                .addPoint(midPointContinentalness, buildErosionJaggednessSpline(erosionInput, weirdnessInput, peaksAndValleysInput,
                        1.0F, 1.0F, 1.0F, 0.0F, jaggednessTransform)) // Peaks with higher jaggedness
                .build();
    }

    /**
     * Builds a jaggedness spline based on erosion, weirdness, and peaks/valleys.
     *
     * @param erosionInput            Function providing erosion noise.
     * @param weirdnessInput          Function providing weirdness noise.
     * @param peaksAndValleysInput    Function providing peaks and valleys noise.
     * @param valleyWeirdnessMultiplier Multiplier for jaggedness in valleys based on weirdness.
     * @param peakWeirdnessMultiplier   Multiplier for jaggedness on peaks based on weirdness.
     * @param lowErosionFactor        (Unused in original, kept for signature match if called elsewhere)
     * @param highErosionFactor       (Unused in original, kept for signature match if called elsewhere)
     * @param transformFunction       Output transformation function.
     * @return A CubicSpline for jaggedness based on erosion.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildErosionJaggednessSpline(
            I erosionInput, I weirdnessInput, I peaksAndValleysInput,
            float valleyWeirdnessMultiplier, float peakWeirdnessMultiplier,
            float lowErosionFactor, float highErosionFactor, // These seem to be misnamed or unused based on how they are passed down
            ToFloatFunction<Float> transformFunction) {

        float erosionMidPoint = -0.5775F; // Threshold for erosion effects.
        // Jaggedness spline for areas with low erosion.
        CubicSpline<C, I> lowErosionJaggedness = buildRidgeJaggednessSpline(weirdnessInput, peaksAndValleysInput,
                valleyWeirdnessMultiplier, lowErosionFactor, transformFunction); // lowErosionFactor used as valleyWeirdnessMultiplierForRidge
        // Jaggedness spline for areas with high erosion.
        CubicSpline<C, I> highErosionJaggedness = buildRidgeJaggednessSpline(weirdnessInput, peaksAndValleysInput,
                peakWeirdnessMultiplier, highErosionFactor, transformFunction); // highErosionFactor used as peakWeirdnessMultiplierForRidge

        // Combines jaggedness based on erosion level.
        return CubicSpline.builder(erosionInput, transformFunction)
                .addPoint(-1.0F, lowErosionJaggedness) // Very low erosion
                .addPoint(-0.78F, highErosionJaggedness) // Transition to higher erosion
                .addPoint(erosionMidPoint, highErosionJaggedness) // Higher erosion
                .addPoint(-0.375F, 0.0F) // Smooths out at very high erosion (e.g., eroded badlands)
                .build();
    }

    /**
     * Builds a jaggedness spline for ridges, based on weirdness and peaks/valleys.
     *
     * @param weirdnessInput             Function providing weirdness noise.
     * @param peaksAndValleysInput       Function providing peaks and valleys noise.
     * @param valleyWeirdnessMultiplier  Multiplier for jaggedness in valleys.
     * @param peakWeirdnessMultiplier    Multiplier for jaggedness on peaks.
     * @param transformFunction          Output transformation function.
     * @return A CubicSpline for ridge jaggedness.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildRidgeJaggednessSpline(
            I weirdnessInput, I peaksAndValleysInput,
            float valleyWeirdnessMultiplier, float peakWeirdnessMultiplier,
            ToFloatFunction<Float> transformFunction) {

        // peaksAndValleys noise values are mapped by NoiseRouterData.peaksAndValleys to be generally between -1 and 1.
        // These thresholds define points on the spline.
        float valleyNoiseThreshold = NoiseRouterData.peaksAndValleys(0.4F); // A value representing typical valleys
        float peakNoiseThreshold = NoiseRouterData.peaksAndValleys(0.56666666F); // A value representing typical peaks
        float midNoiseThreshold = (valleyNoiseThreshold + peakNoiseThreshold) / 2.0F;

        CubicSpline.Builder<C, I> splineBuilder = CubicSpline.builder(peaksAndValleysInput, transformFunction);
        splineBuilder.addPoint(valleyNoiseThreshold, 0.0F); // Valleys are smoother

        // Add jaggedness based on weirdness if multipliers are positive
        if (peakWeirdnessMultiplier > 0.0F) { // Original code had this as $$3 (peakWeirdnessMultiplier)
            splineBuilder.addPoint(midNoiseThreshold, buildWeirdnessJaggednessSpline(weirdnessInput, peakWeirdnessMultiplier, transformFunction));
        } else {
            splineBuilder.addPoint(midNoiseThreshold, 0.0F);
        }

        if (valleyWeirdnessMultiplier > 0.0F) { // Original code had this as $$2 (valleyWeirdnessMultiplier)
            // This point is at 1.0 which is extreme peak, so valleyWeirdnessMultiplier might be a misnomer here,
            // or it's intended to apply to the "sides" of extreme peaks if weirdness is high.
            splineBuilder.addPoint(1.0F, buildWeirdnessJaggednessSpline(weirdnessInput, valleyWeirdnessMultiplier, transformFunction));
        } else {
            splineBuilder.addPoint(1.0F, 0.0F);
        }

        return splineBuilder.build();
    }

    /**
     * Builds a simple jaggedness spline based on weirdness.
     *
     * @param weirdnessInput    Function providing weirdness noise.
     * @param multiplier        Scales the jaggedness effect.
     * @param transformFunction Output transformation function.
     * @return A CubicSpline for jaggedness based on weirdness.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildWeirdnessJaggednessSpline(
            I weirdnessInput, float multiplier, ToFloatFunction<Float> transformFunction) {
        // Adds a small amount of jaggedness that varies slightly with weirdness.
        float lowWeirdnessJaggedness = 0.63F * multiplier;
        float highWeirdnessJaggedness = 0.3F * multiplier;
        return CubicSpline.builder(weirdnessInput, transformFunction)
                .addPoint(-0.01F, lowWeirdnessJaggedness)
                .addPoint(0.01F, highWeirdnessJaggedness)
                .build();
    }

    /**
     * Creates a scaling factor spline based on erosion, weirdness, and optionally peaks/valleys.
     *
     * @param erosionInput           Function providing erosion noise.
     * @param weirdnessInput         Function providing weirdness noise.
     * @param peaksAndValleysInput   Function providing peaks and valleys noise.
     * @param targetFactor           The base scaling factor to aim for.
     * @param applyPeaksAndValleys   If true, peaks/valleys noise will influence the factor.
     * @param transformFunction      Output transformation function.
     * @return A CubicSpline for a terrain scaling factor.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> getErosionFactor(
            I erosionInput, I weirdnessInput, I peaksAndValleysInput,
            float targetFactor, boolean applyPeaksAndValleys, ToFloatFunction<Float> transformFunction) {

        // Base spline influenced by weirdness
        CubicSpline<C, I> baseWeirdnessSpline = CubicSpline.builder(weirdnessInput, transformFunction)
                .addPoint(-0.4F, 6.3F) // High factor at low weirdness
                .addPoint(0.2F, targetFactor) // Target factor at higher weirdness
                .build();

        CubicSpline.Builder<C, I> erosionSplineBuilder = CubicSpline.builder(erosionInput, transformFunction)
                .addPoint(-0.6F, baseWeirdnessSpline)
                .addPoint(-0.5F, CubicSpline.builder(weirdnessInput, transformFunction) // Nested spline for specific erosion range
                        .addPoint(-0.05F, 6.3F)
                        .addPoint(0.05F, 2.67F).build())
                .addPoint(-0.35F, baseWeirdnessSpline)
                .addPoint(-0.25F, baseWeirdnessSpline)
                .addPoint(-0.1F, CubicSpline.builder(weirdnessInput, transformFunction) // Another nested spline
                        .addPoint(-0.05F, 5.1F)
                        .addPoint(0.05F, 6.3F).build())
                .addPoint(0.03F, baseWeirdnessSpline);

        if (applyPeaksAndValleys) {
            CubicSpline<C, I> pvDependentSplinePart1 = CubicSpline.builder(weirdnessInput, transformFunction)
                    .addPoint(0.0F, targetFactor)
                    .addPoint(0.1F, 0.625F) // Lower factor for specific weirdness
                    .build();
            CubicSpline<C, I> pvDependentSplinePart2 = CubicSpline.builder(peaksAndValleysInput, transformFunction)
                    .addPoint(-0.9F, targetFactor)
                    .addPoint(-0.69F, pvDependentSplinePart1) // Incorporates weirdness effect
                    .build();
            erosionSplineBuilder.addPoint(0.35F, targetFactor)
                    .addPoint(0.45F, pvDependentSplinePart2)
                    .addPoint(0.55F, pvDependentSplinePart2)
                    .addPoint(0.62F, targetFactor);
        } else {
            CubicSpline<C, I> pvIndependentSplinePart1 = CubicSpline.builder(peaksAndValleysInput, transformFunction)
                    .addPoint(-0.7F, baseWeirdnessSpline) // Uses baseWeirdnessSpline when P&V not directly applied
                    .addPoint(-0.15F, 1.37F)
                    .build();
            CubicSpline<C, I> pvIndependentSplinePart2 = CubicSpline.builder(peaksAndValleysInput, transformFunction)
                    .addPoint(0.45F, baseWeirdnessSpline)
                    .addPoint(0.7F, 1.56F)
                    .build();
            erosionSplineBuilder.addPoint(0.05F, pvIndependentSplinePart2)
                    .addPoint(0.4F, pvIndependentSplinePart2)
                    .addPoint(0.45F, pvIndependentSplinePart1)
                    .addPoint(0.55F, pvIndependentSplinePart1)
                    .addPoint(0.58F, targetFactor);
        }

        return erosionSplineBuilder.build();
    }

    /**
     * Calculates the slope between two points (y2-y1)/(x2-x1).
     */
    public static float calculateSlope(float yStart, float yEnd, float xStart, float xEnd) {
        return (yEnd - yStart) / (xEnd - xStart);
    }

    /**
     * Builds a spline representing mountain ridge heights, influenced by peaks/valleys and continentalness.
     *
     * @param peaksAndValleysInput Function providing peaks and valleys noise.
     * @param continentalness      The current continentalness value, affecting mountain height.
     * @param useSharpPeaks        If true, peaks will be sharper.
     * @param transformFunction    Output transformation function.
     * @return A CubicSpline for mountain ridge heights.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildMountainRidgeSplineWithPoints(
            I peaksAndValleysInput, float continentalness, boolean useSharpPeaks, ToFloatFunction<Float> transformFunction) {

        CubicSpline.Builder<C, I> splineBuilder = CubicSpline.builder(peaksAndValleysInput, transformFunction);
        float pvValleyThreshold = -0.7F; // Peaks/Valleys threshold considered as a valley for mountain shaping

        // Heights at extreme peaks/valleys values
        float minPV = -1.0F;
        float heightAtMinPV = mountainContinentalness(minPV, continentalness, pvValleyThreshold);
        float maxPV = 1.0F;
        float heightAtMaxPV = mountainContinentalness(maxPV, continentalness, pvValleyThreshold);

        // Point where mountain continentalness would be zero for the given overall continentalness
        float zeroContinentalnessPVPoint = calculateMountainRidgeZeroContinentalnessPoint(continentalness);

        float midValleyPVThreshold = -0.65F;
        if (midValleyPVThreshold < zeroContinentalnessPVPoint && zeroContinentalnessPVPoint < 1.0F) {
            // Complex case for specific continentalness ranges, creating more detailed mountain shapes
            float heightAtMidValleyPV = mountainContinentalness(midValleyPVThreshold, continentalness, pvValleyThreshold);
            float deeperValleyPVThreshold = -0.75F;
            float heightAtDeeperValleyPV = mountainContinentalness(deeperValleyPVThreshold, continentalness, pvValleyThreshold);

            float slope1 = calculateSlope(heightAtMinPV, heightAtDeeperValleyPV, minPV, deeperValleyPVThreshold);
            splineBuilder.addPoint(minPV, heightAtMinPV, slope1);
            splineBuilder.addPoint(deeperValleyPVThreshold, heightAtDeeperValleyPV);
            splineBuilder.addPoint(midValleyPVThreshold, heightAtMidValleyPV);

            float heightAtZeroContinentalnessPV = mountainContinentalness(zeroContinentalnessPVPoint, continentalness, pvValleyThreshold);
            float slope2 = calculateSlope(heightAtZeroContinentalnessPV, heightAtMaxPV, zeroContinentalnessPVPoint, maxPV);
            float epsilon = 0.01F; // Small offset for derivative continuity
            splineBuilder.addPoint(zeroContinentalnessPVPoint - epsilon, heightAtZeroContinentalnessPV);
            splineBuilder.addPoint(zeroContinentalnessPVPoint, heightAtZeroContinentalnessPV, slope2); // Define slope at this point
            splineBuilder.addPoint(maxPV, heightAtMaxPV, slope2);
        } else {
            // Simpler case for other continentalness ranges
            float overallSlope = calculateSlope(heightAtMinPV, heightAtMaxPV, minPV, maxPV);
            if (useSharpPeaks) {
                splineBuilder.addPoint(minPV, Math.max(0.2F, heightAtMinPV)); // Ensure valleys aren't too deep if sharp peaks
                splineBuilder.addPoint(0.0F, Mth.lerp(0.5F, heightAtMinPV, heightAtMaxPV), overallSlope); // Mid-point with slope
            } else {
                splineBuilder.addPoint(minPV, heightAtMinPV, overallSlope);
            }
            splineBuilder.addPoint(maxPV, heightAtMaxPV, overallSlope);
        }
        return splineBuilder.build();
    }

    /**
     * Calculates a height value for mountains based on peaks/valleys noise and continentalness.
     * This function defines the general shape of mountains before further spline processing.
     *
     * @param pvValue         Current peaks and valleys noise value.
     * @param continentalness Current continentalness noise value.
     * @param pvValleyThreshold Peaks/Valleys threshold below which it's considered a deep valley.
     * @return A height value.
     */
    public static float mountainContinentalness(float pvValue, float continentalness, float pvValleyThreshold) {
        float PV_SCALE_OFFSET = 1.17F; // Magic number for scaling/offsetting PV
        float PV_SCALE_FACTOR = 0.46082947F; // Magic number for scaling PV

        // Scale continentalness to influence mountain height
        float scaledContinentalnessEffect = 1.0F - (1.0F - continentalness) * 0.5F;
        float continentalnessOffset = 0.5F * (1.0F - continentalness);

        // Scale and offset the peaks/valleys value
        float scaledPV = (pvValue + PV_SCALE_OFFSET) * PV_SCALE_FACTOR;
        float baseHeight = scaledPV * scaledContinentalnessEffect - continentalnessOffset;

        // Clamp height: mountains shouldn't dip too low in valleys or below "sea level" on flatter terrain.
        if (pvValue < pvValleyThreshold) { // If in a deep valley (based on pvValue)
            return Math.max(baseHeight, -0.2222F); // Prevent extreme negative heights
        } else {
            return Math.max(baseHeight, 0.0F); // Prevent mountains from going below zero height otherwise
        }
    }

    /**
     * Calculates the peaks/valleys noise value at which {@link #mountainContinentalness} would return zero
     * for a given continentalness. This is essentially the inverse of parts of that function.
     *
     * @param continentalness The continentalness value.
     * @return The peaks/valleys value where mountain height becomes zero.
     */
    public static float calculateMountainRidgeZeroContinentalnessPoint(float continentalness) {
        float PV_SCALE_OFFSET = 1.17F;
        float PV_SCALE_FACTOR = 0.46082947F;

        float scaledContinentalnessEffect = 1.0F - (1.0F - continentalness) * 0.5F;
        float continentalnessOffset = 0.5F * (1.0F - continentalness);

        // Solves for pvValue when mountainContinentalness (baseHeight part) is 0:
        // 0 = (pvValue + PV_SCALE_OFFSET) * PV_SCALE_FACTOR * scaledContinentalnessEffect - continentalnessOffset
        // continentalnessOffset / (PV_SCALE_FACTOR * scaledContinentalnessEffect) = pvValue + PV_SCALE_OFFSET
        // pvValue = continentalnessOffset / (PV_SCALE_FACTOR * scaledContinentalnessEffect) - PV_SCALE_OFFSET
        return continentalnessOffset / (PV_SCALE_FACTOR * scaledContinentalnessEffect) - PV_SCALE_OFFSET;
    }
    /**
     * Builds a spline for erosion-based terrain offsets, incorporating mountain ridges.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildErosionOffsetSpline(
            I erosionFunc, I ridgesFunc,
            float baseOffset, float ridgeParam1, float ridgeParam2, float mountainLerpFactor,
            float ridgeParam3, float ridgeParam4,
            boolean applyAdvancedRidgeLogic, boolean useMaxInMountainRidge,
            ToFloatFunction<Float> valueTransformer) {

        float lerpPoint1 = 0.6F;
        float lerpPoint2 = 0.5F; // Unused in Mth.lerp calls with this name, but used in ridgeSpline calls

        // Splines representing different types of mountain ridges/slopes
        CubicSpline<C, I> mountainRidgeSpline1 = buildMountainRidgeSplineWithPoints(ridgesFunc, Mth.lerp(mountainLerpFactor, lerpPoint1, 1.5F), useMaxInMountainRidge, valueTransformer);
        CubicSpline<C, I> mountainRidgeSpline2 = buildMountainRidgeSplineWithPoints(ridgesFunc, Mth.lerp(mountainLerpFactor, lerpPoint1, 1.0F), useMaxInMountainRidge, valueTransformer);
        CubicSpline<C, I> mountainRidgeSpline3 = buildMountainRidgeSplineWithPoints(ridgesFunc, mountainLerpFactor, useMaxInMountainRidge, valueTransformer);

        // Splines for various ridge shapes based on different parameters
        CubicSpline<C, I> ridgeShape1 = ridgeSpline(ridgesFunc, baseOffset - 0.15F, 0.5F * mountainLerpFactor, Mth.lerp(0.5F, 0.5F, 0.5F) * mountainLerpFactor, 0.5F * mountainLerpFactor, 0.6F * mountainLerpFactor, 0.5F, valueTransformer);
        CubicSpline<C, I> ridgeShape2 = ridgeSpline(ridgesFunc, baseOffset, ridgeParam3 * mountainLerpFactor, ridgeParam1 * mountainLerpFactor, 0.5F * mountainLerpFactor, 0.6F * mountainLerpFactor, 0.5F, valueTransformer);
        CubicSpline<C, I> ridgeShape3 = ridgeSpline(ridgesFunc, baseOffset, ridgeParam3, ridgeParam3, ridgeParam1, ridgeParam2, 0.5F, valueTransformer); // Used twice

        CubicSpline<C, I> flatRidgeArea = CubicSpline.builder(ridgesFunc, valueTransformer)
                .addPoint(-1.0F, baseOffset)
                .addPoint(-0.4F, ridgeShape3) // Re-using ridgeShape3, was $$19
                .addPoint(0.0F, ridgeParam2 + 0.07F)
                .build();

        CubicSpline<C, I> valleyOrLowRidge = ridgeSpline(ridgesFunc, -0.02F, ridgeParam4, ridgeParam4, ridgeParam1, ridgeParam2, 0.0F, valueTransformer);

        CubicSpline.Builder<C, I> builder = CubicSpline.builder(erosionFunc, valueTransformer)
                .addPoint(-0.85F, mountainRidgeSpline1)
                .addPoint(-0.7F, mountainRidgeSpline2)
                .addPoint(-0.4F, mountainRidgeSpline3)
                .addPoint(-0.35F, ridgeShape1)
                .addPoint(-0.1F, ridgeShape2)
                .addPoint(0.2F, ridgeShape3); // Was $$19

        if (applyAdvancedRidgeLogic) {
            builder.addPoint(0.4F, ridgeShape3) // Was $$20 (same as $$19)
                    .addPoint(0.45F, flatRidgeArea)
                    .addPoint(0.55F, flatRidgeArea)
                    .addPoint(0.58F, ridgeShape3); // Was $$20
        }

        builder.addPoint(0.7F, valleyOrLowRidge);
        return builder.build();
    }

    /**
     * Generic helper to build a 5-point spline, often used for defining ridge shapes.
     * Points are at x = -1.0, -0.4, 0.0, 0.4, 1.0.
     */
    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> ridgeSpline(
            I coordinateFunc,
            float yValNegativeMax, float yValNegativeMid, float yValZero, float yValPositiveMid, float yValPositiveMax,
            float derivativeFactor, ToFloatFunction<Float> valueTransformer) {

        // Calculate derivatives for the endpoints
        float derivativeAtNegativeMax = Math.max(0.5F * (yValNegativeMid - yValNegativeMax), derivativeFactor);
        float derivativeAtZero = 5.0F * (yValZero - yValNegativeMid); // Slope into yValZero

        return CubicSpline.builder(coordinateFunc, valueTransformer)
                .addPoint(-1.0F, yValNegativeMax, derivativeAtNegativeMax)
                .addPoint(-0.4F, yValNegativeMid, Math.min(derivativeAtNegativeMax, derivativeAtZero)) // Use min of surrounding slopes for smoother transition
                .addPoint(0.0F, yValZero, derivativeAtZero)
                .addPoint(0.4F, yValPositiveMid, 2.0F * (yValPositiveMid - yValZero)) // Slope out of yValZero
                .addPoint(1.0F, yValPositiveMax, 0.7F * (yValPositiveMax - yValPositiveMid))
                .build();
    }
}