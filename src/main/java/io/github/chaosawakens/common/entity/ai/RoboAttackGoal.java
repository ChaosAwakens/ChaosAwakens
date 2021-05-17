package io.github.chaosawakens.common.entity.ai;

import io.github.chaosawakens.common.entity.RoboEntity;
import io.github.chaosawakens.common.entity.projectile.RoboLaserEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


public class RoboAttackGoal extends Goal {
    private final RoboEntity parentEntity;

    public RoboAttackGoal(RoboEntity robo) {
        this.parentEntity = robo;
    }

    public boolean shouldExecute() {
        return this.parentEntity.getAttackTarget() != null;
    }

    public void tick() {
        LivingEntity livingentity = this.parentEntity.getAttackTarget();
        double d0 = 64.0D;
        if (livingentity.getDistanceSq(this.parentEntity) < 4096.0D && this.parentEntity.canEntityBeSeen(livingentity)) {
            World world = this.parentEntity.world;
            world.playEvent(null, 1015, this.parentEntity.getPosition(), 0);

            double d1 = 4.0D;
            Vector3d vector3d = this.parentEntity.getLook(1.0F);
            double d2 = livingentity.getPosX() - this.parentEntity.getPosX();
            double d3 = livingentity.getPosYHeight(0.3333333333333333D) - this.parentEntity.getPosY();
            double d4 = livingentity.getPosZ() - this.parentEntity.getPosZ();
            if (!this.parentEntity.isSilent()) {
                world.playEvent(null, 1016, this.parentEntity.getPosition(), 0);
            }

            RoboLaserEntity roboLaserEntity = new RoboLaserEntity(world, this.parentEntity, d2, d3, d4);
            float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
            roboLaserEntity.shoot(d0, d1 + (double)f, d2, 1.5F, 0.0F);
            this.parentEntity.world.playSound(null, this.parentEntity.getPosX(), this.parentEntity.getPosY(), this.parentEntity.getPosZ(), SoundEvents.BLOCK_DISPENSER_DISPENSE, this.parentEntity.getSoundCategory(), 1.0F, 1.0F + 1 * 0.2F);
            world.addEntity(roboLaserEntity);
        }
    }
}