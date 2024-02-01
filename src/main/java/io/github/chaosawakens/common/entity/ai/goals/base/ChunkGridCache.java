package io.github.chaosawakens.common.entity.ai.goals.base;

import net.minecraft.world.chunk.Chunk;

public class ChunkGridCache {
    private final Chunk targetChunk;

    public ChunkGridCache(Chunk targetChunk) {
        this.targetChunk = targetChunk;
    }
}
