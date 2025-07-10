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

        CubicSpline<C, I> erosionOffsetSplineFromShallowWater = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.15F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.1F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.05F, 0.03F, 0.1F, 0.9F, 0.01F, 0.01F, true, true, AMPLIFIED_OFFSET);
        CubicSpline<C, I> erosionOffsetSplineFromMostExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.05F, 0.03F, 0.1F, 1.0F, 0.01F, 0.01F, true, true, AMPLIFIED_OFFSET);

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM) // Depth
                .addPoint(-1.1F, 0.044F)
                .addPoint(-1.02F, -0.2222F)
                .addPoint(-0.51F, -0.2222F)
                .addPoint(-0.44F, -0.12F)

                //.addPoint(-0.19F, -0.14F)
                .addPoint(-0.26F, erosionOffsetSplineFromShallowWater)
                .addPoint(-0.15F, erosionOffsetSplineFromShallowWater)
                //.addPoint(-0.12F, -0.14F)

                //.addPoint(0.07F, -0.1F)
                .addPoint(0.15F, erosionOffsetSplineFromLand)
                .addPoint(0.26F, erosionOffsetSplineFromLand)
                //.addPoint(0.28F, -0.1F)

                .addPoint(0.6F, erosionOffsetSplineFromExtremeLand)
                .addPoint(0.9F, erosionOffsetSplineFromExtremeLand)
                .addPoint(1.0F, erosionOffsetSplineFromMostExtremeLand)
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseFactor(I continentSplineCoord, I erosionSplineCoord, I ridgesSplineCoord, I foldedRidgesSplineCoord) {

        CubicSpline<C, I> oceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 6.15F, false, NO_TRANSFORM);
        CubicSpline<C, I> beachErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.47F, false, NO_TRANSFORM);
        CubicSpline<C, I> landErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.38F, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.08F, true, NO_TRANSFORM);
        CubicSpline<C, I> amplifiedErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 4.69F, false, AMPLIFIED_FACTOR);

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM)
                .addPoint(-0.6F, 6.25F)
                .addPoint(-0.51F, oceanErosionFactorSpline)

                .addPoint(-0.1F, beachErosionFactorSpline)
                //.addPoint(-0.07F, 2.67F)

                //.addPoint(0.07F, 2.38F)
                .addPoint(0.1F, landErosionFactorSpline)
                //.addPoint(0.13F, 2.38F)

                //.addPoint(0.27F, 2.08F)
                .addPoint(0.3F, erosionFactorSpline)
                //.addPoint(0.33F, 2.08F)

                //.addPoint(0.57F, 1.69F)
                .addPoint(0.7F,amplifiedErosionFactorSpline)
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseJaggedness(I continentCoord, I erosionCoord, I ridgesCoord, I foldedRidgesCoord) {
        float jaggednessThreshold = 0.71F;

        return CubicSpline.builder(continentCoord, NO_TRANSFORM)
                .addPoint(-0.11F, 0.0F)
                .addPoint(0.03F, RefactoredTerrainProvider.buildErosionJaggednessSpline(erosionCoord, ridgesCoord, foldedRidgesCoord,
                        1.0F,
                        0.5F,
                        0.0F,
                        0.0F
                        , NO_TRANSFORM))
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
