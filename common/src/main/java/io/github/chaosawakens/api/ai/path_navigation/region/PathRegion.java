package io.github.chaosawakens.api.ai.path_navigation.region;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;

import java.util.List;

public interface PathRegion {

    ChunkAccess getCenterChunk();
    ChunkSource getChunkSource();

    List<ChunkAccess> getChunks();
}
