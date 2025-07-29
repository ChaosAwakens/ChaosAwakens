package io.github.chaosawakens.common.worldgen.chunk_gen;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceSystem;

public class OptimizedSurfaceSystem extends SurfaceSystem {

    public OptimizedSurfaceSystem(RandomState randState, BlockState defaultBlockState, int seaLevel, PositionalRandomFactory positionalRandomFactory) {
        super(randState, defaultBlockState, seaLevel, positionalRandomFactory);
    }


}
