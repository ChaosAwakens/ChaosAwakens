package io.github.chaosawakens.common.entity.ai.controllers.movement.ground.base;

import javax.annotation.Nullable;

import io.github.chaosawakens.common.util.EnumUtil.GroundMovementOperation;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class RefinedGroundMovementController extends MovementController {
	protected double speedMod = 1.0D;
	protected Vector3d wantedPos = Vector3d.ZERO;
	protected GroundMovementOperation curMovementOperation = GroundMovementOperation.WAIT;
	protected boolean shouldJumpByDefault = false;

	public RefinedGroundMovementController(MobEntity owner) {
		super(owner);
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
		
	}
	
	@Override
	public void strafe(float pForward, float pStrafe) {
		strafe(pForward, pStrafe, 0.25D);
	}
	
	public void circleStrafeAround(double x, double y, double z, double radius, double speedMod, boolean counterClockwise) {
		
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