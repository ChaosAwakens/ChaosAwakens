package io.github.chaosawakens.common.entity.ai.controllers.body.base;

import io.github.chaosawakens.common.entity.base.AnimatableMonsterEntity;
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
		for (int i = ROT_TICK_THRESHOLD - 1; i > 0; i--) {
			this.xRotHist[i] = xRotHist[i - 1];
			this.zRotHist[i] = zRotHist[i - 1];
		}

		this.xRotHist[0] = owner.getX();
		this.zRotHist[0] = owner.getZ();

		if (isOwnerMoving()) {
			owner.yBodyRot = owner.yRot;
			
			rotateHeadIfNecessary();
			
			this.targetHeadRot = owner.yHeadRot;
			this.curRotTime = 0;
		} else if (owner.getPassengers().isEmpty() || !(owner.getPassengers().get(0) instanceof MobEntity)) {
			float rotLimit = ROT_THRESHOLD;

			if (Math.abs(owner.yHeadRot - targetHeadRot) > 15) {
				this.curRotTime = 0;
				this.targetHeadRot = owner.yHeadRot;
				rotateBodyIfNecessary();
			} else {
				final int rotSpeed = 10;

				this.curRotTime++;

				if (curRotTime > rotSpeed) rotLimit = Math.max(1 - (curRotTime - rotSpeed) / rotSpeed, 0) * ROT_THRESHOLD;
				
				owner.yBodyRot = approachRot(owner.yHeadRot, owner.yBodyRot, rotLimit);
			}
		}
	}

	protected boolean isOwnerMoving() {
		double dx = owner.getX() - owner.xo;
		double dz = owner.getZ() - owner.zo;
		double dxSqr = dx * dx;
		double dzSqr = dz * dz;

		return dxSqr + dzSqr > (owner instanceof AnimatableMonsterEntity ? ((AnimatableMonsterEntity) owner).getMovementThreshold() : 2.500000277905201E-7);
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

	private void rotateBodyIfNecessary() {
		this.owner.yBodyRot = MathHelper.rotateIfNecessary(this.owner.yBodyRot, this.owner.yHeadRot, (float)this.owner.getMaxHeadYRot());
	}

	private void rotateHeadIfNecessary() {
		this.owner.yHeadRot = MathHelper.rotateIfNecessary(this.owner.yHeadRot, this.owner.yBodyRot, (float)this.owner.getMaxHeadYRot());
	}
}
