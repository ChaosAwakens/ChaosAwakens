package io.github.chaosawakens.common.entity.ai.controllers.movement.air;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class JumpFlyingMovementController extends MovementController {
    private final int maxTurn;
    private final boolean hoversInPlace;
    public State state = State.WALKING;

    public JumpFlyingMovementController(MobEntity p_i225710_1_, int p_i225710_2_, boolean p_i225710_3_) {
        super(p_i225710_1_);
        this.maxTurn = p_i225710_2_;
        this.hoversInPlace = p_i225710_3_;
    }

    public void tick() {
        if (this.operation == MovementController.Action.MOVE_TO) {
            this.operation = MovementController.Action.WAIT;
            if(state != State.JUMPING)
                this.mob.setNoGravity(true);
            double deltaX = this.wantedX - this.mob.getX();
            double deltaY = this.wantedY - this.mob.getY();
            double deltaZ = this.wantedZ - this.mob.getZ();
            double distanceSqr = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
            if (distanceSqr < (double)2.5000003E-7F) {
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
                state = State.WALKING;
                return;
            }

            float targetYAngle = (float)(MathHelper.atan2(deltaZ, deltaX) * 180D / Math.PI) - 90.0F;
            this.mob.yRot = this.rotlerp(this.mob.yRot, targetYAngle, 90.0F);
            float speed;
            if (this.mob.isOnGround()) {
                speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            } else {
                speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
            }

            this.mob.setSpeed(speed);
            double horizontalDistSqr = deltaX * deltaX + deltaZ * deltaZ;
            double horizontalDist = MathHelper.sqrt(horizontalDistSqr);
            float targetXAngle = (float)(-(MathHelper.atan2(deltaY, horizontalDist) * 180F / Math.PI));
            this.mob.xRot = this.rotlerp(this.mob.xRot, targetXAngle, (float)this.maxTurn);
            if (deltaY < 1.0D && deltaY > mob.maxUpStep && horizontalDistSqr < Math.max(1.0, this.mob.getBbWidth())) {
                this.mob.setNoGravity(false);
                this.mob.getJumpControl().jump();
                this.operation = MovementController.Action.JUMPING;
                state = State.JUMPING;
            } else if(deltaY < 0.0D || deltaY > 1.0D) {
                this.mob.setYya(deltaY < 0.0D ? -speed : speed);
                state = State.FLYING;
            }
            if (mob.yya == 0.0F && state == State.FLYING)
                state = State.WALKING;
        } else if (this.operation == MovementController.Action.JUMPING) {
            this.mob.setNoGravity(false);
            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (this.mob.isOnGround()) {
                this.operation = MovementController.Action.WAIT;
                state = State.WALKING;
            }
        } else {
            if (!this.hoversInPlace) {
                this.mob.setNoGravity(false);
            }

            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
            state = State.WALKING;
        }

    }
    public enum State {
        WALKING,
        JUMPING,
        FLYING
    }
}
