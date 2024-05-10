package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RefinedPathNode implements Comparable<RefinedPathNode> {
    protected final ObjectArrayList<BlockPos> encompassedPositions = new ObjectArrayList<>(1);
    protected final World curWorld;
    protected BlockPos originPos;
    protected double xSize = 1.0D;
    protected double ySize = 1.0D;
    protected double zSize = 1.0D;
    protected double f;
    protected double g;
    protected double h;
    protected double dangerFactor;
    protected double totalScore;

    public RefinedPathNode(World curWorld, BlockPos originPos) {
        this.curWorld = Objects.requireNonNull(curWorld);
        this.originPos = Objects.requireNonNull(originPos);

        encompassedPositions.add(originPos);
    }

    public RefinedPathNode setXSize(double xSize) {
        return setSize(xSize, ySize, zSize);
    }

    public RefinedPathNode setYSize(double ySize) {
        return setSize(xSize, ySize, zSize);
    }

    public RefinedPathNode setZSize(double zSize) {
        return setSize(xSize, ySize, zSize);
    }

    public RefinedPathNode setSize(double xSize, double ySize, double zSize) {
        this.xSize = Math.round(xSize);
        this.ySize = Math.round(ySize);
        this.zSize = Math.round(zSize);

        BlockPos.Mutable encompassedPositionCheckerPos = new BlockPos.Mutable(originPos.getX(), originPos.getY(), originPos.getZ());

        encompassedPositions.clear(); // In case it was scaled-down
        encompassedPositions.add(originPos);

        for (double curXSize = -xSize; curXSize <= xSize; curXSize++) { // Scale by diameter, not radius, using the provided radius
            for (double curYSize = -ySize; curYSize <= ySize; curYSize++) {
                for (double curZSize = -zSize; curZSize <= zSize; curZSize++) {
                    encompassedPositionCheckerPos.set(originPos.getX() + curXSize, originPos.getY() + curYSize, originPos.getZ() + curZSize);
                    BlockPos cachedEncompassedCheckerPos = encompassedPositionCheckerPos.immutable();

                    if (!encompassedPositions.contains(cachedEncompassedCheckerPos)) encompassedPositions.add(cachedEncompassedCheckerPos);
                }
            }
        }

        return this;
    }

    public BlockPos getOriginPos() {
        return originPos;
    }

    public World getCurWorld() {
        return curWorld;
    }

    public ImmutableList<BlockPos> getEncompassedPositions() {
        return ImmutableList.copyOf(encompassedPositions);
    }

    public double getXSize() {
        return xSize;
    }

    public double getYSize() {
        return ySize;
    }

    public double getZSize() {
        return zSize;
    }

    public double getFCost() { // G + H cost (basically total path cost through this particular node)
        return f;
    }

    public double getGCost() { // Start node -> Cur node (total cost)
        return g;
    }

    public double getHCost() { // Cur node -> End node (total cost)
        return h;
    }

    public double getDangerFactor() {
        return dangerFactor;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public boolean collidesWith(RefinedPathNode o) {
        return encompassedPositions.stream().anyMatch(pos -> o.getEncompassedPositions().contains(pos));
    }

    public void moveTo(@Nonnull BlockPos newOriginPos) {
        this.originPos = newOriginPos;
        this.encompassedPositions.clear();

        setSize(xSize, ySize, zSize); // Lazy ahh shortcut :skull:
    }

    public void updateNodeCosts(RefinedNodeProcessor ownerNodeProcessor) { // Updates based on origin pos, factoring size and danger factor in
        BlockPos originPos = ownerNodeProcessor.calculateStartingNode().getOriginPos();
    }

    @Override
    public int compareTo(RefinedPathNode o) {
        return equals(o) ? 0 : totalScore < o.totalScore ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RefinedPathNode)) return false;
        if (this == o) return true;

        RefinedPathNode otherNode = (RefinedPathNode) o;
        return Double.compare(xSize, otherNode.xSize) == 0 && Double.compare(ySize, otherNode.ySize) == 0 && Double.compare(zSize, otherNode.zSize) == 0 && Double.compare(f, otherNode.f) == 0 && Double.compare(g, otherNode.g) == 0 && Double.compare(h, otherNode.h) == 0 && Double.compare(dangerFactor, otherNode.dangerFactor) == 0 && Double.compare(totalScore, otherNode.totalScore) == 0 && Objects.equals(encompassedPositions, otherNode.encompassedPositions) && Objects.equals(originPos, otherNode.originPos);
    }

    @Override
    public String toString() {
        return String.format("[Refined Path Node]: {Origin Pos}: (%s), {Node Size (XYZ)}: (%d, %d, %d), {Node Costs (FGHDT)}: (%d, %d, %d, %d, %d)", originPos, xSize, ySize, zSize, f, g, h, dangerFactor, totalScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(encompassedPositions, curWorld, originPos, xSize, ySize, zSize); // TODO Revisit and actually hash properly(?)
    }
}
