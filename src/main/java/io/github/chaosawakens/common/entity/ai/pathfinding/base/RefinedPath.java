package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class RefinedPath extends Path {
    private final ObjectArrayList<PathPoint> openNodes = new ObjectArrayList<>();
    private final ObjectArrayList<PathPoint> closedNodes = new ObjectArrayList<>();
    

    public RefinedPath(List<PathPoint> nodes, BlockPos targetPos, boolean hasReachedTarget) {
        super(nodes, targetPos, hasReachedTarget);
    }

    @Override
    public boolean notStarted() {
        return super.notStarted();
    }


}