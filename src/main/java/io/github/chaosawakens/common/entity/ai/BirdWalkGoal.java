package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.entity.BirdEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class BirdWalkGoal extends Goal{	
    protected double x;
    protected double y;
    protected double z;
    protected final BirdEntity bird;
    private boolean canFly = false;
    
    public BirdWalkGoal(BirdEntity bird) {
    	super();    
    	this.bird = bird;
    	this.setFlags(EnumSet.of(Flag.MOVE));
    }
	
	@Override
	public boolean canUse() {
		if (bird.getRandom().nextInt(70) != 0 && !bird.isFlying()) return false;
		
		if (bird.isOnGround()) {
			canFly = bird.getRandom().nextBoolean();
		} else {
			canFly = bird.getRandom().nextInt(10) > 0 && bird.flyTicks < 300;
		}
		Vector3d targetPos = getValidPos();
		if (targetPos == null) {			
			return false;
		} else {
			x = targetPos.x();
			y = targetPos.y();
			z = targetPos.z();
		}
		return false;
	}
	
	@Nullable
	private Vector3d getValidPos() {
		Vector3d birdPos = bird.position();
		if (aboveWater(bird)) {
			canFly = true;
		}
		
		if (canFly) {
			if (aboveWater(bird) || bird.flyTicks < 100) {
				getBlockInViewAway(birdPos, 0);
			} else {
				getGroundBlocks(birdPos);		
			}		
		} else {
			return RandomPositionGenerator.getPos(bird, 10, 7);
		}
		return RandomPositionGenerator.getPos(bird, 10, 7);
	}
	
	private boolean aboveWater(BirdEntity bird) {
		BlockPos birdPos = bird.blockPosition();
		do {
			birdPos = birdPos.below();
		} while (birdPos.getY() > 2 && bird.level.isEmptyBlock(birdPos));
		return !bird.level.getFluidState(birdPos).isEmpty();
	}
	
	@SuppressWarnings("deprecation")
	private BlockPos getPosBelowSolid(BlockPos pos) {
		BlockPos targetPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
		do {
			targetPos = targetPos.below();
		} while (targetPos.getY() < 2 && !bird.level.getBlockState(targetPos).isAir());
		return targetPos;
	}
	
	private boolean targetPosInvalid(Vector3d targetPos) {
		Vector3d target = new Vector3d(bird.getX(), bird.getEyeY(), bird.getZ());
		RayTraceContext context = new RayTraceContext(targetPos, target, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, bird);
		return bird.level.clip(context).getType() != RayTraceResult.Type.MISS;
	}
	
    public Vector3d getGroundBlocks(Vector3d fleePos) {
        float radius = 0.45F * (0.4F * 5) * -2 - bird.getRandom().nextInt(15);
        float neg = bird.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = bird.yRotO;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (bird.getRandom().nextFloat() * neg);
        double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
        double extraZ = radius * MathHelper.cos(angle);
        BlockPos radialPos = new BlockPos(fleePos.x() + extraX, bird.getY(), fleePos.z() + extraZ);
        BlockPos ground = this.getPosBelowSolid(radialPos);
        if (ground.getY() == 0) {
            return bird.position();
        } else {
            ground = bird.blockPosition();
            while (ground.getY() > 2 && bird.level.isEmptyBlock(ground)) {
                ground = ground.below();
            }
        }
        if (!this.targetPosInvalid(Vector3d.atCenterOf(ground.above()))) {
            return Vector3d.atCenterOf(ground);
        }
        return null;
    }
    
    public Vector3d getBlockInViewAway(Vector3d fleePos, float radiusAdd) {
        float radius = 0.45F * (0.4F * 5) * -2 - bird.getRandom().nextInt(15) - radiusAdd;
        float neg = bird.getRandom().nextBoolean() ? 1 : -1;
        float renderYawOffset = bird.yRotO;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (bird.getRandom().nextFloat() * neg);
        double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
        double extraZ = radius * MathHelper.cos(angle);
        BlockPos radialPos = new BlockPos(fleePos.x() + extraX, 0, fleePos.z() + extraZ);
        BlockPos ground = getPosBelowSolid(radialPos);
        int distFromGround = (int) bird.getY() - ground.getY();
        int flightHeight = 4 + bird.getRandom().nextInt(10);
        BlockPos newPos = ground.above(distFromGround > 8 ? flightHeight : (int)bird.getRandom().nextInt(6) + 1);
        if (!this.targetPosInvalid(Vector3d.atCenterOf(newPos)) && bird.distanceToSqr(Vector3d.atCenterOf(newPos)) > 1) {
            return Vector3d.atCenterOf(newPos);
        }
        return null;
    }

}
