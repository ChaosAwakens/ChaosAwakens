package io.github.chaosawakens.common.entity.ai.goals.passive.water.whale;

import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;
import java.util.Iterator;

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
        return whale.getAirSupply() < 500 && whale.getRandom().nextInt(85) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return whale.getAirSupply() < whale.getMaxAirSupply() && whale.isAlive();
    }

    @SuppressWarnings("rawtypes")
	private void findPos() {
        Iterable<BlockPos> whaleBox = BlockPos.betweenClosed(MathHelper.floor(whale.getX() - 10.0D), MathHelper.floor(whale.getY()), MathHelper.floor(whale.getZ() - 10.0D), MathHelper.floor(whale.getX() + 10.0D), MathHelper.floor(whale.getY() + 10.0D), MathHelper.floor(whale.getZ() + 10.0D));
        BlockPos.Mutable destinationSearch = whale.blockPosition().mutable();

        for (BlockPos nextPosInWhaleBox : whaleBox) {
            if (canBreatheAt(whale.level, nextPosInWhaleBox)) {
                destinationSearch.set(nextPosInWhaleBox.above((int) (whale.getBbHeight() * 0.25D)));
                break;
            }
        }
        
        // Continue to go up until the whale is at the surface
        if (whale.isEyeInFluid(FluidTags.WATER)) {
        	whale.setDeltaMovement(whale.getDeltaMovement().add(0, 0.03F, 0));
        }

        whale.getNavigation().moveTo(destinationSearch.getX(), destinationSearch.getY(), destinationSearch.getZ(), 0.7D);
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
