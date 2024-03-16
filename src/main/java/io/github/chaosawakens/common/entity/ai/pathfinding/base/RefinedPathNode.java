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
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;

        BlockPos.Mutable encompassedPositionCheckerPos = new BlockPos.Mutable(originPos.getX(), originPos.getY(), originPos.getZ());

        encompassedPositions.clear(); // In case it was scaled-down
        encompassedPositions.add(originPos);

        for (double curXSize = -xSize; curXSize <= xSize; curXSize++) { // Scale by diameter, not radius
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

    public boolean collidesWith(RefinedPathNode o) {
        return encompassedPositions.stream().anyMatch(pos -> o.getEncompassedPositions().contains(pos));
    }

    public void moveTo(@Nonnull BlockPos newOriginPos) {
        this.originPos = newOriginPos;
        this.encompassedPositions.clear();

        setSize(xSize, ySize, zSize); // Lazy ahh shortcut :skull:
    }

    public void updateNodeCosts() {

    }

    @Override
    public int compareTo(RefinedPathNode o) {
        if (equals(o)) return 0;

        boolean isLarger = getXSize() >= o.getXSize() && getYSize() >= o.getYSize() && getZSize() >= o.getZSize();
        boolean positionComparison = originPos.getX() <= o.originPos.getX() && originPos.getY() <= o.originPos.getY() && originPos.getZ() <= o.originPos.getZ();

        if (isLarger || positionComparison) return 1;

        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof RefinedPathNode)) return false;
        if (this == o) return true;

        RefinedPathNode otherNode = (RefinedPathNode) o;
        return Double.compare(xSize, otherNode.xSize) == 0 && Double.compare(ySize, otherNode.ySize) == 0 && Double.compare(zSize, otherNode.zSize) == 0 && Objects.equals(encompassedPositions, otherNode.encompassedPositions) && Objects.equals(originPos, otherNode.originPos);
    }

    @Override
    public String toString() {
        return String.format("[Refined Path Node]: {Origin Pos}: (%s), {Node Size (XYZ)}: (%d%d%d), {Node Costs (FGH)}: (%d%d%d)", originPos, xSize, ySize, zSize, f, g, h);
    }

    @Override
    public int hashCode() {
        return Objects.hash(encompassedPositions, curWorld, originPos, xSize, ySize, zSize); // TODO Revisit and actually hash properly(?)
    }
}
