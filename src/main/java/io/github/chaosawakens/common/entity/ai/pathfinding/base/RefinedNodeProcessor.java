package io.github.chaosawakens.common.entity.ai.pathfinding.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.Region;

public class RefinedNodeProcessor extends NodeProcessor {
    protected Region targetRegion;
    protected MobEntity ownerEntity;
    protected AutoSizedPathNode curNode;
    protected boolean isProcessing = false;
    protected boolean isBlockSafe = false;
    protected boolean isFluidSafe = false;
    protected boolean isImmuneToDanger = false;
    protected ITag<Fluid> blacklistedFluids;
    protected ITag<Block> blacklistedBlocks;

    public RefinedNodeProcessor() {

    }

    public RefinedNodeProcessor setBlockSafe(boolean isBlockSafe) {
        this.isBlockSafe = isBlockSafe;
        return this;
    }

    public RefinedNodeProcessor setFluidSafe(boolean isFluidSafe) {
        this.isFluidSafe = isFluidSafe;
        return this;
    }

    public RefinedNodeProcessor setImmuneToDanger(boolean isImmuneToDanger) {
        this.isImmuneToDanger = isImmuneToDanger;
        return this;
    }

    public RefinedNodeProcessor setFluidBlacklist(ITag<Fluid> blacklistedFluids) {
        this.blacklistedFluids = blacklistedFluids;
        return this;
    }

    public RefinedNodeProcessor setBlockBlacklist(ITag<Block> blacklistedBlocks) {
        this.blacklistedBlocks = blacklistedBlocks;
        return this;
    }

    public AutoSizedPathNode calculateStartingNode() {
        return null;
    }

    public int getMalusFor(BlockPos targetBlockNode) {
        BlockNodeType targetNode = new BlockNodeType(targetRegion, targetBlockNode);
        int finalDangerFactor = 0; // Clamped between 1 and 10

        if (!targetNode.isAir()) {
            if (targetNode.isFluid()) {
                FluidState curFluidState = targetNode.getBlockState().getFluidState();

                if (!isFluidSafe) finalDangerFactor += 1;
                if (blacklistedFluids.contains(curFluidState.getType())) finalDangerFactor += 2;
            }

            if (targetNode.isSolid() && !isBlockSafe) {
                BlockState curBlockState = targetNode.getBlockState();

                if (blacklistedBlocks.contains(curBlockState.getBlock())) finalDangerFactor += 3;
                if (targetNode.isPassable(ownerEntity)) finalDangerFactor -= 1;
                else if (!targetNode.isStepable(ownerEntity)) finalDangerFactor += 1;

                if (curBlockState.getAiPathNodeType(targetNode.getLevel(), targetNode.getPos()).getDanger() != null && !isImmuneToDanger) finalDangerFactor += 3;
            }
        }

        return MathHelper.clamp(finalDangerFactor, 0, 10);
    }

    @Override
    public void prepare(Region targetRegion, MobEntity ownerEntity) {
        this.targetRegion = targetRegion;
        this.ownerEntity = ownerEntity;
        this.isProcessing = true;
    }

    @Override
    public void done() {
        this.targetRegion = null;
        this.ownerEntity = null;
        this.isProcessing = false;
    }

    public boolean isProcessing() {
        return isProcessing;
    }

    @Override
    public PathPoint getStart() { // Methods like this one are overridden to return null or do nothing cause they're subsituted by better methods used in the refined pathnav system CA uses
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
}
