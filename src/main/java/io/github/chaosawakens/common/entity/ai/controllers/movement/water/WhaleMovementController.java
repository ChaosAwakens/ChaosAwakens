package io.github.chaosawakens.common.entity.ai.controllers.movement.water;

import io.github.chaosawakens.common.entity.creature.water.WhaleEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class WhaleMovementController extends MovementController { // Stolen from the dolphin ;trollge: (bandaid solution for 1.16.5 CA)
    protected final WhaleEntity owner;

    public WhaleMovementController(WhaleEntity owner) {
        super(owner);
        this.owner = owner;
    }

    @Override
    public void tick() {
        if (this.owner.isInWater()) {
            this.owner.setDeltaMovement(owner.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
        }

        if (this.operation == MovementController.Action.MOVE_TO && !this.owner.getNavigation().isDone()) {
            double d0 = this.wantedX - this.owner.getX();
            double d1 = this.wantedY - this.owner.getY();
            double d2 = this.wantedZ - this.owner.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < (double)2.5000003E-7F) {
                this.mob.setZza(0.0F);
            } else {
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.owner.yRot = this.rotlerp(this.owner.yRot, f, 10.0F);
                this.owner.yBodyRot = this.owner.yRot;
                this.owner.yHeadRot = this.owner.yRot;
                float f1 = (float)(this.speedModifier * this.owner.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (this.owner.isInWater()) {
                    this.owner.setSpeed(f1 * 0.02F);
                    float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                    f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                    this.owner.xRot = this.rotlerp(this.owner.xRot, f2, 5.0F);
                    float f3 = MathHelper.cos(this.owner.xRot * ((float)Math.PI / 180F));
                    float f4 = MathHelper.sin(this.owner.xRot * ((float)Math.PI / 180F));
                    this.owner.zza = f3 * f1;
                    this.owner.yya = -f4 * f1;
                } else {
                    this.owner.setSpeed(f1 * 0.1F);
                }

            }
        } else {
            this.owner.setSpeed(0.0F);
            this.owner.setXxa(0.0F);
            this.owner.setYya(0.0F);
            this.owner.setZza(0.0F);
        }
    }
}
