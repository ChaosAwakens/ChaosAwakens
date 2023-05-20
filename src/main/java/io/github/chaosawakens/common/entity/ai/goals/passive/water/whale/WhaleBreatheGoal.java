package io.github.chaosawakens.common.entity.ai.goals.passive.water.whale;

import java.util.EnumSet;
import java.util.Iterator;

import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

//TODO Rewrite
public class WhaleBreatheGoal extends Goal {
	private final WhaleEntity whale;

    public WhaleBreatheGoal(WhaleEntity whale) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.whale = whale;
    }
    
    @Override
    public void start() {
    	findPos();
    }
    
    @Override
    public void stop() {
    	whale.getNavigation().stop();
    	whale.getNavigation().recomputePath();
    }

    @Override
    public boolean canUse() {
        return whale.getAirSupply() < 500;
    }

    @Override
    public boolean canContinueToUse() {
        return whale.getAirSupply() < whale.getMaxAirSupply() / whale.getRandom().nextInt(4);
    }

    @SuppressWarnings("rawtypes")
	private void findPos() {
        Iterable<BlockPos> whaleBox = BlockPos.betweenClosed(MathHelper.floor(whale.getX() - 1.0D), MathHelper.floor(whale.getY()), MathHelper.floor(whale.getZ() - 1.0D), MathHelper.floor(whale.getX() + 1.0D), MathHelper.floor(whale.getY() + 8.0D), MathHelper.floor(whale.getZ() + 1.0D));
        BlockPos destination = null;
        Iterator whaleBoxIterator = whaleBox.iterator();

        while (whaleBoxIterator.hasNext()) {
            BlockPos nextPosInWhaleBox = (BlockPos) whaleBoxIterator.next();
            if (this.canBreatheAt(whale.level, nextPosInWhaleBox)) {
                destination = nextPosInWhaleBox.above((int) (whale.getBbHeight() * 0.25D));
                break;
            }
        }

        if (destination == null) {
            destination = new BlockPos(whale.getX(), whale.getY() + 4.0D, whale.getZ());
        }
        
        //Continue to go up until the whale is at the surface
        if (whale.isEyeInFluid(FluidTags.WATER)) {
        	whale.setDeltaMovement(whale.getDeltaMovement().add(0, 0.03F, 0));
        }

        whale.getNavigation().moveTo(destination.getX(), destination.getY(), destination.getZ(), 0.7D);
    }

    private boolean canBreatheAt(IWorldReader world, BlockPos pos) {
        BlockState targetState = world.getBlockState(pos);
        return targetState.isPathfindable(world, pos, PathType.LAND) && (world.getFluidState(pos).isEmpty() || targetState.is(Blocks.BUBBLE_COLUMN));
    }
    
    @Override
    public void tick() {
        findPos();
    }
}
