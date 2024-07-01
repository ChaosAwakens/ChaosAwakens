package io.github.chaosawakens.common.entity.ai.controllers.movement.ground.base;

import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
import io.github.chaosawakens.common.util.EnumUtil.GroundMovementOperation;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class RefinedGroundMovementController extends MovementController {
	protected AnimatableMonsterEntity owner;
	protected double speedMod = 1.0D;
	protected Vector3d wantedPos = Vector3d.ZERO;
	protected GroundMovementOperation curMovementOperation = GroundMovementOperation.WAIT;
	protected boolean shouldJumpByDefault = false;

	public RefinedGroundMovementController(AnimatableMonsterEntity owner) {
		super(owner);
		this.owner = owner;
	}
	
	public RefinedGroundMovementController setJumpsByDefault(boolean shouldJumpByDefault) {
		this.shouldJumpByDefault = shouldJumpByDefault;
		return this;
	}
	
	@Override
	public double getSpeedModifier() {
		return speedMod;
	}
	
	public void setSpeedModifier(double speedModifier) {
		this.speedMod = speedModifier;
	}
	
	@Nullable
	public Vector3d getWantedPos() {
		return wantedPos;
	}
	
	@Override
	public void setWantedPosition(double pX, double pY, double pZ, double speedMod) {
		this.wantedPos = new Vector3d(pX, pY, pZ);
		this.speedMod = speedMod;
		setMovementOperation(GroundMovementOperation.MOVE);
	}
	
	public void setWantedPosition(Vector3d wantedPos, double speedMod) {
		setWantedPosition(wantedPos.x, wantedPos.y, wantedPos.z, speedMod);
	}
	
	public void setWantedPosition(BlockPos wantedPos, double speedMod) {
		setWantedPosition(wantedPos.getX(), wantedPos.getY(), wantedPos.getZ(), speedMod);
	}
	
	@Override
	public double getWantedX() {
		return wantedPos.x;
	}
	
	@Override
	public double getWantedY() {
		return wantedPos.y;
	}
	
	@Override
	public double getWantedZ() {
		return wantedPos.z;
	}
	
	public GroundMovementOperation getCurrentMovementOperation() {
		return curMovementOperation;
	}
	
	public void setMovementOperation(GroundMovementOperation operation) {
		this.curMovementOperation = operation;
	}
	
	public void strafe(float forwardMovement, float strafeMovement, double speedMod) {
		this.strafeForwards = forwardMovement;
		this.strafeRight = strafeMovement;
		this.speedMod = speedMod;
	}
	
	@Override
	public void strafe(float pForward, float pStrafe) {
		strafe(pForward, pStrafe, 0.25D);
	}
	
	public void circleStrafeAround(double centerX, double centerY, double centerZ, double radius, double speedMod, boolean counterClockwise) {
		double theta = Math.atan2(owner.getZ() - centerZ, owner.getX() - centerX) + (speedMod * (counterClockwise ? 1 : -1));
		double targetX = centerX + radius * Math.cos(theta);
		double targetZ = centerZ + radius * Math.sin(theta);

		setWantedPosition(targetX, centerY, targetZ, speedMod);
		setMovementOperation(GroundMovementOperation.STRAFE);
	}
	
	public void circleStrafeAround(Vector3d centerPos, double radius, double speedMod, boolean counterClockwise) {
		circleStrafeAround(centerPos.x, centerPos.y, centerPos.z, radius, speedMod, counterClockwise);
	}
	
	public void circleStrafeAround(BlockPos centerPos, double radius, double speedMod, boolean counterClockwise) {
		circleStrafeAround(centerPos.getX(), centerPos.getY(), centerPos.getZ(), radius, speedMod, counterClockwise);
	}
	
	@Override
	public void tick() {
		switch (curMovementOperation) {
		default: break;
		case WAIT:
			if (wantedPos != null && mob.distanceToSqr(wantedPos) > 2.5000003E-7F) setMovementOperation(GroundMovementOperation.MOVE);
			break;
		case MOVE:
			
			break;
		case REVERSE:
			
			break;
		case JUMP:
			
			break;
		case STRAFE:
			
			break;
		}
	}
}