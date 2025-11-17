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
        CubicSpline<C, I> erosionOffsetSplineFromShallowWater = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.15F, 0.0F, 0.0F, 0.1F, 0.0F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromShore = RefactoredTerrainProvider.buildErosionOffsetRidgeSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.1F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromLand = RefactoredTerrainProvider.buildErosionOffsetRidgeSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.1F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.1F, 0.03F, 0.1F, 0.7F, 0.01F, 0.01F, true, true, AMPLIFIED_OFFSET);
        CubicSpline<C, I> erosionOffsetSplineFromMostExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.05F, 0.03F, 0.1F, 1.0F, 0.01F, 0.01F, false, true, AMPLIFIED_OFFSET);

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM) // Depth
                .addPoint(-1.1F, 0.044F)
                .addPoint(-1.02F, -0.2222F)
                .addPoint(-0.51F, -0.2222F)
                .addPoint(-0.44F, -0.12F)
                .addPoint(-0.18F, -0.12F)

                .addPoint(-0.1F, erosionOffsetSplineFromShallowWater)

                .addPoint(0.1F, erosionOffsetSplineFromShore)

                .addPoint(0.23F, erosionOffsetSplineFromLand)

                .addPoint(0.33F, erosionOffsetSplineFromLand)

                .addPoint(0.61F, erosionOffsetSplineFromExtremeLand)

                .addPoint(0.8F, erosionOffsetSplineFromMostExtremeLand)
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseFactor(I continentSplineCoord, I erosionSplineCoord, I ridgesSplineCoord, I foldedRidgesSplineCoord) {
        CubicSpline<C, I> deepOceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.55F, 5.8F, 5.6F, 4.9F,false, NO_TRANSFORM);
        CubicSpline<C, I> oceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.35F, 5.6F, 5.4F, 4.6F,false, NO_TRANSFORM);
        CubicSpline<C, I> beachErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 3.85F,4.7F, 3.95F, 4.6F, false, NO_TRANSFORM);
        CubicSpline<C, I> shoreErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 3.8F,4.7F, 3.9F, 4.6F, false, NO_TRANSFORM);
        CubicSpline<C, I> landErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 3.6F, 4.6F, 3.7F, 4.0F, false, NO_TRANSFORM);
        CubicSpline<C, I> plateauErosionFactorSpline = RefactoredTerrainProvider.getPlateauErosionFactor(erosionSplineCoord, ridgesSplineCoord, 2.8F,  NO_TRANSFORM);
        CubicSpline<C, I> inLandPlateauErosionFactorSpline = RefactoredTerrainProvider.getPlateauErosionFactor(erosionSplineCoord, ridgesSplineCoord, 2.55F,  NO_TRANSFORM);
        CubicSpline<C, I> amplifiedErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 2.25F, 2.1F, 2.15F, 2.0F, true, NO_TRANSFORM);
        CubicSpline<C, I> highAmplifiedErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 2.0F, 2.5F, 1.68F, 1.2F, true, NO_TRANSFORM);
        /*
        CubicSpline<C, I> shoreValleyErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 3.7F,3.7F, 3.6F, 3.6F, false, NO_TRANSFORM);
        CubicSpline<C, I> landValleyErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 2.7F, 3.7F, 3.6F, 2.4F, false, NO_TRANSFORM);
        CubicSpline<C, I> amplifiedValleyErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 1.75F, 3.7F, 3.6F, 1.65F, false, NO_TRANSFORM);
        CubicSpline<C, I> highAmplifiedValleyErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 1.6F, 3.6F, 3.6F, 1.45F, false, NO_TRANSFORM);
        */

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM)
                .addPoint(-0.8F, deepOceanErosionFactorSpline)
                .addPoint(-0.4F, oceanErosionFactorSpline)

                .addPoint(-0.29F, 3.9F)

                .addPoint(-0.18F, 3.9F)
                .addPoint(-0.125F, beachErosionFactorSpline)
                .addPoint(-0.071F, 3.9F)

                .addPoint(-0.07F, 4.125F)
                .addPoint(-0.0675F, 4.15F)
                .addPoint(-0.065F, 4.2F)
                .addPoint(-0.055F, 4.2F)
                .addPoint(-0.0525F, 4.15F)
                .addPoint(-0.03F, 4.125F)

                .addPoint(-0.029F, 3.9F)
                .addPoint(0.075F, shoreErosionFactorSpline)
                .addPoint(0.1F, 3.8F)

                .addPoint(0.15F, 3.75F)
                .addPoint(0.195F, landErosionFactorSpline)
                .addPoint(0.24F, 3.65F)

                .addPoint(0.26F, 3.1F)
                .addPoint(0.3F, plateauErosionFactorSpline)
                .addPoint(0.34F, 2.5F)

                .addPoint(0.359F, 2.6F)
                .addPoint(0.36F, 3.45F)
                .addPoint(0.37F, 3.5F)
                .addPoint(0.38F, 3.7F)
                .addPoint(0.4F, 3.7F)
                .addPoint(0.41F, 3.5F)
                .addPoint(0.42F, 3.45F)

                .addPoint(0.421F, 2.6F)
                .addPoint(0.5F, inLandPlateauErosionFactorSpline)
                .addPoint(0.55F, 2.45F)

                .addPoint(0.57F, 2.35F)
                .addPoint(0.605F, amplifiedErosionFactorSpline)
                .addPoint(0.64F, 2.15F)

                .addPoint(0.659F, 2.25F)
                .addPoint(0.66F, 2.5F)
                .addPoint(0.675F, 3.0F)
                .addPoint(0.685F, 3.7F)
                .addPoint(0.705F, 3.7F)
                .addPoint(0.715F, 3.0F)
                .addPoint(0.73F, 2.5F)
                .addPoint(0.731F, 2.15F)

                .addPoint(0.75F, 2.15F)
                .addPoint(0.785F, highAmplifiedErosionFactorSpline)
                .addPoint(0.82F, 1.95F)

                .addPoint(0.839F, 2.05F)
                .addPoint(0.84F, 2.5F)
                .addPoint(0.855F, 2.9F)
                .addPoint(0.865F, 3.6F)
                .addPoint(0.885F, 3.6F)
                .addPoint(0.895F, 2.9F)
                .addPoint(0.91F, 2.5F)
                .addPoint(0.911F, 2.05F)

                .addPoint(0.95F, 1.95F)
                .addPoint(1.0F, highAmplifiedErosionFactorSpline)
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
