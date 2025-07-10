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

        CubicSpline<C, I> erosionOffsetSplineFromShallowWater = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.2F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.13F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionOffsetSplineFromExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.05F, 0.03F, 0.1F, 0.7F, 0.01F, 0.01F, true, true, AMPLIFIED_OFFSET);
        CubicSpline<C, I> erosionOffsetSplineFromMostExtremeLand = RefactoredTerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.05F, 0.03F, 0.1F, 1.0F, 0.01F, 0.01F, true, true, AMPLIFIED_OFFSET);

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM) // Depth
                .addPoint(-1.1F, 0.044F)
                .addPoint(-1.02F, -0.2222F)
                .addPoint(-0.51F, -0.2222F)
                .addPoint(-0.44F, -0.12F)

                .addPoint(-0.36F, erosionOffsetSplineFromShallowWater)
                .addPoint(-0.15F, erosionOffsetSplineFromShallowWater)

                .addPoint(0.05F, erosionOffsetSplineFromLand)
                .addPoint(0.36F, erosionOffsetSplineFromLand)

                .addPoint(0.6F, erosionOffsetSplineFromExtremeLand)
                .addPoint(0.75F, erosionOffsetSplineFromExtremeLand)
                .addPoint(0.9F, erosionOffsetSplineFromMostExtremeLand)
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseFactor(I continentSplineCoord, I erosionSplineCoord, I ridgesSplineCoord, I foldedRidgesSplineCoord) {

        CubicSpline<C, I> oceanErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 6.15F, 6.3F, 5.5F, 4.8F,false, NO_TRANSFORM);
        CubicSpline<C, I> beachErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 4.75F,5.2F, 4.75F, 4.6F, false, NO_TRANSFORM);
        CubicSpline<C, I> landErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 4.6F, 5.1F, 4.45F, 4.4F, false, NO_TRANSFORM);
        CubicSpline<C, I> erosionFactorSpline = RefactoredTerrainProvider.getPlateauErosionFactor(erosionSplineCoord, ridgesSplineCoord, 3.09F,  NO_TRANSFORM);
        CubicSpline<C, I> plateauErosionFactorSpline = RefactoredTerrainProvider.getPlateauErosionFactor(erosionSplineCoord, ridgesSplineCoord, 2.09F,  NO_TRANSFORM);
        CubicSpline<C, I> amplifiedErosionFactorSpline = RefactoredTerrainProvider.getErosionFactor(erosionSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.48F, 5.1F, 4.45F, 2.0F, true, AMPLIFIED_FACTOR);

        return CubicSpline.builder(continentSplineCoord, NO_TRANSFORM)
                .addPoint(-0.6F, 6.25F)
                .addPoint(-0.51F, oceanErosionFactorSpline)

                .addPoint(-0.27F, 5.2F)
                .addPoint(-0.13F, 4.75F)
                .addPoint(-0.1F, beachErosionFactorSpline)
                .addPoint(-0.07F, 4.65F)
                .addPoint(0.07F, 4.5F)

                .addPoint(0.13F, 4.44F)
                .addPoint(0.22F, 4.38F)
                .addPoint(0.25F, landErosionFactorSpline)
                .addPoint(0.28F, 4.28F)

                .addPoint(0.3F, 3.58F)
                .addPoint(0.41F, 3.19F)
                .addPoint(0.44F, erosionFactorSpline)
                .addPoint(0.47F, 2.99F)

                .addPoint(0.48F, 2.46F)
                .addPoint(0.53F, 2.29F)
                .addPoint(0.56F, plateauErosionFactorSpline)
                .addPoint(0.59F, 1.89F)

                .addPoint(0.8F,amplifiedErosionFactorSpline)
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
