package io.github.chaosawakens.common.entity.ai.controllers.movement;

import com.sk89q.worldedit.math.Vector3;
import io.github.chaosawakens.common.entity.creature.land.TreeFrogEntity;
import io.github.chaosawakens.common.util.MathUtil;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

public class TreeFrogMovementController extends MovementController {
	private final TreeFrogEntity owner;
	private float yRot;
	private float targetXRot;
	private int jumpDelay = 20;
	private boolean isPanicking;
	private boolean persistentPanic;
	private BlockPos selectedTargetPos;
	private BlockPos selectedGenPos;

	public TreeFrogMovementController(TreeFrogEntity owner) {
		super(owner);
		this.owner = owner;
		this.yRot = (float) (180.0F * owner.yRot / Math.PI);
	}

	@Override
	public void strafe(float pForward, float pStrafe) {

	}

	@Override
	public void tick() {
		jumpDelay--;

		this.isPanicking = owner.hurtTime > 0 || (owner.getLastHurtByMob() != null && owner.getLastHurtByMob().isAlive() && (owner.getLastHurtByMob().distanceTo(owner) <= 10 || (owner.getLastHurtByMob() instanceof MobEntity && ((MobEntity) owner.getLastHurtByMob()).getTarget() != null && ((MobEntity) owner.getLastHurtByMob()).getTarget().is(owner))));

		if (this.operation == Action.WAIT) {
			this.mob.setXxa(0.0F);
			this.mob.setZza(0.0F);

			if (owner.isOnGround()) {
				if ((isPanicking || jumpDelay <= 0) && !owner.isInWater()) this.operation = Action.MOVE_TO;
			} else if (owner.isInWater()) {
			//	if (selectedGenPos == null) this.selectedGenPos = getRandomSwimmablePos(owner, 50, 20);
				this.operation = Action.STRAFE;
			}
		}

		if (this.operation == Action.STRAFE) {
		/*	if (selectedGenPos == null) this.selectedGenPos = getRandomSwimmablePos(owner, 50, 20);
			if (selectedGenPos != null) {
				double xDelta = selectedGenPos.getX() - owner.getX();
				double yDelta = selectedGenPos.getY() - owner.getY();
				double zDelta = selectedGenPos.getZ() - owner.getZ();
				double totalHorizontalDelta = Math.sqrt(xDelta * xDelta + zDelta * zDelta);

				yRot = (float) (Math.toDegrees(Math.atan2(zDelta, xDelta)) - 90.0F);

				float totalSpeed = (float) this.owner.getAttributeValue(Attributes.MOVEMENT_SPEED);

				if (this.owner.isInWater()) {
					this.owner.setSpeed(totalSpeed * 2);

					if (Math.abs(yDelta) > 1.0E-5D || Math.abs(totalHorizontalDelta) > 1.0E-5D) {
						targetXRot = (float) -Math.toDegrees(Math.atan2(zDelta, totalHorizontalDelta));

						targetXRot = MathHelper.clamp(MathHelper.wrapDegrees(targetXRot), -85.0F, 85.0F);
					}

					float zXRot = (float) Math.cos(Math.toRadians(this.owner.xRot));
					float yXRot = (float) Math.sin(Math.toRadians(this.owner.xRot));
					this.owner.zza = zXRot * totalSpeed;
					this.owner.yya = -yXRot * totalSpeed;

					this.owner.setDeltaMovement(new Vector3d(owner.xxa, owner.yya, owner.zza));
				}
			} else {
				owner.setDeltaMovement(owner.getDeltaMovement().add(0, 0.03D, 0));
			}

			if (selectedGenPos != null && owner.distanceToSqr(new Vector3d(selectedGenPos.getX(), selectedGenPos.getY(), selectedGenPos.getZ())) < 4.0D) {
				this.selectedGenPos = null;
			} */
			owner.setDeltaMovement(owner.getDeltaMovement().add(0, 0.03D, 0));

			this.operation = Action.MOVE_TO;
		}

		if (this.operation == Action.MOVE_TO) {
			if (owner.tickCount % 20 == 0 && jumpDelay <= 0) {
				this.selectedTargetPos = findJumpPos(20, isPanicking);

				if (selectedTargetPos != null) {
					this.yRot = (float) Math.toDegrees(Math.atan2(selectedTargetPos.getZ() - owner.getZ(), selectedTargetPos.getX() - owner.getX())) - 90.0F;
					this.targetXRot = (float) -Math.toDegrees(Math.atan2(selectedTargetPos.getY() - owner.getY(), selectedTargetPos.getX() - owner.getX()));
					this.targetXRot = MathHelper.clamp(MathHelper.wrapDegrees(this.targetXRot), -85.0F, 85.0F);
					this.jumpDelay = MathHelper.nextInt(owner.getRandom(), 20, 100);
					this.operation = Action.JUMPING;
				}
			} else if (isPanicking) {
				this.selectedTargetPos = findJumpPos(20, true);
				if (selectedTargetPos != null) {
					this.yRot = (float) Math.toDegrees(Math.atan2(selectedTargetPos.getZ() - owner.getZ(), selectedTargetPos.getX() - owner.getX())) - 90.0F;
					this.targetXRot = (float) -Math.toDegrees(Math.atan2(selectedTargetPos.getY() - owner.getY(), selectedTargetPos.getX() - owner.getX()));
					this.targetXRot = MathHelper.clamp(MathHelper.wrapDegrees(this.targetXRot), -85.0F, 85.0F);
					this.jumpDelay = MathHelper.nextInt(owner.getRandom(), 2, 20);
					this.operation = Action.JUMPING;
				}
			} else this.operation = Action.WAIT;
		}

		if (this.operation == Action.JUMPING) {
			if (selectedTargetPos != null && this.owner.distanceToSqr(new Vector3d(selectedTargetPos.getX(), selectedTargetPos.getY(), selectedTargetPos.getZ())) > 4.0D) {
				this.owner.setSpeed((float) (owner.getAttributeValue(Attributes.MOVEMENT_SPEED)));

				this.owner.setXxa((float) (selectedTargetPos.getX() - owner.getX()));
				this.owner.setYya(2.0F);
				this.owner.setZza((float) (selectedTargetPos.getZ() - owner.getZ()));

				this.owner.setDeltaMovement(new Vector3d(owner.xxa, owner.yya, owner.zza).multiply(0.1, 0.5, 0.1));

				this.operation = Action.WAIT;
			}

			if (selectedTargetPos != null && this.owner.distanceToSqr(new Vector3d(selectedTargetPos.getX(), selectedTargetPos.getY(), selectedTargetPos.getZ())) <= 4.0D) {
				if (isPanicking) {
					this.selectedTargetPos = findJumpPos(20, true);

					this.owner.setSpeed((float) (owner.getAttributeValue(Attributes.MOVEMENT_SPEED)));

					this.owner.setXxa((float) (selectedTargetPos.getX() - owner.getX()));
					this.owner.setYya(1.0F);
					this.owner.setZza((float) (selectedTargetPos.getZ() - owner.getZ()));

					this.owner.setDeltaMovement(new Vector3d(owner.xxa, owner.yya, owner.zza).multiply(0.1, 0.5, 0.1));
				} else {
					this.selectedTargetPos = null;
				}
				this.operation = Action.WAIT;
			}
		}

		this.owner.xRot = rotlerp(this.owner.xRot, targetXRot, 5.0F);
		this.owner.yRot = rotlerp(this.owner.yRot, this.yRot, 20.0F);
		this.owner.yHeadRot = this.owner.yRot;
		this.owner.yBodyRot = this.owner.yRot;
	}

	@Nullable
	private BlockPos findJumpPos(int searchDiameter, boolean isPanicking) {
		Vector3d panicPos = RandomPositionGenerator.getLandPosAvoid(owner, searchDiameter, searchDiameter, owner.getLastHurtByMob() != null ? owner.getLastHurtByMob().position() : owner.position());
		Vector3d targetPos = RandomPositionGenerator.getLandPos(owner, searchDiameter, searchDiameter);

		if (targetPos == null && owner.isInWater()) targetPos = RandomPositionGenerator.getPos(owner, searchDiameter, searchDiameter);

		BlockPos originPos = (isPanicking && panicPos == null) || (!isPanicking && targetPos == null) ? null : new BlockPos(isPanicking ? panicPos : targetPos);

		if (owner.isDeadOrDying()) return null;

		return originPos;
	}
}