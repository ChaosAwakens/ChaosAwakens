package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class RefinedPath extends Path {

    public RefinedPath(List<PathPoint> nodes, BlockPos targetPos, boolean hasReachedTarget) {
        super(nodes, targetPos, hasReachedTarget);
    }

    @Override
    public boolean notStarted() {
        return super.notStarted();
    }
}