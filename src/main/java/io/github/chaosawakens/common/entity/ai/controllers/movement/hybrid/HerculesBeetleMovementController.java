package io.github.chaosawakens.common.entity.ai.controllers.movement.hybrid;

import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.controller.MovementController;
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

        updateMovementStatus();

        switch (curStatus) {
            default: break;
            case WALKING:
                if (hasValidTarget) ownerBeetle.lookAt(curTarget, 30.0F, 30.0F);

                ownerBeetle.setSpeed(curSpeed);
                ownerBeetle.setZza(strafeForwards);
                ownerBeetle.setXxa(strafeRight);
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
                ownerBeetle.setZza(strafeForwards);
                ownerBeetle.setXxa(strafeRight);
                break;
        }
    }

    protected void updateMovementStatus() {
        if (ownerBeetle.isDocile() || ((ownerBeetle.isActivelyPassivelyWandering() && !ownerBeetle.isMoving()) && ownerBeetle.getNavigation().isDone())) this.curStatus = MovementStatus.IDLE;
        else if (ownerBeetle.isMoving() && !ownerBeetle.isFlying()) this.curStatus = MovementStatus.WALKING;
        else if (ownerBeetle.isFlying()) this.curStatus = MovementStatus.FLYING;
        else if ((ownerBeetle.isSwimming() || ownerBeetle.isInWater()) && !ownerBeetle.isFlying()) this.curStatus = MovementStatus.SWIMMING;
        else if (ownerBeetle.isEvasive() || ownerBeetle.isCritical()) this.curStatus = MovementStatus.EVADING;
    }

    protected void rotateBasedOnMovement() {
        if (ownerBeetle.isFlying()) {
            final Vector3d curDeltaMovement = ownerBeetle.getDeltaMovement();

            ownerBeetle.yRot = (float) (-((float) Math.atan2(curDeltaMovement.x, curDeltaMovement.z)) * (Math.PI / 180.0F));

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
