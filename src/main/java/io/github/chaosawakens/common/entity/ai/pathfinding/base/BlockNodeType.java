package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockNodeType implements ISubNodeType {
    private final IBlockReader curLevel;
    private final BlockPos targetStatePos;
    private final BlockState targetState;

    public BlockNodeType(IBlockReader curLevel, BlockPos targetStatePos) {
        this.curLevel = curLevel;
        this.targetStatePos = targetStatePos;
        this.targetState = curLevel.getBlockState(targetStatePos);
    }

    public IBlockReader getLevel() {
        return curLevel;
    }

    public BlockPos getPos() {
        return targetStatePos;
    }

    public BlockState getBlockState() {
        return targetState;
    }

    @Override
    public int getMalus(RefinedNodeProcessor targetProcessor) {
        return targetProcessor.getMalusFor(targetStatePos);
    }

    @Override
    public boolean isPassable(LivingEntity targetPathfindingEntity) {
        return targetPathfindingEntity.noPhysics || targetState.getCollisionShape(curLevel, targetStatePos).isEmpty();
    }

    @Override
    public boolean isStepable(LivingEntity targetPathfindingEntity) {
        float maxUpStep = targetPathfindingEntity.maxUpStep;
        double blockHeight = targetState.getCollisionShape(curLevel, targetStatePos).max(Direction.Axis.Y) - targetPathfindingEntity.getY();

        return !isAir() && !isFluid() && !isPassable(targetPathfindingEntity) && blockHeight <= maxUpStep;
    }

    @Override
    public boolean isFluid() {
        return targetState.getFluidState() != null && !targetState.getFluidState().equals(Fluids.EMPTY.defaultFluidState());
    }

    @Override
    public boolean isAir() {
        return targetState.isAir(curLevel, targetStatePos);
    }

    @Override
    public boolean isSolid() {
        return !isAir() && !isFluid() && targetState.canOcclude();
    }
}
