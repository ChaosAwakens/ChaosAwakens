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

        CubicSpline<C, I> erosionOffsetSplineFromDeepOcean = RefactoredTerrainProvider.buildErosionOffsetRidgeSpline(erosionSplineCoord, foldedRidgesSplineCoord,  0.01F, 0.005F, 0.015F, 0.0F, 0.011F, -0.03F, false, false, NO_TRANSFORM);

        CubicSpline<C, I> erosionOffsetSplineFromLake = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord,  0.012F, 0.008F, 0.017F, 0.04F, 0.013F, -0.03F, false, false, NO_TRANSFORM);

        CubicSpline<C, I> erosionOffsetSplineFromLand = RefactoredTerrainProvider.buildErosionOffsetRidgeSpline(erosionSplineCoord, foldedRidgesSplineCoord,  0.018F, 0.013F, 0.023F, 0.04F, 0.019F, -0.03F, false, false, NO_TRANSFORM);

        CubicSpline<C, I> erosionOffsetSplineFromExtremeLand = RefactoredTerrainProvider.buildErosionOffsetRidgeSpline(erosionSplineCoord, foldedRidgesSplineCoord,  0.025F, 0.019F, 0.03F, 0.06F, 0.03F, -0.03F, true, true, NO_TRANSFORM);

        CubicSpline<C, I> erosionOffsetSplineFromMostExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord,  0.03F, 0.022F, 0.035F, 0.1F, 0.035F, -0.03F, true, true, NO_TRANSFORM);

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM) // Depth

                .addPoint(-0.7F, erosionOffsetSplineFromDeepOcean)

                .addPoint(-0.3F, erosionOffsetSplineFromLake)

                .addPoint(0.3F, erosionOffsetSplineFromLand)

                .addPoint(0.55F, erosionOffsetSplineFromExtremeLand)

                .addPoint(0.71F, erosionOffsetSplineFromMostExtremeLand)

                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseFactor(I continentSplineCoord, I erosionSplineCoord, I ridgesSplineCoord, I foldedRidgesSplineCoord) {

        CubicSpline<C, I> deepOceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.08F, 0.07F, 0.09F, 0.1F, false, NO_TRANSFORM);
        CubicSpline<C, I> oceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.1F, 0.08F, 0.11F, 0.12F, false, NO_TRANSFORM);

        CubicSpline<C, I> shallowOceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.11F, 0.09F, 0.12F, 0.13F, false, NO_TRANSFORM);
        CubicSpline<C, I> lakeErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.14F, 0.1F, 0.17F, 0.19F, false, NO_TRANSFORM);

        CubicSpline<C, I> shoreErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.16F, 0.12F, 0.17F, 0.20F, false, NO_TRANSFORM);
        CubicSpline<C, I> beachErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.18F, 0.15F, 0.19F, 0.22F, false, NO_TRANSFORM);
        //125  0.625
        CubicSpline<C, I> landErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.21F, 0.17F, 0.22F, 0.25F, false, NO_TRANSFORM);
        CubicSpline<C, I> inLandErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.33F, 0.28F, 0.31F, 0.37F, false, NO_TRANSFORM);

        //170 0.85
        CubicSpline<C, I> highLandErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.48F, 0.38F, 0.5F, 0.58F, false, NO_TRANSFORM);

        //230
        CubicSpline<C, I> amplifiedErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.755F, 0.605F, 0.725F, 0.855F, true, NO_TRANSFORM);

        //300
        CubicSpline<C, I> highAmplifiedErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 0.93F, 0.78F, 0.85F, 1.1F, true, NO_TRANSFORM);



        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM)
                .addPoint(-1.0F, deepOceanErosionFactorSpline)
                .addPoint(-0.6F, oceanErosionFactorSpline)
                .addPoint(-0.3F, shallowOceanErosionFactorSpline)
                .addPoint(-0.15F, lakeErosionFactorSpline)
                .addPoint(0.0F, beachErosionFactorSpline)
                .addPoint(0.1F, shoreErosionFactorSpline)
                .addPoint(0.18F, landErosionFactorSpline)
                .addPoint(0.3F, inLandErosionFactorSpline)
                .addPoint(0.43F, highLandErosionFactorSpline)
                .addPoint(0.53F, amplifiedErosionFactorSpline)
                .addPoint(0.81F, highAmplifiedErosionFactorSpline)
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
