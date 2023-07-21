package io.github.chaosawakens.common.entity.ai.pathfinding.bodycontrollers.base;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.util.math.MathHelper;

// MM implementation revised
public class SmoothBodyController extends BodyController {
	protected final MobEntity owner;
	protected static final int ROT_TICK_THRESHOLD = 10;
	protected static final int ROT_THRESHOLD = 75;
	protected int curRotTime;
	protected float targetHeadRot;
	protected double[] xRotHist = new double[ROT_TICK_THRESHOLD];
	protected double[] zRotHist = new double[ROT_TICK_THRESHOLD];

	public SmoothBodyController(MobEntity owner) {
		super(owner);
		this.owner = owner;
	}
	
	@Override
	public void clientTick() {
		updateRotation();
		
		double distX = meanDelta(xRotHist);
		double distZ = meanDelta(zRotHist);
		double horizontalDistSqr = distX * distX + distZ * distZ;
		
		if (horizontalDistSqr > 2.5E-7) {
			float movementAngleDeg = (float) Math.atan2(distZ, distX) * (180 / (float) Math.PI) - 90;
			
			owner.yBodyRot += MathHelper.degreesDifference(owner.yBodyRot, movementAngleDeg) * 0.6F;
			
			this.targetHeadRot = owner.yHeadRot;
			this.curRotTime = 0;
		} else if (owner.getPassengers().isEmpty() || !(owner.getPassengers().get(0) instanceof MobEntity)) {
			float rotLimit = ROT_THRESHOLD;
			
			if (Math.abs(owner.yHeadRot - targetHeadRot) > 15) {
				this.curRotTime = 0;
				this.targetHeadRot = owner.yHeadRot;
			} else {
				this.curRotTime++;
				
				final int rotSpeed = 20;
				
				if (curRotTime > rotSpeed) rotLimit = Math.max(1 - (curRotTime - rotSpeed) / rotSpeed, 0) * ROT_THRESHOLD;
			}
			
			owner.yBodyRot = approachRot(owner.yHeadRot, owner.yBodyRot, rotLimit);
		}
	}
	
	/**
	 * Updates the current horizontal rotation of the owner {@link MobEntity} in decrementing order using {@link #ROT_TICK_THRESHOLD}. 
	 * Also sets the first element of both {@link #xRotHist} and {@link #zRotHist} to the owner {@link MobEntity}'s current world 
	 * position.
	 */
	protected void updateRotation() {
		for (int i = ROT_TICK_THRESHOLD - 1; i > 0; i--) {
			this.xRotHist[i] = xRotHist[i - 1];
			this.zRotHist[i] = zRotHist[i - 1];
		}
		
		this.xRotHist[0] = owner.getX();
		this.zRotHist[0] = owner.getZ();
	}
	
	private double meanDelta(double[] rotHist) {
		return mean(rotHist, 0) - mean(rotHist, ROT_TICK_THRESHOLD / 2);
	}
	
	private double mean(double[] rotHist, int startingIndex) {
		double curMean = 0;
		
		for (int i = 0; i < ROT_TICK_THRESHOLD / 2; i++) curMean += rotHist[i + startingIndex];
		
		return curMean / rotHist.length;
	}
	
	private static float approachRot(float curRot, float targetRot, float rotThreshold) {
		float rotDelta = MathHelper.degreesDifference(targetRot, curRot);
		
		if (rotDelta < -rotThreshold) rotDelta = -rotThreshold;
		else if (rotDelta >= rotThreshold) rotDelta = rotThreshold;
		
		return targetRot + rotDelta * 0.55F;
	}
	
	public int getRotationTickThreshold() {
		return ROT_TICK_THRESHOLD;
	}
}
