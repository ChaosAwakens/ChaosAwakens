package io.github.chaosawakens.api.ai.pathnav.path;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;

public class PathNode { // TODO Clearance, danger
    protected final double length;
    protected final double width;
    protected final double height;
    protected final double x;
    protected final double y;
    protected final double z;
    protected final List<BlockState> containedBlockStates = new ObjectArrayList<>();
    protected final List<FluidState> containedFluidStates = new ObjectArrayList<>();
    protected final List<PathNode> neighbors = new ObjectArrayList<>();
    protected double g;
    protected double h;
    protected double f;
    protected double c;
    protected double d;
    protected double t;
    protected boolean reached = false;

    public PathNode(double length, double width, double height, double x, double y, double z) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PathNode(AABB sizeBox, BlockPos nodePos) {
        this(sizeBox.getXsize(), sizeBox.getYsize(), sizeBox.getZsize(), nodePos.getX(), nodePos.getY(), nodePos.getZ());
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public List<BlockState> getContainedBlockStates() {
        return ImmutableList.copyOf(containedBlockStates);
    }

    public List<FluidState> getContainedFluidStates() {
        return ImmutableList.copyOf(containedFluidStates);
    }

    public List<PathNode> getNeighbors() {
        return ImmutableList.copyOf(neighbors);
    }

    public double getCostFromStart() {
        return g;
    }

    public double getEstimatedCostToEnd() {
        return h;
    }

    public double getTotalHeuristicCost() {
        return f;
    }

    public double getClearance() {
        return c;
    }

    public double getDanger() {
        return d;
    }

    public double getTotalCost() {
        return t;
    }

    public boolean isReached() {
        return reached;
    }

    public void setReached(boolean reached) {
        this.reached = reached;
    }

    public void addContainedState(BlockState state) {
        this.containedBlockStates.add(state);
    }

    public void addNeighbor(PathNode node) {
        this.neighbors.add(node);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PathNode other = (PathNode) obj;

        return Double.compare(this.c, other.c) == 0 &&
                Double.compare(this.d, other.d) == 0 &&
                Double.compare(this.f, other.f) == 0 &&
                Double.compare(this.g, other.g) == 0 &&
                Double.compare(this.height, other.height) == 0 &&
                Double.compare(this.h, other.h) == 0 &&
                Double.compare(this.length, other.length) == 0 &&
                Double.compare(this.t, other.t) == 0 &&
                Double.compare(this.width, other.width) == 0 &&
                Double.compare(this.x, other.x) == 0 &&
                Double.compare(this.y, other.y) == 0 &&
                Double.compare(this.z, other.z) == 0 &&
                this.reached == other.reached &&
                Objects.equals(this.containedBlockStates, other.containedBlockStates) &&
                Objects.equals(this.containedFluidStates, other.containedFluidStates) &&
                Objects.equals(this.neighbors, other.neighbors);
    }
}
