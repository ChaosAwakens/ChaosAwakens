package io.github.chaosawakens.common.entity.ai.controllers.movement.air;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class JumpFlyingMovementController extends MovementController {
    private final int maxTurn;
    private final boolean hoversInPlace;
    public boolean isFlying;

    public JumpFlyingMovementController(MobEntity p_i225710_1_, int p_i225710_2_, boolean p_i225710_3_) {
        super(p_i225710_1_);
        this.maxTurn = p_i225710_2_;
        this.hoversInPlace = p_i225710_3_;
    }

    public void tick() {
        if (isFlying)
            this.mob.setNoGravity(true);
        if (this.operation == MovementController.Action.JUMPING) {
            this.mob.setNoGravity(false);
            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
        } else if (this.operation == MovementController.Action.MOVE_TO) {
            this.operation = MovementController.Action.WAIT;
            Vector3d deltaVector = new Vector3d(this.wantedX - this.mob.getX(), this.wantedY - this.mob.getY(), this.wantedZ - this.mob.getZ());
            if (deltaVector.lengthSqr() < (double)2.5000003E-7F) {
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
                isFlying = false;
                return;
            }

            this.mob.yRot = this.rotlerp(this.mob.yRot, (float)(MathHelper.atan2(deltaVector.z, deltaVector.x) * 180D / Math.PI) - 90.0F, 90.0F);
            float speed;
            if (this.mob.isOnGround())
                speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            else
                speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));

            this.mob.setSpeed(speed);
            double horizontalDistSqr = deltaVector.subtract(0, deltaVector.y, 0).length();
            this.mob.xRot = this.rotlerp(this.mob.xRot, (float)(-(MathHelper.atan2(deltaVector.y, MathHelper.sqrt(horizontalDistSqr)) * 180F / Math.PI)), (float)this.maxTurn);
            if (deltaVector.y < 0.0D && deltaVector.y > -1.0D) {
                this.mob.setNoGravity(false);
                isFlying = false;
            } else if (deltaVector.y < 1.0D && deltaVector.y > mob.maxUpStep && horizontalDistSqr < Math.max(1.0, this.mob.getBbWidth())) {
                this.mob.setNoGravity(false);
                this.mob.getJumpControl().jump();
                this.operation = MovementController.Action.JUMPING;
                isFlying = false;
            } else if(deltaVector.y < -1.0D || deltaVector.y > 1.0D) {
                this.mob.setYya(deltaVector.y < -1.0D ? -speed : speed);
                isFlying = true;
            }
            if (mob.yya == 0.0F && isFlying)
                isFlying = false;
            return;
        }
        if (this.mob.isOnGround()) {
            this.operation = MovementController.Action.WAIT;
            isFlying = false;
        }

        if (!this.hoversInPlace)
            this.mob.setNoGravity(false);

        this.mob.setYya(0.0F);
        this.mob.setZza(0.0F);
    }
}
