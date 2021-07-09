package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;

import io.github.chaosawakens.common.entity.AnimatableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;

/**
 * @author invalid2
 *
 */
public class AnimatableRamGoal extends AnimatableMovableGoal {
	
	private final double speedMultiplier;
	private final int checkRate;
	
	/**
	 * 
	 */
	public AnimatableRamGoal(AnimatableMonsterEntity entity, double speedMultiplier, int checkRate) {
		this.entity = entity;
		this.speedMultiplier = speedMultiplier;
		this.checkRate = checkRate;
		
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}
	
	@Override
	public boolean shouldExecute() {
		if(RANDOM.nextInt(this.checkRate) == 0)return false;
		
		return this.isExecutable(this, this.entity, this.entity.getAttackTarget());
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		if(RANDOM.nextInt(this.checkRate) == 0)return true;
		
		return this.isExecutable(this, this.entity, this.entity.getAttackTarget());
	}
	
	@Override
	public void startExecuting() {
		this.entity.setAggroed(true);
		this.entity.setMoving(true);
		this.entity.getNavigator().setPath(this.path, this.speedMultiplier);
	}
	
	@Override
	public void resetTask() {
		LivingEntity target = this.entity.getAttackTarget();
		if (!EntityPredicates.CAN_AI_TARGET.test(target)) {
			this.entity.setAttackTarget(null);
		}
		this.entity.setAggroed(false);
		this.entity.setMoving(false);
		this.entity.getNavigator().clearPath();
	}
	
	@Override
	public void tick() {
		LivingEntity target = this.entity.getAttackTarget();
		if(target == null)return;
		
		this.entity.faceEntity(target, 30, 30);
		//BlockPos targetPos = this.entity.getNavigator().getPath().getTarget();
		
		//this.entity.getLookController().setLookPosition(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 30, 30);
		
		//this.entity.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speedMultiplier);
	}
	
	@Override
	protected boolean isExecutable(AnimatableMovableGoal goal, AnimatableMonsterEntity attacker, LivingEntity target) {
		if(target == null)return false;
		if(target.isAlive() && !target.isSpectator()) {
			if(target instanceof PlayerEntity && ((PlayerEntity) target).isCreative())return false;
			
			double distance = goal.entity.getDistanceSq(target.getPosX(), target.getPosY(), target.getPosZ());

			return attacker.getEntitySenses().canSee(target) && distance >= AnimatableGoal.getAttackReachSq(attacker, target);
		}
		return false;
	}
}
