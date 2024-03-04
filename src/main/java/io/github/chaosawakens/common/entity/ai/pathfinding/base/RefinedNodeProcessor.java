package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.world.IBlockReader;

public class RefinedNodeProcessor extends NodeProcessor {

    public RefinedNodeProcessor() {

    }

    @Override
    public PathPoint getStart() {
        return null;
    }

    @Override
    public FlaggedPathPoint getGoal(double targetX, double targetY, double targetZ) {
        return null;
    }

    @Override
    public int getNeighbors(PathPoint[] p_222859_1_, PathPoint p_222859_2_) {
        return 0;
    }

    @Override
    public PathNodeType getBlockPathType(IBlockReader pBlockaccess, int pX, int pY, int pZ, MobEntity pEntityliving, int pXSize, int pYSize, int pZSize, boolean pCanBreakDoors, boolean pCanEnterDoors) {
        return null;
    }

    @Override
    public PathNodeType getBlockPathType(IBlockReader pLevel, int pX, int pY, int pZ) {
        return null;
    }

    public int getMalusFor(BlockNodeType targetBlockNode) {
        return 0;
    }
}
