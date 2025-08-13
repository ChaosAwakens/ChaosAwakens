package io.github.chaosawakens.common.worldgen.chunk_gen;

import io.github.chaosawakens.common.worldgen.chunk_gen.chunk.OptimizedNoiseChunk;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class OptimizedChunkGenerator extends NoiseBasedChunkGenerator {

    public OptimizedChunkGenerator(BiomeSource biomeSrc, Holder<NoiseGeneratorSettings> noiseGenSettingsRegHolder) {
        super(biomeSrc, noiseGenSettingsRegHolder);
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(Executor mainThread, Blender chunkBlender, RandomState randState, StructureManager structureManager, ChunkAccess targetChunkAccess) {
        return super.fillFromNoise(mainThread, chunkBlender, randState, structureManager, targetChunkAccess);
    }

    @Override
    public @NotNull ChunkAccess doFill(Blender chunkBlender, StructureManager structureManager, RandomState randState, ChunkAccess targetChunkAccess, int minCellY, int cellYCount) {
        OptimizedNoiseChunk noisechunk = targetChunkAccess.getOrCreateNoiseChunk((p_224255_) -> {
            return this.createNoiseChunk(p_224255_, structureManager, chunkBlender, randState);
        });
        Heightmap heightmap = targetChunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = targetChunkAccess.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
        ChunkPos chunkpos = targetChunkAccess.getPos();
        int i = chunkpos.getMinBlockX();
        int j = chunkpos.getMinBlockZ();
        Aquifer aquifer = noisechunk.aquifer();
        noisechunk.initializeForFirstCellX();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int k = noisechunk.cellWidth();
        int l = noisechunk.cellHeight();
        int i1 = 16 / k;
        int j1 = 16 / k;

        for(int k1 = 0; k1 < i1; ++k1) {
            noisechunk.advanceCellX(k1);

            for(int l1 = 0; l1 < j1; ++l1) {
                int i2 = targetChunkAccess.getSectionsCount() - 1;
                LevelChunkSection levelchunksection = targetChunkAccess.getSection(i2);

                for(int j2 = cellYCount - 1; j2 >= 0; --j2) {
                    noisechunk.selectCellYZ(j2, l1);

                    for(int k2 = l - 1; k2 >= 0; --k2) {
                        int l2 = (minCellY + j2) * l + k2;
                        int i3 = l2 & 15;
                        int j3 = targetChunkAccess.getSectionIndex(l2);
                        if (i2 != j3) {
                            i2 = j3;
                            levelchunksection = targetChunkAccess.getSection(j3);
                        }

                        double d0 = (double)k2 / (double)l;
                        noisechunk.updateForY(l2, d0);

                        for(int k3 = 0; k3 < k; ++k3) {
                            int l3 = i + k1 * k + k3;
                            int i4 = l3 & 15;
                            double d1 = (double)k3 / (double)k;
                            noisechunk.updateForX(l3, d1);

                            for(int j4 = 0; j4 < k; ++j4) {
                                int k4 = j + l1 * k + j4;
                                int l4 = k4 & 15;
                                double d2 = (double)j4 / (double)k;
                                noisechunk.updateForZ(k4, d2);
                                BlockState blockstate = noisechunk.getInterpolatedState();
                                if (blockstate == null) {
                                    blockstate = this.settings.value().defaultBlock();
                                }

                                blockstate = this.debugPreliminarySurfaceLevel(noisechunk, l3, l2, k4, blockstate);
                                if (blockstate != AIR && !SharedConstants.debugVoidTerrain(targetChunkAccess.getPos())) {
                                    levelchunksection.setBlockState(i4, i3, l4, blockstate, false);
                                    heightmap.update(i4, l2, l4, blockstate);
                                    heightmap1.update(i4, l2, l4, blockstate);
                                    if (aquifer.shouldScheduleFluidUpdate() && !blockstate.getFluidState().isEmpty()) {
                                        blockpos$mutableblockpos.set(l3, l2, k4);
                                        targetChunkAccess.markPosForPostprocessing(blockpos$mutableblockpos);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            noisechunk.swapSlices();
        }

        noisechunk.stopInterpolation();
        return targetChunkAccess;
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int targetX, int targetZ, LevelHeightAccessor heightInfo, RandomState rand) {
        return super.getBaseColumn(targetX, targetZ, heightInfo, rand);
    }

    @Override
    public void buildSurface(ChunkAccess targetChunkAccess, WorldGenerationContext worldGenCtx, RandomState randState, StructureManager levelStructureManager, BiomeManager levelBiomeManager, Registry<Biome> biomeRegistry, Blender chunkBlender) {
        super.buildSurface(targetChunkAccess, worldGenCtx, randState, levelStructureManager, levelBiomeManager, biomeRegistry, chunkBlender);
    }

    protected OptionalInt iterateOptimizedNoiseColumn() {
        return OptionalInt.empty();
    }
}
