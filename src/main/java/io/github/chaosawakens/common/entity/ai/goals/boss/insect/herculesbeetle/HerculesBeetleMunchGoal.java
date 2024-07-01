package io.github.chaosawakens.common.entity.ai.goals.boss.insect.herculesbeetle;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.boss.insect.HerculesBeetleEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.util.EntityUtil;
import io.github.chaosawakens.common.util.MathUtil;
import io.github.chaosawakens.common.util.ObjectUtil;
import io.github.chaosawakens.mixins.IEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class HerculesBeetleMunchGoal extends Goal {
    private final HerculesBeetleEntity ownerBeetle;
    protected final Supplier<IAnimationBuilder> grabAnim;
    protected final Supplier<IAnimationBuilder> munchAttackAnim;
    protected final byte attackId;
    protected final double angleRange = 120.0D;
    protected int curCooldown = 120; //TODO I love inconsistencies in constant-setting (:trollge:)
    protected int maxDur = MathHelper.nextInt(new Random(), 120, 360);
    @Nullable
    protected LivingEntity targetToBeGrabbed;
    protected boolean shouldFling = false;
    protected boolean miscContinuationFlags = true;

    public HerculesBeetleMunchGoal(HerculesBeetleEntity ownerBeetle, Supplier<IAnimationBuilder> grabAnim, Supplier<IAnimationBuilder> munchAttackAnim, byte attackId) {
        this.ownerBeetle = ownerBeetle;
        this.grabAnim = grabAnim;
        this.munchAttackAnim = munchAttackAnim;
        this.attackId = attackId;
    }

    @Override
    public boolean canUse() {
        if (curCooldown > 0) curCooldown--;

        return ObjectUtil.performNullityChecks(false, ownerBeetle, targetToBeGrabbed = ownerBeetle.getTarget()) && ownerBeetle.isActive() && !ownerBeetle.isOnAttackCooldown() && curCooldown <= 0 && !targetToBeGrabbed.isInvulnerable() && ownerBeetle.isAlive() && !ownerBeetle.isAttacking() && targetToBeGrabbed.isAlive()
                && !ownerBeetle.isOnFire() && !ownerBeetle.isInLava() && !ownerBeetle.isOnAttackCooldown() && !ownerBeetle.isFlinging() // JIC
                && ownerBeetle.distanceTo(targetToBeGrabbed) <= ownerBeetle.getMeleeAttackReach() && ((IEntityAccessor) targetToBeGrabbed).invokeCanRide(ownerBeetle) //TODO Would it better to check with size? Eh, problem for 1.20.1+ me :emojibruh:
                && ownerBeetle.getRandom().nextInt(20) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return ObjectUtil.performNullityChecks(false, ownerBeetle, targetToBeGrabbed, grabAnim.get(), munchAttackAnim.get()) && !ownerBeetle.isDeadOrDying()
                && !targetToBeGrabbed.isDeadOrDying() && ((IEntityAccessor) ownerBeetle.getTarget()).invokeCanRide(ownerBeetle) && maxDur > 0 && !shouldFling && miscContinuationFlags
                && (!grabAnim.get().hasAnimationFinished() || ownerBeetle.hasPassenger(targetToBeGrabbed));
    }

    @Override
    public void start() {
        ownerBeetle.setAttackID(attackId);
        ownerBeetle.getNavigation().stop();

        ownerBeetle.playAnimation(grabAnim.get(), true);
        ownerBeetle.playSound(CASoundEvents.HERCULES_BEETLE_GRAB.get(), 1.0F, 1.0F);

        this.shouldFling = !ownerBeetle.isDeadOrDying() && ownerBeetle.getLastDamageAmount() >= 30.0F || (ownerBeetle.getHealth() <= 150.0F && ownerBeetle.getLastDamageAmount() >= 15.0F) || ownerBeetle.isInLava() || ownerBeetle.isOnFire();
    }

    @Override
    public void stop() {
        ownerBeetle.setAttackID((byte) 0);
        ownerBeetle.setAttackCooldown(20);
        ownerBeetle.stopAnimation(grabAnim.get());
        ownerBeetle.stopAnimation(munchAttackAnim.get());

        if (shouldFling && !targetToBeGrabbed.isDeadOrDying()) ownerBeetle.setFlinging(true);
        else targetToBeGrabbed.stopRiding();

        this.curCooldown = 120;
        this.maxDur = MathHelper.nextInt(ownerBeetle.getRandom(), 120, 360);
        this.miscContinuationFlags = true;
        this.targetToBeGrabbed = null;
    }

    @Override
    public boolean isInterruptable() {
        return ownerBeetle.isDeadOrDying();
    }

    @Override
    public void tick() {
        ownerBeetle.stopAnimation(ownerBeetle.getWalkAnim());

        if (!ObjectUtil.performNullityChecks(false, targetToBeGrabbed)) return;

        this.shouldFling = !ownerBeetle.isDeadOrDying() && (ownerBeetle.getLastDamageAmount() >= 30.0F || (ownerBeetle.getHealth() <= 150.0F && ownerBeetle.getLastDamageAmount() >= 15.0F) || ownerBeetle.isInLava() || ownerBeetle.isOnFire());

        if (grabAnim.get().getWrappedAnimProgress() < 4.8D) ownerBeetle.lookAt(targetToBeGrabbed, 30.0F, 30.0F);
        else if (MathUtil.isBetween(grabAnim.get().getWrappedAnimProgress(), 4.8D, 10.0D)) {
            double reach = ownerBeetle.getMeleeAttackReach();
            List<LivingEntity> potentialAffectedTargets = EntityUtil.getAllEntitiesAround(ownerBeetle, reach, reach, reach, reach);

            for (LivingEntity potentialAffectedTarget : potentialAffectedTargets) {
                if (ownerBeetle.isAlliedTo(potentialAffectedTarget) || ownerBeetle.getClass() == potentialAffectedTarget.getClass() || potentialAffectedTarget.isInvulnerable() || potentialAffectedTarget.noPhysics) continue;
                if (potentialAffectedTarget instanceof PlayerEntity && ((PlayerEntity) potentialAffectedTarget).isCreative()) continue; // Needs to be extracted into its own conditional because Java class-casting :face_with_3_hearts:
                if (targetToBeGrabbed != null && targetToBeGrabbed.isAlive() && ownerBeetle.hasPassenger(targetToBeGrabbed)) break;

                double targetAngle = MathUtil.getRelativeAngleBetweenEntities(ownerBeetle, potentialAffectedTarget);
                double attackAngle = ownerBeetle.yBodyRot % 360;

                if (targetAngle < 0) targetAngle += 360;
                if (attackAngle < 0) attackAngle += 360;

                double relativeHitAngle = targetAngle - attackAngle;
                float hitDistanceSqr = (float) (Math.sqrt((targetToBeGrabbed.getZ() - ownerBeetle.getZ()) * (targetToBeGrabbed.getZ() - ownerBeetle.getZ()) + (targetToBeGrabbed.getX() - ownerBeetle.getX()) * (targetToBeGrabbed.getX() - ownerBeetle.getX())) - ownerBeetle.getBbWidth() / 2F);

                if (hitDistanceSqr <= reach && MathUtil.isWithinAngleRestriction(relativeHitAngle, angleRange)) {
                    targetToBeGrabbed = potentialAffectedTarget;
                    if (!ownerBeetle.hasPassenger(targetToBeGrabbed)) targetToBeGrabbed.startRiding(ownerBeetle, true);
                    break;
                }
            }
        }

        if (grabAnim.get().hasAnimationFinished() && (targetToBeGrabbed.isDeadOrDying() || !ownerBeetle.hasPassenger(targetToBeGrabbed))) {
            this.miscContinuationFlags = false;
            return;
        }

        if (grabAnim.get().hasAnimationFinished() && targetToBeGrabbed.isAlive() && ownerBeetle.hasPassenger(targetToBeGrabbed) && !ownerBeetle.isPlayingAnimation(munchAttackAnim.get())) ownerBeetle.playAnimation(munchAttackAnim.get(), false);
        else if (ownerBeetle.isPlayingAnimation(munchAttackAnim.get()) && ownerBeetle.hasPassenger(targetToBeGrabbed)) {
            --maxDur;

            if (ownerBeetle.tickCount % 2 == 0) ownerBeetle.doHurtTarget(targetToBeGrabbed);
        }

        if (targetToBeGrabbed.isDeadOrDying()) ownerBeetle.stopAnimation(munchAttackAnim.get());
    }
}
