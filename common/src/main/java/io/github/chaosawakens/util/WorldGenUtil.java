package io.github.chaosawakens.util;


import net.minecraft.util.CubicSpline;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

/**
 * Utility class containing part-general part-arbitrary helper/shortcut methods for world/terrain generation.
 */
public final class WorldGenUtil {
    public static final ToFloatFunction<Float> NO_TRANSFORM = ToFloatFunction.IDENTITY;
    private static final ToFloatFunction<Float> AMPLIFIED_OFFSET = ToFloatFunction.createUnlimited((value) -> value < 0.0F ? value : value * 2.0F); // Doubles positive offsets
    private static final ToFloatFunction<Float> AMPLIFIED_FACTOR = ToFloatFunction.createUnlimited((value) -> 1.25F - 6.25F / (value + 5.0F)); // Non-linear scaling
    private static final ToFloatFunction<Float> AMPLIFIED_JAGGEDNESS = ToFloatFunction.createUnlimited((value) -> value * 2.0F); // Doubles jaggedness


    private WorldGenUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseOffset(I continentSplineCoord, I erosionSplineCoord, I foldedRidgesSplineCoord) {
        CubicSpline<C, I> erosionOffsetSplineFromDeepWater = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.22F, 0.03F, -0.1F, -0.45F, 0.08F, -0.07F, true, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromShallowWater = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.09F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromShore = RefactoredTerrainProvider.buildErosionOffsetRidgeSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.06F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromLand = RefactoredTerrainProvider.buildErosionOffsetRidgeSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.22F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.02F, 0.03F, 0.1F, 0.7F, 0.01F, 0.01F, true, true, AMPLIFIED_OFFSET);
        CubicSpline<C, I> erosionOffsetSplineFromMostExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.01F, 0.03F, 0.1F, 1.0F, 0.01F, 0.01F, false, true, AMPLIFIED_OFFSET);

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM) // Depth
                .addPoint(-1.1F, erosionOffsetSplineFromDeepWater)
                .addPoint(-0.44F, erosionOffsetSplineFromDeepWater)

                .addPoint(-0.26F, erosionOffsetSplineFromShallowWater)
                .addPoint(-0.05F, erosionOffsetSplineFromShallowWater)

                .addPoint(0.05F, erosionOffsetSplineFromShore)
                .addPoint(0.15F, erosionOffsetSplineFromShore)

                .addPoint(0.25F, erosionOffsetSplineFromLand)
                .addPoint(0.5F, erosionOffsetSplineFromLand)

                .addPoint(0.65F, erosionOffsetSplineFromExtremeLand)
                .addPoint(0.70F, erosionOffsetSplineFromExtremeLand)

                .addPoint(0.8F, erosionOffsetSplineFromMostExtremeLand)
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseFactor(I continentSplineCoord, I erosionSplineCoord, I ridgesSplineCoord, I foldedRidgesSplineCoord) {
        CubicSpline<C, I> deepOceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 6.45F, 6.8F, 6.5F, 4.9F,false, NO_TRANSFORM);
        CubicSpline<C, I> oceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.85F, 6.1F, 5.9F, 4.8F,false, NO_TRANSFORM);
        CubicSpline<C, I> beachErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 3.8F,4.2F, 4.05F, 3.9F, false, NO_TRANSFORM);
        CubicSpline<C, I> shoreErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 3.65F,4.2F, 3.85F, 3.9F, false, NO_TRANSFORM);
        CubicSpline<C, I> landErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 3.25F, 4.2F, 3.25F, 3.3F, false, NO_TRANSFORM);
        CubicSpline<C, I> inLandErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 2.9F, 4.2F, 2.9F, 2.95F, false, NO_TRANSFORM);
        CubicSpline<C, I> plateauErosionFactorSpline = RefactoredTerrainProvider.getPlateauErosionFactor(erosionSplineCoord, ridgesSplineCoord, 2.3F,  NO_TRANSFORM);
        CubicSpline<C, I> amplifiedErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 2.08F, 3.8F, 2.15F, 2.0F, true, NO_TRANSFORM);
        CubicSpline<C, I> highAmplifiedErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 1.58F, 3.5F, 1.68F, 0.88F, true, NO_TRANSFORM);

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM)
                .addPoint(-1.1F, deepOceanErosionFactorSpline)
                .addPoint(-1.02F, deepOceanErosionFactorSpline)
                .addPoint(-0.6F, oceanErosionFactorSpline)
                .addPoint(-0.51F, oceanErosionFactorSpline)

                .addPoint(-0.27F, 4.2F)

                .addPoint(-0.13F, 3.85F)
                .addPoint(-0.1F, beachErosionFactorSpline)
                .addPoint(-0.07F, 3.75F)

                .addPoint(0.03F, 3.7F)
                .addPoint(0.11F, shoreErosionFactorSpline)
                .addPoint(0.18F, 3.6F)

                .addPoint(0.22F, 3.5F)
                .addPoint(0.25F, landErosionFactorSpline)
                .addPoint(0.28F, 2.95F)

                .addPoint(0.32F, 3.15F)
                .addPoint(0.36F, inLandErosionFactorSpline)
                .addPoint(0.4F, 2.75F)

                .addPoint(0.42F, 2.45F)
                .addPoint(0.46F, plateauErosionFactorSpline)
                .addPoint(0.58F, 2.2F)

                .addPoint(0.6F, 1.95F)
                .addPoint(0.7F, amplifiedErosionFactorSpline)
                .addPoint(0.8F, highAmplifiedErosionFactorSpline)
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseJaggedness(I continentCoord, I erosionCoord, I ridgesCoord, I foldedRidgesCoord) {
        float jaggednessThreshold = 0.71F;

        return CubicSpline.builder(continentCoord, NO_TRANSFORM)
                .addPoint(-0.11F, 0.0F)
                .addPoint(0.13F, RefactoredTerrainProvider.buildErosionJaggednessSpline(erosionCoord, ridgesCoord, foldedRidgesCoord,
                        1.0F,
                        0.5F,
                        0.0F,
                        0.0F,
                        NO_TRANSFORM))
                .addPoint(0.65F, RefactoredTerrainProvider.buildErosionJaggednessSpline(erosionCoord, ridgesCoord, foldedRidgesCoord,
                        1.0F,
                        1.0F,
                        1.0F,
                        0.0F,
                        NO_TRANSFORM))
                .build();
    }

    public static DensityFunction noiseGradientDensity(DensityFunction firstDF, DensityFunction secondDF, float gradientMultiplier) {
        DensityFunction productDF = DensityFunctions.mul(firstDF, secondDF);
        return DensityFunctions.mul(DensityFunctions.constant(gradientMultiplier), productDF.quarterNegative());
    }
}
