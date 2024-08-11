package io.github.chaosawakens.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public final class PredicateUtil {

    private PredicateUtil() {
        throw new IllegalAccessError("Attempted to construct Utility Class!");
    }

    public static Boolean neverSpawnOnBlock(BlockState targetState, BlockGetter targetBlockGetter, BlockPos targetPos, EntityType<?> typeToSpawn) {
        return false;
    }
}
