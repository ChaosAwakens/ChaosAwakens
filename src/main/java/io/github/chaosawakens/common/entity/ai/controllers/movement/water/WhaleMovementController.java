package io.github.chaosawakens.common.entity.ai.controllers.movement.water;

import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class WhaleMovementController extends MovementController { // Stolen from the dolphin ;trollge: (bandaid solution for 1.16.5 CA) (Reorganized tho)
    protected final WhaleEntity owner;

    public WhaleMovementController(WhaleEntity owner) {
        super(owner);
        this.owner = owner;
    }

    @Override
    public void tick() {
        if (this.owner.isInWater()) this.owner.setDeltaMovement(owner.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
        if (this.operation == MovementController.Action.MOVE_TO && !this.owner.getNavigation().isDone()) {
            double xDelta = this.wantedX - this.owner.getX();
            double yDelta = this.wantedY - this.owner.getY();
            double zDelta = this.wantedZ - this.owner.getZ();
            double totalDist = xDelta * xDelta + yDelta * yDelta + zDelta * zDelta;

            if (totalDist < 2.5000003E-7F) this.mob.setZza(0.0F);
            else {
                float polarDeltaHorizontal = (float) (MathHelper.atan2(zDelta, xDelta) * (double) (180F / (float) Math.PI)) - 90.0F;

                this.owner.yRot = rotlerp(this.owner.yRot, polarDeltaHorizontal, 1.0F);
                this.owner.yBodyRot = this.owner.yRot;
                this.owner.yHeadRot = this.owner.yRot;

                float speedMod = (float)(this.speedModifier * this.owner.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (this.owner.isInWater()) {
                    this.owner.setSpeed(speedMod * 0.5F);

                    float polarDeltaSqrd = -((float) (MathHelper.atan2(yDelta, MathHelper.sqrt(xDelta * xDelta + zDelta * zDelta)) * (double) (180F / (float) Math.PI)));
                    polarDeltaSqrd = MathHelper.clamp(MathHelper.wrapDegrees(polarDeltaSqrd), -85.0F, 85.0F);

                    this.owner.xRot = rotlerp(this.owner.xRot, polarDeltaSqrd, 15.0F);

                    float zRotBasedMovement = MathHelper.cos(this.owner.xRot * ((float) Math.PI / 180F));
                    float yRotBasedMovement = MathHelper.sin(this.owner.xRot * ((float) Math.PI / 180F));

                    this.owner.zza = zRotBasedMovement * speedMod;
                    this.owner.yya = -yRotBasedMovement * speedMod;
                } else this.owner.setSpeed(speedMod * 0.1F);
            }
        } else {
            this.owner.setSpeed(0.0F);
            this.owner.setXxa(0.0F);
            this.owner.setYya(0.0F);
            this.owner.setZza(0.0F);
        }
    }
}
