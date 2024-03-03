package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class RefinedPath extends Path {
    private final ObjectArrayList<RefinedPathNode> initialCachedNodes = new ObjectArrayList<>();
    private final ObjectArrayList<RefinedPathNode> openNodes = new ObjectArrayList<>();
    private final ObjectArrayList<RefinedPathNode> closedNodes = new ObjectArrayList<>();
    @Nullable
    private RefinedPathNode curNode;
    private int currentRecomputations = 0;
    private final int maxRecomputationAttempts;
    private boolean isRecomputing = false;
    private boolean conserveOnBreak = false;
    private boolean hasBroken = false;
    private boolean hasReachedTargetPos = false;

    public RefinedPath(BlockPos targetPos, int maxRecomputationAttempts) {
        super(Lists.newArrayList(), targetPos, false);
        this.maxRecomputationAttempts = maxRecomputationAttempts;
    }

    public RefinedPath(BlockPos targetPos) {
        super(Lists.newArrayList(), targetPos, false);
        this.maxRecomputationAttempts = 50;
    }

    public int getPerformedRecomputationAmount() {
        return currentRecomputations;
    }

    protected void setPerformedRecomputationAmount(int currentRecomputations) {
        this.currentRecomputations = currentRecomputations;
    }

    protected void resetPerformedRecomputationAmount() {
        setPerformedRecomputationAmount(0);
    }

    protected void incrementPerformedRecomputationAmount() {
        setPerformedRecomputationAmount(getPerformedRecomputationAmount() + 1);
    }

    public int getMaxRecomputationAttempts() {
        return maxRecomputationAttempts;
    }

    public boolean isRecomputing() {
        return isRecomputing;
    }

    protected void setRecomputing(boolean isRecomputing) {
        this.isRecomputing = isRecomputing;
    }

    public boolean shouldBreak() {
        return conserveOnBreak;
    }

    protected void setConserveOnBreak(boolean conserveOnBreak) {
        this.conserveOnBreak = conserveOnBreak;
    }

    public boolean hasBroken() {
        return hasBroken;
    }

    protected void setHasBroken(boolean hasBroken) {
        this.hasBroken = hasBroken;
    }

    public boolean hasReachedTargetPos() {
        return hasReachedTargetPos;
    }

    protected void setHasReachedTargetPos(boolean hasReachedTargetPos) {
        this.hasReachedTargetPos = hasReachedTargetPos;
    }

    public ImmutableList<RefinedPathNode> getInitialCachedNodes() {
        return ImmutableList.copyOf(initialCachedNodes);
    }

    public ImmutableList<RefinedPathNode> getOpenNodes() {
        return ImmutableList.copyOf(openNodes);
    }

    public ImmutableList<RefinedPathNode> getClosedNodes() {
        return ImmutableList.copyOf(closedNodes);
    }

    @Override
    public boolean notStarted() {
        return super.notStarted();
    }

    public void computePath() {

    }

    public void recomputePath() {

    }

    public void resetPath() {
        resetPerformedRecomputationAmount();
        setRecomputing(false);
        setHasBroken(false);
        setHasReachedTargetPos(false);
        setConserveOnBreak(false);

        initialCachedNodes.clear();
        openNodes.clear();
        closedNodes.clear();
    }

    public double getManhattanDistanceTo(RefinedPath otherPath) {
        if (otherPath == null || equals(otherPath) || curNode == null || otherPath.curNode == null) return 0;

        return Math.abs(curNode.getOriginPos().getX() - otherPath.curNode.getOriginPos().getX()) + Math.abs(curNode.getOriginPos().getY() - otherPath.curNode.getOriginPos().getY()) + Math.abs(curNode.getOriginPos().getZ() - otherPath.curNode.getOriginPos().getZ());
    }

    public double getEuclideanDistanceTo(RefinedPath otherPath) {
        if (otherPath == null || equals(otherPath) || curNode == null || otherPath.curNode == null) return 0;

        return Math.abs(Math.sqrt(otherPath.curNode.getOriginPos().distSqr(curNode.getOriginPos())));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}