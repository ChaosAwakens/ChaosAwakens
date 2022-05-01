package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.LavaMobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.MathHelper;

public class LavaMoveHelper extends MovementController{
    private final LavaMobEntity entity;

    public LavaMoveHelper(LavaMobEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void tick() {
        if(this.entity.isEyeInFluid(FluidTags.LAVA)) {
            this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
        }

        if(this.operation == MovementController.Action.MOVE_TO && !this.entity.getNavigation().isStuck()) {
            double d0 = this.getWantedX() - this.entity.getX();
            double d1 = this.getWantedY() - this.entity.getY();
            double d2 = this.getWantedZ() - this.entity.getZ();
            double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 = d1 / d3;
            float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
            this.entity.yRot = this.rotlerp(this.entity.yRot, f, 90.0F);
            this.entity.yRotO = this.entity.yRot;
            float f1 = (float) (this.speedModifier * this.entity.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
            this.entity.setSpeed(MathHelper.lerp(0.125F, this.entity.getSpeed(), f1));
            this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0D, (double) this.entity.getSpeed() * d1 * 0.1D, 0.0D));
        } else {
            this.entity.setSpeed(0.0F);
        }
    }

}
