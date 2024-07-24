package io.github.chaosawakens.common.entity.ai.goals.hostile.robo.robowarrior;

import io.github.chaosawakens.api.animation.IAnimationBuilder;
import io.github.chaosawakens.common.entity.hostile.robo.RoboWarriorEntity;
import io.github.chaosawakens.common.registry.CASoundEvents;
import io.github.chaosawakens.common.util.ObjectUtil;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

import java.util.function.Supplier;

public class RoboWarriorShieldGoal extends Goal {
    protected final RoboWarriorEntity owner;
    protected final Supplier<IAnimationBuilder> shieldUpAnim;
    protected final Supplier<IAnimationBuilder> shieldAnim;
    protected final Supplier<IAnimationBuilder> shieldDownAnim;
    protected final Supplier<IAnimationBuilder> shieldDestroyedAnim;
    protected int curCooldown;
    protected boolean hasPlayedDeactivationSound = false;

    public RoboWarriorShieldGoal(RoboWarriorEntity owner, Supplier<IAnimationBuilder> shieldUpAnim, Supplier<IAnimationBuilder> shieldAnim, Supplier<IAnimationBuilder> shieldDownAnim, Supplier<IAnimationBuilder> shieldDestroyedAnim) {
        this.owner = owner;
        this.shieldUpAnim = shieldUpAnim;
        this.shieldAnim = shieldAnim;
        this.shieldDownAnim = shieldDownAnim;
        this.shieldDestroyedAnim = shieldDestroyedAnim;
    }

    @Override
    public boolean canUse() {
        if (curCooldown > 0) curCooldown--;
        return ObjectUtil.performNullityChecks(false, owner, shieldAnim.get(), shieldUpAnim.get(), shieldDestroyedAnim.get(), shieldDownAnim.get()) && !owner.isDeadOrDying()
                && !owner.isShielded() && !owner.isAttacking() && owner.hasHitHealthThreshold() && !owner.isShieldDestroyed() && curCooldown <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        return ObjectUtil.performNullityChecks(false, owner, shieldAnim.get(), shieldUpAnim.get(), shieldDestroyedAnim.get(), shieldDownAnim.get())
                && !shouldDeactivateShield() && !owner.isDeadOrDying();
    }

    @Override
    public void start() {
        owner.setShielded(true);
        owner.setShieldDestroyed(false);
        owner.setShieldDamage(0);
        owner.setStoredDamage(0);
        owner.getNavigation().stop();

        owner.playAnimation(shieldUpAnim.get(), true);
        owner.playSound(CASoundEvents.ROBO_WARRIOR_SHIELD_ACTIVATION.get(), 1.0F, 1.0F);

        this.hasPlayedDeactivationSound = false;
    }

    @Override
    public void stop() {
        owner.setShielded(false);
        owner.setShieldDestroyed(false);
        owner.setHasHitHealthThreshold(false);
        owner.setAttackCooldown(10);
        owner.setShieldActivationTime(0);
        owner.setStoredDamage(0);
        owner.setShieldDamage(0);

        owner.stopAnimation(shieldDownAnim.get());
        owner.stopAnimation(shieldDestroyedAnim.get());

        this.curCooldown = MathHelper.nextInt(owner.getRandom(), 200, 300);
    }

    @Override
    public boolean isInterruptable() {
        return owner.isDeadOrDying();
    }

    private boolean isShieldUp() {
        return shieldUpAnim.get().getWrappedAnimProgress() >= MathHelper.nextDouble(owner.getRandom(), shieldUpAnim.get().getWrappedAnimLength() / 1.4D, shieldUpAnim.get().getWrappedAnimLength() / 1.2D);
    }

    private boolean shouldDeactivateShield() {
        return owner.isShieldDestroyed() ? shieldDestroyedAnim.get().getWrappedAnimProgress() >= MathHelper.nextDouble(owner.getRandom(), shieldDestroyedAnim.get().getWrappedAnimLength() / 1.8D, shieldDestroyedAnim.get().getWrappedAnimLength() / 1.2D)
                : shieldDownAnim.get().getWrappedAnimProgress() >= MathHelper.nextDouble(owner.getRandom(), shieldDownAnim.get().getWrappedAnimLength() / 1.8D, shieldDownAnim.get().getWrappedAnimLength() / 1.2D);
    }

    @Override
    public void tick() {
        owner.setDeltaMovement(0, owner.getDeltaMovement().y, 0);
        owner.getNavigation().stop();

        if (isShieldUp()) owner.playAnimation(shieldAnim.get(), false);

        if (shieldAnim.get().isPlaying()) owner.heal(0.5F);

        if (owner.isShieldDestroyed()) {
            owner.playAnimation(shieldDestroyedAnim.get(), false);

            if (!hasPlayedDeactivationSound) {
                owner.playSound(CASoundEvents.ROBO_WARRIOR_SHIELD_DESTROYED.get(), 1.0F, 1.0F);

                this.hasPlayedDeactivationSound = true;
            }
        } else if (owner.getHealth() >= owner.getMaxHealth()) {
            owner.playAnimation(shieldDownAnim.get(), false);

            if (!hasPlayedDeactivationSound) {
                owner.playSound(CASoundEvents.ROBO_WARRIOR_SHIELD_DEACTIVATION.get(), 1.0F, 1.0F);

                this.hasPlayedDeactivationSound = true;
            }
        }
    }
}
