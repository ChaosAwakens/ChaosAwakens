package io.github.chaosawakens.common.entity.ai.controllers.movement.hybrid;

import io.github.chaosawakens.ChaosAwakens;
import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class HerculesBeetleMovementController extends MovementController {
    private final HerculesBeetleEntity ownerBeetle;
    private MovementStatus curStatus = MovementStatus.IDLE;

    public HerculesBeetleMovementController(HerculesBeetleEntity owner) {
        super(owner);
        this.ownerBeetle = owner;
    }

    @Override
    public void tick() {
        float curSpeed = (float) (ownerBeetle.getMovementSpeed() * speedModifier);
        LivingEntity curTarget = ownerBeetle.getTarget();
        boolean hasValidTarget = curTarget != null && curTarget.isAlive();

        this.curStatus = MovementStatus.WALKING;

        switch (curStatus) {
            default: break;
            case WALKING:
                float strafeForwards = this.strafeForwards;
                float strafeRight = this.strafeRight;
                float totalMagnitude = curSpeed / MathHelper.clamp(MathHelper.sqrt(strafeForwards * strafeForwards + strafeRight * strafeRight), 1.0F, MathHelper.sqrt(strafeForwards * strafeForwards + strafeRight * strafeRight));

                strafeForwards *= totalMagnitude;
                strafeRight *= totalMagnitude;

                float verAngle = MathHelper.sin(ownerBeetle.yRot * ((float) Math.PI / 180F));
                float horAngle = MathHelper.cos(ownerBeetle.yRot * ((float) Math.PI / 180F));

                float updatedStrafeForwards = strafeForwards * horAngle - strafeRight * verAngle;
                float updatedStrafeRight = strafeRight * horAngle - strafeForwards * verAngle;
                double deltaX = wantedX - ownerBeetle.getX();
                double deltaY = wantedY - ownerBeetle.getY();
                double deltaZ = wantedZ - ownerBeetle.getZ();
                double distanceSqrd = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

                if (distanceSqrd <= ownerBeetle.getMovementThreshold()) {
                    ownerBeetle.setZza(0.0F);
                    ownerBeetle.setXxa(0.0F);
                    this.curStatus = MovementStatus.IDLE;
                    break;
                }

                if (hasValidTarget) ownerBeetle.lookAt(curTarget, 30.0F, 90.0F);
                else {
                    float updatedYawRot = (float) (MathHelper.atan2(deltaZ, deltaX) * (double) (180F / (float) Math.PI)) - 90.0F;

                    ownerBeetle.yRot = rotlerp(ownerBeetle.yRot, updatedYawRot, 180.0F);
                }

                ownerBeetle.setSpeed(curSpeed);

                if (strafeForwards != 0 && strafeRight != 0) {
                    ownerBeetle.setZza(updatedStrafeForwards);
                    ownerBeetle.setXxa(updatedStrafeRight);
                }
                this.curStatus = MovementStatus.IDLE;
                break;
            case FLYING:
                rotateBasedOnMovement();

                if (hasValidTarget) {
                    ownerBeetle.lookAt(curTarget, 30.0F, 30.0F);

                    double yDelta = curTarget.getY() - ownerBeetle.getY();
                    double xDelta = curTarget.getX() - ownerBeetle.getX();

                    ownerBeetle.xRot = (float) (-((float) Math.atan2(yDelta, xDelta)) * (Math.PI / 180.0F));
                }

                if (ownerBeetle.isInWater()) {

                }

                if (ownerBeetle.isInLava()) {

                }

                ownerBeetle.setSpeed(curSpeed);
                break;
        }
    }

    protected void updateMovementStatus() {//TODO Redo
        if (ownerBeetle.isDocile() || ((ownerBeetle.isActivelyPassivelyWandering() && !ownerBeetle.isMoving()) && ownerBeetle.getNavigation().isDone())) this.curStatus = MovementStatus.IDLE;
        if ((ownerBeetle.isMoving() || ownerBeetle.getTarget() != null || !ownerBeetle.getNavigation().isDone()) && !ownerBeetle.isFlying()) this.curStatus = MovementStatus.WALKING;
        if (ownerBeetle.isFlying()) this.curStatus = MovementStatus.FLYING;
        if ((ownerBeetle.isSwimming() || ownerBeetle.isInWater()) && !ownerBeetle.isFlying()) this.curStatus = MovementStatus.SWIMMING;
        if (ownerBeetle.isEvasive() || ownerBeetle.isCritical()) this.curStatus = MovementStatus.EVADING;
    }

    protected void rotateBasedOnMovement() {
        if (ownerBeetle.isFlying()) {
            final Vector3d curDeltaMovement = ownerBeetle.getDeltaMovement();

            ownerBeetle.yRot = (float) (-((float) Math.atan2(curDeltaMovement.z, curDeltaMovement.x)) * (Math.PI / 180.0F));

            ownerBeetle.setYHeadRot(ownerBeetle.yRot);
            ownerBeetle.setYBodyRot(ownerBeetle.yRot);
        }
    }

    private boolean hasCollision() {
        return false;
    }

    public enum MovementStatus {
        IDLE,
        WALKING,
        FLYING,
        SWIMMING,
        EVADING
    }
}
