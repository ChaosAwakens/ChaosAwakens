package io.github.chaosawakens.common.entity.ai.goals.boss.insect.herculesbeetle;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

import java.util.function.Supplier;

public class HerculesBeetleDefenseMechanismGoal extends Goal {
    private final HerculesBeetleEntity ownerBeetle;
    private final Supplier<IAnimationBuilder> defenseMechAnim;
    private final byte defenseMechId; // Lazy solution cuz I cba to substitute attack checks for all the movement/other goal stuff when the update is literally a few days away and is the last update for this version lol
    private int curCooldown = 60;

    public HerculesBeetleDefenseMechanismGoal(HerculesBeetleEntity ownerBeetle, Supplier<IAnimationBuilder> defenseMechAnim, byte defenseMechId) {
        this.ownerBeetle = ownerBeetle;
        this.defenseMechAnim = defenseMechAnim;
        this.defenseMechId = defenseMechId;
    }

    @Override
    public boolean canUse() {
        if (curCooldown > 0) curCooldown--; //TODO Maybe inline? Same with other goals n stuff? Eh, who cares.
        return !ownerBeetle.isDeadOrDying() && ownerBeetle.isActive() && !ownerBeetle.isFlinging() && !ownerBeetle.isAttacking() && ownerBeetle.getAttackCooldown() <= 0 && ((ownerBeetle.getTarget() != null && !ownerBeetle.getTarget().isDeadOrDying()) || (ownerBeetle.getLastHurtByMob() != null && !ownerBeetle.getLastHurtByMob().isDeadOrDying())) && (ownerBeetle.getLastDamageAmount() >= 50.0F || (ownerBeetle.getLastDamageAmount() >= 25.0F && ownerBeetle.isOnFire()) || (ownerBeetle.getLastDamageAmount() >= 20.0F && ownerBeetle.getHealth() <= 100.0F) || (ownerBeetle.getHealth() <= 50.0F) || (!EntityUtil.checkIncomingProjectiles(ownerBeetle, 55.0D, 0.2D).isEmpty()));
    }

    @Override
    public boolean canContinueToUse() {
        return ObjectUtil.performNullityChecks(false, ownerBeetle, defenseMechAnim.get(), defenseMechId) && !ownerBeetle.isDeadOrDying() && !defenseMechAnim.get().hasAnimationFinished();
    }

    @Override
    public void start() {
        ownerBeetle.setAttackID(defenseMechId);
        ownerBeetle.playAnimation(defenseMechAnim.get(), true);
        ownerBeetle.playSound(CASoundEvents.HERCULES_BEETLE_ROAR.get(), 1.0F, 1.0F);
    }

    @Override
    public void stop() {
        ownerBeetle.setAttackID((byte) 0);
        ownerBeetle.setAttackCooldown(10);
        ownerBeetle.stopAnimation(defenseMechAnim.get());

        this.curCooldown = MathHelper.nextInt(ownerBeetle.getRandom(), 100, 400);
    }

    @Override
    public boolean isInterruptable() {
        return ownerBeetle.isDeadOrDying();
    }

    @Override
    public void tick() {
        ownerBeetle.setDeltaMovement(0, ownerBeetle.getDeltaMovement().y, 0);
        ownerBeetle.getNavigation().stop();

        EntityUtil.freezeEntityRotation(ownerBeetle);
    }
}
