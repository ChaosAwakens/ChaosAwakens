package io.github.chaosawakens.common.entity.ai.goals.boss.insect.herculesbeetle;

import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class HerculesBeetleMoveToTargetGoal extends Goal {
    private final HerculesBeetleEntity ownerBeetle;
    @Nullable
    private Path curPath;
    private int pathResetCooldown = 100;

    public HerculesBeetleMoveToTargetGoal(HerculesBeetleEntity ownerBeetle) {
        this.ownerBeetle = ownerBeetle;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public void start() {
        ownerBeetle.lookAt(ownerBeetle.getTarget(), 30.0F, 100.0F);

        this.curPath = ownerBeetle.getNavigation().createPath(ownerBeetle.getTarget(), 1);
        this.pathResetCooldown = 100;
    }

    @Override
    public void stop() {
        ownerBeetle.getNavigation().stop();
    }

    @Override
    public boolean canUse() {
        return ownerBeetle.getTarget() != null && ownerBeetle.getTarget().isAlive() &&
                ownerBeetle.isAlive() && !ownerBeetle.isEvasive() && !ownerBeetle.isCritical() && !ownerBeetle.isAttacking() &&
                (ownerBeetle.isFlying() || (ownerBeetle.isWalking() && ownerBeetle.distanceTo(ownerBeetle.getTarget()) > ownerBeetle.getMeleeAttackReach(ownerBeetle.getTarget())));
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public boolean isInterruptable() {
        return ownerBeetle.isDeadOrDying();
    }

    @Override
    public void tick() {
        if (ownerBeetle.isFlying()) {
            if (curPath == null) this.curPath = ownerBeetle.getNavigation().createPath(ownerBeetle.getTarget(), 4);
            else ownerBeetle.getNavigation().moveTo(curPath, 1.2D);

            if (--pathResetCooldown <= 0) {
                this.curPath = ownerBeetle.getNavigation().createPath(ownerBeetle.getTarget(), 3);
                this.pathResetCooldown = ownerBeetle.getRandom().nextInt(100) + 10;
            }
        } else if (ownerBeetle.isWalking()) {
            if (curPath == null) this.curPath = ownerBeetle.getNavigation().createPath(ownerBeetle.getTarget(), 0);
            else ownerBeetle.getNavigation().moveTo(curPath, 1.0D);

            if (--pathResetCooldown <= 0) {
                this.curPath = ownerBeetle.getNavigation().createPath(ownerBeetle.getTarget(), 0);
                this.pathResetCooldown = ownerBeetle.getRandom().nextInt(5) + 5;
            }
        }
    }
}
