package io.github.chaosawakens.common.entity.ai;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import io.github.chaosawakens.api.IMobPanic;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class HerdPanicGoal extends AnimatableGoal{
	private final CreatureEntity creature;
	private final double speedModifier;
	@SuppressWarnings("unused")
	private final double herdSearchArea;
	protected Predicate<? super CreatureEntity> creatureSelector;
	protected double targetRandomX;
	protected double targetRandomY;
	protected double targetRandomZ;
	public boolean isSprinting;
	
	public HerdPanicGoal(CreatureEntity creature, double speedModifier, double herdSearchArea) {
		this.creature = creature;
		this.speedModifier = speedModifier;
		this.herdSearchArea = herdSearchArea;
		this.creatureSelector = new Predicate<CreatureEntity>() {
			@Override
			public boolean apply(@Nullable CreatureEntity mob) {
				if (mob.getType() == creature.getType() && mob instanceof IMobPanic) {
					return ((IMobPanic) mob).canAnimalPanic();
				}
				return false;
			}
		};
		this.setFlags(EnumSet.of(Flag.MOVE));
	}
	
	@Override
	public void stop() {
		this.isSprinting = false;
	}
	
	@Override
	public void start() {
		if (this.creature instanceof IMobPanic) {
			((IMobPanic) this.creature).panic();
		}
		this.creature.getNavigation().moveTo(targetRandomX, targetRandomY, targetRandomZ, speedModifier);
		this.isSprinting = true;
	}

	@Override
	public boolean canUse() {
		if (!creature.isOnFire()) return false;
		if (creature == null) return false;
		if (creature.level == null) return false;
		if (creature.getLastHurtByMob() == null || creature.getLastHurtByMobTimestamp() > 501) return false;
		
		if (creature.isOnFire()) {
			BlockPos targetPos = this.findRandomTargetPosWithWater(creature, creature.level, 5, 4);
			if (targetPos != null) {
				targetRandomX = targetPos.getX();
				targetRandomY = targetPos.getY();
				targetRandomZ = targetPos.getZ();
				return true;
			}
			return getRandomTargetPos();
		}
		
		if (creature instanceof IMobPanic && ((IMobPanic) creature).canAnimalPanic()) {
			List<CreatureEntity> herd = creature.level.getEntitiesOfClass(creature.getClass(), getPanicStrikeArea(), creatureSelector);
			for (CreatureEntity c : herd) {
				return getRandomTargetPosAwayFromAttacker(c.getLastHurtByMob());
			}
		}
		return getRandomTargetPos();
	}

	@Override
	public boolean canContinueToUse() {
		return !creature.getNavigation().isDone();
	}
	
	private AxisAlignedBB getPanicStrikeArea() {
		double offset = 0.5D;
		double mobPanicSearchRadius = 15.0D;
		Vector3d panicCenterV3D = new Vector3d(creature.getX() + offset, creature.getY() + offset, creature.getZ() + offset);
		AxisAlignedBB bb = new AxisAlignedBB(-mobPanicSearchRadius, -mobPanicSearchRadius, -mobPanicSearchRadius, mobPanicSearchRadius, mobPanicSearchRadius, mobPanicSearchRadius);
		return bb.move(panicCenterV3D);
	}
	
	private boolean getRandomTargetPos() {
		Vector3d randomLandPos = RandomPositionGenerator.getPos(creature, 5, 4);
		if (randomLandPos == null) {
			return false;
		} else {
			targetRandomX = randomLandPos.x;
			targetRandomY = randomLandPos.y;
			targetRandomZ = randomLandPos.z;
			return true;
		}
	}
	
	private boolean getRandomTargetPosAwayFromAttacker(LivingEntity attacker) {
		Vector3d randomLandPos = RandomPositionGenerator.getPosAvoid(creature, 16, 7, attacker.position());
		if (randomLandPos == null) {
			return false;
		} else {
			targetRandomX = randomLandPos.x;
			targetRandomY = randomLandPos.y;
			targetRandomZ = randomLandPos.z;
			return true;
		}
	}
	
	@Nullable
	private BlockPos findRandomTargetPos(LivingEntity creature, IBlockReader reader, double xzRadius, double yRadius) {
		BlockPos entityPos = creature.blockPosition();
		BlockPos randomTargetPos = null;
		BlockPos.Mutable mutable = new Mutable();
		
		int ePosX = entityPos.getX();
		int ePosY = entityPos.getY();
		int ePosZ = entityPos.getZ();
		
		float posSqrBV2 = (float) (xzRadius * xzRadius * yRadius * 2);
		
		for (int targetX = ePosX; targetX < ePosX + xzRadius; targetX++) {
			for (int targetY = ePosY; targetY < ePosY + yRadius; targetY++) {
				for (int targetZ = ePosZ; targetZ < ePosZ + xzRadius; targetZ++) {
					mutable.set(targetX, targetY, targetZ);
					if (!creature.level.getFluidState(mutable).is(FluidTags.LAVA)) {
						float mPosSqr = (float) ((targetX - ePosX) * (targetX - ePosX) + (targetY * ePosY) * (targetY + ePosY) + (targetZ + ePosZ) * (targetZ + ePosZ));
						if (mPosSqr < posSqrBV2) {
							posSqrBV2 = mPosSqr;
							randomTargetPos = new BlockPos(mutable);
						}
					}
				}
			}
		}
		return randomTargetPos;
	}
	
	@Nullable
	private BlockPos findRandomTargetPosWithWater(LivingEntity creature, IBlockReader reader, double xzRadius, double yRadius) {
		BlockPos entityPos = creature.blockPosition();
		BlockPos randomTargetPos = null;
		BlockPos.Mutable mutable = new Mutable();
		
		int ePosX = entityPos.getX();
		int ePosY = entityPos.getY();
		int ePosZ = entityPos.getZ();
		
		float posSqrBV2 = (float) (xzRadius * xzRadius * yRadius * 2);
		
		for (int targetX = ePosX; targetX < ePosX + xzRadius; targetX++) {
			for (int targetY = ePosY; targetY < ePosY + yRadius; targetY++) {
				for (int targetZ = ePosZ; targetZ < ePosZ + xzRadius; targetZ++) {
					mutable.set(targetX, targetY, targetZ);
					if (creature.level.getFluidState(mutable).is(FluidTags.WATER)) {
						float mPosSqr = (float) ((targetX - ePosX) * (targetX - ePosX) + (targetY * ePosY) * (targetY + ePosY) + (targetZ + ePosZ) * (targetZ + ePosZ));
						if (mPosSqr < posSqrBV2) {
							posSqrBV2 = mPosSqr;
							randomTargetPos = new BlockPos(mutable);
						}
					}
				}
			}
		}
		return randomTargetPos;
	}

}
