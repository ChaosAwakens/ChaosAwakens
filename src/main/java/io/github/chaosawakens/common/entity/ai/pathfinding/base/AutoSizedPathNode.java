package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import io.github.chaosawakens.api.animation.IAnimatableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class AutoSizedPathNode extends RefinedPathNode {
    protected final IAnimatableEntity ownerEntity;
    protected final LivingEntity ownerLivingEntity;

    public AutoSizedPathNode(IAnimatableEntity ownerEntity, BlockPos originPos) {
        super(((LivingEntity) ownerEntity).level, originPos);
        this.ownerEntity = ownerEntity;
        this.ownerLivingEntity = (LivingEntity) ownerEntity;
    }

    @Override
    public AutoSizedPathNode setSize(double xSize, double ySize, double zSize) {
        this.xSize = Math.round(ownerLivingEntity.getBoundingBox().getXsize());
        this.ySize = Math.round(ownerLivingEntity.getBoundingBox().getYsize());
        this.zSize = Math.round(ownerLivingEntity.getBoundingBox().getZsize());

        BlockPos.Mutable encompassedPositionCheckerPos = new BlockPos.Mutable(originPos.getX(), originPos.getY(), originPos.getZ());

        encompassedPositions.clear(); // In case it was scaled-down
        encompassedPositions.add(originPos);

        for (double curXSize = -this.xSize; curXSize <= this.xSize; curXSize++) { // Scale by diameter, not radius, using the provided radius
            for (double curYSize = -this.ySize; curYSize <= this.ySize; curYSize++) {
                for (double curZSize = -this.zSize; curZSize <= this.zSize; curZSize++) {
                    encompassedPositionCheckerPos.set(originPos.getX() + curXSize, originPos.getY() + curYSize, originPos.getZ() + curZSize);
                    BlockPos cachedEncompassedCheckerPos = encompassedPositionCheckerPos.immutable();

                    if (!encompassedPositions.contains(cachedEncompassedCheckerPos)) encompassedPositions.add(cachedEncompassedCheckerPos);
                }
            }
        }

        return this;
    }
}
