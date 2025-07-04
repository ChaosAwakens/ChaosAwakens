package io.github.chaosawakens.common.worldgen.chunk_gen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
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
        return super.doFill(chunkBlender, structureManager, randState, targetChunkAccess, minCellY, cellYCount);
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int targetX, int targetZ, LevelHeightAccessor heightInfo, RandomState rand) {
        return super.getBaseColumn(targetX, targetZ, heightInfo, rand);
    }

    protected OptionalInt iterateOptimizedNoiseColumn() {

        return OptionalInt.empty();
    }
}
