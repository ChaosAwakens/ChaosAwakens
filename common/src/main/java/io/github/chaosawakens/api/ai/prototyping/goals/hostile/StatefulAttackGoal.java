package io.github.chaosawakens.api.ai.prototyping.goals.hostile;

import io.github.chaosawakens.api.misc.QuadConsumer;
import io.github.chaosawakens.api.misc.QuadPredicate;
import io.github.chaosawakens.content.entity.base.stateful.StatefulMonster;
import io.github.chaosawakens.util.EntityUtil;
import io.github.chaosawakens.util.MathUtil;
import it.unimi.dsi.fastutil.doubles.DoubleDoubleMutablePair;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class StatefulAttackGoal<AO extends StatefulMonster> extends Goal {
    protected final AO animatableOwner;
    protected final double attackTickDuration;
    protected final boolean staticAttack;
    protected final byte attackId;
    protected final DoubleDoubleMutablePair attackFrame = DoubleDoubleMutablePair.of(2.0D, 4.0D);
    @Nullable
    protected QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnStart;
    @Nullable
    protected QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnEnd;
    @Nullable
    protected QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnAttack;
    @Nullable
    protected QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnTick;
    @Nullable
    protected QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnCancel;
    @Nullable
    protected QuadPredicate<AO, @Nullable LivingEntity, List<LivingEntity>, Double> extraCancelConditions;
    protected double potentialTargetRadius = 3.0D;
    protected boolean usePreciseTicking = true;
    protected double preciseAttackTick = 0.0D;
    protected double attackArc = 100.0D;
    protected double initiationRange = Double.MAX_VALUE;
    protected double attackTickCooldown = 20.0D;
    protected double attackCooldownTimer = 0.0D;
    protected boolean performDefaultAttack = true;
    @Nullable
    protected Predicate<AO> additionalStartConditions;
    protected boolean forcePose = false;

    public StatefulAttackGoal(AO animatableOwner, double attackTickDuration, boolean staticAttack, byte attackId) {
        this.animatableOwner = animatableOwner;
        this.attackTickDuration = attackTickDuration;
        this.staticAttack = staticAttack;
        this.attackId = attackId;
    }

    public StatefulAttackGoal<AO> actionOnStart(QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnStart) {
        this.actionOnStart = actionOnStart;
        return this;
    }

    public StatefulAttackGoal<AO> actionOnEnd(QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnEnd) {
        this.actionOnEnd = actionOnEnd;
        return this;
    }

    public StatefulAttackGoal<AO> actionOnAttack(QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnAttack) {
        this.actionOnAttack = actionOnAttack;
        return this;
    }

    public StatefulAttackGoal<AO> attackFrame(double start, double end) {
        this.attackFrame.left(start);
        this.attackFrame.right(end);

        return this;
    }

    public StatefulAttackGoal<AO> potentialTargetRadius(double potentialTargetRadius) {
        this.potentialTargetRadius = potentialTargetRadius;
        return this;
    }

    public StatefulAttackGoal<AO> usePreciseTicking(boolean usePreciseTicking) {
        this.usePreciseTicking = usePreciseTicking;
        return this;
    }

    public StatefulAttackGoal<AO> attackArc(double attackArc) {
        this.attackArc = attackArc;
        return this;
    }

    public StatefulAttackGoal<AO> initiationRange(double initiationRange) {
        this.initiationRange = initiationRange;
        return this;
    }

    public StatefulAttackGoal<AO> attackTickCooldown(double attackTickCooldown) {
        this.attackTickCooldown = attackTickCooldown;
        return this;
    }

    public StatefulAttackGoal<AO> actionOnTick(QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnTick) {
        this.actionOnTick = actionOnTick;
        return this;
    }

    public StatefulAttackGoal<AO> actionOnCancel(QuadConsumer<AO, @Nullable LivingEntity, List<LivingEntity>, Double> actionOnCancel) {
        this.actionOnCancel = actionOnCancel;
        return this;
    }

    public StatefulAttackGoal<AO> extraCancelConditions(QuadPredicate<AO, @Nullable LivingEntity, List<LivingEntity>, Double> extraCancelConditions) {
        this.extraCancelConditions = extraCancelConditions;
        return this;
    }

    public StatefulAttackGoal<AO> performDefaultAttack(boolean performDefaultAttack) {
        this.performDefaultAttack = performDefaultAttack;
        return this;
    }

    public StatefulAttackGoal<AO> additionalStartConditions(Predicate<AO> additionalStartConditions) {
        this.additionalStartConditions = additionalStartConditions;
        return this;
    }

    public StatefulAttackGoal<AO> forcePose(boolean forcePose) {
        this.forcePose = forcePose;
        return this;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = animatableOwner.getTarget();
        double validRange = initiationRange != Double.MAX_VALUE ? initiationRange * initiationRange : target == null ? animatableOwner.getBbWidth() / 2.0D : animatableOwner.getMeleeAttackRangeSqr(target);

        return ((attackCooldownTimer = Math.max(--attackCooldownTimer, 0.0D)) <= 0.0D) && animatableOwner.isAlive() && !animatableOwner.isAttacking() && target != null && target.isAlive() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target) && !target.isInvulnerable() && animatableOwner.distanceToSqr(target) <= validRange && (additionalStartConditions == null || additionalStartConditions.test(animatableOwner));
    }

    @Override
    public boolean canContinueToUse() {
        double currentAttackTick = usePreciseTicking ? preciseAttackTick : preciseAttackTick; // TODO
        double endTick = usePreciseTicking ? attackTickDuration : attackTickDuration;

        return animatableOwner.isAlive() && currentAttackTick < endTick && (extraCancelConditions == null || !extraCancelConditions.test(animatableOwner, animatableOwner.getTarget(), EntityUtil.getAllEntitiesAround(animatableOwner, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius), currentAttackTick));
    }

    @Override
    public void start() {
        LivingEntity target = animatableOwner.getTarget();

        this.preciseAttackTick = 0.0D;

        animatableOwner.setAttackID(attackId);

        if (actionOnStart != null) actionOnStart.accept(animatableOwner, target, EntityUtil.getAllEntitiesAround(animatableOwner, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius), 0.0D);
        if (staticAttack) {
            animatableOwner.setYRot(animatableOwner.yRotO);
            animatableOwner.getNavigation().stop();
            animatableOwner.setDeltaMovement(animatableOwner.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
        }
    }

    @Override
    public void stop() {
        if (actionOnEnd != null) actionOnEnd.accept(animatableOwner, animatableOwner.getTarget(), EntityUtil.getAllEntitiesAround(animatableOwner, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius), attackTickDuration);

        animatableOwner.resetAttackId();

        this.preciseAttackTick = 0.0D;
        this.attackCooldownTimer = attackTickCooldown;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        return animatableOwner.isDeadOrDying();
    }

    @Override
    public void tick() {
        LivingEntity target = animatableOwner.getTarget();

        if (staticAttack) {
            animatableOwner.setYRot(animatableOwner.yRotO);
            animatableOwner.getNavigation().stop();
            animatableOwner.setDeltaMovement(animatableOwner.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
        }

        if (usePreciseTicking) this.preciseAttackTick++;

        double currentAttackTick = usePreciseTicking ? preciseAttackTick : preciseAttackTick; // TODO

        if (target != null && !staticAttack && ((!performDefaultAttack && actionOnAttack == null) || !MathUtil.isBetween(currentAttackTick, attackFrame.leftDouble(), attackFrame.rightDouble()))) animatableOwner.getLookControl().setLookAt(target, 30.0F, 30.0F);
        if (actionOnTick != null) actionOnTick.accept(animatableOwner, target, EntityUtil.getAllEntitiesAround(animatableOwner, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius), currentAttackTick);
        if (actionOnAttack != null && MathUtil.isBetween(currentAttackTick, attackFrame.leftDouble(), attackFrame.rightDouble())) actionOnAttack.accept(animatableOwner, target, EntityUtil.getAllEntitiesAround(animatableOwner, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius), currentAttackTick);
        if (performDefaultAttack && target != null) {
            List<LivingEntity> potentialTargets = EntityUtil.getAllEntitiesAround(animatableOwner, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius, potentialTargetRadius);

            for (LivingEntity potentialTarget : potentialTargets) {
                if (animatableOwner.isAlliedTo(potentialTarget) || animatableOwner.getClass() == potentialTarget.getClass()) continue;

                double targetAngle = MathUtil.getRelativeAngleBetweenEntities(animatableOwner, potentialTarget);
                double attackAngle = animatableOwner.yBodyRot % 360;

                if (targetAngle < 0) targetAngle += 360;
                if (attackAngle < 0) attackAngle += 360;

                double relativeHitAngle = targetAngle - attackAngle;

                if (MathUtil.isBetween(currentAttackTick, attackFrame.leftDouble(), attackFrame.rightDouble()) && animatableOwner.distanceToSqr(potentialTarget) <= potentialTargetRadius * potentialTargetRadius && MathUtil.isWithinAngleRestriction(relativeHitAngle, attackArc)) {
                    animatableOwner.doHurtTarget(potentialTarget);
                }
            }
        }
    }
}
