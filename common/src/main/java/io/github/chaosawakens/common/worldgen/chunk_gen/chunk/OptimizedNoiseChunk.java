package io.github.chaosawakens.common.worldgen.chunk_gen.chunk;

import com.google.common.collect.ImmutableList;
import io.github.chaosawakens.common.worldgen.aquifer.OptimizedAquifer;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.DensityFunctions.BeardifierMarker;
import java.util.ArrayList;
import java.util.List;

public class OptimizedNoiseChunk extends NoiseChunk {
    // Cache for frequently accessed values
    private static final int CACHE_SIZE = 16;
    private final double[] lerpCacheX = new double[CACHE_SIZE];
    private final double[] lerpCacheY = new double[CACHE_SIZE];
    private final double[] lerpCacheZ = new double[CACHE_SIZE];
    private final ImmutableList<BlockStateFiller> blockStateFillers;

    // Optimized storage for interpolators
    private final OptimizedNoiseInterpolator[] interpolatorArray;
    private final CacheAllInCell[] cellCacheArray;

    // Pre-computed values
    private final int cellVolume;
    private final int cellArea;

    // Optimized Aquifer
    private final OptimizedAquifer optimizedAquifer;

    public OptimizedNoiseChunk(int cellCountXZ, RandomState random, int firstNoiseX, int firstNoiseZ,
                               NoiseSettings noiseSettings, DensityFunctions.BeardifierOrMarker beardifier,
                               NoiseGeneratorSettings noiseGeneratorSettings, OptimizedAquifer.FluidPicker fluidPicker,
                               Blender blender) {
        super(cellCountXZ, random, firstNoiseX, firstNoiseZ, noiseSettings, beardifier,
                noiseGeneratorSettings, fluidPicker, blender);

        // Pre-compute values
        this.cellVolume = this.cellWidth * this.cellHeight * this.cellWidth;
        this.cellArea = this.cellWidth * this.cellWidth;

        // Convert lists to arrays for better performance
        this.interpolatorArray = this.interpolators.toArray(new OptimizedNoiseInterpolator[0]);
        this.cellCacheArray = this.cellCaches.toArray(new CacheAllInCell[0]);

        // Initialize caches
        for (int i = 0; i < CACHE_SIZE; i++) {
            double d = (double) i / (double) CACHE_SIZE;
            this.lerpCacheX[i] = d;
            this.lerpCacheY[i] = d;  // Add this line
            this.lerpCacheZ[i] = d;
        }

        NoiseRouter randomRouter = random.router();
        NoiseRouter mappedRouter = randomRouter.mapAll(this::wrap);
        // Optimize by pre-computing values and reducing object allocations
        this.optimizedAquifer = noiseGeneratorSettings.isAquifersEnabled()
                ? OptimizedAquifer.create(this,
                new ChunkPos(SectionPos.blockToSectionCoord(firstNoiseX), SectionPos.blockToSectionCoord(firstNoiseZ)),
                mappedRouter,
                random.aquiferRandom(),
                noiseSettings.minY(),
                noiseSettings.height(),
                fluidPicker
        )
                : OptimizedAquifer.createDisabled(fluidPicker);

        // Use direct array allocation and population for better performance
        List<BlockStateFiller> fillers = new ArrayList<>(noiseGeneratorSettings.oreVeinsEnabled() ? 2 : 1);

        // Cache the wrapped density function
        DensityFunction cachedDensity = DensityFunctions.cacheAllInCell(
                DensityFunctions.add(mappedRouter.finalDensity(), BeardifierMarker.INSTANCE)
        ).mapAll(this::wrap);

        // Add aquifer filler
        fillers.add(ctx -> this.optimizedAquifer.computeSubstance(ctx, cachedDensity.compute(ctx)));

        // Add ore vein filler if enabled
        if (noiseGeneratorSettings.oreVeinsEnabled()) {
            fillers.add(OreVeinifier.create(
                    mappedRouter.veinToggle(),
                    mappedRouter.veinRidged(),
                    mappedRouter.veinGap(),
                    random.oreRandom()
            ));
        }

        // Convert to immutable list at the end
        this.blockStateFillers = ImmutableList.copyOf(fillers);
    }
    @Override
    public void selectCellYZ(int pY, int pZ) {
        // Optimized version of selectCellYZ
        for (OptimizedNoiseInterpolator interpolator : this.interpolatorArray) {
            interpolator.selectCellYZ(pY, pZ);
        }

        this.fillingCell = true;
        this.cellStartBlockY = (pY + this.cellNoiseMinY) * this.cellHeight;
        this.cellStartBlockZ = (this.firstCellZ + pZ) * this.cellWidth;
        this.arrayInterpolationCounter++;

        // Process cell caches
        for (CacheAllInCell cache : this.cellCacheArray) {
            cache.noiseFiller.fillArray(cache.values, this);
        }

        this.arrayInterpolationCounter++;
        this.fillingCell = false;
    }

    @Override
    public void updateForY(int pCellEndBlockY, double pY) {
        this.inCellY = pCellEndBlockY - this.cellStartBlockY;
        double lerpY = pY * (CACHE_SIZE - 1);
        int y0 = (int)lerpY;
        int y1 = Math.min(y0 + 1, CACHE_SIZE - 1);
        double yFrac = lerpY - y0;
        double lerpedY = Mth.lerp(yFrac, this.lerpCacheY[y0], this.lerpCacheY[y1]);

        for (OptimizedNoiseInterpolator interpolator : this.interpolatorArray) {
            interpolator.updateForY(lerpedY);
        }
    }

    @Override
    public void updateForX(int pCellEndBlockX, double pX) {
        this.inCellX = pCellEndBlockX - this.cellStartBlockX;
        double lerpX = pX * (CACHE_SIZE - 1);
        int x0 = (int)lerpX;
        int x1 = Math.min(x0 + 1, CACHE_SIZE - 1);
        double xFrac = lerpX - x0;
        double lerpedX = Mth.lerp(xFrac, this.lerpCacheX[x0], this.lerpCacheX[x1]);

        for (OptimizedNoiseInterpolator interpolator : this.interpolatorArray) {
            interpolator.updateForX(lerpedX);
        }
    }

    @Override
    public void updateForZ(int pCellEndBlockZ, double pZ) {
        this.inCellZ = pCellEndBlockZ - this.cellStartBlockZ;
        this.interpolationCounter++;

        double lerpZ = pZ * (CACHE_SIZE - 1);
        int z0 = (int)lerpZ;
        int z1 = Math.min(z0 + 1, CACHE_SIZE - 1);
        double zFrac = lerpZ - z0;
        double lerpedZ = Mth.lerp(zFrac, this.lerpCacheZ[z0], this.lerpCacheZ[z1]);

        for (OptimizedNoiseInterpolator interpolator : this.interpolatorArray) {
            interpolator.updateForZ(lerpedZ);
        }
    }

    @Override
    public void fillAllDirectly(double[] pValues, DensityFunction pFunction) {
        int index = 0;
        for (int y = this.cellHeight - 1; y >= 0; --y) {
            this.inCellY = y;
            for (int x = 0; x < this.cellWidth; ++x) {
                this.inCellX = x;
                for (int z = 0; z < this.cellWidth; ++z) {
                    this.inCellZ = z;
                    pValues[index++] = pFunction.compute(this);
                }
            }
        }
    }
    public OptimizedAquifer aquifer() {
        return this.optimizedAquifer;
    }
    // Optimized inner classes
    private class OptimizedNoiseInterpolator extends NoiseChunk.NoiseInterpolator {
        private static final int CACHE_SIZE = 16;
        private final double[] lerpCache = new double[CACHE_SIZE];
        private final java.lang.reflect.Field fillingCellField;
        private boolean interpolating = false;
        // Pre-computed noise values
        private double noise000, noise001, noise010, noise011;
        private double noise100, noise101, noise110, noise111;

        // Intermediate interpolation values
        private double valueXZ00, valueXZ10, valueXZ01, valueXZ11;
        private double valueZ0, valueZ1;

        // Pre-allocated arrays for slices to reduce GC pressure
        private final double[][] slice0;
        private final double[][] slice1;

        OptimizedNoiseInterpolator(NoiseChunk noiseChunk, DensityFunction NoiseFilter) {
            noiseChunk.super(NoiseFilter);
            int size = OptimizedNoiseChunk.this.cellCountXZ + 1;
            int height = OptimizedNoiseChunk.this.cellCountY + 1;

            // Pre-allocate slices with exact sizes
            this.slice0 = new double[size][height];
            this.slice1 = new double[size][height];

            // Initialize arrays to avoid null checks
            for (int i = 0; i < size; i++) {
                this.slice0[i] = new double[height];
                this.slice1[i] = new double[height];
            }

            for (int i = 0; i < CACHE_SIZE; i++) {
                this.lerpCache[i] = (double)i / (double)(CACHE_SIZE - 1);
            }

            // Initialize reflection for accessing fillingCell
            try {
                this.fillingCellField = NoiseChunk.NoiseInterpolator.class.getDeclaredField("fillingCell");
                this.fillingCellField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Failed to access fillingCell field", e);
            }
        }
        @Override
        public void selectCellYZ(int y, int z) {
            // Direct array access with bounds checking removed (handled by parent)
            this.noise000 = this.slice0[z][y];
            this.noise001 = this.slice0[z + 1][y];
            this.noise100 = this.slice1[z][y];
            this.noise101 = this.slice1[z + 1][y];
            this.noise010 = this.slice0[z][y + 1];
            this.noise011 = this.slice0[z + 1][y + 1];
            this.noise110 = this.slice1[z][y + 1];
            this.noise111 = this.slice1[z + 1][y + 1];
        }

        @Override
        public void updateForY(double y) {
            // Single pass Y interpolation
            double y1 = 1.0 - y;
            this.valueXZ00 = this.noise000 * y1 + this.noise010 * y;
            this.valueXZ10 = this.noise100 * y1 + this.noise110 * y;
            this.valueXZ01 = this.noise001 * y1 + this.noise011 * y;
            this.valueXZ11 = this.noise101 * y1 + this.noise111 * y;
        }

        @Override
        public void updateForX(double x) {
            // Single pass X interpolation
            double x1 = 1.0 - x;
            this.valueZ0 = this.valueXZ00 * x1 + this.valueXZ10 * x;
            this.valueZ1 = this.valueXZ01 * x1 + this.valueXZ11 * x;
        }

        @Override
        public void updateForZ(double z) {
            // Single pass Z interpolation
            this.value = this.valueZ0 * (1.0 - z) + this.valueZ1 * z;
        }
        private boolean isFillingCell() {
            try {
                return this.fillingCellField.getBoolean(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to get fillingCell value", e);
            }
        }

        @Override
        public double compute(DensityFunction.FunctionContext pContext) {
            if (pContext != this) {
                return this.noiseFiller.compute(pContext);
            } else if (!this.interpolating) {
                throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
            } else if (isFillingCell()) {
                double x = (double) OptimizedNoiseChunk.this.inCellX / (double) OptimizedNoiseChunk.this.cellWidth;
                double y = (double) OptimizedNoiseChunk.this.inCellY / (double) OptimizedNoiseChunk.this.cellHeight;
                double z = (double) OptimizedNoiseChunk.this.inCellZ / (double) OptimizedNoiseChunk.this.cellWidth;
                return Mth.lerp3(x, y, z,
                        this.noise000, this.noise100, this.noise010,
                        this.noise110, this.noise001, this.noise101,
                        this.noise011, this.noise111);
            } else {
                return this.value;
            }
        }
    }
}