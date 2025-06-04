package io.github.chaosawakens.util;

import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.ToFloatFunction;

/**
 * Utility class containing part-general part-arbitrary helper/shortcut methods for world/terrain generation.
 */
public final class WorldGenUtil {
    public static final ToFloatFunction<Float> NO_TRANSFORM = ToFloatFunction.IDENTITY;

    private WorldGenUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseOffset(I continentSplineCoord, I erosionSplineCoord, I foldedRidgesSplineCoord) {
        ToFloatFunction<Float> noTransform = NO_TRANSFORM;

        CubicSpline<C, I> erosionOffsetSplineFromDeepWater = TerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.312F, 0.0F, 0.01F, -0.32F, 0.083F, -0.2763F, true, false, noTransform);
        CubicSpline<C, I> erosionOffsetSplineFromShallowWater = TerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.223F, 0.03F, 0.1F, -0.124F, 0.1F, -0.0334F, false, false, noTransform);
        CubicSpline<C, I> erosionOffsetSplineFromLand = TerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.132F, 0.03F, 0.1F, 0.95F, 0.19F, 0.334F, true, true, noTransform);
        CubicSpline<C, I> erosionOffsetSplineFromExtremeLand = TerrainProvider.buildErosionOffsetSpline(erosionSplineCoord, foldedRidgesSplineCoord, -0.18F, 0.26F, 1.4F, 1.5F, 0.3F, 0.011F, true, true, noTransform);

        return CubicSpline.builder(continentSplineCoord, noTransform) // Depth
                // Deep water to shallow water transition
                .addPoint(-1.2F, -0.4F, 0.2F)
                .addPoint(-1.0F, -0.35F, 0.1F)
                .addPoint(-0.8F, -0.32F, 0.0F)
                .addPoint(-0.6F, -0.31F, 0.0F)  // Flat deep water
                
                // Shallow water to shore
                .addPoint(-0.4F, -0.3F, 0.1F)
                .addPoint(-0.2F, -0.28F, 0.0F)
                .addPoint(0.0F, erosionOffsetSplineFromDeepWater)  // Deep water erosion
                
                // Flat lowlands with some variation
                .addPoint(0.1F, -0.15F, 0.0F)
                .addPoint(0.2F, -0.1F, 0.0F)    // Flat lowland
                
                // Transition to highlands with erosion
                .addPoint(0.3F, 0.0F, 0.3F)
                .addPoint(0.4F, erosionOffsetSplineFromShallowWater)  // Shallow water erosion
                .addPoint(0.5F, 0.5F, 0.0F)     // Flat highland
                
                // Extreme terrain with erosion
                .addPoint(0.6F, 0.8F, 0.4F)
                .addPoint(0.7F, erosionOffsetSplineFromLand)  // Land erosion
                .addPoint(0.8F, 1.2F, 0.0F)     // Flat plateau
                
                // Most extreme terrain with extreme erosion
                .addPoint(0.9F, 1.5F, 0.8F)
                .addPoint(1.0F, erosionOffsetSplineFromExtremeLand)  // Extreme land erosion
                .addPoint(1.1F, 1.8F, 0.0F)     // Very high flat
                
                // Drop back down with some variation
                .addPoint(1.3F, 1.0F, -1.0F)
                .addPoint(1.5F, 0.0F, 0.0F)      // Back to water level
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseFactor(I continentSplineCoord, I erosionSplineCoord, I ridgesSplineCoord, I foldedRidgesSplineCoord) {
        ToFloatFunction<Float> noTransform =  NO_TRANSFORM;

        CubicSpline<C, I> erosionFactorSpline = TerrainProvider.getErosionFactor(continentSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 8.65F, true, noTransform);
        CubicSpline<C, I> amplifiedErosionFactorSpline = TerrainProvider.getErosionFactor(continentSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.97F, true, noTransform);
        CubicSpline<C, I> erosionFactorSplineContinued = TerrainProvider.getErosionFactor(continentSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 5.68F, true, noTransform);
        CubicSpline<C, I> erosionFactorSplineFinal = TerrainProvider.getErosionFactor(continentSplineCoord, ridgesSplineCoord, foldedRidgesSplineCoord, 1.19F, true, noTransform);

        return CubicSpline.builder(erosionSplineCoord, NO_TRANSFORM)
                // Start with high erosion factor for deep water
                .addPoint(-0.25F, 6.0F)           // Start with high erosion for deep water
                .addPoint(-0.2F, 5.5F)            // Gradual decrease in erosion
                
                // Transition to medium erosion for shallow water and lowlands
                .addPoint(-0.19F, 5.0F)           // Slightly reduced erosion
                .addPoint(-0.15F, erosionFactorSpline)  // Standard erosion for water
                
                // Increased erosion for mid-elevation (cliffs and slopes)
                .addPoint(-0.12F, 7.0F)          // Ramp up erosion for cliffs
                .addPoint(-0.1F, amplifiedErosionFactorSpline)  // High erosion for steep areas
                
                // Reduced erosion for high flat areas
                .addPoint(-0.05F, 2.5F)           // Lower erosion for high plateaus
                .addPoint(0.05F, 2.0F)            // Maintain low erosion for flat areas
                
                // Transition back to standard erosion
                .addPoint(0.1F, erosionFactorSplineContinued)  // Standard erosion for highlands
                
                // Very low erosion for extreme peaks and plateaus
                .addPoint(0.15F, 1.5F)            // Minimal erosion for highest points
                .addPoint(0.19F, 1.0F)            // Very low erosion for extreme peaks
                .addPoint(0.25F, 0.8F)            // Maintain low erosion beyond
                .build();
    }

    public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> miningParadiseJaggedness(I continentCoord, I erosionCoord, I ridgesCoord, I foldedRidgesCoord) {
        ToFloatFunction<Float> noTransform = NO_TRANSFORM;
        float jaggednessThreshold = 0.71F;

        return CubicSpline.builder(continentCoord, noTransform)
                // Start with smooth terrain for water and lowlands
                .addPoint(-0.2F, 0.0F)  // Smooth underwater terrain
                .addPoint(-0.11F, 0.0F)  // Continue smoothness into shallow water
                
                // Gradual increase in jaggedness for lowlands
                .addPoint(-0.05F, 0.2F)  // Slight roughness
                
                // Sharp increase in jaggedness for cliffs and slopes
                .addPoint(0.0F, 0.8F)   // Very jagged for steep cliffs
                
                // Use custom jaggedness for mid-elevation
                .addPoint(0.1F, TerrainProvider.buildErosionJaggednessSpline(
                    erosionCoord, ridgesCoord, foldedRidgesCoord, 
                    1.2F,  // Increased from 1.0 for more dramatic features
                    0.7F,   // Slightly reduced for some variation
                    0.2F,   // Increased from 0.0 for more detail
                    0.0F,   // Keep at 0.0 for consistency
                    noTransform))
                
                // Transition to extreme jaggedness for high terrain
                .addPoint(0.15F, 1.5F)  // Very high jaggedness for dramatic peaks
                
                // Use final jaggedness configuration for highest elevations
                .addPoint(jaggednessThreshold, TerrainProvider.buildErosionJaggednessSpline(
                    erosionCoord, ridgesCoord, foldedRidgesCoord, 
                    1.5F,   // Increased from 1.0 for more extreme features
                    1.2F,   // Increased from 1.0 for more variation
                    0.8F,   // Increased from 0.0 for more detail
                    0.2F,   // Added for additional complexity
                    noTransform))
                
                // Continue high jaggedness for extreme peaks
                .addPoint(jaggednessThreshold + 0.1F, 1.8F)
                .build();
    }
}
