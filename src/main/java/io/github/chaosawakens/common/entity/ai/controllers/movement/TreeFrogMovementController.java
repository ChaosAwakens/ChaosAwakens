package io.github.chaosawakens.common.entity.ai.controllers.movement;

import io.github.chaosawakens.common.entity.creature.land.TreeFrogEntity;
import io.github.chaosawakens.common.util.MathUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TreeFrogMovementController extends MovementController {
	private final TreeFrogEntity owner;
	private float yRot;
	private int jumpDelay;
	private boolean isPanicking;

	public TreeFrogMovementController(TreeFrogEntity owner) {
		super(owner);
		this.owner = owner;
		this.yRot = (float) (180.0F * owner.yRot / Math.PI);
	}

	@Override
	public void tick() {
		MathUtil.decrementToZero(jumpDelay);

		BlockPos selectedTargetPos = null;

		this.mob.yRot = rotlerp(this.mob.yRot, this.yRot, 180.0F);
		this.mob.yHeadRot = this.mob.yRot;
		this.mob.yBodyRot = this.mob.yRot;
		this.isPanicking = owner.hurtTime > 0;

		if (this.operation == Action.WAIT) {
			this.mob.setXxa(0.0F);
			this.mob.setZza(0.0F);

			if (owner.isOnGround()) {
				BlockPos targetPos = findJumpPos(isPanicking ? 30.0D : 15.0D, isPanicking ? 10.0D : 4.0D);

				if (targetPos != null) {
					this.operation = Action.JUMPING;
					selectedTargetPos = targetPos;
				}
			} else if (owner.isInWater()) {

			}
		}

		if (this.operation == Action.JUMPING) {
			if (selectedTargetPos != null) {
				
			}
		}

		if (this.operation == Action.MOVE_TO) {
			BlockPos targetPos = findJumpPos(isPanicking ? 30.0D : 15.0D, isPanicking ? 10.0D : 4.0D);

			if (targetPos != null) {
				this.operation = Action.JUMPING;
				selectedTargetPos = targetPos;
			} else if (!owner.getNavigation().isDone()) {
				selectedTargetPos = owner.getNavigation().getTargetPos();

				if (selectedTargetPos != null) {
					
				}
			} else this.operation = Action.WAIT;
		}
	}

	@Nullable
	private BlockPos findJumpPos(double searchDiameter, double minDistThreshold) {
		BlockPos originPos = this.owner.blockPosition();
		BlockPos.Mutable iteratingPos = new BlockPos.Mutable(originPos.getX(), originPos.getY(), originPos.getZ());

		if (owner.isDeadOrDying()) return null;

		for (double curXRange = originPos.getX() - searchDiameter; curXRange <= originPos.getX() + searchDiameter; curXRange++) {
			for (double curZRange = originPos.getZ() - searchDiameter; curZRange <= originPos.getZ() + searchDiameter; curZRange++) {
				for (double curYRange = originPos.getY() - owner.getAttributeValue(Attributes.JUMP_STRENGTH); curYRange <= originPos.getY() + owner.getAttributeValue(Attributes.JUMP_STRENGTH); curYRange++) {
					iteratingPos.set(curXRange, curYRange, curZRange);

					BlockState curState = owner.level.getBlockState(iteratingPos);

					if (!curState.canOcclude() || curState.getCollisionShape(owner.level, iteratingPos).isEmpty() || !owner.level.getFluidState(iteratingPos).isEmpty()) continue;
					if (!iteratingPos.closerThan(owner.position(), minDistThreshold)) return iteratingPos;
				}
			}
		}

		return null;
	}
}